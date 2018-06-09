package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class XORInstruction extends AbstractThreeRegInstruction {
    public XORInstruction(RegisterName destinationRegister, RegisterName source2Register, RegisterName source1Register) {
        super("XOR", destinationRegister, source2Register, source1Register, new BitSet16("0001 100 000 000 000"));
    }
}
