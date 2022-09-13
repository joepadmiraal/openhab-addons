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

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.Test;
import org.openhab.binding.eufy.internal.dto.AsyncResultMessage;
import org.openhab.binding.eufy.internal.dto.EventMessage;
import org.openhab.binding.eufy.internal.dto.Message;
import org.openhab.binding.eufy.internal.dto.MotionDetectedEvent;
import org.openhab.binding.eufy.internal.dto.PersonDetectedEvent;
import org.openhab.binding.eufy.internal.dto.PropertyChangedEvent;
import org.openhab.binding.eufy.internal.dto.StatusMessage;
import org.openhab.binding.eufy.internal.dto.VersionMessage;
import org.openhab.binding.eufy.internal.dto.objects.Device;
import org.openhab.binding.eufy.internal.dto.objects.Doorbell;

/**
 *
 * @author Iwan Bron - Initial contribution
 *
 */
@NonNullByDefault
public class JsonParserTest {
    @Test
    public void testParseSessionOpened() throws Exception {
        Message message = MessageHelper.parse(Files.readString(Paths.get("src/test/resources/session_opened.json")));
        assertTrue(message instanceof VersionMessage);
        VersionMessage version = (VersionMessage) message;
        assertEquals(8, version.getMaxSchemaVersion());
        assertEquals(0, version.getMinSchemaVersion());
    }

    @Test
    public void testParseListeningResponse() throws Exception {
        Message message = MessageHelper.parse(Files.readString(Paths.get("src/test/resources/status_response.json")));
        assertTrue(message instanceof StatusMessage);
        StatusMessage status = (StatusMessage) message;
        assertNotNull(status.getResult());
        assertNotNull(status.getResult().getState());
        assertEquals(3, status.getResult().getState().getDevices().size());
        assertEquals(2, status.getResult().getState().getStations().size());

        int doorbellCount = 0;
        for (Device device : status.getResult().getState().getDevices()) {
            if (device instanceof Doorbell) {
                doorbellCount++;
            }
        }
        assertEquals(1, doorbellCount);
    }

    @Test
    public void testParsePRopertyChanged() throws Exception {
        Message message = MessageHelper
                .parse(Files.readString(Paths.get("src/test/resources/property_ringing_changed.json")));
        assertTrue(message instanceof EventMessage);
        EventMessage event = (EventMessage) message;
        assertTrue(event.getEvent() instanceof PropertyChangedEvent);
        assertEquals(Boolean.TRUE.toString(), ((PropertyChangedEvent) event.getEvent()).getValue());
    }

    @Test
    public void testParseMotionEvent() throws Exception {
        Message message = MessageHelper
                .parse(Files.readString(Paths.get("src/test/resources/motion_detected_event.json")));
        assertTrue(message instanceof EventMessage);
        EventMessage event = (EventMessage) message;
        assertTrue(event.getEvent() instanceof MotionDetectedEvent);
        MotionDetectedEvent motion = (MotionDetectedEvent) event.getEvent();
        assertTrue(motion.isState());
    }

    @Test
    public void testParsePersonEvent() throws Exception {
        Message message = MessageHelper
                .parse(Files.readString(Paths.get("src/test/resources/person_detected_event.json")));
        assertTrue(message instanceof EventMessage);
        EventMessage event = (EventMessage) message;
        assertTrue(event.getEvent() instanceof PersonDetectedEvent);
        PersonDetectedEvent person = (PersonDetectedEvent) event.getEvent();
        assertTrue(person.isState());
        assertEquals("Unknown", person.getPerson());
    }

    @Test
    public void testParseLivestreamingResult() throws Exception {
        Message message = MessageHelper
                .parse(Files.readString(Paths.get("src/test/resources/livestreaming_result.json")));
        assertTrue(message instanceof AsyncResultMessage);
        AsyncResultMessage result = (AsyncResultMessage) message;
        assertEquals(true, result.getResult());
    }
}
