package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.bitset.FixedBitSet;

public class ConditionalJMPInstruction implements Instruction {
    private final static BitSet16 template = new BitSet16("0000 1000 000 0 0000");
    private final ConditionalJumpType type;
    private final String name;
    private final BitSet16 binary;

    public ConditionalJMPInstruction(ConditionalJumpType type) {
        this.type = type;
        this.name = type.name();

        BitSet16 bits = template.copy();
        FixedBitSet typeBits = type.getBitSet();
        for (int i = 0; i < typeBits.size(); i++) {
            bits = bits.set(i, typeBits.get(i));
        }

        this.binary = bits;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BitSet16 toBinary() {
        return binary;
    }
}
