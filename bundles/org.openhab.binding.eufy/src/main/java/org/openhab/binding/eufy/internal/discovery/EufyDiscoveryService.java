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
package org.openhab.binding.eufy.internal.discovery;

import static org.openhab.binding.eufy.internal.EufyBindingConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.eufy.internal.InvalidConfigurationException;
import org.openhab.binding.eufy.internal.dto.objects.Device;
import org.openhab.binding.eufy.internal.dto.objects.Doorbell;
import org.openhab.binding.eufy.internal.dto.objects.Station;
import org.openhab.binding.eufy.internal.handlers.ContainerHandler;
import org.openhab.core.config.discovery.AbstractDiscoveryService;
import org.openhab.core.config.discovery.DiscoveryResult;
import org.openhab.core.config.discovery.DiscoveryResultBuilder;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * After connecting to a Eufy docker container, the discovery service handles adding the devices and stations found to
 * the OpenHAB Thing inbox.
 *
 * @author Iwan Bron - Initial contribution
 *
 */
@NonNullByDefault
public class EufyDiscoveryService extends AbstractDiscoveryService {
    private Logger logger = LoggerFactory.getLogger(EufyDiscoveryService.class);

    private @Nullable ContainerHandler container;

    public EufyDiscoveryService() {
        super(10);
    }

    @Override
    protected void startScan() {
        start();
    }

    private @Nullable Map<String, Object> buildProperties(Station station) {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(STATION_PROPERTY_MODEL, station.getModel());
        props.put(STATION_PROPERTY_NAME, station.getName());
        props.put(STATION_PROPERTY_HARDWAREVERSION, station.getHardwareVersion());
        props.put(STATION_PROPERTY_SOFTWAREVERSION, station.getSoftwareVersion());
        props.put(STATION_PROPERTY_SERIALNUMBER, station.getSerialNumber());
        props.put(STATION_PROPERTY_IPADDRESS, station.getLanIpAddress());
        props.put(STATION_PROPERTY_MACADDRESS, station.getMacAddress());
        props.put(STATION_PROPERTY_MACADDRESS, station.getMacAddress());
        return props;
    }

    private @Nullable Map<String, Object> buildProperties(Device device) {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(DEVICE_PROPERTY_MODEL, device.getModel());
        props.put(DEVICE_PROPERTY_NAME, device.getName());
        props.put(DEVICE_PROPERTY_HARDWAREVERSION, device.getHardwareVersion());
        props.put(DEVICE_PROPERTY_SOFTWAREVERSION, device.getSoftwareVersion());
        props.put(DEVICE_PROPERTY_SERIALNUMBER, device.getSerialNumber());
        return props;
    }

    private ThingUID createThingUUID(Station station) throws InvalidConfigurationException {
        ContainerHandler bridge = container;
        if (bridge != null) {
            return new ThingUID(THING_TYPE_STATION, station.getSerialNumber(), bridge.getThing().getUID().getId());
        }
        throw new InvalidConfigurationException("Container missing");
    }

    private ThingUID createThingUUID(Device device) throws InvalidConfigurationException {
        ContainerHandler bridge = container;
        if (bridge != null) {
            return new ThingUID(getThingTypeForDevice(device), device.getSerialNumber(),
                    bridge.getThing().getUID().getId());
        }
        throw new InvalidConfigurationException("Container missing");
    }

    private ThingTypeUID getThingTypeForDevice(Device device) {
        if (device instanceof Doorbell) {
            return THING_TYPE_DOORBELL;
        }
        return THING_TYPE_CAMERA;
    }

    public void registerBridge(ContainerHandler containerHandler) {
        this.container = containerHandler;
    }

    public void start() {
        ContainerHandler bridge = container;
        if (bridge != null) {
            for (Station station : bridge.getStations()) {
                try {
                    DiscoveryResult discovery = DiscoveryResultBuilder.create(createThingUUID(station))
                            .withBridge(bridge.getThing().getUID()).withProperties(buildProperties(station))
                            .withRepresentationProperty(STATION_REPRESENTATION_PROPERTY)
                            .withLabel(String.format(STATION_LABEL_FORMAT, station.getName()))
                            .withThingType(THING_TYPE_STATION).build();
                    thingDiscovered(discovery);
                    logger.debug("Discovered Station: {} ({})", station.getName(), station.getSerialNumber());
                } catch (InvalidConfigurationException e) {
                    logger.warn("Could not create discovery for station {}: {}", station.getName(), e.getMessage());
                }
            }
            for (Device device : bridge.getDevices()) {
                try {
                    DiscoveryResult discovery = DiscoveryResultBuilder.create(createThingUUID(device))
                            .withBridge(bridge.getThing().getUID()).withProperties(buildProperties(device))
                            .withRepresentationProperty(DEVICE_REPRESENTATION_PROPERTY)
                            .withLabel(String.format(DEVICE_LABEL_FORMAT, device.getName()))
                            .withThingType(getThingTypeForDevice(device)).build();
                    thingDiscovered(discovery);
                    logger.debug("Discovered {}: {} ({})", device.getClass().getSimpleName(), device.getName(),
                            device.getSerialNumber());
                } catch (InvalidConfigurationException e) {
                    logger.warn("Could not create discovery for device {}: {}", device.getName(), e.getMessage());
                }
            }
        }
    }
}
