package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;

import static java.lang.Short.SIZE;

public class NOPInstruction extends AbstractNoArgInstruction {
    public NOPInstruction() {
        super("NOP", new FixedBitSet(SIZE));
    }
}
