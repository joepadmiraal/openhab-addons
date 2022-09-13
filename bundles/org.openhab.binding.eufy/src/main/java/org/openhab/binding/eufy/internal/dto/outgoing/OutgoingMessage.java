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
public abstract class OutgoingMessage {
    public static final String REQUEST_START_LISTENING = "start_listening";
    public static final String REQUEST_SET_API_SCHEMA = "set_api_schema";
    public static final String REQUEST_KEEPALIVE = "driver.poll_refresh";
    public static final String TRIGGER_ALARM = "station.trigger_alarm";
    public static final String RESET_ALARM = "station.reset_alarm";
    public static final String COMMAND_SET_PROPERTY = "device.set_property";
    public static final String COMMAND_IS_RTSP = "device.is_rtsp_livestream";
    public static final String COMMAND_START_RTSP = "device.start_rtsp_livestream";
    public static final String COMMAND_STOP_RTSP = "device.stop_rtsp_livestream";

    private String messageId;
    private String command;

    public OutgoingMessage(String messageId, String command) {
        this.messageId = messageId;
        this.command = command;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
