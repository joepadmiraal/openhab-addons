package org.openhab.binding.arcam.internal.devices;

import java.util.ArrayList;
import java.util.List;

import org.openhab.binding.arcam.internal.ArcamCommandData;
import org.openhab.binding.arcam.internal.ArcamCommandDataFinder;

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

    public static String SA30 = "SA30";

    private ArcamCommandDataFinder commandDataFinder;

    public ArcamSA30() {
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
    public byte getDisplayBrightnessDataByte(String displayBrightness) {
        return commandDataFinder.getByteFromCommandDataCode(displayBrightness, displaybrightnessCommands);
    }

    @Override
    public String getDisplayBrightness(byte dataByte) {

        return commandDataFinder.getCommandCodeFromByte(dataByte, displaybrightnessCommands);
    }

}
