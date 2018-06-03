package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.emulator.component.bus.BusValueChangedEvent;

@FunctionalInterface
public interface BusValueChangeHandler {
    void handleBusValueChangedEvent(BusValueChangedEvent busValueChangedEvent);
}
