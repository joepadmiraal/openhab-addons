package org.openhab.binding.arcam.internal.devices;

import java.util.List;

import org.openhab.binding.arcam.internal.ArcamCommandCode;
import org.openhab.binding.arcam.internal.ArcamNowPlaying;
import org.openhab.binding.arcam.internal.ArcamZone;

public class ArcamSA20 implements ArcamDevice {
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
    public String getInputName(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayBrightness(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getPowerCommand(boolean on, ArcamZone zone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getInputCommand(String inputName, ArcamZone zone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getVolumeCommand(int volume, ArcamZone zone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getDisplayBrightnessCommand(String displayBrightness) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getStateCommandByte(ArcamCommandCode commandCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArcamNowPlaying setNowPlaying(List<Byte> dataBytes) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNowPlayingSampleRate(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNowPlayingEncoder(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getMuteCommand(boolean mute, ArcamZone zone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getMute(byte dataByte) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public byte[] getRebootCommand() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getBalanceCommand(int balance, ArcamZone zone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getRoomEqualisationCommand(String eq, ArcamZone zone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getBalance(byte dataByte) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getRoomEqualisation(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSoftwareVersion(List<Byte> dataBytes) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public String getIncomingSampleRate(byte dataByte) {
        // TODO Auto-generated method stub
        return null;
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

}
