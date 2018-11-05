package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

import static java.lang.Short.SIZE;

public class NOPInstruction extends AbstractNoArgInstruction {
    public NOPInstruction() {
        super("NOP", new FixedBitSet(SIZE));
    }
}
