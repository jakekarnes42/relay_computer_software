package org.karnes.homebrew.emulator.component.bus;

public class InterruptFromBusConnection extends ReadFromBusConnection implements BusValueChangeHandler {

    private BusValueChangeHandler handler;

    InterruptFromBusConnection(Bus bus) {
        super(bus);
    }

    public InterruptFromBusConnection(Bus bus, BusValueChangeHandler handler) {
        this(bus);
        this.handler = handler;

    }

    @Override
    public void handleBusValueChangedEvent(BusValueChangedEvent event) {
        handler.handleBusValueChangedEvent(event);
    }
}
