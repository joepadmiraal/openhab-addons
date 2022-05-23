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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@NonNullByDefault
public class ArcamModelUtil {
    @Nullable
    public static ArcamModel getModel(String modelName, String modelNumber) {
        if (!modelName.trim().isEmpty()) {
            String upperCase = modelName.toUpperCase();
            if (upperCase.contains("AVR380")) {
                return ArcamModel.AVR380;
            }
            if (upperCase.contains("CDS27")) {
                return ArcamModel.CDS27;
            }
            if (upperCase.contains("CDS50")) {
                return ArcamModel.CDS50;
            }
            if (upperCase.contains("SA10")) {
                return ArcamModel.SA10;
            }
            if (upperCase.contains("SA20")) {
                return ArcamModel.SA20;
            }
            if (upperCase.contains("SA30")) {
                return ArcamModel.SA30;
            }
            if (upperCase.contains("SOLO MOVIE")) {
                return ArcamModel.SOLO_MOVIE;
            }
            if (upperCase.contains("SOLO MUSIC")) {
                return ArcamModel.SOLO_MUSIC;
            }
            if (upperCase.contains("AVR390")) {
                return ArcamModel.AVR390;
            }
            if (upperCase.contains("UDP411")) {
                return ArcamModel.UDP411;
            }
            if (upperCase.contains("AVR750")) {
                return ArcamModel.AVR450_750;
            }
            if (upperCase.contains("AVR10")) {
                return ArcamModel.AVR10;
            }
            if (upperCase.matches("ARCAM (AVR20|AVR30|AV40)")) {
                return ArcamModel.AVR20_40;
            }
            return null;
        } else if (modelNumber.isEmpty()) {
            return null;
        } else {
            String upperCase2 = modelNumber.toUpperCase();
            if (upperCase2.contains("AVR390")) {
                return ArcamModel.AVR390;
            }
            if (upperCase2.contains("AVR380")) {
                return ArcamModel.AVR380;
            }
            if (upperCase2.contains("AVR450")) {
                return ArcamModel.AVR450_750;
            }
            if (upperCase2.contains("AVR750")) {
                return ArcamModel.AVR450_750;
            }
            if (upperCase2.contains("AVR850")) {
                return ArcamModel.AVR450_750;
            }
            return null;
        }
    }
}
