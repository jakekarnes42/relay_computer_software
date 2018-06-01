package org.karnes.homebrew.bitset;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class BitSet16 implements FixedBitSet<BitSet16> {

    static final int SIZE = 16;
    private final ArbitraryBitSet bitSet;

    public BitSet16() {
        bitSet = new ArbitraryBitSet(SIZE);
    }


    public BitSet16(boolean[] value) {
        if (value.length != SIZE) {
            throw new IllegalArgumentException("A BitSet16 must be exactly " + SIZE + "bits");
        }
        bitSet = new ArbitraryBitSet(value);

    }

    public BitSet16(String bitString) {
        bitSet = new ArbitraryBitSet(bitString);

        if (bitSet.size() != SIZE) {
            throw new IllegalArgumentException("A BitSet16 must be exactly " + SIZE + "bits");
        }
    }

    public static BitSet16 fromShort(short value) {
        final boolean[] bits = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            bits[SIZE - 1 - i] = (1 << i & value) != 0;
        }
        return new BitSet16(bits);
    }

    public short toShort() {
        if (bitSet.size() != SIZE) {
            throw new IllegalArgumentException("A BitSet16 must be exactly " + SIZE + "bits");
        }

        //Convert to an int in the interim
        int n = 0;
        for (int i = 0; i < SIZE; ++i) {
            n = (n << 1) + (bitSet.bits[i] ? 1 : 0);
        }
        return (short) n;
    }

    public static BitSet16 fromChar(char value) {
        return fromShort((short) value);
    }


    public char toChar() {
        return (char) toShort();
    }

    public int size() {
        return SIZE;
    }

    public BitSet16 negate() {
        return new BitSet16(bitSet.negate().bits);
    }

    public String toString() {
        return bitSet.toString();
    }

    public boolean get(int position) throws ArrayIndexOutOfBoundsException {
        return bitSet.get(position);
    }

    public BitSet16 set(int position, boolean newValue) throws ArrayIndexOutOfBoundsException {
        return new BitSet16(bitSet.set(position, newValue).bits);
    }

    public Iterator<Boolean> iterator() {
        return bitSet.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BitSet16)) return false;
        BitSet16 booleans = (BitSet16) o;
        return Objects.equals(bitSet, booleans.bitSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bitSet);
    }


    @Override
    public BitSet16 copy() {
        return new BitSet16(Arrays.copyOf(bitSet.bits, SIZE));
    }

    /**
     * Returns the two bytes ({@code BitSet8}) which make up this word (BitSet16).
     * The upper 8 bits will be at index 0, the lower 8 bits will be at index 1 of the resulting array.
     *
     * @return the two bytes ({@code BitSet8}) which make up this word (BitSet16).
     */
    public BitSet8[] toBytes() {
        return new BitSet8[]{getUpperByte(), getLowerByte()};
    }

    public BitSet8 getLowerByte() {
        return new BitSet8(Arrays.copyOfRange(bitSet.bits, BitSet8.SIZE, SIZE));
    }

    public BitSet8 getUpperByte() {
        return new BitSet8(Arrays.copyOfRange(bitSet.bits, 0, BitSet8.SIZE));
    }

    @Override
    public FixedBitSet getSlice(int from, int to) {
        return bitSet.getSlice(from, to);
    }
}
