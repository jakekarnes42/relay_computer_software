package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class FETCHInstruction extends AbstractTwoRegInstruction {
    public FETCHInstruction(RegisterName destination, RegisterName source) {
        super("FETCH", destination, source, new BitSet16("0010 0100 00 000 000"));
    }
}
