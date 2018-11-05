package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

public class JMPInstruction extends AbstractNoArgInstruction {
    public JMPInstruction() {
        super("JMP", new FixedBitSet("0010 0001 0000 0 111"));
    }
}
