package org.karnes.homebrew.bitset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BitSet8Test {

    @Test
    public void testBytes() {
        byte zero = (byte) 0;
        BitSet8 zeroBitSet = BitSet8.fromByte(zero);
        zeroBitSet.forEach(val -> assertFalse(val));
        assertEquals(zero, zeroBitSet.toByte());

        byte one = (byte) 1;
        BitSet8 oneBitSet = BitSet8.fromByte(one);
        assertTrue(oneBitSet.get(0));
        for (int i = 1; i < oneBitSet.size(); i++) {
            assertFalse(oneBitSet.get(i));
        }
        assertEquals(one, oneBitSet.toByte());

        byte minusOne = (byte) -1;
        BitSet8 minusOneBitSet = BitSet8.fromByte(minusOne);
        minusOneBitSet.forEach(val -> assertTrue(val));
        assertEquals(minusOne, minusOneBitSet.toByte());


        byte min = Byte.MIN_VALUE;
        BitSet8 minBitSet = BitSet8.fromByte(min);
        assertTrue(minBitSet.get(7));
        for (int i = 0; i < minBitSet.size() - 1; i++) {
            assertFalse(minBitSet.get(i));
        }
        assertEquals(min, minBitSet.toByte());


        byte max = Byte.MAX_VALUE;
        BitSet8 maxBitSet = BitSet8.fromByte(max);
        assertFalse(maxBitSet.get(7));
        for (int i = 0; i < maxBitSet.size() - 1; i++) {
            assertTrue(maxBitSet.get(i));
        }
        assertEquals(max, maxBitSet.toByte());
    }


    @Test
    public void testCopy() {
        BitSet8 zeroBitSet = new BitSet8();
        assertEquals((byte) 0, zeroBitSet.copy().toByte());
        assertEquals(zeroBitSet, zeroBitSet.copy());

        //If we change the copy, the original should not be changed
        BitSet8 negated = zeroBitSet.copy().negate();
        assertNotEquals(negated, zeroBitSet);
        assertEquals((short) 0, zeroBitSet.toByte());
    }

    @Test
    public void testZeroNibbles() {
        byte zero = (byte) 0;
        BitSet8 bitSet = BitSet8.fromByte(zero);
        BitSet4 lowerNibble = bitSet.getLowerNibble();
        lowerNibble.forEach(val -> assertFalse(val));
        BitSet4 upperNibble = bitSet.getUpperNibble();
        upperNibble.forEach(val -> assertFalse(val));
        BitSet4[] nibbles = bitSet.toNibbles();
        assertEquals(upperNibble, nibbles[0]);
        assertEquals(lowerNibble, nibbles[1]);
    }

    @Test
    public void testMaxNibbles() {
        byte max = Byte.MAX_VALUE;
        BitSet8 bitSet = BitSet8.fromByte(max);
        BitSet4 lowerNibble = bitSet.getLowerNibble();
        lowerNibble.forEach(val -> assertTrue(val));
        BitSet4 upperNibble = bitSet.getUpperNibble();
        upperNibble.forEach(val -> assertTrue(val));
        BitSet4[] nibbles = bitSet.toNibbles();
        assertEquals(upperNibble, nibbles[0]);
        assertEquals(lowerNibble, nibbles[1]);
    }

    @Test
    public void testMixNibbles() {
        BitSet8 bitSet = new BitSet8("1010 0110");
        BitSet4 lowerNibble = bitSet.getLowerNibble();
        assertEquals("[0110]", lowerNibble.toString());
        BitSet4 upperNibble = bitSet.getUpperNibble();
        assertEquals("[1010]", upperNibble.toString());
        BitSet4[] nibbles = bitSet.toNibbles();
        assertEquals(upperNibble, nibbles[0]);
        assertEquals(lowerNibble, nibbles[1]);
    }


}