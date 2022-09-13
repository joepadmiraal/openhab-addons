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

/**
 *
 * 
 * @author Iwan Bron - Initial contribution
 */
@NonNullByDefault
public class SetPropertyRequest extends OutgoingMessage {
    private String serialNumber;
    private Object value;
    private String name;

    public SetPropertyRequest(String serialNumber, String messageId, String propertyName, Object newValue) {
        super(messageId, OutgoingMessage.COMMAND_SET_PROPERTY);
        this.serialNumber = serialNumber;
        this.value = newValue;
        this.name = propertyName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
