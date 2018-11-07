package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;

public class FETCHInstruction extends AbstractTwoRegInstruction {
    public FETCHInstruction(RegisterName destination, RegisterName source) {
        super("FETCH", destination, source, new FixedBitSet("0010 0100 00 000 000"));
    }
}
