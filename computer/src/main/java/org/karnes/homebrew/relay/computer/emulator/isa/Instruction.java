package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

public interface Instruction {
    String getName();

    FixedBitSet toBinary();
}
