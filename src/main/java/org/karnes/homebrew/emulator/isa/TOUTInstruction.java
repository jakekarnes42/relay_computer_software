package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;

public class TOUTInstruction extends AbstractNoArgInstruction {
    public TOUTInstruction() {
        super("TOUT", new FixedBitSet("1000 1000 0000 0000"));
    }
}
