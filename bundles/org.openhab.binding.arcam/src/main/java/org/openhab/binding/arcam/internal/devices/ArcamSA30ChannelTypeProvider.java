package org.openhab.binding.arcam.internal.devices;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.arcam.internal.ArcamBindingConstants;
import org.openhab.binding.arcam.internal.ArcamCommandDataFinder;
import org.openhab.core.thing.type.ChannelType;
import org.openhab.core.thing.type.ChannelTypeProvider;
import org.openhab.core.thing.type.ChannelTypeUID;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ChannelTypeProvider.class)
public class ArcamSA30ChannelTypeProvider implements ChannelTypeProvider {
    private final Logger logger = LoggerFactory.getLogger(ArcamSA30ChannelTypeProvider.class);
    public static final String SA30_INPUT = "sa30Input";
    public static final String SA30_DISPLAY_BRIGHTNESS = "sa30DisplayBrightness";

    @Override
    public Collection<@NonNull ChannelType> getChannelTypes(@Nullable Locale locale) {
        List<ChannelType> channelTypeList = new LinkedList<>();
        logger.info("getChannleType list from arcam");
        channelTypeList.add(getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, SA30_INPUT), locale));
        channelTypeList.add(
                getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, SA30_DISPLAY_BRIGHTNESS), locale));

        return channelTypeList;
    }

    @Override
    public @Nullable ChannelType getChannelType(ChannelTypeUID channelTypeUID, @Nullable Locale locale) {
        if (!channelTypeUID.getBindingId().equals(ArcamBindingConstants.BINDING_ID)) {
            return null;
        }

        String channelID = channelTypeUID.getId();

        if (channelID.equals(SA30_INPUT)) {
            return ArcamCommandDataFinder.generateStringOptionChannelType( //
                    channelTypeUID, //
                    "Input", //
                    "Select the input source", //
                    ArcamSA30.inputCommands); //
        }

        if (channelID.equals(SA30_DISPLAY_BRIGHTNESS)) {
            return ArcamCommandDataFinder.generateStringOptionChannelType( //
                    channelTypeUID, //
                    "Display brightness", //
                    "Select display brightness", //
                    ArcamSA30.displaybrightnessCommands); //
        }

        return null;
    }

}
