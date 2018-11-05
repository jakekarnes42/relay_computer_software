package org.karnes.homebrew.relay.common.emulator.event;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

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
