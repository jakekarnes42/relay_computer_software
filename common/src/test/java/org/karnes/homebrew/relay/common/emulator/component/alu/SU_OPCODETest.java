package org.karnes.homebrew.relay.common.emulator.component.alu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.alu.shiftunit.SU_OPCODE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SU_OPCODETest {

    @Test
    void testROR() {
        Assertions.assertEquals(SU_OPCODE.ROR, SU_OPCODE.fromBitSet(new FixedBitSet("100")));
    }

    @Test
    void testROL() {
        assertEquals(SU_OPCODE.ROL, SU_OPCODE.fromBitSet(new FixedBitSet("101")));
    }

    @Test
    void testRCR() {
        assertEquals(SU_OPCODE.RCR, SU_OPCODE.fromBitSet(new FixedBitSet("110")));
    }

    @Test
    void testRCL() {
        assertEquals(SU_OPCODE.RCL, SU_OPCODE.fromBitSet(new FixedBitSet("111")));
    }

    @Test
    void testOFF() {
        //Test valid opcodes
        assertEquals(SU_OPCODE.OFF, SU_OPCODE.fromBitSet(new FixedBitSet("000")));
        assertEquals(SU_OPCODE.OFF, SU_OPCODE.fromBitSet(new FixedBitSet("001")));
        assertEquals(SU_OPCODE.OFF, SU_OPCODE.fromBitSet(new FixedBitSet("010")));
        assertEquals(SU_OPCODE.OFF, SU_OPCODE.fromBitSet(new FixedBitSet("011")));
    }

    @Test
    void testInvalid() {
        //too small
        assertThrows(IllegalArgumentException.class, () -> SU_OPCODE.fromBitSet(new FixedBitSet("0")));

        //too large
        assertThrows(IllegalArgumentException.class, () -> SU_OPCODE.fromBitSet(new FixedBitSet("1111")));
    }
}
