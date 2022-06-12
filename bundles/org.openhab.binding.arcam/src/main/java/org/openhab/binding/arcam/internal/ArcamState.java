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
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.State;

@NonNullByDefault
public class ArcamState {
    // Generic
    @Nullable
    private State dacFilter;
    @Nullable
    private State dcOffset;
    @Nullable
    private State displayBrightness;
    @Nullable
    private State headphones;
    @Nullable
    private State incomingSampleRate;
    @Nullable
    private State lifterTemperature;
    @Nullable
    private State outputTemperature;
    @Nullable
    private State softwareVersion;
    @Nullable
    private State timeoutCounter;

    // Master zone
    @Nullable
    private State masterBalance;
    @Nullable
    private State masterDirectMode;
    @Nullable
    private State masterInput;
    @Nullable
    private State masterInputDetect;
    @Nullable
    private State masterNowPlayingTitle;
    @Nullable
    private State masterNowPlayingArtist;
    @Nullable
    private State masterNowPlayingAlbum;
    @Nullable
    private State masterNowPlayingApplication;
    @Nullable
    private State masterNowPlayingSampleRate;
    @Nullable
    private State masterNowPlayingAudioEncoder;
    @Nullable
    private State masterMute;
    @Nullable
    private State masterPower;
    @Nullable
    private State masterRoomEqualisation;
    @Nullable
    private State masterShortCircuit;
    @Nullable
    private State masterVolume;

    // Zone2
    @Nullable
    private State zone2Input;
    @Nullable
    private State zone2Mute;
    @Nullable
    private State zone2Power;
    @Nullable
    private State zone2Volume;

    private ArcamStateChangedListener handler;

    public ArcamState(ArcamStateChangedListener handler) {
        this.handler = handler;
    }

