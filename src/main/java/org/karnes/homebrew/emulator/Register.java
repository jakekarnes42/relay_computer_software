package org.karnes.homebrew.emulator;


public enum Register {
    /**
     * Holds the current executing instruction
     */
    INST((short) 0, (short) 0, (short) 0, false), //No masks since it should be in instructions
    /**
     * General purpose.
     */
    AX((short) 0b0000000_000_000_000, (short) 0b00000000_00_000_000, (short) 0b00000000_00000_000, false),
    /**
     * General purpose.
     */
    BX((short) 0b0000000_001_000_000, (short) 0b00000000_00_001_000, (short) 0b00000000_00000_001, false),
    /**
     * General purpose.
     */
    CX((short) 0b0000000_010_000_000, (short) 0b00000000_00_010_000, (short) 0b00000000_00000_010, false),
    /**
     * General purpose.
     */
    DX((short) 0b0000000_011_000_000, (short) 0b00000000_00_011_000, (short) 0b00000000_00000_011, false),
    /**
     * General purpose.
     */
    EX((short) 0b0000000_100_000_000, (short) 0b00000000_00_100_000, (short) 0b00000000_00000_100, false),
    /**
     * Data Stack Pointer. Points to the memory address of the top most item on the data stack.
     */
    SP((short) 0b0000000_101_000_000, (short) 0b00000000_00_101_000, (short) 0b00000000_00000_101, true),
    /**
     * Return Stack Pointer. Points to the memory address of the top most item on the return stack.
     */
    RP((short) 0b0000000_110_000_000, (short) 0b00000000_00_110_000, (short) 0b00000000_00000_110, true),
    /**
     * The program counter register.
     * At the start of fetch-decode-execute cycle,
     * it points to the memory address containing the instruction to execute in that cycle.
     * By the end of the execute phase, it points to the memory address to execute.
     */
    PC((short) 0b0000000_111_000_000, (short) 0b00000000_00_111_000, (short) 0b00000000_00000_111, false),
    /**
     * Used internally by the CPU as the "scratch pad." Temporary values may be written and trashed here for other ops.
     * This is the input to the ALU.
     */
    TMP1X((short) 0, (short) 0, (short) 0, false), //No masks since it should be in instructions
    /**
     * Used internally by the CPU as the "scratch pad." Temporary values may be written and trashed here for other ops.
     * This is the input to the ALU.
     */
    TMP2X((short) 0, (short) 0, (short) 0, false); //No masks since it should be in instructions

    private final short firstPositionInstructionMask;
    private final short secondPositionInstructionMask;
    private final short thirdPositionInstructionMask;
    private final boolean isStackRegister;

    Register(short firstPositionInstructionMask, short secondPositionInstructionMask, short thirdPositionInstructionMask, boolean isStackRegister) {
        this.firstPositionInstructionMask = firstPositionInstructionMask;
        this.secondPositionInstructionMask = secondPositionInstructionMask;
        this.thirdPositionInstructionMask = thirdPositionInstructionMask;
        this.isStackRegister = isStackRegister;
    }

    public short getFirstPositionInstructionMask() {
        return firstPositionInstructionMask;
    }

    public short getSecondPositionInstructionMask() {
        return secondPositionInstructionMask;
    }

    public short getThirdPositionInstructionMask() {
        return thirdPositionInstructionMask;
    }

    public boolean isStackRegister() {
        return isStackRegister;
    }

    public short getStackRegisterInstructionMask() {
        //Check if it's a stack register
        if (!this.isStackRegister) {
            throw new IllegalArgumentException("Cannot get stack register mask from non-stack register: " + this.toString());
        }

        //Get the stack register mask
        if (this == SP) {
            return 0b00000000_0000_0_000;
        } else if (this == RP) {
            return 0b00000000_0000_1_000;
        } else {
            //Uh-oh, we didn't define a mask for this stack register.
            throw new IllegalStateException("There is no stack register instruction mask defined for register: " + this.toString());
        }
    }
}
