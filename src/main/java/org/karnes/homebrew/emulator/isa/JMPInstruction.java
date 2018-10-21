package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.FixedBitSet;

public class JMPInstruction extends AbstractNoArgInstruction {
    public JMPInstruction() {
        super("JMP", new FixedBitSet("0010 0001 0000 0 111"));
    }
}
