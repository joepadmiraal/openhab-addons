// package org.openhab.binding.arcam.internal.devices;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// public class ArcamSA30Old implements ArcamDevice {
//
// public enum SA30Input {
// PHONO("PHONO", (byte) 0x01),
// AUX("AUX", (byte) 0x02),
// PVR("PVR", (byte) 0x03),
// AV("AV", (byte) 0x04),
// STB("STB", (byte) 0x05),
// CD("CD", (byte) 0x06),
// BD("BD", (byte) 0x07),
// SAT("SAT", (byte) 0x08),
// GAME("GAME", (byte) 0x09),
// NETUSB("NET/USB", (byte) 0x0B),
// ARC("ARC", (byte) 0x0D);
//
// public final String value;
// public final byte dataByte;
//
// private SA30Input(String value, byte dataByte) {
// this.value = value;
// this.dataByte = dataByte;
// }
// }
//
// public enum SA30DisplayBrightness {
// OFF("OFF", (byte) 0x00),
// DIM("DIM", (byte) 0x01),
// FULL("FULL", (byte) 0x02);
//
// public final String value;
// public final byte dataByte;
//
// private SA30DisplayBrightness(String value, byte dataByte) {
// this.value = value;
// this.dataByte = dataByte;
// }
// }
//
// private final Logger logger = LoggerFactory.getLogger(ArcamSA30Old.class);
//
// public static String SA30 = "SA30";
//
// @Override
// public byte getInputDataByte(String inputName) {
// SA30Input input = SA30Input.valueOf(inputName);
// return input.dataByte;
// }
//
// @Override
// public String getInputName(byte dataByte) {
//
// for (SA30Input input : SA30Input.values()) {
// if (input.dataByte == dataByte) {
// return input.name();
// }
// }
//
// logger.warn("Could not parse input response from Arcam device. Value: {}", dataByte);
// return null;
// }
//
// @Override
// public byte getDisplayBrightnessDataByte(String inputName) {
// SA30DisplayBrightness db = SA30DisplayBrightness.valueOf(inputName);
// return db.dataByte;
// }
//
// @Override
// public String getDisplayBrightness(byte dataByte) {
// for (SA30DisplayBrightness db : SA30DisplayBrightness.values()) {
// if (db.dataByte == dataByte) {
// return db.name();
// }
// }
//
// logger.warn("Could not parse display brightness response from Arcam device. Value: {}", dataByte);
// return null;
// }
// }
