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
 * The {@link ArcamCommand} class defines the commands that the Arcam amplifiers understand.
 * They are specified as byte arrays.
 *
 * @author Joep Admiraal - Initial contribution
 */
public enum ArcamCommand {
    GET_POWER(new byte[] { 0x21, 0x01, 0x00, 0x01, (byte) 0xF0, 0x0D }),
    SET_POWER(new byte[] { 0x21, 0x01, 0x00, 0x01, (byte) 0xF0, 0x0D }),
    GET_VOLUME(new byte[] { 0x21, 0x01, 0x0D, 0x01, (byte) 0xF0, 0x0D }),
    SET_VOLUME(new byte[] { 0x21, 0x01, 0x0D, 0x01, 0x2D, 0x0D }),
    GET_INPUT(new byte[] { 0x21, 0x01, 0x1D, 0x01, (byte) 0xF0, 0x0D }),
    SET_INPUT(new byte[] { 0x21, 0x01, 0x1D, 0x01, (byte) 0x01, 0x0D }),
    GET_SYSTEM_STATUS(new byte[] { 0x21, 0x01, 0x5D, 0x01, (byte) 0xF0, 0x0D });

    public final byte[] data;

    private ArcamCommand(byte[] data) {
        this.data = data;
    }
}
