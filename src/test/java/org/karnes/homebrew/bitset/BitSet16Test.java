package org.karnes.homebrew.bitset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BitSet16Test {

    @Test
    public void testMyShorts() {
        short zero = (short) 0;
        BitSet16 zeroBitSet = BitSet16.fromShort(zero);
        zeroBitSet.forEach(val -> assertFalse(val));
        assertEquals(zero, zeroBitSet.toShort());

        short one = (short) 1;
        BitSet16 oneBitSet = BitSet16.fromShort(one);
        assertTrue(oneBitSet.get(0));
        for (int i = 1; i < oneBitSet.size(); i++) {
            assertFalse(oneBitSet.get(i));
        }
        assertEquals(one, oneBitSet.toShort());

        short minusOne = (short) -1;
        BitSet16 minusOneBitSet = BitSet16.fromShort(minusOne);
        minusOneBitSet.forEach(val -> assertTrue(val));
        assertEquals(minusOne, minusOneBitSet.toShort());


        short min = Short.MIN_VALUE;
        BitSet16 minBitSet = BitSet16.fromShort(min);
        assertTrue(minBitSet.get(15));
        for (int i = 0; i < minBitSet.size() - 1; i++) {
            assertFalse(minBitSet.get(i));
        }
        assertEquals(min, minBitSet.toShort());


        short max = Short.MAX_VALUE;
        BitSet16 maxBitSet = BitSet16.fromShort(max);
        assertFalse(maxBitSet.get(15));
        for (int i = 0; i < maxBitSet.size() - 1; i++) {
            assertTrue(maxBitSet.get(i));
        }
        assertEquals(max, maxBitSet.toShort());
    }

    @Test
    public void testChars() {
        char zero = (char) 0;
        BitSet16 zeroBitSet = BitSet16.fromChar(zero);
        zeroBitSet.forEach(val -> assertFalse(val));
        assertEquals(zero, zeroBitSet.toChar());

        char one = (char) 1;
        BitSet16 oneBitSet = BitSet16.fromChar(one);
        assertTrue(oneBitSet.get(0));
        for (int i = 1; i < oneBitSet.size(); i++) {
            assertFalse(oneBitSet.get(i));
        }
        assertEquals(one, oneBitSet.toChar());


        char min = Character.MIN_VALUE;
        BitSet16 minBitSet = BitSet16.fromChar(min);
        minBitSet.forEach(val -> assertFalse(val));
        assertEquals(min, minBitSet.toChar());


        char max = Character.MAX_VALUE;
        BitSet16 maxBitSet = BitSet16.fromChar(max);
        maxBitSet.forEach(val -> assertTrue(val));
        assertEquals(max, maxBitSet.toChar());
    }

    @Test
    public void testShortCharExchange() {
        BitSet16 zeroBitSet = new BitSet16();
        assertEquals((short) 0, zeroBitSet.toShort());
        assertEquals((char) 0, zeroBitSet.toChar());

        BitSet16 oneBitSet = new BitSet16("0000 0000 0000 0001");
        assertEquals((short) 1, oneBitSet.toShort());
        assertEquals((char) 1, oneBitSet.toChar());

        BitSet16 onesBitSet = new BitSet16("1111 1111 1111 1111");
        assertEquals((short) -1, onesBitSet.toShort());
        assertEquals(Character.MAX_VALUE, onesBitSet.toChar());

        BitSet16 shortMinBitSet = new BitSet16("1000 0000 0000 0000");
        assertEquals(Short.MIN_VALUE, shortMinBitSet.toShort());
        assertEquals((char) 32768, shortMinBitSet.toChar());

        BitSet16 shortMaxBitSet = new BitSet16("0111 1111 1111 1111");
        assertEquals(Short.MAX_VALUE, shortMaxBitSet.toShort());
        assertEquals((char) 32767, shortMaxBitSet.toChar());
    }

    @Test
    public void testCopy() {
        BitSet16 zeroBitSet = new BitSet16();
        assertEquals((short) 0, zeroBitSet.copy().toShort());
        assertEquals((char) 0, zeroBitSet.copy().toChar());
        assertEquals(zeroBitSet, zeroBitSet.copy());

        //If we change the copy, the original should not be changed
        BitSet16 negated = zeroBitSet.copy().negate();
        assertNotEquals(negated, zeroBitSet);
        assertEquals((short) 0, zeroBitSet.toShort());
        assertEquals((char) 0, zeroBitSet.toChar());
    }

    @Test
    public void testZeroBytes() {
        char zero = (char) 0;
        BitSet16 bitSet = BitSet16.fromChar(zero);
        BitSet8 lowerByte = bitSet.getLowerByte();
        lowerByte.forEach(val -> assertFalse(val));
        BitSet8 upperByte = bitSet.getUpperByte();
        upperByte.forEach(val -> assertFalse(val));
        BitSet8[] bytes = bitSet.toBytes();
        assertEquals(upperByte, bytes[0]);
        assertEquals(lowerByte, bytes[1]);
    }

    @Test
    public void testMaxBytes() {
        char max = Character.MAX_VALUE;
        BitSet16 bitSet = BitSet16.fromChar(max);
        BitSet8 lowerByte = bitSet.getLowerByte();
        lowerByte.forEach(val -> assertTrue(val));
        BitSet8 upperByte = bitSet.getUpperByte();
        upperByte.forEach(val -> assertTrue(val));
        BitSet8[] bytes = bitSet.toBytes();
        assertEquals(upperByte, bytes[0]);
        assertEquals(lowerByte, bytes[1]);
    }

    @Test
    public void testMixBytes() {
        BitSet16 bitSet = new BitSet16("1010 0110 0101 1001");
        BitSet8 lowerByte = bitSet.getLowerByte();
        assertEquals("[01011001]", lowerByte.toString());
        BitSet8 upperByte = bitSet.getUpperByte();
        assertEquals("[10100110]", upperByte.toString());
        BitSet8[] bytes = bitSet.toBytes();
        assertEquals(upperByte, bytes[0]);
        assertEquals(lowerByte, bytes[1]);
    }


}