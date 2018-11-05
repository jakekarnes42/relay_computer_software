package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

public class TINInstruction extends AbstractNoArgInstruction {
    public TINInstruction() {
        super("TIN", new FixedBitSet("1000 0000 0000 0000"));
    }
}
