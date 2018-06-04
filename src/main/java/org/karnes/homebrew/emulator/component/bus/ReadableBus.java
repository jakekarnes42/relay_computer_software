package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.connection.BusValueChangeHandler;
import org.karnes.homebrew.emulator.component.bus.connection.InterruptFromBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableBusConnection;

/**
 * A Bus which can have its current value read through software.
 */
public interface ReadableBus extends Bus {

    /**
     * Get's the current value of the Bus
     *
     * @return The current value of the Bus.
     */
    FixedBitSet getValue();

    /**
     * Gets a connection which can be later used for reading from this Bus.
     *
     * @return A readable connection to the Bus.
     */
    ReadableBusConnection getReadConnection();

    /**
     * Gets a connection to the Bus which can read the Bus' value, and will have the provided logic in the
     * {@code handler} executed when the Bus' value changes.
     *
     * @param handler The logic to be executed when this Bus' value changes.
     * @return A readable connection to the Bus which includes interrupt functionality.
     */
    InterruptFromBusConnection getInterruptConnection(BusValueChangeHandler handler);

}
