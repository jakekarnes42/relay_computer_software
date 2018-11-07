package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;

public class NOTInstruction extends AbstractTwoRegALUInstruction {
    public NOTInstruction(RegisterName destination, RegisterName source) {
        super("NOT", destination, source, new FixedBitSet("0001 111 000 000 000"));
    }
}
