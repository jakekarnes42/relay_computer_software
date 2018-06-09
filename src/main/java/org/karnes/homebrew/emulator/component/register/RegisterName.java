package org.karnes.homebrew.emulator.component.register;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * The names of the registers available to the Relay CPU.
 */
public enum RegisterName {
    AX(new ArbitraryBitSet("000")),
    BX(new ArbitraryBitSet("001")),
    CX(new ArbitraryBitSet("010")),
    DX(new ArbitraryBitSet("011")),
    EX(new ArbitraryBitSet("100")),
    SP(new ArbitraryBitSet("101")),
    RP(new ArbitraryBitSet("110")),
    PC(new ArbitraryBitSet("111"));

    private final FixedBitSet bitSet;

    RegisterName(FixedBitSet bitSet) {
        this.bitSet = bitSet;
    }

    public static RegisterName fromBitSet(FixedBitSet bitSet) {
        if (bitSet.size() != 3) {
            throw new IllegalArgumentException("BitSet must be size 3 to convert to a RegisterName");
        }
        for (RegisterName name : values()) {
            if (name.bitSet.equals(bitSet)) {
                return name;
            }
        }

        return null;
    }


    public FixedBitSet getBitSet() {
        return bitSet;
    }
}
