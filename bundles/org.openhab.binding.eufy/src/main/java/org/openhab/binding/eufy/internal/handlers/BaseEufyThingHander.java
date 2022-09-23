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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.eufy.internal.annotations.EufyChannel;
import org.openhab.binding.eufy.internal.dto.AsyncResultMessage;
import org.openhab.binding.eufy.internal.dto.Event;
import org.openhab.binding.eufy.internal.dto.MotionDetectedEvent;
import org.openhab.binding.eufy.internal.dto.PersonDetectedEvent;
import org.openhab.binding.eufy.internal.dto.PropertyChangedEvent;
import org.openhab.binding.eufy.internal.dto.ResultMessage;
import org.openhab.binding.eufy.internal.dto.RingsEvent;
import org.openhab.binding.eufy.internal.dto.StateChangedEvent;
import org.openhab.binding.eufy.internal.dto.objects.EufyObject;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.RawType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Channel;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusInfo;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.core.util.ReflectionUtils;

/**
 *
 * @author Iwan Bron - initial contribution
 *
 */
@NonNullByDefault
public abstract class BaseEufyThingHander extends BaseThingHandler {
    private final Logger logger = LoggerFactory.getLogger(BaseEufyThingHander.class);

    protected BaseEufyThingHander(Thing thing) {
        super(thing);
    }

    protected @Nullable ContainerHandler getContainer() {
        Bridge bridge = getBridge();
        if (bridge != null && bridge.getHandler() instanceof ContainerHandler) {
            return (ContainerHandler) bridge.getHandler();
        }
        return null;
    }

    private boolean isChanged(EufyObject object, String propertyName, Object value) {
        try {
            String prefix = value instanceof Boolean ? "is" : "get";
            Method getter = object.getClass().getMethod(prefix + capitalize(propertyName));
            Object oldValue = getter.invoke(object);
            return (oldValue == null || !Objects.equals(value, oldValue));
        } catch (Exception e) {
            logger.debug("{}: Problem finding methods for {}: {}", getIdentifier(), propertyName, e.getMessage());
            return false;
        }
    }

    protected String getIdentifier() {
        String label = getThing().getLabel();
        return label != null ? label : "unknown";
    }

    protected void updateChannel(String propertyName, @Nullable Object value) {
        Channel channel = getChannelForProperty(propertyName);
        if (channel != null) {

            State newState = value == null ? UnDefType.NULL : StringType.valueOf(value.toString());
            if (value instanceof Boolean) {
                newState = OnOffType.from((Boolean) value);
            } else if (value instanceof Integer) {
                if ("Dimmer".equals(channel.getAcceptedItemType())) {
                    newState = new PercentType((int) value);
                } else {
                    newState = new DecimalType(((Integer) value).longValue());
                }
            } else if (value instanceof byte[]) {
                newState = new RawType((byte[]) value, "image/jpg");
            }
            updateState(channel.getUID(), newState);
            logger.debug("{}: Sent update to channel {}: {}", getIdentifier(), channel.getUID(), newState);
        } else {
            logger.debug("{}: Unable to send update for property ({}:){} to {}: channel not found", getIdentifier(),
                    propertyName, value == null ? null : value.getClass().getSimpleName(), value);
        }
    }

    private @Nullable Channel getChannelForProperty(String propertyName) {
        return getThing().getChannel(propertyName);
    }

    protected String getPropertyName(ChannelUID channelUID) {
        String[] split = channelUID.getAsString().split(":");
        return split[split.length - 1];
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        EufyObject object = getEufyObject();
        if (object == null) {
            logger.debug("Cannot handle command. EufyObject not found");
            return;
        }
        String propertyName = getPropertyName(channelUID);
        if (command instanceof RefreshType) {
            // no need to refresh. we are up to date as long as we are connected, and a reconnect will do a refresh
            updateProperties(object);
        } else if (command instanceof OnOffType) {
            boolean isOn = command.equals(OnOffType.ON);
            if (isChanged(object, propertyName, isOn)) {
                sendRequest(object, propertyName, isOn, r -> {
                    updateState(channelUID, (State) command);
                    updateProperty(propertyName, isOn);
                });
            }
        } else if (command instanceof DecimalType) {
            int intValue = ((DecimalType) command).intValue();
            if (isChanged(object, propertyName, intValue)) {
                sendRequest(object, propertyName, intValue, r -> {
                    updateState(channelUID, (State) command);
                    updateProperty(propertyName, intValue);
                });
            }
        } else if (command instanceof StringType) {
            String newValue = ((StringType) command).format("%s");
            if (isChanged(object, propertyName, newValue)) {
                sendRequest(object, propertyName, newValue, r -> {
                    updateState(channelUID, (State) command);
                    updateProperty(propertyName, newValue);
                });
            }
        } else {
            logger.debug("Unable to handle command {} for channel {}: unknown type", command, channelUID);
        }
    }

    private void sendRequest(EufyObject object, String propertyName, Object value, Consumer<ResultMessage> callback) {
        ContainerHandler container = getContainer();
        if (container != null) {
            container.sendRequest(object, propertyName, value, callback);
        }
    }

