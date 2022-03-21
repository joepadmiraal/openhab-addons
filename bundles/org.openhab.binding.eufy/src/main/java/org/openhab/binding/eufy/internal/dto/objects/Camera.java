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
package org.openhab.binding.eufy.internal.dto.objects;

import org.openhab.binding.eufy.internal.annotations.EufyChannel;
import org.openhab.binding.eufy.internal.dto.outgoing.OutgoingMessage;

/**
 *
 * 
 * @author Iwan Bron - Initial contribution
 */
public class Camera extends Device {
    @EufyChannel
    private boolean antitheftDetection;
    @EufyChannel
    private boolean rtspStream;
    @EufyChannel
    private boolean microphone;
    @EufyChannel
    private boolean speaker;
    @EufyChannel
    private int powerSource;
    @EufyChannel
    private String rtspStreamUrl;
    @EufyChannel(get = OutgoingMessage.COMMAND_IS_RTSP, inStatus = false, on = OutgoingMessage.COMMAND_START_RTSP, off = OutgoingMessage.COMMAND_STOP_RTSP)
    private boolean rtspStreaming;

    public boolean isAntitheftDetection() {
        return antitheftDetection;
    }

    public void setAntitheftDetection(boolean antitheftDetection) {
        this.antitheftDetection = antitheftDetection;
    }

    public boolean isRtspStream() {
        return rtspStream;
    }

    public void setRtspStream(boolean rtspStream) {
        this.rtspStream = rtspStream;
    }

    public boolean isMicrophone() {
        return microphone;
    }

    public void setMicrophone(boolean microphone) {
        this.microphone = microphone;
    }

    public boolean isSpeaker() {
        return speaker;
    }

    public void setSpeaker(boolean speaker) {
        this.speaker = speaker;
    }

    public int getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(int powerSource) {
        this.powerSource = powerSource;
    }

    public String getRtspStreamUrl() {
        return rtspStreamUrl;
    }

    public void setRtspStreamUrl(String rtspStreamUrl) {
        this.rtspStreamUrl = rtspStreamUrl;
    }
}
