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
 * The {@link ArcamConnection} class manages the socket connection with the device.
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
    @Nullable
    private ArcamCommandCode nowPlayingInTransit;

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

        Socket s = new Socket(hostname, PORT);
        socket = s;
        outputStream = s.getOutputStream();
        ArcamConnectionReader acr = new ArcamConnectionReader(s, this);
        acr.start();
        this.acr = acr;

        requestAllValues();
    }

    public void dispose() {
        try {
            OutputStream os = outputStream;
            if (os != null) {
                os.close();
            }
            ArcamConnectionReader acr = this.acr;
            if (acr != null) {
                acr.dispose();
            }
            Socket s = socket;
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {
            logger.debug("{}", e.getMessage());
        }
    }

    public void reboot() {
        byte[] data = device.getRebootCommand();

        logger.info("Sending reboot array: {}", ArcamUtil.bytesToHex(data));
        sendCommand(data);
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

        if (data[2] == 0x64) {
            nowPlayingInTransit = commandCode;
        }

        sendCommand(data);
    }

    public void setBalance(int balance, ArcamZone zone) {
        byte[] data = device.getBalanceCommand(balance, zone);

        logger.info("Sending balance byte: {}, array: {}, zone: {}", balance, ArcamUtil.bytesToHex(data), zone);
        sendCommand(data);
    }

    public void setDacFilter(String dacFilter) {
        byte[] data = device.getDacFilterCommand(dacFilter);

        logger.info("Sending dacFilter byte: {}, array: {}", data[4], ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setDisplayBrightness(String displayBrightness) {
        byte[] data = device.getDisplayBrightnessCommand(displayBrightness);

        logger.info("Sending display brightness byte: {}, array: {}", data[4], ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setInput(String inputStr, ArcamZone zone) {
        byte[] data = device.getInputCommand(inputStr, zone);

        logger.info("Sending input byte: {}, array: {}", data[4], ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setPower(boolean on, ArcamZone zone) {
        byte[] data = device.getPowerCommand(on, zone);

        logger.info("Sending power byte: {}, array: {}, zone: {}", on, ArcamUtil.bytesToHex(data), zone);
        sendCommand(data);
    }

    public void setRoomEqualisation(String eqStr, ArcamZone zone) {
        byte[] data = device.getRoomEqualisationCommand(eqStr, zone);

        logger.info("Sending eq byte: {}, array: {}", data[4], ArcamUtil.bytesToHex(data));
        sendCommand(data);
    }

    public void setVolume(int volume, ArcamZone zone) {
        byte[] data = device.getVolumeCommand(volume, zone);

        logger.info("Sending volume byte: {}, array: {}, zone: {}", volume, ArcamUtil.bytesToHex(data), zone);
        sendCommand(data);
    }

    @Override
    public void onResponse(ArcamResponse response) {
        if (response.ac != 0x00) {
            logger.warn("There is an error with the command");
            return;
        }
        // Balance
        if (response.cc == 0x3B) {
            int balance = device.getBalance(response.data.get(0));
            state.setMasterBalance(balance);
        }
        // DAC filter
        if (response.cc == 0x61) {
            String dacFilter = device.getDacFilter(response.data.get(0));
            logger.info("Got DAC filter: {}, {}", ArcamUtil.bytesToHex(response.data), dacFilter);
            state.setDacFilter(dacFilter);
        }
        // DC offset
        if (response.cc == 0x51) {
            logger.info("Got DC offset response: {}", response.data);
            boolean dc = device.getBoolean(response.data.get(0));
            state.setDcOffset(dc);
        }
        // Direct mode
        if (response.cc == 0x0F) {
            logger.info("Got direct mode response: {}", response.data);
            if (response.data.size() > 1) {
                boolean directMode = device.getBoolean(response.data.get(1));
                state.setMasterDirectMode(directMode);
            }
        }
        // Display brightness
        if (response.cc == 0x01) {
            String brightness = device.getDisplayBrightness(response.data.get(0));

            logger.info("brightness info: {}", brightness);

            state.setDisplayBrightness(brightness);
        }
        // Headphones
        if (response.cc == 0x02) {
            logger.info("Got headphones response: {}", response.data);
            boolean headphones = device.getBoolean(response.data.get(0));
            state.setHeadphones(headphones);
        }
        // Incoming audio sample rate
        if (response.cc == 0x44) {
            String sampleRate = device.getIncomingSampleRate(response.data.get(0));
            logger.info("Got incomingSampleRateresponse: {}, {}", ArcamUtil.bytesToHex(response.data), sampleRate);
            state.setIncomingSampleRate(sampleRate);
        }
        // Input detect
        if (response.cc == 0x5A) {
            logger.info("Got Input detetc response: {}", response.data);
            boolean inputDetect = device.getBoolean(response.data.get(0));
            ArcamZone zone = byteToZone(response.zn);
            if (zone == ArcamZone.MASTER) {
                state.setMasterInputDetect(inputDetect);
            }
        }
        // Input source
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
        // Lifter temperature
        if (response.cc == 0x56) {
            int temperature = device.getTemperature(response.data, 0);
            logger.info("Got Lifter temperature: {}, value: {}", ArcamUtil.bytesToHex(response.data), temperature);
            state.setLifterTemperature(temperature);
        }
        // Mute
        if (response.cc == 0x0E) {
            logger.info("Got mute response: {}", response.data);
            boolean mute = device.getMute(response.data.get(0));
            ArcamZone zone = byteToZone(response.zn);
            if (zone == ArcamZone.MASTER) {
                state.setMasterMute(mute);
            } else {
                state.setZone2Mute(mute);
            }
        }
        // Now Playing information
        if (response.cc == 0x64) {
            logger.info("Got now playing response: {}", response.data);
            ArcamCommandCode nowPlayingCommandCode = nowPlayingInTransit;
            if (nowPlayingCommandCode != null) {
                String value = ArcamUtil.byteListToUTF(response.data);

                switch (nowPlayingCommandCode) {
                    case MASTER_NOW_PLAYING_ALBUM:
                        state.setMasterNowPlayingAlbum(value);
                        break;

                    case MASTER_NOW_PLAYING_APPLICATION:
                        state.setMasterNowPlayingApplication(value);
                        break;

                    case MASTER_NOW_PLAYING_ARTIST:
                        state.setMasterNowPlayingArtist(value);
                        break;

                    case MASTER_NOW_PLAYING_AUDIO_ENCODER:
                        String audioEncoder = device.getNowPlayingEncoder(response.data.get(0));
                        state.setMasterNowPlayingAudioEncoder(audioEncoder);
                        break;

                    case MASTER_NOW_PLAYING_SAMPLE_RATE:
                        String sampleRate = device.getNowPlayingSampleRate(response.data.get(0));
                        state.setMasterNowPlayingSampleRate(sampleRate);
                        break;

                    case MASTER_NOW_PLAYING_TITLE:
                        state.setMasterNowPlayingTitle(value);
                        break;
                    default:
                        break;
                }
                nowPlayingInTransit = null;
            } else {
                ArcamNowPlaying nowPlaying = device.setNowPlaying(response.data);
                if (nowPlaying != null) {
                    state.setMasterNowPlayingAlbum(nowPlaying.album);
                    state.setMasterNowPlayingTitle(nowPlaying.track);
                    state.setMasterNowPlayingArtist(nowPlaying.artist);

                    state.setMasterNowPlayingApplication(nowPlaying.application);
                    state.setMasterNowPlayingSampleRate(nowPlaying.sampleRate);
                    state.setMasterNowPlayingAudioEncoder(nowPlaying.audioEncoder);
                }
            }
        }
        // Output temperature
        if (response.cc == 0x57) {
            int temperature = device.getTemperature(response.data, 0);
            logger.info("Got Output temperature: {}, value: {}", ArcamUtil.bytesToHex(response.data), temperature);
            state.setOutputTemperature(temperature);
        }
        // Power
        if (response.cc == 0x00) {
            logger.info("Got power response: {}", response.data);
            boolean power = device.getBoolean(response.data.get(0));
            state.setMasterPower(power);
        }
        // Room Equalisation
        if (response.cc == 0x37) {

            String eq = device.getRoomEqualisation(response.data.get(0));
            ArcamZone zone = byteToZone(response.zn);

            logger.info("room eq info: {}, zone: {}", eq, zone);
            state.setMasterRoomEqualisation(eq);
        }
        // Short circuit status
        if (response.cc == 0x52) {
            logger.info("Got Short circuit status response: {}", response.data);
            boolean shortCircuit = device.getBoolean(response.data.get(0));
            state.setMasterShortCircuit(shortCircuit);
        }
        // SoftwareVersion
        if (response.cc == 0x04) {
            String version = device.getSoftwareVersion(response.data);
            logger.info("Got SoftwareVersion response: {}, {}", ArcamUtil.bytesToHex(response.data), version);
            state.setSoftwareVersion(version);
        }
        // Timeout counter
        if (response.cc == 0x55) {
            int counter = device.getTimeoutCounter(response.data);
            logger.info("Got Timeout counter response: {}, {}", ArcamUtil.bytesToHex(response.data), counter);
            state.setTimeoutCounter(counter);
        }
        // Volume
        if (response.cc == 0x0D) {
            BigDecimal bd = BigDecimal.valueOf(Byte.valueOf(response.data.get(0)).intValue());
            state.setMasterVolume(bd);
        }
    }

    private ArcamZone byteToZone(byte zone) {
        if (zone == 0x01) {
            return ArcamZone.MASTER;
        }

        return ArcamZone.ZONE2;
    }

    private void sendCommand(byte[] data) {
        OutputStream os = outputStream;
        if (os == null) {
            return;
        }

        try {
            logger.info("outputStream write: {}", ArcamUtil.bytesToHex(data));
            os.write(data);
        } catch (IOException e) {
            connectionListener.onError();
        }
    }
}
