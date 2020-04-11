package org.karnes.homebrew.relay.common.emulator.component.alu.shiftunit;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

/**
 * Binary Opcodes for Shift Unit operations
 */
public enum SU_OPCODE {
    ROR(new FixedBitSet("100")),
    ROL(new FixedBitSet("101")),
    RCR(new FixedBitSet("110")),
    RCL(new FixedBitSet("111")),
    OFF(new FixedBitSet("000"));

    public static final int WIDTH = 3;
    private final FixedBitSet bitSet;

    SU_OPCODE(FixedBitSet bitSet) {
        if (bitSet.size() != WIDTH) {
            throw new IllegalArgumentException("SU_OPCODE must be created from a FixedBitSet of size " + WIDTH);
        }
        this.bitSet = bitSet;
    }

    /**
     * Converts the SU_OPCODE to its binary representation as a FixedBitSet
     *
     * @return the binary representation of the opcode
     */
    public FixedBitSet toBitSet() {
        return bitSet;
    }

    /**
     * Gets the corresponding SU_OPCODE for the given binary representation.
     *
     * @param input The binary repesentation
     * @return The corresponding SU_OPCODE
     * @throws IllegalArgumentException if the binary representation doesn't correspond to a valid SU_OPCODE
     */
    public static SU_OPCODE fromBitSet(FixedBitSet input) throws IllegalArgumentException {
        if (input.size() != WIDTH) {
            throw new IllegalArgumentException("SU_OPCODE must be created from a FixedBitSet of size " + WIDTH);
        }

        if (input.get(2) == false) {
            return OFF;
        } else if (input.equals(new FixedBitSet("100"))) {
            return ROR;
        } else if (input.equals(new FixedBitSet("101"))) {
            return ROL;
        } else if (input.equals(new FixedBitSet("110"))) {
            return RCR;
        } else if (input.equals(new FixedBitSet("111"))) {
            return RCL;
        } else {
            throw new IllegalArgumentException("Invalid BitSet for SU_OPCODE " + input);
        }
    }


}
