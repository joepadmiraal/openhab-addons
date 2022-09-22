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
import org.openhab.binding.eufy.internal.dto.objects.BoolInt;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 *
 * @author Joep Admiraal - Initial contribution
 */
@NonNullByDefault
public class BoolIntDeserializer implements JsonDeserializer<BoolInt> {

    @Override
    public @Nullable BoolInt deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        BoolInt b = new BoolInt();
        if (json.getAsJsonPrimitive().isBoolean()) {
            b.value = json.getAsBoolean();
        } else {
            int intValue = json.getAsInt();
            b.value = intValue == 1;
        }

        return b;
    }
}
