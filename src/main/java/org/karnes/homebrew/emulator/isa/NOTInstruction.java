package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class NOTInstruction extends AbstractTwoRegALUInstruction {
    NOTInstruction(RegisterName destination, RegisterName source
    ) {
        super("NOT", destination, source, new BitSet16("0001 111 000 000 000"));
    }
}
