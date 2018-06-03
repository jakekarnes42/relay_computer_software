package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;

public interface WriteableBusConnection {
    FixedBitSet getValueFromConnection();

    void writeValueToBus(FixedBitSet value);
}
