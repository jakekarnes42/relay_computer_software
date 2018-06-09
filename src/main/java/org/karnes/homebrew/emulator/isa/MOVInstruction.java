package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class MOVInstruction extends AbstractTwoRegInstruction {
    public MOVInstruction(RegisterName destination, RegisterName source
    ) {
        super("MOV", destination, source, new BitSet16("1111 1111 11 000 000"));
    }
}
