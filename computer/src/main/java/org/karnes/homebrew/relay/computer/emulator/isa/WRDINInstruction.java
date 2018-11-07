package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;

public class WRDINInstruction extends AbstractOneRegInstruction {

    public WRDINInstruction(RegisterName register) {
        super("WRDIN", register, new FixedBitSet("1000 0000 0000 1 000"));
    }
}
