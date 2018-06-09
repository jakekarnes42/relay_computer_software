package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;

public class NOPInstruction extends AbstractNoArgInstruction {
    public NOPInstruction() {
        super("NOP", new BitSet16());
    }
}
