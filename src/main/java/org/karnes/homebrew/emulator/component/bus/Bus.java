package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;

import java.util.List;

public interface Bus {
    String getName();

    int getWidth();

    List<AbstractBusConnection> getConnections();

    /**
     * Gets the current value of the bus, which is the results of OR-ing all the inputs from the Bus' connections
     *
     * @return current value of the bus
     */
    FixedBitSet getValue();

    void update();

    ReadFromBusConnection getReadConnection();

    InterruptFromBusConnection getInterruptConnection(BusValueChangeHandler handler);

    WriteToBusConnection getWriteConnection();
}
