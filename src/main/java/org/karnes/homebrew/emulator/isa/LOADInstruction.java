package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class LOADInstruction extends AbstractOneRegInstruction {

    public LOADInstruction(RegisterName register) {
        super("LOAD", register, new BitSet16("0010 0001 0000 0 000"));
    }
}
