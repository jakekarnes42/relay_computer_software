package org.karnes.homebrew.emulator.component.alu.logicunit;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * Binary Opcodes for Logic Unit operations
 */
public enum LU_OPCODE {
    XOR(new FixedBitSet("100")),
    OR(new FixedBitSet("101")),
    AND(new FixedBitSet("110")),
    NOT(new FixedBitSet("111")),
    OFF(new FixedBitSet("000"));

    public static final int WIDTH = 3;
    private final FixedBitSet bitSet;

    LU_OPCODE(FixedBitSet bitSet) {
        if (bitSet.size() != WIDTH) {
            throw new IllegalArgumentException("LU_OPCODE must be created from a FixedBitSet of size " + WIDTH);
        }
        this.bitSet = bitSet;
    }

    /**
     * Converts the LU_OPCODE to its binary representation as a FixedBitSet
     *
     * @return the binary representation of the opcode
     */
    public FixedBitSet toBitSet() {
        return bitSet;
    }

    /**
     * Gets the corresponding LU_OPCODE for the given binary representation.
     *
     * @param input The binary repesentation
     * @return The corresponding LU_OPCODE
     * @throws IllegalArgumentException if the binary representation doesn't correspond to a valid LU_OPCODE
     */
    public static LU_OPCODE fromBitSet(FixedBitSet input) throws IllegalArgumentException {
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
