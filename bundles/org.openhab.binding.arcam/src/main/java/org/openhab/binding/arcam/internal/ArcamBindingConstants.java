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

import java.util.HashSet;
import java.util.Set;

import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link ArcamBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Joep Admiraal - Initial contribution
 */
public class ArcamBindingConstants {

    public static final String BINDING_ID = "arcam";

    // List of all Thing Type UIDs
    public static final ThingTypeUID SA20_THING_TYPE_UID = new ThingTypeUID(BINDING_ID, "SA20");
    public static final ThingTypeUID SA30_THING_TYPE_UID = new ThingTypeUID(BINDING_ID, "SA30");
    public static final ThingTypeUID AVR30_THING_TYPE_UID = new ThingTypeUID(BINDING_ID, "AVR30");
    public static final Set<ThingTypeUID> SUPPORTED_KNOWN_THING_TYPES_UIDS = Set.of(SA20_THING_TYPE_UID,
            SA30_THING_TYPE_UID, AVR30_THING_TYPE_UID);
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = new HashSet<>(SUPPORTED_KNOWN_THING_TYPES_UIDS);

    // List of all Channel ids
    // Generic
    public static final String CHANNEL_DISPLAY_BRIGHTNESS = "masterZone#displaybrightness";

    // Master zone
    public static final String CHANNEL_MASTER_VOLUME = "masterZone#volume";
    public static final String CHANNEL_MASTER_POWER = "masterZone#power";
    public static final String CHANNEL_MASTER_INPUT = "masterZone#input";

    // Zone 2
    public static final String CHANNEL_ZONE2_VOLUME = "zone2#volume";
    public static final String CHANNEL_ZONE2_POWER = "zone2#power";
    public static final String CHANNEL_ZONE2_INPUT = "zone2#input";

}
