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
package org.openhab.binding.eufy.internal.dto;

/**
 * The VersionMessage is sent after connecting to the container to tell the client which propocol version it supports.
 * The client should then request a protocol version for further communication.s
 *
 * @author Iwan Bron - Initial contribution
 *
 */
public class VersionMessage extends Message {
    private String driverVersion;
    private String serverVersion;
    private int minSchemaVersion;
    private int maxSchemaVersion;

    public String getDriverVersion() {
        return driverVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public int getMinSchemaVersion() {
        return minSchemaVersion;
    }

    public int getMaxSchemaVersion() {
        return maxSchemaVersion;
    }
}
