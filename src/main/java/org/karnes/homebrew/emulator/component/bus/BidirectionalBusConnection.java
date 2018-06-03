package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

public class BidirectionalBusConnection extends AbstractBusConnection implements ReadableBusConnection, WriteableBusConnection {

    private FixedBitSet value;

    BidirectionalBusConnection(Bus bus) {
        super(bus);
        value = new ArbitraryBitSet(bus.getWidth());
    }

    @Override
    public FixedBitSet getValueFromConnection() {
        return value;
    }

    @Override
    public void writeValueToBus(FixedBitSet value) {
        this.value = value;
        bus.update();
    }

    @Override
    public FixedBitSet readBusValue() {
        return bus.getValue();
    }
}
