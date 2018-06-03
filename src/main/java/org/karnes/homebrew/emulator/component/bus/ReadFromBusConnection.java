package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;

public class ReadFromBusConnection extends AbstractBusConnection {
    ReadFromBusConnection(Bus bus) {
        super(bus);
    }

    public FixedBitSet readBusValue() {
        return bus.getValue();
    }
}
