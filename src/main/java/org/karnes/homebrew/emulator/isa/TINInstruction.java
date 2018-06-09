package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;

public class TINInstruction extends AbstractNoArgInstruction {
    TINInstruction() {
        super("TIN", new BitSet16("1000 0000 0000 0000"));
    }
}
