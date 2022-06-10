package org.openhab.binding.arcam.internal.devices;

import java.util.ArrayList;
import java.util.List;

import org.openhab.binding.arcam.internal.ArcamCommandData;

public class ArcamDeviceConstants {
    public static List<ArcamCommandData> ROOM_EQ = new ArrayList<>(List.of( //
            new ArcamCommandData("ROOM_EQ_OFF", (byte) 0x00), //
            new ArcamCommandData("ROOM_EQ_1", (byte) 0x01), //
            new ArcamCommandData("ROOM_EQ_2", (byte) 0x02), //
            new ArcamCommandData("ROOM_EQ_3", (byte) 0x03) //
    ));
}
