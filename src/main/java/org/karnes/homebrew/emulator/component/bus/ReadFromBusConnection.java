package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;

public class ReadFromBusConnection extends AbstractBusConnection implements ReadableBusConnection {
    ReadFromBusConnection(Bus bus) {
        super(bus);
    }

    @Override
    public FixedBitSet readBusValue() {
        return bus.getValue();
    }
}
