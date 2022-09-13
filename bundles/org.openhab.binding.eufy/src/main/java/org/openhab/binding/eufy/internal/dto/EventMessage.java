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
package org.openhab.binding.eufy.internal.dto;

/**
 * An EventMessage contains an {@link Event} and is sent from the container if a property changed (like motion detected
 * or led status)
 *
 * @author Iwan Bron - Initial contribution
 *
 */
public class EventMessage extends Message {
    private Event event;

    public Event getEvent() {
        return event;
    }
}
