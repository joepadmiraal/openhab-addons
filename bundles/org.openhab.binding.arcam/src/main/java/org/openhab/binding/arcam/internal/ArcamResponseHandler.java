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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NonNullByDefault
public class ArcamResponseHandler {
    private final Logger logger = LoggerFactory.getLogger(ArcamConnectionReader.class);
    private int byteNr;
    private boolean readyForNewResponse = true;
    private ArcamResponse response;

    public ArcamResponseHandler() {
        response = new ArcamResponse();
    }

    @Nullable
    public ArcamResponse parseByte(byte b) {
        // logger.info("parseByte: {}: {}", byteNr + 1, b);
        byteNr++;
        if (readyForNewResponse && b == 0x21) {
            byteNr = 0;
            readyForNewResponse = false;
            // logger.info("New response");
            return null;
        }
        if (readyForNewResponse) {
            return null;
        }

        if (byteNr == 1) {
            response.zn = b;
            return null;
        }
        if (byteNr == 2) {
            response.cc = b;
            return null;
        }
        if (byteNr == 3) {
            response.ac = b;
            return null;
        }
        if (byteNr == 4) {
            response.dl = Byte.valueOf(b).intValue();
            return null;
        }
        if (byteNr > 4 && byteNr <= response.dl + 4) {
            response.data.add(b);
            return null;
        }

        logger.info("Got full response, cc: {}, zone: {}, length: {}, data: {}", ArcamUtil.byteToHex(response.cc),
                ArcamUtil.byteToHex(response.zn), response.dl, response.data);
        ArcamResponse retVal = response;
        response = new ArcamResponse();
        readyForNewResponse = true;
        return retVal;
    }
}
