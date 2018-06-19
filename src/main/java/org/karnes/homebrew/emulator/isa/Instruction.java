package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;

public interface Instruction {
    String getName();

    FixedBitSet toBinary();
}
