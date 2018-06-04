package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.emulator.component.bus.ReadableBus;

/**
 * An interface for handling changes to a {@link ReadableBus}'s value
 */
@FunctionalInterface
public interface BusValueChangeHandler {
    void handleBusValueChangedEvent(BusValueChangedEvent busValueChangedEvent);
}
