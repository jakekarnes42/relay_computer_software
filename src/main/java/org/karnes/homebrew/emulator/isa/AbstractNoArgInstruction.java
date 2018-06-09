package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;

/**
 * Abstract class for instructions which require no register arguments. They may take arguments (such as labels/values)
 * in Assembly
 */
public class AbstractNoArgInstruction implements Instruction {

    private final String name;
    private final BitSet16 binary;

    AbstractNoArgInstruction(String name, BitSet16 binary) {
        this.name = name;
        this.binary = binary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BitSet16 toBinary() {
        return binary;
    }
}
