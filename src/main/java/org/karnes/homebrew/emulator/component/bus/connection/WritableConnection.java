package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;

public interface WritableConnection extends Connection {

    /**
     * Indicates the the Component's value being output onto the Bus has changed, and the connected Bus should be notified.
     * <b>Note</b> This does not necessarily define the Bus's value. The Bus may have
     * other connections which are also writing their own values onto the Bus as well.
     *
     * @param value The value that this connection should output onto the Bus
     */
    void writeValue(FixedBitSet value);
}
