package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.emulator.component.bus.Bus;

/**
 * A connection to a Bus.
 *
 * @see ReadableBusConnection
 * @see WriteableBusConnection
 * @see BidirectionalBusConnection
 */
public interface BusConnection {
    /**
     * The width of the {@link Bus} which this connects to.
     *
     * @return The width of the connection.
     * @see Bus#getWidth()
     */
    int getWidth();
}
