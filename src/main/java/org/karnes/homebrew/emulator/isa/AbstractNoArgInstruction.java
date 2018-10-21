package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * Abstract class for instructions which require no register arguments. They may take arguments (such as labels/values)
 * in Assembly
 */
public class AbstractNoArgInstruction implements Instruction {

    private final String name;
    private final FixedBitSet binary;

    AbstractNoArgInstruction(String name, FixedBitSet binary) {
        this.name = name;
        this.binary = binary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FixedBitSet toBinary() {
        return binary;
    }
}
