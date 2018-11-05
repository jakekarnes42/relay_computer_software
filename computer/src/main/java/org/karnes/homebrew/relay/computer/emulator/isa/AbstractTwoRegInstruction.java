package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterName;

/**
 * Abstract class for instructions which require two registers argument. They may take more arguments (such as labels/values)
 * in Assembly
 */
public class AbstractTwoRegInstruction implements Instruction {

    String name;
    RegisterName destinationRegister;
    RegisterName sourceRegister;
    FixedBitSet binary;

    AbstractTwoRegInstruction(String name, RegisterName destinationRegister, RegisterName sourceRegister, FixedBitSet template) {
        this.name = name;
        this.destinationRegister = destinationRegister;
        this.sourceRegister = sourceRegister;

        FixedBitSet bits = template.copy();

        FixedBitSet typeBits = sourceRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i, typeBits.get(i));
        }

        typeBits = destinationRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i+3, typeBits.get(i));
        }

        this.binary = bits;
    }

    AbstractTwoRegInstruction() {

    }

    @Override
    public String getName() {
        return name;
    }

    public RegisterName getDestinationRegister() {
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
