/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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

/**
 * This class provides the device specific channel types.
 *
 * @author Joep Admiraal - Initial contribution
 */
@Component(service = ChannelTypeProvider.class)
public class ArcamSA30ChannelTypeProvider implements ChannelTypeProvider {
    private final Logger logger = LoggerFactory.getLogger(ArcamSA30ChannelTypeProvider.class);

    public static final String SA30_DAC_FILTER = "sa30DacFilter";
    public static final String SA30_DISPLAY_BRIGHTNESS = "sa30DisplayBrightness";
    public static final String SA30_INPUT = "sa30Input";

    @Override
    public Collection<@NonNull ChannelType> getChannelTypes(@Nullable Locale locale) {
        List<ChannelType> channelTypeList = new LinkedList<>();
        logger.info("getChannleType list from arcam");

        channelTypeList
                .add(getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, SA30_DAC_FILTER), locale));
        channelTypeList.add(
                getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, SA30_DISPLAY_BRIGHTNESS), locale));
        channelTypeList.add(getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, SA30_INPUT), locale));

        return channelTypeList;
    }

    @Override
    public @Nullable ChannelType getChannelType(ChannelTypeUID channelTypeUID, @Nullable Locale locale) {
        if (!channelTypeUID.getBindingId().equals(ArcamBindingConstants.BINDING_ID)) {
            return null;
        }

        String channelID = channelTypeUID.getId();

        if (channelID.equals(SA30_DAC_FILTER)) {
            return ArcamCommandDataFinder.generateStringOptionChannelType( //
                    channelTypeUID, //
                    "DAC filter", //
                    "Select DAC filter", //
                    ArcamSA30.dacFilterCommands); //
        }

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
