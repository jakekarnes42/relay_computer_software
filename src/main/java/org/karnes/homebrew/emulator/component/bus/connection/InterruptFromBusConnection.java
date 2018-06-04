package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.emulator.component.bus.ReadableBus;

public class InterruptFromBusConnection extends ReadFromBusConnection implements InterruptableBusConnection {

    private BusValueChangeHandler handler;

    InterruptFromBusConnection(ReadableBus bus) {
        super(bus);
    }

    public InterruptFromBusConnection(ReadableBus bus, BusValueChangeHandler handler) {
        this(bus);
        this.handler = handler;
    }

    @Override
    public void handleBusValueChangedEvent(BusValueChangedEvent event) {
        handler.handleBusValueChangedEvent(event);
    }
}
