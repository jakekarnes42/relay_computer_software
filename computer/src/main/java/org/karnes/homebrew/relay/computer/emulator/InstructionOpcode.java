package org.karnes.homebrew.relay.computer.emulator;


public enum InstructionOpcode {
    //No-arg
    NOP((short) 0b0000_0000_0000_0000, (byte) 16),
    HALT((short) 0b1111_1111_1111_1111, (byte) 16),

    //MOV-based
    MOV((short) 0b1111_1111_11_000_000, (byte) 10),
    CLR((short) 0b1111_1111_11_000_000, (byte) 10),

    //Arithmetic Unit
    ADD((short) 0b0001_000_000_000_000, (byte) 7),
    INC((short) 0b0001_001_000_000_000, (byte) 7),
    CMP((short) 0b0001_010_000_000_000, (byte) 7),
    SUB((short) 0b0001_010_000_000_000, (byte) 7),
    DEC((short) 0b0001_011_000_000_000, (byte) 7),

    //Logic Unit
    XOR((short) 0b0001_100_000_000_000, (byte) 7),
    OR((short)  0b0001_101_000_000_000, (byte) 7),
    AND((short) 0b0001_110_000_000_000, (byte) 7),
    NOT((short) 0b0001_111_000_000_000, (byte) 7),

    // Jumps
    JMP((short) 0b0010_0001_0000_0_111, (byte) 16),
    JZ((short) 0b0000_1000_000_0_0001, (byte) 16),
    JS((short) 0b0000_1000_000_0_0010, (byte) 16),
    JO((short) 0b0000_1000_000_0_0100, (byte) 16),
    JC((short) 0b0000_1000_000_0_1000, (byte) 16),
    JNZ((short) 0b0000_1000_000_1_0001, (byte) 16),
    JNS((short) 0b0000_1000_000_1_0010, (byte) 16),
    JNO((short) 0b0000_1000_000_1_0100, (byte) 16),
    JNC((short) 0b0000_1000_000_1_1000, (byte) 16),

    //Memory
    LOAD((short) 0b0010_0001_0000_0_000, (byte) 13),
    STORE((short) 0b0010_0010_00_000_000, (byte) 10),
    FETCH((short) 0b0010_0100_00_000_000, (byte) 10),

    //Stack
    PUSH((short) 0b0100_0000_0001_0_000, (byte) 12),
    RET((short) 0b0100_0000_0010_0_111, (byte) 16),
    POP((short) 0b0100_0000_0010_0_000, (byte) 12),
    CALL((short) 0b0100_0000_0100_0_000, (byte) 12),

    //I/O
    TIN((short) 0b1000_0000_0000_0_000, (byte) 16),
    TOUT((short) 0b1000_1000_0000_0_000, (byte) 16),
    WRDIN((short) 0b1000_0000_0000_1_000, (byte) 13),
    WRDOUT((short) 0b1000_1000_0000_1_000, (byte) 13);


    private final short instructionOpcodeMask;

    private final byte numMaskBits;

    InstructionOpcode(short instructionOpcodeMask, byte numMaskBits) {
        this.instructionOpcodeMask = instructionOpcodeMask;
        this.numMaskBits = numMaskBits;
    }

    public short getInstructionOpcodeMask() {
        return instructionOpcodeMask;
    }

    public static InstructionOpcode getOpcode(short instruction) {
        for (InstructionOpcode opcode : InstructionOpcode.values()) {
            short opcodeMask = (short) (opcode.instructionOpcodeMask >> (16 - opcode.numMaskBits));
            short instructionMask = (short) (instruction >> (16 - opcode.numMaskBits));
            if ((opcodeMask ^ instructionMask) == 0) {
                return opcode;
            }
        }
        throw new IllegalArgumentException("Unable to find corresponding opcode for instruction: "
                + Integer.toBinaryString(0xFFFF & instruction));
    }
}
