package org.openhab.binding.arcam.internal.devices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArcamSa30Test {

    @InjectMocks
    ArcamSA30 sut;

    @Test
    public void getBalanceShouldParseNegativeValue() {
        int result = sut.getBalance((byte) 0x83);
        assertEquals(-3, result);
    }

    @Test
    public void getBalanceShouldParsePositiveValue() {
        int result = sut.getBalance((byte) 0x03);
        assertEquals(3, result);
    }

    @Test
    public void getTimeoutCounter() {
        int result = sut.getTimeoutCounter(List.of((byte) 0x00, (byte) 0x03));
        assertEquals(3, result);
    }
}
