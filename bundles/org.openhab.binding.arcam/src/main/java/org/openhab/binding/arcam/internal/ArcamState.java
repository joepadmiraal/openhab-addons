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
package org.openhab.binding.arcam.internal;

import java.math.BigDecimal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.State;

@NonNullByDefault
public class ArcamState {
    @Nullable
    private State power;
    @Nullable
    private State volume;
    @Nullable
    private State input;

    private ArcamStateChangedListener handler;

    public ArcamState(ArcamStateChangedListener handler) {
        this.handler = handler;
    }

    public void setPower(boolean power) {
        OnOffType newVal = power ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.power) {
            this.power = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_POWER, newVal);
        }
    }

    public void setVolume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume);
        if (!newVal.equals(this.volume)) {
            this.volume = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_VOLUME, newVal);
        }
    }

    public void setInput(String input) {
        StringType newVal = new StringType(input);
        if (!newVal.equals(this.input)) {
            this.input = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_INPUT, this.input);
        }
    }
}
