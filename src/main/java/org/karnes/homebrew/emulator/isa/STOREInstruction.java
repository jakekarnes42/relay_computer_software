package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class STOREInstruction extends AbstractTwoRegInstruction {
    public STOREInstruction(RegisterName destination, RegisterName source) {
        super("STORE", destination, source, new FixedBitSet("0010 0010 00 000 000"));
    }
}
