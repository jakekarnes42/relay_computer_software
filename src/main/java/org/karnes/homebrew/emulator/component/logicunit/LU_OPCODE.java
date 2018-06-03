package org.karnes.homebrew.emulator.component.logicunit;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

public enum LU_OPCODE {
    XOR(new ArbitraryBitSet("00")), OR(new ArbitraryBitSet("01")), AND(new ArbitraryBitSet("10")), NOT(new ArbitraryBitSet("11"));


    private final FixedBitSet bitSet;

    LU_OPCODE(FixedBitSet bitSet) {
        if (bitSet.size() != 2) {
            throw new IllegalArgumentException("LU_OPCODE must be created from a FixedBitSet of size 2");
        }
        this.bitSet = bitSet;
    }

    public FixedBitSet toBitSet() {
        return bitSet;
    }

    public static LU_OPCODE fromBitSet(FixedBitSet input) {
        if (input.equals(new ArbitraryBitSet("00"))) {
            return XOR;
        } else if (input.equals(new ArbitraryBitSet("01"))) {
            return OR;
        } else if (input.equals(new ArbitraryBitSet("10"))) {
            return AND;
        } else if (input.equals(new ArbitraryBitSet("11"))) {
            return NOT;
        } else {
            throw new IllegalArgumentException("Invalid BitSet for LU_OPCODE " + input);
        }
    }


}
