package org.openhab.binding.arcam.internal.devices;

import static org.openhab.binding.arcam.internal.ArcamCommandCode.*;

import java.util.ArrayList;
import java.util.List;

import org.openhab.binding.arcam.internal.ArcamCommand;
import org.openhab.binding.arcam.internal.ArcamCommandCode;
import org.openhab.binding.arcam.internal.ArcamCommandData;
import org.openhab.binding.arcam.internal.ArcamCommandDataFinder;
import org.openhab.binding.arcam.internal.ArcamCommandFinder;

public class ArcamSA30 implements ArcamDevice {

    public static List<ArcamCommandData> inputCommands = new ArrayList<>(List.of( //
            new ArcamCommandData("PHONO", (byte) 0x01), //
            new ArcamCommandData("AUX", (byte) 0x02), //
            new ArcamCommandData("PVR", (byte) 0x03), //
            new ArcamCommandData("AV", (byte) 0x04), //
            new ArcamCommandData("STB", (byte) 0x05), //
            new ArcamCommandData("CD", (byte) 0x06), //
            new ArcamCommandData("BD", (byte) 0x07), //
            new ArcamCommandData("SAT", (byte) 0x08), //
            new ArcamCommandData("GAME", (byte) 0x09), //
            new ArcamCommandData("NETUSB", "NET/USB", (byte) 0x0B), //
            new ArcamCommandData("ARC", (byte) 0x0D) //
    ));

    public static List<ArcamCommandData> displaybrightnessCommands = new ArrayList<>(List.of( //
            new ArcamCommandData("OFF", (byte) 0x00), //
            new ArcamCommandData("DIM", (byte) 0x01), //
            new ArcamCommandData("FULL", (byte) 0x02) //
    ));

    // List of commands, with the databytes set to the request state bytes
    public static List<ArcamCommand> commands = new ArrayList<>(List.of( //
            new ArcamCommand(SYSTEM_STATUS, new byte[] { 0x21, 0x01, 0x5D, 0x01, (byte) 0xF0, 0x0D }), //
            new ArcamCommand(POWER, new byte[] { 0x21, 0x01, 0x00, 0x01, (byte) 0xF0, 0x0D }), //
            new ArcamCommand(INPUT, new byte[] { 0x21, 0x01, 0x1D, 0x01, (byte) 0xF0, 0x0D }), //
            new ArcamCommand(DISPLAY_BRIGHTNESS, new byte[] { 0x21, 0x01, 0x01, 0x01, (byte) 0xF0, 0x0D }), //
            new ArcamCommand(VOLUME, new byte[] { 0x21, 0x01, 0x0D, 0x01, (byte) 0xF0, 0x0D }) //
    ));

    public static String SA30 = "SA30";

    private ArcamCommandDataFinder commandDataFinder;
    private ArcamCommandFinder commandFinder;

    public ArcamSA30() {
        this.commandDataFinder = new ArcamCommandDataFinder();
        this.commandFinder = new ArcamCommandFinder();
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
    public byte[] getInputCommand(String inputName) {
        byte[] data = new byte[] { 0x21, 0x01, 0x1D, 0x01, (byte) 0x01, 0x0D };
        data[4] = commandDataFinder.getByteFromCommandDataCode(inputName, inputCommands);

        return data;
    }

    @Override
    public byte[] getVolumeCommand(int volume) {
        byte[] data = new byte[] { 0x21, 0x01, 0x0D, 0x01, 0x2D, 0x0D };
        data[4] = data[4] = (byte) volume;

        return data;
    }

    @Override
    public String getDisplayBrightness(byte dataByte) {
        return commandDataFinder.getCommandCodeFromByte(dataByte, displaybrightnessCommands);
    }

    @Override
    public byte[] getPowerCommand(boolean on) {
        if (on == true) {
            return new byte[] { 0x21, 0x01, 0x00, 0x01, 0x01, 0x0D };
        }

        return new byte[] { 0x21, 0x01, 0x00, 0x01, 0x00, 0x0D };
    }

    @Override
    public byte[] getDisplayBrightnessCommand(String displayBrightness) {
        byte[] data = new byte[] { 0x21, 0x01, 0x01, 0x01, (byte) 0xF0, 0x0D };
        data[4] = commandDataFinder.getByteFromCommandDataCode(displayBrightness, displaybrightnessCommands);

        return data;
    }

    @Override
    public byte[] getStateCommandByte(ArcamCommandCode commandCode) {
        return commandFinder.getCommandFromCode(commandCode, commands);
    }

}
