package org.karnes.homebrew.bitset;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class BitSet8 implements FixedBitSet<BitSet8> {

    static final int SIZE = 8;
    private final ArbitraryBitSet bitSet;

    public BitSet8() {
        bitSet = new ArbitraryBitSet(SIZE);
    }


    public BitSet8(boolean[] value) {
        if (value.length != SIZE) {
            throw new IllegalArgumentException("A BitSet8 must be exactly " + SIZE + "bits");
        }
        bitSet = new ArbitraryBitSet(value);

    }

    public BitSet8(String bitString) {
        bitSet = new ArbitraryBitSet(bitString);

        if (bitSet.size() != SIZE) {
            throw new IllegalArgumentException("A BitSet8 must be exactly " + SIZE + "bits");
        }
    }

    public static BitSet8 fromByte(byte value) {
        final boolean[] bits = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            bits[SIZE - 1 - i] = (1 << i & value) != 0;
        }
        return new BitSet8(bits);
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

    /**
     * Returns the two nibbles ({@code BitSet4}) which make up this byte (BitSet8).
     * The upper 4 bits will be at index 0, the lower 4 bits will be at index 1 of the resulting array.
     *
     * @return the two nibbles ({@code BitSet4}) which make up this byte (BitSet8).
     */
    public BitSet4[] toNibbles() {
        return new BitSet4[]{getUpperNibble(), getLowerNibble()};
    }

    public BitSet4 getLowerNibble() {
        return new BitSet4(Arrays.copyOfRange(bitSet.bits, BitSet4.SIZE, SIZE));
    }

    public BitSet4 getUpperNibble() {
        return new BitSet4(Arrays.copyOfRange(bitSet.bits, 0, BitSet4.SIZE));
    }


    public int size() {
        return SIZE;
    }

    public BitSet8 negate() {
        return new BitSet8(bitSet.negate().bits);
    }

    public String toString() {
        return bitSet.toString();
    }

    public boolean get(int position) throws ArrayIndexOutOfBoundsException {
        return bitSet.get(position);
    }

    public BitSet8 set(int position, boolean newValue) throws ArrayIndexOutOfBoundsException {
        return new BitSet8(bitSet.set(position, newValue).bits);
    }

    public Iterator<Boolean> iterator() {
        return bitSet.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BitSet8)) return false;
        BitSet8 booleans = (BitSet8) o;
        return Objects.equals(bitSet, booleans.bitSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bitSet);
    }

    @Override
    public BitSet8 copy() {
        return new BitSet8(Arrays.copyOf(bitSet.bits, SIZE));
    }

    @Override
    public FixedBitSet getSlice(int from, int to) {
        return bitSet.getSlice(from, to);
    }
}
