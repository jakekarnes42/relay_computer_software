package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * A connection to a Bus which can be used to write new values onto the Bus
 */
public interface WriteableBusConnection extends BusConnection {

    /**
     * Gets the value which this connection is current writing onto the Bus. <b>Note</b> This is <u>not</u> the current
     * value of the Bus itself (which may have other values written to it from other connections).
     *
     * @return The value that this connection is currently writing onto the Bus.
     * @see #writeValueToBus(FixedBitSet)
     */
    FixedBitSet getValueFromConnection();

    /**
     * Writes a new value onto the Bus. <b>Note</b> This does not necessarily define the Bus's value. The Bus may have
     * other connections which are also writing their own values onto the Bus as well.
     *
     * @param value The value that this connection should output onto the Bus
     */
    void writeValueToBus(FixedBitSet value);
}
