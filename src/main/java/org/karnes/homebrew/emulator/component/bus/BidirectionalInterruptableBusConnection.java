package org.karnes.homebrew.emulator.component.bus;

public class BidirectionalInterruptableBusConnection extends BidirectionalBusConnection implements InterruptableBusConnection {

    private BusValueChangeHandler handler;

    BidirectionalInterruptableBusConnection(Bus bus) {
        super(bus);
    }

    public BidirectionalInterruptableBusConnection(Bus bus, BusValueChangeHandler handler) {
        this(bus);
        this.handler = handler;

    }

    @Override
    public void handleBusValueChangedEvent(BusValueChangedEvent event) {
        handler.handleBusValueChangedEvent(event);
    }
}
