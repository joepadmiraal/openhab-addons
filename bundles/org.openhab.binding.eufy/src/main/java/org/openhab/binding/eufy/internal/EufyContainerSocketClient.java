/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.eufy.internal;

import static org.openhab.binding.eufy.internal.EufyBindingConstants.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.openhab.binding.eufy.internal.annotations.EufyChannel;
import org.openhab.binding.eufy.internal.dto.Message;
import org.openhab.binding.eufy.internal.dto.ResultMessage;
import org.openhab.binding.eufy.internal.dto.StatusMessage;
import org.openhab.binding.eufy.internal.dto.VersionMessage;
import org.openhab.binding.eufy.internal.dto.objects.EufyObject;
import org.openhab.binding.eufy.internal.dto.outgoing.Command;
import org.openhab.binding.eufy.internal.dto.outgoing.KeepaliveRequest;
import org.openhab.binding.eufy.internal.dto.outgoing.OutgoingMessage;
import org.openhab.binding.eufy.internal.dto.outgoing.SchemaRequest;
import org.openhab.binding.eufy.internal.dto.outgoing.SetPropertyRequest;
import org.openhab.binding.eufy.internal.dto.outgoing.StartListeningRequest;
import org.openhab.binding.eufy.internal.handlers.ContainerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client that handles the WebSocket communication between the Docker image and the ThingHandlers.
 *
 * @author Iwan Bron - Initial contribution
 *
 */
@NonNullByDefault
@WebSocket(maxIdleTime = 600_000)
public class EufyContainerSocketClient {
    private static final int PREFERRED_SCHEMA_VERSION = 8;
    private static final int KEEPALIVE_INTERVAL_MINUTES = 8;
    // private static final int CLOSE_CODE_ERROR_RECEIVED = 30001;

    private final Logger logger = LoggerFactory.getLogger(EufyContainerSocketClient.class);

    private ContainerHandler handler;
    private int currentSchemaVersion = 0;
    private AtomicInteger messageNumber = new AtomicInteger();
    private Map<String, Consumer<ResultMessage>> callbacks = new HashMap<String, Consumer<ResultMessage>>();

    private @Nullable Session session;

    private ScheduledExecutorService scheduler;

    private @Nullable ScheduledFuture<?> pollFuture;

