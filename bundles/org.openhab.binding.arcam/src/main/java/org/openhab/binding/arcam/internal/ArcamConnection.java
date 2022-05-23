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
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ArcamConnection} class manages the socket connection with the amplifier.
 * It will trigger state changes when new messages are received and writes commands to the socket.
 *
 * @author Joep Admiraal - Initial contribution
 */
@NonNullByDefault
public class ArcamConnection implements ArcamConnectionReaderListener {
    private final Logger logger = LoggerFactory.getLogger(ArcamConnection.class);
    private static int PORT = 50000;

    private ArcamState state;
    protected ScheduledExecutorService scheduler;
    @Nullable
    private ArcamConnectionReader acr;
    @Nullable
    private Socket socket;
    @Nullable
    private OutputStream outputStream;

    private ArcamConnectionListener connectionListener;

    public ArcamConnection(ArcamState state, ScheduledExecutorService scheduler,
            ArcamConnectionListener connectionListener) {
        this.state = state;
        this.scheduler = scheduler;
        this.connectionListener = connectionListener;
    }

    public void connect(String hostname) throws UnknownHostException, IOException {

        logger.info("connecting to: {} {}", hostname, PORT);

        socket = new Socket(hostname, PORT);
        Socket s = socket;
        outputStream = s.getOutputStream();
        acr = new ArcamConnectionReader(s, this);
        acr.start();

        requestAllValues();
    }

    public void dispose() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (acr != null) {
                acr.dispose();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            logger.debug("{}", e.getMessage());
        }
    }

    public void requestAllValues() {
        logger.info("requestAllValues");
        getValue(ArcamCommand.GET_SYSTEM_STATUS);
    }

    public void getValue(ArcamCommand command) {
        sendCommand(command.data);
    }

    private void sendCommand(byte[] data) {
        OutputStream os = outputStream;
        if (os == null) {
            return;
        }

        try {
            logger.info("outputStream write: {}", data);
            os.write(data);
        } catch (IOException e) {
            connectionListener.onError();
        }
    }

    public void setVolume(int volume) {
        byte[] data = ArcamCommand.SET_VOLUME.data;
        data[4] = (byte) volume;

        logger.info("Sending volume byte: {}, array: {}", volume, ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setPower(int power) {
        byte[] data = ArcamCommand.SET_POWER.data;
        data[4] = (byte) power;

        logger.info("Sending power byte: {}, array: {}", power, ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setInput(String input) {
        byte[] data = ArcamCommand.SET_INPUT.data;
        ArcamInput inputEnum = ArcamInput.valueOf(input);
        data[4] = (byte) (inputEnum.ordinal() + 1);

        logger.info("Sending input byte: {}, array: {}", data[4], ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    @Override
    public void onResponse(ArcamResponse response) {
        if (response.ac != 0x00) {
            logger.warn("There is an error with the command");
            return;
        }

        // Power
        if (response.cc == 0x00) {
            if (response.data.get(0) == 1) {
                state.setPower(true);
            }
            if (response.data.get(0) == 0) {
                state.setPower(false);
            }
        }
        // Volume
        if (response.cc == 0x0D) {
            BigDecimal bd = BigDecimal.valueOf(Byte.valueOf(response.data.get(0)).intValue());
            state.setVolume(bd);
        }
        // Current input source
        if (response.cc == 0x1D) {
            Integer input = Integer.valueOf(Byte.valueOf(response.data.get(0)).intValue());
            if (input < 1) {
                logger.warn("Could not parse input response from Arcam device. Value: {}", input);
            }
            // ArcamInput.valueOf(input).ordinal();
            ArcamInput inputEnum = ArcamInput.values()[input - 1];
            logger.info("input info: {}, enum: {}", input, inputEnum);

            state.setInput(inputEnum.value);
        }
    }
}
