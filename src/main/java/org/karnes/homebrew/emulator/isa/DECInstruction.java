package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class DECInstruction extends AbstractTwoRegALUInstruction {
    public DECInstruction(RegisterName destination, RegisterName source) {
        super("DEC", destination, source, new BitSet16("0001 011 000 000 000"));
    }
}
