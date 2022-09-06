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
package org.openhab.binding.arcam.internal.connection;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class allows thread safe access to a command that is currently being executed on the Arcam device.
 *
 * @author Joep Admiraal - Initial contribution
 */
@NonNullByDefault
public class ArcamCommandInTransit {

    private final Logger logger = LoggerFactory.getLogger(ArcamCommandInTransit.class);

    @Nullable
    private ArcamCommandCode commandInTransit;

    public void waitFor() {
        int counter = 0;
        while (hasCommand()) {
            counter++;
            if (counter > 20) {
                synchronized (this) {
                    logger.debug("Stop waiting for a command to finish, command: {}", commandInTransit);
                    commandInTransit = null;
                }
                return;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void set(ArcamCommandCode commandCode) {
        commandInTransit = commandCode;
    }

    public synchronized void finish(ArcamCommandCode commandCode) {
        if (commandCode.equals(commandInTransit)) {
            commandInTransit = null;
        }
    }

    public synchronized boolean hasCommand() {
        return commandInTransit != null;
    }
}
