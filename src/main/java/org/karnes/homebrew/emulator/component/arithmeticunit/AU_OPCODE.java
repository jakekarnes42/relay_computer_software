package org.karnes.homebrew.emulator.component.arithmeticunit;

import org.karnes.homebrew.bitset.FixedBitSet;

public enum AU_OPCODE {
    ADD(new FixedBitSet("100")),
    INC(new FixedBitSet("101")),
    SUB(new FixedBitSet("110")),
    DEC(new FixedBitSet("111")),
    OFF(new FixedBitSet("000"));


    private final FixedBitSet bitSet;

    AU_OPCODE(FixedBitSet bitSet) {
        if (bitSet.size() != 3) {
            throw new IllegalArgumentException("AU_OPCODE must be created from a FixedBitSet of size 3");
        }
        this.bitSet = bitSet;
    }

    public FixedBitSet toBitSet() {
        return bitSet;
    }

    public static AU_OPCODE fromBitSet(FixedBitSet input) {
        if (input.get(2) == false) {
            return OFF;
        } else if (input.equals(new FixedBitSet("100"))) {
            return ADD;
        } else if (input.equals(new FixedBitSet("101"))) {
            return INC;
        } else if (input.equals(new FixedBitSet("110"))) {
            return SUB;
        } else if (input.equals(new FixedBitSet("111"))) {
            return DEC;
        } else {
            throw new IllegalArgumentException("Invalid BitSet for AU_OPCODE " + input);
        }
    }


}
