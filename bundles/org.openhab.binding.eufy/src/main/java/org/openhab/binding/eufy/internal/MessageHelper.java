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
package org.openhab.binding.eufy.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.eufy.internal.dto.Event;
import org.openhab.binding.eufy.internal.dto.Message;
import org.openhab.binding.eufy.internal.dto.ResultMessage;
import org.openhab.binding.eufy.internal.dto.objects.BoolInt;
import org.openhab.binding.eufy.internal.dto.objects.Device;
import org.openhab.binding.eufy.internal.dto.outgoing.OutgoingMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The messageHelper is the main class to serialize and deserialize json communication between the
 * Bridge and the Eufy Docker container.
 *
 * @author Iwan Bron - initial contribution
 *
 */
@NonNullByDefault
public class MessageHelper {
    private static MessageHelper INSTANCE = new MessageHelper();

    final Gson gson;

    private MessageHelper() {
        gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageDeserializer())
                .registerTypeAdapter(Device.class, new DeviceDeserializer())
                .registerTypeAdapter(ResultMessage.class, new ResultDeserializer())
                .registerTypeAdapter(Event.class, new EventDeserializer())
                .registerTypeAdapter(BoolInt.class, new BoolIntDeserializer()).create();
    }

    public static @Nullable Message parse(String message) {
        return INSTANCE.gson.fromJson(message, Message.class);
    }

    public static String toString(OutgoingMessage command) {
        return INSTANCE.gson.toJson(command);
    }
}
