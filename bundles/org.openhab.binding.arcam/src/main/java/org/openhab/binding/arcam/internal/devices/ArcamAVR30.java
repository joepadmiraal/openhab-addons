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
import org.openhab.binding.arcam.internal.connection.ArcamCommandDataFinder;

/**
 * The {@link ArcamAVR30} class contains the device specific implementation for the AVR30
 *
 * @author Joep Admiraal - Initial contribution
 */
@NonNullByDefault
public class ArcamAVR30 implements ArcamDevice {

    public static List<ArcamCommandData> inputCommands = new ArrayList<>(List.of( //
            new ArcamCommandData("FZONE1", "Follow Zone 1", (byte) 0x00), //
            new ArcamCommandData("CD", (byte) 0x01), //
            new ArcamCommandData("BD", (byte) 0x02), //
            new ArcamCommandData("AV", (byte) 0x03), //
            new ArcamCommandData("SAT", (byte) 0x04), //
            new ArcamCommandData("PVR", (byte) 0x05), //
            new ArcamCommandData("UHD", (byte) 0x06), //
            new ArcamCommandData("AUX", (byte) 0x08), //
            new ArcamCommandData("DISPLAY", (byte) 0x09), //
            new ArcamCommandData("TUNERFM", "TUNER (FM)", (byte) 0x0B), //
            new ArcamCommandData("TUNERDAB", "TUNER (DAB)", (byte) 0x0C), //
            new ArcamCommandData("NET", (byte) 0x0E), //
            new ArcamCommandData("STB", (byte) 0x10), //
            new ArcamCommandData("GAME", (byte) 0x11), //
            new ArcamCommandData("BT", (byte) 0x12)));

    public static List<ArcamCommandData> displaybrightnessCommands = new ArrayList<>(List.of( //
            new ArcamCommandData("OFF", "Front panel is off", (byte) 0x00), //
            new ArcamCommandData("L1", "Front panel L1", (byte) 0x01), //
            new ArcamCommandData("L2", "Front panel L2", (byte) 0x02) //
    ));

    public static String AVR30 = "AVR30";

    private ArcamCommandDataFinder commandDataFinder;

    public ArcamAVR30() {
        this.commandDataFinder = new ArcamCommandDataFinder();
    }

    @Override
    @Nullable
    public String getInputName(byte dataByte) {

        return commandDataFinder.getCommandCodeFromByte(dataByte, inputCommands);
    }

    @Override
    @Nullable
    public String getDisplayBrightness(byte dataByte) {

        return commandDataFinder.getCommandCodeFromByte(dataByte, displaybrightnessCommands);
    }

    @Override
    public byte[] getPowerCommand(boolean on, ArcamZone zone) {
        // Using RC5 simulation
        if (on == true) {
            return new byte[] { 0x21, ArcamDeviceUtil.zoneToByte(zone), 0x08, 0x02, 0x10, 0x7B };
        }

        return new byte[] { 0x21, ArcamDeviceUtil.zoneToByte(zone), 0x08, 0x02, 0x10, 0x7C };
    }

    @Override
    public byte[] getInputCommand(String inputName, ArcamZone zone) {
        byte[] data = new byte[] { 0x21, ArcamDeviceUtil.zoneToByte(zone), 0x1D, 0x01, (byte) 0x01, 0x0D };
        data[4] = commandDataFinder.getByteFromCommandDataCode(inputName, inputCommands);

        return data;
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
    public byte[] getStateCommandByte(ArcamCommandCode commandCode) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    @Nullable
    public ArcamNowPlaying setNowPlaying(List<Byte> dataBytes) {
        // TODO Auto-generated method stub
        return null;
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
    @Nullable
    public String getRoomEqualisation(byte dataByte) {
        // TODO Auto-generated method stub
        return "";
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
    @Nullable
    public String getDacFilter(Byte dataByte) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public byte[] getDacFilterCommand(String dacFilter) {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }

    @Override
    public byte[] getHeartbeatCommand() {
        // TODO Auto-generated method stub
        return new byte[] { 0x00 };
    }
}
