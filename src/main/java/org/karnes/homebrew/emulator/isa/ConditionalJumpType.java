package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

public enum ConditionalJumpType {
    UJMP(new ArbitraryBitSet("00000")),
    JZ(new ArbitraryBitSet("00001")),
    JS(new ArbitraryBitSet("00010")),
    JSZ(new ArbitraryBitSet("00011")),
    JO(new ArbitraryBitSet("00100")),
    JOZ(new ArbitraryBitSet("00101")),
    JOS(new ArbitraryBitSet("00110")),
    JOSZ(new ArbitraryBitSet("00111")),
    JC(new ArbitraryBitSet("01000")),
    JCZ(new ArbitraryBitSet("01001")),
    JCS(new ArbitraryBitSet("01010")),
    JCSZ(new ArbitraryBitSet("01011")),
    JCO(new ArbitraryBitSet("01100")),
    JCOZ(new ArbitraryBitSet("01101")),
    JCOS(new ArbitraryBitSet("01110")),
    JCOSZ(new ArbitraryBitSet("01111")),
    JN(new ArbitraryBitSet("10000")),
    JNZ(new ArbitraryBitSet("10001")),
    JNS(new ArbitraryBitSet("10010")),
    JNSZ(new ArbitraryBitSet("10011")),
    JNO(new ArbitraryBitSet("10100")),
    JNOZ(new ArbitraryBitSet("10101")),
    JNOS(new ArbitraryBitSet("10110")),
    JNOSZ(new ArbitraryBitSet("10111")),
    JNC(new ArbitraryBitSet("11000")),
    JNCZ(new ArbitraryBitSet("11001")),
    JNCS(new ArbitraryBitSet("11010")),
    JNCSZ(new ArbitraryBitSet("11011")),
    JNCO(new ArbitraryBitSet("11100")),
    JNCOZ(new ArbitraryBitSet("11101")),
    JNCOS(new ArbitraryBitSet("11110")),
    JNCOSZ(new ArbitraryBitSet("11111"));


    private final FixedBitSet bitSet;

    ConditionalJumpType(FixedBitSet bitSet) {
        this.bitSet = bitSet;
    }

    public static ConditionalJumpType fromBitSet(FixedBitSet bitSet) {
        if (bitSet.size() != 5) {
            throw new IllegalArgumentException("BitSet must be size 5 to convert to a ConditionalJumpType");
        }
        for (ConditionalJumpType name : values()) {
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
