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

import java.lang.reflect.Type;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.eufy.internal.dto.EventMessage;
import org.openhab.binding.eufy.internal.dto.Message;
import org.openhab.binding.eufy.internal.dto.ResultMessage;
import org.openhab.binding.eufy.internal.dto.StatusMessage;
import org.openhab.binding.eufy.internal.dto.VersionMessage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Deserializer that can handle Message json objects
 *
 * @author Iwan Bron - initial contribution
 */
@NonNullByDefault
class MessageDeserializer implements JsonDeserializer<Message> {

    private static final String ATTRIBUTE_NAME_STATE = "state";
    private static final String ATTRIBUTE_NAME_RESULT = "result";
    private static final String ATTRIBUTE_NAME_TYPE = "type";
    private static final String ATTRIBUTE_VALUE_RESULT = "result";
    private static final String ATTRIBUTE_VALUE_EVENT = "event";
    private static final String ATTRIBUTE_VALUE_VERSION = "version";

    @Override
    public @Nullable Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get(ATTRIBUTE_NAME_TYPE);
        if (jsonType != null) {
            String type = jsonType.getAsString();
            switch (type) {
                case ATTRIBUTE_VALUE_RESULT:
                    JsonElement jsonResult = jsonObject.get(ATTRIBUTE_NAME_RESULT);
                    if (jsonResult != null) {
                        JsonElement state = jsonResult.getAsJsonObject().get(ATTRIBUTE_NAME_STATE);
                        if (state != null) {
                            return context.deserialize(json, StatusMessage.class);
                        }
                    }
                    return context.deserialize(json, ResultMessage.class);
                case ATTRIBUTE_VALUE_EVENT:
                    return context.deserialize(json, EventMessage.class);
                case ATTRIBUTE_VALUE_VERSION:
                    return context.deserialize(json, VersionMessage.class);
            }
        }
        return context.deserialize(jsonObject, ResultMessage.class);
    }
}
