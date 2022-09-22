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
import org.openhab.binding.eufy.internal.dto.Event;
import org.openhab.binding.eufy.internal.dto.MotionDetectedEvent;
import org.openhab.binding.eufy.internal.dto.PersonDetectedEvent;
import org.openhab.binding.eufy.internal.dto.PropertyChangedEvent;
import org.openhab.binding.eufy.internal.dto.RingsEvent;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Deserializer that can handle Event json nodes
 *
 * @author Iwan Bron - Initial contribution
 */
@NonNullByDefault
class EventDeserializer implements JsonDeserializer<Event> {

    private static final String ATTRIBUTE_EVENT = "event";
    private static final String TYPE_MOTION_DETECTED = "motion detected";
    private static final String TYPE_PERSON_DETECTED = "person detected";
    private static final String TYPE_PROPERTY_CHANGED = "property changed";
    private static final String TYPE_RINGS = "rings";

    @Override
    public @Nullable Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get(ATTRIBUTE_EVENT);
        if (jsonType != null) {
            if (jsonType.getAsString().equals(TYPE_MOTION_DETECTED)) {
                return context.deserialize(jsonObject, MotionDetectedEvent.class);
            } else if (jsonType.getAsString().equals(TYPE_PERSON_DETECTED)) {
                return context.deserialize(jsonObject, PersonDetectedEvent.class);
            } else if (jsonType.getAsString().equals(TYPE_PROPERTY_CHANGED)) {
                return context.deserialize(jsonObject, PropertyChangedEvent.class);
            } else if (jsonType.getAsString().equals(TYPE_RINGS)) {
                return context.deserialize(jsonObject, RingsEvent.class);
            }
        }
        return context.deserialize(jsonObject, PropertyChangedEvent.class);
    }
}