    // Generic
    public void setDacFilter(String dacFilter) {
        StringType newVal = new StringType(dacFilter);
        if (!newVal.equals(this.dacFilter)) {
            this.dacFilter = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_DAC_FILTER, this.dacFilter);
        }
    }

    public void setDcOffset(boolean dc) {
        OnOffType newVal = dc ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.dcOffset) {
            this.dcOffset = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_DC_OFFSET, newVal);
        }
    }

    public void setDisplayBrightness(String brightness) {
        StringType newVal = new StringType(brightness);
        if (!newVal.equals(this.displayBrightness)) {
            this.displayBrightness = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_DISPLAY_BRIGHTNESS, this.displayBrightness);
        }
    }

    public void setHeadphones(boolean headphones) {
        OnOffType newVal = headphones ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.headphones) {
            this.headphones = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_HEADPHONES, newVal);
        }
    }

    public void setIncomingSampleRate(String sampleRate) {
        StringType newVal = new StringType(sampleRate);
        if (!newVal.equals(this.incomingSampleRate)) {
            this.incomingSampleRate = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_INCOMING_SAMPLE_RATE, this.incomingSampleRate);
        }
    }

    public void setLifterTemperature(int temperature) {
        DecimalType newVal = new DecimalType(temperature);
        if (!newVal.equals(this.lifterTemperature)) {
            this.lifterTemperature = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_LIFTER_TEMPERATURE, newVal);
        }
    }

    public void setOutputTemperature(int temperature) {
        DecimalType newVal = new DecimalType(temperature);
        if (!newVal.equals(this.outputTemperature)) {
            this.outputTemperature = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_OUTPUT_TEMPERATURE, newVal);
        }
    }

    public void setSoftwareVersion(String version) {
        StringType newVal = new StringType(version);
        if (!newVal.equals(this.softwareVersion)) {
            this.softwareVersion = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_SOFTWARE_VERSION, this.softwareVersion);
        }
    }

    public void setTimeoutCounter(int minutes) {
        DecimalType newVal = new DecimalType(minutes);
        if (!newVal.equals(this.timeoutCounter)) {
            this.timeoutCounter = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_TIMEOUT_COUNTER, newVal);
        }
    }

    // Master zone
    public void setMasterBalance(int balance) {
        DecimalType newVal = new DecimalType(balance);
        if (!newVal.equals(this.masterBalance)) {
            this.masterBalance = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_BALANCE, newVal);
        }
    }

    public void setMasterDirectMode(boolean direct) {
        OnOffType newVal = direct ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.masterDirectMode) {
            this.masterDirectMode = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_DIRECT_MODE, newVal);
        }
    }

    public void setMasterInput(String input) {
        StringType newVal = new StringType(input);
        if (!newVal.equals(this.masterInput)) {
            this.masterInput = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_INPUT, this.masterInput);
        }
    }

    public void setMasterInputDetect(boolean inputDetect) {
        OnOffType newVal = inputDetect ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.masterInputDetect) {
            this.masterInputDetect = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_INPUT_DETECT, newVal);
        }
    }

    public void setMasterMute(boolean mute) {
        OnOffType newVal = mute ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.masterMute) {
            this.masterMute = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_MUTE, newVal);
        }
    }

    public void setMasterNowPlayingTitle(String title) {
        StringType newVal = new StringType(title);
        if (!newVal.equals(this.masterNowPlayingTitle)) {
            this.masterNowPlayingTitle = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_NOW_PLAYING_TITLE, this.masterNowPlayingTitle);
        }
    }

    public void setMasterNowPlayingArtist(String artist) {
        StringType newVal = new StringType(artist);
        if (!newVal.equals(this.masterNowPlayingArtist)) {
            this.masterNowPlayingArtist = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_NOW_PLAYING_ARTIST, this.masterNowPlayingArtist);
        }
    }

    public void setMasterNowPlayingAlbum(String album) {
        StringType newVal = new StringType(album);
        if (!newVal.equals(this.masterNowPlayingAlbum)) {
            this.masterNowPlayingAlbum = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_NOW_PLAYING_ALBUM, this.masterNowPlayingAlbum);
        }
    }

    public void setMasterNowPlayingApplication(String application) {
        StringType newVal = new StringType(application);
        if (!newVal.equals(this.masterNowPlayingApplication)) {
            this.masterNowPlayingApplication = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_NOW_PLAYING_APPLICATION,
                    this.masterNowPlayingApplication);
        }
    }

    public void setMasterNowPlayingSampleRate(String sampleRate) {
        StringType newVal = new StringType(sampleRate);
        if (!newVal.equals(this.masterNowPlayingSampleRate)) {
            this.masterNowPlayingSampleRate = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_NOW_PLAYING_SAMPLE_RATE,
                    this.masterNowPlayingSampleRate);
        }
    }

    public void setMasterNowPlayingAudioEncoder(String audioEncoder) {
        StringType newVal = new StringType(audioEncoder);
        if (!newVal.equals(this.masterNowPlayingAudioEncoder)) {
            this.masterNowPlayingAudioEncoder = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_NOW_PLAYING_AUDIO_ENCODER,
                    this.masterNowPlayingAudioEncoder);
        }
    }

    public void setMasterPower(boolean power) {
        OnOffType newVal = power ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.masterPower) {
            this.masterPower = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_POWER, newVal);
        }
    }

    public void setMasterRoomEqualisation(String eq) {
        StringType newVal = new StringType(eq);
        if (!newVal.equals(this.masterRoomEqualisation)) {
            this.masterRoomEqualisation = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_ROOM_EQUALISATION, newVal);
        }
    }

    public void setMasterShortCircuit(boolean shortCircuit) {
        OnOffType newVal = shortCircuit ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.masterShortCircuit) {
            this.masterShortCircuit = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_SHORT_CIRCUIT, newVal);
        }
    }

    public void setMasterVolume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume);
        if (!newVal.equals(this.masterVolume)) {
            this.masterVolume = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_MASTER_VOLUME, newVal);
        }
    }

    // Zone2
    public void setZone2Input(String input) {
        StringType newVal = new StringType(input);
        if (!newVal.equals(this.zone2Input)) {
            this.zone2Input = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_ZONE2_INPUT, this.zone2Input);
        }
    }

    public void setZone2Power(boolean power) {
        OnOffType newVal = power ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.zone2Power) {
            this.zone2Power = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_ZONE2_POWER, newVal);
        }
    }

    public void setZone2Mute(boolean mute) {
        OnOffType newVal = mute ? OnOffType.ON : OnOffType.OFF;
        if (newVal != this.zone2Mute) {
            this.zone2Mute = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_ZONE2_MUTE, newVal);
        }
    }

    public void setZone2Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume);
        if (!newVal.equals(this.zone2Volume)) {
            this.zone2Volume = newVal;
            handler.stateChanged(ArcamBindingConstants.CHANNEL_ZONE2_VOLUME, newVal);
        }
    }

}
