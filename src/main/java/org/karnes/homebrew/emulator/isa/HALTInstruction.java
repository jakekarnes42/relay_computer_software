package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;

public class HALTInstruction extends AbstractNoArgInstruction {
    public HALTInstruction() {
        super("HALT", new BitSet16("1111 1111 1111 1111"));
    }

}
