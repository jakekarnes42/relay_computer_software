package org.karnes.homebrew.relay.computer.emulator.isa;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

public enum ConditionalJumpType {
    UJMP(new FixedBitSet("00000")),
    JZ(new FixedBitSet("00001")),
    JS(new FixedBitSet("00010")),
    JSZ(new FixedBitSet("00011")),
    JO(new FixedBitSet("00100")),
    JOZ(new FixedBitSet("00101")),
    JOS(new FixedBitSet("00110")),
    JOSZ(new FixedBitSet("00111")),
    JC(new FixedBitSet("01000")),
    JCZ(new FixedBitSet("01001")),
    JCS(new FixedBitSet("01010")),
    JCSZ(new FixedBitSet("01011")),
    JCO(new FixedBitSet("01100")),
    JCOZ(new FixedBitSet("01101")),
    JCOS(new FixedBitSet("01110")),
    JCOSZ(new FixedBitSet("01111")),
    JN(new FixedBitSet("10000")),
    JNZ(new FixedBitSet("10001")),
    JNS(new FixedBitSet("10010")),
    JNSZ(new FixedBitSet("10011")),
    JNO(new FixedBitSet("10100")),
    JNOZ(new FixedBitSet("10101")),
    JNOS(new FixedBitSet("10110")),
    JNOSZ(new FixedBitSet("10111")),
    JNC(new FixedBitSet("11000")),
    JNCZ(new FixedBitSet("11001")),
    JNCS(new FixedBitSet("11010")),
    JNCSZ(new FixedBitSet("11011")),
    JNCO(new FixedBitSet("11100")),
    JNCOZ(new FixedBitSet("11101")),
    JNCOS(new FixedBitSet("11110")),
    JNCOSZ(new FixedBitSet("11111"));


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
