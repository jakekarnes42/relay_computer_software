package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.StackRegisterName;

/**
 * Abstract class for instructions which require one stack register argument. They may take more arguments (such as labels/values)
 * in Assembly
 */
public class AbstractOneStackRegInstruction implements Instruction {

    private final String name;
    private final StackRegisterName register;
    private final BitSet16 binary;

    AbstractOneStackRegInstruction(String name, StackRegisterName register, BitSet16 template) {
        this.name = name;
        this.register = register;

        BitSet16 bits = template.copy();
        FixedBitSet typeBits = register.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i+3, typeBits.get(i));
        }

        this.binary = bits;
    }

    @Override
    public String getName() {
        return name;
    }

    public StackRegisterName getRegister() {
        return register;
    }

    @Override
    public BitSet16 toBinary() {
        return binary;
    }
}
