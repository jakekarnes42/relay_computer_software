package org.karnes.homebrew.emulator;

import static org.karnes.homebrew.emulator.StackReg.STACK_RP;
import static org.karnes.homebrew.emulator.StackReg.STACK_SP;

/**
 * An emulator for the Relay-based computer
 */
public class RelayComputer {

    //1 bit status registers
    /**
     * True if the most recent ALU operation result was all zero.
     */
    private boolean zero = false;
    /**
     * True if B+C in the last ALU operation would have resulted in UNSIGNED overflow
     */
    private boolean carry = false;
    /**
     * True if the result of the last ALU operation was negative (i.e. the highest bit was 1)
     */
    private boolean sign = false;
    /**
     * True if B+C in the last ALU operation would have resulted in SIGNED overflow
     */
    private boolean overflow = false;


    //16 bit registers. Using char since they are unsigned 16 bit integers
    /**
     * Holds the current executing instruction
     */
    private short INST = 0;
    /**
     * The program counter register.
     * At the start of fetch-decode-execute cycle,
     * it points to the memory address containing the instruction to execute in that cycle.
     * By the end of the execute phase, it points to the memory address to execute.
     */
    private short PC = 0;
    /**
     * Data Stack Pointer. Points to the memory address of the top most item on the data stack.
     */
    private short SP = 0;
    /**
     * Return Stack Pointer. Points to the memory address of the top most item on the return stack.
     */
    private short RP = 0;
    /**
     * General purpose.
     */
    private short AX = 0;
    /**
     * General purpose.
     */
    private short BX = 0;
    /**
     * General purpose.
     */
    private short CX = 0;
    /**
     * General purpose.
     */
    private short DX = 0;
    /**
     * General purpose.
     */
    private short EX = 0;
    /**
     * Used internally by the CPU as the "scratch pad." Temporary values may be written and trashed here for other ops.
     * This is the input to the ALU.
     */
    private short TMP1X = 0;
    /**
     * Used internally by the CPU as the "scratch pad." Temporary values may be written and trashed here for other ops.
     * This is the input to the ALU.
     */
    private short TMP2X = 0;

    /**
     * Main memory
     */
    private short[] mainMemory = new short[(1 << 16)];

    /**
     * I/O Device
     */
    private IODevice ioDevice = new JavaSimulatedIODevice();

    /**
     * Emulator specific: Indicator that the computer should stop
     */
    private boolean halted = false;


    public void start() {
        while (!halted) {
            //Fetch: Loads the byte at memory address pointed to by PC, into the INST register.
            INST = mainMemory[(char) PC];

            //Increment: Increment the PC register
            PC = (short) (PC + 1);

            //Execute
            executeCurrentInstruction();
        }
    }

    private void executeCurrentInstruction() {
        InstructionOpcode currentOpcode = InstructionOpcode.getOpcode(INST);
        switch (currentOpcode) {
            case NOP:
                executeNOPInstruction();
                break;
            case HALT:
            case MOV:
            case CLR:
                executeMOVInstruction();
                break;
            case INC:
                executeINCInstruction();
                break;
            case DEC:
                executeDECInstruction();
                break;
            case NOT:
                executeNOTInstruction();
                break;
            case ROL:
                executeROLInstruction();
                break;
            case ADD:
                executeADDInstruction();
                break;
            case AND:
                executeANDInstruction();
                break;
            case OR:
                executeORInstruction();
                break;
            case XOR:
                executeXORInstruction();
                break;
            case CMP:
            case SUB:
                executeCMPSUBInstruction();
                break;
            case LOAD:
            case JMP:
                executeLOADJMPInstruction();
                break;
            case JNEG:
            case JZ:
            case JC:
            case JO:
            case JNNEG:
            case JNZ:
            case JNC:
            case JNO:
                executeConditionalJumpInstruction();
                break;
            case STORE:
                executeSTOREInstruction();
                break;
            case FETCH:
                executeFETCHInstruction();
                break;
            case PUSH:
                executePUSHInstruction();
                break;
            case POP:
            case RET:
                executePOPInstruction();
                break;
            case CALL:
                executeCALLInstruction();
                break;
            case WRDIN:
                executeWRDINInstruction();
                break;
            case WRDOUT:
                executeWRDOUTInstruction();
                break;
        }
    }


    /*
     * NO-OP. Do nothing.
     */
    private void executeNOPInstruction() {
        return; //Intentionally empty
    }


    /*
     * MOVE. Move values between registers.
     */
    private void executeMOVInstruction() {
        switch (INST) {
            case (short) 0b1111_1111_11_000_000: //MOV AX AX | CLR AX
                AX = 0;
                break;
            case (short) 0b1111_1111_11_000_001: //MOV AX BX
                AX = BX;
                break;
            case (short) 0b1111_1111_11_000_010: //MOV AX CX
                AX = CX;
                break;
            case (short) 0b1111_1111_11_000_011: //MOV AX DX
                AX = DX;
                break;
            case (short) 0b1111_1111_11_000_100: //MOV AX EX
                AX = EX;
                break;
            case (short) 0b1111_1111_11_000_101: //MOV AX SP
                AX = SP;
                break;
            case (short) 0b1111_1111_11_000_110: //MOV AX RP
                AX = RP;
                break;
            case (short) 0b1111_1111_11_000_111: //MOV AX PC
                AX = PC;
                break;
            case (short) 0b1111_1111_11_001_000: //MOV BX AX
                BX = AX;
                break;
            case (short) 0b1111_1111_11_001_001: //MOV BX BX | CLR BX
                BX = 0;
                break;
            case (short) 0b1111_1111_11_001_010: //MOV BX CX
                BX = CX;
                break;
            case (short) 0b1111_1111_11_001_011: //MOV BX DX
                BX = DX;
                break;
            case (short) 0b1111_1111_11_001_100: //MOV BX EX
                BX = EX;
                break;
            case (short) 0b1111_1111_11_001_101: //MOV BX SP
                BX = SP;
                break;
            case (short) 0b1111_1111_11_001_110: //MOV BX RP
                BX = RP;
                break;
            case (short) 0b1111_1111_11_001_111: //MOV BX PC
                BX = PC;
                break;
            case (short) 0b1111_1111_11_010_000: //MOV CX AX
                CX = AX;
                break;
            case (short) 0b1111_1111_11_010_001: //MOV CX BX
                CX = BX;
                break;
            case (short) 0b1111_1111_11_010_010: //MOV CX CX | CLR CX
                CX = 0;
                break;
            case (short) 0b1111_1111_11_010_011: //MOV CX DX
                CX = DX;
                break;
            case (short) 0b1111_1111_11_010_100: //MOV CX EX
                CX = EX;
                break;
            case (short) 0b1111_1111_11_010_101: //MOV CX SP
                CX = SP;
                break;
            case (short) 0b1111_1111_11_010_110: //MOV CX RP
                CX = RP;
                break;
            case (short) 0b1111_1111_11_010_111: //MOV CX PC
                CX = PC;
                break;
            case (short) 0b1111_1111_11_011_000: //MOV DX AX
                DX = AX;
                break;
            case (short) 0b1111_1111_11_011_001: //MOV DX BX
                DX = BX;
                break;
            case (short) 0b1111_1111_11_011_010: //MOV DX CX
                DX = CX;
                break;
            case (short) 0b1111_1111_11_011_011: //MOV DX DX | CLR DX
                DX = 0;
                break;
            case (short) 0b1111_1111_11_011_100: //MOV DX EX
                DX = EX;
                break;
            case (short) 0b1111_1111_11_011_101: //MOV DX SP
                DX = SP;
                break;
            case (short) 0b1111_1111_11_011_110: //MOV DX RP
                DX = RP;
                break;
            case (short) 0b1111_1111_11_011_111: //MOV DX PC
                DX = PC;
                break;
            case (short) 0b1111_1111_11_100_000: //MOV EX AX
                EX = AX;
                break;
            case (short) 0b1111_1111_11_100_001: //MOV EX BX
                EX = BX;
                break;
            case (short) 0b1111_1111_11_100_010: //MOV EX CX
                EX = CX;
                break;
            case (short) 0b1111_1111_11_100_011: //MOV EX DX
                EX = DX;
                break;
            case (short) 0b1111_1111_11_100_100: //MOV EX EX | CLR EX
                EX = 0;
                break;
            case (short) 0b1111_1111_11_100_101: //MOV EX SP
                EX = SP;
                break;
            case (short) 0b1111_1111_11_100_110: //MOV EX RP
                EX = RP;
                break;
            case (short) 0b1111_1111_11_100_111: //MOV EX PC
                EX = PC;
                break;
            case (short) 0b1111_1111_11_101_000: //MOV SP AX
                SP = AX;
                break;
            case (short) 0b1111_1111_11_101_001: //MOV SP BX
                SP = BX;
                break;
            case (short) 0b1111_1111_11_101_010: //MOV SP CX
                SP = CX;
                break;
            case (short) 0b1111_1111_11_101_011: //MOV SP DX
                SP = DX;
                break;
            case (short) 0b1111_1111_11_101_100: //MOV SP EX
                SP = EX;
                break;
            case (short) 0b1111_1111_11_101_101: //MOV SP SP | CLR SP
                SP = 0;
                break;
            case (short) 0b1111_1111_11_101_110: //MOV SP RP
                SP = RP;
                break;
            case (short) 0b1111_1111_11_101_111: //MOV SP PC
                SP = PC;
                break;
            case (short) 0b1111_1111_11_110_000: //MOV RP AX
                RP = AX;
                break;
            case (short) 0b1111_1111_11_110_001: //MOV RP BX
                RP = BX;
                break;
            case (short) 0b1111_1111_11_110_010: //MOV RP CX
                RP = CX;
                break;
            case (short) 0b1111_1111_11_110_011: //MOV RP DX
                RP = DX;
                break;
            case (short) 0b1111_1111_11_110_100: //MOV RP EX
                RP = EX;
                break;
            case (short) 0b1111_1111_11_110_101: //MOV RP SP
                RP = SP;
                break;
            case (short) 0b1111_1111_11_110_110: //MOV RP RP | CLR RP
                RP = 0;
                break;
            case (short) 0b1111_1111_11_110_111: //MOV RP PC
                RP = PC;
                break;
            case (short) 0b1111_1111_11_111_000: //MOV PC AX
                PC = AX;
                break;
            case (short) 0b1111_1111_11_111_001: //MOV PC BX
                PC = BX;
                break;
            case (short) 0b1111_1111_11_111_010: //MOV PC CX
                PC = CX;
                break;
            case (short) 0b1111_1111_11_111_011: //MOV PC DX
                PC = DX;
                break;
            case (short) 0b1111_1111_11_111_100: //MOV PC EX
                PC = EX;
                break;
            case (short) 0b1111_1111_11_111_101: //MOV PC SP
                PC = SP;
                break;
            case (short) 0b1111_1111_11_111_110: //MOV PC RP
                PC = RP;
                break;
            case (short) 0b1111_1111_11_111_111: //MOV PC PC | CLR PC | HALT
                PC = 0;
                halted = true;
                break;
            default:
                unknownInstruction(INST);

        }
    }

    /*
     * INC. Increment a value by one.
     */
    private void executeINCInstruction() {
        switch (INST) {

            case 0b0001_0000_00_000_000: //INC AX AX (AX + 1 -> AX)
                AX = aluIncrement(AX);
                break;
            case 0b0001_0000_00_000_001: //INC AX BX (BX + 1 -> AX)
                AX = aluIncrement(BX);
                break;
            case 0b0001_0000_00_000_010: //INC AX CX (CX + 1 -> AX)
                AX = aluIncrement(CX);
                break;
            case 0b0001_0000_00_000_011: //INC AX DX (DX + 1 -> AX)
                AX = aluIncrement(DX);
                break;
            case 0b0001_0000_00_000_100: //INC AX EX (EX + 1 -> AX)
                AX = aluIncrement(EX);
                break;
            case 0b0001_0000_00_000_101: //INC AX SP (SP + 1 -> AX)
                AX = aluIncrement(SP);
                break;
            case 0b0001_0000_00_000_110: //INC AX RP (RP + 1 -> AX)
                AX = aluIncrement(RP);
                break;
            case 0b0001_0000_00_000_111: //INC AX PC (PC + 1 -> AX)
                AX = aluIncrement(PC);
                break;
            case 0b0001_0000_00_001_000: //INC BX AX (AX + 1 -> BX)
                BX = aluIncrement(AX);
                break;
            case 0b0001_0000_00_001_001: //INC BX BX (BX + 1 -> BX)
                BX = aluIncrement(BX);
                break;
            case 0b0001_0000_00_001_010: //INC BX CX (CX + 1 -> BX)
                BX = aluIncrement(CX);
                break;
            case 0b0001_0000_00_001_011: //INC BX DX (DX + 1 -> BX)
                BX = aluIncrement(DX);
                break;
            case 0b0001_0000_00_001_100: //INC BX EX (EX + 1 -> BX)
                BX = aluIncrement(EX);
                break;
            case 0b0001_0000_00_001_101: //INC BX SP (SP + 1 -> BX)
                BX = aluIncrement(SP);
                break;
            case 0b0001_0000_00_001_110: //INC BX RP (RP + 1 -> BX)
                BX = aluIncrement(RP);
                break;
            case 0b0001_0000_00_001_111: //INC BX PC (PC + 1 -> BX)
                BX = aluIncrement(PC);
                break;
            case 0b0001_0000_00_010_000: //INC CX AX (AX + 1 -> CX)
                CX = aluIncrement(AX);
                break;
            case 0b0001_0000_00_010_001: //INC CX BX (BX + 1 -> CX)
                CX = aluIncrement(BX);
                break;
            case 0b0001_0000_00_010_010: //INC CX CX (CX + 1 -> CX)
                CX = aluIncrement(CX);
                break;
            case 0b0001_0000_00_010_011: //INC CX DX (DX + 1 -> CX)
                CX = aluIncrement(DX);
                break;
            case 0b0001_0000_00_010_100: //INC CX EX (EX + 1 -> CX)
                CX = aluIncrement(EX);
                break;
            case 0b0001_0000_00_010_101: //INC CX SP (SP + 1 -> CX)
                CX = aluIncrement(SP);
                break;
            case 0b0001_0000_00_010_110: //INC CX RP (RP + 1 -> CX)
                CX = aluIncrement(RP);
                break;
            case 0b0001_0000_00_010_111: //INC CX PC (PC + 1 -> CX)
                CX = aluIncrement(PC);
                break;
            case 0b0001_0000_00_011_000: //INC DX AX (AX + 1 -> DX)
                DX = aluIncrement(AX);
                break;
            case 0b0001_0000_00_011_001: //INC DX BX (BX + 1 -> DX)
                DX = aluIncrement(BX);
                break;
            case 0b0001_0000_00_011_010: //INC DX CX (CX + 1 -> DX)
                DX = aluIncrement(CX);
                break;
            case 0b0001_0000_00_011_011: //INC DX DX (DX + 1 -> DX)
                DX = aluIncrement(DX);
                break;
            case 0b0001_0000_00_011_100: //INC DX EX (EX + 1 -> DX)
                DX = aluIncrement(EX);
                break;
            case 0b0001_0000_00_011_101: //INC DX SP (SP + 1 -> DX)
                DX = aluIncrement(SP);
                break;
            case 0b0001_0000_00_011_110: //INC DX RP (RP + 1 -> DX)
                DX = aluIncrement(RP);
                break;
            case 0b0001_0000_00_011_111: //INC DX PC (PC + 1 -> DX)
                DX = aluIncrement(PC);
                break;
            case 0b0001_0000_00_100_000: //INC EX AX (AX + 1 -> EX)
                EX = aluIncrement(AX);
                break;
            case 0b0001_0000_00_100_001: //INC EX BX (BX + 1 -> EX)
                EX = aluIncrement(BX);
                break;
            case 0b0001_0000_00_100_010: //INC EX CX (CX + 1 -> EX)
                EX = aluIncrement(CX);
                break;
            case 0b0001_0000_00_100_011: //INC EX DX (DX + 1 -> EX)
                EX = aluIncrement(DX);
                break;
            case 0b0001_0000_00_100_100: //INC EX EX (EX + 1 -> EX)
                EX = aluIncrement(EX);
                break;
            case 0b0001_0000_00_100_101: //INC EX SP (SP + 1 -> EX)
                EX = aluIncrement(SP);
                break;
            case 0b0001_0000_00_100_110: //INC EX RP (RP + 1 -> EX)
                EX = aluIncrement(RP);
                break;
            case 0b0001_0000_00_100_111: //INC EX PC (PC + 1 -> EX)
                EX = aluIncrement(PC);
                break;
            case 0b0001_0000_00_101_000: //INC SP AX (AX + 1 -> SP)
                SP = aluIncrement(AX);
                break;
            case 0b0001_0000_00_101_001: //INC SP BX (BX + 1 -> SP)
                SP = aluIncrement(BX);
                break;
            case 0b0001_0000_00_101_010: //INC SP CX (CX + 1 -> SP)
                SP = aluIncrement(CX);
                break;
            case 0b0001_0000_00_101_011: //INC SP DX (DX + 1 -> SP)
                SP = aluIncrement(DX);
                break;
            case 0b0001_0000_00_101_100: //INC SP EX (EX + 1 -> SP)
                SP = aluIncrement(EX);
                break;
            case 0b0001_0000_00_101_101: //INC SP SP (SP + 1 -> SP)
                SP = aluIncrement(SP);
                break;
            case 0b0001_0000_00_101_110: //INC SP RP (RP + 1 -> SP)
                SP = aluIncrement(RP);
                break;
            case 0b0001_0000_00_101_111: //INC SP PC (PC + 1 -> SP)
                SP = aluIncrement(PC);
                break;
            case 0b0001_0000_00_110_000: //INC RP AX (AX + 1 -> RP)
                RP = aluIncrement(AX);
                break;
            case 0b0001_0000_00_110_001: //INC RP BX (BX + 1 -> RP)
                RP = aluIncrement(BX);
                break;
            case 0b0001_0000_00_110_010: //INC RP CX (CX + 1 -> RP)
                RP = aluIncrement(CX);
                break;
            case 0b0001_0000_00_110_011: //INC RP DX (DX + 1 -> RP)
                RP = aluIncrement(DX);
                break;
            case 0b0001_0000_00_110_100: //INC RP EX (EX + 1 -> RP)
                RP = aluIncrement(EX);
                break;
            case 0b0001_0000_00_110_101: //INC RP SP (SP + 1 -> RP)
                RP = aluIncrement(SP);
                break;
            case 0b0001_0000_00_110_110: //INC RP RP (RP + 1 -> RP)
                RP = aluIncrement(RP);
                break;
            case 0b0001_0000_00_110_111: //INC RP PC (PC + 1 -> RP)
                RP = aluIncrement(PC);
                break;
            case 0b0001_0000_00_111_000: //INC PC AX (AX + 1 -> PC)
                PC = aluIncrement(AX);
                break;
            case 0b0001_0000_00_111_001: //INC PC BX (BX + 1 -> PC)
                PC = aluIncrement(BX);
                break;
            case 0b0001_0000_00_111_010: //INC PC CX (CX + 1 -> PC)
                PC = aluIncrement(CX);
                break;
            case 0b0001_0000_00_111_011: //INC PC DX (DX + 1 -> PC)
                PC = aluIncrement(DX);
                break;
            case 0b0001_0000_00_111_100: //INC PC EX (EX + 1 -> PC)
                PC = aluIncrement(EX);
                break;
            case 0b0001_0000_00_111_101: //INC PC SP (SP + 1 -> PC)
                PC = aluIncrement(SP);
                break;
            case 0b0001_0000_00_111_110: //INC PC RP (RP + 1 -> PC)
                PC = aluIncrement(RP);
                break;
            case 0b0001_0000_00_111_111: //INC PC PC (PC + 1 -> PC)
                PC = aluIncrement(PC);
                break;
            default:
                unknownInstruction(INST);
        }
    }

    /*
     * Decrement. Decrement a value by one.
     */
    private void executeDECInstruction() {
        switch (INST) {
            case 0b0001_0000_01_000_000: //DEC AX AX (AX - 1 -> AX)
                AX = aluDecrement(AX);
                break;
            case 0b0001_0000_01_000_001: //DEC AX BX (BX - 1 -> AX)
                AX = aluDecrement(BX);
                break;
            case 0b0001_0000_01_000_010: //DEC AX CX (CX - 1 -> AX)
                AX = aluDecrement(CX);
                break;
            case 0b0001_0000_01_000_011: //DEC AX DX (DX - 1 -> AX)
                AX = aluDecrement(DX);
                break;
            case 0b0001_0000_01_000_100: //DEC AX EX (EX - 1 -> AX)
                AX = aluDecrement(EX);
                break;
            case 0b0001_0000_01_000_101: //DEC AX SP (SP - 1 -> AX)
                AX = aluDecrement(SP);
                break;
            case 0b0001_0000_01_000_110: //DEC AX RP (RP - 1 -> AX)
                AX = aluDecrement(RP);
                break;
            case 0b0001_0000_01_000_111: //DEC AX PC (PC - 1 -> AX)
                AX = aluDecrement(PC);
                break;
            case 0b0001_0000_01_001_000: //DEC BX AX (AX - 1 -> BX)
                BX = aluDecrement(AX);
                break;
            case 0b0001_0000_01_001_001: //DEC BX BX (BX - 1 -> BX)
                BX = aluDecrement(BX);
                break;
            case 0b0001_0000_01_001_010: //DEC BX CX (CX - 1 -> BX)
                BX = aluDecrement(CX);
                break;
            case 0b0001_0000_01_001_011: //DEC BX DX (DX - 1 -> BX)
                BX = aluDecrement(DX);
                break;
            case 0b0001_0000_01_001_100: //DEC BX EX (EX - 1 -> BX)
                BX = aluDecrement(EX);
                break;
            case 0b0001_0000_01_001_101: //DEC BX SP (SP - 1 -> BX)
                BX = aluDecrement(SP);
                break;
            case 0b0001_0000_01_001_110: //DEC BX RP (RP - 1 -> BX)
                BX = aluDecrement(RP);
                break;
            case 0b0001_0000_01_001_111: //DEC BX PC (PC - 1 -> BX)
                BX = aluDecrement(PC);
                break;
            case 0b0001_0000_01_010_000: //DEC CX AX (AX - 1 -> CX)
                CX = aluDecrement(AX);
                break;
            case 0b0001_0000_01_010_001: //DEC CX BX (BX - 1 -> CX)
                CX = aluDecrement(BX);
                break;
            case 0b0001_0000_01_010_010: //DEC CX CX (CX - 1 -> CX)
                CX = aluDecrement(CX);
                break;
            case 0b0001_0000_01_010_011: //DEC CX DX (DX - 1 -> CX)
                CX = aluDecrement(DX);
                break;
            case 0b0001_0000_01_010_100: //DEC CX EX (EX - 1 -> CX)
                CX = aluDecrement(EX);
                break;
            case 0b0001_0000_01_010_101: //DEC CX SP (SP - 1 -> CX)
                CX = aluDecrement(SP);
                break;
            case 0b0001_0000_01_010_110: //DEC CX RP (RP - 1 -> CX)
                CX = aluDecrement(RP);
                break;
            case 0b0001_0000_01_010_111: //DEC CX PC (PC - 1 -> CX)
                CX = aluDecrement(PC);
                break;
            case 0b0001_0000_01_011_000: //DEC DX AX (AX - 1 -> DX)
                DX = aluDecrement(AX);
                break;
            case 0b0001_0000_01_011_001: //DEC DX BX (BX - 1 -> DX)
                DX = aluDecrement(BX);
                break;
            case 0b0001_0000_01_011_010: //DEC DX CX (CX - 1 -> DX)
                DX = aluDecrement(CX);
                break;
            case 0b0001_0000_01_011_011: //DEC DX DX (DX - 1 -> DX)
                DX = aluDecrement(DX);
                break;
            case 0b0001_0000_01_011_100: //DEC DX EX (EX - 1 -> DX)
                DX = aluDecrement(EX);
                break;
            case 0b0001_0000_01_011_101: //DEC DX SP (SP - 1 -> DX)
                DX = aluDecrement(SP);
                break;
            case 0b0001_0000_01_011_110: //DEC DX RP (RP - 1 -> DX)
                DX = aluDecrement(RP);
                break;
            case 0b0001_0000_01_011_111: //DEC DX PC (PC - 1 -> DX)
                DX = aluDecrement(PC);
                break;
            case 0b0001_0000_01_100_000: //DEC EX AX (AX - 1 -> EX)
                EX = aluDecrement(AX);
                break;
            case 0b0001_0000_01_100_001: //DEC EX BX (BX - 1 -> EX)
                EX = aluDecrement(BX);
                break;
            case 0b0001_0000_01_100_010: //DEC EX CX (CX - 1 -> EX)
                EX = aluDecrement(CX);
                break;
            case 0b0001_0000_01_100_011: //DEC EX DX (DX - 1 -> EX)
                EX = aluDecrement(DX);
                break;
            case 0b0001_0000_01_100_100: //DEC EX EX (EX - 1 -> EX)
                EX = aluDecrement(EX);
                break;
            case 0b0001_0000_01_100_101: //DEC EX SP (SP - 1 -> EX)
                EX = aluDecrement(SP);
                break;
            case 0b0001_0000_01_100_110: //DEC EX RP (RP - 1 -> EX)
                EX = aluDecrement(RP);
                break;
            case 0b0001_0000_01_100_111: //DEC EX PC (PC - 1 -> EX)
                EX = aluDecrement(PC);
                break;
            case 0b0001_0000_01_101_000: //DEC SP AX (AX - 1 -> SP)
                SP = aluDecrement(AX);
                break;
            case 0b0001_0000_01_101_001: //DEC SP BX (BX - 1 -> SP)
                SP = aluDecrement(BX);
                break;
            case 0b0001_0000_01_101_010: //DEC SP CX (CX - 1 -> SP)
                SP = aluDecrement(CX);
                break;
            case 0b0001_0000_01_101_011: //DEC SP DX (DX - 1 -> SP)
                SP = aluDecrement(DX);
                break;
            case 0b0001_0000_01_101_100: //DEC SP EX (EX - 1 -> SP)
                SP = aluDecrement(EX);
                break;
            case 0b0001_0000_01_101_101: //DEC SP SP (SP - 1 -> SP)
                SP = aluDecrement(SP);
                break;
            case 0b0001_0000_01_101_110: //DEC SP RP (RP - 1 -> SP)
                SP = aluDecrement(RP);
                break;
            case 0b0001_0000_01_101_111: //DEC SP PC (PC - 1 -> SP)
                SP = aluDecrement(PC);
                break;
            case 0b0001_0000_01_110_000: //DEC RP AX (AX - 1 -> RP)
                RP = aluDecrement(AX);
                break;
            case 0b0001_0000_01_110_001: //DEC RP BX (BX - 1 -> RP)
                RP = aluDecrement(BX);
                break;
            case 0b0001_0000_01_110_010: //DEC RP CX (CX - 1 -> RP)
                RP = aluDecrement(CX);
                break;
            case 0b0001_0000_01_110_011: //DEC RP DX (DX - 1 -> RP)
                RP = aluDecrement(DX);
                break;
            case 0b0001_0000_01_110_100: //DEC RP EX (EX - 1 -> RP)
                RP = aluDecrement(EX);
                break;
            case 0b0001_0000_01_110_101: //DEC RP SP (SP - 1 -> RP)
                RP = aluDecrement(SP);
                break;
            case 0b0001_0000_01_110_110: //DEC RP RP (RP - 1 -> RP)
                RP = aluDecrement(RP);
                break;
            case 0b0001_0000_01_110_111: //DEC RP PC (PC - 1 -> RP)
                RP = aluDecrement(PC);
                break;
            case 0b0001_0000_01_111_000: //DEC PC AX (AX - 1 -> PC)
                PC = aluDecrement(AX);
                break;
            case 0b0001_0000_01_111_001: //DEC PC BX (BX - 1 -> PC)
                PC = aluDecrement(BX);
                break;
            case 0b0001_0000_01_111_010: //DEC PC CX (CX - 1 -> PC)
                PC = aluDecrement(CX);
                break;
            case 0b0001_0000_01_111_011: //DEC PC DX (DX - 1 -> PC)
                PC = aluDecrement(DX);
                break;
            case 0b0001_0000_01_111_100: //DEC PC EX (EX - 1 -> PC)
                PC = aluDecrement(EX);
                break;
            case 0b0001_0000_01_111_101: //DEC PC SP (SP - 1 -> PC)
                PC = aluDecrement(SP);
                break;
            case 0b0001_0000_01_111_110: //DEC PC RP (RP - 1 -> PC)
                PC = aluDecrement(RP);
                break;
            case 0b0001_0000_01_111_111: //DEC PC PC (PC - 1 -> PC)
                PC = aluDecrement(PC);
                break;


        }
    }

    /*
     * Negation. Binary NOT of a value.
     */
    private void executeNOTInstruction() {
        switch (INST) {
            case 0b0001_0000_10_000_000: //NOT AX AX (~AX -> AX)
                AX = aluNegate(AX);
                break;
            case 0b0001_0000_10_000_001: //NOT AX BX (~BX -> AX)
                AX = aluNegate(BX);
                break;
            case 0b0001_0000_10_000_010: //NOT AX CX (~CX -> AX)
                AX = aluNegate(CX);
                break;
            case 0b0001_0000_10_000_011: //NOT AX DX (~DX -> AX)
                AX = aluNegate(DX);
                break;
            case 0b0001_0000_10_000_100: //NOT AX EX (~EX -> AX)
                AX = aluNegate(EX);
                break;
            case 0b0001_0000_10_000_101: //NOT AX SP (~SP -> AX)
                AX = aluNegate(SP);
                break;
            case 0b0001_0000_10_000_110: //NOT AX RP (~RP -> AX)
                AX = aluNegate(RP);
                break;
            case 0b0001_0000_10_000_111: //NOT AX PC (~PC -> AX)
                AX = aluNegate(PC);
                break;
            case 0b0001_0000_10_001_000: //NOT BX AX (~AX -> BX)
                BX = aluNegate(AX);
                break;
            case 0b0001_0000_10_001_001: //NOT BX BX (~BX -> BX)
                BX = aluNegate(BX);
                break;
            case 0b0001_0000_10_001_010: //NOT BX CX (~CX -> BX)
                BX = aluNegate(CX);
                break;
            case 0b0001_0000_10_001_011: //NOT BX DX (~DX -> BX)
                BX = aluNegate(DX);
                break;
            case 0b0001_0000_10_001_100: //NOT BX EX (~EX -> BX)
                BX = aluNegate(EX);
                break;
            case 0b0001_0000_10_001_101: //NOT BX SP (~SP -> BX)
                BX = aluNegate(SP);
                break;
            case 0b0001_0000_10_001_110: //NOT BX RP (~RP -> BX)
                BX = aluNegate(RP);
                break;
            case 0b0001_0000_10_001_111: //NOT BX PC (~PC -> BX)
                BX = aluNegate(PC);
                break;
            case 0b0001_0000_10_010_000: //NOT CX AX (~AX -> CX)
                CX = aluNegate(AX);
                break;
            case 0b0001_0000_10_010_001: //NOT CX BX (~BX -> CX)
                CX = aluNegate(BX);
                break;
            case 0b0001_0000_10_010_010: //NOT CX CX (~CX -> CX)
                CX = aluNegate(CX);
                break;
            case 0b0001_0000_10_010_011: //NOT CX DX (~DX -> CX)
                CX = aluNegate(DX);
                break;
            case 0b0001_0000_10_010_100: //NOT CX EX (~EX -> CX)
                CX = aluNegate(EX);
                break;
            case 0b0001_0000_10_010_101: //NOT CX SP (~SP -> CX)
                CX = aluNegate(SP);
                break;
            case 0b0001_0000_10_010_110: //NOT CX RP (~RP -> CX)
                CX = aluNegate(RP);
                break;
            case 0b0001_0000_10_010_111: //NOT CX PC (~PC -> CX)
                CX = aluNegate(PC);
                break;
            case 0b0001_0000_10_011_000: //NOT DX AX (~AX -> DX)
                DX = aluNegate(AX);
                break;
            case 0b0001_0000_10_011_001: //NOT DX BX (~BX -> DX)
                DX = aluNegate(BX);
                break;
            case 0b0001_0000_10_011_010: //NOT DX CX (~CX -> DX)
                DX = aluNegate(CX);
                break;
            case 0b0001_0000_10_011_011: //NOT DX DX (~DX -> DX)
                DX = aluNegate(DX);
                break;
            case 0b0001_0000_10_011_100: //NOT DX EX (~EX -> DX)
                DX = aluNegate(EX);
                break;
            case 0b0001_0000_10_011_101: //NOT DX SP (~SP -> DX)
                DX = aluNegate(SP);
                break;
            case 0b0001_0000_10_011_110: //NOT DX RP (~RP -> DX)
                DX = aluNegate(RP);
                break;
            case 0b0001_0000_10_011_111: //NOT DX PC (~PC -> DX)
                DX = aluNegate(PC);
                break;
            case 0b0001_0000_10_100_000: //NOT EX AX (~AX -> EX)
                EX = aluNegate(AX);
                break;
            case 0b0001_0000_10_100_001: //NOT EX BX (~BX -> EX)
                EX = aluNegate(BX);
                break;
            case 0b0001_0000_10_100_010: //NOT EX CX (~CX -> EX)
                EX = aluNegate(CX);
                break;
            case 0b0001_0000_10_100_011: //NOT EX DX (~DX -> EX)
                EX = aluNegate(DX);
                break;
            case 0b0001_0000_10_100_100: //NOT EX EX (~EX -> EX)
                EX = aluNegate(EX);
                break;
            case 0b0001_0000_10_100_101: //NOT EX SP (~SP -> EX)
                EX = aluNegate(SP);
                break;
            case 0b0001_0000_10_100_110: //NOT EX RP (~RP -> EX)
                EX = aluNegate(RP);
                break;
            case 0b0001_0000_10_100_111: //NOT EX PC (~PC -> EX)
                EX = aluNegate(PC);
                break;
            case 0b0001_0000_10_101_000: //NOT SP AX (~AX -> SP)
                SP = aluNegate(AX);
                break;
            case 0b0001_0000_10_101_001: //NOT SP BX (~BX -> SP)
                SP = aluNegate(BX);
                break;
            case 0b0001_0000_10_101_010: //NOT SP CX (~CX -> SP)
                SP = aluNegate(CX);
                break;
            case 0b0001_0000_10_101_011: //NOT SP DX (~DX -> SP)
                SP = aluNegate(DX);
                break;
            case 0b0001_0000_10_101_100: //NOT SP EX (~EX -> SP)
                SP = aluNegate(EX);
                break;
            case 0b0001_0000_10_101_101: //NOT SP SP (~SP -> SP)
                SP = aluNegate(SP);
                break;
            case 0b0001_0000_10_101_110: //NOT SP RP (~RP -> SP)
                SP = aluNegate(RP);
                break;
            case 0b0001_0000_10_101_111: //NOT SP PC (~PC -> SP)
                SP = aluNegate(PC);
                break;
            case 0b0001_0000_10_110_000: //NOT RP AX (~AX -> RP)
                RP = aluNegate(AX);
                break;
            case 0b0001_0000_10_110_001: //NOT RP BX (~BX -> RP)
                RP = aluNegate(BX);
                break;
            case 0b0001_0000_10_110_010: //NOT RP CX (~CX -> RP)
                RP = aluNegate(CX);
                break;
            case 0b0001_0000_10_110_011: //NOT RP DX (~DX -> RP)
                RP = aluNegate(DX);
                break;
            case 0b0001_0000_10_110_100: //NOT RP EX (~EX -> RP)
                RP = aluNegate(EX);
                break;
            case 0b0001_0000_10_110_101: //NOT RP SP (~SP -> RP)
                RP = aluNegate(SP);
                break;
            case 0b0001_0000_10_110_110: //NOT RP RP (~RP -> RP)
                RP = aluNegate(RP);
                break;
            case 0b0001_0000_10_110_111: //NOT RP PC (~PC -> RP)
                RP = aluNegate(PC);
                break;
            case 0b0001_0000_10_111_000: //NOT PC AX (~AX -> PC)
                PC = aluNegate(AX);
                break;
            case 0b0001_0000_10_111_001: //NOT PC BX (~BX -> PC)
                PC = aluNegate(BX);
                break;
            case 0b0001_0000_10_111_010: //NOT PC CX (~CX -> PC)
                PC = aluNegate(CX);
                break;
            case 0b0001_0000_10_111_011: //NOT PC DX (~DX -> PC)
                PC = aluNegate(DX);
                break;
            case 0b0001_0000_10_111_100: //NOT PC EX (~EX -> PC)
                PC = aluNegate(EX);
                break;
            case 0b0001_0000_10_111_101: //NOT PC SP (~SP -> PC)
                PC = aluNegate(SP);
                break;
            case 0b0001_0000_10_111_110: //NOT PC RP (~RP -> PC)
                PC = aluNegate(RP);
                break;
            case 0b0001_0000_10_111_111: //NOT PC PC (~PC -> PC)
                PC = aluNegate(PC);
                break;


        }

    }

    /*
     * ROL: rotation one bit left
     */
    private void executeROLInstruction() {
        switch (INST) {
            case 0b0001_0000_11_000_000: //ROL AX AX (rol AX -> AX)
                AX = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_000_001: //ROL AX BX (rol BX -> AX)
                AX = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_000_010: //ROL AX CX (rol CX -> AX)
                AX = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_000_011: //ROL AX DX (rol DX -> AX)
                AX = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_000_100: //ROL AX EX (rol EX -> AX)
                AX = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_000_101: //ROL AX SP (rol SP -> AX)
                AX = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_000_110: //ROL AX RP (rol RP -> AX)
                AX = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_000_111: //ROL AX PC (rol PC -> AX)
                AX = aluLeftRotation(PC);
                break;
            case 0b0001_0000_11_001_000: //ROL BX AX (rol AX -> BX)
                BX = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_001_001: //ROL BX BX (rol BX -> BX)
                BX = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_001_010: //ROL BX CX (rol CX -> BX)
                BX = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_001_011: //ROL BX DX (rol DX -> BX)
                BX = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_001_100: //ROL BX EX (rol EX -> BX)
                BX = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_001_101: //ROL BX SP (rol SP -> BX)
                BX = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_001_110: //ROL BX RP (rol RP -> BX)
                BX = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_001_111: //ROL BX PC (rol PC -> BX)
                BX = aluLeftRotation(PC);
                break;
            case 0b0001_0000_11_010_000: //ROL CX AX (rol AX -> CX)
                CX = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_010_001: //ROL CX BX (rol BX -> CX)
                CX = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_010_010: //ROL CX CX (rol CX -> CX)
                CX = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_010_011: //ROL CX DX (rol DX -> CX)
                CX = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_010_100: //ROL CX EX (rol EX -> CX)
                CX = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_010_101: //ROL CX SP (rol SP -> CX)
                CX = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_010_110: //ROL CX RP (rol RP -> CX)
                CX = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_010_111: //ROL CX PC (rol PC -> CX)
                CX = aluLeftRotation(PC);
                break;
            case 0b0001_0000_11_011_000: //ROL DX AX (rol AX -> DX)
                DX = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_011_001: //ROL DX BX (rol BX -> DX)
                DX = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_011_010: //ROL DX CX (rol CX -> DX)
                DX = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_011_011: //ROL DX DX (rol DX -> DX)
                DX = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_011_100: //ROL DX EX (rol EX -> DX)
                DX = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_011_101: //ROL DX SP (rol SP -> DX)
                DX = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_011_110: //ROL DX RP (rol RP -> DX)
                DX = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_011_111: //ROL DX PC (rol PC -> DX)
                DX = aluLeftRotation(PC);
                break;
            case 0b0001_0000_11_100_000: //ROL EX AX (rol AX -> EX)
                EX = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_100_001: //ROL EX BX (rol BX -> EX)
                EX = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_100_010: //ROL EX CX (rol CX -> EX)
                EX = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_100_011: //ROL EX DX (rol DX -> EX)
                EX = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_100_100: //ROL EX EX (rol EX -> EX)
                EX = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_100_101: //ROL EX SP (rol SP -> EX)
                EX = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_100_110: //ROL EX RP (rol RP -> EX)
                EX = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_100_111: //ROL EX PC (rol PC -> EX)
                EX = aluLeftRotation(PC);
                break;
            case 0b0001_0000_11_101_000: //ROL SP AX (rol AX -> SP)
                SP = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_101_001: //ROL SP BX (rol BX -> SP)
                SP = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_101_010: //ROL SP CX (rol CX -> SP)
                SP = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_101_011: //ROL SP DX (rol DX -> SP)
                SP = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_101_100: //ROL SP EX (rol EX -> SP)
                SP = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_101_101: //ROL SP SP (rol SP -> SP)
                SP = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_101_110: //ROL SP RP (rol RP -> SP)
                SP = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_101_111: //ROL SP PC (rol PC -> SP)
                SP = aluLeftRotation(PC);
                break;
            case 0b0001_0000_11_110_000: //ROL RP AX (rol AX -> RP)
                RP = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_110_001: //ROL RP BX (rol BX -> RP)
                RP = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_110_010: //ROL RP CX (rol CX -> RP)
                RP = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_110_011: //ROL RP DX (rol DX -> RP)
                RP = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_110_100: //ROL RP EX (rol EX -> RP)
                RP = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_110_101: //ROL RP SP (rol SP -> RP)
                RP = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_110_110: //ROL RP RP (rol RP -> RP)
                RP = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_110_111: //ROL RP PC (rol PC -> RP)
                RP = aluLeftRotation(PC);
                break;
            case 0b0001_0000_11_111_000: //ROL PC AX (rol AX -> PC)
                PC = aluLeftRotation(AX);
                break;
            case 0b0001_0000_11_111_001: //ROL PC BX (rol BX -> PC)
                PC = aluLeftRotation(BX);
                break;
            case 0b0001_0000_11_111_010: //ROL PC CX (rol CX -> PC)
                PC = aluLeftRotation(CX);
                break;
            case 0b0001_0000_11_111_011: //ROL PC DX (rol DX -> PC)
                PC = aluLeftRotation(DX);
                break;
            case 0b0001_0000_11_111_100: //ROL PC EX (rol EX -> PC)
                PC = aluLeftRotation(EX);
                break;
            case 0b0001_0000_11_111_101: //ROL PC SP (rol SP -> PC)
                PC = aluLeftRotation(SP);
                break;
            case 0b0001_0000_11_111_110: //ROL PC RP (rol RP -> PC)
                PC = aluLeftRotation(RP);
                break;
            case 0b0001_0000_11_111_111: //ROL PC PC (rol PC -> PC)
                PC = aluLeftRotation(PC);
                break;


            default:
                unknownInstruction(INST);
        }
    }

    /*
     * ADD: add two registers together, storing into the destination register
     */
    private void executeADDInstruction() {

        switch (INST) {
            case 0b0001_1_00_000_000_000: //ADD AX AX AX (AX + AX -> AX)
                AX = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_000_000_001: //ADD AX BX AX (AX + BX -> AX)
                AX = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_000_000_010: //ADD AX CX AX (AX + CX -> AX)
                AX = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_000_000_011: //ADD AX DX AX (AX + DX -> AX)
                AX = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_000_000_100: //ADD AX EX AX (AX + EX -> AX)
                AX = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_000_000_101: //ADD AX SP AX (AX + SP -> AX)
                AX = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_000_000_110: //ADD AX RP AX (AX + RP -> AX)
                AX = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_000_000_111: //ADD AX PC AX (AX + PC -> AX)
                AX = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_001_000_000: //ADD AX AX BX (AX + AX -> BX)
                BX = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_001_000_001: //ADD AX BX BX (AX + BX -> BX)
                BX = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_001_000_010: //ADD AX CX BX (AX + CX -> BX)
                BX = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_001_000_011: //ADD AX DX BX (AX + DX -> BX)
                BX = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_001_000_100: //ADD AX EX BX (AX + EX -> BX)
                BX = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_001_000_101: //ADD AX SP BX (AX + SP -> BX)
                BX = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_001_000_110: //ADD AX RP BX (AX + RP -> BX)
                BX = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_001_000_111: //ADD AX PC BX (AX + PC -> BX)
                BX = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_010_000_000: //ADD AX AX CX (AX + AX -> CX)
                CX = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_010_000_001: //ADD AX BX CX (AX + BX -> CX)
                CX = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_010_000_010: //ADD AX CX CX (AX + CX -> CX)
                CX = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_010_000_011: //ADD AX DX CX (AX + DX -> CX)
                CX = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_010_000_100: //ADD AX EX CX (AX + EX -> CX)
                CX = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_010_000_101: //ADD AX SP CX (AX + SP -> CX)
                CX = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_010_000_110: //ADD AX RP CX (AX + RP -> CX)
                CX = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_010_000_111: //ADD AX PC CX (AX + PC -> CX)
                CX = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_011_000_000: //ADD AX AX DX (AX + AX -> DX)
                DX = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_011_000_001: //ADD AX BX DX (AX + BX -> DX)
                DX = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_011_000_010: //ADD AX CX DX (AX + CX -> DX)
                DX = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_011_000_011: //ADD AX DX DX (AX + DX -> DX)
                DX = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_011_000_100: //ADD AX EX DX (AX + EX -> DX)
                DX = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_011_000_101: //ADD AX SP DX (AX + SP -> DX)
                DX = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_011_000_110: //ADD AX RP DX (AX + RP -> DX)
                DX = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_011_000_111: //ADD AX PC DX (AX + PC -> DX)
                DX = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_100_000_000: //ADD AX AX EX (AX + AX -> EX)
                EX = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_100_000_001: //ADD AX EX SP (AX + BX -EXSP)
                EX = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_100_000_010: //ADD AX EX SP (AX + CX -EXSP)
                EX = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_100_000_011: //ADD AX EX SP (AX + DX -EXSP)
                EX = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_100_000_100: //ADD AX EX SP (AX + EX -EXSP)
                EX = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_100_000_101: //ADD AX EX SP (AX + SP -EXSP)
                EX = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_100_000_110: //ADD AX EX SP (AX + RP -EXSP)
                EX = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_100_000_111: //ADD AX EX SP (AX + PC -EXSP)
                EX = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_101_000_000: //ADD AX AX SP (AX + AX -> SP)
                SP = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_101_000_001: //ADD AX BX SP (AX + BX -> SP)
                SP = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_101_000_010: //ADD AX CX SP (AX + CX -> SP)
                SP = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_101_000_011: //ADD AX DX SP (AX + DX -> SP)
                SP = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_101_000_100: //ADD AX EX SP (AX + EX -> SP)
                SP = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_101_000_101: //ADD AX SP SP (AX + SP -> SP)
                SP = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_101_000_110: //ADD AX RP SP (AX + RP -> SP)
                SP = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_101_000_111: //ADD AX PC SP (AX + PC -> SP)
                SP = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_110_000_000: //ADD AX AX RP (AX + AX -> RP)
                RP = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_110_000_001: //ADD AX BX RP (AX + BX -> RP)
                RP = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_110_000_010: //ADD AX CX RP (AX + CX -> RP)
                RP = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_110_000_011: //ADD AX DX RP (AX + DX -> RP)
                RP = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_110_000_100: //ADD AX EX RP (AX + EX -> RP)
                RP = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_110_000_101: //ADD AX SP RP (AX + SP -> RP)
                RP = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_110_000_110: //ADD AX RP RP (AX + RP -> RP)
                RP = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_110_000_111: //ADD AX PC RP (AX + PC -> RP)
                RP = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_111_000_000: //ADD AX AX PC (AX + AX -> PC)
                PC = aluAdd(AX, AX);
                break;
            case 0b0001_1_00_111_000_001: //ADD AX BX PC (AX + BX -> PC)
                PC = aluAdd(AX, BX);
                break;
            case 0b0001_1_00_111_000_010: //ADD AX CX PC (AX + CX -> PC)
                PC = aluAdd(AX, CX);
                break;
            case 0b0001_1_00_111_000_011: //ADD AX DX PC (AX + DX -> PC)
                PC = aluAdd(AX, DX);
                break;
            case 0b0001_1_00_111_000_100: //ADD AX EX PC (AX + EX -> PC)
                PC = aluAdd(AX, EX);
                break;
            case 0b0001_1_00_111_000_101: //ADD AX SP PC (AX + SP -> PC)
                PC = aluAdd(AX, SP);
                break;
            case 0b0001_1_00_111_000_110: //ADD AX RP PC (AX + RP -> PC)
                PC = aluAdd(AX, RP);
                break;
            case 0b0001_1_00_111_000_111: //ADD AX PC PC (AX + PC -> PC)
                PC = aluAdd(AX, PC);
                break;
            case 0b0001_1_00_000_001_000: //ADD BX AX AX (BX + AX -> AX)
                AX = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_000_001_001: //ADD BX BX AX (BX + BX -> AX)
                AX = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_000_001_010: //ADD BX CX AX (BX + CX -> AX)
                AX = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_000_001_011: //ADD BX DX AX (BX + DX -> AX)
                AX = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_000_001_100: //ADD BX EX AX (BX + EX -> AX)
                AX = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_000_001_101: //ADD BX SP AX (BX + SP -> AX)
                AX = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_000_001_110: //ADD BX RP AX (BX + RP -> AX)
                AX = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_000_001_111: //ADD BX PC AX (BX + PC -> AX)
                AX = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_001_001_000: //ADD BX AX BX (BX + AX -> BX)
                BX = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_001_001_001: //ADD BX BX BX (BX + BX -> BX)
                BX = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_001_001_010: //ADD BX CX BX (BX + CX -> BX)
                BX = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_001_001_011: //ADD BX DX BX (BX + DX -> BX)
                BX = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_001_001_100: //ADD BX EX BX (BX + EX -> BX)
                BX = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_001_001_101: //ADD BX SP BX (BX + SP -> BX)
                BX = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_001_001_110: //ADD BX RP BX (BX + RP -> BX)
                BX = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_001_001_111: //ADD BX PC BX (BX + PC -> BX)
                BX = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_010_001_000: //ADD BX AX CX (BX + AX -> CX)
                CX = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_010_001_001: //ADD BX BX CX (BX + BX -> CX)
                CX = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_010_001_010: //ADD BX CX CX (BX + CX -> CX)
                CX = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_010_001_011: //ADD BX DX CX (BX + DX -> CX)
                CX = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_010_001_100: //ADD BX EX CX (BX + EX -> CX)
                CX = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_010_001_101: //ADD BX SP CX (BX + SP -> CX)
                CX = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_010_001_110: //ADD BX RP CX (BX + RP -> CX)
                CX = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_010_001_111: //ADD BX PC CX (BX + PC -> CX)
                CX = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_011_001_000: //ADD BX AX DX (BX + AX -> DX)
                DX = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_011_001_001: //ADD BX BX DX (BX + BX -> DX)
                DX = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_011_001_010: //ADD BX CX DX (BX + CX -> DX)
                DX = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_011_001_011: //ADD BX DX DX (BX + DX -> DX)
                DX = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_011_001_100: //ADD BX EX DX (BX + EX -> DX)
                DX = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_011_001_101: //ADD BX SP DX (BX + SP -> DX)
                DX = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_011_001_110: //ADD BX RP DX (BX + RP -> DX)
                DX = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_011_001_111: //ADD BX PC DX (BX + PC -> DX)
                DX = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_100_001_000: //ADD BX AX EX (BX + AX -> EX)
                EX = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_100_001_001: //ADD BX EX SP (BX + BX -> SP)
                EX = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_100_001_010: //ADD BX EX SP (BX + CX -> SP)
                EX = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_100_001_011: //ADD BX EX SP (BX + DX -> SP)
                EX = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_100_001_100: //ADD BX EX SP (BX + EX -> SP)
                EX = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_100_001_101: //ADD BX EX SP (BX + SP -> SP)
                EX = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_100_001_110: //ADD BX EX SP (BX + RP -> SP)
                EX = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_100_001_111: //ADD BX EX SP (BX + PC -> SP)
                EX = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_101_001_000: //ADD BX AX SP (BX + AX -> SP)
                SP = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_101_001_001: //ADD BX BX SP (BX + BX -> SP)
                SP = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_101_001_010: //ADD BX CX SP (BX + CX -> SP)
                SP = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_101_001_011: //ADD BX DX SP (BX + DX -> SP)
                SP = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_101_001_100: //ADD BX EX SP (BX + EX -> SP)
                SP = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_101_001_101: //ADD BX SP SP (BX + SP -> SP)
                SP = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_101_001_110: //ADD BX RP SP (BX + RP -> SP)
                SP = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_101_001_111: //ADD BX PC SP (BX + PC -> SP)
                SP = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_110_001_000: //ADD BX AX RP (BX + AX -> RP)
                RP = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_110_001_001: //ADD BX BX RP (BX + BX -> RP)
                RP = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_110_001_010: //ADD BX CX RP (BX + CX -> RP)
                RP = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_110_001_011: //ADD BX DX RP (BX + DX -> RP)
                RP = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_110_001_100: //ADD BX EX RP (BX + EX -> RP)
                RP = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_110_001_101: //ADD BX SP RP (BX + SP -> RP)
                RP = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_110_001_110: //ADD BX RP RP (BX + RP -> RP)
                RP = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_110_001_111: //ADD BX PC RP (BX + PC -> RP)
                RP = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_111_001_000: //ADD BX AX PC (BX + AX -> PC)
                PC = aluAdd(BX, AX);
                break;
            case 0b0001_1_00_111_001_001: //ADD BX BX PC (BX + BX -> PC)
                PC = aluAdd(BX, BX);
                break;
            case 0b0001_1_00_111_001_010: //ADD BX CX PC (BX + CX -> PC)
                PC = aluAdd(BX, CX);
                break;
            case 0b0001_1_00_111_001_011: //ADD BX DX PC (BX + DX -> PC)
                PC = aluAdd(BX, DX);
                break;
            case 0b0001_1_00_111_001_100: //ADD BX EX PC (BX + EX -> PC)
                PC = aluAdd(BX, EX);
                break;
            case 0b0001_1_00_111_001_101: //ADD BX SP PC (BX + SP -> PC)
                PC = aluAdd(BX, SP);
                break;
            case 0b0001_1_00_111_001_110: //ADD BX RP PC (BX + RP -> PC)
                PC = aluAdd(BX, RP);
                break;
            case 0b0001_1_00_111_001_111: //ADD BX PC PC (BX + PC -> PC)
                PC = aluAdd(BX, PC);
                break;
            case 0b0001_1_00_000_010_000: //ADD CX AX AX (CX + AX -> AX)
                AX = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_000_010_001: //ADD CX BX AX (CX + BX -> AX)
                AX = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_000_010_010: //ADD CX CX AX (CX + CX -> AX)
                AX = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_000_010_011: //ADD CX DX AX (CX + DX -> AX)
                AX = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_000_010_100: //ADD CX EX AX (CX + EX -> AX)
                AX = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_000_010_101: //ADD CX SP AX (CX + SP -> AX)
                AX = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_000_010_110: //ADD CX RP AX (CX + RP -> AX)
                AX = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_000_010_111: //ADD CX PC AX (CX + PC -> AX)
                AX = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_001_010_000: //ADD CX AX BX (CX + AX -> BX)
                BX = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_001_010_001: //ADD CX BX BX (CX + BX -> BX)
                BX = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_001_010_010: //ADD CX CX BX (CX + CX -> BX)
                BX = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_001_010_011: //ADD CX DX BX (CX + DX -> BX)
                BX = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_001_010_100: //ADD CX EX BX (CX + EX -> BX)
                BX = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_001_010_101: //ADD CX SP BX (CX + SP -> BX)
                BX = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_001_010_110: //ADD CX RP BX (CX + RP -> BX)
                BX = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_001_010_111: //ADD CX PC BX (CX + PC -> BX)
                BX = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_010_010_000: //ADD CX AX CX (CX + AX -> CX)
                CX = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_010_010_001: //ADD CX BX CX (CX + BX -> CX)
                CX = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_010_010_010: //ADD CX CX CX (CX + CX -> CX)
                CX = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_010_010_011: //ADD CX DX CX (CX + DX -> CX)
                CX = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_010_010_100: //ADD CX EX CX (CX + EX -> CX)
                CX = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_010_010_101: //ADD CX SP CX (CX + SP -> CX)
                CX = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_010_010_110: //ADD CX RP CX (CX + RP -> CX)
                CX = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_010_010_111: //ADD CX PC CX (CX + PC -> CX)
                CX = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_011_010_000: //ADD CX AX DX (CX + AX -> DX)
                DX = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_011_010_001: //ADD CX BX DX (CX + BX -> DX)
                DX = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_011_010_010: //ADD CX CX DX (CX + CX -> DX)
                DX = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_011_010_011: //ADD CX DX DX (CX + DX -> DX)
                DX = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_011_010_100: //ADD CX EX DX (CX + EX -> DX)
                DX = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_011_010_101: //ADD CX SP DX (CX + SP -> DX)
                DX = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_011_010_110: //ADD CX RP DX (CX + RP -> DX)
                DX = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_011_010_111: //ADD CX PC DX (CX + PC -> DX)
                DX = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_100_010_000: //ADD CX AX EX (CX + AX -> EX)
                EX = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_100_010_001: //ADD CX EX SP (CX + BX -> SP)
                EX = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_100_010_010: //ADD CX EX SP (CX + CX -> SP)
                EX = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_100_010_011: //ADD CX EX SP (CX + DX -> SP)
                EX = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_100_010_100: //ADD CX EX SP (CX + EX -> SP)
                EX = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_100_010_101: //ADD CX EX SP (CX + SP -> SP)
                EX = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_100_010_110: //ADD CX EX SP (CX + RP -> SP)
                EX = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_100_010_111: //ADD CX EX SP (CX + PC -> SP)
                EX = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_101_010_000: //ADD CX AX SP (CX + AX -> SP)
                SP = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_101_010_001: //ADD CX BX SP (CX + BX -> SP)
                SP = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_101_010_010: //ADD CX CX SP (CX + CX -> SP)
                SP = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_101_010_011: //ADD CX DX SP (CX + DX -> SP)
                SP = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_101_010_100: //ADD CX EX SP (CX + EX -> SP)
                SP = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_101_010_101: //ADD CX SP SP (CX + SP -> SP)
                SP = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_101_010_110: //ADD CX RP SP (CX + RP -> SP)
                SP = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_101_010_111: //ADD CX PC SP (CX + PC -> SP)
                SP = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_110_010_000: //ADD CX AX RP (CX + AX -> RP)
                RP = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_110_010_001: //ADD CX BX RP (CX + BX -> RP)
                RP = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_110_010_010: //ADD CX CX RP (CX + CX -> RP)
                RP = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_110_010_011: //ADD CX DX RP (CX + DX -> RP)
                RP = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_110_010_100: //ADD CX EX RP (CX + EX -> RP)
                RP = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_110_010_101: //ADD CX SP RP (CX + SP -> RP)
                RP = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_110_010_110: //ADD CX RP RP (CX + RP -> RP)
                RP = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_110_010_111: //ADD CX PC RP (CX + PC -> RP)
                RP = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_111_010_000: //ADD CX AX PC (CX + AX -> PC)
                PC = aluAdd(CX, AX);
                break;
            case 0b0001_1_00_111_010_001: //ADD CX BX PC (CX + BX -> PC)
                PC = aluAdd(CX, BX);
                break;
            case 0b0001_1_00_111_010_010: //ADD CX CX PC (CX + CX -> PC)
                PC = aluAdd(CX, CX);
                break;
            case 0b0001_1_00_111_010_011: //ADD CX DX PC (CX + DX -> PC)
                PC = aluAdd(CX, DX);
                break;
            case 0b0001_1_00_111_010_100: //ADD CX EX PC (CX + EX -> PC)
                PC = aluAdd(CX, EX);
                break;
            case 0b0001_1_00_111_010_101: //ADD CX SP PC (CX + SP -> PC)
                PC = aluAdd(CX, SP);
                break;
            case 0b0001_1_00_111_010_110: //ADD CX RP PC (CX + RP -> PC)
                PC = aluAdd(CX, RP);
                break;
            case 0b0001_1_00_111_010_111: //ADD CX PC PC (CX + PC -> PC)
                PC = aluAdd(CX, PC);
                break;
            case 0b0001_1_00_000_011_000: //ADD DX AX AX (DX + AX -> AX)
                AX = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_000_011_001: //ADD DX BX AX (DX + BX -> AX)
                AX = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_000_011_010: //ADD DX CX AX (DX + CX -> AX)
                AX = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_000_011_011: //ADD DX DX AX (DX + DX -> AX)
                AX = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_000_011_100: //ADD DX EX AX (DX + EX -> AX)
                AX = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_000_011_101: //ADD DX SP AX (DX + SP -> AX)
                AX = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_000_011_110: //ADD DX RP AX (DX + RP -> AX)
                AX = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_000_011_111: //ADD DX PC AX (DX + PC -> AX)
                AX = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_001_011_000: //ADD DX AX BX (DX + AX -> BX)
                BX = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_001_011_001: //ADD DX BX BX (DX + BX -> BX)
                BX = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_001_011_010: //ADD DX CX BX (DX + CX -> BX)
                BX = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_001_011_011: //ADD DX DX BX (DX + DX -> BX)
                BX = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_001_011_100: //ADD DX EX BX (DX + EX -> BX)
                BX = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_001_011_101: //ADD DX SP BX (DX + SP -> BX)
                BX = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_001_011_110: //ADD DX RP BX (DX + RP -> BX)
                BX = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_001_011_111: //ADD DX PC BX (DX + PC -> BX)
                BX = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_010_011_000: //ADD DX AX CX (DX + AX -> CX)
                CX = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_010_011_001: //ADD DX BX CX (DX + BX -> CX)
                CX = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_010_011_010: //ADD DX CX CX (DX + CX -> CX)
                CX = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_010_011_011: //ADD DX DX CX (DX + DX -> CX)
                CX = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_010_011_100: //ADD DX EX CX (DX + EX -> CX)
                CX = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_010_011_101: //ADD DX SP CX (DX + SP -> CX)
                CX = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_010_011_110: //ADD DX RP CX (DX + RP -> CX)
                CX = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_010_011_111: //ADD DX PC CX (DX + PC -> CX)
                CX = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_011_011_000: //ADD DX AX DX (DX + AX -> DX)
                DX = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_011_011_001: //ADD DX BX DX (DX + BX -> DX)
                DX = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_011_011_010: //ADD DX CX DX (DX + CX -> DX)
                DX = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_011_011_011: //ADD DX DX DX (DX + DX -> DX)
                DX = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_011_011_100: //ADD DX EX DX (DX + EX -> DX)
                DX = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_011_011_101: //ADD DX SP DX (DX + SP -> DX)
                DX = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_011_011_110: //ADD DX RP DX (DX + RP -> DX)
                DX = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_011_011_111: //ADD DX PC DX (DX + PC -> DX)
                DX = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_100_011_000: //ADD DX AX EX (DX + AX -> EX)
                EX = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_100_011_001: //ADD DX EX SP (DX + BX -> SP)
                EX = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_100_011_010: //ADD DX EX SP (DX + CX -> SP)
                EX = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_100_011_011: //ADD DX EX SP (DX + DX -> SP)
                EX = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_100_011_100: //ADD DX EX SP (DX + EX -> SP)
                EX = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_100_011_101: //ADD DX EX SP (DX + SP -> SP)
                EX = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_100_011_110: //ADD DX EX SP (DX + RP -> SP)
                EX = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_100_011_111: //ADD DX EX SP (DX + PC -> SP)
                EX = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_101_011_000: //ADD DX AX SP (DX + AX -> SP)
                SP = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_101_011_001: //ADD DX BX SP (DX + BX -> SP)
                SP = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_101_011_010: //ADD DX CX SP (DX + CX -> SP)
                SP = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_101_011_011: //ADD DX DX SP (DX + DX -> SP)
                SP = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_101_011_100: //ADD DX EX SP (DX + EX -> SP)
                SP = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_101_011_101: //ADD DX SP SP (DX + SP -> SP)
                SP = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_101_011_110: //ADD DX RP SP (DX + RP -> SP)
                SP = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_101_011_111: //ADD DX PC SP (DX + PC -> SP)
                SP = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_110_011_000: //ADD DX AX RP (DX + AX -> RP)
                RP = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_110_011_001: //ADD DX BX RP (DX + BX -> RP)
                RP = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_110_011_010: //ADD DX CX RP (DX + CX -> RP)
                RP = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_110_011_011: //ADD DX DX RP (DX + DX -> RP)
                RP = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_110_011_100: //ADD DX EX RP (DX + EX -> RP)
                RP = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_110_011_101: //ADD DX SP RP (DX + SP -> RP)
                RP = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_110_011_110: //ADD DX RP RP (DX + RP -> RP)
                RP = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_110_011_111: //ADD DX PC RP (DX + PC -> RP)
                RP = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_111_011_000: //ADD DX AX PC (DX + AX -> PC)
                PC = aluAdd(DX, AX);
                break;
            case 0b0001_1_00_111_011_001: //ADD DX BX PC (DX + BX -> PC)
                PC = aluAdd(DX, BX);
                break;
            case 0b0001_1_00_111_011_010: //ADD DX CX PC (DX + CX -> PC)
                PC = aluAdd(DX, CX);
                break;
            case 0b0001_1_00_111_011_011: //ADD DX DX PC (DX + DX -> PC)
                PC = aluAdd(DX, DX);
                break;
            case 0b0001_1_00_111_011_100: //ADD DX EX PC (DX + EX -> PC)
                PC = aluAdd(DX, EX);
                break;
            case 0b0001_1_00_111_011_101: //ADD DX SP PC (DX + SP -> PC)
                PC = aluAdd(DX, SP);
                break;
            case 0b0001_1_00_111_011_110: //ADD DX RP PC (DX + RP -> PC)
                PC = aluAdd(DX, RP);
                break;
            case 0b0001_1_00_111_011_111: //ADD DX PC PC (DX + PC -> PC)
                PC = aluAdd(DX, PC);
                break;
            case 0b0001_1_00_000_100_000: //ADD EX AX AX (EX + AX -> AX)
                AX = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_000_100_001: //ADD EX BX AX (EX + BX -> AX)
                AX = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_000_100_010: //ADD EX CX AX (EX + CX -> AX)
                AX = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_000_100_011: //ADD EX DX AX (EX + DX -> AX)
                AX = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_000_100_100: //ADD EX EX AX (EX + EX -> AX)
                AX = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_000_100_101: //ADD EX SP AX (EX + SP -> AX)
                AX = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_000_100_110: //ADD EX RP AX (EX + RP -> AX)
                AX = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_000_100_111: //ADD EX PC AX (EX + PC -> AX)
                AX = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_001_100_000: //ADD EX AX BX (EX + AX -> BX)
                BX = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_001_100_001: //ADD EX BX BX (EX + BX -> BX)
                BX = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_001_100_010: //ADD EX CX BX (EX + CX -> BX)
                BX = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_001_100_011: //ADD EX DX BX (EX + DX -> BX)
                BX = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_001_100_100: //ADD EX EX BX (EX + EX -> BX)
                BX = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_001_100_101: //ADD EX SP BX (EX + SP -> BX)
                BX = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_001_100_110: //ADD EX RP BX (EX + RP -> BX)
                BX = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_001_100_111: //ADD EX PC BX (EX + PC -> BX)
                BX = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_010_100_000: //ADD EX AX CX (EX + AX -> CX)
                CX = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_010_100_001: //ADD EX BX CX (EX + BX -> CX)
                CX = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_010_100_010: //ADD EX CX CX (EX + CX -> CX)
                CX = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_010_100_011: //ADD EX DX CX (EX + DX -> CX)
                CX = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_010_100_100: //ADD EX EX CX (EX + EX -> CX)
                CX = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_010_100_101: //ADD EX SP CX (EX + SP -> CX)
                CX = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_010_100_110: //ADD EX RP CX (EX + RP -> CX)
                CX = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_010_100_111: //ADD EX PC CX (EX + PC -> CX)
                CX = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_011_100_000: //ADD EX AX DX (EX + AX -> DX)
                DX = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_011_100_001: //ADD EX BX DX (EX + BX -> DX)
                DX = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_011_100_010: //ADD EX CX DX (EX + CX -> DX)
                DX = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_011_100_011: //ADD EX DX DX (EX + DX -> DX)
                DX = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_011_100_100: //ADD EX EX DX (EX + EX -> DX)
                DX = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_011_100_101: //ADD EX SP DX (EX + SP -> DX)
                DX = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_011_100_110: //ADD EX RP DX (EX + RP -> DX)
                DX = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_011_100_111: //ADD EX PC DX (EX + PC -> DX)
                DX = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_100_100_000: //ADD EX AX EX (EX + AX -> EX)
                EX = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_100_100_001: //ADD EX EX SP (EX + BX -> SP)
                EX = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_100_100_010: //ADD EX EX SP (EX + CX -> SP)
                EX = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_100_100_011: //ADD EX EX SP (EX + DX -> SP)
                EX = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_100_100_100: //ADD EX EX SP (EX + EX -> SP)
                EX = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_100_100_101: //ADD EX EX SP (EX + SP -> SP)
                EX = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_100_100_110: //ADD EX EX SP (EX + RP -> SP)
                EX = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_100_100_111: //ADD EX EX SP (EX + PC -> SP)
                EX = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_101_100_000: //ADD EX AX SP (EX + AX -> SP)
                SP = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_101_100_001: //ADD EX BX SP (EX + BX -> SP)
                SP = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_101_100_010: //ADD EX CX SP (EX + CX -> SP)
                SP = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_101_100_011: //ADD EX DX SP (EX + DX -> SP)
                SP = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_101_100_100: //ADD EX EX SP (EX + EX -> SP)
                SP = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_101_100_101: //ADD EX SP SP (EX + SP -> SP)
                SP = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_101_100_110: //ADD EX RP SP (EX + RP -> SP)
                SP = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_101_100_111: //ADD EX PC SP (EX + PC -> SP)
                SP = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_110_100_000: //ADD EX AX RP (EX + AX -> RP)
                RP = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_110_100_001: //ADD EX BX RP (EX + BX -> RP)
                RP = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_110_100_010: //ADD EX CX RP (EX + CX -> RP)
                RP = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_110_100_011: //ADD EX DX RP (EX + DX -> RP)
                RP = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_110_100_100: //ADD EX EX RP (EX + EX -> RP)
                RP = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_110_100_101: //ADD EX SP RP (EX + SP -> RP)
                RP = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_110_100_110: //ADD EX RP RP (EX + RP -> RP)
                RP = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_110_100_111: //ADD EX PC RP (EX + PC -> RP)
                RP = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_111_100_000: //ADD EX AX PC (EX + AX -> PC)
                PC = aluAdd(EX, AX);
                break;
            case 0b0001_1_00_111_100_001: //ADD EX BX PC (EX + BX -> PC)
                PC = aluAdd(EX, BX);
                break;
            case 0b0001_1_00_111_100_010: //ADD EX CX PC (EX + CX -> PC)
                PC = aluAdd(EX, CX);
                break;
            case 0b0001_1_00_111_100_011: //ADD EX DX PC (EX + DX -> PC)
                PC = aluAdd(EX, DX);
                break;
            case 0b0001_1_00_111_100_100: //ADD EX EX PC (EX + EX -> PC)
                PC = aluAdd(EX, EX);
                break;
            case 0b0001_1_00_111_100_101: //ADD EX SP PC (EX + SP -> PC)
                PC = aluAdd(EX, SP);
                break;
            case 0b0001_1_00_111_100_110: //ADD EX RP PC (EX + RP -> PC)
                PC = aluAdd(EX, RP);
                break;
            case 0b0001_1_00_111_100_111: //ADD EX PC PC (EX + PC -> PC)
                PC = aluAdd(EX, PC);
                break;
            case 0b0001_1_00_000_101_000: //ADD SP AX AX (SP + AX -> AX)
                AX = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_000_101_001: //ADD SP BX AX (SP + BX -> AX)
                AX = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_000_101_010: //ADD SP CX AX (SP + CX -> AX)
                AX = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_000_101_011: //ADD SP DX AX (SP + DX -> AX)
                AX = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_000_101_100: //ADD SP EX AX (SP + EX -> AX)
                AX = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_000_101_101: //ADD SP SP AX (SP + SP -> AX)
                AX = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_000_101_110: //ADD SP RP AX (SP + RP -> AX)
                AX = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_000_101_111: //ADD SP PC AX (SP + PC -> AX)
                AX = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_001_101_000: //ADD SP AX BX (SP + AX -> BX)
                BX = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_001_101_001: //ADD SP BX BX (SP + BX -> BX)
                BX = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_001_101_010: //ADD SP CX BX (SP + CX -> BX)
                BX = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_001_101_011: //ADD SP DX BX (SP + DX -> BX)
                BX = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_001_101_100: //ADD SP EX BX (SP + EX -> BX)
                BX = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_001_101_101: //ADD SP SP BX (SP + SP -> BX)
                BX = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_001_101_110: //ADD SP RP BX (SP + RP -> BX)
                BX = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_001_101_111: //ADD SP PC BX (SP + PC -> BX)
                BX = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_010_101_000: //ADD SP AX CX (SP + AX -> CX)
                CX = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_010_101_001: //ADD SP BX CX (SP + BX -> CX)
                CX = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_010_101_010: //ADD SP CX CX (SP + CX -> CX)
                CX = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_010_101_011: //ADD SP DX CX (SP + DX -> CX)
                CX = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_010_101_100: //ADD SP EX CX (SP + EX -> CX)
                CX = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_010_101_101: //ADD SP SP CX (SP + SP -> CX)
                CX = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_010_101_110: //ADD SP RP CX (SP + RP -> CX)
                CX = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_010_101_111: //ADD SP PC CX (SP + PC -> CX)
                CX = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_011_101_000: //ADD SP AX DX (SP + AX -> DX)
                DX = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_011_101_001: //ADD SP BX DX (SP + BX -> DX)
                DX = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_011_101_010: //ADD SP CX DX (SP + CX -> DX)
                DX = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_011_101_011: //ADD SP DX DX (SP + DX -> DX)
                DX = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_011_101_100: //ADD SP EX DX (SP + EX -> DX)
                DX = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_011_101_101: //ADD SP SP DX (SP + SP -> DX)
                DX = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_011_101_110: //ADD SP RP DX (SP + RP -> DX)
                DX = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_011_101_111: //ADD SP PC DX (SP + PC -> DX)
                DX = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_100_101_000: //ADD SP AX EX (SP + AX -> EX)
                EX = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_100_101_001: //ADD SP EX SP (SP + BX -> SP)
                EX = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_100_101_010: //ADD SP EX SP (SP + CX -> SP)
                EX = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_100_101_011: //ADD SP EX SP (SP + DX -> SP)
                EX = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_100_101_100: //ADD SP EX SP (SP + EX -> SP)
                EX = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_100_101_101: //ADD SP EX SP (SP + SP -> SP)
                EX = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_100_101_110: //ADD SP EX SP (SP + RP -> SP)
                EX = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_100_101_111: //ADD SP EX SP (SP + PC -> SP)
                EX = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_101_101_000: //ADD SP AX SP (SP + AX -> SP)
                SP = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_101_101_001: //ADD SP BX SP (SP + BX -> SP)
                SP = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_101_101_010: //ADD SP CX SP (SP + CX -> SP)
                SP = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_101_101_011: //ADD SP DX SP (SP + DX -> SP)
                SP = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_101_101_100: //ADD SP EX SP (SP + EX -> SP)
                SP = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_101_101_101: //ADD SP SP SP (SP + SP -> SP)
                SP = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_101_101_110: //ADD SP RP SP (SP + RP -> SP)
                SP = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_101_101_111: //ADD SP PC SP (SP + PC -> SP)
                SP = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_110_101_000: //ADD SP AX RP (SP + AX -> RP)
                RP = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_110_101_001: //ADD SP BX RP (SP + BX -> RP)
                RP = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_110_101_010: //ADD SP CX RP (SP + CX -> RP)
                RP = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_110_101_011: //ADD SP DX RP (SP + DX -> RP)
                RP = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_110_101_100: //ADD SP EX RP (SP + EX -> RP)
                RP = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_110_101_101: //ADD SP SP RP (SP + SP -> RP)
                RP = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_110_101_110: //ADD SP RP RP (SP + RP -> RP)
                RP = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_110_101_111: //ADD SP PC RP (SP + PC -> RP)
                RP = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_111_101_000: //ADD SP AX PC (SP + AX -> PC)
                PC = aluAdd(SP, AX);
                break;
            case 0b0001_1_00_111_101_001: //ADD SP BX PC (SP + BX -> PC)
                PC = aluAdd(SP, BX);
                break;
            case 0b0001_1_00_111_101_010: //ADD SP CX PC (SP + CX -> PC)
                PC = aluAdd(SP, CX);
                break;
            case 0b0001_1_00_111_101_011: //ADD SP DX PC (SP + DX -> PC)
                PC = aluAdd(SP, DX);
                break;
            case 0b0001_1_00_111_101_100: //ADD SP EX PC (SP + EX -> PC)
                PC = aluAdd(SP, EX);
                break;
            case 0b0001_1_00_111_101_101: //ADD SP SP PC (SP + SP -> PC)
                PC = aluAdd(SP, SP);
                break;
            case 0b0001_1_00_111_101_110: //ADD SP RP PC (SP + RP -> PC)
                PC = aluAdd(SP, RP);
                break;
            case 0b0001_1_00_111_101_111: //ADD SP PC PC (SP + PC -> PC)
                PC = aluAdd(SP, PC);
                break;
            case 0b0001_1_00_000_110_000: //ADD RP AX AX (RP + AX -> AX)
                AX = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_000_110_001: //ADD RP BX AX (RP + BX -> AX)
                AX = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_000_110_010: //ADD RP CX AX (RP + CX -> AX)
                AX = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_000_110_011: //ADD RP DX AX (RP + DX -> AX)
                AX = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_000_110_100: //ADD RP EX AX (RP + EX -> AX)
                AX = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_000_110_101: //ADD RP SP AX (RP + SP -> AX)
                AX = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_000_110_110: //ADD RP RP AX (RP + RP -> AX)
                AX = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_000_110_111: //ADD RP PC AX (RP + PC -> AX)
                AX = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_001_110_000: //ADD RP AX BX (RP + AX -> BX)
                BX = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_001_110_001: //ADD RP BX BX (RP + BX -> BX)
                BX = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_001_110_010: //ADD RP CX BX (RP + CX -> BX)
                BX = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_001_110_011: //ADD RP DX BX (RP + DX -> BX)
                BX = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_001_110_100: //ADD RP EX BX (RP + EX -> BX)
                BX = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_001_110_101: //ADD RP SP BX (RP + SP -> BX)
                BX = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_001_110_110: //ADD RP RP BX (RP + RP -> BX)
                BX = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_001_110_111: //ADD RP PC BX (RP + PC -> BX)
                BX = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_010_110_000: //ADD RP AX CX (RP + AX -> CX)
                CX = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_010_110_001: //ADD RP BX CX (RP + BX -> CX)
                CX = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_010_110_010: //ADD RP CX CX (RP + CX -> CX)
                CX = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_010_110_011: //ADD RP DX CX (RP + DX -> CX)
                CX = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_010_110_100: //ADD RP EX CX (RP + EX -> CX)
                CX = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_010_110_101: //ADD RP SP CX (RP + SP -> CX)
                CX = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_010_110_110: //ADD RP RP CX (RP + RP -> CX)
                CX = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_010_110_111: //ADD RP PC CX (RP + PC -> CX)
                CX = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_011_110_000: //ADD RP AX DX (RP + AX -> DX)
                DX = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_011_110_001: //ADD RP BX DX (RP + BX -> DX)
                DX = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_011_110_010: //ADD RP CX DX (RP + CX -> DX)
                DX = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_011_110_011: //ADD RP DX DX (RP + DX -> DX)
                DX = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_011_110_100: //ADD RP EX DX (RP + EX -> DX)
                DX = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_011_110_101: //ADD RP SP DX (RP + SP -> DX)
                DX = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_011_110_110: //ADD RP RP DX (RP + RP -> DX)
                DX = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_011_110_111: //ADD RP PC DX (RP + PC -> DX)
                DX = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_100_110_000: //ADD RP AX EX (RP + AX -> EX)
                EX = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_100_110_001: //ADD RP EX SP (RP + BX -> SP)
                EX = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_100_110_010: //ADD RP EX SP (RP + CX -> SP)
                EX = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_100_110_011: //ADD RP EX SP (RP + DX -> SP)
                EX = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_100_110_100: //ADD RP EX SP (RP + EX -> SP)
                EX = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_100_110_101: //ADD RP EX SP (RP + SP -> SP)
                EX = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_100_110_110: //ADD RP EX SP (RP + RP -> SP)
                EX = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_100_110_111: //ADD RP EX SP (RP + PC -> SP)
                EX = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_101_110_000: //ADD RP AX SP (RP + AX -> SP)
                SP = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_101_110_001: //ADD RP BX SP (RP + BX -> SP)
                SP = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_101_110_010: //ADD RP CX SP (RP + CX -> SP)
                SP = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_101_110_011: //ADD RP DX SP (RP + DX -> SP)
                SP = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_101_110_100: //ADD RP EX SP (RP + EX -> SP)
                SP = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_101_110_101: //ADD RP SP SP (RP + SP -> SP)
                SP = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_101_110_110: //ADD RP RP SP (RP + RP -> SP)
                SP = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_101_110_111: //ADD RP PC SP (RP + PC -> SP)
                SP = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_110_110_000: //ADD RP AX RP (RP + AX -> RP)
                RP = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_110_110_001: //ADD RP BX RP (RP + BX -> RP)
                RP = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_110_110_010: //ADD RP CX RP (RP + CX -> RP)
                RP = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_110_110_011: //ADD RP DX RP (RP + DX -> RP)
                RP = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_110_110_100: //ADD RP EX RP (RP + EX -> RP)
                RP = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_110_110_101: //ADD RP SP RP (RP + SP -> RP)
                RP = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_110_110_110: //ADD RP RP RP (RP + RP -> RP)
                RP = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_110_110_111: //ADD RP PC RP (RP + PC -> RP)
                RP = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_111_110_000: //ADD RP AX PC (RP + AX -> PC)
                PC = aluAdd(RP, AX);
                break;
            case 0b0001_1_00_111_110_001: //ADD RP BX PC (RP + BX -> PC)
                PC = aluAdd(RP, BX);
                break;
            case 0b0001_1_00_111_110_010: //ADD RP CX PC (RP + CX -> PC)
                PC = aluAdd(RP, CX);
                break;
            case 0b0001_1_00_111_110_011: //ADD RP DX PC (RP + DX -> PC)
                PC = aluAdd(RP, DX);
                break;
            case 0b0001_1_00_111_110_100: //ADD RP EX PC (RP + EX -> PC)
                PC = aluAdd(RP, EX);
                break;
            case 0b0001_1_00_111_110_101: //ADD RP SP PC (RP + SP -> PC)
                PC = aluAdd(RP, SP);
                break;
            case 0b0001_1_00_111_110_110: //ADD RP RP PC (RP + RP -> PC)
                PC = aluAdd(RP, RP);
                break;
            case 0b0001_1_00_111_110_111: //ADD RP PC PC (RP + PC -> PC)
                PC = aluAdd(RP, PC);
                break;
            case 0b0001_1_00_000_111_000: //ADD PC AX AX (PC + AX -> AX)
                AX = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_000_111_001: //ADD PC BX AX (PC + BX -> AX)
                AX = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_000_111_010: //ADD PC CX AX (PC + CX -> AX)
                AX = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_000_111_011: //ADD PC DX AX (PC + DX -> AX)
                AX = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_000_111_100: //ADD PC EX AX (PC + EX -> AX)
                AX = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_000_111_101: //ADD PC SP AX (PC + SP -> AX)
                AX = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_000_111_110: //ADD PC RP AX (PC + RP -> AX)
                AX = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_000_111_111: //ADD PC PC AX (PC + PC -> AX)
                AX = aluAdd(PC, PC);
                break;
            case 0b0001_1_00_001_111_000: //ADD PC AX BX (PC + AX -> BX)
                BX = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_001_111_001: //ADD PC BX BX (PC + BX -> BX)
                BX = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_001_111_010: //ADD PC CX BX (PC + CX -> BX)
                BX = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_001_111_011: //ADD PC DX BX (PC + DX -> BX)
                BX = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_001_111_100: //ADD PC EX BX (PC + EX -> BX)
                BX = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_001_111_101: //ADD PC SP BX (PC + SP -> BX)
                BX = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_001_111_110: //ADD PC RP BX (PC + RP -> BX)
                BX = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_001_111_111: //ADD PC PC BX (PC + PC -> BX)
                BX = aluAdd(PC, PC);
                break;
            case 0b0001_1_00_010_111_000: //ADD PC AX CX (PC + AX -> CX)
                CX = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_010_111_001: //ADD PC BX CX (PC + BX -> CX)
                CX = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_010_111_010: //ADD PC CX CX (PC + CX -> CX)
                CX = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_010_111_011: //ADD PC DX CX (PC + DX -> CX)
                CX = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_010_111_100: //ADD PC EX CX (PC + EX -> CX)
                CX = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_010_111_101: //ADD PC SP CX (PC + SP -> CX)
                CX = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_010_111_110: //ADD PC RP CX (PC + RP -> CX)
                CX = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_010_111_111: //ADD PC PC CX (PC + PC -> CX)
                CX = aluAdd(PC, PC);
                break;
            case 0b0001_1_00_011_111_000: //ADD PC AX DX (PC + AX -> DX)
                DX = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_011_111_001: //ADD PC BX DX (PC + BX -> DX)
                DX = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_011_111_010: //ADD PC CX DX (PC + CX -> DX)
                DX = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_011_111_011: //ADD PC DX DX (PC + DX -> DX)
                DX = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_011_111_100: //ADD PC EX DX (PC + EX -> DX)
                DX = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_011_111_101: //ADD PC SP DX (PC + SP -> DX)
                DX = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_011_111_110: //ADD PC RP DX (PC + RP -> DX)
                DX = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_011_111_111: //ADD PC PC DX (PC + PC -> DX)
                DX = aluAdd(PC, PC);
                break;
            case 0b0001_1_00_100_111_000: //ADD PC AX EX (PC + AX -> EX)
                EX = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_100_111_001: //ADD PC EX SP (PC + BX -> SP)
                EX = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_100_111_010: //ADD PC EX SP (PC + CX -> SP)
                EX = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_100_111_011: //ADD PC EX SP (PC + DX -> SP)
                EX = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_100_111_100: //ADD PC EX SP (PC + EX -> SP)
                EX = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_100_111_101: //ADD PC EX SP (PC + SP -> SP)
                EX = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_100_111_110: //ADD PC EX SP (PC + RP -> SP)
                EX = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_100_111_111: //ADD PC EX SP (PC + PC -> SP)
                EX = aluAdd(PC, PC);
                break;
            case 0b0001_1_00_101_111_000: //ADD PC AX SP (PC + AX -> SP)
                SP = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_101_111_001: //ADD PC BX SP (PC + BX -> SP)
                SP = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_101_111_010: //ADD PC CX SP (PC + CX -> SP)
                SP = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_101_111_011: //ADD PC DX SP (PC + DX -> SP)
                SP = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_101_111_100: //ADD PC EX SP (PC + EX -> SP)
                SP = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_101_111_101: //ADD PC SP SP (PC + SP -> SP)
                SP = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_101_111_110: //ADD PC RP SP (PC + RP -> SP)
                SP = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_101_111_111: //ADD PC PC SP (PC + PC -> SP)
                SP = aluAdd(PC, PC);
                break;
            case 0b0001_1_00_110_111_000: //ADD PC AX RP (PC + AX -> RP)
                RP = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_110_111_001: //ADD PC BX RP (PC + BX -> RP)
                RP = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_110_111_010: //ADD PC CX RP (PC + CX -> RP)
                RP = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_110_111_011: //ADD PC DX RP (PC + DX -> RP)
                RP = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_110_111_100: //ADD PC EX RP (PC + EX -> RP)
                RP = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_110_111_101: //ADD PC SP RP (PC + SP -> RP)
                RP = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_110_111_110: //ADD PC RP RP (PC + RP -> RP)
                RP = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_110_111_111: //ADD PC PC RP (PC + PC -> RP)
                RP = aluAdd(PC, PC);
                break;
            case 0b0001_1_00_111_111_000: //ADD PC AX PC (PC + AX -> PC)
                PC = aluAdd(PC, AX);
                break;
            case 0b0001_1_00_111_111_001: //ADD PC BX PC (PC + BX -> PC)
                PC = aluAdd(PC, BX);
                break;
            case 0b0001_1_00_111_111_010: //ADD PC CX PC (PC + CX -> PC)
                PC = aluAdd(PC, CX);
                break;
            case 0b0001_1_00_111_111_011: //ADD PC DX PC (PC + DX -> PC)
                PC = aluAdd(PC, DX);
                break;
            case 0b0001_1_00_111_111_100: //ADD PC EX PC (PC + EX -> PC)
                PC = aluAdd(PC, EX);
                break;
            case 0b0001_1_00_111_111_101: //ADD PC SP PC (PC + SP -> PC)
                PC = aluAdd(PC, SP);
                break;
            case 0b0001_1_00_111_111_110: //ADD PC RP PC (PC + RP -> PC)
                PC = aluAdd(PC, RP);
                break;
            case 0b0001_1_00_111_111_111: //ADD PC PC PC (PC + PC -> PC)
                PC = aluAdd(PC, PC);
                break;

            default:
                unknownInstruction(INST);
        }

    }

    /*
     * AND: Bit-wise AND two registers together, storing into the destination register
     */
    private void executeANDInstruction() {
        switch (INST) {
            case 0b0001_1_01_000_000_000: //AND AX AX AX (AX & AX -> AX)
                AX = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_000_000_001: //AND AX BX AX (AX & BX -> AX)
                AX = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_000_000_010: //AND AX CX AX (AX & CX -> AX)
                AX = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_000_000_011: //AND AX DX AX (AX & DX -> AX)
                AX = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_000_000_100: //AND AX EX AX (AX & EX -> AX)
                AX = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_000_000_101: //AND AX SP AX (AX & SP -> AX)
                AX = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_000_000_110: //AND AX RP AX (AX & RP -> AX)
                AX = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_000_000_111: //AND AX PC AX (AX & PC -> AX)
                AX = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_001_000_000: //AND AX AX BX (AX & AX -> BX)
                BX = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_001_000_001: //AND AX BX BX (AX & BX -> BX)
                BX = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_001_000_010: //AND AX CX BX (AX & CX -> BX)
                BX = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_001_000_011: //AND AX DX BX (AX & DX -> BX)
                BX = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_001_000_100: //AND AX EX BX (AX & EX -> BX)
                BX = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_001_000_101: //AND AX SP BX (AX & SP -> BX)
                BX = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_001_000_110: //AND AX RP BX (AX & RP -> BX)
                BX = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_001_000_111: //AND AX PC BX (AX & PC -> BX)
                BX = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_010_000_000: //AND AX AX CX (AX & AX -> CX)
                CX = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_010_000_001: //AND AX BX CX (AX & BX -> CX)
                CX = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_010_000_010: //AND AX CX CX (AX & CX -> CX)
                CX = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_010_000_011: //AND AX DX CX (AX & DX -> CX)
                CX = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_010_000_100: //AND AX EX CX (AX & EX -> CX)
                CX = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_010_000_101: //AND AX SP CX (AX & SP -> CX)
                CX = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_010_000_110: //AND AX RP CX (AX & RP -> CX)
                CX = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_010_000_111: //AND AX PC CX (AX & PC -> CX)
                CX = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_011_000_000: //AND AX AX DX (AX & AX -> DX)
                DX = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_011_000_001: //AND AX BX DX (AX & BX -> DX)
                DX = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_011_000_010: //AND AX CX DX (AX & CX -> DX)
                DX = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_011_000_011: //AND AX DX DX (AX & DX -> DX)
                DX = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_011_000_100: //AND AX EX DX (AX & EX -> DX)
                DX = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_011_000_101: //AND AX SP DX (AX & SP -> DX)
                DX = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_011_000_110: //AND AX RP DX (AX & RP -> DX)
                DX = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_011_000_111: //AND AX PC DX (AX & PC -> DX)
                DX = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_100_000_000: //AND AX AX EX (AX & AX -> EX)
                EX = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_100_000_001: //AND AX EX SP (AX & BX -EXSP)
                EX = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_100_000_010: //AND AX EX SP (AX & CX -EXSP)
                EX = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_100_000_011: //AND AX EX SP (AX & DX -EXSP)
                EX = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_100_000_100: //AND AX EX SP (AX & EX -EXSP)
                EX = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_100_000_101: //AND AX EX SP (AX & SP -EXSP)
                EX = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_100_000_110: //AND AX EX SP (AX & RP -EXSP)
                EX = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_100_000_111: //AND AX EX SP (AX & PC -EXSP)
                EX = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_101_000_000: //AND AX AX SP (AX & AX -> SP)
                SP = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_101_000_001: //AND AX BX SP (AX & BX -> SP)
                SP = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_101_000_010: //AND AX CX SP (AX & CX -> SP)
                SP = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_101_000_011: //AND AX DX SP (AX & DX -> SP)
                SP = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_101_000_100: //AND AX EX SP (AX & EX -> SP)
                SP = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_101_000_101: //AND AX SP SP (AX & SP -> SP)
                SP = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_101_000_110: //AND AX RP SP (AX & RP -> SP)
                SP = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_101_000_111: //AND AX PC SP (AX & PC -> SP)
                SP = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_110_000_000: //AND AX AX RP (AX & AX -> RP)
                RP = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_110_000_001: //AND AX BX RP (AX & BX -> RP)
                RP = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_110_000_010: //AND AX CX RP (AX & CX -> RP)
                RP = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_110_000_011: //AND AX DX RP (AX & DX -> RP)
                RP = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_110_000_100: //AND AX EX RP (AX & EX -> RP)
                RP = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_110_000_101: //AND AX SP RP (AX & SP -> RP)
                RP = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_110_000_110: //AND AX RP RP (AX & RP -> RP)
                RP = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_110_000_111: //AND AX PC RP (AX & PC -> RP)
                RP = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_111_000_000: //AND AX AX PC (AX & AX -> PC)
                PC = aluAnd(AX, AX);
                break;
            case 0b0001_1_01_111_000_001: //AND AX BX PC (AX & BX -> PC)
                PC = aluAnd(AX, BX);
                break;
            case 0b0001_1_01_111_000_010: //AND AX CX PC (AX & CX -> PC)
                PC = aluAnd(AX, CX);
                break;
            case 0b0001_1_01_111_000_011: //AND AX DX PC (AX & DX -> PC)
                PC = aluAnd(AX, DX);
                break;
            case 0b0001_1_01_111_000_100: //AND AX EX PC (AX & EX -> PC)
                PC = aluAnd(AX, EX);
                break;
            case 0b0001_1_01_111_000_101: //AND AX SP PC (AX & SP -> PC)
                PC = aluAnd(AX, SP);
                break;
            case 0b0001_1_01_111_000_110: //AND AX RP PC (AX & RP -> PC)
                PC = aluAnd(AX, RP);
                break;
            case 0b0001_1_01_111_000_111: //AND AX PC PC (AX & PC -> PC)
                PC = aluAnd(AX, PC);
                break;
            case 0b0001_1_01_000_001_000: //AND BX AX AX (BX & AX -> AX)
                AX = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_000_001_001: //AND BX BX AX (BX & BX -> AX)
                AX = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_000_001_010: //AND BX CX AX (BX & CX -> AX)
                AX = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_000_001_011: //AND BX DX AX (BX & DX -> AX)
                AX = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_000_001_100: //AND BX EX AX (BX & EX -> AX)
                AX = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_000_001_101: //AND BX SP AX (BX & SP -> AX)
                AX = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_000_001_110: //AND BX RP AX (BX & RP -> AX)
                AX = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_000_001_111: //AND BX PC AX (BX & PC -> AX)
                AX = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_001_001_000: //AND BX AX BX (BX & AX -> BX)
                BX = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_001_001_001: //AND BX BX BX (BX & BX -> BX)
                BX = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_001_001_010: //AND BX CX BX (BX & CX -> BX)
                BX = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_001_001_011: //AND BX DX BX (BX & DX -> BX)
                BX = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_001_001_100: //AND BX EX BX (BX & EX -> BX)
                BX = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_001_001_101: //AND BX SP BX (BX & SP -> BX)
                BX = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_001_001_110: //AND BX RP BX (BX & RP -> BX)
                BX = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_001_001_111: //AND BX PC BX (BX & PC -> BX)
                BX = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_010_001_000: //AND BX AX CX (BX & AX -> CX)
                CX = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_010_001_001: //AND BX BX CX (BX & BX -> CX)
                CX = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_010_001_010: //AND BX CX CX (BX & CX -> CX)
                CX = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_010_001_011: //AND BX DX CX (BX & DX -> CX)
                CX = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_010_001_100: //AND BX EX CX (BX & EX -> CX)
                CX = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_010_001_101: //AND BX SP CX (BX & SP -> CX)
                CX = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_010_001_110: //AND BX RP CX (BX & RP -> CX)
                CX = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_010_001_111: //AND BX PC CX (BX & PC -> CX)
                CX = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_011_001_000: //AND BX AX DX (BX & AX -> DX)
                DX = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_011_001_001: //AND BX BX DX (BX & BX -> DX)
                DX = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_011_001_010: //AND BX CX DX (BX & CX -> DX)
                DX = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_011_001_011: //AND BX DX DX (BX & DX -> DX)
                DX = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_011_001_100: //AND BX EX DX (BX & EX -> DX)
                DX = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_011_001_101: //AND BX SP DX (BX & SP -> DX)
                DX = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_011_001_110: //AND BX RP DX (BX & RP -> DX)
                DX = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_011_001_111: //AND BX PC DX (BX & PC -> DX)
                DX = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_100_001_000: //AND BX AX EX (BX & AX -> EX)
                EX = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_100_001_001: //AND BX EX SP (BX & BX -> SP)
                EX = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_100_001_010: //AND BX EX SP (BX & CX -> SP)
                EX = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_100_001_011: //AND BX EX SP (BX & DX -> SP)
                EX = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_100_001_100: //AND BX EX SP (BX & EX -> SP)
                EX = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_100_001_101: //AND BX EX SP (BX & SP -> SP)
                EX = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_100_001_110: //AND BX EX SP (BX & RP -> SP)
                EX = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_100_001_111: //AND BX EX SP (BX & PC -> SP)
                EX = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_101_001_000: //AND BX AX SP (BX & AX -> SP)
                SP = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_101_001_001: //AND BX BX SP (BX & BX -> SP)
                SP = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_101_001_010: //AND BX CX SP (BX & CX -> SP)
                SP = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_101_001_011: //AND BX DX SP (BX & DX -> SP)
                SP = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_101_001_100: //AND BX EX SP (BX & EX -> SP)
                SP = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_101_001_101: //AND BX SP SP (BX & SP -> SP)
                SP = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_101_001_110: //AND BX RP SP (BX & RP -> SP)
                SP = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_101_001_111: //AND BX PC SP (BX & PC -> SP)
                SP = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_110_001_000: //AND BX AX RP (BX & AX -> RP)
                RP = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_110_001_001: //AND BX BX RP (BX & BX -> RP)
                RP = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_110_001_010: //AND BX CX RP (BX & CX -> RP)
                RP = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_110_001_011: //AND BX DX RP (BX & DX -> RP)
                RP = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_110_001_100: //AND BX EX RP (BX & EX -> RP)
                RP = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_110_001_101: //AND BX SP RP (BX & SP -> RP)
                RP = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_110_001_110: //AND BX RP RP (BX & RP -> RP)
                RP = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_110_001_111: //AND BX PC RP (BX & PC -> RP)
                RP = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_111_001_000: //AND BX AX PC (BX & AX -> PC)
                PC = aluAnd(BX, AX);
                break;
            case 0b0001_1_01_111_001_001: //AND BX BX PC (BX & BX -> PC)
                PC = aluAnd(BX, BX);
                break;
            case 0b0001_1_01_111_001_010: //AND BX CX PC (BX & CX -> PC)
                PC = aluAnd(BX, CX);
                break;
            case 0b0001_1_01_111_001_011: //AND BX DX PC (BX & DX -> PC)
                PC = aluAnd(BX, DX);
                break;
            case 0b0001_1_01_111_001_100: //AND BX EX PC (BX & EX -> PC)
                PC = aluAnd(BX, EX);
                break;
            case 0b0001_1_01_111_001_101: //AND BX SP PC (BX & SP -> PC)
                PC = aluAnd(BX, SP);
                break;
            case 0b0001_1_01_111_001_110: //AND BX RP PC (BX & RP -> PC)
                PC = aluAnd(BX, RP);
                break;
            case 0b0001_1_01_111_001_111: //AND BX PC PC (BX & PC -> PC)
                PC = aluAnd(BX, PC);
                break;
            case 0b0001_1_01_000_010_000: //AND CX AX AX (CX & AX -> AX)
                AX = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_000_010_001: //AND CX BX AX (CX & BX -> AX)
                AX = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_000_010_010: //AND CX CX AX (CX & CX -> AX)
                AX = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_000_010_011: //AND CX DX AX (CX & DX -> AX)
                AX = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_000_010_100: //AND CX EX AX (CX & EX -> AX)
                AX = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_000_010_101: //AND CX SP AX (CX & SP -> AX)
                AX = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_000_010_110: //AND CX RP AX (CX & RP -> AX)
                AX = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_000_010_111: //AND CX PC AX (CX & PC -> AX)
                AX = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_001_010_000: //AND CX AX BX (CX & AX -> BX)
                BX = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_001_010_001: //AND CX BX BX (CX & BX -> BX)
                BX = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_001_010_010: //AND CX CX BX (CX & CX -> BX)
                BX = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_001_010_011: //AND CX DX BX (CX & DX -> BX)
                BX = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_001_010_100: //AND CX EX BX (CX & EX -> BX)
                BX = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_001_010_101: //AND CX SP BX (CX & SP -> BX)
                BX = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_001_010_110: //AND CX RP BX (CX & RP -> BX)
                BX = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_001_010_111: //AND CX PC BX (CX & PC -> BX)
                BX = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_010_010_000: //AND CX AX CX (CX & AX -> CX)
                CX = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_010_010_001: //AND CX BX CX (CX & BX -> CX)
                CX = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_010_010_010: //AND CX CX CX (CX & CX -> CX)
                CX = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_010_010_011: //AND CX DX CX (CX & DX -> CX)
                CX = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_010_010_100: //AND CX EX CX (CX & EX -> CX)
                CX = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_010_010_101: //AND CX SP CX (CX & SP -> CX)
                CX = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_010_010_110: //AND CX RP CX (CX & RP -> CX)
                CX = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_010_010_111: //AND CX PC CX (CX & PC -> CX)
                CX = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_011_010_000: //AND CX AX DX (CX & AX -> DX)
                DX = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_011_010_001: //AND CX BX DX (CX & BX -> DX)
                DX = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_011_010_010: //AND CX CX DX (CX & CX -> DX)
                DX = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_011_010_011: //AND CX DX DX (CX & DX -> DX)
                DX = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_011_010_100: //AND CX EX DX (CX & EX -> DX)
                DX = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_011_010_101: //AND CX SP DX (CX & SP -> DX)
                DX = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_011_010_110: //AND CX RP DX (CX & RP -> DX)
                DX = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_011_010_111: //AND CX PC DX (CX & PC -> DX)
                DX = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_100_010_000: //AND CX AX EX (CX & AX -> EX)
                EX = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_100_010_001: //AND CX EX SP (CX & BX -> SP)
                EX = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_100_010_010: //AND CX EX SP (CX & CX -> SP)
                EX = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_100_010_011: //AND CX EX SP (CX & DX -> SP)
                EX = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_100_010_100: //AND CX EX SP (CX & EX -> SP)
                EX = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_100_010_101: //AND CX EX SP (CX & SP -> SP)
                EX = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_100_010_110: //AND CX EX SP (CX & RP -> SP)
                EX = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_100_010_111: //AND CX EX SP (CX & PC -> SP)
                EX = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_101_010_000: //AND CX AX SP (CX & AX -> SP)
                SP = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_101_010_001: //AND CX BX SP (CX & BX -> SP)
                SP = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_101_010_010: //AND CX CX SP (CX & CX -> SP)
                SP = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_101_010_011: //AND CX DX SP (CX & DX -> SP)
                SP = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_101_010_100: //AND CX EX SP (CX & EX -> SP)
                SP = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_101_010_101: //AND CX SP SP (CX & SP -> SP)
                SP = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_101_010_110: //AND CX RP SP (CX & RP -> SP)
                SP = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_101_010_111: //AND CX PC SP (CX & PC -> SP)
                SP = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_110_010_000: //AND CX AX RP (CX & AX -> RP)
                RP = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_110_010_001: //AND CX BX RP (CX & BX -> RP)
                RP = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_110_010_010: //AND CX CX RP (CX & CX -> RP)
                RP = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_110_010_011: //AND CX DX RP (CX & DX -> RP)
                RP = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_110_010_100: //AND CX EX RP (CX & EX -> RP)
                RP = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_110_010_101: //AND CX SP RP (CX & SP -> RP)
                RP = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_110_010_110: //AND CX RP RP (CX & RP -> RP)
                RP = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_110_010_111: //AND CX PC RP (CX & PC -> RP)
                RP = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_111_010_000: //AND CX AX PC (CX & AX -> PC)
                PC = aluAnd(CX, AX);
                break;
            case 0b0001_1_01_111_010_001: //AND CX BX PC (CX & BX -> PC)
                PC = aluAnd(CX, BX);
                break;
            case 0b0001_1_01_111_010_010: //AND CX CX PC (CX & CX -> PC)
                PC = aluAnd(CX, CX);
                break;
            case 0b0001_1_01_111_010_011: //AND CX DX PC (CX & DX -> PC)
                PC = aluAnd(CX, DX);
                break;
            case 0b0001_1_01_111_010_100: //AND CX EX PC (CX & EX -> PC)
                PC = aluAnd(CX, EX);
                break;
            case 0b0001_1_01_111_010_101: //AND CX SP PC (CX & SP -> PC)
                PC = aluAnd(CX, SP);
                break;
            case 0b0001_1_01_111_010_110: //AND CX RP PC (CX & RP -> PC)
                PC = aluAnd(CX, RP);
                break;
            case 0b0001_1_01_111_010_111: //AND CX PC PC (CX & PC -> PC)
                PC = aluAnd(CX, PC);
                break;
            case 0b0001_1_01_000_011_000: //AND DX AX AX (DX & AX -> AX)
                AX = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_000_011_001: //AND DX BX AX (DX & BX -> AX)
                AX = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_000_011_010: //AND DX CX AX (DX & CX -> AX)
                AX = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_000_011_011: //AND DX DX AX (DX & DX -> AX)
                AX = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_000_011_100: //AND DX EX AX (DX & EX -> AX)
                AX = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_000_011_101: //AND DX SP AX (DX & SP -> AX)
                AX = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_000_011_110: //AND DX RP AX (DX & RP -> AX)
                AX = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_000_011_111: //AND DX PC AX (DX & PC -> AX)
                AX = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_001_011_000: //AND DX AX BX (DX & AX -> BX)
                BX = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_001_011_001: //AND DX BX BX (DX & BX -> BX)
                BX = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_001_011_010: //AND DX CX BX (DX & CX -> BX)
                BX = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_001_011_011: //AND DX DX BX (DX & DX -> BX)
                BX = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_001_011_100: //AND DX EX BX (DX & EX -> BX)
                BX = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_001_011_101: //AND DX SP BX (DX & SP -> BX)
                BX = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_001_011_110: //AND DX RP BX (DX & RP -> BX)
                BX = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_001_011_111: //AND DX PC BX (DX & PC -> BX)
                BX = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_010_011_000: //AND DX AX CX (DX & AX -> CX)
                CX = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_010_011_001: //AND DX BX CX (DX & BX -> CX)
                CX = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_010_011_010: //AND DX CX CX (DX & CX -> CX)
                CX = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_010_011_011: //AND DX DX CX (DX & DX -> CX)
                CX = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_010_011_100: //AND DX EX CX (DX & EX -> CX)
                CX = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_010_011_101: //AND DX SP CX (DX & SP -> CX)
                CX = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_010_011_110: //AND DX RP CX (DX & RP -> CX)
                CX = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_010_011_111: //AND DX PC CX (DX & PC -> CX)
                CX = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_011_011_000: //AND DX AX DX (DX & AX -> DX)
                DX = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_011_011_001: //AND DX BX DX (DX & BX -> DX)
                DX = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_011_011_010: //AND DX CX DX (DX & CX -> DX)
                DX = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_011_011_011: //AND DX DX DX (DX & DX -> DX)
                DX = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_011_011_100: //AND DX EX DX (DX & EX -> DX)
                DX = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_011_011_101: //AND DX SP DX (DX & SP -> DX)
                DX = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_011_011_110: //AND DX RP DX (DX & RP -> DX)
                DX = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_011_011_111: //AND DX PC DX (DX & PC -> DX)
                DX = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_100_011_000: //AND DX AX EX (DX & AX -> EX)
                EX = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_100_011_001: //AND DX EX SP (DX & BX -> SP)
                EX = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_100_011_010: //AND DX EX SP (DX & CX -> SP)
                EX = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_100_011_011: //AND DX EX SP (DX & DX -> SP)
                EX = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_100_011_100: //AND DX EX SP (DX & EX -> SP)
                EX = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_100_011_101: //AND DX EX SP (DX & SP -> SP)
                EX = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_100_011_110: //AND DX EX SP (DX & RP -> SP)
                EX = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_100_011_111: //AND DX EX SP (DX & PC -> SP)
                EX = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_101_011_000: //AND DX AX SP (DX & AX -> SP)
                SP = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_101_011_001: //AND DX BX SP (DX & BX -> SP)
                SP = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_101_011_010: //AND DX CX SP (DX & CX -> SP)
                SP = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_101_011_011: //AND DX DX SP (DX & DX -> SP)
                SP = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_101_011_100: //AND DX EX SP (DX & EX -> SP)
                SP = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_101_011_101: //AND DX SP SP (DX & SP -> SP)
                SP = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_101_011_110: //AND DX RP SP (DX & RP -> SP)
                SP = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_101_011_111: //AND DX PC SP (DX & PC -> SP)
                SP = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_110_011_000: //AND DX AX RP (DX & AX -> RP)
                RP = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_110_011_001: //AND DX BX RP (DX & BX -> RP)
                RP = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_110_011_010: //AND DX CX RP (DX & CX -> RP)
                RP = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_110_011_011: //AND DX DX RP (DX & DX -> RP)
                RP = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_110_011_100: //AND DX EX RP (DX & EX -> RP)
                RP = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_110_011_101: //AND DX SP RP (DX & SP -> RP)
                RP = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_110_011_110: //AND DX RP RP (DX & RP -> RP)
                RP = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_110_011_111: //AND DX PC RP (DX & PC -> RP)
                RP = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_111_011_000: //AND DX AX PC (DX & AX -> PC)
                PC = aluAnd(DX, AX);
                break;
            case 0b0001_1_01_111_011_001: //AND DX BX PC (DX & BX -> PC)
                PC = aluAnd(DX, BX);
                break;
            case 0b0001_1_01_111_011_010: //AND DX CX PC (DX & CX -> PC)
                PC = aluAnd(DX, CX);
                break;
            case 0b0001_1_01_111_011_011: //AND DX DX PC (DX & DX -> PC)
                PC = aluAnd(DX, DX);
                break;
            case 0b0001_1_01_111_011_100: //AND DX EX PC (DX & EX -> PC)
                PC = aluAnd(DX, EX);
                break;
            case 0b0001_1_01_111_011_101: //AND DX SP PC (DX & SP -> PC)
                PC = aluAnd(DX, SP);
                break;
            case 0b0001_1_01_111_011_110: //AND DX RP PC (DX & RP -> PC)
                PC = aluAnd(DX, RP);
                break;
            case 0b0001_1_01_111_011_111: //AND DX PC PC (DX & PC -> PC)
                PC = aluAnd(DX, PC);
                break;
            case 0b0001_1_01_000_100_000: //AND EX AX AX (EX & AX -> AX)
                AX = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_000_100_001: //AND EX BX AX (EX & BX -> AX)
                AX = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_000_100_010: //AND EX CX AX (EX & CX -> AX)
                AX = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_000_100_011: //AND EX DX AX (EX & DX -> AX)
                AX = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_000_100_100: //AND EX EX AX (EX & EX -> AX)
                AX = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_000_100_101: //AND EX SP AX (EX & SP -> AX)
                AX = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_000_100_110: //AND EX RP AX (EX & RP -> AX)
                AX = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_000_100_111: //AND EX PC AX (EX & PC -> AX)
                AX = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_001_100_000: //AND EX AX BX (EX & AX -> BX)
                BX = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_001_100_001: //AND EX BX BX (EX & BX -> BX)
                BX = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_001_100_010: //AND EX CX BX (EX & CX -> BX)
                BX = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_001_100_011: //AND EX DX BX (EX & DX -> BX)
                BX = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_001_100_100: //AND EX EX BX (EX & EX -> BX)
                BX = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_001_100_101: //AND EX SP BX (EX & SP -> BX)
                BX = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_001_100_110: //AND EX RP BX (EX & RP -> BX)
                BX = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_001_100_111: //AND EX PC BX (EX & PC -> BX)
                BX = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_010_100_000: //AND EX AX CX (EX & AX -> CX)
                CX = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_010_100_001: //AND EX BX CX (EX & BX -> CX)
                CX = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_010_100_010: //AND EX CX CX (EX & CX -> CX)
                CX = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_010_100_011: //AND EX DX CX (EX & DX -> CX)
                CX = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_010_100_100: //AND EX EX CX (EX & EX -> CX)
                CX = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_010_100_101: //AND EX SP CX (EX & SP -> CX)
                CX = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_010_100_110: //AND EX RP CX (EX & RP -> CX)
                CX = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_010_100_111: //AND EX PC CX (EX & PC -> CX)
                CX = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_011_100_000: //AND EX AX DX (EX & AX -> DX)
                DX = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_011_100_001: //AND EX BX DX (EX & BX -> DX)
                DX = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_011_100_010: //AND EX CX DX (EX & CX -> DX)
                DX = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_011_100_011: //AND EX DX DX (EX & DX -> DX)
                DX = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_011_100_100: //AND EX EX DX (EX & EX -> DX)
                DX = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_011_100_101: //AND EX SP DX (EX & SP -> DX)
                DX = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_011_100_110: //AND EX RP DX (EX & RP -> DX)
                DX = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_011_100_111: //AND EX PC DX (EX & PC -> DX)
                DX = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_100_100_000: //AND EX AX EX (EX & AX -> EX)
                EX = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_100_100_001: //AND EX EX SP (EX & BX -> SP)
                EX = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_100_100_010: //AND EX EX SP (EX & CX -> SP)
                EX = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_100_100_011: //AND EX EX SP (EX & DX -> SP)
                EX = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_100_100_100: //AND EX EX SP (EX & EX -> SP)
                EX = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_100_100_101: //AND EX EX SP (EX & SP -> SP)
                EX = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_100_100_110: //AND EX EX SP (EX & RP -> SP)
                EX = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_100_100_111: //AND EX EX SP (EX & PC -> SP)
                EX = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_101_100_000: //AND EX AX SP (EX & AX -> SP)
                SP = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_101_100_001: //AND EX BX SP (EX & BX -> SP)
                SP = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_101_100_010: //AND EX CX SP (EX & CX -> SP)
                SP = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_101_100_011: //AND EX DX SP (EX & DX -> SP)
                SP = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_101_100_100: //AND EX EX SP (EX & EX -> SP)
                SP = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_101_100_101: //AND EX SP SP (EX & SP -> SP)
                SP = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_101_100_110: //AND EX RP SP (EX & RP -> SP)
                SP = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_101_100_111: //AND EX PC SP (EX & PC -> SP)
                SP = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_110_100_000: //AND EX AX RP (EX & AX -> RP)
                RP = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_110_100_001: //AND EX BX RP (EX & BX -> RP)
                RP = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_110_100_010: //AND EX CX RP (EX & CX -> RP)
                RP = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_110_100_011: //AND EX DX RP (EX & DX -> RP)
                RP = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_110_100_100: //AND EX EX RP (EX & EX -> RP)
                RP = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_110_100_101: //AND EX SP RP (EX & SP -> RP)
                RP = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_110_100_110: //AND EX RP RP (EX & RP -> RP)
                RP = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_110_100_111: //AND EX PC RP (EX & PC -> RP)
                RP = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_111_100_000: //AND EX AX PC (EX & AX -> PC)
                PC = aluAnd(EX, AX);
                break;
            case 0b0001_1_01_111_100_001: //AND EX BX PC (EX & BX -> PC)
                PC = aluAnd(EX, BX);
                break;
            case 0b0001_1_01_111_100_010: //AND EX CX PC (EX & CX -> PC)
                PC = aluAnd(EX, CX);
                break;
            case 0b0001_1_01_111_100_011: //AND EX DX PC (EX & DX -> PC)
                PC = aluAnd(EX, DX);
                break;
            case 0b0001_1_01_111_100_100: //AND EX EX PC (EX & EX -> PC)
                PC = aluAnd(EX, EX);
                break;
            case 0b0001_1_01_111_100_101: //AND EX SP PC (EX & SP -> PC)
                PC = aluAnd(EX, SP);
                break;
            case 0b0001_1_01_111_100_110: //AND EX RP PC (EX & RP -> PC)
                PC = aluAnd(EX, RP);
                break;
            case 0b0001_1_01_111_100_111: //AND EX PC PC (EX & PC -> PC)
                PC = aluAnd(EX, PC);
                break;
            case 0b0001_1_01_000_101_000: //AND SP AX AX (SP & AX -> AX)
                AX = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_000_101_001: //AND SP BX AX (SP & BX -> AX)
                AX = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_000_101_010: //AND SP CX AX (SP & CX -> AX)
                AX = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_000_101_011: //AND SP DX AX (SP & DX -> AX)
                AX = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_000_101_100: //AND SP EX AX (SP & EX -> AX)
                AX = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_000_101_101: //AND SP SP AX (SP & SP -> AX)
                AX = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_000_101_110: //AND SP RP AX (SP & RP -> AX)
                AX = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_000_101_111: //AND SP PC AX (SP & PC -> AX)
                AX = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_001_101_000: //AND SP AX BX (SP & AX -> BX)
                BX = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_001_101_001: //AND SP BX BX (SP & BX -> BX)
                BX = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_001_101_010: //AND SP CX BX (SP & CX -> BX)
                BX = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_001_101_011: //AND SP DX BX (SP & DX -> BX)
                BX = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_001_101_100: //AND SP EX BX (SP & EX -> BX)
                BX = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_001_101_101: //AND SP SP BX (SP & SP -> BX)
                BX = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_001_101_110: //AND SP RP BX (SP & RP -> BX)
                BX = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_001_101_111: //AND SP PC BX (SP & PC -> BX)
                BX = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_010_101_000: //AND SP AX CX (SP & AX -> CX)
                CX = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_010_101_001: //AND SP BX CX (SP & BX -> CX)
                CX = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_010_101_010: //AND SP CX CX (SP & CX -> CX)
                CX = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_010_101_011: //AND SP DX CX (SP & DX -> CX)
                CX = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_010_101_100: //AND SP EX CX (SP & EX -> CX)
                CX = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_010_101_101: //AND SP SP CX (SP & SP -> CX)
                CX = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_010_101_110: //AND SP RP CX (SP & RP -> CX)
                CX = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_010_101_111: //AND SP PC CX (SP & PC -> CX)
                CX = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_011_101_000: //AND SP AX DX (SP & AX -> DX)
                DX = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_011_101_001: //AND SP BX DX (SP & BX -> DX)
                DX = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_011_101_010: //AND SP CX DX (SP & CX -> DX)
                DX = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_011_101_011: //AND SP DX DX (SP & DX -> DX)
                DX = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_011_101_100: //AND SP EX DX (SP & EX -> DX)
                DX = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_011_101_101: //AND SP SP DX (SP & SP -> DX)
                DX = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_011_101_110: //AND SP RP DX (SP & RP -> DX)
                DX = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_011_101_111: //AND SP PC DX (SP & PC -> DX)
                DX = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_100_101_000: //AND SP AX EX (SP & AX -> EX)
                EX = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_100_101_001: //AND SP EX SP (SP & BX -> SP)
                EX = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_100_101_010: //AND SP EX SP (SP & CX -> SP)
                EX = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_100_101_011: //AND SP EX SP (SP & DX -> SP)
                EX = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_100_101_100: //AND SP EX SP (SP & EX -> SP)
                EX = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_100_101_101: //AND SP EX SP (SP & SP -> SP)
                EX = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_100_101_110: //AND SP EX SP (SP & RP -> SP)
                EX = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_100_101_111: //AND SP EX SP (SP & PC -> SP)
                EX = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_101_101_000: //AND SP AX SP (SP & AX -> SP)
                SP = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_101_101_001: //AND SP BX SP (SP & BX -> SP)
                SP = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_101_101_010: //AND SP CX SP (SP & CX -> SP)
                SP = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_101_101_011: //AND SP DX SP (SP & DX -> SP)
                SP = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_101_101_100: //AND SP EX SP (SP & EX -> SP)
                SP = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_101_101_101: //AND SP SP SP (SP & SP -> SP)
                SP = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_101_101_110: //AND SP RP SP (SP & RP -> SP)
                SP = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_101_101_111: //AND SP PC SP (SP & PC -> SP)
                SP = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_110_101_000: //AND SP AX RP (SP & AX -> RP)
                RP = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_110_101_001: //AND SP BX RP (SP & BX -> RP)
                RP = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_110_101_010: //AND SP CX RP (SP & CX -> RP)
                RP = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_110_101_011: //AND SP DX RP (SP & DX -> RP)
                RP = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_110_101_100: //AND SP EX RP (SP & EX -> RP)
                RP = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_110_101_101: //AND SP SP RP (SP & SP -> RP)
                RP = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_110_101_110: //AND SP RP RP (SP & RP -> RP)
                RP = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_110_101_111: //AND SP PC RP (SP & PC -> RP)
                RP = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_111_101_000: //AND SP AX PC (SP & AX -> PC)
                PC = aluAnd(SP, AX);
                break;
            case 0b0001_1_01_111_101_001: //AND SP BX PC (SP & BX -> PC)
                PC = aluAnd(SP, BX);
                break;
            case 0b0001_1_01_111_101_010: //AND SP CX PC (SP & CX -> PC)
                PC = aluAnd(SP, CX);
                break;
            case 0b0001_1_01_111_101_011: //AND SP DX PC (SP & DX -> PC)
                PC = aluAnd(SP, DX);
                break;
            case 0b0001_1_01_111_101_100: //AND SP EX PC (SP & EX -> PC)
                PC = aluAnd(SP, EX);
                break;
            case 0b0001_1_01_111_101_101: //AND SP SP PC (SP & SP -> PC)
                PC = aluAnd(SP, SP);
                break;
            case 0b0001_1_01_111_101_110: //AND SP RP PC (SP & RP -> PC)
                PC = aluAnd(SP, RP);
                break;
            case 0b0001_1_01_111_101_111: //AND SP PC PC (SP & PC -> PC)
                PC = aluAnd(SP, PC);
                break;
            case 0b0001_1_01_000_110_000: //AND RP AX AX (RP & AX -> AX)
                AX = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_000_110_001: //AND RP BX AX (RP & BX -> AX)
                AX = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_000_110_010: //AND RP CX AX (RP & CX -> AX)
                AX = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_000_110_011: //AND RP DX AX (RP & DX -> AX)
                AX = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_000_110_100: //AND RP EX AX (RP & EX -> AX)
                AX = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_000_110_101: //AND RP SP AX (RP & SP -> AX)
                AX = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_000_110_110: //AND RP RP AX (RP & RP -> AX)
                AX = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_000_110_111: //AND RP PC AX (RP & PC -> AX)
                AX = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_001_110_000: //AND RP AX BX (RP & AX -> BX)
                BX = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_001_110_001: //AND RP BX BX (RP & BX -> BX)
                BX = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_001_110_010: //AND RP CX BX (RP & CX -> BX)
                BX = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_001_110_011: //AND RP DX BX (RP & DX -> BX)
                BX = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_001_110_100: //AND RP EX BX (RP & EX -> BX)
                BX = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_001_110_101: //AND RP SP BX (RP & SP -> BX)
                BX = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_001_110_110: //AND RP RP BX (RP & RP -> BX)
                BX = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_001_110_111: //AND RP PC BX (RP & PC -> BX)
                BX = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_010_110_000: //AND RP AX CX (RP & AX -> CX)
                CX = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_010_110_001: //AND RP BX CX (RP & BX -> CX)
                CX = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_010_110_010: //AND RP CX CX (RP & CX -> CX)
                CX = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_010_110_011: //AND RP DX CX (RP & DX -> CX)
                CX = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_010_110_100: //AND RP EX CX (RP & EX -> CX)
                CX = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_010_110_101: //AND RP SP CX (RP & SP -> CX)
                CX = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_010_110_110: //AND RP RP CX (RP & RP -> CX)
                CX = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_010_110_111: //AND RP PC CX (RP & PC -> CX)
                CX = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_011_110_000: //AND RP AX DX (RP & AX -> DX)
                DX = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_011_110_001: //AND RP BX DX (RP & BX -> DX)
                DX = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_011_110_010: //AND RP CX DX (RP & CX -> DX)
                DX = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_011_110_011: //AND RP DX DX (RP & DX -> DX)
                DX = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_011_110_100: //AND RP EX DX (RP & EX -> DX)
                DX = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_011_110_101: //AND RP SP DX (RP & SP -> DX)
                DX = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_011_110_110: //AND RP RP DX (RP & RP -> DX)
                DX = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_011_110_111: //AND RP PC DX (RP & PC -> DX)
                DX = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_100_110_000: //AND RP AX EX (RP & AX -> EX)
                EX = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_100_110_001: //AND RP EX SP (RP & BX -> SP)
                EX = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_100_110_010: //AND RP EX SP (RP & CX -> SP)
                EX = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_100_110_011: //AND RP EX SP (RP & DX -> SP)
                EX = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_100_110_100: //AND RP EX SP (RP & EX -> SP)
                EX = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_100_110_101: //AND RP EX SP (RP & SP -> SP)
                EX = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_100_110_110: //AND RP EX SP (RP & RP -> SP)
                EX = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_100_110_111: //AND RP EX SP (RP & PC -> SP)
                EX = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_101_110_000: //AND RP AX SP (RP & AX -> SP)
                SP = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_101_110_001: //AND RP BX SP (RP & BX -> SP)
                SP = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_101_110_010: //AND RP CX SP (RP & CX -> SP)
                SP = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_101_110_011: //AND RP DX SP (RP & DX -> SP)
                SP = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_101_110_100: //AND RP EX SP (RP & EX -> SP)
                SP = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_101_110_101: //AND RP SP SP (RP & SP -> SP)
                SP = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_101_110_110: //AND RP RP SP (RP & RP -> SP)
                SP = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_101_110_111: //AND RP PC SP (RP & PC -> SP)
                SP = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_110_110_000: //AND RP AX RP (RP & AX -> RP)
                RP = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_110_110_001: //AND RP BX RP (RP & BX -> RP)
                RP = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_110_110_010: //AND RP CX RP (RP & CX -> RP)
                RP = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_110_110_011: //AND RP DX RP (RP & DX -> RP)
                RP = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_110_110_100: //AND RP EX RP (RP & EX -> RP)
                RP = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_110_110_101: //AND RP SP RP (RP & SP -> RP)
                RP = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_110_110_110: //AND RP RP RP (RP & RP -> RP)
                RP = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_110_110_111: //AND RP PC RP (RP & PC -> RP)
                RP = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_111_110_000: //AND RP AX PC (RP & AX -> PC)
                PC = aluAnd(RP, AX);
                break;
            case 0b0001_1_01_111_110_001: //AND RP BX PC (RP & BX -> PC)
                PC = aluAnd(RP, BX);
                break;
            case 0b0001_1_01_111_110_010: //AND RP CX PC (RP & CX -> PC)
                PC = aluAnd(RP, CX);
                break;
            case 0b0001_1_01_111_110_011: //AND RP DX PC (RP & DX -> PC)
                PC = aluAnd(RP, DX);
                break;
            case 0b0001_1_01_111_110_100: //AND RP EX PC (RP & EX -> PC)
                PC = aluAnd(RP, EX);
                break;
            case 0b0001_1_01_111_110_101: //AND RP SP PC (RP & SP -> PC)
                PC = aluAnd(RP, SP);
                break;
            case 0b0001_1_01_111_110_110: //AND RP RP PC (RP & RP -> PC)
                PC = aluAnd(RP, RP);
                break;
            case 0b0001_1_01_111_110_111: //AND RP PC PC (RP & PC -> PC)
                PC = aluAnd(RP, PC);
                break;
            case 0b0001_1_01_000_111_000: //AND PC AX AX (PC & AX -> AX)
                AX = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_000_111_001: //AND PC BX AX (PC & BX -> AX)
                AX = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_000_111_010: //AND PC CX AX (PC & CX -> AX)
                AX = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_000_111_011: //AND PC DX AX (PC & DX -> AX)
                AX = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_000_111_100: //AND PC EX AX (PC & EX -> AX)
                AX = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_000_111_101: //AND PC SP AX (PC & SP -> AX)
                AX = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_000_111_110: //AND PC RP AX (PC & RP -> AX)
                AX = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_000_111_111: //AND PC PC AX (PC & PC -> AX)
                AX = aluAnd(PC, PC);
                break;
            case 0b0001_1_01_001_111_000: //AND PC AX BX (PC & AX -> BX)
                BX = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_001_111_001: //AND PC BX BX (PC & BX -> BX)
                BX = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_001_111_010: //AND PC CX BX (PC & CX -> BX)
                BX = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_001_111_011: //AND PC DX BX (PC & DX -> BX)
                BX = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_001_111_100: //AND PC EX BX (PC & EX -> BX)
                BX = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_001_111_101: //AND PC SP BX (PC & SP -> BX)
                BX = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_001_111_110: //AND PC RP BX (PC & RP -> BX)
                BX = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_001_111_111: //AND PC PC BX (PC & PC -> BX)
                BX = aluAnd(PC, PC);
                break;
            case 0b0001_1_01_010_111_000: //AND PC AX CX (PC & AX -> CX)
                CX = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_010_111_001: //AND PC BX CX (PC & BX -> CX)
                CX = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_010_111_010: //AND PC CX CX (PC & CX -> CX)
                CX = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_010_111_011: //AND PC DX CX (PC & DX -> CX)
                CX = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_010_111_100: //AND PC EX CX (PC & EX -> CX)
                CX = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_010_111_101: //AND PC SP CX (PC & SP -> CX)
                CX = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_010_111_110: //AND PC RP CX (PC & RP -> CX)
                CX = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_010_111_111: //AND PC PC CX (PC & PC -> CX)
                CX = aluAnd(PC, PC);
                break;
            case 0b0001_1_01_011_111_000: //AND PC AX DX (PC & AX -> DX)
                DX = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_011_111_001: //AND PC BX DX (PC & BX -> DX)
                DX = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_011_111_010: //AND PC CX DX (PC & CX -> DX)
                DX = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_011_111_011: //AND PC DX DX (PC & DX -> DX)
                DX = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_011_111_100: //AND PC EX DX (PC & EX -> DX)
                DX = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_011_111_101: //AND PC SP DX (PC & SP -> DX)
                DX = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_011_111_110: //AND PC RP DX (PC & RP -> DX)
                DX = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_011_111_111: //AND PC PC DX (PC & PC -> DX)
                DX = aluAnd(PC, PC);
                break;
            case 0b0001_1_01_100_111_000: //AND PC AX EX (PC & AX -> EX)
                EX = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_100_111_001: //AND PC EX SP (PC & BX -> SP)
                EX = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_100_111_010: //AND PC EX SP (PC & CX -> SP)
                EX = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_100_111_011: //AND PC EX SP (PC & DX -> SP)
                EX = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_100_111_100: //AND PC EX SP (PC & EX -> SP)
                EX = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_100_111_101: //AND PC EX SP (PC & SP -> SP)
                EX = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_100_111_110: //AND PC EX SP (PC & RP -> SP)
                EX = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_100_111_111: //AND PC EX SP (PC & PC -> SP)
                EX = aluAnd(PC, PC);
                break;
            case 0b0001_1_01_101_111_000: //AND PC AX SP (PC & AX -> SP)
                SP = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_101_111_001: //AND PC BX SP (PC & BX -> SP)
                SP = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_101_111_010: //AND PC CX SP (PC & CX -> SP)
                SP = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_101_111_011: //AND PC DX SP (PC & DX -> SP)
                SP = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_101_111_100: //AND PC EX SP (PC & EX -> SP)
                SP = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_101_111_101: //AND PC SP SP (PC & SP -> SP)
                SP = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_101_111_110: //AND PC RP SP (PC & RP -> SP)
                SP = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_101_111_111: //AND PC PC SP (PC & PC -> SP)
                SP = aluAnd(PC, PC);
                break;
            case 0b0001_1_01_110_111_000: //AND PC AX RP (PC & AX -> RP)
                RP = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_110_111_001: //AND PC BX RP (PC & BX -> RP)
                RP = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_110_111_010: //AND PC CX RP (PC & CX -> RP)
                RP = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_110_111_011: //AND PC DX RP (PC & DX -> RP)
                RP = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_110_111_100: //AND PC EX RP (PC & EX -> RP)
                RP = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_110_111_101: //AND PC SP RP (PC & SP -> RP)
                RP = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_110_111_110: //AND PC RP RP (PC & RP -> RP)
                RP = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_110_111_111: //AND PC PC RP (PC & PC -> RP)
                RP = aluAnd(PC, PC);
                break;
            case 0b0001_1_01_111_111_000: //AND PC AX PC (PC & AX -> PC)
                PC = aluAnd(PC, AX);
                break;
            case 0b0001_1_01_111_111_001: //AND PC BX PC (PC & BX -> PC)
                PC = aluAnd(PC, BX);
                break;
            case 0b0001_1_01_111_111_010: //AND PC CX PC (PC & CX -> PC)
                PC = aluAnd(PC, CX);
                break;
            case 0b0001_1_01_111_111_011: //AND PC DX PC (PC & DX -> PC)
                PC = aluAnd(PC, DX);
                break;
            case 0b0001_1_01_111_111_100: //AND PC EX PC (PC & EX -> PC)
                PC = aluAnd(PC, EX);
                break;
            case 0b0001_1_01_111_111_101: //AND PC SP PC (PC & SP -> PC)
                PC = aluAnd(PC, SP);
                break;
            case 0b0001_1_01_111_111_110: //AND PC RP PC (PC & RP -> PC)
                PC = aluAnd(PC, RP);
                break;
            case 0b0001_1_01_111_111_111: //AND PC PC PC (PC & PC -> PC)
                PC = aluAnd(PC, PC);
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }


    /*
     * OR: Bit-wise OR two registers together, storing into the destination register
     */
    private void executeORInstruction() {
        switch (INST) {

            case 0b0001_1_10_000_000_000: //OR AX AX AX (AX | AX -> AX)
                AX = aluOr(AX, AX);
                break;
            case 0b0001_1_10_000_000_001: //OR AX BX AX (AX | BX -> AX)
                AX = aluOr(AX, BX);
                break;
            case 0b0001_1_10_000_000_010: //OR AX CX AX (AX | CX -> AX)
                AX = aluOr(AX, CX);
                break;
            case 0b0001_1_10_000_000_011: //OR AX DX AX (AX | DX -> AX)
                AX = aluOr(AX, DX);
                break;
            case 0b0001_1_10_000_000_100: //OR AX EX AX (AX | EX -> AX)
                AX = aluOr(AX, EX);
                break;
            case 0b0001_1_10_000_000_101: //OR AX SP AX (AX | SP -> AX)
                AX = aluOr(AX, SP);
                break;
            case 0b0001_1_10_000_000_110: //OR AX RP AX (AX | RP -> AX)
                AX = aluOr(AX, RP);
                break;
            case 0b0001_1_10_000_000_111: //OR AX PC AX (AX | PC -> AX)
                AX = aluOr(AX, PC);
                break;
            case 0b0001_1_10_001_000_000: //OR AX AX BX (AX | AX -> BX)
                BX = aluOr(AX, AX);
                break;
            case 0b0001_1_10_001_000_001: //OR AX BX BX (AX | BX -> BX)
                BX = aluOr(AX, BX);
                break;
            case 0b0001_1_10_001_000_010: //OR AX CX BX (AX | CX -> BX)
                BX = aluOr(AX, CX);
                break;
            case 0b0001_1_10_001_000_011: //OR AX DX BX (AX | DX -> BX)
                BX = aluOr(AX, DX);
                break;
            case 0b0001_1_10_001_000_100: //OR AX EX BX (AX | EX -> BX)
                BX = aluOr(AX, EX);
                break;
            case 0b0001_1_10_001_000_101: //OR AX SP BX (AX | SP -> BX)
                BX = aluOr(AX, SP);
                break;
            case 0b0001_1_10_001_000_110: //OR AX RP BX (AX | RP -> BX)
                BX = aluOr(AX, RP);
                break;
            case 0b0001_1_10_001_000_111: //OR AX PC BX (AX | PC -> BX)
                BX = aluOr(AX, PC);
                break;
            case 0b0001_1_10_010_000_000: //OR AX AX CX (AX | AX -> CX)
                CX = aluOr(AX, AX);
                break;
            case 0b0001_1_10_010_000_001: //OR AX BX CX (AX | BX -> CX)
                CX = aluOr(AX, BX);
                break;
            case 0b0001_1_10_010_000_010: //OR AX CX CX (AX | CX -> CX)
                CX = aluOr(AX, CX);
                break;
            case 0b0001_1_10_010_000_011: //OR AX DX CX (AX | DX -> CX)
                CX = aluOr(AX, DX);
                break;
            case 0b0001_1_10_010_000_100: //OR AX EX CX (AX | EX -> CX)
                CX = aluOr(AX, EX);
                break;
            case 0b0001_1_10_010_000_101: //OR AX SP CX (AX | SP -> CX)
                CX = aluOr(AX, SP);
                break;
            case 0b0001_1_10_010_000_110: //OR AX RP CX (AX | RP -> CX)
                CX = aluOr(AX, RP);
                break;
            case 0b0001_1_10_010_000_111: //OR AX PC CX (AX | PC -> CX)
                CX = aluOr(AX, PC);
                break;
            case 0b0001_1_10_011_000_000: //OR AX AX DX (AX | AX -> DX)
                DX = aluOr(AX, AX);
                break;
            case 0b0001_1_10_011_000_001: //OR AX BX DX (AX | BX -> DX)
                DX = aluOr(AX, BX);
                break;
            case 0b0001_1_10_011_000_010: //OR AX CX DX (AX | CX -> DX)
                DX = aluOr(AX, CX);
                break;
            case 0b0001_1_10_011_000_011: //OR AX DX DX (AX | DX -> DX)
                DX = aluOr(AX, DX);
                break;
            case 0b0001_1_10_011_000_100: //OR AX EX DX (AX | EX -> DX)
                DX = aluOr(AX, EX);
                break;
            case 0b0001_1_10_011_000_101: //OR AX SP DX (AX | SP -> DX)
                DX = aluOr(AX, SP);
                break;
            case 0b0001_1_10_011_000_110: //OR AX RP DX (AX | RP -> DX)
                DX = aluOr(AX, RP);
                break;
            case 0b0001_1_10_011_000_111: //OR AX PC DX (AX | PC -> DX)
                DX = aluOr(AX, PC);
                break;
            case 0b0001_1_10_100_000_000: //OR AX AX EX (AX | AX -> EX)
                EX = aluOr(AX, AX);
                break;
            case 0b0001_1_10_100_000_001: //OR AX EX SP (AX | BX -EXSP)
                EX = aluOr(AX, BX);
                break;
            case 0b0001_1_10_100_000_010: //OR AX EX SP (AX | CX -EXSP)
                EX = aluOr(AX, CX);
                break;
            case 0b0001_1_10_100_000_011: //OR AX EX SP (AX | DX -EXSP)
                EX = aluOr(AX, DX);
                break;
            case 0b0001_1_10_100_000_100: //OR AX EX SP (AX | EX -EXSP)
                EX = aluOr(AX, EX);
                break;
            case 0b0001_1_10_100_000_101: //OR AX EX SP (AX | SP -EXSP)
                EX = aluOr(AX, SP);
                break;
            case 0b0001_1_10_100_000_110: //OR AX EX SP (AX | RP -EXSP)
                EX = aluOr(AX, RP);
                break;
            case 0b0001_1_10_100_000_111: //OR AX EX SP (AX | PC -EXSP)
                EX = aluOr(AX, PC);
                break;
            case 0b0001_1_10_101_000_000: //OR AX AX SP (AX | AX -> SP)
                SP = aluOr(AX, AX);
                break;
            case 0b0001_1_10_101_000_001: //OR AX BX SP (AX | BX -> SP)
                SP = aluOr(AX, BX);
                break;
            case 0b0001_1_10_101_000_010: //OR AX CX SP (AX | CX -> SP)
                SP = aluOr(AX, CX);
                break;
            case 0b0001_1_10_101_000_011: //OR AX DX SP (AX | DX -> SP)
                SP = aluOr(AX, DX);
                break;
            case 0b0001_1_10_101_000_100: //OR AX EX SP (AX | EX -> SP)
                SP = aluOr(AX, EX);
                break;
            case 0b0001_1_10_101_000_101: //OR AX SP SP (AX | SP -> SP)
                SP = aluOr(AX, SP);
                break;
            case 0b0001_1_10_101_000_110: //OR AX RP SP (AX | RP -> SP)
                SP = aluOr(AX, RP);
                break;
            case 0b0001_1_10_101_000_111: //OR AX PC SP (AX | PC -> SP)
                SP = aluOr(AX, PC);
                break;
            case 0b0001_1_10_110_000_000: //OR AX AX RP (AX | AX -> RP)
                RP = aluOr(AX, AX);
                break;
            case 0b0001_1_10_110_000_001: //OR AX BX RP (AX | BX -> RP)
                RP = aluOr(AX, BX);
                break;
            case 0b0001_1_10_110_000_010: //OR AX CX RP (AX | CX -> RP)
                RP = aluOr(AX, CX);
                break;
            case 0b0001_1_10_110_000_011: //OR AX DX RP (AX | DX -> RP)
                RP = aluOr(AX, DX);
                break;
            case 0b0001_1_10_110_000_100: //OR AX EX RP (AX | EX -> RP)
                RP = aluOr(AX, EX);
                break;
            case 0b0001_1_10_110_000_101: //OR AX SP RP (AX | SP -> RP)
                RP = aluOr(AX, SP);
                break;
            case 0b0001_1_10_110_000_110: //OR AX RP RP (AX | RP -> RP)
                RP = aluOr(AX, RP);
                break;
            case 0b0001_1_10_110_000_111: //OR AX PC RP (AX | PC -> RP)
                RP = aluOr(AX, PC);
                break;
            case 0b0001_1_10_111_000_000: //OR AX AX PC (AX | AX -> PC)
                PC = aluOr(AX, AX);
                break;
            case 0b0001_1_10_111_000_001: //OR AX BX PC (AX | BX -> PC)
                PC = aluOr(AX, BX);
                break;
            case 0b0001_1_10_111_000_010: //OR AX CX PC (AX | CX -> PC)
                PC = aluOr(AX, CX);
                break;
            case 0b0001_1_10_111_000_011: //OR AX DX PC (AX | DX -> PC)
                PC = aluOr(AX, DX);
                break;
            case 0b0001_1_10_111_000_100: //OR AX EX PC (AX | EX -> PC)
                PC = aluOr(AX, EX);
                break;
            case 0b0001_1_10_111_000_101: //OR AX SP PC (AX | SP -> PC)
                PC = aluOr(AX, SP);
                break;
            case 0b0001_1_10_111_000_110: //OR AX RP PC (AX | RP -> PC)
                PC = aluOr(AX, RP);
                break;
            case 0b0001_1_10_111_000_111: //OR AX PC PC (AX | PC -> PC)
                PC = aluOr(AX, PC);
                break;
            case 0b0001_1_10_000_001_000: //OR BX AX AX (BX | AX -> AX)
                AX = aluOr(BX, AX);
                break;
            case 0b0001_1_10_000_001_001: //OR BX BX AX (BX | BX -> AX)
                AX = aluOr(BX, BX);
                break;
            case 0b0001_1_10_000_001_010: //OR BX CX AX (BX | CX -> AX)
                AX = aluOr(BX, CX);
                break;
            case 0b0001_1_10_000_001_011: //OR BX DX AX (BX | DX -> AX)
                AX = aluOr(BX, DX);
                break;
            case 0b0001_1_10_000_001_100: //OR BX EX AX (BX | EX -> AX)
                AX = aluOr(BX, EX);
                break;
            case 0b0001_1_10_000_001_101: //OR BX SP AX (BX | SP -> AX)
                AX = aluOr(BX, SP);
                break;
            case 0b0001_1_10_000_001_110: //OR BX RP AX (BX | RP -> AX)
                AX = aluOr(BX, RP);
                break;
            case 0b0001_1_10_000_001_111: //OR BX PC AX (BX | PC -> AX)
                AX = aluOr(BX, PC);
                break;
            case 0b0001_1_10_001_001_000: //OR BX AX BX (BX | AX -> BX)
                BX = aluOr(BX, AX);
                break;
            case 0b0001_1_10_001_001_001: //OR BX BX BX (BX | BX -> BX)
                BX = aluOr(BX, BX);
                break;
            case 0b0001_1_10_001_001_010: //OR BX CX BX (BX | CX -> BX)
                BX = aluOr(BX, CX);
                break;
            case 0b0001_1_10_001_001_011: //OR BX DX BX (BX | DX -> BX)
                BX = aluOr(BX, DX);
                break;
            case 0b0001_1_10_001_001_100: //OR BX EX BX (BX | EX -> BX)
                BX = aluOr(BX, EX);
                break;
            case 0b0001_1_10_001_001_101: //OR BX SP BX (BX | SP -> BX)
                BX = aluOr(BX, SP);
                break;
            case 0b0001_1_10_001_001_110: //OR BX RP BX (BX | RP -> BX)
                BX = aluOr(BX, RP);
                break;
            case 0b0001_1_10_001_001_111: //OR BX PC BX (BX | PC -> BX)
                BX = aluOr(BX, PC);
                break;
            case 0b0001_1_10_010_001_000: //OR BX AX CX (BX | AX -> CX)
                CX = aluOr(BX, AX);
                break;
            case 0b0001_1_10_010_001_001: //OR BX BX CX (BX | BX -> CX)
                CX = aluOr(BX, BX);
                break;
            case 0b0001_1_10_010_001_010: //OR BX CX CX (BX | CX -> CX)
                CX = aluOr(BX, CX);
                break;
            case 0b0001_1_10_010_001_011: //OR BX DX CX (BX | DX -> CX)
                CX = aluOr(BX, DX);
                break;
            case 0b0001_1_10_010_001_100: //OR BX EX CX (BX | EX -> CX)
                CX = aluOr(BX, EX);
                break;
            case 0b0001_1_10_010_001_101: //OR BX SP CX (BX | SP -> CX)
                CX = aluOr(BX, SP);
                break;
            case 0b0001_1_10_010_001_110: //OR BX RP CX (BX | RP -> CX)
                CX = aluOr(BX, RP);
                break;
            case 0b0001_1_10_010_001_111: //OR BX PC CX (BX | PC -> CX)
                CX = aluOr(BX, PC);
                break;
            case 0b0001_1_10_011_001_000: //OR BX AX DX (BX | AX -> DX)
                DX = aluOr(BX, AX);
                break;
            case 0b0001_1_10_011_001_001: //OR BX BX DX (BX | BX -> DX)
                DX = aluOr(BX, BX);
                break;
            case 0b0001_1_10_011_001_010: //OR BX CX DX (BX | CX -> DX)
                DX = aluOr(BX, CX);
                break;
            case 0b0001_1_10_011_001_011: //OR BX DX DX (BX | DX -> DX)
                DX = aluOr(BX, DX);
                break;
            case 0b0001_1_10_011_001_100: //OR BX EX DX (BX | EX -> DX)
                DX = aluOr(BX, EX);
                break;
            case 0b0001_1_10_011_001_101: //OR BX SP DX (BX | SP -> DX)
                DX = aluOr(BX, SP);
                break;
            case 0b0001_1_10_011_001_110: //OR BX RP DX (BX | RP -> DX)
                DX = aluOr(BX, RP);
                break;
            case 0b0001_1_10_011_001_111: //OR BX PC DX (BX | PC -> DX)
                DX = aluOr(BX, PC);
                break;
            case 0b0001_1_10_100_001_000: //OR BX AX EX (BX | AX -> EX)
                EX = aluOr(BX, AX);
                break;
            case 0b0001_1_10_100_001_001: //OR BX EX SP (BX | BX -> SP)
                EX = aluOr(BX, BX);
                break;
            case 0b0001_1_10_100_001_010: //OR BX EX SP (BX | CX -> SP)
                EX = aluOr(BX, CX);
                break;
            case 0b0001_1_10_100_001_011: //OR BX EX SP (BX | DX -> SP)
                EX = aluOr(BX, DX);
                break;
            case 0b0001_1_10_100_001_100: //OR BX EX SP (BX | EX -> SP)
                EX = aluOr(BX, EX);
                break;
            case 0b0001_1_10_100_001_101: //OR BX EX SP (BX | SP -> SP)
                EX = aluOr(BX, SP);
                break;
            case 0b0001_1_10_100_001_110: //OR BX EX SP (BX | RP -> SP)
                EX = aluOr(BX, RP);
                break;
            case 0b0001_1_10_100_001_111: //OR BX EX SP (BX | PC -> SP)
                EX = aluOr(BX, PC);
                break;
            case 0b0001_1_10_101_001_000: //OR BX AX SP (BX | AX -> SP)
                SP = aluOr(BX, AX);
                break;
            case 0b0001_1_10_101_001_001: //OR BX BX SP (BX | BX -> SP)
                SP = aluOr(BX, BX);
                break;
            case 0b0001_1_10_101_001_010: //OR BX CX SP (BX | CX -> SP)
                SP = aluOr(BX, CX);
                break;
            case 0b0001_1_10_101_001_011: //OR BX DX SP (BX | DX -> SP)
                SP = aluOr(BX, DX);
                break;
            case 0b0001_1_10_101_001_100: //OR BX EX SP (BX | EX -> SP)
                SP = aluOr(BX, EX);
                break;
            case 0b0001_1_10_101_001_101: //OR BX SP SP (BX | SP -> SP)
                SP = aluOr(BX, SP);
                break;
            case 0b0001_1_10_101_001_110: //OR BX RP SP (BX | RP -> SP)
                SP = aluOr(BX, RP);
                break;
            case 0b0001_1_10_101_001_111: //OR BX PC SP (BX | PC -> SP)
                SP = aluOr(BX, PC);
                break;
            case 0b0001_1_10_110_001_000: //OR BX AX RP (BX | AX -> RP)
                RP = aluOr(BX, AX);
                break;
            case 0b0001_1_10_110_001_001: //OR BX BX RP (BX | BX -> RP)
                RP = aluOr(BX, BX);
                break;
            case 0b0001_1_10_110_001_010: //OR BX CX RP (BX | CX -> RP)
                RP = aluOr(BX, CX);
                break;
            case 0b0001_1_10_110_001_011: //OR BX DX RP (BX | DX -> RP)
                RP = aluOr(BX, DX);
                break;
            case 0b0001_1_10_110_001_100: //OR BX EX RP (BX | EX -> RP)
                RP = aluOr(BX, EX);
                break;
            case 0b0001_1_10_110_001_101: //OR BX SP RP (BX | SP -> RP)
                RP = aluOr(BX, SP);
                break;
            case 0b0001_1_10_110_001_110: //OR BX RP RP (BX | RP -> RP)
                RP = aluOr(BX, RP);
                break;
            case 0b0001_1_10_110_001_111: //OR BX PC RP (BX | PC -> RP)
                RP = aluOr(BX, PC);
                break;
            case 0b0001_1_10_111_001_000: //OR BX AX PC (BX | AX -> PC)
                PC = aluOr(BX, AX);
                break;
            case 0b0001_1_10_111_001_001: //OR BX BX PC (BX | BX -> PC)
                PC = aluOr(BX, BX);
                break;
            case 0b0001_1_10_111_001_010: //OR BX CX PC (BX | CX -> PC)
                PC = aluOr(BX, CX);
                break;
            case 0b0001_1_10_111_001_011: //OR BX DX PC (BX | DX -> PC)
                PC = aluOr(BX, DX);
                break;
            case 0b0001_1_10_111_001_100: //OR BX EX PC (BX | EX -> PC)
                PC = aluOr(BX, EX);
                break;
            case 0b0001_1_10_111_001_101: //OR BX SP PC (BX | SP -> PC)
                PC = aluOr(BX, SP);
                break;
            case 0b0001_1_10_111_001_110: //OR BX RP PC (BX | RP -> PC)
                PC = aluOr(BX, RP);
                break;
            case 0b0001_1_10_111_001_111: //OR BX PC PC (BX | PC -> PC)
                PC = aluOr(BX, PC);
                break;
            case 0b0001_1_10_000_010_000: //OR CX AX AX (CX | AX -> AX)
                AX = aluOr(CX, AX);
                break;
            case 0b0001_1_10_000_010_001: //OR CX BX AX (CX | BX -> AX)
                AX = aluOr(CX, BX);
                break;
            case 0b0001_1_10_000_010_010: //OR CX CX AX (CX | CX -> AX)
                AX = aluOr(CX, CX);
                break;
            case 0b0001_1_10_000_010_011: //OR CX DX AX (CX | DX -> AX)
                AX = aluOr(CX, DX);
                break;
            case 0b0001_1_10_000_010_100: //OR CX EX AX (CX | EX -> AX)
                AX = aluOr(CX, EX);
                break;
            case 0b0001_1_10_000_010_101: //OR CX SP AX (CX | SP -> AX)
                AX = aluOr(CX, SP);
                break;
            case 0b0001_1_10_000_010_110: //OR CX RP AX (CX | RP -> AX)
                AX = aluOr(CX, RP);
                break;
            case 0b0001_1_10_000_010_111: //OR CX PC AX (CX | PC -> AX)
                AX = aluOr(CX, PC);
                break;
            case 0b0001_1_10_001_010_000: //OR CX AX BX (CX | AX -> BX)
                BX = aluOr(CX, AX);
                break;
            case 0b0001_1_10_001_010_001: //OR CX BX BX (CX | BX -> BX)
                BX = aluOr(CX, BX);
                break;
            case 0b0001_1_10_001_010_010: //OR CX CX BX (CX | CX -> BX)
                BX = aluOr(CX, CX);
                break;
            case 0b0001_1_10_001_010_011: //OR CX DX BX (CX | DX -> BX)
                BX = aluOr(CX, DX);
                break;
            case 0b0001_1_10_001_010_100: //OR CX EX BX (CX | EX -> BX)
                BX = aluOr(CX, EX);
                break;
            case 0b0001_1_10_001_010_101: //OR CX SP BX (CX | SP -> BX)
                BX = aluOr(CX, SP);
                break;
            case 0b0001_1_10_001_010_110: //OR CX RP BX (CX | RP -> BX)
                BX = aluOr(CX, RP);
                break;
            case 0b0001_1_10_001_010_111: //OR CX PC BX (CX | PC -> BX)
                BX = aluOr(CX, PC);
                break;
            case 0b0001_1_10_010_010_000: //OR CX AX CX (CX | AX -> CX)
                CX = aluOr(CX, AX);
                break;
            case 0b0001_1_10_010_010_001: //OR CX BX CX (CX | BX -> CX)
                CX = aluOr(CX, BX);
                break;
            case 0b0001_1_10_010_010_010: //OR CX CX CX (CX | CX -> CX)
                CX = aluOr(CX, CX);
                break;
            case 0b0001_1_10_010_010_011: //OR CX DX CX (CX | DX -> CX)
                CX = aluOr(CX, DX);
                break;
            case 0b0001_1_10_010_010_100: //OR CX EX CX (CX | EX -> CX)
                CX = aluOr(CX, EX);
                break;
            case 0b0001_1_10_010_010_101: //OR CX SP CX (CX | SP -> CX)
                CX = aluOr(CX, SP);
                break;
            case 0b0001_1_10_010_010_110: //OR CX RP CX (CX | RP -> CX)
                CX = aluOr(CX, RP);
                break;
            case 0b0001_1_10_010_010_111: //OR CX PC CX (CX | PC -> CX)
                CX = aluOr(CX, PC);
                break;
            case 0b0001_1_10_011_010_000: //OR CX AX DX (CX | AX -> DX)
                DX = aluOr(CX, AX);
                break;
            case 0b0001_1_10_011_010_001: //OR CX BX DX (CX | BX -> DX)
                DX = aluOr(CX, BX);
                break;
            case 0b0001_1_10_011_010_010: //OR CX CX DX (CX | CX -> DX)
                DX = aluOr(CX, CX);
                break;
            case 0b0001_1_10_011_010_011: //OR CX DX DX (CX | DX -> DX)
                DX = aluOr(CX, DX);
                break;
            case 0b0001_1_10_011_010_100: //OR CX EX DX (CX | EX -> DX)
                DX = aluOr(CX, EX);
                break;
            case 0b0001_1_10_011_010_101: //OR CX SP DX (CX | SP -> DX)
                DX = aluOr(CX, SP);
                break;
            case 0b0001_1_10_011_010_110: //OR CX RP DX (CX | RP -> DX)
                DX = aluOr(CX, RP);
                break;
            case 0b0001_1_10_011_010_111: //OR CX PC DX (CX | PC -> DX)
                DX = aluOr(CX, PC);
                break;
            case 0b0001_1_10_100_010_000: //OR CX AX EX (CX | AX -> EX)
                EX = aluOr(CX, AX);
                break;
            case 0b0001_1_10_100_010_001: //OR CX EX SP (CX | BX -> SP)
                EX = aluOr(CX, BX);
                break;
            case 0b0001_1_10_100_010_010: //OR CX EX SP (CX | CX -> SP)
                EX = aluOr(CX, CX);
                break;
            case 0b0001_1_10_100_010_011: //OR CX EX SP (CX | DX -> SP)
                EX = aluOr(CX, DX);
                break;
            case 0b0001_1_10_100_010_100: //OR CX EX SP (CX | EX -> SP)
                EX = aluOr(CX, EX);
                break;
            case 0b0001_1_10_100_010_101: //OR CX EX SP (CX | SP -> SP)
                EX = aluOr(CX, SP);
                break;
            case 0b0001_1_10_100_010_110: //OR CX EX SP (CX | RP -> SP)
                EX = aluOr(CX, RP);
                break;
            case 0b0001_1_10_100_010_111: //OR CX EX SP (CX | PC -> SP)
                EX = aluOr(CX, PC);
                break;
            case 0b0001_1_10_101_010_000: //OR CX AX SP (CX | AX -> SP)
                SP = aluOr(CX, AX);
                break;
            case 0b0001_1_10_101_010_001: //OR CX BX SP (CX | BX -> SP)
                SP = aluOr(CX, BX);
                break;
            case 0b0001_1_10_101_010_010: //OR CX CX SP (CX | CX -> SP)
                SP = aluOr(CX, CX);
                break;
            case 0b0001_1_10_101_010_011: //OR CX DX SP (CX | DX -> SP)
                SP = aluOr(CX, DX);
                break;
            case 0b0001_1_10_101_010_100: //OR CX EX SP (CX | EX -> SP)
                SP = aluOr(CX, EX);
                break;
            case 0b0001_1_10_101_010_101: //OR CX SP SP (CX | SP -> SP)
                SP = aluOr(CX, SP);
                break;
            case 0b0001_1_10_101_010_110: //OR CX RP SP (CX | RP -> SP)
                SP = aluOr(CX, RP);
                break;
            case 0b0001_1_10_101_010_111: //OR CX PC SP (CX | PC -> SP)
                SP = aluOr(CX, PC);
                break;
            case 0b0001_1_10_110_010_000: //OR CX AX RP (CX | AX -> RP)
                RP = aluOr(CX, AX);
                break;
            case 0b0001_1_10_110_010_001: //OR CX BX RP (CX | BX -> RP)
                RP = aluOr(CX, BX);
                break;
            case 0b0001_1_10_110_010_010: //OR CX CX RP (CX | CX -> RP)
                RP = aluOr(CX, CX);
                break;
            case 0b0001_1_10_110_010_011: //OR CX DX RP (CX | DX -> RP)
                RP = aluOr(CX, DX);
                break;
            case 0b0001_1_10_110_010_100: //OR CX EX RP (CX | EX -> RP)
                RP = aluOr(CX, EX);
                break;
            case 0b0001_1_10_110_010_101: //OR CX SP RP (CX | SP -> RP)
                RP = aluOr(CX, SP);
                break;
            case 0b0001_1_10_110_010_110: //OR CX RP RP (CX | RP -> RP)
                RP = aluOr(CX, RP);
                break;
            case 0b0001_1_10_110_010_111: //OR CX PC RP (CX | PC -> RP)
                RP = aluOr(CX, PC);
                break;
            case 0b0001_1_10_111_010_000: //OR CX AX PC (CX | AX -> PC)
                PC = aluOr(CX, AX);
                break;
            case 0b0001_1_10_111_010_001: //OR CX BX PC (CX | BX -> PC)
                PC = aluOr(CX, BX);
                break;
            case 0b0001_1_10_111_010_010: //OR CX CX PC (CX | CX -> PC)
                PC = aluOr(CX, CX);
                break;
            case 0b0001_1_10_111_010_011: //OR CX DX PC (CX | DX -> PC)
                PC = aluOr(CX, DX);
                break;
            case 0b0001_1_10_111_010_100: //OR CX EX PC (CX | EX -> PC)
                PC = aluOr(CX, EX);
                break;
            case 0b0001_1_10_111_010_101: //OR CX SP PC (CX | SP -> PC)
                PC = aluOr(CX, SP);
                break;
            case 0b0001_1_10_111_010_110: //OR CX RP PC (CX | RP -> PC)
                PC = aluOr(CX, RP);
                break;
            case 0b0001_1_10_111_010_111: //OR CX PC PC (CX | PC -> PC)
                PC = aluOr(CX, PC);
                break;
            case 0b0001_1_10_000_011_000: //OR DX AX AX (DX | AX -> AX)
                AX = aluOr(DX, AX);
                break;
            case 0b0001_1_10_000_011_001: //OR DX BX AX (DX | BX -> AX)
                AX = aluOr(DX, BX);
                break;
            case 0b0001_1_10_000_011_010: //OR DX CX AX (DX | CX -> AX)
                AX = aluOr(DX, CX);
                break;
            case 0b0001_1_10_000_011_011: //OR DX DX AX (DX | DX -> AX)
                AX = aluOr(DX, DX);
                break;
            case 0b0001_1_10_000_011_100: //OR DX EX AX (DX | EX -> AX)
                AX = aluOr(DX, EX);
                break;
            case 0b0001_1_10_000_011_101: //OR DX SP AX (DX | SP -> AX)
                AX = aluOr(DX, SP);
                break;
            case 0b0001_1_10_000_011_110: //OR DX RP AX (DX | RP -> AX)
                AX = aluOr(DX, RP);
                break;
            case 0b0001_1_10_000_011_111: //OR DX PC AX (DX | PC -> AX)
                AX = aluOr(DX, PC);
                break;
            case 0b0001_1_10_001_011_000: //OR DX AX BX (DX | AX -> BX)
                BX = aluOr(DX, AX);
                break;
            case 0b0001_1_10_001_011_001: //OR DX BX BX (DX | BX -> BX)
                BX = aluOr(DX, BX);
                break;
            case 0b0001_1_10_001_011_010: //OR DX CX BX (DX | CX -> BX)
                BX = aluOr(DX, CX);
                break;
            case 0b0001_1_10_001_011_011: //OR DX DX BX (DX | DX -> BX)
                BX = aluOr(DX, DX);
                break;
            case 0b0001_1_10_001_011_100: //OR DX EX BX (DX | EX -> BX)
                BX = aluOr(DX, EX);
                break;
            case 0b0001_1_10_001_011_101: //OR DX SP BX (DX | SP -> BX)
                BX = aluOr(DX, SP);
                break;
            case 0b0001_1_10_001_011_110: //OR DX RP BX (DX | RP -> BX)
                BX = aluOr(DX, RP);
                break;
            case 0b0001_1_10_001_011_111: //OR DX PC BX (DX | PC -> BX)
                BX = aluOr(DX, PC);
                break;
            case 0b0001_1_10_010_011_000: //OR DX AX CX (DX | AX -> CX)
                CX = aluOr(DX, AX);
                break;
            case 0b0001_1_10_010_011_001: //OR DX BX CX (DX | BX -> CX)
                CX = aluOr(DX, BX);
                break;
            case 0b0001_1_10_010_011_010: //OR DX CX CX (DX | CX -> CX)
                CX = aluOr(DX, CX);
                break;
            case 0b0001_1_10_010_011_011: //OR DX DX CX (DX | DX -> CX)
                CX = aluOr(DX, DX);
                break;
            case 0b0001_1_10_010_011_100: //OR DX EX CX (DX | EX -> CX)
                CX = aluOr(DX, EX);
                break;
            case 0b0001_1_10_010_011_101: //OR DX SP CX (DX | SP -> CX)
                CX = aluOr(DX, SP);
                break;
            case 0b0001_1_10_010_011_110: //OR DX RP CX (DX | RP -> CX)
                CX = aluOr(DX, RP);
                break;
            case 0b0001_1_10_010_011_111: //OR DX PC CX (DX | PC -> CX)
                CX = aluOr(DX, PC);
                break;
            case 0b0001_1_10_011_011_000: //OR DX AX DX (DX | AX -> DX)
                DX = aluOr(DX, AX);
                break;
            case 0b0001_1_10_011_011_001: //OR DX BX DX (DX | BX -> DX)
                DX = aluOr(DX, BX);
                break;
            case 0b0001_1_10_011_011_010: //OR DX CX DX (DX | CX -> DX)
                DX = aluOr(DX, CX);
                break;
            case 0b0001_1_10_011_011_011: //OR DX DX DX (DX | DX -> DX)
                DX = aluOr(DX, DX);
                break;
            case 0b0001_1_10_011_011_100: //OR DX EX DX (DX | EX -> DX)
                DX = aluOr(DX, EX);
                break;
            case 0b0001_1_10_011_011_101: //OR DX SP DX (DX | SP -> DX)
                DX = aluOr(DX, SP);
                break;
            case 0b0001_1_10_011_011_110: //OR DX RP DX (DX | RP -> DX)
                DX = aluOr(DX, RP);
                break;
            case 0b0001_1_10_011_011_111: //OR DX PC DX (DX | PC -> DX)
                DX = aluOr(DX, PC);
                break;
            case 0b0001_1_10_100_011_000: //OR DX AX EX (DX | AX -> EX)
                EX = aluOr(DX, AX);
                break;
            case 0b0001_1_10_100_011_001: //OR DX EX SP (DX | BX -> SP)
                EX = aluOr(DX, BX);
                break;
            case 0b0001_1_10_100_011_010: //OR DX EX SP (DX | CX -> SP)
                EX = aluOr(DX, CX);
                break;
            case 0b0001_1_10_100_011_011: //OR DX EX SP (DX | DX -> SP)
                EX = aluOr(DX, DX);
                break;
            case 0b0001_1_10_100_011_100: //OR DX EX SP (DX | EX -> SP)
                EX = aluOr(DX, EX);
                break;
            case 0b0001_1_10_100_011_101: //OR DX EX SP (DX | SP -> SP)
                EX = aluOr(DX, SP);
                break;
            case 0b0001_1_10_100_011_110: //OR DX EX SP (DX | RP -> SP)
                EX = aluOr(DX, RP);
                break;
            case 0b0001_1_10_100_011_111: //OR DX EX SP (DX | PC -> SP)
                EX = aluOr(DX, PC);
                break;
            case 0b0001_1_10_101_011_000: //OR DX AX SP (DX | AX -> SP)
                SP = aluOr(DX, AX);
                break;
            case 0b0001_1_10_101_011_001: //OR DX BX SP (DX | BX -> SP)
                SP = aluOr(DX, BX);
                break;
            case 0b0001_1_10_101_011_010: //OR DX CX SP (DX | CX -> SP)
                SP = aluOr(DX, CX);
                break;
            case 0b0001_1_10_101_011_011: //OR DX DX SP (DX | DX -> SP)
                SP = aluOr(DX, DX);
                break;
            case 0b0001_1_10_101_011_100: //OR DX EX SP (DX | EX -> SP)
                SP = aluOr(DX, EX);
                break;
            case 0b0001_1_10_101_011_101: //OR DX SP SP (DX | SP -> SP)
                SP = aluOr(DX, SP);
                break;
            case 0b0001_1_10_101_011_110: //OR DX RP SP (DX | RP -> SP)
                SP = aluOr(DX, RP);
                break;
            case 0b0001_1_10_101_011_111: //OR DX PC SP (DX | PC -> SP)
                SP = aluOr(DX, PC);
                break;
            case 0b0001_1_10_110_011_000: //OR DX AX RP (DX | AX -> RP)
                RP = aluOr(DX, AX);
                break;
            case 0b0001_1_10_110_011_001: //OR DX BX RP (DX | BX -> RP)
                RP = aluOr(DX, BX);
                break;
            case 0b0001_1_10_110_011_010: //OR DX CX RP (DX | CX -> RP)
                RP = aluOr(DX, CX);
                break;
            case 0b0001_1_10_110_011_011: //OR DX DX RP (DX | DX -> RP)
                RP = aluOr(DX, DX);
                break;
            case 0b0001_1_10_110_011_100: //OR DX EX RP (DX | EX -> RP)
                RP = aluOr(DX, EX);
                break;
            case 0b0001_1_10_110_011_101: //OR DX SP RP (DX | SP -> RP)
                RP = aluOr(DX, SP);
                break;
            case 0b0001_1_10_110_011_110: //OR DX RP RP (DX | RP -> RP)
                RP = aluOr(DX, RP);
                break;
            case 0b0001_1_10_110_011_111: //OR DX PC RP (DX | PC -> RP)
                RP = aluOr(DX, PC);
                break;
            case 0b0001_1_10_111_011_000: //OR DX AX PC (DX | AX -> PC)
                PC = aluOr(DX, AX);
                break;
            case 0b0001_1_10_111_011_001: //OR DX BX PC (DX | BX -> PC)
                PC = aluOr(DX, BX);
                break;
            case 0b0001_1_10_111_011_010: //OR DX CX PC (DX | CX -> PC)
                PC = aluOr(DX, CX);
                break;
            case 0b0001_1_10_111_011_011: //OR DX DX PC (DX | DX -> PC)
                PC = aluOr(DX, DX);
                break;
            case 0b0001_1_10_111_011_100: //OR DX EX PC (DX | EX -> PC)
                PC = aluOr(DX, EX);
                break;
            case 0b0001_1_10_111_011_101: //OR DX SP PC (DX | SP -> PC)
                PC = aluOr(DX, SP);
                break;
            case 0b0001_1_10_111_011_110: //OR DX RP PC (DX | RP -> PC)
                PC = aluOr(DX, RP);
                break;
            case 0b0001_1_10_111_011_111: //OR DX PC PC (DX | PC -> PC)
                PC = aluOr(DX, PC);
                break;
            case 0b0001_1_10_000_100_000: //OR EX AX AX (EX | AX -> AX)
                AX = aluOr(EX, AX);
                break;
            case 0b0001_1_10_000_100_001: //OR EX BX AX (EX | BX -> AX)
                AX = aluOr(EX, BX);
                break;
            case 0b0001_1_10_000_100_010: //OR EX CX AX (EX | CX -> AX)
                AX = aluOr(EX, CX);
                break;
            case 0b0001_1_10_000_100_011: //OR EX DX AX (EX | DX -> AX)
                AX = aluOr(EX, DX);
                break;
            case 0b0001_1_10_000_100_100: //OR EX EX AX (EX | EX -> AX)
                AX = aluOr(EX, EX);
                break;
            case 0b0001_1_10_000_100_101: //OR EX SP AX (EX | SP -> AX)
                AX = aluOr(EX, SP);
                break;
            case 0b0001_1_10_000_100_110: //OR EX RP AX (EX | RP -> AX)
                AX = aluOr(EX, RP);
                break;
            case 0b0001_1_10_000_100_111: //OR EX PC AX (EX | PC -> AX)
                AX = aluOr(EX, PC);
                break;
            case 0b0001_1_10_001_100_000: //OR EX AX BX (EX | AX -> BX)
                BX = aluOr(EX, AX);
                break;
            case 0b0001_1_10_001_100_001: //OR EX BX BX (EX | BX -> BX)
                BX = aluOr(EX, BX);
                break;
            case 0b0001_1_10_001_100_010: //OR EX CX BX (EX | CX -> BX)
                BX = aluOr(EX, CX);
                break;
            case 0b0001_1_10_001_100_011: //OR EX DX BX (EX | DX -> BX)
                BX = aluOr(EX, DX);
                break;
            case 0b0001_1_10_001_100_100: //OR EX EX BX (EX | EX -> BX)
                BX = aluOr(EX, EX);
                break;
            case 0b0001_1_10_001_100_101: //OR EX SP BX (EX | SP -> BX)
                BX = aluOr(EX, SP);
                break;
            case 0b0001_1_10_001_100_110: //OR EX RP BX (EX | RP -> BX)
                BX = aluOr(EX, RP);
                break;
            case 0b0001_1_10_001_100_111: //OR EX PC BX (EX | PC -> BX)
                BX = aluOr(EX, PC);
                break;
            case 0b0001_1_10_010_100_000: //OR EX AX CX (EX | AX -> CX)
                CX = aluOr(EX, AX);
                break;
            case 0b0001_1_10_010_100_001: //OR EX BX CX (EX | BX -> CX)
                CX = aluOr(EX, BX);
                break;
            case 0b0001_1_10_010_100_010: //OR EX CX CX (EX | CX -> CX)
                CX = aluOr(EX, CX);
                break;
            case 0b0001_1_10_010_100_011: //OR EX DX CX (EX | DX -> CX)
                CX = aluOr(EX, DX);
                break;
            case 0b0001_1_10_010_100_100: //OR EX EX CX (EX | EX -> CX)
                CX = aluOr(EX, EX);
                break;
            case 0b0001_1_10_010_100_101: //OR EX SP CX (EX | SP -> CX)
                CX = aluOr(EX, SP);
                break;
            case 0b0001_1_10_010_100_110: //OR EX RP CX (EX | RP -> CX)
                CX = aluOr(EX, RP);
                break;
            case 0b0001_1_10_010_100_111: //OR EX PC CX (EX | PC -> CX)
                CX = aluOr(EX, PC);
                break;
            case 0b0001_1_10_011_100_000: //OR EX AX DX (EX | AX -> DX)
                DX = aluOr(EX, AX);
                break;
            case 0b0001_1_10_011_100_001: //OR EX BX DX (EX | BX -> DX)
                DX = aluOr(EX, BX);
                break;
            case 0b0001_1_10_011_100_010: //OR EX CX DX (EX | CX -> DX)
                DX = aluOr(EX, CX);
                break;
            case 0b0001_1_10_011_100_011: //OR EX DX DX (EX | DX -> DX)
                DX = aluOr(EX, DX);
                break;
            case 0b0001_1_10_011_100_100: //OR EX EX DX (EX | EX -> DX)
                DX = aluOr(EX, EX);
                break;
            case 0b0001_1_10_011_100_101: //OR EX SP DX (EX | SP -> DX)
                DX = aluOr(EX, SP);
                break;
            case 0b0001_1_10_011_100_110: //OR EX RP DX (EX | RP -> DX)
                DX = aluOr(EX, RP);
                break;
            case 0b0001_1_10_011_100_111: //OR EX PC DX (EX | PC -> DX)
                DX = aluOr(EX, PC);
                break;
            case 0b0001_1_10_100_100_000: //OR EX AX EX (EX | AX -> EX)
                EX = aluOr(EX, AX);
                break;
            case 0b0001_1_10_100_100_001: //OR EX EX SP (EX | BX -> SP)
                EX = aluOr(EX, BX);
                break;
            case 0b0001_1_10_100_100_010: //OR EX EX SP (EX | CX -> SP)
                EX = aluOr(EX, CX);
                break;
            case 0b0001_1_10_100_100_011: //OR EX EX SP (EX | DX -> SP)
                EX = aluOr(EX, DX);
                break;
            case 0b0001_1_10_100_100_100: //OR EX EX SP (EX | EX -> SP)
                EX = aluOr(EX, EX);
                break;
            case 0b0001_1_10_100_100_101: //OR EX EX SP (EX | SP -> SP)
                EX = aluOr(EX, SP);
                break;
            case 0b0001_1_10_100_100_110: //OR EX EX SP (EX | RP -> SP)
                EX = aluOr(EX, RP);
                break;
            case 0b0001_1_10_100_100_111: //OR EX EX SP (EX | PC -> SP)
                EX = aluOr(EX, PC);
                break;
            case 0b0001_1_10_101_100_000: //OR EX AX SP (EX | AX -> SP)
                SP = aluOr(EX, AX);
                break;
            case 0b0001_1_10_101_100_001: //OR EX BX SP (EX | BX -> SP)
                SP = aluOr(EX, BX);
                break;
            case 0b0001_1_10_101_100_010: //OR EX CX SP (EX | CX -> SP)
                SP = aluOr(EX, CX);
                break;
            case 0b0001_1_10_101_100_011: //OR EX DX SP (EX | DX -> SP)
                SP = aluOr(EX, DX);
                break;
            case 0b0001_1_10_101_100_100: //OR EX EX SP (EX | EX -> SP)
                SP = aluOr(EX, EX);
                break;
            case 0b0001_1_10_101_100_101: //OR EX SP SP (EX | SP -> SP)
                SP = aluOr(EX, SP);
                break;
            case 0b0001_1_10_101_100_110: //OR EX RP SP (EX | RP -> SP)
                SP = aluOr(EX, RP);
                break;
            case 0b0001_1_10_101_100_111: //OR EX PC SP (EX | PC -> SP)
                SP = aluOr(EX, PC);
                break;
            case 0b0001_1_10_110_100_000: //OR EX AX RP (EX | AX -> RP)
                RP = aluOr(EX, AX);
                break;
            case 0b0001_1_10_110_100_001: //OR EX BX RP (EX | BX -> RP)
                RP = aluOr(EX, BX);
                break;
            case 0b0001_1_10_110_100_010: //OR EX CX RP (EX | CX -> RP)
                RP = aluOr(EX, CX);
                break;
            case 0b0001_1_10_110_100_011: //OR EX DX RP (EX | DX -> RP)
                RP = aluOr(EX, DX);
                break;
            case 0b0001_1_10_110_100_100: //OR EX EX RP (EX | EX -> RP)
                RP = aluOr(EX, EX);
                break;
            case 0b0001_1_10_110_100_101: //OR EX SP RP (EX | SP -> RP)
                RP = aluOr(EX, SP);
                break;
            case 0b0001_1_10_110_100_110: //OR EX RP RP (EX | RP -> RP)
                RP = aluOr(EX, RP);
                break;
            case 0b0001_1_10_110_100_111: //OR EX PC RP (EX | PC -> RP)
                RP = aluOr(EX, PC);
                break;
            case 0b0001_1_10_111_100_000: //OR EX AX PC (EX | AX -> PC)
                PC = aluOr(EX, AX);
                break;
            case 0b0001_1_10_111_100_001: //OR EX BX PC (EX | BX -> PC)
                PC = aluOr(EX, BX);
                break;
            case 0b0001_1_10_111_100_010: //OR EX CX PC (EX | CX -> PC)
                PC = aluOr(EX, CX);
                break;
            case 0b0001_1_10_111_100_011: //OR EX DX PC (EX | DX -> PC)
                PC = aluOr(EX, DX);
                break;
            case 0b0001_1_10_111_100_100: //OR EX EX PC (EX | EX -> PC)
                PC = aluOr(EX, EX);
                break;
            case 0b0001_1_10_111_100_101: //OR EX SP PC (EX | SP -> PC)
                PC = aluOr(EX, SP);
                break;
            case 0b0001_1_10_111_100_110: //OR EX RP PC (EX | RP -> PC)
                PC = aluOr(EX, RP);
                break;
            case 0b0001_1_10_111_100_111: //OR EX PC PC (EX | PC -> PC)
                PC = aluOr(EX, PC);
                break;
            case 0b0001_1_10_000_101_000: //OR SP AX AX (SP | AX -> AX)
                AX = aluOr(SP, AX);
                break;
            case 0b0001_1_10_000_101_001: //OR SP BX AX (SP | BX -> AX)
                AX = aluOr(SP, BX);
                break;
            case 0b0001_1_10_000_101_010: //OR SP CX AX (SP | CX -> AX)
                AX = aluOr(SP, CX);
                break;
            case 0b0001_1_10_000_101_011: //OR SP DX AX (SP | DX -> AX)
                AX = aluOr(SP, DX);
                break;
            case 0b0001_1_10_000_101_100: //OR SP EX AX (SP | EX -> AX)
                AX = aluOr(SP, EX);
                break;
            case 0b0001_1_10_000_101_101: //OR SP SP AX (SP | SP -> AX)
                AX = aluOr(SP, SP);
                break;
            case 0b0001_1_10_000_101_110: //OR SP RP AX (SP | RP -> AX)
                AX = aluOr(SP, RP);
                break;
            case 0b0001_1_10_000_101_111: //OR SP PC AX (SP | PC -> AX)
                AX = aluOr(SP, PC);
                break;
            case 0b0001_1_10_001_101_000: //OR SP AX BX (SP | AX -> BX)
                BX = aluOr(SP, AX);
                break;
            case 0b0001_1_10_001_101_001: //OR SP BX BX (SP | BX -> BX)
                BX = aluOr(SP, BX);
                break;
            case 0b0001_1_10_001_101_010: //OR SP CX BX (SP | CX -> BX)
                BX = aluOr(SP, CX);
                break;
            case 0b0001_1_10_001_101_011: //OR SP DX BX (SP | DX -> BX)
                BX = aluOr(SP, DX);
                break;
            case 0b0001_1_10_001_101_100: //OR SP EX BX (SP | EX -> BX)
                BX = aluOr(SP, EX);
                break;
            case 0b0001_1_10_001_101_101: //OR SP SP BX (SP | SP -> BX)
                BX = aluOr(SP, SP);
                break;
            case 0b0001_1_10_001_101_110: //OR SP RP BX (SP | RP -> BX)
                BX = aluOr(SP, RP);
                break;
            case 0b0001_1_10_001_101_111: //OR SP PC BX (SP | PC -> BX)
                BX = aluOr(SP, PC);
                break;
            case 0b0001_1_10_010_101_000: //OR SP AX CX (SP | AX -> CX)
                CX = aluOr(SP, AX);
                break;
            case 0b0001_1_10_010_101_001: //OR SP BX CX (SP | BX -> CX)
                CX = aluOr(SP, BX);
                break;
            case 0b0001_1_10_010_101_010: //OR SP CX CX (SP | CX -> CX)
                CX = aluOr(SP, CX);
                break;
            case 0b0001_1_10_010_101_011: //OR SP DX CX (SP | DX -> CX)
                CX = aluOr(SP, DX);
                break;
            case 0b0001_1_10_010_101_100: //OR SP EX CX (SP | EX -> CX)
                CX = aluOr(SP, EX);
                break;
            case 0b0001_1_10_010_101_101: //OR SP SP CX (SP | SP -> CX)
                CX = aluOr(SP, SP);
                break;
            case 0b0001_1_10_010_101_110: //OR SP RP CX (SP | RP -> CX)
                CX = aluOr(SP, RP);
                break;
            case 0b0001_1_10_010_101_111: //OR SP PC CX (SP | PC -> CX)
                CX = aluOr(SP, PC);
                break;
            case 0b0001_1_10_011_101_000: //OR SP AX DX (SP | AX -> DX)
                DX = aluOr(SP, AX);
                break;
            case 0b0001_1_10_011_101_001: //OR SP BX DX (SP | BX -> DX)
                DX = aluOr(SP, BX);
                break;
            case 0b0001_1_10_011_101_010: //OR SP CX DX (SP | CX -> DX)
                DX = aluOr(SP, CX);
                break;
            case 0b0001_1_10_011_101_011: //OR SP DX DX (SP | DX -> DX)
                DX = aluOr(SP, DX);
                break;
            case 0b0001_1_10_011_101_100: //OR SP EX DX (SP | EX -> DX)
                DX = aluOr(SP, EX);
                break;
            case 0b0001_1_10_011_101_101: //OR SP SP DX (SP | SP -> DX)
                DX = aluOr(SP, SP);
                break;
            case 0b0001_1_10_011_101_110: //OR SP RP DX (SP | RP -> DX)
                DX = aluOr(SP, RP);
                break;
            case 0b0001_1_10_011_101_111: //OR SP PC DX (SP | PC -> DX)
                DX = aluOr(SP, PC);
                break;
            case 0b0001_1_10_100_101_000: //OR SP AX EX (SP | AX -> EX)
                EX = aluOr(SP, AX);
                break;
            case 0b0001_1_10_100_101_001: //OR SP EX SP (SP | BX -> SP)
                EX = aluOr(SP, BX);
                break;
            case 0b0001_1_10_100_101_010: //OR SP EX SP (SP | CX -> SP)
                EX = aluOr(SP, CX);
                break;
            case 0b0001_1_10_100_101_011: //OR SP EX SP (SP | DX -> SP)
                EX = aluOr(SP, DX);
                break;
            case 0b0001_1_10_100_101_100: //OR SP EX SP (SP | EX -> SP)
                EX = aluOr(SP, EX);
                break;
            case 0b0001_1_10_100_101_101: //OR SP EX SP (SP | SP -> SP)
                EX = aluOr(SP, SP);
                break;
            case 0b0001_1_10_100_101_110: //OR SP EX SP (SP | RP -> SP)
                EX = aluOr(SP, RP);
                break;
            case 0b0001_1_10_100_101_111: //OR SP EX SP (SP | PC -> SP)
                EX = aluOr(SP, PC);
                break;
            case 0b0001_1_10_101_101_000: //OR SP AX SP (SP | AX -> SP)
                SP = aluOr(SP, AX);
                break;
            case 0b0001_1_10_101_101_001: //OR SP BX SP (SP | BX -> SP)
                SP = aluOr(SP, BX);
                break;
            case 0b0001_1_10_101_101_010: //OR SP CX SP (SP | CX -> SP)
                SP = aluOr(SP, CX);
                break;
            case 0b0001_1_10_101_101_011: //OR SP DX SP (SP | DX -> SP)
                SP = aluOr(SP, DX);
                break;
            case 0b0001_1_10_101_101_100: //OR SP EX SP (SP | EX -> SP)
                SP = aluOr(SP, EX);
                break;
            case 0b0001_1_10_101_101_101: //OR SP SP SP (SP | SP -> SP)
                SP = aluOr(SP, SP);
                break;
            case 0b0001_1_10_101_101_110: //OR SP RP SP (SP | RP -> SP)
                SP = aluOr(SP, RP);
                break;
            case 0b0001_1_10_101_101_111: //OR SP PC SP (SP | PC -> SP)
                SP = aluOr(SP, PC);
                break;
            case 0b0001_1_10_110_101_000: //OR SP AX RP (SP | AX -> RP)
                RP = aluOr(SP, AX);
                break;
            case 0b0001_1_10_110_101_001: //OR SP BX RP (SP | BX -> RP)
                RP = aluOr(SP, BX);
                break;
            case 0b0001_1_10_110_101_010: //OR SP CX RP (SP | CX -> RP)
                RP = aluOr(SP, CX);
                break;
            case 0b0001_1_10_110_101_011: //OR SP DX RP (SP | DX -> RP)
                RP = aluOr(SP, DX);
                break;
            case 0b0001_1_10_110_101_100: //OR SP EX RP (SP | EX -> RP)
                RP = aluOr(SP, EX);
                break;
            case 0b0001_1_10_110_101_101: //OR SP SP RP (SP | SP -> RP)
                RP = aluOr(SP, SP);
                break;
            case 0b0001_1_10_110_101_110: //OR SP RP RP (SP | RP -> RP)
                RP = aluOr(SP, RP);
                break;
            case 0b0001_1_10_110_101_111: //OR SP PC RP (SP | PC -> RP)
                RP = aluOr(SP, PC);
                break;
            case 0b0001_1_10_111_101_000: //OR SP AX PC (SP | AX -> PC)
                PC = aluOr(SP, AX);
                break;
            case 0b0001_1_10_111_101_001: //OR SP BX PC (SP | BX -> PC)
                PC = aluOr(SP, BX);
                break;
            case 0b0001_1_10_111_101_010: //OR SP CX PC (SP | CX -> PC)
                PC = aluOr(SP, CX);
                break;
            case 0b0001_1_10_111_101_011: //OR SP DX PC (SP | DX -> PC)
                PC = aluOr(SP, DX);
                break;
            case 0b0001_1_10_111_101_100: //OR SP EX PC (SP | EX -> PC)
                PC = aluOr(SP, EX);
                break;
            case 0b0001_1_10_111_101_101: //OR SP SP PC (SP | SP -> PC)
                PC = aluOr(SP, SP);
                break;
            case 0b0001_1_10_111_101_110: //OR SP RP PC (SP | RP -> PC)
                PC = aluOr(SP, RP);
                break;
            case 0b0001_1_10_111_101_111: //OR SP PC PC (SP | PC -> PC)
                PC = aluOr(SP, PC);
                break;
            case 0b0001_1_10_000_110_000: //OR RP AX AX (RP | AX -> AX)
                AX = aluOr(RP, AX);
                break;
            case 0b0001_1_10_000_110_001: //OR RP BX AX (RP | BX -> AX)
                AX = aluOr(RP, BX);
                break;
            case 0b0001_1_10_000_110_010: //OR RP CX AX (RP | CX -> AX)
                AX = aluOr(RP, CX);
                break;
            case 0b0001_1_10_000_110_011: //OR RP DX AX (RP | DX -> AX)
                AX = aluOr(RP, DX);
                break;
            case 0b0001_1_10_000_110_100: //OR RP EX AX (RP | EX -> AX)
                AX = aluOr(RP, EX);
                break;
            case 0b0001_1_10_000_110_101: //OR RP SP AX (RP | SP -> AX)
                AX = aluOr(RP, SP);
                break;
            case 0b0001_1_10_000_110_110: //OR RP RP AX (RP | RP -> AX)
                AX = aluOr(RP, RP);
                break;
            case 0b0001_1_10_000_110_111: //OR RP PC AX (RP | PC -> AX)
                AX = aluOr(RP, PC);
                break;
            case 0b0001_1_10_001_110_000: //OR RP AX BX (RP | AX -> BX)
                BX = aluOr(RP, AX);
                break;
            case 0b0001_1_10_001_110_001: //OR RP BX BX (RP | BX -> BX)
                BX = aluOr(RP, BX);
                break;
            case 0b0001_1_10_001_110_010: //OR RP CX BX (RP | CX -> BX)
                BX = aluOr(RP, CX);
                break;
            case 0b0001_1_10_001_110_011: //OR RP DX BX (RP | DX -> BX)
                BX = aluOr(RP, DX);
                break;
            case 0b0001_1_10_001_110_100: //OR RP EX BX (RP | EX -> BX)
                BX = aluOr(RP, EX);
                break;
            case 0b0001_1_10_001_110_101: //OR RP SP BX (RP | SP -> BX)
                BX = aluOr(RP, SP);
                break;
            case 0b0001_1_10_001_110_110: //OR RP RP BX (RP | RP -> BX)
                BX = aluOr(RP, RP);
                break;
            case 0b0001_1_10_001_110_111: //OR RP PC BX (RP | PC -> BX)
                BX = aluOr(RP, PC);
                break;
            case 0b0001_1_10_010_110_000: //OR RP AX CX (RP | AX -> CX)
                CX = aluOr(RP, AX);
                break;
            case 0b0001_1_10_010_110_001: //OR RP BX CX (RP | BX -> CX)
                CX = aluOr(RP, BX);
                break;
            case 0b0001_1_10_010_110_010: //OR RP CX CX (RP | CX -> CX)
                CX = aluOr(RP, CX);
                break;
            case 0b0001_1_10_010_110_011: //OR RP DX CX (RP | DX -> CX)
                CX = aluOr(RP, DX);
                break;
            case 0b0001_1_10_010_110_100: //OR RP EX CX (RP | EX -> CX)
                CX = aluOr(RP, EX);
                break;
            case 0b0001_1_10_010_110_101: //OR RP SP CX (RP | SP -> CX)
                CX = aluOr(RP, SP);
                break;
            case 0b0001_1_10_010_110_110: //OR RP RP CX (RP | RP -> CX)
                CX = aluOr(RP, RP);
                break;
            case 0b0001_1_10_010_110_111: //OR RP PC CX (RP | PC -> CX)
                CX = aluOr(RP, PC);
                break;
            case 0b0001_1_10_011_110_000: //OR RP AX DX (RP | AX -> DX)
                DX = aluOr(RP, AX);
                break;
            case 0b0001_1_10_011_110_001: //OR RP BX DX (RP | BX -> DX)
                DX = aluOr(RP, BX);
                break;
            case 0b0001_1_10_011_110_010: //OR RP CX DX (RP | CX -> DX)
                DX = aluOr(RP, CX);
                break;
            case 0b0001_1_10_011_110_011: //OR RP DX DX (RP | DX -> DX)
                DX = aluOr(RP, DX);
                break;
            case 0b0001_1_10_011_110_100: //OR RP EX DX (RP | EX -> DX)
                DX = aluOr(RP, EX);
                break;
            case 0b0001_1_10_011_110_101: //OR RP SP DX (RP | SP -> DX)
                DX = aluOr(RP, SP);
                break;
            case 0b0001_1_10_011_110_110: //OR RP RP DX (RP | RP -> DX)
                DX = aluOr(RP, RP);
                break;
            case 0b0001_1_10_011_110_111: //OR RP PC DX (RP | PC -> DX)
                DX = aluOr(RP, PC);
                break;
            case 0b0001_1_10_100_110_000: //OR RP AX EX (RP | AX -> EX)
                EX = aluOr(RP, AX);
                break;
            case 0b0001_1_10_100_110_001: //OR RP EX SP (RP | BX -> SP)
                EX = aluOr(RP, BX);
                break;
            case 0b0001_1_10_100_110_010: //OR RP EX SP (RP | CX -> SP)
                EX = aluOr(RP, CX);
                break;
            case 0b0001_1_10_100_110_011: //OR RP EX SP (RP | DX -> SP)
                EX = aluOr(RP, DX);
                break;
            case 0b0001_1_10_100_110_100: //OR RP EX SP (RP | EX -> SP)
                EX = aluOr(RP, EX);
                break;
            case 0b0001_1_10_100_110_101: //OR RP EX SP (RP | SP -> SP)
                EX = aluOr(RP, SP);
                break;
            case 0b0001_1_10_100_110_110: //OR RP EX SP (RP | RP -> SP)
                EX = aluOr(RP, RP);
                break;
            case 0b0001_1_10_100_110_111: //OR RP EX SP (RP | PC -> SP)
                EX = aluOr(RP, PC);
                break;
            case 0b0001_1_10_101_110_000: //OR RP AX SP (RP | AX -> SP)
                SP = aluOr(RP, AX);
                break;
            case 0b0001_1_10_101_110_001: //OR RP BX SP (RP | BX -> SP)
                SP = aluOr(RP, BX);
                break;
            case 0b0001_1_10_101_110_010: //OR RP CX SP (RP | CX -> SP)
                SP = aluOr(RP, CX);
                break;
            case 0b0001_1_10_101_110_011: //OR RP DX SP (RP | DX -> SP)
                SP = aluOr(RP, DX);
                break;
            case 0b0001_1_10_101_110_100: //OR RP EX SP (RP | EX -> SP)
                SP = aluOr(RP, EX);
                break;
            case 0b0001_1_10_101_110_101: //OR RP SP SP (RP | SP -> SP)
                SP = aluOr(RP, SP);
                break;
            case 0b0001_1_10_101_110_110: //OR RP RP SP (RP | RP -> SP)
                SP = aluOr(RP, RP);
                break;
            case 0b0001_1_10_101_110_111: //OR RP PC SP (RP | PC -> SP)
                SP = aluOr(RP, PC);
                break;
            case 0b0001_1_10_110_110_000: //OR RP AX RP (RP | AX -> RP)
                RP = aluOr(RP, AX);
                break;
            case 0b0001_1_10_110_110_001: //OR RP BX RP (RP | BX -> RP)
                RP = aluOr(RP, BX);
                break;
            case 0b0001_1_10_110_110_010: //OR RP CX RP (RP | CX -> RP)
                RP = aluOr(RP, CX);
                break;
            case 0b0001_1_10_110_110_011: //OR RP DX RP (RP | DX -> RP)
                RP = aluOr(RP, DX);
                break;
            case 0b0001_1_10_110_110_100: //OR RP EX RP (RP | EX -> RP)
                RP = aluOr(RP, EX);
                break;
            case 0b0001_1_10_110_110_101: //OR RP SP RP (RP | SP -> RP)
                RP = aluOr(RP, SP);
                break;
            case 0b0001_1_10_110_110_110: //OR RP RP RP (RP | RP -> RP)
                RP = aluOr(RP, RP);
                break;
            case 0b0001_1_10_110_110_111: //OR RP PC RP (RP | PC -> RP)
                RP = aluOr(RP, PC);
                break;
            case 0b0001_1_10_111_110_000: //OR RP AX PC (RP | AX -> PC)
                PC = aluOr(RP, AX);
                break;
            case 0b0001_1_10_111_110_001: //OR RP BX PC (RP | BX -> PC)
                PC = aluOr(RP, BX);
                break;
            case 0b0001_1_10_111_110_010: //OR RP CX PC (RP | CX -> PC)
                PC = aluOr(RP, CX);
                break;
            case 0b0001_1_10_111_110_011: //OR RP DX PC (RP | DX -> PC)
                PC = aluOr(RP, DX);
                break;
            case 0b0001_1_10_111_110_100: //OR RP EX PC (RP | EX -> PC)
                PC = aluOr(RP, EX);
                break;
            case 0b0001_1_10_111_110_101: //OR RP SP PC (RP | SP -> PC)
                PC = aluOr(RP, SP);
                break;
            case 0b0001_1_10_111_110_110: //OR RP RP PC (RP | RP -> PC)
                PC = aluOr(RP, RP);
                break;
            case 0b0001_1_10_111_110_111: //OR RP PC PC (RP | PC -> PC)
                PC = aluOr(RP, PC);
                break;
            case 0b0001_1_10_000_111_000: //OR PC AX AX (PC | AX -> AX)
                AX = aluOr(PC, AX);
                break;
            case 0b0001_1_10_000_111_001: //OR PC BX AX (PC | BX -> AX)
                AX = aluOr(PC, BX);
                break;
            case 0b0001_1_10_000_111_010: //OR PC CX AX (PC | CX -> AX)
                AX = aluOr(PC, CX);
                break;
            case 0b0001_1_10_000_111_011: //OR PC DX AX (PC | DX -> AX)
                AX = aluOr(PC, DX);
                break;
            case 0b0001_1_10_000_111_100: //OR PC EX AX (PC | EX -> AX)
                AX = aluOr(PC, EX);
                break;
            case 0b0001_1_10_000_111_101: //OR PC SP AX (PC | SP -> AX)
                AX = aluOr(PC, SP);
                break;
            case 0b0001_1_10_000_111_110: //OR PC RP AX (PC | RP -> AX)
                AX = aluOr(PC, RP);
                break;
            case 0b0001_1_10_000_111_111: //OR PC PC AX (PC | PC -> AX)
                AX = aluOr(PC, PC);
                break;
            case 0b0001_1_10_001_111_000: //OR PC AX BX (PC | AX -> BX)
                BX = aluOr(PC, AX);
                break;
            case 0b0001_1_10_001_111_001: //OR PC BX BX (PC | BX -> BX)
                BX = aluOr(PC, BX);
                break;
            case 0b0001_1_10_001_111_010: //OR PC CX BX (PC | CX -> BX)
                BX = aluOr(PC, CX);
                break;
            case 0b0001_1_10_001_111_011: //OR PC DX BX (PC | DX -> BX)
                BX = aluOr(PC, DX);
                break;
            case 0b0001_1_10_001_111_100: //OR PC EX BX (PC | EX -> BX)
                BX = aluOr(PC, EX);
                break;
            case 0b0001_1_10_001_111_101: //OR PC SP BX (PC | SP -> BX)
                BX = aluOr(PC, SP);
                break;
            case 0b0001_1_10_001_111_110: //OR PC RP BX (PC | RP -> BX)
                BX = aluOr(PC, RP);
                break;
            case 0b0001_1_10_001_111_111: //OR PC PC BX (PC | PC -> BX)
                BX = aluOr(PC, PC);
                break;
            case 0b0001_1_10_010_111_000: //OR PC AX CX (PC | AX -> CX)
                CX = aluOr(PC, AX);
                break;
            case 0b0001_1_10_010_111_001: //OR PC BX CX (PC | BX -> CX)
                CX = aluOr(PC, BX);
                break;
            case 0b0001_1_10_010_111_010: //OR PC CX CX (PC | CX -> CX)
                CX = aluOr(PC, CX);
                break;
            case 0b0001_1_10_010_111_011: //OR PC DX CX (PC | DX -> CX)
                CX = aluOr(PC, DX);
                break;
            case 0b0001_1_10_010_111_100: //OR PC EX CX (PC | EX -> CX)
                CX = aluOr(PC, EX);
                break;
            case 0b0001_1_10_010_111_101: //OR PC SP CX (PC | SP -> CX)
                CX = aluOr(PC, SP);
                break;
            case 0b0001_1_10_010_111_110: //OR PC RP CX (PC | RP -> CX)
                CX = aluOr(PC, RP);
                break;
            case 0b0001_1_10_010_111_111: //OR PC PC CX (PC | PC -> CX)
                CX = aluOr(PC, PC);
                break;
            case 0b0001_1_10_011_111_000: //OR PC AX DX (PC | AX -> DX)
                DX = aluOr(PC, AX);
                break;
            case 0b0001_1_10_011_111_001: //OR PC BX DX (PC | BX -> DX)
                DX = aluOr(PC, BX);
                break;
            case 0b0001_1_10_011_111_010: //OR PC CX DX (PC | CX -> DX)
                DX = aluOr(PC, CX);
                break;
            case 0b0001_1_10_011_111_011: //OR PC DX DX (PC | DX -> DX)
                DX = aluOr(PC, DX);
                break;
            case 0b0001_1_10_011_111_100: //OR PC EX DX (PC | EX -> DX)
                DX = aluOr(PC, EX);
                break;
            case 0b0001_1_10_011_111_101: //OR PC SP DX (PC | SP -> DX)
                DX = aluOr(PC, SP);
                break;
            case 0b0001_1_10_011_111_110: //OR PC RP DX (PC | RP -> DX)
                DX = aluOr(PC, RP);
                break;
            case 0b0001_1_10_011_111_111: //OR PC PC DX (PC | PC -> DX)
                DX = aluOr(PC, PC);
                break;
            case 0b0001_1_10_100_111_000: //OR PC AX EX (PC | AX -> EX)
                EX = aluOr(PC, AX);
                break;
            case 0b0001_1_10_100_111_001: //OR PC EX SP (PC | BX -> SP)
                EX = aluOr(PC, BX);
                break;
            case 0b0001_1_10_100_111_010: //OR PC EX SP (PC | CX -> SP)
                EX = aluOr(PC, CX);
                break;
            case 0b0001_1_10_100_111_011: //OR PC EX SP (PC | DX -> SP)
                EX = aluOr(PC, DX);
                break;
            case 0b0001_1_10_100_111_100: //OR PC EX SP (PC | EX -> SP)
                EX = aluOr(PC, EX);
                break;
            case 0b0001_1_10_100_111_101: //OR PC EX SP (PC | SP -> SP)
                EX = aluOr(PC, SP);
                break;
            case 0b0001_1_10_100_111_110: //OR PC EX SP (PC | RP -> SP)
                EX = aluOr(PC, RP);
                break;
            case 0b0001_1_10_100_111_111: //OR PC EX SP (PC | PC -> SP)
                EX = aluOr(PC, PC);
                break;
            case 0b0001_1_10_101_111_000: //OR PC AX SP (PC | AX -> SP)
                SP = aluOr(PC, AX);
                break;
            case 0b0001_1_10_101_111_001: //OR PC BX SP (PC | BX -> SP)
                SP = aluOr(PC, BX);
                break;
            case 0b0001_1_10_101_111_010: //OR PC CX SP (PC | CX -> SP)
                SP = aluOr(PC, CX);
                break;
            case 0b0001_1_10_101_111_011: //OR PC DX SP (PC | DX -> SP)
                SP = aluOr(PC, DX);
                break;
            case 0b0001_1_10_101_111_100: //OR PC EX SP (PC | EX -> SP)
                SP = aluOr(PC, EX);
                break;
            case 0b0001_1_10_101_111_101: //OR PC SP SP (PC | SP -> SP)
                SP = aluOr(PC, SP);
                break;
            case 0b0001_1_10_101_111_110: //OR PC RP SP (PC | RP -> SP)
                SP = aluOr(PC, RP);
                break;
            case 0b0001_1_10_101_111_111: //OR PC PC SP (PC | PC -> SP)
                SP = aluOr(PC, PC);
                break;
            case 0b0001_1_10_110_111_000: //OR PC AX RP (PC | AX -> RP)
                RP = aluOr(PC, AX);
                break;
            case 0b0001_1_10_110_111_001: //OR PC BX RP (PC | BX -> RP)
                RP = aluOr(PC, BX);
                break;
            case 0b0001_1_10_110_111_010: //OR PC CX RP (PC | CX -> RP)
                RP = aluOr(PC, CX);
                break;
            case 0b0001_1_10_110_111_011: //OR PC DX RP (PC | DX -> RP)
                RP = aluOr(PC, DX);
                break;
            case 0b0001_1_10_110_111_100: //OR PC EX RP (PC | EX -> RP)
                RP = aluOr(PC, EX);
                break;
            case 0b0001_1_10_110_111_101: //OR PC SP RP (PC | SP -> RP)
                RP = aluOr(PC, SP);
                break;
            case 0b0001_1_10_110_111_110: //OR PC RP RP (PC | RP -> RP)
                RP = aluOr(PC, RP);
                break;
            case 0b0001_1_10_110_111_111: //OR PC PC RP (PC | PC -> RP)
                RP = aluOr(PC, PC);
                break;
            case 0b0001_1_10_111_111_000: //OR PC AX PC (PC | AX -> PC)
                PC = aluOr(PC, AX);
                break;
            case 0b0001_1_10_111_111_001: //OR PC BX PC (PC | BX -> PC)
                PC = aluOr(PC, BX);
                break;
            case 0b0001_1_10_111_111_010: //OR PC CX PC (PC | CX -> PC)
                PC = aluOr(PC, CX);
                break;
            case 0b0001_1_10_111_111_011: //OR PC DX PC (PC | DX -> PC)
                PC = aluOr(PC, DX);
                break;
            case 0b0001_1_10_111_111_100: //OR PC EX PC (PC | EX -> PC)
                PC = aluOr(PC, EX);
                break;
            case 0b0001_1_10_111_111_101: //OR PC SP PC (PC | SP -> PC)
                PC = aluOr(PC, SP);
                break;
            case 0b0001_1_10_111_111_110: //OR PC RP PC (PC | RP -> PC)
                PC = aluOr(PC, RP);
                break;
            case 0b0001_1_10_111_111_111: //OR PC PC PC (PC | PC -> PC)
                PC = aluOr(PC, PC);
                break;
            default:
                unknownInstruction(INST);
                break;
        }
    }

    /*
     * XOR: Bit-wise XOR two registers together, storing into the destination register
     */
    private void executeXORInstruction() {
        switch (INST) {
            case 0b0001_1_11_000_000_000: //XOR AX AX AX (AX ^ AX -> AX)
                AX = aluXor(AX, AX);
                break;
            case 0b0001_1_11_000_000_001: //XOR AX BX AX (AX ^ BX -> AX)
                AX = aluXor(AX, BX);
                break;
            case 0b0001_1_11_000_000_010: //XOR AX CX AX (AX ^ CX -> AX)
                AX = aluXor(AX, CX);
                break;
            case 0b0001_1_11_000_000_011: //XOR AX DX AX (AX ^ DX -> AX)
                AX = aluXor(AX, DX);
                break;
            case 0b0001_1_11_000_000_100: //XOR AX EX AX (AX ^ EX -> AX)
                AX = aluXor(AX, EX);
                break;
            case 0b0001_1_11_000_000_101: //XOR AX SP AX (AX ^ SP -> AX)
                AX = aluXor(AX, SP);
                break;
            case 0b0001_1_11_000_000_110: //XOR AX RP AX (AX ^ RP -> AX)
                AX = aluXor(AX, RP);
                break;
            case 0b0001_1_11_000_000_111: //XOR AX PC AX (AX ^ PC -> AX)
                AX = aluXor(AX, PC);
                break;
            case 0b0001_1_11_001_000_000: //XOR AX AX BX (AX ^ AX -> BX)
                BX = aluXor(AX, AX);
                break;
            case 0b0001_1_11_001_000_001: //XOR AX BX BX (AX ^ BX -> BX)
                BX = aluXor(AX, BX);
                break;
            case 0b0001_1_11_001_000_010: //XOR AX CX BX (AX ^ CX -> BX)
                BX = aluXor(AX, CX);
                break;
            case 0b0001_1_11_001_000_011: //XOR AX DX BX (AX ^ DX -> BX)
                BX = aluXor(AX, DX);
                break;
            case 0b0001_1_11_001_000_100: //XOR AX EX BX (AX ^ EX -> BX)
                BX = aluXor(AX, EX);
                break;
            case 0b0001_1_11_001_000_101: //XOR AX SP BX (AX ^ SP -> BX)
                BX = aluXor(AX, SP);
                break;
            case 0b0001_1_11_001_000_110: //XOR AX RP BX (AX ^ RP -> BX)
                BX = aluXor(AX, RP);
                break;
            case 0b0001_1_11_001_000_111: //XOR AX PC BX (AX ^ PC -> BX)
                BX = aluXor(AX, PC);
                break;
            case 0b0001_1_11_010_000_000: //XOR AX AX CX (AX ^ AX -> CX)
                CX = aluXor(AX, AX);
                break;
            case 0b0001_1_11_010_000_001: //XOR AX BX CX (AX ^ BX -> CX)
                CX = aluXor(AX, BX);
                break;
            case 0b0001_1_11_010_000_010: //XOR AX CX CX (AX ^ CX -> CX)
                CX = aluXor(AX, CX);
                break;
            case 0b0001_1_11_010_000_011: //XOR AX DX CX (AX ^ DX -> CX)
                CX = aluXor(AX, DX);
                break;
            case 0b0001_1_11_010_000_100: //XOR AX EX CX (AX ^ EX -> CX)
                CX = aluXor(AX, EX);
                break;
            case 0b0001_1_11_010_000_101: //XOR AX SP CX (AX ^ SP -> CX)
                CX = aluXor(AX, SP);
                break;
            case 0b0001_1_11_010_000_110: //XOR AX RP CX (AX ^ RP -> CX)
                CX = aluXor(AX, RP);
                break;
            case 0b0001_1_11_010_000_111: //XOR AX PC CX (AX ^ PC -> CX)
                CX = aluXor(AX, PC);
                break;
            case 0b0001_1_11_011_000_000: //XOR AX AX DX (AX ^ AX -> DX)
                DX = aluXor(AX, AX);
                break;
            case 0b0001_1_11_011_000_001: //XOR AX BX DX (AX ^ BX -> DX)
                DX = aluXor(AX, BX);
                break;
            case 0b0001_1_11_011_000_010: //XOR AX CX DX (AX ^ CX -> DX)
                DX = aluXor(AX, CX);
                break;
            case 0b0001_1_11_011_000_011: //XOR AX DX DX (AX ^ DX -> DX)
                DX = aluXor(AX, DX);
                break;
            case 0b0001_1_11_011_000_100: //XOR AX EX DX (AX ^ EX -> DX)
                DX = aluXor(AX, EX);
                break;
            case 0b0001_1_11_011_000_101: //XOR AX SP DX (AX ^ SP -> DX)
                DX = aluXor(AX, SP);
                break;
            case 0b0001_1_11_011_000_110: //XOR AX RP DX (AX ^ RP -> DX)
                DX = aluXor(AX, RP);
                break;
            case 0b0001_1_11_011_000_111: //XOR AX PC DX (AX ^ PC -> DX)
                DX = aluXor(AX, PC);
                break;
            case 0b0001_1_11_100_000_000: //XOR AX AX EX (AX ^ AX -> EX)
                EX = aluXor(AX, AX);
                break;
            case 0b0001_1_11_100_000_001: //XOR AX EX SP (AX ^ BX -EXSP)
                EX = aluXor(AX, BX);
                break;
            case 0b0001_1_11_100_000_010: //XOR AX EX SP (AX ^ CX -EXSP)
                EX = aluXor(AX, CX);
                break;
            case 0b0001_1_11_100_000_011: //XOR AX EX SP (AX ^ DX -EXSP)
                EX = aluXor(AX, DX);
                break;
            case 0b0001_1_11_100_000_100: //XOR AX EX SP (AX ^ EX -EXSP)
                EX = aluXor(AX, EX);
                break;
            case 0b0001_1_11_100_000_101: //XOR AX EX SP (AX ^ SP -EXSP)
                EX = aluXor(AX, SP);
                break;
            case 0b0001_1_11_100_000_110: //XOR AX EX SP (AX ^ RP -EXSP)
                EX = aluXor(AX, RP);
                break;
            case 0b0001_1_11_100_000_111: //XOR AX EX SP (AX ^ PC -EXSP)
                EX = aluXor(AX, PC);
                break;
            case 0b0001_1_11_101_000_000: //XOR AX AX SP (AX ^ AX -> SP)
                SP = aluXor(AX, AX);
                break;
            case 0b0001_1_11_101_000_001: //XOR AX BX SP (AX ^ BX -> SP)
                SP = aluXor(AX, BX);
                break;
            case 0b0001_1_11_101_000_010: //XOR AX CX SP (AX ^ CX -> SP)
                SP = aluXor(AX, CX);
                break;
            case 0b0001_1_11_101_000_011: //XOR AX DX SP (AX ^ DX -> SP)
                SP = aluXor(AX, DX);
                break;
            case 0b0001_1_11_101_000_100: //XOR AX EX SP (AX ^ EX -> SP)
                SP = aluXor(AX, EX);
                break;
            case 0b0001_1_11_101_000_101: //XOR AX SP SP (AX ^ SP -> SP)
                SP = aluXor(AX, SP);
                break;
            case 0b0001_1_11_101_000_110: //XOR AX RP SP (AX ^ RP -> SP)
                SP = aluXor(AX, RP);
                break;
            case 0b0001_1_11_101_000_111: //XOR AX PC SP (AX ^ PC -> SP)
                SP = aluXor(AX, PC);
                break;
            case 0b0001_1_11_110_000_000: //XOR AX AX RP (AX ^ AX -> RP)
                RP = aluXor(AX, AX);
                break;
            case 0b0001_1_11_110_000_001: //XOR AX BX RP (AX ^ BX -> RP)
                RP = aluXor(AX, BX);
                break;
            case 0b0001_1_11_110_000_010: //XOR AX CX RP (AX ^ CX -> RP)
                RP = aluXor(AX, CX);
                break;
            case 0b0001_1_11_110_000_011: //XOR AX DX RP (AX ^ DX -> RP)
                RP = aluXor(AX, DX);
                break;
            case 0b0001_1_11_110_000_100: //XOR AX EX RP (AX ^ EX -> RP)
                RP = aluXor(AX, EX);
                break;
            case 0b0001_1_11_110_000_101: //XOR AX SP RP (AX ^ SP -> RP)
                RP = aluXor(AX, SP);
                break;
            case 0b0001_1_11_110_000_110: //XOR AX RP RP (AX ^ RP -> RP)
                RP = aluXor(AX, RP);
                break;
            case 0b0001_1_11_110_000_111: //XOR AX PC RP (AX ^ PC -> RP)
                RP = aluXor(AX, PC);
                break;
            case 0b0001_1_11_111_000_000: //XOR AX AX PC (AX ^ AX -> PC)
                PC = aluXor(AX, AX);
                break;
            case 0b0001_1_11_111_000_001: //XOR AX BX PC (AX ^ BX -> PC)
                PC = aluXor(AX, BX);
                break;
            case 0b0001_1_11_111_000_010: //XOR AX CX PC (AX ^ CX -> PC)
                PC = aluXor(AX, CX);
                break;
            case 0b0001_1_11_111_000_011: //XOR AX DX PC (AX ^ DX -> PC)
                PC = aluXor(AX, DX);
                break;
            case 0b0001_1_11_111_000_100: //XOR AX EX PC (AX ^ EX -> PC)
                PC = aluXor(AX, EX);
                break;
            case 0b0001_1_11_111_000_101: //XOR AX SP PC (AX ^ SP -> PC)
                PC = aluXor(AX, SP);
                break;
            case 0b0001_1_11_111_000_110: //XOR AX RP PC (AX ^ RP -> PC)
                PC = aluXor(AX, RP);
                break;
            case 0b0001_1_11_111_000_111: //XOR AX PC PC (AX ^ PC -> PC)
                PC = aluXor(AX, PC);
                break;
            case 0b0001_1_11_000_001_000: //XOR BX AX AX (BX ^ AX -> AX)
                AX = aluXor(BX, AX);
                break;
            case 0b0001_1_11_000_001_001: //XOR BX BX AX (BX ^ BX -> AX)
                AX = aluXor(BX, BX);
                break;
            case 0b0001_1_11_000_001_010: //XOR BX CX AX (BX ^ CX -> AX)
                AX = aluXor(BX, CX);
                break;
            case 0b0001_1_11_000_001_011: //XOR BX DX AX (BX ^ DX -> AX)
                AX = aluXor(BX, DX);
                break;
            case 0b0001_1_11_000_001_100: //XOR BX EX AX (BX ^ EX -> AX)
                AX = aluXor(BX, EX);
                break;
            case 0b0001_1_11_000_001_101: //XOR BX SP AX (BX ^ SP -> AX)
                AX = aluXor(BX, SP);
                break;
            case 0b0001_1_11_000_001_110: //XOR BX RP AX (BX ^ RP -> AX)
                AX = aluXor(BX, RP);
                break;
            case 0b0001_1_11_000_001_111: //XOR BX PC AX (BX ^ PC -> AX)
                AX = aluXor(BX, PC);
                break;
            case 0b0001_1_11_001_001_000: //XOR BX AX BX (BX ^ AX -> BX)
                BX = aluXor(BX, AX);
                break;
            case 0b0001_1_11_001_001_001: //XOR BX BX BX (BX ^ BX -> BX)
                BX = aluXor(BX, BX);
                break;
            case 0b0001_1_11_001_001_010: //XOR BX CX BX (BX ^ CX -> BX)
                BX = aluXor(BX, CX);
                break;
            case 0b0001_1_11_001_001_011: //XOR BX DX BX (BX ^ DX -> BX)
                BX = aluXor(BX, DX);
                break;
            case 0b0001_1_11_001_001_100: //XOR BX EX BX (BX ^ EX -> BX)
                BX = aluXor(BX, EX);
                break;
            case 0b0001_1_11_001_001_101: //XOR BX SP BX (BX ^ SP -> BX)
                BX = aluXor(BX, SP);
                break;
            case 0b0001_1_11_001_001_110: //XOR BX RP BX (BX ^ RP -> BX)
                BX = aluXor(BX, RP);
                break;
            case 0b0001_1_11_001_001_111: //XOR BX PC BX (BX ^ PC -> BX)
                BX = aluXor(BX, PC);
                break;
            case 0b0001_1_11_010_001_000: //XOR BX AX CX (BX ^ AX -> CX)
                CX = aluXor(BX, AX);
                break;
            case 0b0001_1_11_010_001_001: //XOR BX BX CX (BX ^ BX -> CX)
                CX = aluXor(BX, BX);
                break;
            case 0b0001_1_11_010_001_010: //XOR BX CX CX (BX ^ CX -> CX)
                CX = aluXor(BX, CX);
                break;
            case 0b0001_1_11_010_001_011: //XOR BX DX CX (BX ^ DX -> CX)
                CX = aluXor(BX, DX);
                break;
            case 0b0001_1_11_010_001_100: //XOR BX EX CX (BX ^ EX -> CX)
                CX = aluXor(BX, EX);
                break;
            case 0b0001_1_11_010_001_101: //XOR BX SP CX (BX ^ SP -> CX)
                CX = aluXor(BX, SP);
                break;
            case 0b0001_1_11_010_001_110: //XOR BX RP CX (BX ^ RP -> CX)
                CX = aluXor(BX, RP);
                break;
            case 0b0001_1_11_010_001_111: //XOR BX PC CX (BX ^ PC -> CX)
                CX = aluXor(BX, PC);
                break;
            case 0b0001_1_11_011_001_000: //XOR BX AX DX (BX ^ AX -> DX)
                DX = aluXor(BX, AX);
                break;
            case 0b0001_1_11_011_001_001: //XOR BX BX DX (BX ^ BX -> DX)
                DX = aluXor(BX, BX);
                break;
            case 0b0001_1_11_011_001_010: //XOR BX CX DX (BX ^ CX -> DX)
                DX = aluXor(BX, CX);
                break;
            case 0b0001_1_11_011_001_011: //XOR BX DX DX (BX ^ DX -> DX)
                DX = aluXor(BX, DX);
                break;
            case 0b0001_1_11_011_001_100: //XOR BX EX DX (BX ^ EX -> DX)
                DX = aluXor(BX, EX);
                break;
            case 0b0001_1_11_011_001_101: //XOR BX SP DX (BX ^ SP -> DX)
                DX = aluXor(BX, SP);
                break;
            case 0b0001_1_11_011_001_110: //XOR BX RP DX (BX ^ RP -> DX)
                DX = aluXor(BX, RP);
                break;
            case 0b0001_1_11_011_001_111: //XOR BX PC DX (BX ^ PC -> DX)
                DX = aluXor(BX, PC);
                break;
            case 0b0001_1_11_100_001_000: //XOR BX AX EX (BX ^ AX -> EX)
                EX = aluXor(BX, AX);
                break;
            case 0b0001_1_11_100_001_001: //XOR BX EX SP (BX ^ BX -> SP)
                EX = aluXor(BX, BX);
                break;
            case 0b0001_1_11_100_001_010: //XOR BX EX SP (BX ^ CX -> SP)
                EX = aluXor(BX, CX);
                break;
            case 0b0001_1_11_100_001_011: //XOR BX EX SP (BX ^ DX -> SP)
                EX = aluXor(BX, DX);
                break;
            case 0b0001_1_11_100_001_100: //XOR BX EX SP (BX ^ EX -> SP)
                EX = aluXor(BX, EX);
                break;
            case 0b0001_1_11_100_001_101: //XOR BX EX SP (BX ^ SP -> SP)
                EX = aluXor(BX, SP);
                break;
            case 0b0001_1_11_100_001_110: //XOR BX EX SP (BX ^ RP -> SP)
                EX = aluXor(BX, RP);
                break;
            case 0b0001_1_11_100_001_111: //XOR BX EX SP (BX ^ PC -> SP)
                EX = aluXor(BX, PC);
                break;
            case 0b0001_1_11_101_001_000: //XOR BX AX SP (BX ^ AX -> SP)
                SP = aluXor(BX, AX);
                break;
            case 0b0001_1_11_101_001_001: //XOR BX BX SP (BX ^ BX -> SP)
                SP = aluXor(BX, BX);
                break;
            case 0b0001_1_11_101_001_010: //XOR BX CX SP (BX ^ CX -> SP)
                SP = aluXor(BX, CX);
                break;
            case 0b0001_1_11_101_001_011: //XOR BX DX SP (BX ^ DX -> SP)
                SP = aluXor(BX, DX);
                break;
            case 0b0001_1_11_101_001_100: //XOR BX EX SP (BX ^ EX -> SP)
                SP = aluXor(BX, EX);
                break;
            case 0b0001_1_11_101_001_101: //XOR BX SP SP (BX ^ SP -> SP)
                SP = aluXor(BX, SP);
                break;
            case 0b0001_1_11_101_001_110: //XOR BX RP SP (BX ^ RP -> SP)
                SP = aluXor(BX, RP);
                break;
            case 0b0001_1_11_101_001_111: //XOR BX PC SP (BX ^ PC -> SP)
                SP = aluXor(BX, PC);
                break;
            case 0b0001_1_11_110_001_000: //XOR BX AX RP (BX ^ AX -> RP)
                RP = aluXor(BX, AX);
                break;
            case 0b0001_1_11_110_001_001: //XOR BX BX RP (BX ^ BX -> RP)
                RP = aluXor(BX, BX);
                break;
            case 0b0001_1_11_110_001_010: //XOR BX CX RP (BX ^ CX -> RP)
                RP = aluXor(BX, CX);
                break;
            case 0b0001_1_11_110_001_011: //XOR BX DX RP (BX ^ DX -> RP)
                RP = aluXor(BX, DX);
                break;
            case 0b0001_1_11_110_001_100: //XOR BX EX RP (BX ^ EX -> RP)
                RP = aluXor(BX, EX);
                break;
            case 0b0001_1_11_110_001_101: //XOR BX SP RP (BX ^ SP -> RP)
                RP = aluXor(BX, SP);
                break;
            case 0b0001_1_11_110_001_110: //XOR BX RP RP (BX ^ RP -> RP)
                RP = aluXor(BX, RP);
                break;
            case 0b0001_1_11_110_001_111: //XOR BX PC RP (BX ^ PC -> RP)
                RP = aluXor(BX, PC);
                break;
            case 0b0001_1_11_111_001_000: //XOR BX AX PC (BX ^ AX -> PC)
                PC = aluXor(BX, AX);
                break;
            case 0b0001_1_11_111_001_001: //XOR BX BX PC (BX ^ BX -> PC)
                PC = aluXor(BX, BX);
                break;
            case 0b0001_1_11_111_001_010: //XOR BX CX PC (BX ^ CX -> PC)
                PC = aluXor(BX, CX);
                break;
            case 0b0001_1_11_111_001_011: //XOR BX DX PC (BX ^ DX -> PC)
                PC = aluXor(BX, DX);
                break;
            case 0b0001_1_11_111_001_100: //XOR BX EX PC (BX ^ EX -> PC)
                PC = aluXor(BX, EX);
                break;
            case 0b0001_1_11_111_001_101: //XOR BX SP PC (BX ^ SP -> PC)
                PC = aluXor(BX, SP);
                break;
            case 0b0001_1_11_111_001_110: //XOR BX RP PC (BX ^ RP -> PC)
                PC = aluXor(BX, RP);
                break;
            case 0b0001_1_11_111_001_111: //XOR BX PC PC (BX ^ PC -> PC)
                PC = aluXor(BX, PC);
                break;
            case 0b0001_1_11_000_010_000: //XOR CX AX AX (CX ^ AX -> AX)
                AX = aluXor(CX, AX);
                break;
            case 0b0001_1_11_000_010_001: //XOR CX BX AX (CX ^ BX -> AX)
                AX = aluXor(CX, BX);
                break;
            case 0b0001_1_11_000_010_010: //XOR CX CX AX (CX ^ CX -> AX)
                AX = aluXor(CX, CX);
                break;
            case 0b0001_1_11_000_010_011: //XOR CX DX AX (CX ^ DX -> AX)
                AX = aluXor(CX, DX);
                break;
            case 0b0001_1_11_000_010_100: //XOR CX EX AX (CX ^ EX -> AX)
                AX = aluXor(CX, EX);
                break;
            case 0b0001_1_11_000_010_101: //XOR CX SP AX (CX ^ SP -> AX)
                AX = aluXor(CX, SP);
                break;
            case 0b0001_1_11_000_010_110: //XOR CX RP AX (CX ^ RP -> AX)
                AX = aluXor(CX, RP);
                break;
            case 0b0001_1_11_000_010_111: //XOR CX PC AX (CX ^ PC -> AX)
                AX = aluXor(CX, PC);
                break;
            case 0b0001_1_11_001_010_000: //XOR CX AX BX (CX ^ AX -> BX)
                BX = aluXor(CX, AX);
                break;
            case 0b0001_1_11_001_010_001: //XOR CX BX BX (CX ^ BX -> BX)
                BX = aluXor(CX, BX);
                break;
            case 0b0001_1_11_001_010_010: //XOR CX CX BX (CX ^ CX -> BX)
                BX = aluXor(CX, CX);
                break;
            case 0b0001_1_11_001_010_011: //XOR CX DX BX (CX ^ DX -> BX)
                BX = aluXor(CX, DX);
                break;
            case 0b0001_1_11_001_010_100: //XOR CX EX BX (CX ^ EX -> BX)
                BX = aluXor(CX, EX);
                break;
            case 0b0001_1_11_001_010_101: //XOR CX SP BX (CX ^ SP -> BX)
                BX = aluXor(CX, SP);
                break;
            case 0b0001_1_11_001_010_110: //XOR CX RP BX (CX ^ RP -> BX)
                BX = aluXor(CX, RP);
                break;
            case 0b0001_1_11_001_010_111: //XOR CX PC BX (CX ^ PC -> BX)
                BX = aluXor(CX, PC);
                break;
            case 0b0001_1_11_010_010_000: //XOR CX AX CX (CX ^ AX -> CX)
                CX = aluXor(CX, AX);
                break;
            case 0b0001_1_11_010_010_001: //XOR CX BX CX (CX ^ BX -> CX)
                CX = aluXor(CX, BX);
                break;
            case 0b0001_1_11_010_010_010: //XOR CX CX CX (CX ^ CX -> CX)
                CX = aluXor(CX, CX);
                break;
            case 0b0001_1_11_010_010_011: //XOR CX DX CX (CX ^ DX -> CX)
                CX = aluXor(CX, DX);
                break;
            case 0b0001_1_11_010_010_100: //XOR CX EX CX (CX ^ EX -> CX)
                CX = aluXor(CX, EX);
                break;
            case 0b0001_1_11_010_010_101: //XOR CX SP CX (CX ^ SP -> CX)
                CX = aluXor(CX, SP);
                break;
            case 0b0001_1_11_010_010_110: //XOR CX RP CX (CX ^ RP -> CX)
                CX = aluXor(CX, RP);
                break;
            case 0b0001_1_11_010_010_111: //XOR CX PC CX (CX ^ PC -> CX)
                CX = aluXor(CX, PC);
                break;
            case 0b0001_1_11_011_010_000: //XOR CX AX DX (CX ^ AX -> DX)
                DX = aluXor(CX, AX);
                break;
            case 0b0001_1_11_011_010_001: //XOR CX BX DX (CX ^ BX -> DX)
                DX = aluXor(CX, BX);
                break;
            case 0b0001_1_11_011_010_010: //XOR CX CX DX (CX ^ CX -> DX)
                DX = aluXor(CX, CX);
                break;
            case 0b0001_1_11_011_010_011: //XOR CX DX DX (CX ^ DX -> DX)
                DX = aluXor(CX, DX);
                break;
            case 0b0001_1_11_011_010_100: //XOR CX EX DX (CX ^ EX -> DX)
                DX = aluXor(CX, EX);
                break;
            case 0b0001_1_11_011_010_101: //XOR CX SP DX (CX ^ SP -> DX)
                DX = aluXor(CX, SP);
                break;
            case 0b0001_1_11_011_010_110: //XOR CX RP DX (CX ^ RP -> DX)
                DX = aluXor(CX, RP);
                break;
            case 0b0001_1_11_011_010_111: //XOR CX PC DX (CX ^ PC -> DX)
                DX = aluXor(CX, PC);
                break;
            case 0b0001_1_11_100_010_000: //XOR CX AX EX (CX ^ AX -> EX)
                EX = aluXor(CX, AX);
                break;
            case 0b0001_1_11_100_010_001: //XOR CX EX SP (CX ^ BX -> SP)
                EX = aluXor(CX, BX);
                break;
            case 0b0001_1_11_100_010_010: //XOR CX EX SP (CX ^ CX -> SP)
                EX = aluXor(CX, CX);
                break;
            case 0b0001_1_11_100_010_011: //XOR CX EX SP (CX ^ DX -> SP)
                EX = aluXor(CX, DX);
                break;
            case 0b0001_1_11_100_010_100: //XOR CX EX SP (CX ^ EX -> SP)
                EX = aluXor(CX, EX);
                break;
            case 0b0001_1_11_100_010_101: //XOR CX EX SP (CX ^ SP -> SP)
                EX = aluXor(CX, SP);
                break;
            case 0b0001_1_11_100_010_110: //XOR CX EX SP (CX ^ RP -> SP)
                EX = aluXor(CX, RP);
                break;
            case 0b0001_1_11_100_010_111: //XOR CX EX SP (CX ^ PC -> SP)
                EX = aluXor(CX, PC);
                break;
            case 0b0001_1_11_101_010_000: //XOR CX AX SP (CX ^ AX -> SP)
                SP = aluXor(CX, AX);
                break;
            case 0b0001_1_11_101_010_001: //XOR CX BX SP (CX ^ BX -> SP)
                SP = aluXor(CX, BX);
                break;
            case 0b0001_1_11_101_010_010: //XOR CX CX SP (CX ^ CX -> SP)
                SP = aluXor(CX, CX);
                break;
            case 0b0001_1_11_101_010_011: //XOR CX DX SP (CX ^ DX -> SP)
                SP = aluXor(CX, DX);
                break;
            case 0b0001_1_11_101_010_100: //XOR CX EX SP (CX ^ EX -> SP)
                SP = aluXor(CX, EX);
                break;
            case 0b0001_1_11_101_010_101: //XOR CX SP SP (CX ^ SP -> SP)
                SP = aluXor(CX, SP);
                break;
            case 0b0001_1_11_101_010_110: //XOR CX RP SP (CX ^ RP -> SP)
                SP = aluXor(CX, RP);
                break;
            case 0b0001_1_11_101_010_111: //XOR CX PC SP (CX ^ PC -> SP)
                SP = aluXor(CX, PC);
                break;
            case 0b0001_1_11_110_010_000: //XOR CX AX RP (CX ^ AX -> RP)
                RP = aluXor(CX, AX);
                break;
            case 0b0001_1_11_110_010_001: //XOR CX BX RP (CX ^ BX -> RP)
                RP = aluXor(CX, BX);
                break;
            case 0b0001_1_11_110_010_010: //XOR CX CX RP (CX ^ CX -> RP)
                RP = aluXor(CX, CX);
                break;
            case 0b0001_1_11_110_010_011: //XOR CX DX RP (CX ^ DX -> RP)
                RP = aluXor(CX, DX);
                break;
            case 0b0001_1_11_110_010_100: //XOR CX EX RP (CX ^ EX -> RP)
                RP = aluXor(CX, EX);
                break;
            case 0b0001_1_11_110_010_101: //XOR CX SP RP (CX ^ SP -> RP)
                RP = aluXor(CX, SP);
                break;
            case 0b0001_1_11_110_010_110: //XOR CX RP RP (CX ^ RP -> RP)
                RP = aluXor(CX, RP);
                break;
            case 0b0001_1_11_110_010_111: //XOR CX PC RP (CX ^ PC -> RP)
                RP = aluXor(CX, PC);
                break;
            case 0b0001_1_11_111_010_000: //XOR CX AX PC (CX ^ AX -> PC)
                PC = aluXor(CX, AX);
                break;
            case 0b0001_1_11_111_010_001: //XOR CX BX PC (CX ^ BX -> PC)
                PC = aluXor(CX, BX);
                break;
            case 0b0001_1_11_111_010_010: //XOR CX CX PC (CX ^ CX -> PC)
                PC = aluXor(CX, CX);
                break;
            case 0b0001_1_11_111_010_011: //XOR CX DX PC (CX ^ DX -> PC)
                PC = aluXor(CX, DX);
                break;
            case 0b0001_1_11_111_010_100: //XOR CX EX PC (CX ^ EX -> PC)
                PC = aluXor(CX, EX);
                break;
            case 0b0001_1_11_111_010_101: //XOR CX SP PC (CX ^ SP -> PC)
                PC = aluXor(CX, SP);
                break;
            case 0b0001_1_11_111_010_110: //XOR CX RP PC (CX ^ RP -> PC)
                PC = aluXor(CX, RP);
                break;
            case 0b0001_1_11_111_010_111: //XOR CX PC PC (CX ^ PC -> PC)
                PC = aluXor(CX, PC);
                break;
            case 0b0001_1_11_000_011_000: //XOR DX AX AX (DX ^ AX -> AX)
                AX = aluXor(DX, AX);
                break;
            case 0b0001_1_11_000_011_001: //XOR DX BX AX (DX ^ BX -> AX)
                AX = aluXor(DX, BX);
                break;
            case 0b0001_1_11_000_011_010: //XOR DX CX AX (DX ^ CX -> AX)
                AX = aluXor(DX, CX);
                break;
            case 0b0001_1_11_000_011_011: //XOR DX DX AX (DX ^ DX -> AX)
                AX = aluXor(DX, DX);
                break;
            case 0b0001_1_11_000_011_100: //XOR DX EX AX (DX ^ EX -> AX)
                AX = aluXor(DX, EX);
                break;
            case 0b0001_1_11_000_011_101: //XOR DX SP AX (DX ^ SP -> AX)
                AX = aluXor(DX, SP);
                break;
            case 0b0001_1_11_000_011_110: //XOR DX RP AX (DX ^ RP -> AX)
                AX = aluXor(DX, RP);
                break;
            case 0b0001_1_11_000_011_111: //XOR DX PC AX (DX ^ PC -> AX)
                AX = aluXor(DX, PC);
                break;
            case 0b0001_1_11_001_011_000: //XOR DX AX BX (DX ^ AX -> BX)
                BX = aluXor(DX, AX);
                break;
            case 0b0001_1_11_001_011_001: //XOR DX BX BX (DX ^ BX -> BX)
                BX = aluXor(DX, BX);
                break;
            case 0b0001_1_11_001_011_010: //XOR DX CX BX (DX ^ CX -> BX)
                BX = aluXor(DX, CX);
                break;
            case 0b0001_1_11_001_011_011: //XOR DX DX BX (DX ^ DX -> BX)
                BX = aluXor(DX, DX);
                break;
            case 0b0001_1_11_001_011_100: //XOR DX EX BX (DX ^ EX -> BX)
                BX = aluXor(DX, EX);
                break;
            case 0b0001_1_11_001_011_101: //XOR DX SP BX (DX ^ SP -> BX)
                BX = aluXor(DX, SP);
                break;
            case 0b0001_1_11_001_011_110: //XOR DX RP BX (DX ^ RP -> BX)
                BX = aluXor(DX, RP);
                break;
            case 0b0001_1_11_001_011_111: //XOR DX PC BX (DX ^ PC -> BX)
                BX = aluXor(DX, PC);
                break;
            case 0b0001_1_11_010_011_000: //XOR DX AX CX (DX ^ AX -> CX)
                CX = aluXor(DX, AX);
                break;
            case 0b0001_1_11_010_011_001: //XOR DX BX CX (DX ^ BX -> CX)
                CX = aluXor(DX, BX);
                break;
            case 0b0001_1_11_010_011_010: //XOR DX CX CX (DX ^ CX -> CX)
                CX = aluXor(DX, CX);
                break;
            case 0b0001_1_11_010_011_011: //XOR DX DX CX (DX ^ DX -> CX)
                CX = aluXor(DX, DX);
                break;
            case 0b0001_1_11_010_011_100: //XOR DX EX CX (DX ^ EX -> CX)
                CX = aluXor(DX, EX);
                break;
            case 0b0001_1_11_010_011_101: //XOR DX SP CX (DX ^ SP -> CX)
                CX = aluXor(DX, SP);
                break;
            case 0b0001_1_11_010_011_110: //XOR DX RP CX (DX ^ RP -> CX)
                CX = aluXor(DX, RP);
                break;
            case 0b0001_1_11_010_011_111: //XOR DX PC CX (DX ^ PC -> CX)
                CX = aluXor(DX, PC);
                break;
            case 0b0001_1_11_011_011_000: //XOR DX AX DX (DX ^ AX -> DX)
                DX = aluXor(DX, AX);
                break;
            case 0b0001_1_11_011_011_001: //XOR DX BX DX (DX ^ BX -> DX)
                DX = aluXor(DX, BX);
                break;
            case 0b0001_1_11_011_011_010: //XOR DX CX DX (DX ^ CX -> DX)
                DX = aluXor(DX, CX);
                break;
            case 0b0001_1_11_011_011_011: //XOR DX DX DX (DX ^ DX -> DX)
                DX = aluXor(DX, DX);
                break;
            case 0b0001_1_11_011_011_100: //XOR DX EX DX (DX ^ EX -> DX)
                DX = aluXor(DX, EX);
                break;
            case 0b0001_1_11_011_011_101: //XOR DX SP DX (DX ^ SP -> DX)
                DX = aluXor(DX, SP);
                break;
            case 0b0001_1_11_011_011_110: //XOR DX RP DX (DX ^ RP -> DX)
                DX = aluXor(DX, RP);
                break;
            case 0b0001_1_11_011_011_111: //XOR DX PC DX (DX ^ PC -> DX)
                DX = aluXor(DX, PC);
                break;
            case 0b0001_1_11_100_011_000: //XOR DX AX EX (DX ^ AX -> EX)
                EX = aluXor(DX, AX);
                break;
            case 0b0001_1_11_100_011_001: //XOR DX EX SP (DX ^ BX -> SP)
                EX = aluXor(DX, BX);
                break;
            case 0b0001_1_11_100_011_010: //XOR DX EX SP (DX ^ CX -> SP)
                EX = aluXor(DX, CX);
                break;
            case 0b0001_1_11_100_011_011: //XOR DX EX SP (DX ^ DX -> SP)
                EX = aluXor(DX, DX);
                break;
            case 0b0001_1_11_100_011_100: //XOR DX EX SP (DX ^ EX -> SP)
                EX = aluXor(DX, EX);
                break;
            case 0b0001_1_11_100_011_101: //XOR DX EX SP (DX ^ SP -> SP)
                EX = aluXor(DX, SP);
                break;
            case 0b0001_1_11_100_011_110: //XOR DX EX SP (DX ^ RP -> SP)
                EX = aluXor(DX, RP);
                break;
            case 0b0001_1_11_100_011_111: //XOR DX EX SP (DX ^ PC -> SP)
                EX = aluXor(DX, PC);
                break;
            case 0b0001_1_11_101_011_000: //XOR DX AX SP (DX ^ AX -> SP)
                SP = aluXor(DX, AX);
                break;
            case 0b0001_1_11_101_011_001: //XOR DX BX SP (DX ^ BX -> SP)
                SP = aluXor(DX, BX);
                break;
            case 0b0001_1_11_101_011_010: //XOR DX CX SP (DX ^ CX -> SP)
                SP = aluXor(DX, CX);
                break;
            case 0b0001_1_11_101_011_011: //XOR DX DX SP (DX ^ DX -> SP)
                SP = aluXor(DX, DX);
                break;
            case 0b0001_1_11_101_011_100: //XOR DX EX SP (DX ^ EX -> SP)
                SP = aluXor(DX, EX);
                break;
            case 0b0001_1_11_101_011_101: //XOR DX SP SP (DX ^ SP -> SP)
                SP = aluXor(DX, SP);
                break;
            case 0b0001_1_11_101_011_110: //XOR DX RP SP (DX ^ RP -> SP)
                SP = aluXor(DX, RP);
                break;
            case 0b0001_1_11_101_011_111: //XOR DX PC SP (DX ^ PC -> SP)
                SP = aluXor(DX, PC);
                break;
            case 0b0001_1_11_110_011_000: //XOR DX AX RP (DX ^ AX -> RP)
                RP = aluXor(DX, AX);
                break;
            case 0b0001_1_11_110_011_001: //XOR DX BX RP (DX ^ BX -> RP)
                RP = aluXor(DX, BX);
                break;
            case 0b0001_1_11_110_011_010: //XOR DX CX RP (DX ^ CX -> RP)
                RP = aluXor(DX, CX);
                break;
            case 0b0001_1_11_110_011_011: //XOR DX DX RP (DX ^ DX -> RP)
                RP = aluXor(DX, DX);
                break;
            case 0b0001_1_11_110_011_100: //XOR DX EX RP (DX ^ EX -> RP)
                RP = aluXor(DX, EX);
                break;
            case 0b0001_1_11_110_011_101: //XOR DX SP RP (DX ^ SP -> RP)
                RP = aluXor(DX, SP);
                break;
            case 0b0001_1_11_110_011_110: //XOR DX RP RP (DX ^ RP -> RP)
                RP = aluXor(DX, RP);
                break;
            case 0b0001_1_11_110_011_111: //XOR DX PC RP (DX ^ PC -> RP)
                RP = aluXor(DX, PC);
                break;
            case 0b0001_1_11_111_011_000: //XOR DX AX PC (DX ^ AX -> PC)
                PC = aluXor(DX, AX);
                break;
            case 0b0001_1_11_111_011_001: //XOR DX BX PC (DX ^ BX -> PC)
                PC = aluXor(DX, BX);
                break;
            case 0b0001_1_11_111_011_010: //XOR DX CX PC (DX ^ CX -> PC)
                PC = aluXor(DX, CX);
                break;
            case 0b0001_1_11_111_011_011: //XOR DX DX PC (DX ^ DX -> PC)
                PC = aluXor(DX, DX);
                break;
            case 0b0001_1_11_111_011_100: //XOR DX EX PC (DX ^ EX -> PC)
                PC = aluXor(DX, EX);
                break;
            case 0b0001_1_11_111_011_101: //XOR DX SP PC (DX ^ SP -> PC)
                PC = aluXor(DX, SP);
                break;
            case 0b0001_1_11_111_011_110: //XOR DX RP PC (DX ^ RP -> PC)
                PC = aluXor(DX, RP);
                break;
            case 0b0001_1_11_111_011_111: //XOR DX PC PC (DX ^ PC -> PC)
                PC = aluXor(DX, PC);
                break;
            case 0b0001_1_11_000_100_000: //XOR EX AX AX (EX ^ AX -> AX)
                AX = aluXor(EX, AX);
                break;
            case 0b0001_1_11_000_100_001: //XOR EX BX AX (EX ^ BX -> AX)
                AX = aluXor(EX, BX);
                break;
            case 0b0001_1_11_000_100_010: //XOR EX CX AX (EX ^ CX -> AX)
                AX = aluXor(EX, CX);
                break;
            case 0b0001_1_11_000_100_011: //XOR EX DX AX (EX ^ DX -> AX)
                AX = aluXor(EX, DX);
                break;
            case 0b0001_1_11_000_100_100: //XOR EX EX AX (EX ^ EX -> AX)
                AX = aluXor(EX, EX);
                break;
            case 0b0001_1_11_000_100_101: //XOR EX SP AX (EX ^ SP -> AX)
                AX = aluXor(EX, SP);
                break;
            case 0b0001_1_11_000_100_110: //XOR EX RP AX (EX ^ RP -> AX)
                AX = aluXor(EX, RP);
                break;
            case 0b0001_1_11_000_100_111: //XOR EX PC AX (EX ^ PC -> AX)
                AX = aluXor(EX, PC);
                break;
            case 0b0001_1_11_001_100_000: //XOR EX AX BX (EX ^ AX -> BX)
                BX = aluXor(EX, AX);
                break;
            case 0b0001_1_11_001_100_001: //XOR EX BX BX (EX ^ BX -> BX)
                BX = aluXor(EX, BX);
                break;
            case 0b0001_1_11_001_100_010: //XOR EX CX BX (EX ^ CX -> BX)
                BX = aluXor(EX, CX);
                break;
            case 0b0001_1_11_001_100_011: //XOR EX DX BX (EX ^ DX -> BX)
                BX = aluXor(EX, DX);
                break;
            case 0b0001_1_11_001_100_100: //XOR EX EX BX (EX ^ EX -> BX)
                BX = aluXor(EX, EX);
                break;
            case 0b0001_1_11_001_100_101: //XOR EX SP BX (EX ^ SP -> BX)
                BX = aluXor(EX, SP);
                break;
            case 0b0001_1_11_001_100_110: //XOR EX RP BX (EX ^ RP -> BX)
                BX = aluXor(EX, RP);
                break;
            case 0b0001_1_11_001_100_111: //XOR EX PC BX (EX ^ PC -> BX)
                BX = aluXor(EX, PC);
                break;
            case 0b0001_1_11_010_100_000: //XOR EX AX CX (EX ^ AX -> CX)
                CX = aluXor(EX, AX);
                break;
            case 0b0001_1_11_010_100_001: //XOR EX BX CX (EX ^ BX -> CX)
                CX = aluXor(EX, BX);
                break;
            case 0b0001_1_11_010_100_010: //XOR EX CX CX (EX ^ CX -> CX)
                CX = aluXor(EX, CX);
                break;
            case 0b0001_1_11_010_100_011: //XOR EX DX CX (EX ^ DX -> CX)
                CX = aluXor(EX, DX);
                break;
            case 0b0001_1_11_010_100_100: //XOR EX EX CX (EX ^ EX -> CX)
                CX = aluXor(EX, EX);
                break;
            case 0b0001_1_11_010_100_101: //XOR EX SP CX (EX ^ SP -> CX)
                CX = aluXor(EX, SP);
                break;
            case 0b0001_1_11_010_100_110: //XOR EX RP CX (EX ^ RP -> CX)
                CX = aluXor(EX, RP);
                break;
            case 0b0001_1_11_010_100_111: //XOR EX PC CX (EX ^ PC -> CX)
                CX = aluXor(EX, PC);
                break;
            case 0b0001_1_11_011_100_000: //XOR EX AX DX (EX ^ AX -> DX)
                DX = aluXor(EX, AX);
                break;
            case 0b0001_1_11_011_100_001: //XOR EX BX DX (EX ^ BX -> DX)
                DX = aluXor(EX, BX);
                break;
            case 0b0001_1_11_011_100_010: //XOR EX CX DX (EX ^ CX -> DX)
                DX = aluXor(EX, CX);
                break;
            case 0b0001_1_11_011_100_011: //XOR EX DX DX (EX ^ DX -> DX)
                DX = aluXor(EX, DX);
                break;
            case 0b0001_1_11_011_100_100: //XOR EX EX DX (EX ^ EX -> DX)
                DX = aluXor(EX, EX);
                break;
            case 0b0001_1_11_011_100_101: //XOR EX SP DX (EX ^ SP -> DX)
                DX = aluXor(EX, SP);
                break;
            case 0b0001_1_11_011_100_110: //XOR EX RP DX (EX ^ RP -> DX)
                DX = aluXor(EX, RP);
                break;
            case 0b0001_1_11_011_100_111: //XOR EX PC DX (EX ^ PC -> DX)
                DX = aluXor(EX, PC);
                break;
            case 0b0001_1_11_100_100_000: //XOR EX AX EX (EX ^ AX -> EX)
                EX = aluXor(EX, AX);
                break;
            case 0b0001_1_11_100_100_001: //XOR EX EX SP (EX ^ BX -> SP)
                EX = aluXor(EX, BX);
                break;
            case 0b0001_1_11_100_100_010: //XOR EX EX SP (EX ^ CX -> SP)
                EX = aluXor(EX, CX);
                break;
            case 0b0001_1_11_100_100_011: //XOR EX EX SP (EX ^ DX -> SP)
                EX = aluXor(EX, DX);
                break;
            case 0b0001_1_11_100_100_100: //XOR EX EX SP (EX ^ EX -> SP)
                EX = aluXor(EX, EX);
                break;
            case 0b0001_1_11_100_100_101: //XOR EX EX SP (EX ^ SP -> SP)
                EX = aluXor(EX, SP);
                break;
            case 0b0001_1_11_100_100_110: //XOR EX EX SP (EX ^ RP -> SP)
                EX = aluXor(EX, RP);
                break;
            case 0b0001_1_11_100_100_111: //XOR EX EX SP (EX ^ PC -> SP)
                EX = aluXor(EX, PC);
                break;
            case 0b0001_1_11_101_100_000: //XOR EX AX SP (EX ^ AX -> SP)
                SP = aluXor(EX, AX);
                break;
            case 0b0001_1_11_101_100_001: //XOR EX BX SP (EX ^ BX -> SP)
                SP = aluXor(EX, BX);
                break;
            case 0b0001_1_11_101_100_010: //XOR EX CX SP (EX ^ CX -> SP)
                SP = aluXor(EX, CX);
                break;
            case 0b0001_1_11_101_100_011: //XOR EX DX SP (EX ^ DX -> SP)
                SP = aluXor(EX, DX);
                break;
            case 0b0001_1_11_101_100_100: //XOR EX EX SP (EX ^ EX -> SP)
                SP = aluXor(EX, EX);
                break;
            case 0b0001_1_11_101_100_101: //XOR EX SP SP (EX ^ SP -> SP)
                SP = aluXor(EX, SP);
                break;
            case 0b0001_1_11_101_100_110: //XOR EX RP SP (EX ^ RP -> SP)
                SP = aluXor(EX, RP);
                break;
            case 0b0001_1_11_101_100_111: //XOR EX PC SP (EX ^ PC -> SP)
                SP = aluXor(EX, PC);
                break;
            case 0b0001_1_11_110_100_000: //XOR EX AX RP (EX ^ AX -> RP)
                RP = aluXor(EX, AX);
                break;
            case 0b0001_1_11_110_100_001: //XOR EX BX RP (EX ^ BX -> RP)
                RP = aluXor(EX, BX);
                break;
            case 0b0001_1_11_110_100_010: //XOR EX CX RP (EX ^ CX -> RP)
                RP = aluXor(EX, CX);
                break;
            case 0b0001_1_11_110_100_011: //XOR EX DX RP (EX ^ DX -> RP)
                RP = aluXor(EX, DX);
                break;
            case 0b0001_1_11_110_100_100: //XOR EX EX RP (EX ^ EX -> RP)
                RP = aluXor(EX, EX);
                break;
            case 0b0001_1_11_110_100_101: //XOR EX SP RP (EX ^ SP -> RP)
                RP = aluXor(EX, SP);
                break;
            case 0b0001_1_11_110_100_110: //XOR EX RP RP (EX ^ RP -> RP)
                RP = aluXor(EX, RP);
                break;
            case 0b0001_1_11_110_100_111: //XOR EX PC RP (EX ^ PC -> RP)
                RP = aluXor(EX, PC);
                break;
            case 0b0001_1_11_111_100_000: //XOR EX AX PC (EX ^ AX -> PC)
                PC = aluXor(EX, AX);
                break;
            case 0b0001_1_11_111_100_001: //XOR EX BX PC (EX ^ BX -> PC)
                PC = aluXor(EX, BX);
                break;
            case 0b0001_1_11_111_100_010: //XOR EX CX PC (EX ^ CX -> PC)
                PC = aluXor(EX, CX);
                break;
            case 0b0001_1_11_111_100_011: //XOR EX DX PC (EX ^ DX -> PC)
                PC = aluXor(EX, DX);
                break;
            case 0b0001_1_11_111_100_100: //XOR EX EX PC (EX ^ EX -> PC)
                PC = aluXor(EX, EX);
                break;
            case 0b0001_1_11_111_100_101: //XOR EX SP PC (EX ^ SP -> PC)
                PC = aluXor(EX, SP);
                break;
            case 0b0001_1_11_111_100_110: //XOR EX RP PC (EX ^ RP -> PC)
                PC = aluXor(EX, RP);
                break;
            case 0b0001_1_11_111_100_111: //XOR EX PC PC (EX ^ PC -> PC)
                PC = aluXor(EX, PC);
                break;
            case 0b0001_1_11_000_101_000: //XOR SP AX AX (SP ^ AX -> AX)
                AX = aluXor(SP, AX);
                break;
            case 0b0001_1_11_000_101_001: //XOR SP BX AX (SP ^ BX -> AX)
                AX = aluXor(SP, BX);
                break;
            case 0b0001_1_11_000_101_010: //XOR SP CX AX (SP ^ CX -> AX)
                AX = aluXor(SP, CX);
                break;
            case 0b0001_1_11_000_101_011: //XOR SP DX AX (SP ^ DX -> AX)
                AX = aluXor(SP, DX);
                break;
            case 0b0001_1_11_000_101_100: //XOR SP EX AX (SP ^ EX -> AX)
                AX = aluXor(SP, EX);
                break;
            case 0b0001_1_11_000_101_101: //XOR SP SP AX (SP ^ SP -> AX)
                AX = aluXor(SP, SP);
                break;
            case 0b0001_1_11_000_101_110: //XOR SP RP AX (SP ^ RP -> AX)
                AX = aluXor(SP, RP);
                break;
            case 0b0001_1_11_000_101_111: //XOR SP PC AX (SP ^ PC -> AX)
                AX = aluXor(SP, PC);
                break;
            case 0b0001_1_11_001_101_000: //XOR SP AX BX (SP ^ AX -> BX)
                BX = aluXor(SP, AX);
                break;
            case 0b0001_1_11_001_101_001: //XOR SP BX BX (SP ^ BX -> BX)
                BX = aluXor(SP, BX);
                break;
            case 0b0001_1_11_001_101_010: //XOR SP CX BX (SP ^ CX -> BX)
                BX = aluXor(SP, CX);
                break;
            case 0b0001_1_11_001_101_011: //XOR SP DX BX (SP ^ DX -> BX)
                BX = aluXor(SP, DX);
                break;
            case 0b0001_1_11_001_101_100: //XOR SP EX BX (SP ^ EX -> BX)
                BX = aluXor(SP, EX);
                break;
            case 0b0001_1_11_001_101_101: //XOR SP SP BX (SP ^ SP -> BX)
                BX = aluXor(SP, SP);
                break;
            case 0b0001_1_11_001_101_110: //XOR SP RP BX (SP ^ RP -> BX)
                BX = aluXor(SP, RP);
                break;
            case 0b0001_1_11_001_101_111: //XOR SP PC BX (SP ^ PC -> BX)
                BX = aluXor(SP, PC);
                break;
            case 0b0001_1_11_010_101_000: //XOR SP AX CX (SP ^ AX -> CX)
                CX = aluXor(SP, AX);
                break;
            case 0b0001_1_11_010_101_001: //XOR SP BX CX (SP ^ BX -> CX)
                CX = aluXor(SP, BX);
                break;
            case 0b0001_1_11_010_101_010: //XOR SP CX CX (SP ^ CX -> CX)
                CX = aluXor(SP, CX);
                break;
            case 0b0001_1_11_010_101_011: //XOR SP DX CX (SP ^ DX -> CX)
                CX = aluXor(SP, DX);
                break;
            case 0b0001_1_11_010_101_100: //XOR SP EX CX (SP ^ EX -> CX)
                CX = aluXor(SP, EX);
                break;
            case 0b0001_1_11_010_101_101: //XOR SP SP CX (SP ^ SP -> CX)
                CX = aluXor(SP, SP);
                break;
            case 0b0001_1_11_010_101_110: //XOR SP RP CX (SP ^ RP -> CX)
                CX = aluXor(SP, RP);
                break;
            case 0b0001_1_11_010_101_111: //XOR SP PC CX (SP ^ PC -> CX)
                CX = aluXor(SP, PC);
                break;
            case 0b0001_1_11_011_101_000: //XOR SP AX DX (SP ^ AX -> DX)
                DX = aluXor(SP, AX);
                break;
            case 0b0001_1_11_011_101_001: //XOR SP BX DX (SP ^ BX -> DX)
                DX = aluXor(SP, BX);
                break;
            case 0b0001_1_11_011_101_010: //XOR SP CX DX (SP ^ CX -> DX)
                DX = aluXor(SP, CX);
                break;
            case 0b0001_1_11_011_101_011: //XOR SP DX DX (SP ^ DX -> DX)
                DX = aluXor(SP, DX);
                break;
            case 0b0001_1_11_011_101_100: //XOR SP EX DX (SP ^ EX -> DX)
                DX = aluXor(SP, EX);
                break;
            case 0b0001_1_11_011_101_101: //XOR SP SP DX (SP ^ SP -> DX)
                DX = aluXor(SP, SP);
                break;
            case 0b0001_1_11_011_101_110: //XOR SP RP DX (SP ^ RP -> DX)
                DX = aluXor(SP, RP);
                break;
            case 0b0001_1_11_011_101_111: //XOR SP PC DX (SP ^ PC -> DX)
                DX = aluXor(SP, PC);
                break;
            case 0b0001_1_11_100_101_000: //XOR SP AX EX (SP ^ AX -> EX)
                EX = aluXor(SP, AX);
                break;
            case 0b0001_1_11_100_101_001: //XOR SP EX SP (SP ^ BX -> SP)
                EX = aluXor(SP, BX);
                break;
            case 0b0001_1_11_100_101_010: //XOR SP EX SP (SP ^ CX -> SP)
                EX = aluXor(SP, CX);
                break;
            case 0b0001_1_11_100_101_011: //XOR SP EX SP (SP ^ DX -> SP)
                EX = aluXor(SP, DX);
                break;
            case 0b0001_1_11_100_101_100: //XOR SP EX SP (SP ^ EX -> SP)
                EX = aluXor(SP, EX);
                break;
            case 0b0001_1_11_100_101_101: //XOR SP EX SP (SP ^ SP -> SP)
                EX = aluXor(SP, SP);
                break;
            case 0b0001_1_11_100_101_110: //XOR SP EX SP (SP ^ RP -> SP)
                EX = aluXor(SP, RP);
                break;
            case 0b0001_1_11_100_101_111: //XOR SP EX SP (SP ^ PC -> SP)
                EX = aluXor(SP, PC);
                break;
            case 0b0001_1_11_101_101_000: //XOR SP AX SP (SP ^ AX -> SP)
                SP = aluXor(SP, AX);
                break;
            case 0b0001_1_11_101_101_001: //XOR SP BX SP (SP ^ BX -> SP)
                SP = aluXor(SP, BX);
                break;
            case 0b0001_1_11_101_101_010: //XOR SP CX SP (SP ^ CX -> SP)
                SP = aluXor(SP, CX);
                break;
            case 0b0001_1_11_101_101_011: //XOR SP DX SP (SP ^ DX -> SP)
                SP = aluXor(SP, DX);
                break;
            case 0b0001_1_11_101_101_100: //XOR SP EX SP (SP ^ EX -> SP)
                SP = aluXor(SP, EX);
                break;
            case 0b0001_1_11_101_101_101: //XOR SP SP SP (SP ^ SP -> SP)
                SP = aluXor(SP, SP);
                break;
            case 0b0001_1_11_101_101_110: //XOR SP RP SP (SP ^ RP -> SP)
                SP = aluXor(SP, RP);
                break;
            case 0b0001_1_11_101_101_111: //XOR SP PC SP (SP ^ PC -> SP)
                SP = aluXor(SP, PC);
                break;
            case 0b0001_1_11_110_101_000: //XOR SP AX RP (SP ^ AX -> RP)
                RP = aluXor(SP, AX);
                break;
            case 0b0001_1_11_110_101_001: //XOR SP BX RP (SP ^ BX -> RP)
                RP = aluXor(SP, BX);
                break;
            case 0b0001_1_11_110_101_010: //XOR SP CX RP (SP ^ CX -> RP)
                RP = aluXor(SP, CX);
                break;
            case 0b0001_1_11_110_101_011: //XOR SP DX RP (SP ^ DX -> RP)
                RP = aluXor(SP, DX);
                break;
            case 0b0001_1_11_110_101_100: //XOR SP EX RP (SP ^ EX -> RP)
                RP = aluXor(SP, EX);
                break;
            case 0b0001_1_11_110_101_101: //XOR SP SP RP (SP ^ SP -> RP)
                RP = aluXor(SP, SP);
                break;
            case 0b0001_1_11_110_101_110: //XOR SP RP RP (SP ^ RP -> RP)
                RP = aluXor(SP, RP);
                break;
            case 0b0001_1_11_110_101_111: //XOR SP PC RP (SP ^ PC -> RP)
                RP = aluXor(SP, PC);
                break;
            case 0b0001_1_11_111_101_000: //XOR SP AX PC (SP ^ AX -> PC)
                PC = aluXor(SP, AX);
                break;
            case 0b0001_1_11_111_101_001: //XOR SP BX PC (SP ^ BX -> PC)
                PC = aluXor(SP, BX);
                break;
            case 0b0001_1_11_111_101_010: //XOR SP CX PC (SP ^ CX -> PC)
                PC = aluXor(SP, CX);
                break;
            case 0b0001_1_11_111_101_011: //XOR SP DX PC (SP ^ DX -> PC)
                PC = aluXor(SP, DX);
                break;
            case 0b0001_1_11_111_101_100: //XOR SP EX PC (SP ^ EX -> PC)
                PC = aluXor(SP, EX);
                break;
            case 0b0001_1_11_111_101_101: //XOR SP SP PC (SP ^ SP -> PC)
                PC = aluXor(SP, SP);
                break;
            case 0b0001_1_11_111_101_110: //XOR SP RP PC (SP ^ RP -> PC)
                PC = aluXor(SP, RP);
                break;
            case 0b0001_1_11_111_101_111: //XOR SP PC PC (SP ^ PC -> PC)
                PC = aluXor(SP, PC);
                break;
            case 0b0001_1_11_000_110_000: //XOR RP AX AX (RP ^ AX -> AX)
                AX = aluXor(RP, AX);
                break;
            case 0b0001_1_11_000_110_001: //XOR RP BX AX (RP ^ BX -> AX)
                AX = aluXor(RP, BX);
                break;
            case 0b0001_1_11_000_110_010: //XOR RP CX AX (RP ^ CX -> AX)
                AX = aluXor(RP, CX);
                break;
            case 0b0001_1_11_000_110_011: //XOR RP DX AX (RP ^ DX -> AX)
                AX = aluXor(RP, DX);
                break;
            case 0b0001_1_11_000_110_100: //XOR RP EX AX (RP ^ EX -> AX)
                AX = aluXor(RP, EX);
                break;
            case 0b0001_1_11_000_110_101: //XOR RP SP AX (RP ^ SP -> AX)
                AX = aluXor(RP, SP);
                break;
            case 0b0001_1_11_000_110_110: //XOR RP RP AX (RP ^ RP -> AX)
                AX = aluXor(RP, RP);
                break;
            case 0b0001_1_11_000_110_111: //XOR RP PC AX (RP ^ PC -> AX)
                AX = aluXor(RP, PC);
                break;
            case 0b0001_1_11_001_110_000: //XOR RP AX BX (RP ^ AX -> BX)
                BX = aluXor(RP, AX);
                break;
            case 0b0001_1_11_001_110_001: //XOR RP BX BX (RP ^ BX -> BX)
                BX = aluXor(RP, BX);
                break;
            case 0b0001_1_11_001_110_010: //XOR RP CX BX (RP ^ CX -> BX)
                BX = aluXor(RP, CX);
                break;
            case 0b0001_1_11_001_110_011: //XOR RP DX BX (RP ^ DX -> BX)
                BX = aluXor(RP, DX);
                break;
            case 0b0001_1_11_001_110_100: //XOR RP EX BX (RP ^ EX -> BX)
                BX = aluXor(RP, EX);
                break;
            case 0b0001_1_11_001_110_101: //XOR RP SP BX (RP ^ SP -> BX)
                BX = aluXor(RP, SP);
                break;
            case 0b0001_1_11_001_110_110: //XOR RP RP BX (RP ^ RP -> BX)
                BX = aluXor(RP, RP);
                break;
            case 0b0001_1_11_001_110_111: //XOR RP PC BX (RP ^ PC -> BX)
                BX = aluXor(RP, PC);
                break;
            case 0b0001_1_11_010_110_000: //XOR RP AX CX (RP ^ AX -> CX)
                CX = aluXor(RP, AX);
                break;
            case 0b0001_1_11_010_110_001: //XOR RP BX CX (RP ^ BX -> CX)
                CX = aluXor(RP, BX);
                break;
            case 0b0001_1_11_010_110_010: //XOR RP CX CX (RP ^ CX -> CX)
                CX = aluXor(RP, CX);
                break;
            case 0b0001_1_11_010_110_011: //XOR RP DX CX (RP ^ DX -> CX)
                CX = aluXor(RP, DX);
                break;
            case 0b0001_1_11_010_110_100: //XOR RP EX CX (RP ^ EX -> CX)
                CX = aluXor(RP, EX);
                break;
            case 0b0001_1_11_010_110_101: //XOR RP SP CX (RP ^ SP -> CX)
                CX = aluXor(RP, SP);
                break;
            case 0b0001_1_11_010_110_110: //XOR RP RP CX (RP ^ RP -> CX)
                CX = aluXor(RP, RP);
                break;
            case 0b0001_1_11_010_110_111: //XOR RP PC CX (RP ^ PC -> CX)
                CX = aluXor(RP, PC);
                break;
            case 0b0001_1_11_011_110_000: //XOR RP AX DX (RP ^ AX -> DX)
                DX = aluXor(RP, AX);
                break;
            case 0b0001_1_11_011_110_001: //XOR RP BX DX (RP ^ BX -> DX)
                DX = aluXor(RP, BX);
                break;
            case 0b0001_1_11_011_110_010: //XOR RP CX DX (RP ^ CX -> DX)
                DX = aluXor(RP, CX);
                break;
            case 0b0001_1_11_011_110_011: //XOR RP DX DX (RP ^ DX -> DX)
                DX = aluXor(RP, DX);
                break;
            case 0b0001_1_11_011_110_100: //XOR RP EX DX (RP ^ EX -> DX)
                DX = aluXor(RP, EX);
                break;
            case 0b0001_1_11_011_110_101: //XOR RP SP DX (RP ^ SP -> DX)
                DX = aluXor(RP, SP);
                break;
            case 0b0001_1_11_011_110_110: //XOR RP RP DX (RP ^ RP -> DX)
                DX = aluXor(RP, RP);
                break;
            case 0b0001_1_11_011_110_111: //XOR RP PC DX (RP ^ PC -> DX)
                DX = aluXor(RP, PC);
                break;
            case 0b0001_1_11_100_110_000: //XOR RP AX EX (RP ^ AX -> EX)
                EX = aluXor(RP, AX);
                break;
            case 0b0001_1_11_100_110_001: //XOR RP EX SP (RP ^ BX -> SP)
                EX = aluXor(RP, BX);
                break;
            case 0b0001_1_11_100_110_010: //XOR RP EX SP (RP ^ CX -> SP)
                EX = aluXor(RP, CX);
                break;
            case 0b0001_1_11_100_110_011: //XOR RP EX SP (RP ^ DX -> SP)
                EX = aluXor(RP, DX);
                break;
            case 0b0001_1_11_100_110_100: //XOR RP EX SP (RP ^ EX -> SP)
                EX = aluXor(RP, EX);
                break;
            case 0b0001_1_11_100_110_101: //XOR RP EX SP (RP ^ SP -> SP)
                EX = aluXor(RP, SP);
                break;
            case 0b0001_1_11_100_110_110: //XOR RP EX SP (RP ^ RP -> SP)
                EX = aluXor(RP, RP);
                break;
            case 0b0001_1_11_100_110_111: //XOR RP EX SP (RP ^ PC -> SP)
                EX = aluXor(RP, PC);
                break;
            case 0b0001_1_11_101_110_000: //XOR RP AX SP (RP ^ AX -> SP)
                SP = aluXor(RP, AX);
                break;
            case 0b0001_1_11_101_110_001: //XOR RP BX SP (RP ^ BX -> SP)
                SP = aluXor(RP, BX);
                break;
            case 0b0001_1_11_101_110_010: //XOR RP CX SP (RP ^ CX -> SP)
                SP = aluXor(RP, CX);
                break;
            case 0b0001_1_11_101_110_011: //XOR RP DX SP (RP ^ DX -> SP)
                SP = aluXor(RP, DX);
                break;
            case 0b0001_1_11_101_110_100: //XOR RP EX SP (RP ^ EX -> SP)
                SP = aluXor(RP, EX);
                break;
            case 0b0001_1_11_101_110_101: //XOR RP SP SP (RP ^ SP -> SP)
                SP = aluXor(RP, SP);
                break;
            case 0b0001_1_11_101_110_110: //XOR RP RP SP (RP ^ RP -> SP)
                SP = aluXor(RP, RP);
                break;
            case 0b0001_1_11_101_110_111: //XOR RP PC SP (RP ^ PC -> SP)
                SP = aluXor(RP, PC);
                break;
            case 0b0001_1_11_110_110_000: //XOR RP AX RP (RP ^ AX -> RP)
                RP = aluXor(RP, AX);
                break;
            case 0b0001_1_11_110_110_001: //XOR RP BX RP (RP ^ BX -> RP)
                RP = aluXor(RP, BX);
                break;
            case 0b0001_1_11_110_110_010: //XOR RP CX RP (RP ^ CX -> RP)
                RP = aluXor(RP, CX);
                break;
            case 0b0001_1_11_110_110_011: //XOR RP DX RP (RP ^ DX -> RP)
                RP = aluXor(RP, DX);
                break;
            case 0b0001_1_11_110_110_100: //XOR RP EX RP (RP ^ EX -> RP)
                RP = aluXor(RP, EX);
                break;
            case 0b0001_1_11_110_110_101: //XOR RP SP RP (RP ^ SP -> RP)
                RP = aluXor(RP, SP);
                break;
            case 0b0001_1_11_110_110_110: //XOR RP RP RP (RP ^ RP -> RP)
                RP = aluXor(RP, RP);
                break;
            case 0b0001_1_11_110_110_111: //XOR RP PC RP (RP ^ PC -> RP)
                RP = aluXor(RP, PC);
                break;
            case 0b0001_1_11_111_110_000: //XOR RP AX PC (RP ^ AX -> PC)
                PC = aluXor(RP, AX);
                break;
            case 0b0001_1_11_111_110_001: //XOR RP BX PC (RP ^ BX -> PC)
                PC = aluXor(RP, BX);
                break;
            case 0b0001_1_11_111_110_010: //XOR RP CX PC (RP ^ CX -> PC)
                PC = aluXor(RP, CX);
                break;
            case 0b0001_1_11_111_110_011: //XOR RP DX PC (RP ^ DX -> PC)
                PC = aluXor(RP, DX);
                break;
            case 0b0001_1_11_111_110_100: //XOR RP EX PC (RP ^ EX -> PC)
                PC = aluXor(RP, EX);
                break;
            case 0b0001_1_11_111_110_101: //XOR RP SP PC (RP ^ SP -> PC)
                PC = aluXor(RP, SP);
                break;
            case 0b0001_1_11_111_110_110: //XOR RP RP PC (RP ^ RP -> PC)
                PC = aluXor(RP, RP);
                break;
            case 0b0001_1_11_111_110_111: //XOR RP PC PC (RP ^ PC -> PC)
                PC = aluXor(RP, PC);
                break;
            case 0b0001_1_11_000_111_000: //XOR PC AX AX (PC ^ AX -> AX)
                AX = aluXor(PC, AX);
                break;
            case 0b0001_1_11_000_111_001: //XOR PC BX AX (PC ^ BX -> AX)
                AX = aluXor(PC, BX);
                break;
            case 0b0001_1_11_000_111_010: //XOR PC CX AX (PC ^ CX -> AX)
                AX = aluXor(PC, CX);
                break;
            case 0b0001_1_11_000_111_011: //XOR PC DX AX (PC ^ DX -> AX)
                AX = aluXor(PC, DX);
                break;
            case 0b0001_1_11_000_111_100: //XOR PC EX AX (PC ^ EX -> AX)
                AX = aluXor(PC, EX);
                break;
            case 0b0001_1_11_000_111_101: //XOR PC SP AX (PC ^ SP -> AX)
                AX = aluXor(PC, SP);
                break;
            case 0b0001_1_11_000_111_110: //XOR PC RP AX (PC ^ RP -> AX)
                AX = aluXor(PC, RP);
                break;
            case 0b0001_1_11_000_111_111: //XOR PC PC AX (PC ^ PC -> AX)
                AX = aluXor(PC, PC);
                break;
            case 0b0001_1_11_001_111_000: //XOR PC AX BX (PC ^ AX -> BX)
                BX = aluXor(PC, AX);
                break;
            case 0b0001_1_11_001_111_001: //XOR PC BX BX (PC ^ BX -> BX)
                BX = aluXor(PC, BX);
                break;
            case 0b0001_1_11_001_111_010: //XOR PC CX BX (PC ^ CX -> BX)
                BX = aluXor(PC, CX);
                break;
            case 0b0001_1_11_001_111_011: //XOR PC DX BX (PC ^ DX -> BX)
                BX = aluXor(PC, DX);
                break;
            case 0b0001_1_11_001_111_100: //XOR PC EX BX (PC ^ EX -> BX)
                BX = aluXor(PC, EX);
                break;
            case 0b0001_1_11_001_111_101: //XOR PC SP BX (PC ^ SP -> BX)
                BX = aluXor(PC, SP);
                break;
            case 0b0001_1_11_001_111_110: //XOR PC RP BX (PC ^ RP -> BX)
                BX = aluXor(PC, RP);
                break;
            case 0b0001_1_11_001_111_111: //XOR PC PC BX (PC ^ PC -> BX)
                BX = aluXor(PC, PC);
                break;
            case 0b0001_1_11_010_111_000: //XOR PC AX CX (PC ^ AX -> CX)
                CX = aluXor(PC, AX);
                break;
            case 0b0001_1_11_010_111_001: //XOR PC BX CX (PC ^ BX -> CX)
                CX = aluXor(PC, BX);
                break;
            case 0b0001_1_11_010_111_010: //XOR PC CX CX (PC ^ CX -> CX)
                CX = aluXor(PC, CX);
                break;
            case 0b0001_1_11_010_111_011: //XOR PC DX CX (PC ^ DX -> CX)
                CX = aluXor(PC, DX);
                break;
            case 0b0001_1_11_010_111_100: //XOR PC EX CX (PC ^ EX -> CX)
                CX = aluXor(PC, EX);
                break;
            case 0b0001_1_11_010_111_101: //XOR PC SP CX (PC ^ SP -> CX)
                CX = aluXor(PC, SP);
                break;
            case 0b0001_1_11_010_111_110: //XOR PC RP CX (PC ^ RP -> CX)
                CX = aluXor(PC, RP);
                break;
            case 0b0001_1_11_010_111_111: //XOR PC PC CX (PC ^ PC -> CX)
                CX = aluXor(PC, PC);
                break;
            case 0b0001_1_11_011_111_000: //XOR PC AX DX (PC ^ AX -> DX)
                DX = aluXor(PC, AX);
                break;
            case 0b0001_1_11_011_111_001: //XOR PC BX DX (PC ^ BX -> DX)
                DX = aluXor(PC, BX);
                break;
            case 0b0001_1_11_011_111_010: //XOR PC CX DX (PC ^ CX -> DX)
                DX = aluXor(PC, CX);
                break;
            case 0b0001_1_11_011_111_011: //XOR PC DX DX (PC ^ DX -> DX)
                DX = aluXor(PC, DX);
                break;
            case 0b0001_1_11_011_111_100: //XOR PC EX DX (PC ^ EX -> DX)
                DX = aluXor(PC, EX);
                break;
            case 0b0001_1_11_011_111_101: //XOR PC SP DX (PC ^ SP -> DX)
                DX = aluXor(PC, SP);
                break;
            case 0b0001_1_11_011_111_110: //XOR PC RP DX (PC ^ RP -> DX)
                DX = aluXor(PC, RP);
                break;
            case 0b0001_1_11_011_111_111: //XOR PC PC DX (PC ^ PC -> DX)
                DX = aluXor(PC, PC);
                break;
            case 0b0001_1_11_100_111_000: //XOR PC AX EX (PC ^ AX -> EX)
                EX = aluXor(PC, AX);
                break;
            case 0b0001_1_11_100_111_001: //XOR PC EX SP (PC ^ BX -> SP)
                EX = aluXor(PC, BX);
                break;
            case 0b0001_1_11_100_111_010: //XOR PC EX SP (PC ^ CX -> SP)
                EX = aluXor(PC, CX);
                break;
            case 0b0001_1_11_100_111_011: //XOR PC EX SP (PC ^ DX -> SP)
                EX = aluXor(PC, DX);
                break;
            case 0b0001_1_11_100_111_100: //XOR PC EX SP (PC ^ EX -> SP)
                EX = aluXor(PC, EX);
                break;
            case 0b0001_1_11_100_111_101: //XOR PC EX SP (PC ^ SP -> SP)
                EX = aluXor(PC, SP);
                break;
            case 0b0001_1_11_100_111_110: //XOR PC EX SP (PC ^ RP -> SP)
                EX = aluXor(PC, RP);
                break;
            case 0b0001_1_11_100_111_111: //XOR PC EX SP (PC ^ PC -> SP)
                EX = aluXor(PC, PC);
                break;
            case 0b0001_1_11_101_111_000: //XOR PC AX SP (PC ^ AX -> SP)
                SP = aluXor(PC, AX);
                break;
            case 0b0001_1_11_101_111_001: //XOR PC BX SP (PC ^ BX -> SP)
                SP = aluXor(PC, BX);
                break;
            case 0b0001_1_11_101_111_010: //XOR PC CX SP (PC ^ CX -> SP)
                SP = aluXor(PC, CX);
                break;
            case 0b0001_1_11_101_111_011: //XOR PC DX SP (PC ^ DX -> SP)
                SP = aluXor(PC, DX);
                break;
            case 0b0001_1_11_101_111_100: //XOR PC EX SP (PC ^ EX -> SP)
                SP = aluXor(PC, EX);
                break;
            case 0b0001_1_11_101_111_101: //XOR PC SP SP (PC ^ SP -> SP)
                SP = aluXor(PC, SP);
                break;
            case 0b0001_1_11_101_111_110: //XOR PC RP SP (PC ^ RP -> SP)
                SP = aluXor(PC, RP);
                break;
            case 0b0001_1_11_101_111_111: //XOR PC PC SP (PC ^ PC -> SP)
                SP = aluXor(PC, PC);
                break;
            case 0b0001_1_11_110_111_000: //XOR PC AX RP (PC ^ AX -> RP)
                RP = aluXor(PC, AX);
                break;
            case 0b0001_1_11_110_111_001: //XOR PC BX RP (PC ^ BX -> RP)
                RP = aluXor(PC, BX);
                break;
            case 0b0001_1_11_110_111_010: //XOR PC CX RP (PC ^ CX -> RP)
                RP = aluXor(PC, CX);
                break;
            case 0b0001_1_11_110_111_011: //XOR PC DX RP (PC ^ DX -> RP)
                RP = aluXor(PC, DX);
                break;
            case 0b0001_1_11_110_111_100: //XOR PC EX RP (PC ^ EX -> RP)
                RP = aluXor(PC, EX);
                break;
            case 0b0001_1_11_110_111_101: //XOR PC SP RP (PC ^ SP -> RP)
                RP = aluXor(PC, SP);
                break;
            case 0b0001_1_11_110_111_110: //XOR PC RP RP (PC ^ RP -> RP)
                RP = aluXor(PC, RP);
                break;
            case 0b0001_1_11_110_111_111: //XOR PC PC RP (PC ^ PC -> RP)
                RP = aluXor(PC, PC);
                break;
            case 0b0001_1_11_111_111_000: //XOR PC AX PC (PC ^ AX -> PC)
                PC = aluXor(PC, AX);
                break;
            case 0b0001_1_11_111_111_001: //XOR PC BX PC (PC ^ BX -> PC)
                PC = aluXor(PC, BX);
                break;
            case 0b0001_1_11_111_111_010: //XOR PC CX PC (PC ^ CX -> PC)
                PC = aluXor(PC, CX);
                break;
            case 0b0001_1_11_111_111_011: //XOR PC DX PC (PC ^ DX -> PC)
                PC = aluXor(PC, DX);
                break;
            case 0b0001_1_11_111_111_100: //XOR PC EX PC (PC ^ EX -> PC)
                PC = aluXor(PC, EX);
                break;
            case 0b0001_1_11_111_111_101: //XOR PC SP PC (PC ^ SP -> PC)
                PC = aluXor(PC, SP);
                break;
            case 0b0001_1_11_111_111_110: //XOR PC RP PC (PC ^ RP -> PC)
                PC = aluXor(PC, RP);
                break;
            case 0b0001_1_11_111_111_111: //XOR PC PC PC (PC ^ PC -> PC)
                PC = aluXor(PC, PC);
                break;

            default:
                unknownInstruction(INST);
                break;

        }
    }


    /*
     * Compare/Subtract: Subtract two registers, storing into the destination register.
	 * This is useful for comparing values and updating condition registers
     */
    private void executeCMPSUBInstruction() {
        switch (INST) {
            case 0b0001_010_000_000_000: //CMP/SUB AX AX AX (AX - AX -> AX)
                AX = compareSubtract(AX, AX);
                break;
            case 0b0001_010_000_000_001: //CMP/SUB AX BX AX (AX - BX -> AX)
                AX = compareSubtract(AX, BX);
                break;
            case 0b0001_010_000_000_010: //CMP/SUB AX CX AX (AX - CX -> AX)
                AX = compareSubtract(AX, CX);
                break;
            case 0b0001_010_000_000_011: //CMP/SUB AX DX AX (AX - DX -> AX)
                AX = compareSubtract(AX, DX);
                break;
            case 0b0001_010_000_000_100: //CMP/SUB AX EX AX (AX - EX -> AX)
                AX = compareSubtract(AX, EX);
                break;
            case 0b0001_010_000_000_101: //CMP/SUB AX SP AX (AX - SP -> AX)
                AX = compareSubtract(AX, SP);
                break;
            case 0b0001_010_000_000_110: //CMP/SUB AX RP AX (AX - RP -> AX)
                AX = compareSubtract(AX, RP);
                break;
            case 0b0001_010_000_000_111: //CMP/SUB AX PC AX (AX - PC -> AX)
                AX = compareSubtract(AX, PC);
                break;
            case 0b0001_010_001_000_000: //CMP/SUB AX AX BX (AX - AX -> BX)
                BX = compareSubtract(AX, AX);
                break;
            case 0b0001_010_001_000_001: //CMP/SUB AX BX BX (AX - BX -> BX)
                BX = compareSubtract(AX, BX);
                break;
            case 0b0001_010_001_000_010: //CMP/SUB AX CX BX (AX - CX -> BX)
                BX = compareSubtract(AX, CX);
                break;
            case 0b0001_010_001_000_011: //CMP/SUB AX DX BX (AX - DX -> BX)
                BX = compareSubtract(AX, DX);
                break;
            case 0b0001_010_001_000_100: //CMP/SUB AX EX BX (AX - EX -> BX)
                BX = compareSubtract(AX, EX);
                break;
            case 0b0001_010_001_000_101: //CMP/SUB AX SP BX (AX - SP -> BX)
                BX = compareSubtract(AX, SP);
                break;
            case 0b0001_010_001_000_110: //CMP/SUB AX RP BX (AX - RP -> BX)
                BX = compareSubtract(AX, RP);
                break;
            case 0b0001_010_001_000_111: //CMP/SUB AX PC BX (AX - PC -> BX)
                BX = compareSubtract(AX, PC);
                break;
            case 0b0001_010_010_000_000: //CMP/SUB AX AX CX (AX - AX -> CX)
                CX = compareSubtract(AX, AX);
                break;
            case 0b0001_010_010_000_001: //CMP/SUB AX BX CX (AX - BX -> CX)
                CX = compareSubtract(AX, BX);
                break;
            case 0b0001_010_010_000_010: //CMP/SUB AX CX CX (AX - CX -> CX)
                CX = compareSubtract(AX, CX);
                break;
            case 0b0001_010_010_000_011: //CMP/SUB AX DX CX (AX - DX -> CX)
                CX = compareSubtract(AX, DX);
                break;
            case 0b0001_010_010_000_100: //CMP/SUB AX EX CX (AX - EX -> CX)
                CX = compareSubtract(AX, EX);
                break;
            case 0b0001_010_010_000_101: //CMP/SUB AX SP CX (AX - SP -> CX)
                CX = compareSubtract(AX, SP);
                break;
            case 0b0001_010_010_000_110: //CMP/SUB AX RP CX (AX - RP -> CX)
                CX = compareSubtract(AX, RP);
                break;
            case 0b0001_010_010_000_111: //CMP/SUB AX PC CX (AX - PC -> CX)
                CX = compareSubtract(AX, PC);
                break;
            case 0b0001_010_011_000_000: //CMP/SUB AX AX DX (AX - AX -> DX)
                DX = compareSubtract(AX, AX);
                break;
            case 0b0001_010_011_000_001: //CMP/SUB AX BX DX (AX - BX -> DX)
                DX = compareSubtract(AX, BX);
                break;
            case 0b0001_010_011_000_010: //CMP/SUB AX CX DX (AX - CX -> DX)
                DX = compareSubtract(AX, CX);
                break;
            case 0b0001_010_011_000_011: //CMP/SUB AX DX DX (AX - DX -> DX)
                DX = compareSubtract(AX, DX);
                break;
            case 0b0001_010_011_000_100: //CMP/SUB AX EX DX (AX - EX -> DX)
                DX = compareSubtract(AX, EX);
                break;
            case 0b0001_010_011_000_101: //CMP/SUB AX SP DX (AX - SP -> DX)
                DX = compareSubtract(AX, SP);
                break;
            case 0b0001_010_011_000_110: //CMP/SUB AX RP DX (AX - RP -> DX)
                DX = compareSubtract(AX, RP);
                break;
            case 0b0001_010_011_000_111: //CMP/SUB AX PC DX (AX - PC -> DX)
                DX = compareSubtract(AX, PC);
                break;
            case 0b0001_010_100_000_000: //CMP/SUB AX AX EX (AX - AX -> EX)
                EX = compareSubtract(AX, AX);
                break;
            case 0b0001_010_100_000_001: //CMP/SUB AX EX SP (AX - BX -EXSP)
                EX = compareSubtract(AX, BX);
                break;
            case 0b0001_010_100_000_010: //CMP/SUB AX EX SP (AX - CX -EXSP)
                EX = compareSubtract(AX, CX);
                break;
            case 0b0001_010_100_000_011: //CMP/SUB AX EX SP (AX - DX -EXSP)
                EX = compareSubtract(AX, DX);
                break;
            case 0b0001_010_100_000_100: //CMP/SUB AX EX SP (AX - EX -EXSP)
                EX = compareSubtract(AX, EX);
                break;
            case 0b0001_010_100_000_101: //CMP/SUB AX EX SP (AX - SP -EXSP)
                EX = compareSubtract(AX, SP);
                break;
            case 0b0001_010_100_000_110: //CMP/SUB AX EX SP (AX - RP -EXSP)
                EX = compareSubtract(AX, RP);
                break;
            case 0b0001_010_100_000_111: //CMP/SUB AX EX SP (AX - PC -EXSP)
                EX = compareSubtract(AX, PC);
                break;
            case 0b0001_010_101_000_000: //CMP/SUB AX AX SP (AX - AX -> SP)
                SP = compareSubtract(AX, AX);
                break;
            case 0b0001_010_101_000_001: //CMP/SUB AX BX SP (AX - BX -> SP)
                SP = compareSubtract(AX, BX);
                break;
            case 0b0001_010_101_000_010: //CMP/SUB AX CX SP (AX - CX -> SP)
                SP = compareSubtract(AX, CX);
                break;
            case 0b0001_010_101_000_011: //CMP/SUB AX DX SP (AX - DX -> SP)
                SP = compareSubtract(AX, DX);
                break;
            case 0b0001_010_101_000_100: //CMP/SUB AX EX SP (AX - EX -> SP)
                SP = compareSubtract(AX, EX);
                break;
            case 0b0001_010_101_000_101: //CMP/SUB AX SP SP (AX - SP -> SP)
                SP = compareSubtract(AX, SP);
                break;
            case 0b0001_010_101_000_110: //CMP/SUB AX RP SP (AX - RP -> SP)
                SP = compareSubtract(AX, RP);
                break;
            case 0b0001_010_101_000_111: //CMP/SUB AX PC SP (AX - PC -> SP)
                SP = compareSubtract(AX, PC);
                break;
            case 0b0001_010_110_000_000: //CMP/SUB AX AX RP (AX - AX -> RP)
                RP = compareSubtract(AX, AX);
                break;
            case 0b0001_010_110_000_001: //CMP/SUB AX BX RP (AX - BX -> RP)
                RP = compareSubtract(AX, BX);
                break;
            case 0b0001_010_110_000_010: //CMP/SUB AX CX RP (AX - CX -> RP)
                RP = compareSubtract(AX, CX);
                break;
            case 0b0001_010_110_000_011: //CMP/SUB AX DX RP (AX - DX -> RP)
                RP = compareSubtract(AX, DX);
                break;
            case 0b0001_010_110_000_100: //CMP/SUB AX EX RP (AX - EX -> RP)
                RP = compareSubtract(AX, EX);
                break;
            case 0b0001_010_110_000_101: //CMP/SUB AX SP RP (AX - SP -> RP)
                RP = compareSubtract(AX, SP);
                break;
            case 0b0001_010_110_000_110: //CMP/SUB AX RP RP (AX - RP -> RP)
                RP = compareSubtract(AX, RP);
                break;
            case 0b0001_010_110_000_111: //CMP/SUB AX PC RP (AX - PC -> RP)
                RP = compareSubtract(AX, PC);
                break;
            case 0b0001_010_111_000_000: //CMP/SUB AX AX PC (AX - AX -> PC)
                PC = compareSubtract(AX, AX);
                break;
            case 0b0001_010_111_000_001: //CMP/SUB AX BX PC (AX - BX -> PC)
                PC = compareSubtract(AX, BX);
                break;
            case 0b0001_010_111_000_010: //CMP/SUB AX CX PC (AX - CX -> PC)
                PC = compareSubtract(AX, CX);
                break;
            case 0b0001_010_111_000_011: //CMP/SUB AX DX PC (AX - DX -> PC)
                PC = compareSubtract(AX, DX);
                break;
            case 0b0001_010_111_000_100: //CMP/SUB AX EX PC (AX - EX -> PC)
                PC = compareSubtract(AX, EX);
                break;
            case 0b0001_010_111_000_101: //CMP/SUB AX SP PC (AX - SP -> PC)
                PC = compareSubtract(AX, SP);
                break;
            case 0b0001_010_111_000_110: //CMP/SUB AX RP PC (AX - RP -> PC)
                PC = compareSubtract(AX, RP);
                break;
            case 0b0001_010_111_000_111: //CMP/SUB AX PC PC (AX - PC -> PC)
                PC = compareSubtract(AX, PC);
                break;
            case 0b0001_010_000_001_000: //CMP/SUB BX AX AX (BX - AX -> AX)
                AX = compareSubtract(BX, AX);
                break;
            case 0b0001_010_000_001_001: //CMP/SUB BX BX AX (BX - BX -> AX)
                AX = compareSubtract(BX, BX);
                break;
            case 0b0001_010_000_001_010: //CMP/SUB BX CX AX (BX - CX -> AX)
                AX = compareSubtract(BX, CX);
                break;
            case 0b0001_010_000_001_011: //CMP/SUB BX DX AX (BX - DX -> AX)
                AX = compareSubtract(BX, DX);
                break;
            case 0b0001_010_000_001_100: //CMP/SUB BX EX AX (BX - EX -> AX)
                AX = compareSubtract(BX, EX);
                break;
            case 0b0001_010_000_001_101: //CMP/SUB BX SP AX (BX - SP -> AX)
                AX = compareSubtract(BX, SP);
                break;
            case 0b0001_010_000_001_110: //CMP/SUB BX RP AX (BX - RP -> AX)
                AX = compareSubtract(BX, RP);
                break;
            case 0b0001_010_000_001_111: //CMP/SUB BX PC AX (BX - PC -> AX)
                AX = compareSubtract(BX, PC);
                break;
            case 0b0001_010_001_001_000: //CMP/SUB BX AX BX (BX - AX -> BX)
                BX = compareSubtract(BX, AX);
                break;
            case 0b0001_010_001_001_001: //CMP/SUB BX BX BX (BX - BX -> BX)
                BX = compareSubtract(BX, BX);
                break;
            case 0b0001_010_001_001_010: //CMP/SUB BX CX BX (BX - CX -> BX)
                BX = compareSubtract(BX, CX);
                break;
            case 0b0001_010_001_001_011: //CMP/SUB BX DX BX (BX - DX -> BX)
                BX = compareSubtract(BX, DX);
                break;
            case 0b0001_010_001_001_100: //CMP/SUB BX EX BX (BX - EX -> BX)
                BX = compareSubtract(BX, EX);
                break;
            case 0b0001_010_001_001_101: //CMP/SUB BX SP BX (BX - SP -> BX)
                BX = compareSubtract(BX, SP);
                break;
            case 0b0001_010_001_001_110: //CMP/SUB BX RP BX (BX - RP -> BX)
                BX = compareSubtract(BX, RP);
                break;
            case 0b0001_010_001_001_111: //CMP/SUB BX PC BX (BX - PC -> BX)
                BX = compareSubtract(BX, PC);
                break;
            case 0b0001_010_010_001_000: //CMP/SUB BX AX CX (BX - AX -> CX)
                CX = compareSubtract(BX, AX);
                break;
            case 0b0001_010_010_001_001: //CMP/SUB BX BX CX (BX - BX -> CX)
                CX = compareSubtract(BX, BX);
                break;
            case 0b0001_010_010_001_010: //CMP/SUB BX CX CX (BX - CX -> CX)
                CX = compareSubtract(BX, CX);
                break;
            case 0b0001_010_010_001_011: //CMP/SUB BX DX CX (BX - DX -> CX)
                CX = compareSubtract(BX, DX);
                break;
            case 0b0001_010_010_001_100: //CMP/SUB BX EX CX (BX - EX -> CX)
                CX = compareSubtract(BX, EX);
                break;
            case 0b0001_010_010_001_101: //CMP/SUB BX SP CX (BX - SP -> CX)
                CX = compareSubtract(BX, SP);
                break;
            case 0b0001_010_010_001_110: //CMP/SUB BX RP CX (BX - RP -> CX)
                CX = compareSubtract(BX, RP);
                break;
            case 0b0001_010_010_001_111: //CMP/SUB BX PC CX (BX - PC -> CX)
                CX = compareSubtract(BX, PC);
                break;
            case 0b0001_010_011_001_000: //CMP/SUB BX AX DX (BX - AX -> DX)
                DX = compareSubtract(BX, AX);
                break;
            case 0b0001_010_011_001_001: //CMP/SUB BX BX DX (BX - BX -> DX)
                DX = compareSubtract(BX, BX);
                break;
            case 0b0001_010_011_001_010: //CMP/SUB BX CX DX (BX - CX -> DX)
                DX = compareSubtract(BX, CX);
                break;
            case 0b0001_010_011_001_011: //CMP/SUB BX DX DX (BX - DX -> DX)
                DX = compareSubtract(BX, DX);
                break;
            case 0b0001_010_011_001_100: //CMP/SUB BX EX DX (BX - EX -> DX)
                DX = compareSubtract(BX, EX);
                break;
            case 0b0001_010_011_001_101: //CMP/SUB BX SP DX (BX - SP -> DX)
                DX = compareSubtract(BX, SP);
                break;
            case 0b0001_010_011_001_110: //CMP/SUB BX RP DX (BX - RP -> DX)
                DX = compareSubtract(BX, RP);
                break;
            case 0b0001_010_011_001_111: //CMP/SUB BX PC DX (BX - PC -> DX)
                DX = compareSubtract(BX, PC);
                break;
            case 0b0001_010_100_001_000: //CMP/SUB BX AX EX (BX - AX -> EX)
                EX = compareSubtract(BX, AX);
                break;
            case 0b0001_010_100_001_001: //CMP/SUB BX EX SP (BX - BX -> SP)
                EX = compareSubtract(BX, BX);
                break;
            case 0b0001_010_100_001_010: //CMP/SUB BX EX SP (BX - CX -> SP)
                EX = compareSubtract(BX, CX);
                break;
            case 0b0001_010_100_001_011: //CMP/SUB BX EX SP (BX - DX -> SP)
                EX = compareSubtract(BX, DX);
                break;
            case 0b0001_010_100_001_100: //CMP/SUB BX EX SP (BX - EX -> SP)
                EX = compareSubtract(BX, EX);
                break;
            case 0b0001_010_100_001_101: //CMP/SUB BX EX SP (BX - SP -> SP)
                EX = compareSubtract(BX, SP);
                break;
            case 0b0001_010_100_001_110: //CMP/SUB BX EX SP (BX - RP -> SP)
                EX = compareSubtract(BX, RP);
                break;
            case 0b0001_010_100_001_111: //CMP/SUB BX EX SP (BX - PC -> SP)
                EX = compareSubtract(BX, PC);
                break;
            case 0b0001_010_101_001_000: //CMP/SUB BX AX SP (BX - AX -> SP)
                SP = compareSubtract(BX, AX);
                break;
            case 0b0001_010_101_001_001: //CMP/SUB BX BX SP (BX - BX -> SP)
                SP = compareSubtract(BX, BX);
                break;
            case 0b0001_010_101_001_010: //CMP/SUB BX CX SP (BX - CX -> SP)
                SP = compareSubtract(BX, CX);
                break;
            case 0b0001_010_101_001_011: //CMP/SUB BX DX SP (BX - DX -> SP)
                SP = compareSubtract(BX, DX);
                break;
            case 0b0001_010_101_001_100: //CMP/SUB BX EX SP (BX - EX -> SP)
                SP = compareSubtract(BX, EX);
                break;
            case 0b0001_010_101_001_101: //CMP/SUB BX SP SP (BX - SP -> SP)
                SP = compareSubtract(BX, SP);
                break;
            case 0b0001_010_101_001_110: //CMP/SUB BX RP SP (BX - RP -> SP)
                SP = compareSubtract(BX, RP);
                break;
            case 0b0001_010_101_001_111: //CMP/SUB BX PC SP (BX - PC -> SP)
                SP = compareSubtract(BX, PC);
                break;
            case 0b0001_010_110_001_000: //CMP/SUB BX AX RP (BX - AX -> RP)
                RP = compareSubtract(BX, AX);
                break;
            case 0b0001_010_110_001_001: //CMP/SUB BX BX RP (BX - BX -> RP)
                RP = compareSubtract(BX, BX);
                break;
            case 0b0001_010_110_001_010: //CMP/SUB BX CX RP (BX - CX -> RP)
                RP = compareSubtract(BX, CX);
                break;
            case 0b0001_010_110_001_011: //CMP/SUB BX DX RP (BX - DX -> RP)
                RP = compareSubtract(BX, DX);
                break;
            case 0b0001_010_110_001_100: //CMP/SUB BX EX RP (BX - EX -> RP)
                RP = compareSubtract(BX, EX);
                break;
            case 0b0001_010_110_001_101: //CMP/SUB BX SP RP (BX - SP -> RP)
                RP = compareSubtract(BX, SP);
                break;
            case 0b0001_010_110_001_110: //CMP/SUB BX RP RP (BX - RP -> RP)
                RP = compareSubtract(BX, RP);
                break;
            case 0b0001_010_110_001_111: //CMP/SUB BX PC RP (BX - PC -> RP)
                RP = compareSubtract(BX, PC);
                break;
            case 0b0001_010_111_001_000: //CMP/SUB BX AX PC (BX - AX -> PC)
                PC = compareSubtract(BX, AX);
                break;
            case 0b0001_010_111_001_001: //CMP/SUB BX BX PC (BX - BX -> PC)
                PC = compareSubtract(BX, BX);
                break;
            case 0b0001_010_111_001_010: //CMP/SUB BX CX PC (BX - CX -> PC)
                PC = compareSubtract(BX, CX);
                break;
            case 0b0001_010_111_001_011: //CMP/SUB BX DX PC (BX - DX -> PC)
                PC = compareSubtract(BX, DX);
                break;
            case 0b0001_010_111_001_100: //CMP/SUB BX EX PC (BX - EX -> PC)
                PC = compareSubtract(BX, EX);
                break;
            case 0b0001_010_111_001_101: //CMP/SUB BX SP PC (BX - SP -> PC)
                PC = compareSubtract(BX, SP);
                break;
            case 0b0001_010_111_001_110: //CMP/SUB BX RP PC (BX - RP -> PC)
                PC = compareSubtract(BX, RP);
                break;
            case 0b0001_010_111_001_111: //CMP/SUB BX PC PC (BX - PC -> PC)
                PC = compareSubtract(BX, PC);
                break;
            case 0b0001_010_000_010_000: //CMP/SUB CX AX AX (CX - AX -> AX)
                AX = compareSubtract(CX, AX);
                break;
            case 0b0001_010_000_010_001: //CMP/SUB CX BX AX (CX - BX -> AX)
                AX = compareSubtract(CX, BX);
                break;
            case 0b0001_010_000_010_010: //CMP/SUB CX CX AX (CX - CX -> AX)
                AX = compareSubtract(CX, CX);
                break;
            case 0b0001_010_000_010_011: //CMP/SUB CX DX AX (CX - DX -> AX)
                AX = compareSubtract(CX, DX);
                break;
            case 0b0001_010_000_010_100: //CMP/SUB CX EX AX (CX - EX -> AX)
                AX = compareSubtract(CX, EX);
                break;
            case 0b0001_010_000_010_101: //CMP/SUB CX SP AX (CX - SP -> AX)
                AX = compareSubtract(CX, SP);
                break;
            case 0b0001_010_000_010_110: //CMP/SUB CX RP AX (CX - RP -> AX)
                AX = compareSubtract(CX, RP);
                break;
            case 0b0001_010_000_010_111: //CMP/SUB CX PC AX (CX - PC -> AX)
                AX = compareSubtract(CX, PC);
                break;
            case 0b0001_010_001_010_000: //CMP/SUB CX AX BX (CX - AX -> BX)
                BX = compareSubtract(CX, AX);
                break;
            case 0b0001_010_001_010_001: //CMP/SUB CX BX BX (CX - BX -> BX)
                BX = compareSubtract(CX, BX);
                break;
            case 0b0001_010_001_010_010: //CMP/SUB CX CX BX (CX - CX -> BX)
                BX = compareSubtract(CX, CX);
                break;
            case 0b0001_010_001_010_011: //CMP/SUB CX DX BX (CX - DX -> BX)
                BX = compareSubtract(CX, DX);
                break;
            case 0b0001_010_001_010_100: //CMP/SUB CX EX BX (CX - EX -> BX)
                BX = compareSubtract(CX, EX);
                break;
            case 0b0001_010_001_010_101: //CMP/SUB CX SP BX (CX - SP -> BX)
                BX = compareSubtract(CX, SP);
                break;
            case 0b0001_010_001_010_110: //CMP/SUB CX RP BX (CX - RP -> BX)
                BX = compareSubtract(CX, RP);
                break;
            case 0b0001_010_001_010_111: //CMP/SUB CX PC BX (CX - PC -> BX)
                BX = compareSubtract(CX, PC);
                break;
            case 0b0001_010_010_010_000: //CMP/SUB CX AX CX (CX - AX -> CX)
                CX = compareSubtract(CX, AX);
                break;
            case 0b0001_010_010_010_001: //CMP/SUB CX BX CX (CX - BX -> CX)
                CX = compareSubtract(CX, BX);
                break;
            case 0b0001_010_010_010_010: //CMP/SUB CX CX CX (CX - CX -> CX)
                CX = compareSubtract(CX, CX);
                break;
            case 0b0001_010_010_010_011: //CMP/SUB CX DX CX (CX - DX -> CX)
                CX = compareSubtract(CX, DX);
                break;
            case 0b0001_010_010_010_100: //CMP/SUB CX EX CX (CX - EX -> CX)
                CX = compareSubtract(CX, EX);
                break;
            case 0b0001_010_010_010_101: //CMP/SUB CX SP CX (CX - SP -> CX)
                CX = compareSubtract(CX, SP);
                break;
            case 0b0001_010_010_010_110: //CMP/SUB CX RP CX (CX - RP -> CX)
                CX = compareSubtract(CX, RP);
                break;
            case 0b0001_010_010_010_111: //CMP/SUB CX PC CX (CX - PC -> CX)
                CX = compareSubtract(CX, PC);
                break;
            case 0b0001_010_011_010_000: //CMP/SUB CX AX DX (CX - AX -> DX)
                DX = compareSubtract(CX, AX);
                break;
            case 0b0001_010_011_010_001: //CMP/SUB CX BX DX (CX - BX -> DX)
                DX = compareSubtract(CX, BX);
                break;
            case 0b0001_010_011_010_010: //CMP/SUB CX CX DX (CX - CX -> DX)
                DX = compareSubtract(CX, CX);
                break;
            case 0b0001_010_011_010_011: //CMP/SUB CX DX DX (CX - DX -> DX)
                DX = compareSubtract(CX, DX);
                break;
            case 0b0001_010_011_010_100: //CMP/SUB CX EX DX (CX - EX -> DX)
                DX = compareSubtract(CX, EX);
                break;
            case 0b0001_010_011_010_101: //CMP/SUB CX SP DX (CX - SP -> DX)
                DX = compareSubtract(CX, SP);
                break;
            case 0b0001_010_011_010_110: //CMP/SUB CX RP DX (CX - RP -> DX)
                DX = compareSubtract(CX, RP);
                break;
            case 0b0001_010_011_010_111: //CMP/SUB CX PC DX (CX - PC -> DX)
                DX = compareSubtract(CX, PC);
                break;
            case 0b0001_010_100_010_000: //CMP/SUB CX AX EX (CX - AX -> EX)
                EX = compareSubtract(CX, AX);
                break;
            case 0b0001_010_100_010_001: //CMP/SUB CX EX SP (CX - BX -> SP)
                EX = compareSubtract(CX, BX);
                break;
            case 0b0001_010_100_010_010: //CMP/SUB CX EX SP (CX - CX -> SP)
                EX = compareSubtract(CX, CX);
                break;
            case 0b0001_010_100_010_011: //CMP/SUB CX EX SP (CX - DX -> SP)
                EX = compareSubtract(CX, DX);
                break;
            case 0b0001_010_100_010_100: //CMP/SUB CX EX SP (CX - EX -> SP)
                EX = compareSubtract(CX, EX);
                break;
            case 0b0001_010_100_010_101: //CMP/SUB CX EX SP (CX - SP -> SP)
                EX = compareSubtract(CX, SP);
                break;
            case 0b0001_010_100_010_110: //CMP/SUB CX EX SP (CX - RP -> SP)
                EX = compareSubtract(CX, RP);
                break;
            case 0b0001_010_100_010_111: //CMP/SUB CX EX SP (CX - PC -> SP)
                EX = compareSubtract(CX, PC);
                break;
            case 0b0001_010_101_010_000: //CMP/SUB CX AX SP (CX - AX -> SP)
                SP = compareSubtract(CX, AX);
                break;
            case 0b0001_010_101_010_001: //CMP/SUB CX BX SP (CX - BX -> SP)
                SP = compareSubtract(CX, BX);
                break;
            case 0b0001_010_101_010_010: //CMP/SUB CX CX SP (CX - CX -> SP)
                SP = compareSubtract(CX, CX);
                break;
            case 0b0001_010_101_010_011: //CMP/SUB CX DX SP (CX - DX -> SP)
                SP = compareSubtract(CX, DX);
                break;
            case 0b0001_010_101_010_100: //CMP/SUB CX EX SP (CX - EX -> SP)
                SP = compareSubtract(CX, EX);
                break;
            case 0b0001_010_101_010_101: //CMP/SUB CX SP SP (CX - SP -> SP)
                SP = compareSubtract(CX, SP);
                break;
            case 0b0001_010_101_010_110: //CMP/SUB CX RP SP (CX - RP -> SP)
                SP = compareSubtract(CX, RP);
                break;
            case 0b0001_010_101_010_111: //CMP/SUB CX PC SP (CX - PC -> SP)
                SP = compareSubtract(CX, PC);
                break;
            case 0b0001_010_110_010_000: //CMP/SUB CX AX RP (CX - AX -> RP)
                RP = compareSubtract(CX, AX);
                break;
            case 0b0001_010_110_010_001: //CMP/SUB CX BX RP (CX - BX -> RP)
                RP = compareSubtract(CX, BX);
                break;
            case 0b0001_010_110_010_010: //CMP/SUB CX CX RP (CX - CX -> RP)
                RP = compareSubtract(CX, CX);
                break;
            case 0b0001_010_110_010_011: //CMP/SUB CX DX RP (CX - DX -> RP)
                RP = compareSubtract(CX, DX);
                break;
            case 0b0001_010_110_010_100: //CMP/SUB CX EX RP (CX - EX -> RP)
                RP = compareSubtract(CX, EX);
                break;
            case 0b0001_010_110_010_101: //CMP/SUB CX SP RP (CX - SP -> RP)
                RP = compareSubtract(CX, SP);
                break;
            case 0b0001_010_110_010_110: //CMP/SUB CX RP RP (CX - RP -> RP)
                RP = compareSubtract(CX, RP);
                break;
            case 0b0001_010_110_010_111: //CMP/SUB CX PC RP (CX - PC -> RP)
                RP = compareSubtract(CX, PC);
                break;
            case 0b0001_010_111_010_000: //CMP/SUB CX AX PC (CX - AX -> PC)
                PC = compareSubtract(CX, AX);
                break;
            case 0b0001_010_111_010_001: //CMP/SUB CX BX PC (CX - BX -> PC)
                PC = compareSubtract(CX, BX);
                break;
            case 0b0001_010_111_010_010: //CMP/SUB CX CX PC (CX - CX -> PC)
                PC = compareSubtract(CX, CX);
                break;
            case 0b0001_010_111_010_011: //CMP/SUB CX DX PC (CX - DX -> PC)
                PC = compareSubtract(CX, DX);
                break;
            case 0b0001_010_111_010_100: //CMP/SUB CX EX PC (CX - EX -> PC)
                PC = compareSubtract(CX, EX);
                break;
            case 0b0001_010_111_010_101: //CMP/SUB CX SP PC (CX - SP -> PC)
                PC = compareSubtract(CX, SP);
                break;
            case 0b0001_010_111_010_110: //CMP/SUB CX RP PC (CX - RP -> PC)
                PC = compareSubtract(CX, RP);
                break;
            case 0b0001_010_111_010_111: //CMP/SUB CX PC PC (CX - PC -> PC)
                PC = compareSubtract(CX, PC);
                break;
            case 0b0001_010_000_011_000: //CMP/SUB DX AX AX (DX - AX -> AX)
                AX = compareSubtract(DX, AX);
                break;
            case 0b0001_010_000_011_001: //CMP/SUB DX BX AX (DX - BX -> AX)
                AX = compareSubtract(DX, BX);
                break;
            case 0b0001_010_000_011_010: //CMP/SUB DX CX AX (DX - CX -> AX)
                AX = compareSubtract(DX, CX);
                break;
            case 0b0001_010_000_011_011: //CMP/SUB DX DX AX (DX - DX -> AX)
                AX = compareSubtract(DX, DX);
                break;
            case 0b0001_010_000_011_100: //CMP/SUB DX EX AX (DX - EX -> AX)
                AX = compareSubtract(DX, EX);
                break;
            case 0b0001_010_000_011_101: //CMP/SUB DX SP AX (DX - SP -> AX)
                AX = compareSubtract(DX, SP);
                break;
            case 0b0001_010_000_011_110: //CMP/SUB DX RP AX (DX - RP -> AX)
                AX = compareSubtract(DX, RP);
                break;
            case 0b0001_010_000_011_111: //CMP/SUB DX PC AX (DX - PC -> AX)
                AX = compareSubtract(DX, PC);
                break;
            case 0b0001_010_001_011_000: //CMP/SUB DX AX BX (DX - AX -> BX)
                BX = compareSubtract(DX, AX);
                break;
            case 0b0001_010_001_011_001: //CMP/SUB DX BX BX (DX - BX -> BX)
                BX = compareSubtract(DX, BX);
                break;
            case 0b0001_010_001_011_010: //CMP/SUB DX CX BX (DX - CX -> BX)
                BX = compareSubtract(DX, CX);
                break;
            case 0b0001_010_001_011_011: //CMP/SUB DX DX BX (DX - DX -> BX)
                BX = compareSubtract(DX, DX);
                break;
            case 0b0001_010_001_011_100: //CMP/SUB DX EX BX (DX - EX -> BX)
                BX = compareSubtract(DX, EX);
                break;
            case 0b0001_010_001_011_101: //CMP/SUB DX SP BX (DX - SP -> BX)
                BX = compareSubtract(DX, SP);
                break;
            case 0b0001_010_001_011_110: //CMP/SUB DX RP BX (DX - RP -> BX)
                BX = compareSubtract(DX, RP);
                break;
            case 0b0001_010_001_011_111: //CMP/SUB DX PC BX (DX - PC -> BX)
                BX = compareSubtract(DX, PC);
                break;
            case 0b0001_010_010_011_000: //CMP/SUB DX AX CX (DX - AX -> CX)
                CX = compareSubtract(DX, AX);
                break;
            case 0b0001_010_010_011_001: //CMP/SUB DX BX CX (DX - BX -> CX)
                CX = compareSubtract(DX, BX);
                break;
            case 0b0001_010_010_011_010: //CMP/SUB DX CX CX (DX - CX -> CX)
                CX = compareSubtract(DX, CX);
                break;
            case 0b0001_010_010_011_011: //CMP/SUB DX DX CX (DX - DX -> CX)
                CX = compareSubtract(DX, DX);
                break;
            case 0b0001_010_010_011_100: //CMP/SUB DX EX CX (DX - EX -> CX)
                CX = compareSubtract(DX, EX);
                break;
            case 0b0001_010_010_011_101: //CMP/SUB DX SP CX (DX - SP -> CX)
                CX = compareSubtract(DX, SP);
                break;
            case 0b0001_010_010_011_110: //CMP/SUB DX RP CX (DX - RP -> CX)
                CX = compareSubtract(DX, RP);
                break;
            case 0b0001_010_010_011_111: //CMP/SUB DX PC CX (DX - PC -> CX)
                CX = compareSubtract(DX, PC);
                break;
            case 0b0001_010_011_011_000: //CMP/SUB DX AX DX (DX - AX -> DX)
                DX = compareSubtract(DX, AX);
                break;
            case 0b0001_010_011_011_001: //CMP/SUB DX BX DX (DX - BX -> DX)
                DX = compareSubtract(DX, BX);
                break;
            case 0b0001_010_011_011_010: //CMP/SUB DX CX DX (DX - CX -> DX)
                DX = compareSubtract(DX, CX);
                break;
            case 0b0001_010_011_011_011: //CMP/SUB DX DX DX (DX - DX -> DX)
                DX = compareSubtract(DX, DX);
                break;
            case 0b0001_010_011_011_100: //CMP/SUB DX EX DX (DX - EX -> DX)
                DX = compareSubtract(DX, EX);
                break;
            case 0b0001_010_011_011_101: //CMP/SUB DX SP DX (DX - SP -> DX)
                DX = compareSubtract(DX, SP);
                break;
            case 0b0001_010_011_011_110: //CMP/SUB DX RP DX (DX - RP -> DX)
                DX = compareSubtract(DX, RP);
                break;
            case 0b0001_010_011_011_111: //CMP/SUB DX PC DX (DX - PC -> DX)
                DX = compareSubtract(DX, PC);
                break;
            case 0b0001_010_100_011_000: //CMP/SUB DX AX EX (DX - AX -> EX)
                EX = compareSubtract(DX, AX);
                break;
            case 0b0001_010_100_011_001: //CMP/SUB DX EX SP (DX - BX -> SP)
                EX = compareSubtract(DX, BX);
                break;
            case 0b0001_010_100_011_010: //CMP/SUB DX EX SP (DX - CX -> SP)
                EX = compareSubtract(DX, CX);
                break;
            case 0b0001_010_100_011_011: //CMP/SUB DX EX SP (DX - DX -> SP)
                EX = compareSubtract(DX, DX);
                break;
            case 0b0001_010_100_011_100: //CMP/SUB DX EX SP (DX - EX -> SP)
                EX = compareSubtract(DX, EX);
                break;
            case 0b0001_010_100_011_101: //CMP/SUB DX EX SP (DX - SP -> SP)
                EX = compareSubtract(DX, SP);
                break;
            case 0b0001_010_100_011_110: //CMP/SUB DX EX SP (DX - RP -> SP)
                EX = compareSubtract(DX, RP);
                break;
            case 0b0001_010_100_011_111: //CMP/SUB DX EX SP (DX - PC -> SP)
                EX = compareSubtract(DX, PC);
                break;
            case 0b0001_010_101_011_000: //CMP/SUB DX AX SP (DX - AX -> SP)
                SP = compareSubtract(DX, AX);
                break;
            case 0b0001_010_101_011_001: //CMP/SUB DX BX SP (DX - BX -> SP)
                SP = compareSubtract(DX, BX);
                break;
            case 0b0001_010_101_011_010: //CMP/SUB DX CX SP (DX - CX -> SP)
                SP = compareSubtract(DX, CX);
                break;
            case 0b0001_010_101_011_011: //CMP/SUB DX DX SP (DX - DX -> SP)
                SP = compareSubtract(DX, DX);
                break;
            case 0b0001_010_101_011_100: //CMP/SUB DX EX SP (DX - EX -> SP)
                SP = compareSubtract(DX, EX);
                break;
            case 0b0001_010_101_011_101: //CMP/SUB DX SP SP (DX - SP -> SP)
                SP = compareSubtract(DX, SP);
                break;
            case 0b0001_010_101_011_110: //CMP/SUB DX RP SP (DX - RP -> SP)
                SP = compareSubtract(DX, RP);
                break;
            case 0b0001_010_101_011_111: //CMP/SUB DX PC SP (DX - PC -> SP)
                SP = compareSubtract(DX, PC);
                break;
            case 0b0001_010_110_011_000: //CMP/SUB DX AX RP (DX - AX -> RP)
                RP = compareSubtract(DX, AX);
                break;
            case 0b0001_010_110_011_001: //CMP/SUB DX BX RP (DX - BX -> RP)
                RP = compareSubtract(DX, BX);
                break;
            case 0b0001_010_110_011_010: //CMP/SUB DX CX RP (DX - CX -> RP)
                RP = compareSubtract(DX, CX);
                break;
            case 0b0001_010_110_011_011: //CMP/SUB DX DX RP (DX - DX -> RP)
                RP = compareSubtract(DX, DX);
                break;
            case 0b0001_010_110_011_100: //CMP/SUB DX EX RP (DX - EX -> RP)
                RP = compareSubtract(DX, EX);
                break;
            case 0b0001_010_110_011_101: //CMP/SUB DX SP RP (DX - SP -> RP)
                RP = compareSubtract(DX, SP);
                break;
            case 0b0001_010_110_011_110: //CMP/SUB DX RP RP (DX - RP -> RP)
                RP = compareSubtract(DX, RP);
                break;
            case 0b0001_010_110_011_111: //CMP/SUB DX PC RP (DX - PC -> RP)
                RP = compareSubtract(DX, PC);
                break;
            case 0b0001_010_111_011_000: //CMP/SUB DX AX PC (DX - AX -> PC)
                PC = compareSubtract(DX, AX);
                break;
            case 0b0001_010_111_011_001: //CMP/SUB DX BX PC (DX - BX -> PC)
                PC = compareSubtract(DX, BX);
                break;
            case 0b0001_010_111_011_010: //CMP/SUB DX CX PC (DX - CX -> PC)
                PC = compareSubtract(DX, CX);
                break;
            case 0b0001_010_111_011_011: //CMP/SUB DX DX PC (DX - DX -> PC)
                PC = compareSubtract(DX, DX);
                break;
            case 0b0001_010_111_011_100: //CMP/SUB DX EX PC (DX - EX -> PC)
                PC = compareSubtract(DX, EX);
                break;
            case 0b0001_010_111_011_101: //CMP/SUB DX SP PC (DX - SP -> PC)
                PC = compareSubtract(DX, SP);
                break;
            case 0b0001_010_111_011_110: //CMP/SUB DX RP PC (DX - RP -> PC)
                PC = compareSubtract(DX, RP);
                break;
            case 0b0001_010_111_011_111: //CMP/SUB DX PC PC (DX - PC -> PC)
                PC = compareSubtract(DX, PC);
                break;
            case 0b0001_010_000_100_000: //CMP/SUB EX AX AX (EX - AX -> AX)
                AX = compareSubtract(EX, AX);
                break;
            case 0b0001_010_000_100_001: //CMP/SUB EX BX AX (EX - BX -> AX)
                AX = compareSubtract(EX, BX);
                break;
            case 0b0001_010_000_100_010: //CMP/SUB EX CX AX (EX - CX -> AX)
                AX = compareSubtract(EX, CX);
                break;
            case 0b0001_010_000_100_011: //CMP/SUB EX DX AX (EX - DX -> AX)
                AX = compareSubtract(EX, DX);
                break;
            case 0b0001_010_000_100_100: //CMP/SUB EX EX AX (EX - EX -> AX)
                AX = compareSubtract(EX, EX);
                break;
            case 0b0001_010_000_100_101: //CMP/SUB EX SP AX (EX - SP -> AX)
                AX = compareSubtract(EX, SP);
                break;
            case 0b0001_010_000_100_110: //CMP/SUB EX RP AX (EX - RP -> AX)
                AX = compareSubtract(EX, RP);
                break;
            case 0b0001_010_000_100_111: //CMP/SUB EX PC AX (EX - PC -> AX)
                AX = compareSubtract(EX, PC);
                break;
            case 0b0001_010_001_100_000: //CMP/SUB EX AX BX (EX - AX -> BX)
                BX = compareSubtract(EX, AX);
                break;
            case 0b0001_010_001_100_001: //CMP/SUB EX BX BX (EX - BX -> BX)
                BX = compareSubtract(EX, BX);
                break;
            case 0b0001_010_001_100_010: //CMP/SUB EX CX BX (EX - CX -> BX)
                BX = compareSubtract(EX, CX);
                break;
            case 0b0001_010_001_100_011: //CMP/SUB EX DX BX (EX - DX -> BX)
                BX = compareSubtract(EX, DX);
                break;
            case 0b0001_010_001_100_100: //CMP/SUB EX EX BX (EX - EX -> BX)
                BX = compareSubtract(EX, EX);
                break;
            case 0b0001_010_001_100_101: //CMP/SUB EX SP BX (EX - SP -> BX)
                BX = compareSubtract(EX, SP);
                break;
            case 0b0001_010_001_100_110: //CMP/SUB EX RP BX (EX - RP -> BX)
                BX = compareSubtract(EX, RP);
                break;
            case 0b0001_010_001_100_111: //CMP/SUB EX PC BX (EX - PC -> BX)
                BX = compareSubtract(EX, PC);
                break;
            case 0b0001_010_010_100_000: //CMP/SUB EX AX CX (EX - AX -> CX)
                CX = compareSubtract(EX, AX);
                break;
            case 0b0001_010_010_100_001: //CMP/SUB EX BX CX (EX - BX -> CX)
                CX = compareSubtract(EX, BX);
                break;
            case 0b0001_010_010_100_010: //CMP/SUB EX CX CX (EX - CX -> CX)
                CX = compareSubtract(EX, CX);
                break;
            case 0b0001_010_010_100_011: //CMP/SUB EX DX CX (EX - DX -> CX)
                CX = compareSubtract(EX, DX);
                break;
            case 0b0001_010_010_100_100: //CMP/SUB EX EX CX (EX - EX -> CX)
                CX = compareSubtract(EX, EX);
                break;
            case 0b0001_010_010_100_101: //CMP/SUB EX SP CX (EX - SP -> CX)
                CX = compareSubtract(EX, SP);
                break;
            case 0b0001_010_010_100_110: //CMP/SUB EX RP CX (EX - RP -> CX)
                CX = compareSubtract(EX, RP);
                break;
            case 0b0001_010_010_100_111: //CMP/SUB EX PC CX (EX - PC -> CX)
                CX = compareSubtract(EX, PC);
                break;
            case 0b0001_010_011_100_000: //CMP/SUB EX AX DX (EX - AX -> DX)
                DX = compareSubtract(EX, AX);
                break;
            case 0b0001_010_011_100_001: //CMP/SUB EX BX DX (EX - BX -> DX)
                DX = compareSubtract(EX, BX);
                break;
            case 0b0001_010_011_100_010: //CMP/SUB EX CX DX (EX - CX -> DX)
                DX = compareSubtract(EX, CX);
                break;
            case 0b0001_010_011_100_011: //CMP/SUB EX DX DX (EX - DX -> DX)
                DX = compareSubtract(EX, DX);
                break;
            case 0b0001_010_011_100_100: //CMP/SUB EX EX DX (EX - EX -> DX)
                DX = compareSubtract(EX, EX);
                break;
            case 0b0001_010_011_100_101: //CMP/SUB EX SP DX (EX - SP -> DX)
                DX = compareSubtract(EX, SP);
                break;
            case 0b0001_010_011_100_110: //CMP/SUB EX RP DX (EX - RP -> DX)
                DX = compareSubtract(EX, RP);
                break;
            case 0b0001_010_011_100_111: //CMP/SUB EX PC DX (EX - PC -> DX)
                DX = compareSubtract(EX, PC);
                break;
            case 0b0001_010_100_100_000: //CMP/SUB EX AX EX (EX - AX -> EX)
                EX = compareSubtract(EX, AX);
                break;
            case 0b0001_010_100_100_001: //CMP/SUB EX EX SP (EX - BX -> SP)
                EX = compareSubtract(EX, BX);
                break;
            case 0b0001_010_100_100_010: //CMP/SUB EX EX SP (EX - CX -> SP)
                EX = compareSubtract(EX, CX);
                break;
            case 0b0001_010_100_100_011: //CMP/SUB EX EX SP (EX - DX -> SP)
                EX = compareSubtract(EX, DX);
                break;
            case 0b0001_010_100_100_100: //CMP/SUB EX EX SP (EX - EX -> SP)
                EX = compareSubtract(EX, EX);
                break;
            case 0b0001_010_100_100_101: //CMP/SUB EX EX SP (EX - SP -> SP)
                EX = compareSubtract(EX, SP);
                break;
            case 0b0001_010_100_100_110: //CMP/SUB EX EX SP (EX - RP -> SP)
                EX = compareSubtract(EX, RP);
                break;
            case 0b0001_010_100_100_111: //CMP/SUB EX EX SP (EX - PC -> SP)
                EX = compareSubtract(EX, PC);
                break;
            case 0b0001_010_101_100_000: //CMP/SUB EX AX SP (EX - AX -> SP)
                SP = compareSubtract(EX, AX);
                break;
            case 0b0001_010_101_100_001: //CMP/SUB EX BX SP (EX - BX -> SP)
                SP = compareSubtract(EX, BX);
                break;
            case 0b0001_010_101_100_010: //CMP/SUB EX CX SP (EX - CX -> SP)
                SP = compareSubtract(EX, CX);
                break;
            case 0b0001_010_101_100_011: //CMP/SUB EX DX SP (EX - DX -> SP)
                SP = compareSubtract(EX, DX);
                break;
            case 0b0001_010_101_100_100: //CMP/SUB EX EX SP (EX - EX -> SP)
                SP = compareSubtract(EX, EX);
                break;
            case 0b0001_010_101_100_101: //CMP/SUB EX SP SP (EX - SP -> SP)
                SP = compareSubtract(EX, SP);
                break;
            case 0b0001_010_101_100_110: //CMP/SUB EX RP SP (EX - RP -> SP)
                SP = compareSubtract(EX, RP);
                break;
            case 0b0001_010_101_100_111: //CMP/SUB EX PC SP (EX - PC -> SP)
                SP = compareSubtract(EX, PC);
                break;
            case 0b0001_010_110_100_000: //CMP/SUB EX AX RP (EX - AX -> RP)
                RP = compareSubtract(EX, AX);
                break;
            case 0b0001_010_110_100_001: //CMP/SUB EX BX RP (EX - BX -> RP)
                RP = compareSubtract(EX, BX);
                break;
            case 0b0001_010_110_100_010: //CMP/SUB EX CX RP (EX - CX -> RP)
                RP = compareSubtract(EX, CX);
                break;
            case 0b0001_010_110_100_011: //CMP/SUB EX DX RP (EX - DX -> RP)
                RP = compareSubtract(EX, DX);
                break;
            case 0b0001_010_110_100_100: //CMP/SUB EX EX RP (EX - EX -> RP)
                RP = compareSubtract(EX, EX);
                break;
            case 0b0001_010_110_100_101: //CMP/SUB EX SP RP (EX - SP -> RP)
                RP = compareSubtract(EX, SP);
                break;
            case 0b0001_010_110_100_110: //CMP/SUB EX RP RP (EX - RP -> RP)
                RP = compareSubtract(EX, RP);
                break;
            case 0b0001_010_110_100_111: //CMP/SUB EX PC RP (EX - PC -> RP)
                RP = compareSubtract(EX, PC);
                break;
            case 0b0001_010_111_100_000: //CMP/SUB EX AX PC (EX - AX -> PC)
                PC = compareSubtract(EX, AX);
                break;
            case 0b0001_010_111_100_001: //CMP/SUB EX BX PC (EX - BX -> PC)
                PC = compareSubtract(EX, BX);
                break;
            case 0b0001_010_111_100_010: //CMP/SUB EX CX PC (EX - CX -> PC)
                PC = compareSubtract(EX, CX);
                break;
            case 0b0001_010_111_100_011: //CMP/SUB EX DX PC (EX - DX -> PC)
                PC = compareSubtract(EX, DX);
                break;
            case 0b0001_010_111_100_100: //CMP/SUB EX EX PC (EX - EX -> PC)
                PC = compareSubtract(EX, EX);
                break;
            case 0b0001_010_111_100_101: //CMP/SUB EX SP PC (EX - SP -> PC)
                PC = compareSubtract(EX, SP);
                break;
            case 0b0001_010_111_100_110: //CMP/SUB EX RP PC (EX - RP -> PC)
                PC = compareSubtract(EX, RP);
                break;
            case 0b0001_010_111_100_111: //CMP/SUB EX PC PC (EX - PC -> PC)
                PC = compareSubtract(EX, PC);
                break;
            case 0b0001_010_000_101_000: //CMP/SUB SP AX AX (SP - AX -> AX)
                AX = compareSubtract(SP, AX);
                break;
            case 0b0001_010_000_101_001: //CMP/SUB SP BX AX (SP - BX -> AX)
                AX = compareSubtract(SP, BX);
                break;
            case 0b0001_010_000_101_010: //CMP/SUB SP CX AX (SP - CX -> AX)
                AX = compareSubtract(SP, CX);
                break;
            case 0b0001_010_000_101_011: //CMP/SUB SP DX AX (SP - DX -> AX)
                AX = compareSubtract(SP, DX);
                break;
            case 0b0001_010_000_101_100: //CMP/SUB SP EX AX (SP - EX -> AX)
                AX = compareSubtract(SP, EX);
                break;
            case 0b0001_010_000_101_101: //CMP/SUB SP SP AX (SP - SP -> AX)
                AX = compareSubtract(SP, SP);
                break;
            case 0b0001_010_000_101_110: //CMP/SUB SP RP AX (SP - RP -> AX)
                AX = compareSubtract(SP, RP);
                break;
            case 0b0001_010_000_101_111: //CMP/SUB SP PC AX (SP - PC -> AX)
                AX = compareSubtract(SP, PC);
                break;
            case 0b0001_010_001_101_000: //CMP/SUB SP AX BX (SP - AX -> BX)
                BX = compareSubtract(SP, AX);
                break;
            case 0b0001_010_001_101_001: //CMP/SUB SP BX BX (SP - BX -> BX)
                BX = compareSubtract(SP, BX);
                break;
            case 0b0001_010_001_101_010: //CMP/SUB SP CX BX (SP - CX -> BX)
                BX = compareSubtract(SP, CX);
                break;
            case 0b0001_010_001_101_011: //CMP/SUB SP DX BX (SP - DX -> BX)
                BX = compareSubtract(SP, DX);
                break;
            case 0b0001_010_001_101_100: //CMP/SUB SP EX BX (SP - EX -> BX)
                BX = compareSubtract(SP, EX);
                break;
            case 0b0001_010_001_101_101: //CMP/SUB SP SP BX (SP - SP -> BX)
                BX = compareSubtract(SP, SP);
                break;
            case 0b0001_010_001_101_110: //CMP/SUB SP RP BX (SP - RP -> BX)
                BX = compareSubtract(SP, RP);
                break;
            case 0b0001_010_001_101_111: //CMP/SUB SP PC BX (SP - PC -> BX)
                BX = compareSubtract(SP, PC);
                break;
            case 0b0001_010_010_101_000: //CMP/SUB SP AX CX (SP - AX -> CX)
                CX = compareSubtract(SP, AX);
                break;
            case 0b0001_010_010_101_001: //CMP/SUB SP BX CX (SP - BX -> CX)
                CX = compareSubtract(SP, BX);
                break;
            case 0b0001_010_010_101_010: //CMP/SUB SP CX CX (SP - CX -> CX)
                CX = compareSubtract(SP, CX);
                break;
            case 0b0001_010_010_101_011: //CMP/SUB SP DX CX (SP - DX -> CX)
                CX = compareSubtract(SP, DX);
                break;
            case 0b0001_010_010_101_100: //CMP/SUB SP EX CX (SP - EX -> CX)
                CX = compareSubtract(SP, EX);
                break;
            case 0b0001_010_010_101_101: //CMP/SUB SP SP CX (SP - SP -> CX)
                CX = compareSubtract(SP, SP);
                break;
            case 0b0001_010_010_101_110: //CMP/SUB SP RP CX (SP - RP -> CX)
                CX = compareSubtract(SP, RP);
                break;
            case 0b0001_010_010_101_111: //CMP/SUB SP PC CX (SP - PC -> CX)
                CX = compareSubtract(SP, PC);
                break;
            case 0b0001_010_011_101_000: //CMP/SUB SP AX DX (SP - AX -> DX)
                DX = compareSubtract(SP, AX);
                break;
            case 0b0001_010_011_101_001: //CMP/SUB SP BX DX (SP - BX -> DX)
                DX = compareSubtract(SP, BX);
                break;
            case 0b0001_010_011_101_010: //CMP/SUB SP CX DX (SP - CX -> DX)
                DX = compareSubtract(SP, CX);
                break;
            case 0b0001_010_011_101_011: //CMP/SUB SP DX DX (SP - DX -> DX)
                DX = compareSubtract(SP, DX);
                break;
            case 0b0001_010_011_101_100: //CMP/SUB SP EX DX (SP - EX -> DX)
                DX = compareSubtract(SP, EX);
                break;
            case 0b0001_010_011_101_101: //CMP/SUB SP SP DX (SP - SP -> DX)
                DX = compareSubtract(SP, SP);
                break;
            case 0b0001_010_011_101_110: //CMP/SUB SP RP DX (SP - RP -> DX)
                DX = compareSubtract(SP, RP);
                break;
            case 0b0001_010_011_101_111: //CMP/SUB SP PC DX (SP - PC -> DX)
                DX = compareSubtract(SP, PC);
                break;
            case 0b0001_010_100_101_000: //CMP/SUB SP AX EX (SP - AX -> EX)
                EX = compareSubtract(SP, AX);
                break;
            case 0b0001_010_100_101_001: //CMP/SUB SP EX SP (SP - BX -> SP)
                EX = compareSubtract(SP, BX);
                break;
            case 0b0001_010_100_101_010: //CMP/SUB SP EX SP (SP - CX -> SP)
                EX = compareSubtract(SP, CX);
                break;
            case 0b0001_010_100_101_011: //CMP/SUB SP EX SP (SP - DX -> SP)
                EX = compareSubtract(SP, DX);
                break;
            case 0b0001_010_100_101_100: //CMP/SUB SP EX SP (SP - EX -> SP)
                EX = compareSubtract(SP, EX);
                break;
            case 0b0001_010_100_101_101: //CMP/SUB SP EX SP (SP - SP -> SP)
                EX = compareSubtract(SP, SP);
                break;
            case 0b0001_010_100_101_110: //CMP/SUB SP EX SP (SP - RP -> SP)
                EX = compareSubtract(SP, RP);
                break;
            case 0b0001_010_100_101_111: //CMP/SUB SP EX SP (SP - PC -> SP)
                EX = compareSubtract(SP, PC);
                break;
            case 0b0001_010_101_101_000: //CMP/SUB SP AX SP (SP - AX -> SP)
                SP = compareSubtract(SP, AX);
                break;
            case 0b0001_010_101_101_001: //CMP/SUB SP BX SP (SP - BX -> SP)
                SP = compareSubtract(SP, BX);
                break;
            case 0b0001_010_101_101_010: //CMP/SUB SP CX SP (SP - CX -> SP)
                SP = compareSubtract(SP, CX);
                break;
            case 0b0001_010_101_101_011: //CMP/SUB SP DX SP (SP - DX -> SP)
                SP = compareSubtract(SP, DX);
                break;
            case 0b0001_010_101_101_100: //CMP/SUB SP EX SP (SP - EX -> SP)
                SP = compareSubtract(SP, EX);
                break;
            case 0b0001_010_101_101_101: //CMP/SUB SP SP SP (SP - SP -> SP)
                SP = compareSubtract(SP, SP);
                break;
            case 0b0001_010_101_101_110: //CMP/SUB SP RP SP (SP - RP -> SP)
                SP = compareSubtract(SP, RP);
                break;
            case 0b0001_010_101_101_111: //CMP/SUB SP PC SP (SP - PC -> SP)
                SP = compareSubtract(SP, PC);
                break;
            case 0b0001_010_110_101_000: //CMP/SUB SP AX RP (SP - AX -> RP)
                RP = compareSubtract(SP, AX);
                break;
            case 0b0001_010_110_101_001: //CMP/SUB SP BX RP (SP - BX -> RP)
                RP = compareSubtract(SP, BX);
                break;
            case 0b0001_010_110_101_010: //CMP/SUB SP CX RP (SP - CX -> RP)
                RP = compareSubtract(SP, CX);
                break;
            case 0b0001_010_110_101_011: //CMP/SUB SP DX RP (SP - DX -> RP)
                RP = compareSubtract(SP, DX);
                break;
            case 0b0001_010_110_101_100: //CMP/SUB SP EX RP (SP - EX -> RP)
                RP = compareSubtract(SP, EX);
                break;
            case 0b0001_010_110_101_101: //CMP/SUB SP SP RP (SP - SP -> RP)
                RP = compareSubtract(SP, SP);
                break;
            case 0b0001_010_110_101_110: //CMP/SUB SP RP RP (SP - RP -> RP)
                RP = compareSubtract(SP, RP);
                break;
            case 0b0001_010_110_101_111: //CMP/SUB SP PC RP (SP - PC -> RP)
                RP = compareSubtract(SP, PC);
                break;
            case 0b0001_010_111_101_000: //CMP/SUB SP AX PC (SP - AX -> PC)
                PC = compareSubtract(SP, AX);
                break;
            case 0b0001_010_111_101_001: //CMP/SUB SP BX PC (SP - BX -> PC)
                PC = compareSubtract(SP, BX);
                break;
            case 0b0001_010_111_101_010: //CMP/SUB SP CX PC (SP - CX -> PC)
                PC = compareSubtract(SP, CX);
                break;
            case 0b0001_010_111_101_011: //CMP/SUB SP DX PC (SP - DX -> PC)
                PC = compareSubtract(SP, DX);
                break;
            case 0b0001_010_111_101_100: //CMP/SUB SP EX PC (SP - EX -> PC)
                PC = compareSubtract(SP, EX);
                break;
            case 0b0001_010_111_101_101: //CMP/SUB SP SP PC (SP - SP -> PC)
                PC = compareSubtract(SP, SP);
                break;
            case 0b0001_010_111_101_110: //CMP/SUB SP RP PC (SP - RP -> PC)
                PC = compareSubtract(SP, RP);
                break;
            case 0b0001_010_111_101_111: //CMP/SUB SP PC PC (SP - PC -> PC)
                PC = compareSubtract(SP, PC);
                break;
            case 0b0001_010_000_110_000: //CMP/SUB RP AX AX (RP - AX -> AX)
                AX = compareSubtract(RP, AX);
                break;
            case 0b0001_010_000_110_001: //CMP/SUB RP BX AX (RP - BX -> AX)
                AX = compareSubtract(RP, BX);
                break;
            case 0b0001_010_000_110_010: //CMP/SUB RP CX AX (RP - CX -> AX)
                AX = compareSubtract(RP, CX);
                break;
            case 0b0001_010_000_110_011: //CMP/SUB RP DX AX (RP - DX -> AX)
                AX = compareSubtract(RP, DX);
                break;
            case 0b0001_010_000_110_100: //CMP/SUB RP EX AX (RP - EX -> AX)
                AX = compareSubtract(RP, EX);
                break;
            case 0b0001_010_000_110_101: //CMP/SUB RP SP AX (RP - SP -> AX)
                AX = compareSubtract(RP, SP);
                break;
            case 0b0001_010_000_110_110: //CMP/SUB RP RP AX (RP - RP -> AX)
                AX = compareSubtract(RP, RP);
                break;
            case 0b0001_010_000_110_111: //CMP/SUB RP PC AX (RP - PC -> AX)
                AX = compareSubtract(RP, PC);
                break;
            case 0b0001_010_001_110_000: //CMP/SUB RP AX BX (RP - AX -> BX)
                BX = compareSubtract(RP, AX);
                break;
            case 0b0001_010_001_110_001: //CMP/SUB RP BX BX (RP - BX -> BX)
                BX = compareSubtract(RP, BX);
                break;
            case 0b0001_010_001_110_010: //CMP/SUB RP CX BX (RP - CX -> BX)
                BX = compareSubtract(RP, CX);
                break;
            case 0b0001_010_001_110_011: //CMP/SUB RP DX BX (RP - DX -> BX)
                BX = compareSubtract(RP, DX);
                break;
            case 0b0001_010_001_110_100: //CMP/SUB RP EX BX (RP - EX -> BX)
                BX = compareSubtract(RP, EX);
                break;
            case 0b0001_010_001_110_101: //CMP/SUB RP SP BX (RP - SP -> BX)
                BX = compareSubtract(RP, SP);
                break;
            case 0b0001_010_001_110_110: //CMP/SUB RP RP BX (RP - RP -> BX)
                BX = compareSubtract(RP, RP);
                break;
            case 0b0001_010_001_110_111: //CMP/SUB RP PC BX (RP - PC -> BX)
                BX = compareSubtract(RP, PC);
                break;
            case 0b0001_010_010_110_000: //CMP/SUB RP AX CX (RP - AX -> CX)
                CX = compareSubtract(RP, AX);
                break;
            case 0b0001_010_010_110_001: //CMP/SUB RP BX CX (RP - BX -> CX)
                CX = compareSubtract(RP, BX);
                break;
            case 0b0001_010_010_110_010: //CMP/SUB RP CX CX (RP - CX -> CX)
                CX = compareSubtract(RP, CX);
                break;
            case 0b0001_010_010_110_011: //CMP/SUB RP DX CX (RP - DX -> CX)
                CX = compareSubtract(RP, DX);
                break;
            case 0b0001_010_010_110_100: //CMP/SUB RP EX CX (RP - EX -> CX)
                CX = compareSubtract(RP, EX);
                break;
            case 0b0001_010_010_110_101: //CMP/SUB RP SP CX (RP - SP -> CX)
                CX = compareSubtract(RP, SP);
                break;
            case 0b0001_010_010_110_110: //CMP/SUB RP RP CX (RP - RP -> CX)
                CX = compareSubtract(RP, RP);
                break;
            case 0b0001_010_010_110_111: //CMP/SUB RP PC CX (RP - PC -> CX)
                CX = compareSubtract(RP, PC);
                break;
            case 0b0001_010_011_110_000: //CMP/SUB RP AX DX (RP - AX -> DX)
                DX = compareSubtract(RP, AX);
                break;
            case 0b0001_010_011_110_001: //CMP/SUB RP BX DX (RP - BX -> DX)
                DX = compareSubtract(RP, BX);
                break;
            case 0b0001_010_011_110_010: //CMP/SUB RP CX DX (RP - CX -> DX)
                DX = compareSubtract(RP, CX);
                break;
            case 0b0001_010_011_110_011: //CMP/SUB RP DX DX (RP - DX -> DX)
                DX = compareSubtract(RP, DX);
                break;
            case 0b0001_010_011_110_100: //CMP/SUB RP EX DX (RP - EX -> DX)
                DX = compareSubtract(RP, EX);
                break;
            case 0b0001_010_011_110_101: //CMP/SUB RP SP DX (RP - SP -> DX)
                DX = compareSubtract(RP, SP);
                break;
            case 0b0001_010_011_110_110: //CMP/SUB RP RP DX (RP - RP -> DX)
                DX = compareSubtract(RP, RP);
                break;
            case 0b0001_010_011_110_111: //CMP/SUB RP PC DX (RP - PC -> DX)
                DX = compareSubtract(RP, PC);
                break;
            case 0b0001_010_100_110_000: //CMP/SUB RP AX EX (RP - AX -> EX)
                EX = compareSubtract(RP, AX);
                break;
            case 0b0001_010_100_110_001: //CMP/SUB RP EX SP (RP - BX -> SP)
                EX = compareSubtract(RP, BX);
                break;
            case 0b0001_010_100_110_010: //CMP/SUB RP EX SP (RP - CX -> SP)
                EX = compareSubtract(RP, CX);
                break;
            case 0b0001_010_100_110_011: //CMP/SUB RP EX SP (RP - DX -> SP)
                EX = compareSubtract(RP, DX);
                break;
            case 0b0001_010_100_110_100: //CMP/SUB RP EX SP (RP - EX -> SP)
                EX = compareSubtract(RP, EX);
                break;
            case 0b0001_010_100_110_101: //CMP/SUB RP EX SP (RP - SP -> SP)
                EX = compareSubtract(RP, SP);
                break;
            case 0b0001_010_100_110_110: //CMP/SUB RP EX SP (RP - RP -> SP)
                EX = compareSubtract(RP, RP);
                break;
            case 0b0001_010_100_110_111: //CMP/SUB RP EX SP (RP - PC -> SP)
                EX = compareSubtract(RP, PC);
                break;
            case 0b0001_010_101_110_000: //CMP/SUB RP AX SP (RP - AX -> SP)
                SP = compareSubtract(RP, AX);
                break;
            case 0b0001_010_101_110_001: //CMP/SUB RP BX SP (RP - BX -> SP)
                SP = compareSubtract(RP, BX);
                break;
            case 0b0001_010_101_110_010: //CMP/SUB RP CX SP (RP - CX -> SP)
                SP = compareSubtract(RP, CX);
                break;
            case 0b0001_010_101_110_011: //CMP/SUB RP DX SP (RP - DX -> SP)
                SP = compareSubtract(RP, DX);
                break;
            case 0b0001_010_101_110_100: //CMP/SUB RP EX SP (RP - EX -> SP)
                SP = compareSubtract(RP, EX);
                break;
            case 0b0001_010_101_110_101: //CMP/SUB RP SP SP (RP - SP -> SP)
                SP = compareSubtract(RP, SP);
                break;
            case 0b0001_010_101_110_110: //CMP/SUB RP RP SP (RP - RP -> SP)
                SP = compareSubtract(RP, RP);
                break;
            case 0b0001_010_101_110_111: //CMP/SUB RP PC SP (RP - PC -> SP)
                SP = compareSubtract(RP, PC);
                break;
            case 0b0001_010_110_110_000: //CMP/SUB RP AX RP (RP - AX -> RP)
                RP = compareSubtract(RP, AX);
                break;
            case 0b0001_010_110_110_001: //CMP/SUB RP BX RP (RP - BX -> RP)
                RP = compareSubtract(RP, BX);
                break;
            case 0b0001_010_110_110_010: //CMP/SUB RP CX RP (RP - CX -> RP)
                RP = compareSubtract(RP, CX);
                break;
            case 0b0001_010_110_110_011: //CMP/SUB RP DX RP (RP - DX -> RP)
                RP = compareSubtract(RP, DX);
                break;
            case 0b0001_010_110_110_100: //CMP/SUB RP EX RP (RP - EX -> RP)
                RP = compareSubtract(RP, EX);
                break;
            case 0b0001_010_110_110_101: //CMP/SUB RP SP RP (RP - SP -> RP)
                RP = compareSubtract(RP, SP);
                break;
            case 0b0001_010_110_110_110: //CMP/SUB RP RP RP (RP - RP -> RP)
                RP = compareSubtract(RP, RP);
                break;
            case 0b0001_010_110_110_111: //CMP/SUB RP PC RP (RP - PC -> RP)
                RP = compareSubtract(RP, PC);
                break;
            case 0b0001_010_111_110_000: //CMP/SUB RP AX PC (RP - AX -> PC)
                PC = compareSubtract(RP, AX);
                break;
            case 0b0001_010_111_110_001: //CMP/SUB RP BX PC (RP - BX -> PC)
                PC = compareSubtract(RP, BX);
                break;
            case 0b0001_010_111_110_010: //CMP/SUB RP CX PC (RP - CX -> PC)
                PC = compareSubtract(RP, CX);
                break;
            case 0b0001_010_111_110_011: //CMP/SUB RP DX PC (RP - DX -> PC)
                PC = compareSubtract(RP, DX);
                break;
            case 0b0001_010_111_110_100: //CMP/SUB RP EX PC (RP - EX -> PC)
                PC = compareSubtract(RP, EX);
                break;
            case 0b0001_010_111_110_101: //CMP/SUB RP SP PC (RP - SP -> PC)
                PC = compareSubtract(RP, SP);
                break;
            case 0b0001_010_111_110_110: //CMP/SUB RP RP PC (RP - RP -> PC)
                PC = compareSubtract(RP, RP);
                break;
            case 0b0001_010_111_110_111: //CMP/SUB RP PC PC (RP - PC -> PC)
                PC = compareSubtract(RP, PC);
                break;
            case 0b0001_010_000_111_000: //CMP/SUB PC AX AX (PC - AX -> AX)
                AX = compareSubtract(PC, AX);
                break;
            case 0b0001_010_000_111_001: //CMP/SUB PC BX AX (PC - BX -> AX)
                AX = compareSubtract(PC, BX);
                break;
            case 0b0001_010_000_111_010: //CMP/SUB PC CX AX (PC - CX -> AX)
                AX = compareSubtract(PC, CX);
                break;
            case 0b0001_010_000_111_011: //CMP/SUB PC DX AX (PC - DX -> AX)
                AX = compareSubtract(PC, DX);
                break;
            case 0b0001_010_000_111_100: //CMP/SUB PC EX AX (PC - EX -> AX)
                AX = compareSubtract(PC, EX);
                break;
            case 0b0001_010_000_111_101: //CMP/SUB PC SP AX (PC - SP -> AX)
                AX = compareSubtract(PC, SP);
                break;
            case 0b0001_010_000_111_110: //CMP/SUB PC RP AX (PC - RP -> AX)
                AX = compareSubtract(PC, RP);
                break;
            case 0b0001_010_000_111_111: //CMP/SUB PC PC AX (PC - PC -> AX)
                AX = compareSubtract(PC, PC);
                break;
            case 0b0001_010_001_111_000: //CMP/SUB PC AX BX (PC - AX -> BX)
                BX = compareSubtract(PC, AX);
                break;
            case 0b0001_010_001_111_001: //CMP/SUB PC BX BX (PC - BX -> BX)
                BX = compareSubtract(PC, BX);
                break;
            case 0b0001_010_001_111_010: //CMP/SUB PC CX BX (PC - CX -> BX)
                BX = compareSubtract(PC, CX);
                break;
            case 0b0001_010_001_111_011: //CMP/SUB PC DX BX (PC - DX -> BX)
                BX = compareSubtract(PC, DX);
                break;
            case 0b0001_010_001_111_100: //CMP/SUB PC EX BX (PC - EX -> BX)
                BX = compareSubtract(PC, EX);
                break;
            case 0b0001_010_001_111_101: //CMP/SUB PC SP BX (PC - SP -> BX)
                BX = compareSubtract(PC, SP);
                break;
            case 0b0001_010_001_111_110: //CMP/SUB PC RP BX (PC - RP -> BX)
                BX = compareSubtract(PC, RP);
                break;
            case 0b0001_010_001_111_111: //CMP/SUB PC PC BX (PC - PC -> BX)
                BX = compareSubtract(PC, PC);
                break;
            case 0b0001_010_010_111_000: //CMP/SUB PC AX CX (PC - AX -> CX)
                CX = compareSubtract(PC, AX);
                break;
            case 0b0001_010_010_111_001: //CMP/SUB PC BX CX (PC - BX -> CX)
                CX = compareSubtract(PC, BX);
                break;
            case 0b0001_010_010_111_010: //CMP/SUB PC CX CX (PC - CX -> CX)
                CX = compareSubtract(PC, CX);
                break;
            case 0b0001_010_010_111_011: //CMP/SUB PC DX CX (PC - DX -> CX)
                CX = compareSubtract(PC, DX);
                break;
            case 0b0001_010_010_111_100: //CMP/SUB PC EX CX (PC - EX -> CX)
                CX = compareSubtract(PC, EX);
                break;
            case 0b0001_010_010_111_101: //CMP/SUB PC SP CX (PC - SP -> CX)
                CX = compareSubtract(PC, SP);
                break;
            case 0b0001_010_010_111_110: //CMP/SUB PC RP CX (PC - RP -> CX)
                CX = compareSubtract(PC, RP);
                break;
            case 0b0001_010_010_111_111: //CMP/SUB PC PC CX (PC - PC -> CX)
                CX = compareSubtract(PC, PC);
                break;
            case 0b0001_010_011_111_000: //CMP/SUB PC AX DX (PC - AX -> DX)
                DX = compareSubtract(PC, AX);
                break;
            case 0b0001_010_011_111_001: //CMP/SUB PC BX DX (PC - BX -> DX)
                DX = compareSubtract(PC, BX);
                break;
            case 0b0001_010_011_111_010: //CMP/SUB PC CX DX (PC - CX -> DX)
                DX = compareSubtract(PC, CX);
                break;
            case 0b0001_010_011_111_011: //CMP/SUB PC DX DX (PC - DX -> DX)
                DX = compareSubtract(PC, DX);
                break;
            case 0b0001_010_011_111_100: //CMP/SUB PC EX DX (PC - EX -> DX)
                DX = compareSubtract(PC, EX);
                break;
            case 0b0001_010_011_111_101: //CMP/SUB PC SP DX (PC - SP -> DX)
                DX = compareSubtract(PC, SP);
                break;
            case 0b0001_010_011_111_110: //CMP/SUB PC RP DX (PC - RP -> DX)
                DX = compareSubtract(PC, RP);
                break;
            case 0b0001_010_011_111_111: //CMP/SUB PC PC DX (PC - PC -> DX)
                DX = compareSubtract(PC, PC);
                break;
            case 0b0001_010_100_111_000: //CMP/SUB PC AX EX (PC - AX -> EX)
                EX = compareSubtract(PC, AX);
                break;
            case 0b0001_010_100_111_001: //CMP/SUB PC EX SP (PC - BX -> SP)
                EX = compareSubtract(PC, BX);
                break;
            case 0b0001_010_100_111_010: //CMP/SUB PC EX SP (PC - CX -> SP)
                EX = compareSubtract(PC, CX);
                break;
            case 0b0001_010_100_111_011: //CMP/SUB PC EX SP (PC - DX -> SP)
                EX = compareSubtract(PC, DX);
                break;
            case 0b0001_010_100_111_100: //CMP/SUB PC EX SP (PC - EX -> SP)
                EX = compareSubtract(PC, EX);
                break;
            case 0b0001_010_100_111_101: //CMP/SUB PC EX SP (PC - SP -> SP)
                EX = compareSubtract(PC, SP);
                break;
            case 0b0001_010_100_111_110: //CMP/SUB PC EX SP (PC - RP -> SP)
                EX = compareSubtract(PC, RP);
                break;
            case 0b0001_010_100_111_111: //CMP/SUB PC EX SP (PC - PC -> SP)
                EX = compareSubtract(PC, PC);
                break;
            case 0b0001_010_101_111_000: //CMP/SUB PC AX SP (PC - AX -> SP)
                SP = compareSubtract(PC, AX);
                break;
            case 0b0001_010_101_111_001: //CMP/SUB PC BX SP (PC - BX -> SP)
                SP = compareSubtract(PC, BX);
                break;
            case 0b0001_010_101_111_010: //CMP/SUB PC CX SP (PC - CX -> SP)
                SP = compareSubtract(PC, CX);
                break;
            case 0b0001_010_101_111_011: //CMP/SUB PC DX SP (PC - DX -> SP)
                SP = compareSubtract(PC, DX);
                break;
            case 0b0001_010_101_111_100: //CMP/SUB PC EX SP (PC - EX -> SP)
                SP = compareSubtract(PC, EX);
                break;
            case 0b0001_010_101_111_101: //CMP/SUB PC SP SP (PC - SP -> SP)
                SP = compareSubtract(PC, SP);
                break;
            case 0b0001_010_101_111_110: //CMP/SUB PC RP SP (PC - RP -> SP)
                SP = compareSubtract(PC, RP);
                break;
            case 0b0001_010_101_111_111: //CMP/SUB PC PC SP (PC - PC -> SP)
                SP = compareSubtract(PC, PC);
                break;
            case 0b0001_010_110_111_000: //CMP/SUB PC AX RP (PC - AX -> RP)
                RP = compareSubtract(PC, AX);
                break;
            case 0b0001_010_110_111_001: //CMP/SUB PC BX RP (PC - BX -> RP)
                RP = compareSubtract(PC, BX);
                break;
            case 0b0001_010_110_111_010: //CMP/SUB PC CX RP (PC - CX -> RP)
                RP = compareSubtract(PC, CX);
                break;
            case 0b0001_010_110_111_011: //CMP/SUB PC DX RP (PC - DX -> RP)
                RP = compareSubtract(PC, DX);
                break;
            case 0b0001_010_110_111_100: //CMP/SUB PC EX RP (PC - EX -> RP)
                RP = compareSubtract(PC, EX);
                break;
            case 0b0001_010_110_111_101: //CMP/SUB PC SP RP (PC - SP -> RP)
                RP = compareSubtract(PC, SP);
                break;
            case 0b0001_010_110_111_110: //CMP/SUB PC RP RP (PC - RP -> RP)
                RP = compareSubtract(PC, RP);
                break;
            case 0b0001_010_110_111_111: //CMP/SUB PC PC RP (PC - PC -> RP)
                RP = compareSubtract(PC, PC);
                break;
            case 0b0001_010_111_111_000: //CMP/SUB PC AX PC (PC - AX -> PC)
                PC = compareSubtract(PC, AX);
                break;
            case 0b0001_010_111_111_001: //CMP/SUB PC BX PC (PC - BX -> PC)
                PC = compareSubtract(PC, BX);
                break;
            case 0b0001_010_111_111_010: //CMP/SUB PC CX PC (PC - CX -> PC)
                PC = compareSubtract(PC, CX);
                break;
            case 0b0001_010_111_111_011: //CMP/SUB PC DX PC (PC - DX -> PC)
                PC = compareSubtract(PC, DX);
                break;
            case 0b0001_010_111_111_100: //CMP/SUB PC EX PC (PC - EX -> PC)
                PC = compareSubtract(PC, EX);
                break;
            case 0b0001_010_111_111_101: //CMP/SUB PC SP PC (PC - SP -> PC)
                PC = compareSubtract(PC, SP);
                break;
            case 0b0001_010_111_111_110: //CMP/SUB PC RP PC (PC - RP -> PC)
                PC = compareSubtract(PC, RP);
                break;
            case 0b0001_010_111_111_111: //CMP/SUB PC PC PC (PC - PC -> PC)
                PC = compareSubtract(PC, PC);
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }

    /*
     * LOAD/JMP: Immediately fetch word from memory to register
	*/
    private void executeLOADJMPInstruction() {
        switch (INST) {

            case 0b0010_0001_0000_0_000: //LOAD AX
                AX = load();
                break;
            case 0b0010_0001_0000_0_001: //LOAD BX
                BX = load();
                break;
            case 0b0010_0001_0000_0_010: //LOAD CX
                CX = load();
                break;
            case 0b0010_0001_0000_0_011: //LOAD DX
                DX = load();
                break;
            case 0b0010_0001_0000_0_100: //LOAD EX
                EX = load();
                break;
            case 0b0010_0001_0000_0_101: //LOAD SP
                SP = load();
                break;
            case 0b0010_0001_0000_0_110: //LOAD RP
                RP = load();
                break;
            /*
             * JMP: Unconditional Jump to next following value in memory
			 */
            case 0b0010_0001_0000_0_111: //JMP | LOAD PC
                PC = load();
                break;

            default:
                unknownInstruction(INST);
                break;

        }

    }

    /*
     * Conditional Jump: JMP to address depending on condition register
     */
    private void executeConditionalJumpInstruction() {
        switch (INST) {
            case 0b0000_1000_000_0_0001: //JNEG
                if (sign) {
                    PC = load();
                }
                break;
            case 0b0000_1000_000_0_0010: //JZ
                if (zero) {
                    PC = load();
                }
                break;
            case 0b0000_1000_000_0_0100: //JC
                if (carry) {
                    PC = load();
                }
                break;
            case 0b0000_1000_000_0_1000: //JO
                if (overflow) {
                    PC = load();
                }
                break;
            case 0b0000_1000_000_1_0001: //JNNEG
                if (!sign) {
                    PC = load();
                }
                break;
            case 0b0000_1000_000_1_0010: //JNZ
                if (!zero) {
                    PC = load();
                }
                break;
            case 0b0000_1000_000_1_0100: //JNC
                if (!carry) {
                    PC = load();
                }
                break;
            case 0b0000_1000_000_1_1000: //JNO
                if (!overflow) {
                    PC = load();
                }
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }

    /*
     * Store. Store a word from a register to memory
     */
    private void executeSTOREInstruction() {
        switch (INST) {
            case 0b0010_0010_00_000_000: //STORE AX AX (AX -> [AX])
                store(AX, AX);
                break;
            case 0b0010_0010_00_000_001: //STORE AX BX (AX -> [BX])
                store(AX, BX);
                break;
            case 0b0010_0010_00_000_010: //STORE AX CX (AX -> [CX])
                store(AX, CX);
                break;
            case 0b0010_0010_00_000_011: //STORE AX DX (AX -> [DX])
                store(AX, DX);
                break;
            case 0b0010_0010_00_000_100: //STORE AX EX (AX -> [EX])
                store(AX, EX);
                break;
            case 0b0010_0010_00_000_101: //STORE AX SP (AX -> [SP])
                store(AX, SP);
                break;
            case 0b0010_0010_00_000_110: //STORE AX RP (AX -> [RP])
                store(AX, RP);
                break;
            case 0b0010_0010_00_000_111: //STORE AX PC (AX -> [PC])
                store(AX, PC);
                break;
            case 0b0010_0010_00_001_000: //STORE BX AX (BX -> [AX])
                store(BX, AX);
                break;
            case 0b0010_0010_00_001_001: //STORE BX BX (BX -> [BX])
                store(BX, BX);
                break;
            case 0b0010_0010_00_001_010: //STORE BX CX (BX -> [CX])
                store(BX, CX);
                break;
            case 0b0010_0010_00_001_011: //STORE BX DX (BX -> [DX])
                store(BX, DX);
                break;
            case 0b0010_0010_00_001_100: //STORE BX EX (BX -> [EX])
                store(BX, EX);
                break;
            case 0b0010_0010_00_001_101: //STORE BX SP (BX -> [SP])
                store(BX, SP);
                break;
            case 0b0010_0010_00_001_110: //STORE BX RP (BX -> [RP])
                store(BX, RP);
                break;
            case 0b0010_0010_00_001_111: //STORE BX PC (BX -> [PC])
                store(BX, PC);
                break;
            case 0b0010_0010_00_010_000: //STORE CX AX (CX -> [AX])
                store(CX, AX);
                break;
            case 0b0010_0010_00_010_001: //STORE CX BX (CX -> [BX])
                store(CX, BX);
                break;
            case 0b0010_0010_00_010_010: //STORE CX CX (CX -> [CX])
                store(CX, CX);
                break;
            case 0b0010_0010_00_010_011: //STORE CX DX (CX -> [DX])
                store(CX, DX);
                break;
            case 0b0010_0010_00_010_100: //STORE CX EX (CX -> [EX])
                store(CX, EX);
                break;
            case 0b0010_0010_00_010_101: //STORE CX SP (CX -> [SP])
                store(CX, SP);
                break;
            case 0b0010_0010_00_010_110: //STORE CX RP (CX -> [RP])
                store(CX, RP);
                break;
            case 0b0010_0010_00_010_111: //STORE CX PC (CX -> [PC])
                store(CX, PC);
                break;
            case 0b0010_0010_00_011_000: //STORE DX AX (DX -> [AX])
                store(DX, AX);
                break;
            case 0b0010_0010_00_011_001: //STORE DX BX (DX -> [BX])
                store(DX, BX);
                break;
            case 0b0010_0010_00_011_010: //STORE DX CX (DX -> [CX])
                store(DX, CX);
                break;
            case 0b0010_0010_00_011_011: //STORE DX DX (DX -> [DX])
                store(DX, DX);
                break;
            case 0b0010_0010_00_011_100: //STORE DX EX (DX -> [EX])
                store(DX, EX);
                break;
            case 0b0010_0010_00_011_101: //STORE DX SP (DX -> [SP])
                store(DX, SP);
                break;
            case 0b0010_0010_00_011_110: //STORE DX RP (DX -> [RP])
                store(DX, RP);
                break;
            case 0b0010_0010_00_011_111: //STORE DX PC (DX -> [PC])
                store(DX, PC);
                break;
            case 0b0010_0010_00_100_000: //STORE EX AX (EX -> [AX])
                store(EX, AX);
                break;
            case 0b0010_0010_00_100_001: //STORE EX BX (EX -> [BX])
                store(EX, BX);
                break;
            case 0b0010_0010_00_100_010: //STORE EX CX (EX -> [CX])
                store(EX, CX);
                break;
            case 0b0010_0010_00_100_011: //STORE EX DX (EX -> [DX])
                store(EX, DX);
                break;
            case 0b0010_0010_00_100_100: //STORE EX EX (EX -> [EX])
                store(EX, EX);
                break;
            case 0b0010_0010_00_100_101: //STORE EX SP (EX -> [SP])
                store(EX, SP);
                break;
            case 0b0010_0010_00_100_110: //STORE EX RP (EX -> [RP])
                store(EX, RP);
                break;
            case 0b0010_0010_00_100_111: //STORE EX PC (EX -> [PC])
                store(EX, PC);
                break;
            case 0b0010_0010_00_101_000: //STORE SP AX (SP -> [AX])
                store(SP, AX);
                break;
            case 0b0010_0010_00_101_001: //STORE SP BX (SP -> [BX])
                store(SP, BX);
                break;
            case 0b0010_0010_00_101_010: //STORE SP CX (SP -> [CX])
                store(SP, CX);
                break;
            case 0b0010_0010_00_101_011: //STORE SP DX (SP -> [DX])
                store(SP, DX);
                break;
            case 0b0010_0010_00_101_100: //STORE SP EX (SP -> [EX])
                store(SP, EX);
                break;
            case 0b0010_0010_00_101_101: //STORE SP SP (SP -> [SP])
                store(SP, SP);
                break;
            case 0b0010_0010_00_101_110: //STORE SP RP (SP -> [RP])
                store(SP, RP);
                break;
            case 0b0010_0010_00_101_111: //STORE SP PC (SP -> [PC])
                store(SP, PC);
                break;
            case 0b0010_0010_00_110_000: //STORE RP AX (RP -> [AX])
                store(RP, AX);
                break;
            case 0b0010_0010_00_110_001: //STORE RP BX (RP -> [BX])
                store(RP, BX);
                break;
            case 0b0010_0010_00_110_010: //STORE RP CX (RP -> [CX])
                store(RP, CX);
                break;
            case 0b0010_0010_00_110_011: //STORE RP DX (RP -> [DX])
                store(RP, DX);
                break;
            case 0b0010_0010_00_110_100: //STORE RP EX (RP -> [EX])
                store(RP, EX);
                break;
            case 0b0010_0010_00_110_101: //STORE RP SP (RP -> [SP])
                store(RP, SP);
                break;
            case 0b0010_0010_00_110_110: //STORE RP RP (RP -> [RP])
                store(RP, RP);
                break;
            case 0b0010_0010_00_110_111: //STORE RP PC (RP -> [PC])
                store(RP, PC);
                break;
            case 0b0010_0010_00_111_000: //STORE PC AX (PC -> [AX])
                store(PC, AX);
                break;
            case 0b0010_0010_00_111_001: //STORE PC BX (PC -> [BX])
                store(PC, BX);
                break;
            case 0b0010_0010_00_111_010: //STORE PC CX (PC -> [CX])
                store(PC, CX);
                break;
            case 0b0010_0010_00_111_011: //STORE PC DX (PC -> [DX])
                store(PC, DX);
                break;
            case 0b0010_0010_00_111_100: //STORE PC EX (PC -> [EX])
                store(PC, EX);
                break;
            case 0b0010_0010_00_111_101: //STORE PC SP (PC -> [SP])
                store(PC, SP);
                break;
            case 0b0010_0010_00_111_110: //STORE PC RP (PC -> [RP])
                store(PC, RP);
                break;
            case 0b0010_0010_00_111_111: //STORE PC PC (PC -> [PC])
                store(PC, PC);
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }

    /*
     * FETCH. Load word from memory to register
     */
    private void executeFETCHInstruction() {
        switch (INST) {

            case 0b0010_0100_00_000_000: //FETCH AX AX ([AX] -> AX)
                AX = fetch(AX);
                break;
            case 0b0010_0100_00_000_001: //FETCH AX BX ([BX] -> AX)
                AX = fetch(BX);
                break;
            case 0b0010_0100_00_000_010: //FETCH AX CX ([CX] -> AX)
                AX = fetch(CX);
                break;
            case 0b0010_0100_00_000_011: //FETCH AX DX ([DX] -> AX)
                AX = fetch(DX);
                break;
            case 0b0010_0100_00_000_100: //FETCH AX EX ([EX] -> AX)
                AX = fetch(EX);
                break;
            case 0b0010_0100_00_000_101: //FETCH AX SP ([SP] -> AX)
                AX = fetch(SP);
                break;
            case 0b0010_0100_00_000_110: //FETCH AX RP ([RP] -> AX)
                AX = fetch(RP);
                break;
            case 0b0010_0100_00_000_111: //FETCH AX PC ([PC] -> AX)
                AX = fetch(PC);
                break;
            case 0b0010_0100_00_001_000: //FETCH BX AX ([AX] -> BX)
                BX = fetch(AX);
                break;
            case 0b0010_0100_00_001_001: //FETCH BX BX ([BX] -> BX)
                BX = fetch(BX);
                break;
            case 0b0010_0100_00_001_010: //FETCH BX CX ([CX] -> BX)
                BX = fetch(CX);
                break;
            case 0b0010_0100_00_001_011: //FETCH BX DX ([DX] -> BX)
                BX = fetch(DX);
                break;
            case 0b0010_0100_00_001_100: //FETCH BX EX ([EX] -> BX)
                BX = fetch(EX);
                break;
            case 0b0010_0100_00_001_101: //FETCH BX SP ([SP] -> BX)
                BX = fetch(SP);
                break;
            case 0b0010_0100_00_001_110: //FETCH BX RP ([RP] -> BX)
                BX = fetch(RP);
                break;
            case 0b0010_0100_00_001_111: //FETCH BX PC ([PC] -> BX)
                BX = fetch(PC);
                break;
            case 0b0010_0100_00_010_000: //FETCH CX AX ([AX] -> CX)
                CX = fetch(AX);
                break;
            case 0b0010_0100_00_010_001: //FETCH CX BX ([BX] -> CX)
                CX = fetch(BX);
                break;
            case 0b0010_0100_00_010_010: //FETCH CX CX ([CX] -> CX)
                CX = fetch(CX);
                break;
            case 0b0010_0100_00_010_011: //FETCH CX DX ([DX] -> CX)
                CX = fetch(DX);
                break;
            case 0b0010_0100_00_010_100: //FETCH CX EX ([EX] -> CX)
                CX = fetch(EX);
                break;
            case 0b0010_0100_00_010_101: //FETCH CX SP ([SP] -> CX)
                CX = fetch(SP);
                break;
            case 0b0010_0100_00_010_110: //FETCH CX RP ([RP] -> CX)
                CX = fetch(RP);
                break;
            case 0b0010_0100_00_010_111: //FETCH CX PC ([PC] -> CX)
                CX = fetch(PC);
                break;
            case 0b0010_0100_00_011_000: //FETCH DX AX ([AX] -> DX)
                DX = fetch(AX);
                break;
            case 0b0010_0100_00_011_001: //FETCH DX BX ([BX] -> DX)
                DX = fetch(BX);
                break;
            case 0b0010_0100_00_011_010: //FETCH DX CX ([CX] -> DX)
                DX = fetch(CX);
                break;
            case 0b0010_0100_00_011_011: //FETCH DX DX ([DX] -> DX)
                DX = fetch(DX);
                break;
            case 0b0010_0100_00_011_100: //FETCH DX EX ([EX] -> DX)
                DX = fetch(EX);
                break;
            case 0b0010_0100_00_011_101: //FETCH DX SP ([SP] -> DX)
                DX = fetch(SP);
                break;
            case 0b0010_0100_00_011_110: //FETCH DX RP ([RP] -> DX)
                DX = fetch(RP);
                break;
            case 0b0010_0100_00_011_111: //FETCH DX PC ([PC] -> DX)
                DX = fetch(PC);
                break;
            case 0b0010_0100_00_100_000: //FETCH EX AX ([AX] -> EX)
                EX = fetch(AX);
                break;
            case 0b0010_0100_00_100_001: //FETCH EX BX ([BX] -> EX)
                EX = fetch(BX);
                break;
            case 0b0010_0100_00_100_010: //FETCH EX CX ([CX] -> EX)
                EX = fetch(CX);
                break;
            case 0b0010_0100_00_100_011: //FETCH EX DX ([DX] -> EX)
                EX = fetch(DX);
                break;
            case 0b0010_0100_00_100_100: //FETCH EX EX ([EX] -> EX)
                EX = fetch(EX);
                break;
            case 0b0010_0100_00_100_101: //FETCH EX SP ([SP] -> EX)
                EX = fetch(SP);
                break;
            case 0b0010_0100_00_100_110: //FETCH EX RP ([RP] -> EX)
                EX = fetch(RP);
                break;
            case 0b0010_0100_00_100_111: //FETCH EX PC ([PC] -> EX)
                EX = fetch(PC);
                break;
            case 0b0010_0100_00_101_000: //FETCH SP AX ([AX] -> SP)
                SP = fetch(AX);
                break;
            case 0b0010_0100_00_101_001: //FETCH SP BX ([BX] -> SP)
                SP = fetch(BX);
                break;
            case 0b0010_0100_00_101_010: //FETCH SP CX ([CX] -> SP)
                SP = fetch(CX);
                break;
            case 0b0010_0100_00_101_011: //FETCH SP DX ([DX] -> SP)
                SP = fetch(DX);
                break;
            case 0b0010_0100_00_101_100: //FETCH SP EX ([EX] -> SP)
                SP = fetch(EX);
                break;
            case 0b0010_0100_00_101_101: //FETCH SP SP ([SP] -> SP)
                SP = fetch(SP);
                break;
            case 0b0010_0100_00_101_110: //FETCH SP RP ([RP] -> SP)
                SP = fetch(RP);
                break;
            case 0b0010_0100_00_101_111: //FETCH SP PC ([PC] -> SP)
                SP = fetch(PC);
                break;
            case 0b0010_0100_00_110_000: //FETCH RP AX ([AX] -> RP)
                RP = fetch(AX);
                break;
            case 0b0010_0100_00_110_001: //FETCH RP BX ([BX] -> RP)
                RP = fetch(BX);
                break;
            case 0b0010_0100_00_110_010: //FETCH RP CX ([CX] -> RP)
                RP = fetch(CX);
                break;
            case 0b0010_0100_00_110_011: //FETCH RP DX ([DX] -> RP)
                RP = fetch(DX);
                break;
            case 0b0010_0100_00_110_100: //FETCH RP EX ([EX] -> RP)
                RP = fetch(EX);
                break;
            case 0b0010_0100_00_110_101: //FETCH RP SP ([SP] -> RP)
                RP = fetch(SP);
                break;
            case 0b0010_0100_00_110_110: //FETCH RP RP ([RP] -> RP)
                RP = fetch(RP);
                break;
            case 0b0010_0100_00_110_111: //FETCH RP PC ([PC] -> RP)
                RP = fetch(PC);
                break;
            case 0b0010_0100_00_111_000: //FETCH PC AX ([AX] -> PC)
                PC = fetch(AX);
                break;
            case 0b0010_0100_00_111_001: //FETCH PC BX ([BX] -> PC)
                PC = fetch(BX);
                break;
            case 0b0010_0100_00_111_010: //FETCH PC CX ([CX] -> PC)
                PC = fetch(CX);
                break;
            case 0b0010_0100_00_111_011: //FETCH PC DX ([DX] -> PC)
                PC = fetch(DX);
                break;
            case 0b0010_0100_00_111_100: //FETCH PC EX ([EX] -> PC)
                PC = fetch(EX);
                break;
            case 0b0010_0100_00_111_101: //FETCH PC SP ([SP] -> PC)
                PC = fetch(SP);
                break;
            case 0b0010_0100_00_111_110: //FETCH PC RP ([RP] -> PC)
                PC = fetch(RP);
                break;
            case 0b0010_0100_00_111_111: //FETCH PC PC ([PC] -> PC)
                PC = fetch(PC);
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }

    /*
     * PUSH. Push register value onto a stack
     */
    private void executePUSHInstruction() {
        switch (INST) {
            case 0b0100_0000_0001_0_000: //PUSH SP AX
                push(STACK_SP, AX);
                break;
            case 0b0100_0000_0001_0_001: //PUSH SP BX
                push(STACK_SP, BX);
                break;
            case 0b0100_0000_0001_0_010: //PUSH SP CX
                push(STACK_SP, CX);
                break;
            case 0b0100_0000_0001_0_011: //PUSH SP DX
                push(STACK_SP, DX);
                break;
            case 0b0100_0000_0001_0_100: //PUSH SP EX
                push(STACK_SP, EX);
                break;
            case 0b0100_0000_0001_0_101: //PUSH SP SP
                push(STACK_SP, SP);
                break;
            case 0b0100_0000_0001_0_110: //PUSH SP RP
                push(STACK_SP, RP);
                break;
            case 0b0100_0000_0001_0_111: //PUSH SP PC
                push(STACK_SP, PC);
                break;
            case 0b0100_0000_0001_1_000: //PUSH RP AX
                push(STACK_RP, AX);
                break;
            case 0b0100_0000_0001_1_001: //PUSH RP BX
                push(STACK_RP, BX);
                break;
            case 0b0100_0000_0001_1_010: //PUSH RP CX
                push(STACK_RP, CX);
                break;
            case 0b0100_0000_0001_1_011: //PUSH RP DX
                push(STACK_RP, DX);
                break;
            case 0b0100_0000_0001_1_100: //PUSH RP EX
                push(STACK_RP, EX);
                break;
            case 0b0100_0000_0001_1_101: //PUSH RP SP
                push(STACK_RP, SP);
                break;
            case 0b0100_0000_0001_1_110: //PUSH RP RP
                push(STACK_RP, RP);
                break;
            case 0b0100_0000_0001_1_111: //PUSH RP PC
                push(STACK_RP, PC);
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }

    /*
     * POP. Pop the value from a stack into a register
     */
    private void executePOPInstruction() {
        switch (INST) {


            case 0b0100_0000_0010_0_000: //POP AX SP
                AX = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_0_001: //POP BX SP
                BX = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_0_010: //POP CX SP
                CX = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_0_011: //POP DX SP
                DX = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_0_100: //POP EX SP
                EX = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_0_101: //POP SP SP
                SP = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_0_110: //POP RP SP
                RP = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_0_111: //POP PC SP | RET SP
                PC = pop(STACK_SP);
                break;
            case 0b0100_0000_0010_1_000: //POP AX RP
                AX = pop(STACK_RP);
                break;
            case 0b0100_0000_0010_1_001: //POP BX RP
                BX = pop(STACK_RP);
                break;
            case 0b0100_0000_0010_1_010: //POP CX RP
                CX = pop(STACK_RP);
                break;
            case 0b0100_0000_0010_1_011: //POP DX RP
                DX = pop(STACK_RP);
                break;
            case 0b0100_0000_0010_1_100: //POP EX RP
                EX = pop(STACK_RP);
                break;
            case 0b0100_0000_0010_1_101: //POP SP RP
                SP = pop(STACK_RP);
                break;
            case 0b0100_0000_0010_1_110: //POP RP RP
                RP = pop(STACK_RP);
                break;
            case 0b0100_0000_0010_1_111: //POP PC RP | RET RP
                PC = pop(STACK_RP);
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }


    /*
     * CALL: Push PC to stack and Jump Immediate
     */
    private void executeCALLInstruction() {
        switch (INST) {
            case 0b0100_0000_0100_0_000: //CALL SP
                callImmediate(STACK_SP);
                break;
            case 0b0100_0000_0100_1_000: //CALL RP
                callImmediate(STACK_RP);
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }


    /*
     * WRDIN. RECEIVE I/O WORD
     */
    private void executeWRDINInstruction() {
        switch (INST) {

            case (short) 0b1000_0000_0000_0_000: //WRDIN AX
                AX = wordIn();
                break;
            case (short) 0b1000_0000_0000_0_001: //WRDIN BX
                BX = wordIn();
                break;
            case (short) 0b1000_0000_0000_0_010: //WRDIN CX
                CX = wordIn();
                break;
            case (short) 0b1000_0000_0000_0_011: //WRDIN DX
                DX = wordIn();
                break;
            case (short) 0b1000_0000_0000_0_100: //WRDIN EX
                EX = wordIn();
                break;
            case (short) 0b1000_0000_0000_0_101: //WRDIN SP
                SP = wordIn();
                break;
            case (short) 0b1000_0000_0000_0_110: //WRDIN RP
                RP = wordIn();
                break;
            case (short) 0b1000_0000_0000_0_111: //WRDIN PC
                PC = wordIn();
                break;

            default:
                unknownInstruction(INST);
                break;
        }
    }


    /*
     * WRDOUT. Send I/O word
     */
    private void executeWRDOUTInstruction() {
        switch (INST) {

            case (short) 0b1000_1000_0000_0_000: //WRDOUT AX
                wordOut(AX);
                break;
            case (short) 0b1000_1000_0000_0_001: //WRDOUT BX
                wordOut(BX);
                break;
            case (short) 0b1000_1000_0000_0_010: //WRDOUT CX
                wordOut(CX);
                break;
            case (short) 0b1000_1000_0000_0_011: //WRDOUT DX
                wordOut(DX);
                break;
            case (short) 0b1000_1000_0000_0_100: //WRDOUT EX
                wordOut(EX);
                break;
            case (short) 0b1000_1000_0000_0_101: //WRDOUT SP
                wordOut(SP);
                break;
            case (short) 0b1000_1000_0000_0_110: //WRDOUT RP
                wordOut(RP);
                break;
            case (short) 0b1000_1000_0000_0_111: //WRDOUT PC
                wordOut(PC);
                break;
            default:
                unknownInstruction(INST);
                break;
        }
    }


    private void unknownInstruction(short instruction) {
        throw new IllegalArgumentException("Unknown instruction: "
                + Integer.toBinaryString(0xFFFF & instruction));
    }


    private short aluIncrement(short inputRegister) {
        TMP1X = inputRegister;
        int output = TMP1X + 1;
        updateConditionCodes(output);
        return (short) output;
    }

    private short aluDecrement(short inputRegister) {
        TMP1X = inputRegister;
        int output = TMP1X - 1;
        updateConditionCodes(output);
        return (short) output;
    }

    private short aluNegate(short inputRegister) {
        TMP1X = inputRegister;
        short output = (short) ~TMP1X;
        carry = false;
        overflow = false;
        zero = output == 0;
        sign = output < 0;
        return output;
    }

    private short aluLeftRotation(short inputRegister) {
        TMP1X = inputRegister;
        short output = (short) (((TMP1X & 0xffff) << 1) | ((TMP1X & 0xffff) >>> (16 - 1)));
        carry = false;
        overflow = false;
        zero = output == 0;
        sign = output < 0;
        return output;
    }

    private short aluAdd(short inputRegister, short inputRegister2) {
        TMP1X = inputRegister;
        TMP2X = inputRegister2;
        int output = TMP1X + TMP2X;
        updateConditionCodes(output);
        return (short) output;
    }

    private short aluAnd(short inputRegister, short inputRegister2) {
        TMP1X = inputRegister;
        TMP2X = inputRegister2;
        int output = TMP1X & TMP2X;
        updateConditionCodes(output);
        return (short) output;
    }

    private short aluOr(short inputRegister, short inputRegister2) {
        TMP1X = inputRegister;
        TMP2X = inputRegister2;
        int output = TMP1X | TMP2X;
        updateConditionCodes(output);
        return (short) output;
    }

    private short aluXor(short inputRegister, short inputRegister2) {
        TMP1X = inputRegister;
        TMP2X = inputRegister2;
        int output = TMP1X ^ TMP2X;
        updateConditionCodes(output);
        return (short) output;
    }

    private short compareSubtract(short inputRegister, short inputRegister2) {
        TMP1X = inputRegister;
        TMP2X = inputRegister2;
        int output = TMP1X - TMP2X;
        updateConditionCodes(output);
        return (short) output;
    }

    private void updateConditionCodes(int aluOutput) {
        carry = aluOutput > Character.MAX_VALUE;
        overflow = aluOutput > Short.MAX_VALUE || aluOutput < Short.MIN_VALUE;
        zero = ((short) aluOutput) == 0;
        sign = ((short) aluOutput < 0);
    }

    private short load() {
        TMP1X = mainMemory[(char) PC]; //Get the value in mem following instruction
        PC++; //Increment PC to next mem addr
        return TMP1X;
    }

    private void store(short sourceRegister, short destinationRegister) {
        mainMemory[(char) destinationRegister] = sourceRegister; //Put the value of source into mem pointed to by dest
    }

    private short fetch(short sourceRegister) {
        short memValue = mainMemory[(char) sourceRegister]; //Get the value in mem pointed to by source
        return memValue;
    }

    private void push(StackReg stackRegister, short sourceRegister) {
        if (stackRegister == STACK_SP) {
            SP++; //Increment the stack pointer to the new TOS
            mainMemory[(char) SP] = sourceRegister; //Insert value at TOS
        } else {
            RP++; //Increment the stack pointer to the new TOS
            mainMemory[(char) RP] = sourceRegister; //Insert value at TOS

        }
    }

    private short pop(StackReg stackRegister) {
        if (stackRegister == STACK_SP) {
            short value = mainMemory[(char) SP];//Get value at TOS
            SP--; //Decrement the stack pointer to the new TOS
            return value;
        } else {
            short value = mainMemory[(char) RP];//Get value at TOS
            RP--; //Decrement the stack pointer to the new TOS
            return value;
        }
    }

    private void callImmediate(StackReg stackRegister) {
        TMP1X = mainMemory[(char) PC]; //Get the value in mem following instruction
        PC++; //Increment PC to next mem addr

        if (stackRegister == STACK_SP) {
            SP++; //Increment the stack pointer to the new TOS
            mainMemory[(char) SP] = PC; //Insert PC value at TOS
        } else {
            RP++; //Increment the stack pointer to the new TOS
            mainMemory[(char) RP] = PC; //Insert PC value at TOS
        }

        PC = TMP1X; //MOV the value into PC to jump
    }

    private void call(StackReg stackRegister, short sourceRegister) {
        if (stackRegister == STACK_SP) {
            SP++; //Increment the stack pointer to the new TOS
            mainMemory[(char) SP] = PC; //Insert PC value at TOS
        } else {
            RP++; //Increment the stack pointer to the new TOS
            mainMemory[(char) RP] = PC; //Insert PC value at TOS
        }

        PC = sourceRegister; //MOV the value of the source into PC to jump
    }

    private short wordIn() {
        if (ioDevice.hasWord()) {
            zero = false;
            return ioDevice.getWord();
        } else {
            //There is no input word
            zero = true;
            return 0;
        }
    }

    private void wordOut(short sourceRegister) {
        ioDevice.sendWord(sourceRegister);
    }

    public boolean isZero() {
        return zero;
    }

    public boolean isCarry() {
        return carry;
    }

    public boolean isSign() {
        return sign;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public short getINST() {
        return INST;
    }

    public short getPC() {
        return PC;
    }

    public short getSP() {
        return SP;
    }

    public short getRP() {
        return RP;
    }

    public short getAX() {
        return AX;
    }

    public short getBX() {
        return BX;
    }

    public short getCX() {
        return CX;
    }

    public short getDX() {
        return DX;
    }

    public short getEX() {
        return EX;
    }

    public short getTMP1X() {
        return TMP1X;
    }

    public short getTMP2X() {
        return TMP2X;
    }

    public short[] getMainMemory() {
        return mainMemory;
    }

    public void setMainMemory(short[] mainMemory) {
        this.mainMemory = mainMemory;
    }

    public IODevice getIoDevice() {
        return ioDevice;
    }

    public boolean isHalted() {
        return halted;
    }
}
