package org.openhab.binding.arcam.internal.devices;

import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.arcam.internal.ArcamCommandCode;
import org.openhab.binding.arcam.internal.ArcamNowPlaying;
import org.openhab.binding.arcam.internal.ArcamZone;

@NonNullByDefault
public interface ArcamDevice {
    // Methods used to get a value from the device
    public byte @Nullable [] getStateCommandByte(ArcamCommandCode commandCode);

    // Methods used to send a value to the device
    public byte[] getBalanceCommand(int balance, ArcamZone zone);

    public byte[] getDacFilterCommand(String dacFilter);

    public byte[] getDisplayBrightnessCommand(String displayBrightness);

    public byte[] getInputCommand(String inputName, ArcamZone zone);

    public byte[] getMuteCommand(boolean mute, ArcamZone zone);

    public byte[] getPowerCommand(boolean on, ArcamZone zone);

    public byte[] getRoomEqualisationCommand(String eq, ArcamZone zone);

    public byte[] getRebootCommand();

    public byte[] getVolumeCommand(int volume, ArcamZone zone);

    // Methods used to convert incoming data to a value
    public int getBalance(byte dataByte);

    public boolean getBoolean(byte dataByte);

    public String getDacFilter(Byte dataByte);

    public String getDisplayBrightness(byte dataByte);

    public String getIncomingSampleRate(byte dataByte);

    public String getInputName(byte dataByte);

    // PA720 needs a byte array
    public int getTemperature(List<Byte> dataBytes, int tempNr);

    public boolean getMute(byte dataByte);

    public String getNowPlayingSampleRate(byte dataByte);

    public String getNowPlayingEncoder(byte dataByte);

    public String getRoomEqualisation(byte dataByte);

    public String getSoftwareVersion(List<Byte> dataBytes);

    public int getTimeoutCounter(List<Byte> dataBytes);

    // Methods used to successively provide dataByte arrays which belong together. When complete it will return an
    // object with the parsed values
    @Nullable
    public ArcamNowPlaying setNowPlaying(List<Byte> dataBytes);
}
