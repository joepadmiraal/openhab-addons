package org.openhab.binding.arcam.internal.devices;

public interface ArcamDevice {
    public byte getInputDataByte(String inputName);

    public String getInputName(byte dataByte);

    public byte getDisplayBrightnessDataByte(String displayBrightness);

    public String getDisplayBrightness(byte dataByte);
}
