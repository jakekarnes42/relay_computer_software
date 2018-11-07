package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.StackRegisterName;

public class RETInstruction extends AbstractOneStackRegInstruction {
    public RETInstruction(StackRegisterName register) {
        super("RET", register, new FixedBitSet("0100 0000 0010 0 111"));
    }
}
