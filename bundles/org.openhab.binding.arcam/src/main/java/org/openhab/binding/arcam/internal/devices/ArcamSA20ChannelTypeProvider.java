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
import org.openhab.core.thing.type.ChannelType;
import org.openhab.core.thing.type.ChannelTypeProvider;
import org.openhab.core.thing.type.ChannelTypeUID;

/**
 * The {@link ArcamSA20ChannelTypeProvider} class provides the device specific channel types.
 *
 * @author Joep Admiraal - Initial contribution
 */
// @Component(service = ChannelTypeProvider.class)
public class ArcamSA20ChannelTypeProvider implements ChannelTypeProvider {

    private static final String SA20INPUT = "sa20input";

    @Override
    public Collection<@NonNull ChannelType> getChannelTypes(@Nullable Locale locale) {
        List<ChannelType> channelTypeList = new LinkedList<>();
        channelTypeList.add(getChannelType(new ChannelTypeUID(ArcamBindingConstants.BINDING_ID, SA20INPUT), locale));

        return channelTypeList;
    }

    @Override
    public @Nullable ChannelType getChannelType(ChannelTypeUID channelTypeUID, @Nullable Locale locale) {
        return null;
        // if (!channelTypeUID.getBindingId().equals(ArcamBindingConstants.BINDING_ID)) {
        // return null;
        // }
        //
        // String channelID = channelTypeUID.getId();
        // if (!channelID.equals(SA20INPUT)) {
        // return null;
        // }
        //
        // List<StateOption> options = new ArrayList<>();
        // for (SA20Input input : ArcamSA20.SA20Input.values()) {
        // options.add(new StateOption(input.name(), input.value));
        // }
        //
        // StateDescriptionFragment stateDescFrag =
        // StateDescriptionFragmentBuilder.create().withOptions(options).build();
        //
        // return ChannelTypeBuilder.state(channelTypeUID, "sa20 input", "String")
        // .withDescription("Select the input source").withStateDescriptionFragment(stateDescFrag).build();
    }
}
