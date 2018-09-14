package org.karnes.homebrew.emulator.component.alu.arithmeticunit;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * Binary Opcodes for Arithmetic Unit operations
 */
public enum AU_OPCODE {
    ADD(new FixedBitSet("100")),
    INC(new FixedBitSet("101")),
    SUB(new FixedBitSet("110")),
    DEC(new FixedBitSet("111")),
    OFF(new FixedBitSet("000"));

    public static final int WIDTH = 3;

    private final FixedBitSet bitSet;

    AU_OPCODE(FixedBitSet bitSet) {
        if (bitSet.size() != WIDTH) {
            throw new IllegalArgumentException("AU_OPCODE must be created from a FixedBitSet of size " + WIDTH);
        }
        this.bitSet = bitSet;
    }

    /**
     * Converts the AU_OPCODE to its binary representation as a FixedBitSet
     *
     * @return the binary representation of the opcode
     */
    public FixedBitSet toBitSet() {
        return bitSet;
    }

    /**
     * Gets the corresponding AU_OPCODE for the given binary representation.
     *
     * @param input The binary repesentation
     * @return The corresponding AU_OPCODE
     * @throws IllegalArgumentException if the binary representation doesn't correspond to a valid AU_OPCODE
     */
    public static AU_OPCODE fromBitSet(FixedBitSet input) throws IllegalArgumentException {
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
