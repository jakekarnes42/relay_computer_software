package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;

public class HALTInstruction extends AbstractNoArgInstruction {
    public HALTInstruction() {
        super("HALT", new FixedBitSet("1111 1111 1111 1111"));
    }

}
