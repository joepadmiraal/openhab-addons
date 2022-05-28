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

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NonNullByDefault
public class ArcamConnectionReader extends Thread {
    private Socket socket;
    private final Logger logger = LoggerFactory.getLogger(ArcamConnectionReader.class);
    private ArcamConnectionReaderListener listener;

    private ReentrantLock mutex = new ReentrantLock();
    private boolean shouldStop;

    public ArcamConnectionReader(Socket socket, ArcamConnectionReaderListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    private boolean shouldStop() {
        boolean result = false;
        mutex.lock();
        if (shouldStop) {
            result = true;
        }
        mutex.unlock();

        if (result == true) {
            logger.info("Arcam Connection detected should stop");
        }
        return result;
    }

    @Override
    public void run() {
        ArcamResponseHandler responseHandler = new ArcamResponseHandler();
        try {
            InputStream input = socket.getInputStream();

            while (!shouldStop()) {
                byte responseData[] = new byte[1];
                int size = input.read(responseData);
                // logger.info("responseData: {}, size: {}", ArcamUtil.bytesToHex(responseData), size);

                for (int j = 0; j < responseData.length; j++) {
                    ArcamResponse response = responseHandler.parseByte(responseData[j]);
                    if (response != null) {
                        listener.onResponse(response);
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void dispose() {
        // TODO: should we wait until the loop is finished, or simply catch the socketException?
        mutex.lock();
        shouldStop = true;
        mutex.unlock();
    }
}
