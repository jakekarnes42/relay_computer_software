package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterName;

public class AbstractTwoRegALUInstruction extends AbstractTwoRegInstruction {
    AbstractTwoRegALUInstruction(String name, RegisterName destinationRegister, RegisterName sourceRegister, FixedBitSet template) {
        this.name = name;
        this.destinationRegister = destinationRegister;
        this.sourceRegister = sourceRegister;

        FixedBitSet bits = template.copy();

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
