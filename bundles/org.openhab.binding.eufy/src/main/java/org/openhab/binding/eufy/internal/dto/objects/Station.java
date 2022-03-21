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
import org.openhab.binding.eufy.internal.annotations.EufyProperty;

/**
 *
 * 
 * @author Iwan Bron - Initial contribution
 */
public class Station extends EufyObject {
    @EufyProperty
    private int timeFormat;
    @EufyChannel
    private int alarmVolume;
    @EufyChannel
    private int alarmTone;
    @EufyChannel
    private int promptVolume;
    @EufyProperty
    private boolean notificationSwitchModeSchedule;
    @EufyProperty
    private boolean notificationSwitchModeGeofence;
    @EufyProperty
    private boolean notificationSwitchModeApp;
    @EufyProperty
    private boolean notificationSwitchModeKeypad;
    @EufyProperty
    private boolean notificationStartAlarmDelay;
    @EufyProperty
    private String lanIpAddress;
    @EufyProperty
    private String macAddress;
    @EufyChannel
    private int currentMode;
    @EufyChannel
    private int guardMode;
    @EufyChannel
    private boolean connected;

    public int getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }

    public int getAlarmVolume() {
        return alarmVolume;
    }

    public void setAlarmVolume(int alarmVolume) {
        this.alarmVolume = alarmVolume;
    }

    public int getAlarmTone() {
        return alarmTone;
    }

    public void setAlarmTone(int alarmTone) {
        this.alarmTone = alarmTone;
    }

    public int getPromptVolume() {
        return promptVolume;
    }

    public void setPromptVolume(int promptVolume) {
        this.promptVolume = promptVolume;
    }

    public boolean isNotificationSwitchModeSchedule() {
        return notificationSwitchModeSchedule;
    }

    public void setNotificationSwitchModeSchedule(boolean notificationSwitchModeSchedule) {
        this.notificationSwitchModeSchedule = notificationSwitchModeSchedule;
    }

    public boolean isNotificationSwitchModeGeofence() {
        return notificationSwitchModeGeofence;
    }

    public void setNotificationSwitchModeGeofence(boolean notificationSwitchModeGeofence) {
        this.notificationSwitchModeGeofence = notificationSwitchModeGeofence;
    }

    public boolean isNotificationSwitchModeApp() {
        return notificationSwitchModeApp;
    }

    public void setNotificationSwitchModeApp(boolean notificationSwitchModeApp) {
        this.notificationSwitchModeApp = notificationSwitchModeApp;
    }

    public boolean isNotificationSwitchModeKeypad() {
        return notificationSwitchModeKeypad;
    }

    public void setNotificationSwitchModeKeypad(boolean notificationSwitchModeKeypad) {
        this.notificationSwitchModeKeypad = notificationSwitchModeKeypad;
    }

    public boolean isNotificationStartAlarmDelay() {
        return notificationStartAlarmDelay;
    }

    public void setNotificationStartAlarmDelay(boolean notificationStartAlarmDelay) {
        this.notificationStartAlarmDelay = notificationStartAlarmDelay;
    }

    public String getLanIpAddress() {
        return lanIpAddress;
    }

    public void setLanIpAddress(String lanIpAddress) {
        this.lanIpAddress = lanIpAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public int getGuardMode() {
        return guardMode;
    }

    public void setGuardMode(int guardMode) {
        this.guardMode = guardMode;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
