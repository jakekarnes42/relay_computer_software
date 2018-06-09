package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class STOREInstruction extends AbstractTwoRegInstruction {
    STOREInstruction(RegisterName destination, RegisterName source
    ) {
        super("STORE", destination, source, new BitSet16("0010 0010 00 000 000"));
    }
}
