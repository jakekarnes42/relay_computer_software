package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;

public class TOUTInstruction extends AbstractNoArgInstruction {
    public TOUTInstruction() {
        super("TOUT", new BitSet16("1000 1000 0000 0000"));
    }
}
