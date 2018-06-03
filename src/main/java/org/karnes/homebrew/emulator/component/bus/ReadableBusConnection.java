package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;

public interface ReadableBusConnection {
    FixedBitSet readBusValue();
}
