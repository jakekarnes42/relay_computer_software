package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class WRDOUTInstruction extends AbstractOneRegInstruction {

    public WRDOUTInstruction(RegisterName register) {
        super("WRDOUT", register, new FixedBitSet(" 1000 1000 0000 1 000"));
    }
}