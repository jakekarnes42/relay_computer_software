package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;

public class TINInstruction extends AbstractNoArgInstruction {
    public TINInstruction() {
        super("TIN", new FixedBitSet("1000 0000 0000 0000"));
    }
}
