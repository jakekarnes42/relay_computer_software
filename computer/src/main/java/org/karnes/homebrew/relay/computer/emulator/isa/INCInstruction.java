package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;

public class INCInstruction extends AbstractTwoRegALUInstruction {
    public INCInstruction(RegisterName destination, RegisterName source) {
        super("INC", destination, source, new FixedBitSet("0001 001 000 000 000"));
    }
}
