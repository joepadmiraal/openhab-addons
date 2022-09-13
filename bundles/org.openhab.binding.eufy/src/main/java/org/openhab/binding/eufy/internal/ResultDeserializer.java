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
import org.openhab.binding.eufy.internal.dto.CommandResultMessage;
import org.openhab.binding.eufy.internal.dto.LifestreamingResultMessage;
import org.openhab.binding.eufy.internal.dto.ResultMessage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Deserializer that can deserialise Result messages.
 *
 * @author Iwan Bron - Initial contribution
 *
 */
@NonNullByDefault
class ResultDeserializer implements JsonDeserializer<ResultMessage> {

    private static final String ATTRIBUTE_NAME_LIVESTREAMING = "livestreaming";

    @Override
    public @Nullable ResultMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonLivestreaming = jsonObject.get(ATTRIBUTE_NAME_LIVESTREAMING);
        if (jsonLivestreaming == null) {
            return context.deserialize(jsonObject, CommandResultMessage.class);
        }
        return context.deserialize(jsonObject, LifestreamingResultMessage.class);
    }
}
