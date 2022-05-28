package org.openhab.binding.arcam.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.thing.type.ChannelType;
import org.openhab.core.thing.type.ChannelTypeBuilder;
import org.openhab.core.thing.type.ChannelTypeUID;
import org.openhab.core.types.StateDescriptionFragment;
import org.openhab.core.types.StateDescriptionFragmentBuilder;
import org.openhab.core.types.StateOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcamCommandDataFinder {
    private final Logger logger = LoggerFactory.getLogger(ArcamCommandDataFinder.class);

    public ArcamCommandData getCommandDataFromCode(String code, List<ArcamCommandData> list) {
        for (ArcamCommandData commandData : list) {
            if (commandData.code.equals(code)) {
                return commandData;
            }
        }

        logger.warn("Could not find ArcamCommandData for code: {}", code);
        return null;
    }

    public ArcamCommandData getCommandDataFromByte(byte dataByte, List<ArcamCommandData> list) {
        for (ArcamCommandData commandData : list) {
            if (commandData.dataByte == dataByte) {
                return commandData;
            }
        }

        logger.warn("Could not find ArcamCommandData for dataByte: {}", dataByte);
        return null;
    }

    public String getCommandCodeFromByte(byte dataByte, List<ArcamCommandData> list) {
        ArcamCommandData commandData = getCommandDataFromByte(dataByte, list);
        if (commandData == null) {
            return "";
        }
        return commandData.code;
    }

    public byte getByteFromCommandDataCode(String code, List<ArcamCommandData> list) {
        ArcamCommandData commandData = getCommandDataFromCode(code, list);
        if (commandData == null) {
            return 0;
        }
        return commandData.dataByte;
    }

    public static @Nullable ChannelType generateStringOptionChannelType(ChannelTypeUID channelTypeUID, String label,
            String description, List<ArcamCommandData> list) {
        List<StateOption> options = new ArrayList<>();

        for (ArcamCommandData db : list) {
            options.add(new StateOption(db.code, db.name));
        }

        StateDescriptionFragment stateDescFrag = StateDescriptionFragmentBuilder.create().withOptions(options).build();

        return ChannelTypeBuilder.state(channelTypeUID, label, "String").withDescription(description)
                .withStateDescriptionFragment(stateDescFrag).build();
    }

}
