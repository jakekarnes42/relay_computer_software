package org.karnes.homebrew.emulator.component.logicunit;

import org.karnes.homebrew.bitset.FixedBitSet;

public enum LU_OPCODE {
    XOR(new FixedBitSet("100")),
    OR(new FixedBitSet("101")),
    AND(new FixedBitSet("110")),
    NOT(new FixedBitSet("111")),
    OFF(new FixedBitSet("000"));


    private final FixedBitSet bitSet;

    LU_OPCODE(FixedBitSet bitSet) {
        if (bitSet.size() != 3) {
            throw new IllegalArgumentException("LU_OPCODE must be created from a FixedBitSet of size 3");
        }
        this.bitSet = bitSet;
    }

    public FixedBitSet toBitSet() {
        return bitSet;
    }

    public static LU_OPCODE fromBitSet(FixedBitSet input) {
        if (input.get(2) == false) {
            return OFF;
        } else if (input.equals(new FixedBitSet("100"))) {
            return XOR;
        } else if (input.equals(new FixedBitSet("101"))) {
            return OR;
        } else if (input.equals(new FixedBitSet("110"))) {
            return AND;
        } else if (input.equals(new FixedBitSet("111"))) {
            return NOT;
        } else {
            throw new IllegalArgumentException("Invalid BitSet for LU_OPCODE " + input);
        }
    }


}
