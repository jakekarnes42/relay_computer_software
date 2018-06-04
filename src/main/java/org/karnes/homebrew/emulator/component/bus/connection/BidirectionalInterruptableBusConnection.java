package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.emulator.component.bus.BidirectionalBus;

/**
 * A {@link BidirectionalBusConnection} to a {@link BidirectionalBus} which also supports interrupts
 */
public class BidirectionalInterruptableBusConnection extends BidirectionalBusConnection implements InterruptableBusConnection {

    private BusValueChangeHandler handler;

    public BidirectionalInterruptableBusConnection(BidirectionalBus bus, BusValueChangeHandler handler) {
        super(bus);
        this.handler = handler;

    }

    @Override
    public void handleBusValueChangedEvent(BusValueChangedEvent event) {
        handler.handleBusValueChangedEvent(event);
    }
}
