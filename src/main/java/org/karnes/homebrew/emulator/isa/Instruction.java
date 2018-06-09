package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;

public interface Instruction {
    String getName();

    BitSet16 toBinary();
}
