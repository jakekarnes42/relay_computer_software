package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.register.RegisterName;

/**
 * Abstract class for instructions which require two registers argument. They may take more arguments (such as labels/values)
 * in Assembly
 */
public class AbstractThreeRegInstruction implements Instruction {

    String name;
    RegisterName destinationRegister;
    RegisterName source1Register;
    RegisterName source2Register;
    BitSet16 binary;

    AbstractThreeRegInstruction(String name, RegisterName destinationRegister, RegisterName source2Register, RegisterName source1Register, BitSet16 template) {
        this.name = name;
        this.source2Register = source2Register;
        this.source1Register = source1Register;
        this.destinationRegister = destinationRegister;

        BitSet16 bits = template.copy();

        FixedBitSet typeBits = source1Register.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i, typeBits.get(i));
        }

        typeBits = source2Register.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i + 3, typeBits.get(i));
        }

        typeBits = destinationRegister.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i + 6, typeBits.get(i));
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

    public RegisterName getSource2Register() {
        return source2Register;
    }

    public RegisterName getSource1Register() {
        return source1Register;
    }

    @Override
    public BitSet16 toBinary() {
        return binary;
    }
}
