package org.karnes.homebrew.relay.common.emulator.component.alu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.alu.logicunit.LU_OPCODE;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LU_OPCODETest {

    @Test
    void testXOR() {
        Assertions.assertEquals(LU_OPCODE.XOR, LU_OPCODE.fromBitSet(new FixedBitSet("100")));
    }

    @Test
    void testOR() {
        assertEquals(LU_OPCODE.OR, LU_OPCODE.fromBitSet(new FixedBitSet("101")));
    }

    @Test
    void testAND() {
        assertEquals(LU_OPCODE.AND, LU_OPCODE.fromBitSet(new FixedBitSet("110")));
    }

    @Test
    void testNOT() {
        assertEquals(LU_OPCODE.NOT, LU_OPCODE.fromBitSet(new FixedBitSet("111")));
    }

    @Test
    void testOFF() {
        //Test valid opcodes
        assertEquals(LU_OPCODE.OFF, LU_OPCODE.fromBitSet(new FixedBitSet("000")));
        assertEquals(LU_OPCODE.OFF, LU_OPCODE.fromBitSet(new FixedBitSet("001")));
        assertEquals(LU_OPCODE.OFF, LU_OPCODE.fromBitSet(new FixedBitSet("010")));
        assertEquals(LU_OPCODE.OFF, LU_OPCODE.fromBitSet(new FixedBitSet("011")));
    }

    @Test
    void testInvalid() {
        //too small
        assertThrows(IllegalArgumentException.class, () -> LU_OPCODE.fromBitSet(new FixedBitSet("0")));

        //too large
        assertThrows(IllegalArgumentException.class, () -> LU_OPCODE.fromBitSet(new FixedBitSet("1111")));
    }
}
