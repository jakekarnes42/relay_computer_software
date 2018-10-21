package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * A {@link Connection} which can be written to.
 */
public interface WritableConnection extends Connection {

    /**
     * Writes a new value to the connection, which may update another component on the other side.
     *
     * @param value The new value.
     */
    void writeValue(FixedBitSet value);
}
