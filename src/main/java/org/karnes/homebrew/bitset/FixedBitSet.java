package org.karnes.homebrew.bitset;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class FixedBitSet implements Serializable, Cloneable, Iterable<Boolean> {

    boolean[] bits;

    public FixedBitSet(int size) {
        bits = new boolean[size];
    }

    public FixedBitSet(boolean[] value) {
        bits = value;
    }


    public FixedBitSet(String bitString) {
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


    public int size() {
        return bits.length;
    }

    public FixedBitSet negate() {
        boolean[] flippedBits = new boolean[bits.length];
        for (int i = 0; i < bits.length; i++) {
            boolean bitBoolean = bits[i];
            flippedBits[i] = !bitBoolean;
        }
        return new FixedBitSet(flippedBits);

    }

    @Override
    public FixedBitSet clone() {
        return new FixedBitSet(bits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedBitSet)) return false;
        FixedBitSet that = (FixedBitSet) o;
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

    /**
     * Gets the bit in the given position of the bit set.
     *
     * <b>Warning:</b> Because this is considered an array of bits, likely representing a number, the positions are considered from right to left <i>which is the opposite of a typical array.</i> When representing a number, the least significant bit will be in position 0.
     *
     * @param position The bit index (considering 0 is the right-most bit)
     * @return The boolean value of the bit in the specified position
     * @throws ArrayIndexOutOfBoundsException if given an invalid position
     */
    public boolean get(int position) throws ArrayIndexOutOfBoundsException {
        return bits[bits.length - 1 - position];
    }

    /**
     * Gets the bit in the given position of the bit set.
     *
     * <b>Warning:</b> Because this is considered an array of bits, likely representing a number, the positions are considered from right to left <i>which is the opposite of a typical array.</i> When representing a number, the least significant bit will be in position 0.
     *
     * @param position The bit index (considering 0 is the right-most bit)
     * @param newValue The new value for the bit in the specified position.
     * @return A copy of the current FixedBitSet, with the new value in the specified position
     * @throws ArrayIndexOutOfBoundsException if given an invalid position
     */
    public FixedBitSet set(int position, boolean newValue) throws ArrayIndexOutOfBoundsException {
        boolean[] newBits = Arrays.copyOf(bits, bits.length);
        newBits[bits.length - 1 - position] = newValue;
        return new FixedBitSet(newBits);
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
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
}
