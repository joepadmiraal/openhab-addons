package org.openhab.binding.arcam.internal;

public class ArcamCommand {
    public ArcamCommandCode code;
    public byte[] data;

    public ArcamCommand(ArcamCommandCode code, byte[] data) {
        this.code = code;
        this.data = data;
    }
}
