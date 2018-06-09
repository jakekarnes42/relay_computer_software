package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.StackRegisterName;

public class CALLInstruction extends AbstractOneStackRegInstruction {
    public CALLInstruction(StackRegisterName register) {
        super("CALL", register, new BitSet16("0100 0000 0100 0 000"));
    }
}
