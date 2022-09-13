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
package org.openhab.binding.eufy.internal.handlers;

import static org.openhab.binding.eufy.internal.EufyBindingConstants.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.binding.eufy.internal.EufyBindingConstants;
import org.openhab.binding.eufy.internal.EufyContainerSocketClient;
import org.openhab.binding.eufy.internal.discovery.EufyDiscoveryService;
import org.openhab.binding.eufy.internal.dto.Event;
import org.openhab.binding.eufy.internal.dto.EventMessage;
import org.openhab.binding.eufy.internal.dto.Message;
import org.openhab.binding.eufy.internal.dto.ResultMessage;
import org.openhab.binding.eufy.internal.dto.StatusMessage;
import org.openhab.binding.eufy.internal.dto.objects.Device;
import org.openhab.binding.eufy.internal.dto.objects.EufyObject;
import org.openhab.binding.eufy.internal.dto.objects.State;
import org.openhab.binding.eufy.internal.dto.objects.Station;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ContainerHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Iwan Bron - Initial contribution
 */
@NonNullByDefault
public class ContainerHandler extends BaseBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(ContainerHandler.class);

    private final int MAX_RETRY_BACKOFF_SECONDS = 180;

    private final WebSocketClient webSocket = new WebSocketClient();
    private final EufyContainerSocketClient client = new EufyContainerSocketClient(this, scheduler);

    private @Nullable State lastState;

    private final EufyDiscoveryService discoveryService;
    private int connectionRetry = 0;

    public ContainerHandler(Bridge bridge, EufyDiscoveryService discoveryService) {
        super(bridge);
        this.discoveryService = discoveryService;
        this.discoveryService.registerBridge(this);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
    }

    @Override
    public void initialize() {
        updateStatus(ThingStatus.UNKNOWN);

        scheduler.execute(this::init);
    }

    private void init() {
        String hostname = (String) getThing().getConfiguration().get(EufyBindingConstants.CONFIGURATION_HOST);
        int port = ((BigDecimal) getThing().getConfiguration().get(EufyBindingConstants.CONFIGURATION_PORT)).intValue();
        try {
            webSocket.start();
            ClientUpgradeRequest r = new ClientUpgradeRequest();
            webSocket.connect(client, URI.create("ws://" + hostname + ":" + port), r);
        } catch (Exception e) {
            logger.info("exception:", e);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Docker container not reachable");
        }
    }

    public void receivedMessage(Message message) {
        if (message instanceof EventMessage) {
            handleEvent((EventMessage) message);
        }
    }

    private void handleEvent(EventMessage message) {
        Event event = message.getEvent();
        if (event == null) {
            logger.debug("Did not find event in event message. Cannot handle");
            return;
        }
        String serialNumber = event.getSerialNumber();
        if (serialNumber == null) {
            if ("driver".equals(event.getSource())) {
                eventReceived(event);
                return;
            } else {
                logger.debug("Did not find serial number in event message. Cannot handle");
                return;
            }
        }
        String serialPropertyName = "device".equals(event.getSource()) ? DEVICE_PROPERTY_SERIALNUMBER
                : STATION_PROPERTY_SERIALNUMBER;
        for (Thing thing : getThing().getThings()) {
            if (serialNumber.equals(thing.getProperties().get(serialPropertyName))) {
                ThingHandler handler = thing.getHandler();
                if (handler instanceof BaseEufyThingHander) {
                    ((BaseEufyThingHander) handler).eventReceived(event);
                }
            }
        }
    }

    private void eventReceived(Event event) {
        logger.debug("Received Event: not implemented ({})", event);
        // TODO
    }

    public void handleStatusMessage(StatusMessage status) {
        lastState = status.getResult().getState();
        updateStatus(ThingStatus.ONLINE);
        discoveryService.start();
        notifyThings();
    }

    private void notifyThings() {
        for (Thing thing : getThing().getThings()) {
            ThingHandler handler = thing.getHandler();
            if (handler instanceof BaseEufyThingHander) {
                ((BaseEufyThingHander) handler).update();
            }
        }
    }

    public void sessionClosed() {
        updateStatus(ThingStatus.OFFLINE);
        tryReconnect();
    }

    private void tryReconnect() {
        int retry = connectionRetry++;
        // wait an extra 10 seconds each retry
        int backoff = Math.min(retry * 10, MAX_RETRY_BACKOFF_SECONDS);
        scheduler.schedule(this::init, backoff, TimeUnit.SECONDS);
    }

    public void sessionConnected() {
        connectionRetry = 0;
    }

    public List<Station> getStations() {
        State state = lastState;
        if (state != null && state.getStations() != null) {
            return state.getStations();
        }
        return Collections.emptyList();
    }

    public List<Device> getDevices() {
        State state = lastState;
        if (state != null && state.getDevices() != null) {
            return state.getDevices();
        }
        return Collections.emptyList();
    }

    public @Nullable Device getDevice(String serialNumber) {
        return getDevices().stream().filter(device -> serialNumber.equals(device.getSerialNumber())).findFirst()
                .orElse(null);
    }

    public @Nullable Station getStation(String serialNumber) {
        return getStations().stream().filter(station -> serialNumber.equals(station.getSerialNumber())).findFirst()
                .orElse(null);
    }

    public void sendRequest(EufyObject object, String propertyName, Object value, Consumer<ResultMessage> callback) {
        client.sendCommand(object, propertyName, value, callback);
    }

    public void sendRequest(EufyObject object, String propertyName, Consumer<ResultMessage> callback) {
        client.sendRequest(object, propertyName, callback);
    }

    @Override
    public void dispose() {
        super.dispose();
        client.dispose();
        webSocket.destroy();
    }
}
