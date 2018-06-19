package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class MOVInstruction extends AbstractTwoRegInstruction {
    public MOVInstruction(RegisterName destination, RegisterName source
    ) {
        super("MOV", destination, source, new FixedBitSet("1111 1111 11 000 000"));
    }
}
