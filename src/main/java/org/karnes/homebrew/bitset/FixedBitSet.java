package org.karnes.homebrew.bitset;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A binary data holder very similar to Java's {@link java.util.BitSet} with a couple of key differences:
 * <ol>
 * <li>Immutable. If an operation would change the value, it returns a copy with the operation applied.</li>
 * <li>The FixedBitSet never changes size after it's created.</li>
 * <li>Conversion to Java's primitive types is only allowed if the FixedBitSet's size is the exact same number of bits as Java's primitive. For example, to convert to a {@code byte} the FixedBitSet must have a size of 8. Otherwise an {@link IllegalArgumentException} is thrown. </li>
 * </ol>
 */
public class FixedBitSet implements Serializable, Iterable<Boolean> {

    boolean[] bits;

    /**
     * Creates a FixedBitSet of the given size. This will have {@code size} number of bits, all of which will be initialized to 0.
     *
     * @param size The number of bits in the FixedBitSet
     */
    public FixedBitSet(int size) {
        bits = new boolean[size];
    }

    /**
     * Creates a FixedBitSet from the {@code boolean} array. The FixedBitSet's size will be {@code values.length}.
     *
     * @param values The values of the FixedBitSet
     */
    public FixedBitSet(boolean[] values) {
        bits = values;
    }


    /**
     * Creates a FixedBitSet from a String, considering only the characters '0' and '1'. All other characters are stripped out.
     * <p>
     * Examples:
     * <ul>
     * <li><b>Input: </b>"101" <b>Output: </b>FixedBitSet [101]</li>
     * <li><b>Input: </b>"101 010" <b>Output: </b>FixedBitSet [101010]</li>
     * <li><b>Input: </b>"1abc1" <b>Output: </b>FixedBitSet [11]</li>
     * <li><b>Input: </b>"111_000_111" <b>Output: </b>FixedBitSet [111_000_111]</li>
     * <li><b>Input: </b>"[010]" <b>Output: </b>FixedBitSet [010]</li>
     * </ul>
     * <p>
     * This is helpful because we can use this constructor to convert the result of {@link FixedBitSet#toString()} back into a FixedBitSet.
     *
     * @param bitString The String to convert to a FixedBitSet
     */
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

    /**
     * The number of bits in the FixedBitSet
     *
     * @return the number of bits in the FixedBitSet
     */
    public int size() {
        return bits.length;
    }

    /**
     * Negate (flips) all the bits in this FixedBitSet, and returns the resulting FixedBitSet
     *
     * @return The FixedBitSet with all its bits flipped
     */
    public FixedBitSet negate() {
        boolean[] flippedBits = new boolean[bits.length];
        for (int i = 0; i < bits.length; i++) {
            boolean bitBoolean = bits[i];
            flippedBits[i] = !bitBoolean;
        }
        return new FixedBitSet(flippedBits);

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
     * Returns an iterator over each bit in the FixedBitSet (as a {@code boolean}), from right to left.
     *
     * <b>Warning:</b> Because this is considered an array of bits, likely representing a number, the positions are considered from right to left <i>which is the opposite of a typical array.</i> When representing a number, the least significant bit will be in position 0.
     *
     * @return an Iterator.
     */
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

    /**
     * A copy of this FixedBitSet without modification.
     *
     * @return a copy of the FixedBitSet
     */
    public FixedBitSet copy() {
        return new FixedBitSet(Arrays.copyOf(bits, bits.length));
    }

    /**
     * Gets a copy of a slice of the current FixedBitSet. This uses the same indexing scheme as {@link #get(int)}
     *
     * @param from inclusive
     * @param to   exclusive
     * @return A copy of the slice of the FixedBitSet
     */
    public FixedBitSet getSlice(int from, int to) {
        if (to <= from) {
            throw new IllegalArgumentException(to + " <= " + from);
        }

        int toIndex = bits.length - from;
        int fromIndex = bits.length - to;


        return new FixedBitSet(Arrays.copyOfRange(bits, fromIndex, toIndex));
    }

    /**
     * Converts from a byte to a FixedBitSet. The resulting FixedBitSet will have 8 bits.
     *
     * @param value The byte value to convert into a FixedBitSet
     * @return The FixedBitSet
     */
    public static FixedBitSet fromByte(byte value) {
        final boolean[] bits = new boolean[Byte.SIZE];
        for (int i = 0; i < Byte.SIZE; i++) {
            bits[Byte.SIZE - 1 - i] = (1 << i & value) != 0;
        }
        return new FixedBitSet(bits);
    }

    /**
     * Converts the FixedBitSet into a byte.
     * <b>Warning:</b> The FixedBitSet must be exactly 8 bits
     *
     * @return The binary-equvialent byte
     * @throws IllegalArgumentException if the current FixedBitSet's size isn't 8 (the number of bits in a byte)
     */
    public byte toByte() {
        if (size() != Byte.SIZE) {
            throw new IllegalArgumentException("A FixedBitSet must be exactly " + Byte.SIZE + "bits to convert to a byte");
        }

        //Convert to an int in the interim
        int n = 0;
        for (int i = 0; i < Byte.SIZE; ++i) {
            n = (n << 1) + (bits[i] ? 1 : 0);
        }
        return (byte) n;
    }


    /**
     * Converts from a short to a FixedBitSet. The resulting FixedBitSet will have 16 bits.
     *
     * @param value The short value to convert into a FixedBitSet
     * @return The FixedBitSet
     */
    public static FixedBitSet fromShort(short value) {
        final boolean[] bits = new boolean[Short.SIZE];
        for (int i = 0; i < Short.SIZE; i++) {
            bits[Short.SIZE - 1 - i] = (1 << i & value) != 0;
        }
        return new FixedBitSet(bits);
    }

    /**
     * Converts the FixedBitSet into a short.
     * <b>Warning:</b> The FixedBitSet must be exactly 16 bits
     *
     * @return The binary-equvialent short
     * @throws IllegalArgumentException if the current FixedBitSet's size isn't 16 (the number of bits in a short)
     */
    public short toShort() {
        if (size() != Short.SIZE) {
            throw new IllegalArgumentException("A FixedBitSet must be exactly " + Short.SIZE + "bits to convert to a short");
        }

        //Convert to an int in the interim
        int n = 0;
        for (int i = 0; i < Short.SIZE; ++i) {
            n = (n << 1) + (bits[i] ? 1 : 0);
        }
        return (short) n;
    }

    /**
     * Converts from a char to a FixedBitSet. The resulting FixedBitSet will have 16 bits.
     *
     * @param value The char value to convert into a FixedBitSet
     * @return The FixedBitSet
     */
    public static FixedBitSet fromChar(char value) {
        return fromShort((short) value);
    }

    /**
     * Converts the FixedBitSet into a char.
     * <b>Warning:</b> The FixedBitSet must be exactly 16 bits
     *
     * @return The binary-equvialent char
     * @throws IllegalArgumentException if the current FixedBitSet's size isn't 16 (the number of bits in a char)
     */
    public char toChar() {
        return (char) toShort();
    }

    /**
     * Creates a FixedBitSet of the given size, where each value is a 1 (or true)
     *
     * @param size The number of bits in the resulting FixedBitSet
     * @return the resulting BitSet
     */
    public static FixedBitSet allOnes(int size) {
        boolean[] values = new boolean[size];
        Arrays.fill(values, true);
        return new FixedBitSet(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedBitSet)) return false;
        FixedBitSet booleans = (FixedBitSet) o;
        return Arrays.equals(bits, booleans.bits);
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
}
