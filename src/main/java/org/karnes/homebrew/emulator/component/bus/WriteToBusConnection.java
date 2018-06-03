package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

public class WriteToBusConnection extends AbstractBusConnection {

    private FixedBitSet value;

    WriteToBusConnection(Bus bus) {
        super(bus);
        value = new ArbitraryBitSet(bus.getWidth());
    }

    public FixedBitSet getValueFromConnection() {
        return value;
    }

    public void writeValueToBus(FixedBitSet value) {
        this.value = value;
        bus.update();
    }
}
