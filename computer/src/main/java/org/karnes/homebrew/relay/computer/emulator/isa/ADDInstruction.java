package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterName;

public class ADDInstruction extends AbstractThreeRegInstruction {
    public ADDInstruction(RegisterName destinationRegister, RegisterName source2Register, RegisterName source1Register) {
        super("ADD", destinationRegister, source2Register, source1Register, new FixedBitSet("0001 000 000 000 000"));
    }
}
