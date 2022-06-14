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
 * The {@link ArcamCommandData} POJO is used to specify options for commands with the corresponding byte.
 * Used for both interpreting the response from the device as well as generating the command to send to the device.
 *
 * @author Joep Admiraal - Initial contribution
 */
public class ArcamCommandData {
    public String code;
    public String name;
    public byte dataByte;

    public ArcamCommandData(String code, String name, byte dataByte) {
        this.code = code;
        this.name = name;
        this.dataByte = dataByte;
    }

    public ArcamCommandData(String code, byte dataByte) {
        this.code = code;
        this.name = code;
        this.dataByte = dataByte;
    }
}
