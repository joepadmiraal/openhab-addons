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

@Component(service = ChannelTypeProvider.class)
public class ArcamAVR30ChannelTypeProvider implements ChannelTypeProvider {

    public static final String AVR30_DISPLAY_BRIGHTNESS = "avr30DisplayBrightness";
    public static final String AVR30_MASTER_INPUT = "avr30MasterInput";
    public static final String AVR30_ZONE2_INPUT = "avr30Zone2Input";

    @Override
    public Collection<@NonNull ChannelType> getChannelTypes(@Nullable Locale locale) {
        List<ChannelType> channelTypeList = new LinkedList<>();
        channelTypeList.add(
                getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, AVR30_DISPLAY_BRIGHTNESS), locale));
        channelTypeList
                .add(getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, AVR30_MASTER_INPUT), locale));
        channelTypeList
                .add(getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, AVR30_ZONE2_INPUT), locale));

        return channelTypeList;
    }

    @Override
    public @Nullable ChannelType getChannelType(ChannelTypeUID channelTypeUID, @Nullable Locale locale) {
        if (!channelTypeUID.getBindingId().equals(ArcamBindingConstants.BINDING_ID)) {
            return null;
        }

        String channelID = channelTypeUID.getId();

        if (channelID.equals(AVR30_DISPLAY_BRIGHTNESS)) {
            return ArcamCommandDataFinder.generateStringOptionChannelType( //
                    channelTypeUID, //
                    "Display brightness", //
                    "Select display brightness", //
                    ArcamAVR30.displaybrightnessCommands); //
        }

        if (channelID.equals(AVR30_MASTER_INPUT)) {
            return ArcamCommandDataFinder.generateStringOptionChannelType( //
                    channelTypeUID, //
                    "xMaster Input", //
                    "Select the input source", //
                    ArcamAVR30.inputCommands); //
        }

        if (channelID.equals(AVR30_ZONE2_INPUT)) {
            return ArcamCommandDataFinder.generateStringOptionChannelType( //
                    channelTypeUID, //
                    "Zone2 Input", //
                    "Select the input source", //
                    ArcamAVR30.inputCommands); //
        }

        return null;
    }

}
