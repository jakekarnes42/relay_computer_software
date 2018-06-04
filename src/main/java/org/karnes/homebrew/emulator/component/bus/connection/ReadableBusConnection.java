package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;

/**
 * A connection to a {@link ReadableBus} which can be used to read values from the Bus.
 */
public interface ReadableBusConnection extends BusConnection {
    /**
     * Reads the current value of the {@link ReadableBus} which this connection originates from.
     *
     * @return The Bus' current value
     */
    FixedBitSet readBusValue();


}
