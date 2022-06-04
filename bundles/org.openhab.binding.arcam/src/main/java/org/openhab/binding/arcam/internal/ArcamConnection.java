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
import org.openhab.binding.arcam.internal.devices.ArcamDevice;
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
    ArcamDevice device;

    protected ScheduledExecutorService scheduler;
    @Nullable
    private ArcamConnectionReader acr;
    @Nullable
    private Socket socket;
    @Nullable
    private OutputStream outputStream;

    private ArcamConnectionListener connectionListener;

    public ArcamConnection(ArcamState state, ScheduledExecutorService scheduler,
            ArcamConnectionListener connectionListener, ArcamDevice device) {
        this.state = state;
        this.scheduler = scheduler;
        this.connectionListener = connectionListener;
        this.device = device;
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
        requestState(ArcamCommandCode.SYSTEM_STATUS);
    }

    public void requestState(ArcamCommandCode commandCode) {
        byte[] data = device.getStateCommandByte(commandCode);
        if (data == null) {
            return;
        }

        sendCommand(data);
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
        byte[] data = device.getVolumeCommand(volume);

        logger.info("Sending volume byte: {}, array: {}", volume, ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setPower(boolean on) {
        byte[] data = device.getPowerCommand(on);

        logger.info("Sending power byte: {}, array: {}", on, ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setInput(String inputStr, ArcamZone zone) {
        byte[] data = device.getInputCommand(inputStr, zone);

        logger.info("Sending input byte: {}, array: {}", data[4], ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setDisplayBrightness(String displayBrightness) {
        byte[] data = device.getDisplayBrightnessCommand(displayBrightness);

        logger.info("Sending display brightness byte: {}, array: {}", data[4], ArcamUtil.bytesToHex(data));
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
            logger.info("Got power response: {}", response.data);
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

            String input = device.getInputName(response.data.get(0));
            ArcamZone zone = byteToZone(response.zn);

            logger.info("input info: {}, zone: {}", input, zone);
            if (zone == ArcamZone.MASTER) {
                state.setMasterInput(input);
            } else {
                state.setZone2Input(input);
            }
        }
        // Current display brightness
        if (response.cc == 0x01) {

            String brightness = device.getDisplayBrightness(response.data.get(0));

            logger.info("brightness info: {}", brightness);

            state.setDisplayBrightness(brightness);
        }
    }

    private ArcamZone byteToZone(byte zone) {
        if (zone == 0x01) {
            return ArcamZone.MASTER;
        }

        return ArcamZone.ZONE2;
    }

}
