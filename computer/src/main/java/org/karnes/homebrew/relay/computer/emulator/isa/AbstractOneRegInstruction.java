package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;

/**
 * Abstract class for instructions which require one register argument. They may take more arguments (such as labels/values)
 * in Assembly
 */
public class AbstractOneRegInstruction implements Instruction {

    private final String name;
    private final RegisterName register;
    private final FixedBitSet binary;

    AbstractOneRegInstruction(String name, RegisterName register, FixedBitSet template) {
        this.name = name;
        this.register = register;

        FixedBitSet bits = template.copy();
        FixedBitSet typeBits = register.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i, typeBits.get(i));
        }

        this.binary = bits;
    }

    @Override
    public String getName() {
        return name;
    }

    public RegisterName getRegister() {
        return register;
    }

    @Override
    public FixedBitSet toBinary() {
        return binary;
    }
}
