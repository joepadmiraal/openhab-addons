package org.openhab.binding.arcam.internal;

public class ArcamCommandData {
    public String code;
    public String name;
    public byte dataByte;

    public ArcamCommandData(String code, String name, byte dataByte) {
        this.code = code;
        this.name = name;
        this.dataByte = dataByte;
    }

    public ArcamCommandData(String code, byte dataByte) {
        this.code = code;
        this.name = code;
        this.dataByte = dataByte;
    }
}
