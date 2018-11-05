package org.karnes.homebrew.emulator;

import org.junit.jupiter.api.Test;
import org.karnes.homebrew.bitset.FixedBitSet;

import static org.junit.jupiter.api.Assertions.*;

class ConditionCodeTest {

    @Test
    public void testConditionCodeOff() {
        FixedBitSet bits = new FixedBitSet(4);
        ConditionCode cc = new ConditionCode(bits);
        assertEquals(cc, new ConditionCode(false, false, false, false));
        assertFalse(cc.isCarry());
        assertFalse(cc.isOverflow());
        assertFalse(cc.isSign());
        assertFalse(cc.isZero());
        assertEquals(bits, cc.toBitSet());
    }

    @Test
    public void testConditionCodeOn() {
        FixedBitSet bits = new FixedBitSet("1110");
        ConditionCode cc = new ConditionCode(bits);
        assertEquals(cc, new ConditionCode(true, true, true, false));
        assertTrue(cc.isCarry());
        assertTrue(cc.isOverflow());
        assertTrue(cc.isSign());
        assertFalse(cc.isZero());
        assertEquals(bits, cc.toBitSet());
    }


}