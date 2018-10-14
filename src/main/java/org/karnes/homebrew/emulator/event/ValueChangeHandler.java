package org.karnes.homebrew.emulator.event;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;

/**
 * An interface for handling changes to a {@link ReadableConnection}'s value
 */
@FunctionalInterface
public interface ValueChangeHandler {

    /**
     * Invoked when a value changes.
     *
     * @param newValue The new value
     */
    void handleChangedValue(FixedBitSet newValue);
}
