package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;
import org.karnes.homebrew.relay.computer.emulator.register.StackRegisterName;

/**
 * Abstract class for instructions which require two registers argument. They may take more arguments (such as labels/values)
 * in Assembly
 */
public class PUSHInstruction implements Instruction {

    private static final String name = "PUSH";
    private final StackRegisterName destinationRegister;
    private final RegisterName sourceRegister;
    private final FixedBitSet binary;

    public PUSHInstruction(StackRegisterName destinationRegister, RegisterName sourceRegister) {
        this.destinationRegister = destinationRegister;
        this.sourceRegister = sourceRegister;

        FixedBitSet bits = new FixedBitSet("0100 0000 0001 0 000");

        FixedBitSet typeBits = sourceRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i, typeBits.get(i));
        }

        typeBits = destinationRegister.getBitSet();
        for (int i =0; i < typeBits.size(); i++) {
            bits = bits.set(i+3, typeBits.get(i));
        }

        this.binary = bits;
    }


    @Override
    public String getName() {
        return name;
    }

    public StackRegisterName getDestinationRegister() {
        return destinationRegister;
    }

    public RegisterName getSourceRegister() {
        return sourceRegister;
    }

    @Override
    public FixedBitSet toBinary() {
        return binary;
    }
}
