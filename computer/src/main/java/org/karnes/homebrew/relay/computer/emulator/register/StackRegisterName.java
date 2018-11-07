package org.karnes.homebrew.relay.computer.emulator.register;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

/**
 * The names of the stack registers available to the Relay CPU.
 */
public enum StackRegisterName {
    SP(new FixedBitSet("0")),
    RP(new FixedBitSet("1"));

    private final FixedBitSet bitSet;

    StackRegisterName(FixedBitSet bitSet) {
        this.bitSet = bitSet;
    }

    public static StackRegisterName fromBitSet(FixedBitSet bitSet) {
        if (bitSet.size() != 1) {
            throw new IllegalArgumentException("BitSet must be size 1 to convert to a StackRegisterName");
        }
        for (StackRegisterName name : values()) {
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
