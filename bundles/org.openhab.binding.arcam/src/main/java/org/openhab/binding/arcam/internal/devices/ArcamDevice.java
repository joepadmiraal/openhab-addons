package org.openhab.binding.arcam.internal.devices;

import org.openhab.binding.arcam.internal.ArcamCommandCode;
import org.openhab.binding.arcam.internal.ArcamZone;

public interface ArcamDevice {
    public byte getInputDataByte(String inputName);

    public byte[] getInputCommand(String inputName, ArcamZone zone);

    public byte[] getVolumeCommand(int volume);

    public String getInputName(byte dataByte);

    public byte[] getDisplayBrightnessCommand(String displayBrightness);

    public String getDisplayBrightness(byte dataByte);

    public byte[] getPowerCommand(boolean on);

    public byte[] getStateCommandByte(ArcamCommandCode commandCode);
}
