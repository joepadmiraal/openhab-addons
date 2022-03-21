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
package org.openhab.binding.eufy.internal.handlers;

import static org.openhab.binding.eufy.internal.EufyBindingConstants.DEVICE_PROPERTY_SERIALNUMBER;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.eufy.internal.dto.objects.Device;
import org.openhab.binding.eufy.internal.dto.objects.EufyObject;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;

/**
 *
 * @author Iwan Bron - initial contribution
 *
 */
@NonNullByDefault
public class DeviceHandler extends BaseEufyThingHander {

    private @Nullable Device device;

    public DeviceHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        update();
    }

    @Override
    void update() {
        ContainerHandler container = getContainer();
        if (container == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_UNINITIALIZED,
                    "Could not get configuration from bridge");
            return;
        }
        if (getBridgeStatus() != ThingStatus.ONLINE) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
            return;
        }
        String serialNumber = thing.getProperties().get(DEVICE_PROPERTY_SERIALNUMBER);
        if (serialNumber == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "Serial number missing");
            return;
        }
        Device localDevice = container.getDevice(serialNumber);
        if (localDevice != null) {
            updateStatus(ThingStatus.ONLINE);
            updateProperties(localDevice);
            device = localDevice;
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "Device not found in bridge");
        }
    }

    @Override
    protected @Nullable EufyObject getEufyObject() {
        return device;
    }
}
