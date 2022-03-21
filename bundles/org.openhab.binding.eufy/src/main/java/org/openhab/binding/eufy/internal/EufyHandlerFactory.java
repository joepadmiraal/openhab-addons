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
package org.openhab.binding.eufy.internal;

import static org.openhab.binding.eufy.internal.EufyBindingConstants.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.eufy.internal.discovery.EufyDiscoveryService;
import org.openhab.binding.eufy.internal.handlers.ContainerHandler;
import org.openhab.binding.eufy.internal.handlers.DeviceHandler;
import org.openhab.binding.eufy.internal.handlers.StationHandler;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link EufyHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Iwan Bron - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.eufy", service = ThingHandlerFactory.class)
public class EufyHandlerFactory extends BaseThingHandlerFactory {
    private List<ContainerHandler> handlers = new ArrayList<>();

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_CONTAINER, THING_TYPE_STATION,
            THING_TYPE_CAMERA, THING_TYPE_DOORBELL);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_CONTAINER.equals(thingTypeUID)) {
            EufyDiscoveryService discoveryService = new EufyDiscoveryService();
            ContainerHandler handler = new ContainerHandler((Bridge) thing, discoveryService);
            Dictionary<String, Object> dictionary = new Hashtable<>();
            bundleContext.registerService(DiscoveryService.class.getName(), discoveryService, dictionary);
            handlers.add(handler);
            return handler;
        } else if (THING_TYPE_STATION.equals(thingTypeUID)) {
            return new StationHandler(thing);
        } else if (THING_TYPE_CAMERA.equals(thingTypeUID)) {
            return new DeviceHandler(thing);
        } else if (THING_TYPE_DOORBELL.equals(thingTypeUID)) {
            return new DeviceHandler(thing);
        }

        return null;
    }

    @Override
    protected void deactivate(ComponentContext componentContext) {
        for (ContainerHandler handler : handlers) {
            handler.dispose();
        }
        super.deactivate(componentContext);
    }
}
