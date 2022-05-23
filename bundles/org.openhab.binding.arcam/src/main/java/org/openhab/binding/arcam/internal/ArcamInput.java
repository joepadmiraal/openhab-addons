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

/**
 * The {@link ArcamInput} is contains the inputs of the amplifier. The value is the value as specified in the
 * thing-types.xml file.
 *
 * @author Joep Admiraal - Initial contribution
 */
public enum ArcamInput {
    PHONO("PHONO"),
    AUX("AUX"),
    PVR("PVR"),
    AV("AV"),
    STB("STB"),
    CD("CD"),
    BD("BD"),
    SAT("SAT"),
    GAME("GAME"),
    NETUSB("NET/USB");

    public final String value;

    private ArcamInput(String value) {
        this.value = value;
    }
}
