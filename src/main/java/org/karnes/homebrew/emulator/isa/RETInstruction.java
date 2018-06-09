package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.StackRegisterName;

public class RETInstruction extends AbstractOneStackRegInstruction {
    public RETInstruction(StackRegisterName register) {
        super("RET", register, new BitSet16("0100 0000 0010 0 111"));
    }
}
