package org.karnes.homebrew.bitset;

import java.util.Arrays;
import java.util.Iterator;

public class ArbitraryBitSet implements FixedBitSet<ArbitraryBitSet> {

    boolean[] bits;

    public ArbitraryBitSet(int size) {
        bits = new boolean[size];
    }

    public ArbitraryBitSet(boolean[] value) {
        bits = value;
    }


    public ArbitraryBitSet(String bitString) {
        String stripped = bitString.replaceAll("[^01]+", "");
        //Only 0's and 1's should be left
        char[] chars = stripped.toCharArray();
        bits = new boolean[chars.length];

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
                case '0':
                    bits[i] = false;
                    break;
                case '1':
                    bits[i] = true;
                    break;
                default:
                    throw new IllegalStateException("Unexpected character: " + c);
            }
        }

    }

    @Override
    public int size() {
        return bits.length;
    }

    @Override
    public ArbitraryBitSet negate() {
        boolean[] flippedBits = new boolean[bits.length];
        for (int i = 0; i < bits.length; i++) {
            boolean bitBoolean = bits[i];
            flippedBits[i] = !bitBoolean;
        }
        return new ArbitraryBitSet(flippedBits);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArbitraryBitSet)) return false;
        ArbitraryBitSet that = (ArbitraryBitSet) o;
        return Arrays.equals(bits, that.bits);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bits);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(bits.length + 2);
        builder.append('[');
        for (int i = 0; i < bits.length; i++) {
            boolean bitBoolean = bits[i];
            if (bitBoolean) {
                builder.append('1');
            } else {
                builder.append('0');
            }
        }
        builder.append(']');
        return builder.toString();
    }

    @Override
    public boolean get(int position) throws ArrayIndexOutOfBoundsException {
        return bits[bits.length - 1 - position];
    }

    @Override
    public ArbitraryBitSet set(int position, boolean newValue) throws ArrayIndexOutOfBoundsException {
        boolean[] newBits = Arrays.copyOf(bits, bits.length);
        newBits[bits.length - 1 - position] = newValue;
        return new ArbitraryBitSet(newBits);
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new Iterator<Boolean>() {
            private int pos = bits.length - 1;

            @Override
            public boolean hasNext() {
                return pos >= 0;
            }

            @Override
            public Boolean next() {
                return bits[pos--];
            }
        };
    }

    @Override
    public ArbitraryBitSet copy() {
        return new ArbitraryBitSet(Arrays.copyOf(bits, bits.length));
    }

    @Override
    public FixedBitSet getSlice(int from, int to) {
        return new ArbitraryBitSet(Arrays.copyOfRange(bits, from, to));
    }
}
