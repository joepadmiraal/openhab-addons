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
package org.openhab.binding.eufy.internal.dto.objects;

import java.util.List;

/**
 *
 * 
 * @author Iwan Bron - Initial contribution
 */
public class State {
    private Driver driver;
    private List<Station> stations;
    private List<Device> devices;

    public List<Device> getDevices() {
        return devices;
    }

    public Driver getDriver() {
        return driver;
    }

    public List<Station> getStations() {
        return stations;
    }
}
