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
import org.openhab.binding.eufy.internal.dto.objects.Camera;
import org.openhab.binding.eufy.internal.dto.objects.Device;
import org.openhab.binding.eufy.internal.dto.objects.Doorbell;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 *
 * 
 * @author Iwan Bron - Initial contribution
 */
@NonNullByDefault
class DeviceDeserializer implements JsonDeserializer<Device> {

    private static final String ATTRIBUTE_NAME_RINGING = "ringing";

    @Override
    public @Nullable Device deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get(ATTRIBUTE_NAME_RINGING);
        if (jsonType == null) {
            return context.deserialize(jsonObject, Camera.class);
        }
        return context.deserialize(jsonObject, Doorbell.class);
    }
}
