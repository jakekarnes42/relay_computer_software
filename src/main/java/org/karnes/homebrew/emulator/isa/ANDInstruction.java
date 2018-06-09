package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class ANDInstruction extends AbstractThreeRegInstruction {
    ANDInstruction(RegisterName destinationRegister, RegisterName source2Register, RegisterName source1Register) {
        super("AND", destinationRegister, source2Register, source1Register, new BitSet16("0001 110 000 000 000"));
    }
}
