package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class NOTInstruction extends AbstractTwoRegALUInstruction {
    public NOTInstruction(RegisterName destination, RegisterName source) {
        super("NOT", destination, source, new FixedBitSet("0001 111 000 000 000"));
    }
}
