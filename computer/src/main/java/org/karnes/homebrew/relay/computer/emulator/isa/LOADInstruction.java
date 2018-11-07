package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;

public class LOADInstruction extends AbstractOneRegInstruction {

    public LOADInstruction(RegisterName register) {
        super("LOAD", register, new FixedBitSet("0010 0001 0000 0 000"));
    }
}
