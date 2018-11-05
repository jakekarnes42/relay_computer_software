package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class WRDINInstruction extends AbstractOneRegInstruction {

    public WRDINInstruction(RegisterName register) {
        super("WRDIN", register, new FixedBitSet("1000 0000 0000 1 000"));
    }
}
