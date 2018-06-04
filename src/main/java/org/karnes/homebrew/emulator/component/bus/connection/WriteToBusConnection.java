package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.WriteableBus;

public class WriteToBusConnection implements WriteableBusConnection {

    private WriteableBus bus;
    private FixedBitSet value;

    public WriteToBusConnection(WriteableBus bus) {
        this.bus = bus;
        value = new ArbitraryBitSet(bus.getWidth());
    }

    @Override
    public int getWidth() {
        return bus.getWidth();
    }

    @Override
    public FixedBitSet getValueFromConnection() {
        return value;
    }

    @Override
    public void writeValueToBus(FixedBitSet value) {
        this.value = value;
        bus.refreshValues();
    }

    @Override
    public String toString() {
        return "WriteToBusConnection{" +
                "bus=" + bus.getName() +
                ", value=" + value +
                '}';
    }
}
