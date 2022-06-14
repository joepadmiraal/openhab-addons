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
 * The {@link ArcamCommand} class is a POJO to store the bytes that are used to send a command.
 * Used to create dynamic lookup tables so we can share logic between multiple devices.
 *
 * @author Joep Admiraal - Initial contribution
 */
public class ArcamCommand {
    public ArcamCommandCode code;
    public byte[] data;

    public ArcamCommand(ArcamCommandCode code, byte[] data) {
        this.code = code;
        this.data = data;
    }
}
