package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;
import org.karnes.homebrew.emulator.component.register.StackRegisterName;

/**
 * Abstract class for instructions which require two registers argument. They may take more arguments (such as labels/values)
 * in Assembly
 */
public class POPInstruction implements Instruction {

    private final String name = "POP";
    private final RegisterName destinationRegister;
    private final StackRegisterName sourceRegister;
    private final FixedBitSet binary;

    public POPInstruction(RegisterName destinationRegister, StackRegisterName sourceRegister) {
        this.destinationRegister = destinationRegister;
        this.sourceRegister = sourceRegister;

        FixedBitSet bits = new FixedBitSet("0100 0000 0010 0 000");

        FixedBitSet typeBits = destinationRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i, typeBits.get(i));
        }

        typeBits = sourceRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i + 3, typeBits.get(i));
        }

        this.binary = bits;
    }


    @Override
    public String getName() {
        return name;
    }

    public RegisterName getDestinationRegister() {
        return destinationRegister;
    }

    public StackRegisterName getSourceRegister() {
        return sourceRegister;
    }

    @Override
    public FixedBitSet toBinary() {
        return binary;
    }
}
