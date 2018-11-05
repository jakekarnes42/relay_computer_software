package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterName;

public class DECInstruction extends AbstractTwoRegALUInstruction {
    public DECInstruction(RegisterName destination, RegisterName source) {
        super("DEC", destination, source, new FixedBitSet("0001 011 000 000 000"));
    }
}
