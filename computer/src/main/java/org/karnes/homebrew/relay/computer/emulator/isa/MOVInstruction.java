package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;

public class MOVInstruction extends AbstractTwoRegInstruction {
    public MOVInstruction(RegisterName destination, RegisterName source
    ) {
        super("MOV", destination, source, new FixedBitSet("1111 1111 11 000 000"));
    }
}
