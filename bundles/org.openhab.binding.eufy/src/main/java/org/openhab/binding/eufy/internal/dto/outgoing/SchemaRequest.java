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
public class SchemaRequest extends OutgoingMessage {
    private int version;

    public SchemaRequest(String messageId, int version) {
        super(messageId, REQUEST_SET_API_SCHEMA);
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}
