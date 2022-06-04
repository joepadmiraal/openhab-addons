package org.openhab.binding.arcam.internal.devices;

import java.util.ArrayList;
import java.util.List;

import org.openhab.binding.arcam.internal.ArcamCommandCode;
import org.openhab.binding.arcam.internal.ArcamCommandData;
import org.openhab.binding.arcam.internal.ArcamCommandDataFinder;
import org.openhab.binding.arcam.internal.ArcamZone;

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
    public byte getInputDataByte(String inputName) {
        return commandDataFinder.getByteFromCommandDataCode(inputName, inputCommands);
    }

    @Override
    public String getInputName(byte dataByte) {

        return commandDataFinder.getCommandCodeFromByte(dataByte, inputCommands);
    }

    @Override
    public String getDisplayBrightness(byte dataByte) {

        return commandDataFinder.getCommandCodeFromByte(dataByte, displaybrightnessCommands);
    }

    @Override
    public byte[] getPowerCommand(boolean on) {
        // Using RC5 simulation
        if (on == true) {
            return new byte[] { 0x21, 0x01, 0x08, 0x02, 0x10, 0x7B };
        }

        return new byte[] { 0x21, 0x01, 0x08, 0x02, 0x10, 0x7C };
    }

    @Override
    public byte[] getInputCommand(String inputName, ArcamZone zone) {
        byte[] data = new byte[] { 0x21, ArcamDeviceUtil.zoneToByte(zone), 0x1D, 0x01, (byte) 0x01, 0x0D };
        data[4] = commandDataFinder.getByteFromCommandDataCode(inputName, inputCommands);

        return data;
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
