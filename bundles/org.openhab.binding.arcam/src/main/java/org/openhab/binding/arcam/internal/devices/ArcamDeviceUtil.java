package org.openhab.binding.arcam.internal.devices;

import org.openhab.binding.arcam.internal.ArcamBindingConstants;
import org.openhab.binding.arcam.internal.ArcamZone;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;

public class ArcamDeviceUtil {
    public static ArcamDevice getDeviceFromThingUID(ThingUID thingUID) {
        String id = thingUID.getAsString();

        if (id.contains(ArcamSA30.SA30)) {
            return new ArcamSA30();
        }
        if (id.contains(ArcamSA20.SA20)) {
            return new ArcamSA20();
        }
        if (id.contains(ArcamAVR30.AVR30)) {
            return new ArcamAVR30();
        }
        throw new RuntimeException("Could not find an Arcam device from the thingUID: " + id);
    }

    public static ThingTypeUID getThingTypeUIDFromModelName(String modelName) {
        if (modelName.contains("SA20")) {
            return ArcamBindingConstants.SA20_THING_TYPE_UID;
        }
        if (modelName.contains("SA30")) {
            return ArcamBindingConstants.SA30_THING_TYPE_UID;
        }
        if (modelName.contains("AVR30")) {
            return ArcamBindingConstants.AVR30_THING_TYPE_UID;
        }
        return null;
    }

    public static byte zoneToByte(ArcamZone zone) {
        if (zone == ArcamZone.MASTER) {
            return 0x01;
        }

        return 0x02;
    }
}
