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
package org.openhab.binding.arcam.internal.devices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.arcam.internal.ArcamNowPlaying;
import org.openhab.binding.arcam.internal.ArcamZone;
import org.openhab.binding.arcam.internal.connection.ArcamCommandCode;
import org.openhab.binding.arcam.internal.connection.ArcamCommandData;

/**
 * The {@link ArcamSA20} class contains the device specific implementation for the SA20
 *
 * @author Joep Admiraal - Initial contribution
 */
@NonNullByDefault
public class ArcamSA20 implements ArcamDevice {

    // TODO not done yet
    public static List<ArcamCommandData> inputCommands = new ArrayList<>(List.of( //
            new ArcamCommandData("PHONO", (byte) 0x01), //
            new ArcamCommandData("AUX", (byte) 0x02) //
    ));

    public enum SA20Input {
        PHONO("PHONO", (byte) 0x01),
        AUX("AUX", (byte) 0x02),
        PVR("PVR", (byte) 0x03),
        AV("AV", (byte) 0x04),
        STB("STB", (byte) 0x05),
        CD("CD", (byte) 0x06),
        BD("BD", (byte) 0x07),
        SAT("SAT", (byte) 0x08);

        public final String value;
        public final byte dataByte;

        private SA20Input(String value, byte dataByte) {
            this.value = value;
            this.dataByte = dataByte;
        }
    }

    public static String SA20 = "SA20";

    @Override
    public byte[] getPowerCommand(boolean on, ArcamZone zone) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public byte[] getInputCommand(String inputName, ArcamZone zone) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public byte[] getHeartbeatCommand() {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public byte[] getVolumeCommand(int volume, ArcamZone zone) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public byte[] getDisplayBrightnessCommand(String displayBrightness) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public String getNowPlayingSampleRate(byte dataByte) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public String getNowPlayingEncoder(byte dataByte) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public byte[] getMuteCommand(boolean mute, ArcamZone zone) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public boolean getMute(byte dataByte) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public byte[] getRebootCommand() {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public byte[] getBalanceCommand(int balance, ArcamZone zone) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public byte[] getRoomEqualisationCommand(String eq, ArcamZone zone) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public int getBalance(byte dataByte) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getSoftwareVersion(List<Byte> dataBytes) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public String getIncomingSampleRate(byte dataByte) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public boolean getBoolean(byte dataByte) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getTimeoutCounter(List<Byte> dataBytes) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTemperature(List<Byte> dataBytes, int tempNr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public byte[] getDacFilterCommand(String dacFilter) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public @Nullable String getDisplayBrightness(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public @Nullable String getInputName(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getStateCommandByte(ArcamCommandCode commandCode) {
        // TODO Auto-generated method stub
        return new byte[] {};
    }

    @Override
    public @Nullable String getDacFilter(Byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public @Nullable String getRoomEqualisation(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public @Nullable ArcamNowPlaying setNowPlaying(List<Byte> dataBytes) {
        // TODO Auto-generated method stub
        return null;
    }

}
