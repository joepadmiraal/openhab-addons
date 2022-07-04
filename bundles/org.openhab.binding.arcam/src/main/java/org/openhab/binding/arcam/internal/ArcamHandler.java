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
package org.openhab.binding.arcam.internal;

import static org.openhab.binding.arcam.internal.ArcamBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.arcam.internal.config.ArcamConfiguration;
import org.openhab.binding.arcam.internal.devices.ArcamDevice;
import org.openhab.binding.arcam.internal.devices.ArcamDeviceUtil;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.ThingUID;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ArcamHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Joep Admiraal - Initial contribution
 */
@NonNullByDefault
public class ArcamHandler extends BaseThingHandler implements ArcamStateChangedListener, ArcamConnectionListener {

    private final Logger logger = LoggerFactory.getLogger(ArcamHandler.class);

    private ArcamConnection connection;
    private ArcamState state;
    private ArcamDevice device;

    public ArcamHandler(Thing thing) {
        super(thing);

        logger.debug("Creating a ArcamHandler for thing '{}'", getThing().getUID());

        state = new ArcamState(this);
        ThingUID thingUID = getThing().getUID();
        device = ArcamDeviceUtil.getDeviceFromThingUID(thingUID);
        connection = new ArcamConnection(state, scheduler, this, device, thingUID.toString());
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            // String channelId = channelUID.getIdWithoutGroup();
            String channelId = channelUID.getId();
            ArcamCommandCode commandCode = ArcamCommandCode.getFromChannel(channelId);

            logger.info("ArcamHandler handled generic refreshType for: {}, commandCode: {}", channelId, commandCode);
            if (commandCode != null) {
                connection.requestState(commandCode);
            }

            return;
        }

        if (equalsWithoutGroup(CHANNEL_DAC_FILTER, channelUID)) {
            if (command instanceof StringType) {
                StringType c = (StringType) command;
                connection.setDacFilter(c.toFullString());
            }
        }

        if (equalsWithoutGroup(CHANNEL_DISPLAY_BRIGHTNESS, channelUID)) {
            logger.info("handleCommand: {}", command.toFullString());

            if (command instanceof StringType) {
                StringType c = (StringType) command;
                connection.setDisplayBrightness(c.toFullString());
            }
        }

        if (equalsWithoutGroup(CHANNEL_MASTER_BALANCE, channelUID)) {
            logger.info("handleCommand: {}", command.toFullString());

            if (command instanceof DecimalType) {
                DecimalType p = (DecimalType) command;
                connection.setBalance(p.intValue(), ArcamZone.MASTER);
            }
        }

        if (equalsWithoutGroup(CHANNEL_MASTER_INPUT, channelUID)) {
            if (command instanceof StringType) {
                StringType c = (StringType) command;
                connection.setInput(c.toFullString(), ArcamZone.MASTER);
            }
        }

        if (equalsWithoutGroup(CHANNEL_MASTER_POWER, channelUID)) {
            logger.info("handleCommand: {}", command.toFullString());

            if (command == OnOffType.ON) {
                connection.setPower(true, ArcamZone.MASTER);
            }
            if (command == OnOffType.OFF) {
                connection.setPower(false, ArcamZone.MASTER);
            }
        }

        if (equalsWithoutGroup(CHANNEL_MASTER_ROOM_EQUALISATION, channelUID)) {
            logger.info("handleCommand: {}", command.toFullString());

            if (command instanceof StringType) {
                StringType c = (StringType) command;
                connection.setRoomEqualisation(c.toFullString(), ArcamZone.MASTER);
            }
        }

        if (equalsWithoutGroup(CHANNEL_MASTER_VOLUME, channelUID)) {
            logger.info("handleCommand: {}", command.toFullString());

            if (command instanceof PercentType) {
                logger.info("got percenttype");
                PercentType p = (PercentType) command;
                connection.setVolume(p.intValue(), ArcamZone.MASTER);
            }
        }

        if (equalsWithoutGroup(CHANNEL_REBOOT, channelUID)) {
            logger.info("handleCommand: {}", command.toFullString());

            if (command == OnOffType.ON) {
                connection.reboot();
            }
        }

        if (equalsWithoutGroup(CHANNEL_ZONE2_INPUT, channelUID)) {
            if (command instanceof StringType) {
                StringType c = (StringType) command;
                connection.setInput(c.toFullString(), ArcamZone.ZONE2);
            }
        }

        if (equalsWithoutGroup(CHANNEL_ZONE2_POWER, channelUID)) {
            if (command == OnOffType.ON) {
                connection.setPower(true, ArcamZone.ZONE2);
            }
            if (command == OnOffType.OFF) {
                connection.setPower(false, ArcamZone.ZONE2);
            }
        }

        if (equalsWithoutGroup(CHANNEL_ZONE2_VOLUME, channelUID)) {
            if (command instanceof PercentType) {
                PercentType p = (PercentType) command;
                connection.setVolume(p.intValue(), ArcamZone.ZONE2);
            }
        }

        // Note: if communication with thing fails for some reason,
        // indicate that by setting the status with detail information:
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // "Could not control device at IP address x.x.x.x");
    }

    @Override
    public void initialize() {
        ArcamConfiguration config = getConfigAs(ArcamConfiguration.class);

        // The framework requires you to return from this method quickly, i.e. any network access must be done in
        // the background initialization below.
        // Also, before leaving this method a thing status from one of ONLINE, OFFLINE or UNKNOWN must be set. This
        // might already be the real thing status in case you can decide it directly.
        // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
        // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
        // background.

        // set the thing status to UNKNOWN temporarily and let the background task decide for the real status.
        // the framework is then able to reuse the resources from the thing handler initialization.
        // we set this upfront to reliably check status updates in unit tests.
        updateStatus(ThingStatus.UNKNOWN);

        scheduler.execute(() -> {
            String hostname = config.hostname;

            try {
                if (hostname == null) {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "No hostname specified");
                    return;
                }

                connection.connect(hostname);
            } catch (Exception e) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
                return;
            }

            updateStatus(ThingStatus.ONLINE);
            logger.info("handler initialized. ip: {}", config.hostname);
        });

        // These logging types should be primarily used by bindings
        // logger.trace("Example trace message");
        // logger.debug("Example debug message");
        // logger.warn("Example warn message");
        //
        // Logging to INFO should be avoided normally.
        // See https://www.openhab.org/docs/developer/guidelines.html#f-logging

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    @Override
    public void dispose() {
        connection.dispose();

        super.dispose();
    }

    @Override
    public void stateChanged(String channelID, State state) {
        updateState(channelID, state);
    }

    @Override
    public void onError() {
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
    }

    private boolean equalsWithoutGroup(String channelStr, ChannelUID channelUID) {
        String[] parts = channelStr.split(ChannelUID.CHANNEL_GROUP_SEPARATOR);
        if (parts.length - 1 < 0) {
            logger.warn("Could not parse channelStr: {}, unable to change value", channelStr);
            return false;
        }
        String id = parts[parts.length - 1];
        return id.equals(channelUID.getIdWithoutGroup());
    }
}
