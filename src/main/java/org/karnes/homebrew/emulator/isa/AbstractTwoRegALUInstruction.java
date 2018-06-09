package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

public class AbstractTwoRegALUInstruction extends AbstractTwoRegInstruction {
    AbstractTwoRegALUInstruction(String name, RegisterName destinationRegister, RegisterName sourceRegister, BitSet16 template) {
        this.name = name;
        this.destinationRegister = destinationRegister;
        this.sourceRegister = sourceRegister;

        BitSet16 bits = template.copy();

        FixedBitSet typeBits = sourceRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i+3, typeBits.get(i));
        }

        typeBits = destinationRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i+6, typeBits.get(i));
        }

        this.binary = bits;
    }
}