    private void sendRequest(EufyObject object, String propertyName, Consumer<ResultMessage> callback) {
        ContainerHandler container = getContainer();
        if (container != null) {
            container.sendRequest(object, propertyName, callback);
        }
    }

    protected void updateProperties(EufyObject newObject) {
        List<Field> declaredFields = ReflectionUtils.getDeclaredFields(newObject.getClass());
        for (Field field : declaredFields) {
            EufyChannel channel = field.getAnnotation(EufyChannel.class);
            if (channel != null) {
                String propertyName = field.getName();
                if (channel.inStatus()) {
                    Object newValue = null;
                    Object oldValue = null;
                    try {
                        String getterName = (field.getType().equals(boolean.class) ? "is" : "get")
                                + capitalize(propertyName);
                        Method getter = newObject.getClass().getMethod(getterName);
                        newValue = getter.invoke(newObject);
                        if (newValue != null) {
                            propertyChanged(propertyName, newValue);
                            updateChannel(propertyName, newValue);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                            | SecurityException | InvocationTargetException e) {
                        logger.debug("Could not update value from {} to {} for property {}: {}", oldValue, newValue,
                                propertyName, e.getMessage());
                    }
                } else {
                    getPropertyValueAsync(propertyName);
                }
            }
        }
    }

    private void getPropertyValueAsync(String propertyName) {
        EufyObject object = getEufyObject();

        if (object != null) {
            sendRequest(object, propertyName, p -> {
                if (p instanceof AsyncResultMessage) {
                    boolean result = ((AsyncResultMessage) p).getResult();
                    updateProperty(propertyName, result);
                    updateChannel(propertyName, result);
                } else {
                    logger.debug("Got unexpected result class, expected {} but got {}",
                            AsyncResultMessage.class.getSimpleName(), p.getClass().getSimpleName());
                }
            });
        }
    }

    protected ThingStatus getBridgeStatus() {
        Bridge localBridge = getBridge();
        return localBridge == null ? ThingStatus.UNKNOWN : localBridge.getStatus();
    }

    @Override
    public void bridgeStatusChanged(ThingStatusInfo bridgeStatusInfo) {
        super.bridgeStatusChanged(bridgeStatusInfo);
        if (bridgeStatusInfo.getStatus() == ThingStatus.ONLINE) {
            initialize();
        }
    }

    public void eventReceived(Event event) {
        if (event instanceof PropertyChangedEvent) {
            PropertyChangedEvent propEvent = (PropertyChangedEvent) event;
            propertyChanged(propEvent.getName(), propEvent.getValue());
        } else if (event instanceof StateChangedEvent) {
            StateChangedEvent stateEvent = (StateChangedEvent) event;
            stateChanged(stateEvent);
        } else {
            logger.debug("Unhandled event: {}", event.getEvent());
        }
    }

    private void stateChanged(StateChangedEvent stateEvent) {
        if (stateEvent instanceof PersonDetectedEvent) {
            updateChannel(CHANNEL_PERSON_DETECTED, ((PersonDetectedEvent) stateEvent).isState());
            updateChannel(CHANNEL_PERSON_NAME, ((PersonDetectedEvent) stateEvent).getPerson());
        } else if (stateEvent instanceof MotionDetectedEvent) {
            updateChannel(CHANNEL_MOTION_DETECTED, ((MotionDetectedEvent) stateEvent).isState());
        } else if (stateEvent instanceof RingsEvent) {
            updateChannel(CHANNEL_RINGING, ((RingsEvent) stateEvent).isState());
        } else {
            logger.debug("Unhandled state change: {}", stateEvent.getEvent());
        }
    }

    private void propertyChanged(String propertyName, Object value) {
        EufyObject object = getEufyObject();
        if (object == null) {
            return;
        }
        if (isChanged(object, propertyName, value)) {
            updateProperty(propertyName, value);
            updateChannel(propertyName, value);
        }
    }

    private void updateProperty(String propertyName, Object value) {
        EufyObject object = getEufyObject();
        if (object == null) {
            logger.debug("Cannot set property, EufyObject not found");
            return;
        }
        try {
            Class<?> clazz = value.getClass();
            if (clazz.equals(Boolean.class)) {
                clazz = boolean.class;
            } else if (clazz.equals(Integer.class)) {
                clazz = int.class;
            }
            Method setter = object.getClass().getMethod("set" + capitalize(propertyName), clazz);
            setter.invoke(object, value);
            logger.debug("{}: Set value for {} to {}", getIdentifier(), propertyName, value);
        } catch (NoSuchMethodException nsme) {
            logger.trace("{}: Could not find setter method for {}", getIdentifier(), propertyName);
        } catch (Exception e) {
            logger.debug("{}: Problem setting value to {} for {}: {}", getIdentifier(), value, propertyName,
                    e.getMessage());
        }
    }

    protected abstract @Nullable EufyObject getEufyObject();

    abstract void update();

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
}
