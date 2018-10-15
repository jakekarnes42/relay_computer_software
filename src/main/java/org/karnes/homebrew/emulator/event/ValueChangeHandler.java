package org.karnes.homebrew.emulator.event;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * An interface for handling changing values.
 */
@FunctionalInterface
public interface ValueChangeHandler {

    /**
     * Invoked when a value changes.
     *
     * @param newValue The new value.
     */
    void handleChangedValue(FixedBitSet newValue);
}
