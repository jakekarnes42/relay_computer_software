package org.karnes.homebrew.bitset;

import java.io.Serializable;
import java.util.Iterator;

public interface FixedBitSet<T extends FixedBitSet> extends Serializable, Iterable<Boolean> {
    /**
     * The number of bits in the FixedBitSet
     *
     * @return the number of bits in the FixedBitSet
     */
    int size();

    /**
     * Negate (flips) all the bits in this FixedBitSet, and returns the resulting FixedBitSet
     *
     * @return The FixedBitSet with all its bits flipped
     */
    T negate();

    /**
     * Gets the bit in the given position of the bit set.
     *
     * <b>Warning:</b> Because this is considered an array of bits, likely representing a number, the positions are considered from right to left <i>which is the opposite of a typical array.</i> When representing a number, the least significant bit will be in position 0.
     *
     * @param position The bit index (considering 0 is the right-most bit)
     * @return The boolean value of the bit in the specified position
     * @throws ArrayIndexOutOfBoundsException if given an invalid position
     */
    boolean get(int position) throws ArrayIndexOutOfBoundsException;

    /**
     * Gets the bit in the given position of the bit set.
     *
     * <b>Warning:</b> Because this is considered an array of bits, likely representing a number, the positions are considered from right to left <i>which is the opposite of a typical array.</i> When representing a number, the least significant bit will be in position 0.
     *
     * @param position The bit index (considering 0 is the right-most bit)
     * @param newValue The new value for the bit in the specified position.
     * @return A copy of the current ArbitraryBitSet, with the new value in the specified position
     * @throws ArrayIndexOutOfBoundsException if given an invalid position
     */
    T set(int position, boolean newValue) throws ArrayIndexOutOfBoundsException;

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    Iterator<Boolean> iterator();

    /**
     * A copy of this FixedBitSet without modification.
     *
     * @return a copy of the FixedBitSet
     */
    T copy();

    /**
     * Gets a copy of a slice of the current FixedBitSet
     *
     * @param from inclusive
     * @param to   exclusive
     * @return A copy of the slice of the FixedBitSet
     */
    FixedBitSet getSlice(int from, int to);
}
