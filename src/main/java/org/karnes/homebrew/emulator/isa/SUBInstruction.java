package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class SUBInstruction extends AbstractThreeRegInstruction {
    public SUBInstruction(RegisterName destinationRegister, RegisterName source2Register, RegisterName source1Register) {
        super("SUB", destinationRegister, source2Register, source1Register, new FixedBitSet("0001 010 000 000 000"));
    }
}
