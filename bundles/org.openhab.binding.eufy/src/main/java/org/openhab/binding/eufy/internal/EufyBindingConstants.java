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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link EufyBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Iwan Bron - Initial contribution
 */
@NonNullByDefault
public class EufyBindingConstants {

    private static final String BINDING_ID = "eufy";

    public static final String CONFIGURATION_HOST = "hostname";
    public static final String CONFIGURATION_PORT = "port";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_CONTAINER = new ThingTypeUID(BINDING_ID, "container");
    public static final ThingTypeUID THING_TYPE_STATION = new ThingTypeUID(BINDING_ID, "station");
    public static final ThingTypeUID THING_TYPE_CAMERA = new ThingTypeUID(BINDING_ID, "camera");
    public static final ThingTypeUID THING_TYPE_DOORBELL = new ThingTypeUID(BINDING_ID, "doorbell");

    // container properties
    public static final String CONTAINER_PROPERTY_DRIVERVERSION = "driverVersion";
    public static final String CONTAINER_PROPERTY_SERVERVERSION = "serverVersion";
    public static final String CONTAINER_PROPERTY_MAXSCHEMAVERSION = "maxSchemaVersion";
    public static final String CONTAINER_PROPERTY_COMMUNICATIONVERSION = "communicationVersion";

    // station properties
    public static final String STATION_PROPERTY_MODEL = "model";
    public static final String STATION_PROPERTY_NAME = "name";
    public static final String STATION_PROPERTY_HARDWAREVERSION = "hardwareVersion";
    public static final String STATION_PROPERTY_SOFTWAREVERSION = "softwareVersion";
    public static final String STATION_PROPERTY_SERIALNUMBER = "serialNumber";
    public static final String STATION_PROPERTY_IPADDRESS = "ipAddress";
    public static final String STATION_PROPERTY_MACADDRESS = "macAddress";

    public static final String STATION_REPRESENTATION_PROPERTY = STATION_PROPERTY_NAME;
    public static final String STATION_LABEL_FORMAT = "Station %s";

    // device properties
    public static final String DEVICE_PROPERTY_MODEL = "model";
    public static final String DEVICE_PROPERTY_NAME = "name";
    public static final String DEVICE_PROPERTY_HARDWAREVERSION = "harwareVersion";
    public static final String DEVICE_PROPERTY_SOFTWAREVERSION = "sofwareVersion";
    public static final String DEVICE_PROPERTY_SERIALNUMBER = "serialNumber";

    public static final String DEVICE_REPRESENTATION_PROPERTY = DEVICE_PROPERTY_NAME;
    public static final String DEVICE_LABEL_FORMAT = "Device %s";
    // List of Channel ids that need special treatment. Most channels are created after reading the json resonses.
    public static final String CHANNEL_ANTITHEFTPROTECTION = "antitheftProtection";
    public static final String CHANNEL_PERSON_DETECTED = "personDetected";
    public static final String CHANNEL_PERSON_NAME = "personName";
    public static final String CHANNEL_RINGING = "ringing";
    public static final String CHANNEL_MOTION_DETECTED = "motionDetected";
}
