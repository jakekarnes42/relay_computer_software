package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class INCInstruction extends AbstractTwoRegALUInstruction {
    public INCInstruction(RegisterName destination, RegisterName source) {
        super("INC", destination, source, new BitSet16("0001 001 000 000 000"));
    }
}
