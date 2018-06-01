package org.karnes.homebrew.bitset;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class BitSet4 implements FixedBitSet<BitSet4> {

    static final int SIZE = 4;
    private final ArbitraryBitSet bitSet;

    public BitSet4() {
        bitSet = new ArbitraryBitSet(SIZE);
    }


    public BitSet4(boolean[] value) {
        if (value.length != SIZE) {
            throw new IllegalArgumentException("A BitSet8 must be exactly " + SIZE + "bits");
        }
        bitSet = new ArbitraryBitSet(value);

    }

    public BitSet4(String bitString) {
        bitSet = new ArbitraryBitSet(bitString);

        if (bitSet.size() != SIZE) {
            throw new IllegalArgumentException("A BitSet8 must be exactly " + SIZE + "bits");
        }
    }

    public static BitSet4 fromByte(byte value) {
        final boolean[] bits = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            bits[SIZE - 1 - i] = (1 << i & value) != 0;
        }
        return new BitSet4(bits);
    }

    public byte toByte() {
        if (bitSet.size() != SIZE) {
            throw new IllegalArgumentException("A BitSet8 must be exactly " + SIZE + "bits");
        }

        //Convert to an int in the interim
        int n = 0;
        for (int i = 0; i < SIZE; ++i) {
            n = (n << 1) + (bitSet.bits[i] ? 1 : 0);
        }
        return (byte) n;
    }


    public int size() {
        return SIZE;
    }

    public BitSet4 negate() {
        return new BitSet4(bitSet.negate().bits);
    }

    public String toString() {
        return bitSet.toString();
    }

    public boolean get(int position) throws ArrayIndexOutOfBoundsException {
        return bitSet.get(position);
    }

    public BitSet4 set(int position, boolean newValue) throws ArrayIndexOutOfBoundsException {
        return new BitSet4(bitSet.set(position, newValue).bits);
    }

    public Iterator<Boolean> iterator() {
        return bitSet.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BitSet4)) return false;
        BitSet4 booleans = (BitSet4) o;
        return Objects.equals(bitSet, booleans.bitSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bitSet);
    }

    @Override
    public BitSet4 copy() {
        return new BitSet4(Arrays.copyOf(bitSet.bits, SIZE));
    }

    @Override
    public FixedBitSet getSlice(int from, int to) {
        return bitSet.getSlice(from, to);
    }
}
