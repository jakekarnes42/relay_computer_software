package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

public class HALTInstruction extends AbstractNoArgInstruction {
    public HALTInstruction() {
        super("HALT", new FixedBitSet("1111 1111 1111 1111"));
    }

}
