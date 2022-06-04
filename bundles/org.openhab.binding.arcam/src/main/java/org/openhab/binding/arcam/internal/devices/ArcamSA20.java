package org.openhab.binding.arcam.internal.devices;

import org.openhab.binding.arcam.internal.ArcamCommandCode;
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
    public byte getInputDataByte(String inputName) {
        SA20Input input = SA20Input.valueOf(inputName);
        return input.dataByte;
    }

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
    public byte[] getPowerCommand(boolean on) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getInputCommand(String inputName, ArcamZone zone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getVolumeCommand(int volume) {
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
}
