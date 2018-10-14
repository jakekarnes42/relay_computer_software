package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;

public interface ReadableConnection extends Connection {
    /**
     * Reads the current value of the Component which this connection originates from.
     *
     * @return The Bus' current value
     */
    FixedBitSet readValue();

}
