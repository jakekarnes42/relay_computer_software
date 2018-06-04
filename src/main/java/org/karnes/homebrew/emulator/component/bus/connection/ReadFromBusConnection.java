package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;

/**
 * An implementation of a {@link ReadableBusConnection}
 */
public class ReadFromBusConnection implements ReadableBusConnection {
    private final ReadableBus bus;

    public ReadFromBusConnection(ReadableBus bus) {
        this.bus = bus;
    }

    @Override
    public FixedBitSet readBusValue() {
        return bus.getValue();
    }

    @Override
    public int getWidth() {
        return bus.getWidth();
    }

    @Override
    public String toString() {
        return "ReadFromBusConnection{" +
                "bus=" + bus.getName() +
                '}';
    }
}
