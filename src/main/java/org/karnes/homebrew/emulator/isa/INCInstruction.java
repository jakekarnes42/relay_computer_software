package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class INCInstruction extends AbstractTwoRegALUInstruction {
    public INCInstruction(RegisterName destination, RegisterName source) {
        super("INC", destination, source, new FixedBitSet("0001 001 000 000 000"));
    }
}