    public EufyContainerSocketClient(ContainerHandler handler, ScheduledExecutorService scheduler) {
        this.handler = handler;
        this.scheduler = scheduler;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        logger.debug("Session connected for {}", this);
        this.session = session;
        currentSchemaVersion = 0;
        messageNumber.set(0);
        startKeepalive();
        handler.sessionConnected();
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String json) {
        logger.debug("Received message: {}", json);
        Message message = MessageHelper.parse(json);
        if (message != null) {
            if (message instanceof VersionMessage) {
                handleVersionMessage((VersionMessage) message);
            } else if (message instanceof ResultMessage) {
                handleCommandResponse((ResultMessage) message);
            } else {
                handler.receivedMessage(message);
            }
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int code, String reason) {
        logger.debug("Session closed: {}: {}", code, reason);
        this.session = null;
        stopKeepalive();
        handler.sessionClosed();
    }

    private void startKeepalive() {
        pollFuture = scheduler.scheduleWithFixedDelay(this::keepalive, KEEPALIVE_INTERVAL_MINUTES,
                KEEPALIVE_INTERVAL_MINUTES, TimeUnit.MINUTES);
        logger.debug("Started scheduler");
    }

    private void stopKeepalive() {
        ScheduledFuture<?> localFuture = pollFuture;
        if (localFuture != null) {
            localFuture.cancel(true);
            logger.debug("Cancelled scheduler");
            pollFuture = null;
        }
    }

    @OnWebSocketError
    public void onError(Session session, Throwable ex) {
        logger.debug("Error", ex);
        session.close();
    }

    private void handleVersionMessage(VersionMessage version) {
        handler.getThing().setProperty(CONTAINER_PROPERTY_DRIVERVERSION, version.getDriverVersion());
        handler.getThing().setProperty(CONTAINER_PROPERTY_SERVERVERSION, version.getServerVersion());
        handler.getThing().setProperty(CONTAINER_PROPERTY_MAXSCHEMAVERSION,
                Integer.toString(version.getMaxSchemaVersion()));
        if (version.getMaxSchemaVersion() > currentSchemaVersion
                && version.getMaxSchemaVersion() >= PREFERRED_SCHEMA_VERSION) {
            int schemaVersion = Math.min(version.getMaxSchemaVersion(), PREFERRED_SCHEMA_VERSION);
            logger.debug("Preferred schema version is {}, max available is {}, requesting {}", PREFERRED_SCHEMA_VERSION,
                    version.getMaxSchemaVersion(), schemaVersion);
            sendSchemaRequest(schemaVersion);
        }
    }

    private void handleCommandResponse(ResultMessage message) {
        Consumer<ResultMessage> consumer = callbacks.remove(message.getMessageId());
        if (consumer != null) {
            if (message.isSuccess()) {
                consumer.accept(message);
            } else {
                logger.debug("Received success=false for command with id {}", message.getMessageId());
            }
        } else {
            logger.debug("Received response {} but no callback registered", message);
        }
    }

    private void keepalive() {
        send(new KeepaliveRequest(getUniqueMessageId()), this::handleStatus);
    }

    private void sendSchemaRequest(int version) {
        String messageId = getUniqueMessageId();
        SchemaRequest c = new SchemaRequest(messageId, version);
        callbacks.put(messageId, result -> this.sendStartListening());
        write(MessageHelper.toString(c));
    }

    private String getUniqueMessageId() {
        return Integer.toString(messageNumber.getAndIncrement());
    }

    void write(String json) {
        try {
            Session localSession = session;
            if (localSession != null && localSession.isOpen()) {
                logger.debug("Sending {}", json);
                localSession.getRemote().sendString(json);
            }
        } catch (IOException e) {
            logger.debug("Error while sending data to container", e);
        }
    }

    public void sendCommand(EufyObject object, String propertyName, Object value, Consumer<ResultMessage> callback) {
        OutgoingMessage message;
        String command = getCommand(object, propertyName, value);
        if (command != null) {
            message = new Command(object.getSerialNumber(), getUniqueMessageId(), command);
        } else {
            message = new SetPropertyRequest(object.getSerialNumber(), getUniqueMessageId(), propertyName, value);
        }
        send(message, callback);
    }

    public void sendRequest(EufyObject object, String propertyName, Consumer<ResultMessage> callback) {
        String command = getCommand(object, propertyName, null);
        if (command != null) {
            send(new Command(object.getSerialNumber(), getUniqueMessageId(), command), callback);
        }
    }

    private @Nullable String getCommand(EufyObject object, String propertyName, @Nullable Object value) {
        try {
            Method method = object.getClass().getMethod("is" + capitalize(propertyName));
            EufyChannel channel = method.getAnnotation(EufyChannel.class);
            if (channel != null) {
                String command = channel.get();
                if (Boolean.TRUE.equals(value)) {
                    command = channel.on();
                } else if (Boolean.FALSE.equals(value)) {
                    command = channel.off();
                }
                return isEmpty(command) ? null : command;
            }
        } catch (NoSuchMethodException | SecurityException e) {
            logger.trace("{}: Unable to get getter method for property {}", object.getName(), propertyName);
        }
        return null;
    }

    private void send(OutgoingMessage command, Consumer<ResultMessage> callback) {
        callbacks.put(command.getMessageId(), callback);
        write(MessageHelper.toString(command));
    }

    private void sendStartListening() {
        OutgoingMessage command = new StartListeningRequest(getUniqueMessageId());
        send(command, this::handleStatus);
    }

    private void handleStatus(ResultMessage resultMessage) {
        if (resultMessage instanceof StatusMessage) {
            handler.handleStatusMessage((StatusMessage) resultMessage);
        }
    }

    public void dispose() {
        stopKeepalive();
        Session localSession = session;
        if (localSession != null && localSession.isOpen()) {
            try {
                localSession.disconnect();
            } catch (IOException e) {
                logger.trace("Error when disposing websocket session: {}", e.getMessage());
            }
        }
    }

    // TODO

    private static String capitalize(String str) {
        int strLen = str.length();
        if (strLen == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        final int newCodePoints[] = new int[strLen]; // cannot be longer than the char array
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen;) {
            final int codepoint = str.codePointAt(inOffset);
            newCodePoints[outOffset++] = codepoint; // copy the remaining ones
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }

    private static boolean isEmpty(String str) {
        return str.replace(" ", "").equals("");
    }
}
