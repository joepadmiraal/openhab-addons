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
package org.openhab.binding.arcam.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link ArcamResponse} POJO holds the data that is returned from the device.
 *
 * @author Joep Admiraal - Initial contribution
 */
@NonNullByDefault
public class ArcamResponse {
    public byte zn;
    public byte cc;
    public byte ac;
    public int dl;
    public List<Byte> data = new ArrayList<>();
}
