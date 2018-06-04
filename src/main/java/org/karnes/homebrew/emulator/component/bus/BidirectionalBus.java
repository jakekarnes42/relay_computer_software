package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.emulator.component.bus.connection.BidirectionalBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.BidirectionalInterruptableBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.BusValueChangeHandler;

/**
 * Represents a {@link Bus} which can be read from software and written to through software.
 *
 * @see org.karnes.homebrew.emulator.component.bus.Bus
 * @see org.karnes.homebrew.emulator.component.bus.ReadableBus
 * @see org.karnes.homebrew.emulator.component.bus.WriteableBus
 */
public interface BidirectionalBus extends ReadableBus, WriteableBus {

    /**
     * Gets a connection to this bus which can be read from and written to through software.
     *
     * @return a connection which can be read from and written to through software.
     */
    BidirectionalBusConnection getBidirectionalConnection();

    /**
     * Gets a connection which can be read from, written to, and executes the {@code handler} when this bus' value change.
     *
     * @param handler The callback to be executed when this bus' value changes
     * @return a connection which can be read from and written to through software.
     */
    BidirectionalInterruptableBusConnection getBidirectionalInterruptableBusConnection(BusValueChangeHandler handler);
}
