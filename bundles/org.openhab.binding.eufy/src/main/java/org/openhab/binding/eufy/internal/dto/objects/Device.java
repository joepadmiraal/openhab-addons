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
import org.openhab.core.io.net.http.HttpUtil;
import org.openhab.core.library.types.RawType;

/**
 *
 * 
 * @author Iwan Bron - Initial contribution
 */
public abstract class Device extends EufyObject {
    private String stationSerialNumber;
    @EufyChannel
    private boolean enabled;
    @EufyChannel
    private int state;
    @EufyChannel
    private int battery;
    @EufyChannel
    private int batteryTemperature;
    private int lastChargingDays;
    private int lastChargingTotalEvents;
    private int lastChargingRecordedEvents;
    private int lastChargingFalseEvents;
    private int batteryUsageLastWeek;
    @EufyChannel
    private boolean motionDetected;
    @EufyChannel
    private boolean personDetected;
    @EufyChannel
    private String personName;
    @EufyChannel
    private boolean autoNightvision;
    @EufyChannel
    private boolean motionDetection;
    @EufyChannel
    private int watermark;
    private int wifiRSSI;
    @EufyChannel
    private byte[] picture;
    @EufyChannel
    private String pictureUrl;
    @EufyChannel
    private int motionDetectionType;
    @EufyChannel
    private int speakerVolume;
    @EufyChannel
    private boolean audioRecording;
    private int powerWorkingMode;
    @EufyChannel
    private boolean recordingEndClipMotionStops;
    @EufyChannel
    private int recordingClipLength;
    @EufyChannel
    private int recordingRetriggerInterval;
    @EufyChannel
    private int notificationType;
    @EufyChannel
    private int motionDetectionSensitivity;
    @EufyChannel
    private boolean statusLed;
    @EufyChannel
    private int chargingStatus;
    @EufyChannel
    private int wifiSignalLevel;

    @EufyChannel(inStatus = false, on = "device.start_livestreaming", off = "device.stop_livestreaming", get = "device.get_livestreaming")
    private boolean livestreaming;

    public String getStationSerialNumber() {
        return stationSerialNumber;
    }

    public void setStationSerialNumber(String stationSerialNumber) {
        this.stationSerialNumber = stationSerialNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getBatteryTemperature() {
        return batteryTemperature;
    }

    public void setBatteryTemperature(int batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }

    public int getLastChargingDays() {
        return lastChargingDays;
    }

    public void setLastChargingDays(int lastChargingDays) {
        this.lastChargingDays = lastChargingDays;
    }

    public int getLastChargingTotalEvents() {
        return lastChargingTotalEvents;
    }

    public void setLastChargingTotalEvents(int lastChargingTotalEvents) {
        this.lastChargingTotalEvents = lastChargingTotalEvents;
    }

    public int getLastChargingRecordedEvents() {
        return lastChargingRecordedEvents;
    }

    public void setLastChargingRecordedEvents(int lastChargingRecordedEvents) {
        this.lastChargingRecordedEvents = lastChargingRecordedEvents;
    }

    public int getLastChargingFalseEvents() {
        return lastChargingFalseEvents;
    }

    public void setLastChargingFalseEvents(int lastChargingFalseEvents) {
        this.lastChargingFalseEvents = lastChargingFalseEvents;
    }

    public int getBatteryUsageLastWeek() {
        return batteryUsageLastWeek;
    }

    public void setBatteryUsageLastWeek(int batteryUsageLastWeek) {
        this.batteryUsageLastWeek = batteryUsageLastWeek;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public void setMotionDetected(boolean motionDetected) {
        this.motionDetected = motionDetected;
    }

    public boolean isPersonDetected() {
        return personDetected;
    }

    public void setPersonDetected(boolean personDetected) {
        this.personDetected = personDetected;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public boolean isAutoNightvision() {
        return autoNightvision;
    }

    public void setAutoNightvision(boolean autoNightvision) {
        this.autoNightvision = autoNightvision;
    }

    public boolean isMotionDetection() {
        return motionDetection;
    }

    public void setMotionDetection(boolean motionDetection) {
        this.motionDetection = motionDetection;
    }

    public int getWatermark() {
        return watermark;
    }

    public void setWatermark(int watermark) {
        this.watermark = watermark;
    }

    public int getWifiRSSI() {
        return wifiRSSI;
    }

    public void setWifiRSSI(int wifiRSSI) {
        this.wifiRSSI = wifiRSSI;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getMotionDetectionType() {
        return motionDetectionType;
    }

    public void setMotionDetectionType(int motionDetectionType) {
        this.motionDetectionType = motionDetectionType;
    }

    public int getSpeakerVolume() {
        return speakerVolume;
    }

    public void setSpeakerVolume(int speakerVolume) {
        this.speakerVolume = speakerVolume;
    }

    public boolean isAudioRecording() {
        return audioRecording;
    }

    public void setAudioRecording(boolean audioRecording) {
        this.audioRecording = audioRecording;
    }

    public int getPowerWorkingMode() {
        return powerWorkingMode;
    }

    public void setPowerWorkingMode(int powerWorkingMode) {
        this.powerWorkingMode = powerWorkingMode;
    }

    public boolean isRecordingEndClipMotionStops() {
        return recordingEndClipMotionStops;
    }

    public void setRecordingEndClipMotionStops(boolean recordingEndClipMotionStops) {
        this.recordingEndClipMotionStops = recordingEndClipMotionStops;
    }

    public int getRecordingClipLength() {
        return recordingClipLength;
    }

    public void setRecordingClipLength(int recordingClipLength) {
        this.recordingClipLength = recordingClipLength;
    }

    public int getRecordingRetriggerInterval() {
        return recordingRetriggerInterval;
    }

    public void setRecordingRetriggerInterval(int recordingRetriggerInterval) {
        this.recordingRetriggerInterval = recordingRetriggerInterval;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public int getMotionDetectionSensitivity() {
        return motionDetectionSensitivity;
    }

    public void setMotionDetectionSensitivity(int motionDetectionSensitivity) {
        this.motionDetectionSensitivity = motionDetectionSensitivity;
    }

    public boolean isStatusLed() {
        return statusLed;
    }

    public void setStatusLed(boolean statusLed) {
        this.statusLed = statusLed;
    }

    public int getChargingStatus() {
        return chargingStatus;
    }

    public void setChargingStatus(int chargingStatus) {
        this.chargingStatus = chargingStatus;
    }

    public int getWifiSignalLevel() {
        return wifiSignalLevel;
    }

    public void setWifiSignalLevel(int wifiSignalLevel) {
        this.wifiSignalLevel = wifiSignalLevel;
    }

    public boolean isLivestreaming() {
        return livestreaming;
    }

    public void setLivestreaming(boolean livestreaming) {
        this.livestreaming = livestreaming;
    }

    public byte[] getPicture() {
        if (pictureUrl == null) {
            return null;
        }
        RawType download = HttpUtil.downloadData(pictureUrl, "binary/octet-stream", false, -1);
        if (download != null) {
            picture = download.getBytes();
        }
        return picture;
    }
}
