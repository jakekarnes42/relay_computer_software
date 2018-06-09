package org.karnes.homebrew.emulator.component.register;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * The names of the stack registers available to the Relay CPU.
 */
public enum StackRegisterName {
    SP(new ArbitraryBitSet("0")),
    RP(new ArbitraryBitSet("1"));

    private final ArbitraryBitSet bitSet;

    StackRegisterName(ArbitraryBitSet bitSet) {
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

    public ArbitraryBitSet getBitSet() {
        return bitSet;
    }
}
