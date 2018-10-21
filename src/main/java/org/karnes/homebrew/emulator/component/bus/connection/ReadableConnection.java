package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * A {@link Connection} which can be read from.
 */
public interface ReadableConnection extends Connection {
    /**
     * Reads the current value of the Component which this connection originates from.
     *
     * @return The current value of the connection.
     */
    FixedBitSet readValue();

}
