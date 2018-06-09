package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;

public class JMPInstruction extends AbstractNoArgInstruction {
    JMPInstruction() {
        super("JMP", new BitSet16("0010 0001 0000 0 111"));
    }
}
