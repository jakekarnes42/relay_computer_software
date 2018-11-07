package org.karnes.homebrew.relay.computer.emulator.register;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

/**
 * The names of the registers available to the Relay CPU.
 */
public enum RegisterName {
    AX(new FixedBitSet("000")),
    BX(new FixedBitSet("001")),
    CX(new FixedBitSet("010")),
    DX(new FixedBitSet("011")),
    EX(new FixedBitSet("100")),
    SP(new FixedBitSet("101")),
    RP(new FixedBitSet("110")),
    PC(new FixedBitSet("111"));

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
