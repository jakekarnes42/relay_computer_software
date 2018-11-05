package org.karnes.homebrew.relay.common.emulator.component.alu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.alu.arithmeticunit.AU_OPCODE;

import static org.junit.jupiter.api.Assertions.*;

public class AU_OPCODETest {

    @Test
    void testADD() {
        Assertions.assertEquals(AU_OPCODE.ADD, AU_OPCODE.fromBitSet(new FixedBitSet("100")));
    }

    @Test
    void testINC() {
        assertEquals(AU_OPCODE.INC, AU_OPCODE.fromBitSet(new FixedBitSet("101")));
    }

    @Test
    void testSUB() {
        assertEquals(AU_OPCODE.SUB, AU_OPCODE.fromBitSet(new FixedBitSet("110")));
    }

    @Test
    void testDEC() {
        assertEquals(AU_OPCODE.DEC, AU_OPCODE.fromBitSet(new FixedBitSet("111")));
    }

    @Test
    void testOFF() {
        //Test valid opcodes
        assertEquals(AU_OPCODE.OFF, AU_OPCODE.fromBitSet(new FixedBitSet("000")));
        assertEquals(AU_OPCODE.OFF, AU_OPCODE.fromBitSet(new FixedBitSet("001")));
        assertEquals(AU_OPCODE.OFF, AU_OPCODE.fromBitSet(new FixedBitSet("010")));
        assertEquals(AU_OPCODE.OFF, AU_OPCODE.fromBitSet(new FixedBitSet("011")));
    }

    @Test
    void testInvalid() {
        //too small
        assertThrows(IllegalArgumentException.class, () -> AU_OPCODE.fromBitSet(new FixedBitSet("0")));

        //too large
        assertThrows(IllegalArgumentException.class, () -> AU_OPCODE.fromBitSet(new FixedBitSet("1111")));
    }
}
