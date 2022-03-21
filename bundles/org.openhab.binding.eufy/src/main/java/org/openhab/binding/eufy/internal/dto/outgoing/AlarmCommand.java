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
package org.openhab.binding.eufy.internal.dto.outgoing;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.eufy.internal.dto.objects.Station;

/**
 *
 * 
 * @author Iwan Bron - Initial contribution
 */
@NonNullByDefault
public class AlarmCommand extends OutgoingMessage {
    private String serialNumber;

    private AlarmCommand(String messageId, String command, String serialNumber) {
        super(messageId, command);
        this.serialNumber = serialNumber;
    }

    public static AlarmCommand on(Station station, String messageId) {
        return new AlarmCommand(messageId, OutgoingMessage.TRIGGER_ALARM, station.getSerialNumber());
    }

    public static AlarmCommand off(Station station, String messageId) {
        return new AlarmCommand(messageId, OutgoingMessage.RESET_ALARM, station.getSerialNumber());
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
