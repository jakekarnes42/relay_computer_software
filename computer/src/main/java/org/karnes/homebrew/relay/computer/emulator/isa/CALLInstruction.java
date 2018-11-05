package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.register.StackRegisterName;

public class CALLInstruction extends AbstractOneStackRegInstruction {
    public CALLInstruction(StackRegisterName register) {
        super("CALL", register, new FixedBitSet("0100 0000 0100 0 000"));
    }
}
