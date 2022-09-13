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

/**
 * Represents a Eufy doorbell
 *
 * @author Iwan Bron - Initial contribution
 */
public class Doorbell extends Device {
    @EufyChannel
    private boolean ringing;
    @EufyChannel
    private int ringtoneVolume;
    @EufyChannel
    private int videoStreamingQuality;
    private boolean videoWDR;
    @EufyChannel
    private boolean chimeIndoor;
    @EufyChannel
    private boolean chimeHomebase;
    @EufyChannel
    private int chimeHomebaseRingtoneVolume;
    @EufyChannel
    private int chimeHomebaseRingtoneType;
    @EufyChannel
    private boolean notificationRing;
    @EufyChannel
    private boolean notificationMotion;

    public boolean isRinging() {
        return ringing;
    }

    public void setRinging(boolean ringing) {
        this.ringing = ringing;
    }

    public int getRingtoneVolume() {
        return ringtoneVolume;
    }

    public void setRingtoneVolume(int ringtoneVolume) {
        this.ringtoneVolume = ringtoneVolume;
    }

    public int getVideoStreamingQuality() {
        return videoStreamingQuality;
    }

    public void setVideoStreamingQuality(int videoStreamingQuality) {
        this.videoStreamingQuality = videoStreamingQuality;
    }

    public boolean isVideoWDR() {
        return videoWDR;
    }

    public void setVideoWDR(boolean videoWDR) {
        this.videoWDR = videoWDR;
    }

    public boolean isChimeIndoor() {
        return chimeIndoor;
    }

    public void setChimeIndoor(boolean chimeIndoor) {
        this.chimeIndoor = chimeIndoor;
    }

    public boolean isChimeHomebase() {
        return chimeHomebase;
    }

    public void setChimeHomebase(boolean chimeHomebase) {
        this.chimeHomebase = chimeHomebase;
    }

    public int getChimeHomebaseRingtoneVolume() {
        return chimeHomebaseRingtoneVolume;
    }

    public void setChimeHomebaseRingtoneVolume(int chimeHomebaseRingtoneVolume) {
        this.chimeHomebaseRingtoneVolume = chimeHomebaseRingtoneVolume;
    }

    public int getChimeHomebaseRingtoneType() {
        return chimeHomebaseRingtoneType;
    }

    public void setChimeHomebaseRingtoneType(int chimeHomebaseRingtoneType) {
        this.chimeHomebaseRingtoneType = chimeHomebaseRingtoneType;
    }

    public boolean getNotificationRing() {
        return notificationRing;
    }

    public void setNotificationRing(boolean notificationRing) {
        this.notificationRing = notificationRing;
    }

    public boolean getNotificationMotion() {
        return notificationMotion;
    }

    public void setNotificationMotion(boolean notificationMotion) {
        this.notificationMotion = notificationMotion;
    }
}
