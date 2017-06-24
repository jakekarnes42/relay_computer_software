package org.karnes.homebrew.emulator;

import static org.karnes.homebrew.Util.getHighByteFromChar;
import static org.karnes.homebrew.Util.getLowByteFromChar;
import static org.karnes.homebrew.Util.twoBytesToChar;

/**
 * A modified emulator for the Relay Computer which is designed to support eForth
 */
public class ForthRelayComputer {

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


    // 8-bit registers
    /**
     * Holds the current executing instruction
     */
    private byte INST = 0;

    //16 bit registers. Using char since they are unsigned 16 bit integers
    /**
     * The program counter register.
     * At the start of fetch-decode-execute cycle,
     * it points to the memory address containing the instruction to execute in that cycle.
     * By the end of the execute phase, it points to the memory address to execute.
     */
    private char PC = 0;
    /**
     * Interpreter Pointer. Similar to PC, but points to words in the word list.
     */
    private char IP = 0;
    /**
     * Data Stack Pointer. Points to the memory address of the top most item on the data stack. Grows downward.
     */
    private char SP = 0;
    /**
     * Return Stack Pointer. Points to the memory address of the top most item on the return stack. Grows downward.
     */
    private char RP = 0;
    /**
     * General purpose. Can be the destination of ALU operations.
     */
    private char AX = 0;
    /**
     * General purpose. First input to ALU functions that require 1 or 2 operands
     */
    private char BX = 0;
    /**
     * General purpose. Second input to ALU functions that require 2 operands
     */
    private char CX = 0;
    /**
     * General purpose. Can be the destination of ALU operations.
     */
    private char DX = 0;
    /**
     * Used internally by the CPU as the "scratch pad." Temporary values may be written and trashed here for other ops
     */
    private char TMPX = 0;

    /**
     * Main memory
     */
    private byte[] mainMemory = new byte[(1 << 16)];

    /**
     * I/O Device
     */
    private IODevice ioDevice = new JavaSimulatedIODevice();

    //Emulator specific: Indicator that the computer should stop
    private boolean halted = false;


    public void start() {
        while (!halted) {
            //Fetch: Loads the byte at memory address pointed to by PC, into the INST register.
            INST = mainMemory[PC];

            //Increment: Increment the PC register
            PC = (char) (PC + 1);

            //Execute
            executeCurrentInstruction();
        }
    }

    private void executeCurrentInstruction() {
        switch (INST) {
            /*
             * MOV destReg srcReg
             * If dest and srcReg are the same, zero that register.
             */
            case 0b00000000: //MOV AX AX | CLR AX
                AX = 0;
                break;
            case 0b00000001: //MOV AX BX
                AX = BX;
                break;
            case 0b00000010: //MOV AX CX
                AX = CX;
                break;
            case 0b00000011: //MOV AX DX
                AX = DX;
                break;
            case 0b00000100: //MOV AX IP
                AX = IP;
                break;
            case 0b00000101: //MOV AX SP
                AX = SP;
                break;
            case 0b00000110: //MOV AX RP
                AX = RP;
                break;
            case 0b00000111: //MOV AX PC
                AX = PC;
                break;
            case 0b00001000: //MOV BX AX
                BX = AX;
                break;
            case 0b00001001: //MOV BX BX | CLR BX
                BX = 0;
                break;
            case 0b00001010: //MOV BX CX
                BX = CX;
                break;
            case 0b00001011: //MOV BX DX
                BX = DX;
                break;
            case 0b00001100: //MOV BX IP
                BX = IP;
                break;
            case 0b00001101: //MOV BX SP
                BX = SP;
                break;
            case 0b00001110: //MOV BX RP
                BX = RP;
                break;
            case 0b00001111: //MOV BX PC
                BX = PC;
                break;
            case 0b00010000: //MOV CX AX
                CX = AX;
                break;
            case 0b00010001: //MOV CX BX
                CX = BX;
                break;
            case 0b00010010: //MOV CX CX | CLR CX
                CX = 0;
                break;
            case 0b00010011: //MOV CX DX
                CX = DX;
                break;
            case 0b00010100: //MOV CX IP
                CX = IP;
                break;
            case 0b00010101: //MOV CX SP
                CX = SP;
                break;
            case 0b00010110: //MOV CX RP
                CX = RP;
                break;
            case 0b00010111: //MOV CX PC
                CX = PC;
                break;
            case 0b00011000: //MOV DX AX
                DX = AX;
                break;
            case 0b00011001: //MOV DX BX
                DX = BX;
                break;
            case 0b00011010: //MOV DX CX
                DX = CX;
                break;
            case 0b00011011: //MOV DX DX | CLR DX
                DX = 0;
                break;
            case 0b00011100: //MOV DX IP
                DX = IP;
                break;
            case 0b00011101: //MOV DX SP
                DX = SP;
                break;
            case 0b00011110: //MOV DX RP
                DX = RP;
                break;
            case 0b00011111: //MOV DX PC
                DX = PC;
                break;
            case 0b00100000: //MOV IP AX
                IP = AX;
                break;
            case 0b00100001: //MOV IP BX
                IP = BX;
                break;
            case 0b00100010: //MOV IP CX
                IP = CX;
                break;
            case 0b00100011: //MOV IP DX
                IP = DX;
                break;
            case 0b00100100: //MOV IP IP | CLR IP
                IP = 0;
                break;
            case 0b00100101: //MOV IP SP
                IP = SP;
                break;
            case 0b00100110: //MOV IP RP
                IP = RP;
                break;
            case 0b00100111: //MOV IP PC
                IP = PC;
                break;
            case 0b00101000: //MOV SP AX
                SP = AX;
                break;
            case 0b00101001: //MOV SP BX
                SP = BX;
                break;
            case 0b00101010: //MOV SP CX
                SP = CX;
                break;
            case 0b00101011: //MOV SP DX
                SP = DX;
                break;
            case 0b00101100: //MOV SP IP
                SP = IP;
                break;
            case 0b00101101: //MOV SP SP | CLR SP
                SP = 0;
                break;
            case 0b00101110: //MOV SP RP
                SP = RP;
                break;
            case 0b00101111: //MOV SP PC
                SP = PC;
                break;
            case 0b00110000: //MOV RP AX
                RP = AX;
                break;
            case 0b00110001: //MOV RP BX
                RP = BX;
                break;
            case 0b00110010: //MOV RP CX
                RP = CX;
                break;
            case 0b00110011: //MOV RP DX
                RP = DX;
                break;
            case 0b00110100: //MOV RP IP
                RP = IP;
                break;
            case 0b00110101: //MOV RP SP
                RP = SP;
                break;
            case 0b00110110: //MOV RP RP | CLR RP
                RP = 0;
                break;
            case 0b00110111: //MOV RP PC
                RP = PC;
                break;
            case 0b00111000: //MOV PC AX | JMP AX
                PC = AX;
                break;
            case 0b00111001: //MOV PC BX | JMP BX
                PC = BX;
                break;
            case 0b00111010: //MOV PC CX | JMP CX
                PC = CX;
                break;
            case 0b00111011: //MOV PC DX | JMP DX
                PC = DX;
                break;
            case 0b00111100: //MOV PC IP | JMP IP
                PC = IP;
                break;
            case 0b00111101: //MOV PC SP | JMP SP
                PC = SP;
                break;
            case 0b00111110: //MOV PC RP | JMP RP
                PC = RP;
                break;
            case 0b00111111: //MOV PC PC | CLR PC | JMP 0
                PC = 0;
                break;

            /*
             * There is no 000 ALU function code. This is when the ALU is "off"
             * This is NOT how Harry's instruction set works. For his, 111 is "off"
             * All ALU function codes are incremented by one (compared to Harry's)
             */
            case 0b01000000: //NOT USED. ALU has no assigned value for this
                throw new IllegalStateException("There is no 000 ALU function. Did you forget to convert from Harry's instructions?");
            case 0b01001000: //NOT USED. ALU has no assigned value for this
                throw new IllegalStateException("There is no 000 ALU function. Did you forget to convert from Harry's instructions?");

            /*
             * Addition of BX and CX into either AX or DX.
             */
            case 0b01000001: // AX = BX + CX
                AX = (char) (BX + CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (AX == 0);
                sign = ((short) AX < 0);
                break;
            case 0b01001001: // DX = BX + CX
                DX = (char) (BX + CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (DX == 0);
                sign = ((short) DX < 0);
                break;

            /*
             * Increment BX into AX or DX.
             */
            case 0b01000010: // AX = INC BX
                AX = (char) (BX + 1);
                carry = (BX + 1) > Character.MAX_VALUE;
                overflow = (BX + 1) > Short.MAX_VALUE || (BX + 1) < Short.MIN_VALUE;
                zero = (AX == 0);
                sign = ((short) AX < 0);
                break;
            case 0b01001010: // DX = INC BX
                DX = (char) (BX + 1);
                carry = (BX + 1) > Character.MAX_VALUE;
                overflow = (BX + 1) > Short.MAX_VALUE || (BX + 1) < Short.MIN_VALUE;
                zero = (DX == 0);
                sign = ((short) DX < 0);
                break;

            /*
             * Bitwise AND of BX and CX into AX or DX.
             */
            case 0b01000011: // AX = BX AND CX
                AX = (char) (BX & CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (AX == 0);
                sign = ((short) AX < 0);
                break;
            case 0b01001011: // D = B AND C
                DX = (char) (BX & CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (DX == 0);
                sign = ((short) DX < 0);
                break;

            /*
             * Bitwise OR of BX and CX into AX or DX
             */
            case 0b01000100: // AX = BX OR CX
                AX = (char) (BX | CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (AX == 0);
                sign = ((short) AX < 0);
                break;
            case 0b01001100: // DX = BX OR CX
                DX = (char) (BX | CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (DX == 0);
                sign = ((short) DX < 0);
                break;

            /*
             * XOR of BX and CX into AX or DX
             */
            case 0b01000101: // AX = BX XOR CX
                AX = (char) (BX ^ CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (AX == 0);
                sign = ((short) AX < 0);
                break;
            case 0b01001101: // DX = BX XOR CX
                DX = (char) (BX ^ CX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (DX == 0);
                sign = ((short) DX < 0);
                break;

            /*
             * Negation of BX into AX or DX
             */
            case 0b01000110: // AX = NOT BX
                AX = (char) (~BX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (AX == 0);
                sign = ((short) AX < 0);
                break;
            case 0b01001110: // DX = NOT BX
                DX = (char) (~BX);
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (DX == 0);
                sign = ((short) DX < 0);
                break;

            /*
             * Left Rotation of BX by one bit into AX or DX
             */
            case 0b01000111: // A = ROL B
                AX = (char) (((BX & 0xffff) << 1) | ((BX & 0xffff) >>> (16 - 1)));
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (AX == 0);
                sign = ((short) AX < 0);
                break;
            case 0b01001111: // D = ROL B
                DX = (char) (((BX & 0xffff) << 1) | ((BX & 0xffff) >>> (16 - 1)));
                carry = (BX + CX) > Character.MAX_VALUE;
                overflow = (BX + CX) > Short.MAX_VALUE || (BX + CX) < Short.MIN_VALUE;
                zero = (DX == 0);
                sign = ((short) DX < 0);
                break;

            /*
             * LOAD: Immediately load word from memory to register
             */
            case 0b01010_000: //LOAD AX
                AX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into AX as a word.
                PC += 2; //Increment PC to point to next memory address
                break;
            case 0b01010_001: //LOAD BX
                BX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into BX as a word.
                PC += 2; //Increment PC to point to next memory address
                break;
            case 0b01010_010: //LOAD CX
                CX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into CX as a word.
                PC += 2; //Increment PC to point to next memory address
                break;
            case 0b01010_011: //LOAD DX
                DX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into DX as a word.
                PC += 2; //Increment PC to point to next memory address
                break;
            case 0b01010_100: //LOAD IP
                IP = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into IP as a word.
                PC += 2; //Increment PC to point to next memory address
                break;
            case 0b01010_101: //LOAD SP
                SP = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into SP as a word.
                PC += 2; //Increment PC to point to next memory address
                break;
            case 0b01010_110: //LOAD RP
                RP = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into RP as a word.
                PC += 2; //Increment PC to point to next memory address
                break;

            /*
             * JMP (unconditional): Jump to the address of following word in memory.
             */
            case 0b01010_111: //JMP <address>
                TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                PC = TMPX; //Jump to TMPX
                break;
                
            /*
             * PUSH: Push value of register onto stack
             */
            case 0b0110_0_000: //PUSH SP AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_0_001: //PUSH SP BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_0_010: //PUSH SP CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_0_011: //PUSH SP DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_0_100: //PUSH SP IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_0_101: //PUSH SP SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_0_110: //PUSH SP RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_0_111: //PUSH SP PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[SP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_000: //PUSH RP AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_001: //PUSH RP BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_010: //PUSH RP CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_011: //PUSH RP DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_100: //PUSH RP IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_101: //PUSH RP SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_110: //PUSH RP RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;
            case 0b0110_1_111: //PUSH RP PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[RP + 2] = getHighByteFromChar(TMPX); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS
                break;

            /*
             * POP: Pop value off of stack into register
             */
            case 0b0111_0_000: //POP SP AX
                AX = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_0_001: //POP SP BX
                BX = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_0_010: //POP SP CX
                CX = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_0_011: //POP SP DX
                DX = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_0_100: //POP SP IP
                IP = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_0_101: //POP SP SP
                SP = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_0_110: //POP SP RP
                RP = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_0_111: //POP SP PC
                PC = twoBytesToChar(mainMemory[SP], mainMemory[SP + 1]); //Insert the current value of the source stack pointer into the destination register
                SP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_000: //POP RP AX
                AX = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_001: //POP RP BX
                BX = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_010: //POP RP CX
                CX = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_011: //POP RP DX
                DX = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_100: //POP RP IP
                IP = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_101: //POP RP SP
                SP = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_110: //POP RP RP
                RP = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;
            case 0b0111_1_111: //POP RP PC
                PC = twoBytesToChar(mainMemory[RP], mainMemory[RP + 1]); //Insert the current value of the source stack pointer into the destination register
                RP -= 2; //Decrement the destination stack pointer so that it points to the new TOS
                break;

           /*
            * HALT: Ends execution
            */
            case (byte) 0b100_0_0000: //HALT
                halted = true;
                break;
            /*
             * Conditional Jumps: JMP to address if flag. 0b100_n_ocsz
             */
            case (byte) 0b100_0_0001: //JZ <addr>
                if (zero) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;
            case (byte) 0b100_0_0010: //JNEG <addr>
                if (sign) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;
            case (byte) 0b100_0_0100: //JC <addr>
                if (carry) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;
            case (byte) 0b100_0_1000: //JO <addr>
                if (overflow) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;
            case (byte) 0b100_1_0001: //JNZ <addr>
                if (!zero) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;
            case (byte) 0b100_1_0010: //JNNEG <addr>
                if (!sign) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;
            case (byte) 0b100_1_0100: //JNC <addr>
                if (!carry) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;
            case (byte) 0b100_1_1000: //JNO <addr>
                if (!overflow) {
                    TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.
                    PC = TMPX; //Jump to TMPX
                } else {
                    PC += 2; //Increment PC over the operand since we're not taking this jump.
                }
                break;

            /*
             * CALL: Push PC to stack and Jump
             */
            case (byte) 0b1011_0_000: //CALL SP <addr>
                //Get target address into TMPX
                TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.

                PC += 2; //Point PC at next instruction

                //Push next instruction address to SP
                mainMemory[SP + 2] = getHighByteFromChar(PC); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[SP + 3] = getLowByteFromChar(PC); //Store the low byte into the next mem address after that
                SP += 2; //Increment the destination stack pointer so that it points to the new TOS

                //Jump to target address
                PC = TMPX; //Jump to TMPX
                break;
            case (byte) 0b1011_1_000: //CALL RP <addr>
                //Get target address into TMPX
                TMPX = twoBytesToChar(mainMemory[PC], mainMemory[PC + 1]); //Load next two bytes into TMPX as a word.

                PC += 2; //Point PC at next instruction

                //Push next instruction address to SP
                mainMemory[RP + 2] = getHighByteFromChar(PC); //Store the high byte into the mem address after the current TOS value's low byte
                mainMemory[RP + 3] = getLowByteFromChar(PC); //Store the low byte into the next mem address after that
                RP += 2; //Increment the destination stack pointer so that it points to the new TOS

                //Jump to target address
                PC = TMPX; //Jump to TMPX
                break;

            /*
             * NOP: Do nothing
             */
            case (byte) 0b10111111: //NOP
                break;

            /*
             * BYIN: RECEIVE I/O BYTE
             */
            case (byte) 0b1010_0_000: //BYIN AX
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    AX = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;
            case (byte) 0b1010_0_001: //BYIN BX
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    BX = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;
            case (byte) 0b1010_0_010: //BYIN CX
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    CX = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;
            case (byte) 0b1010_0_011: //BYIN DX
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    DX = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;
            case (byte) 0b1010_0_100: //BYIN IP
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    IP = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;
            case (byte) 0b1010_0_101: //BYIN SP
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    SP = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;
            case (byte) 0b1010_0_110: //BYIN RP
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    RP = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;
            case (byte) 0b1010_0_111: //BYIN PC
                if (ioDevice.hasByte()) {
                    byte input = ioDevice.getByte();
                    PC = twoBytesToChar((byte) 0, input);
                } else {
                    zero = true; //No input byte ready. Set zero to true.
                }
                break;

            /*
             * BYOUT: SEND I/O BYTE
             */
            case (byte) 0b1010_1_000: //BYOUT AX
                byte output = getLowByteFromChar(AX);
                ioDevice.sendByte(output);
                break;
            case (byte) 0b1010_1_001: //BYOUT BX
                byte output1 = getLowByteFromChar(BX);
                ioDevice.sendByte(output1);
                break;
            case (byte) 0b1010_1_010: //BYOUT CX
                byte output2 = getLowByteFromChar(CX);
                ioDevice.sendByte(output2);
                break;
            case (byte) 0b1010_1_011: //BYOUT DX
                byte output3 = getLowByteFromChar(DX);
                ioDevice.sendByte(output3);
                break;
            case (byte) 0b1010_1_100: //BYOUT IP
                byte output4 = getLowByteFromChar(IP);
                ioDevice.sendByte(output4);
                break;
            case (byte) 0b1010_1_101: //BYOUT SP
                byte output5 = getLowByteFromChar(SP);
                ioDevice.sendByte(output5);
                break;
            case (byte) 0b1010_1_110: //BYOUT RP
                byte output6 = getLowByteFromChar(RP);
                ioDevice.sendByte(output6);
                break;
            case (byte) 0b1010_1_111: //BYOUT PC
                byte output7 = getLowByteFromChar(PC);
                ioDevice.sendByte(output7);
                break;


            /*
             * STORE: Store register value into memory. STR [DST] SRC 
             */
            case (byte) 0b11_000_000: //STR [AX] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_000_001: //STR [AX] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_000_010: //STR [AX] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_000_011: //STR [AX] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_000_100: //STR [AX] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_000_101: //STR [AX] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_000_110: //STR [AX] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_000_111: //STR [AX] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[AX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[AX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_000: //STR [BX] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_001: //STR [BX] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_010: //STR [BX] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_011: //STR [BX] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_100: //STR [BX] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_101: //STR [BX] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_110: //STR [BX] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_001_111: //STR [BX] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[BX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[BX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_000: //STR [CX] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_001: //STR [CX] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_010: //STR [CX] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_011: //STR [CX] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_100: //STR [CX] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_101: //STR [CX] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_110: //STR [CX] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_010_111: //STR [CX] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[CX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[CX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_000: //STR [DX] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_001: //STR [DX] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_010: //STR [DX] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_011: //STR [DX] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_100: //STR [DX] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_101: //STR [DX] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_110: //STR [DX] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_011_111: //STR [DX] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[DX] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[DX + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_000: //STR [IP] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_001: //STR [IP] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_010: //STR [IP] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_011: //STR [IP] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_100: //STR [IP] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_101: //STR [IP] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_110: //STR [IP] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_100_111: //STR [IP] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[IP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[IP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_000: //STR [SP] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_001: //STR [SP] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_010: //STR [SP] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_011: //STR [SP] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_100: //STR [SP] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_101: //STR [SP] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_110: //STR [SP] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_101_111: //STR [SP] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[SP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[SP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_000: //STR [RP] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_001: //STR [RP] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_010: //STR [RP] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_011: //STR [RP] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_100: //STR [RP] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_101: //STR [RP] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_110: //STR [RP] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_110_111: //STR [RP] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[RP] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[RP + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_000: //STR [PC] AX
                TMPX = AX; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_001: //STR [PC] BX
                TMPX = BX; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_010: //STR [PC] CX
                TMPX = CX; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_011: //STR [PC] DX
                TMPX = DX; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_100: //STR [PC] IP
                TMPX = IP; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_101: //STR [PC] SP
                TMPX = SP; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_110: //STR [PC] RP
                TMPX = RP; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;
            case (byte) 0b11_111_111: //STR [PC] PC
                TMPX = PC; //Save the value so we can rip it up.
                mainMemory[PC] = getHighByteFromChar(TMPX); //Store the high byte into the first mem address
                mainMemory[PC + 1] = getLowByteFromChar(TMPX); //Store the low byte into the next mem address
                break;


            default:
                throw new IllegalStateException("Unable to decode instruction: " + INST);
        }

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

    public byte getINST() {
        return INST;
    }

    public char getPC() {
        return PC;
    }

    public char getIP() {
        return IP;
    }

    public char getSP() {
        return SP;
    }

    public char getRP() {
        return RP;
    }

    public char getAX() {
        return AX;
    }

    public char getBX() {
        return BX;
    }

    public char getCX() {
        return CX;
    }

    public char getDX() {
        return DX;
    }

    public char getTMPX() {
        return TMPX;
    }

    public byte[] getMainMemory() {
        return mainMemory;
    }

    public boolean isHalted() {
        return halted;
    }

    public void setMainMemory(byte[] mainMemory) {
        this.mainMemory = mainMemory;
    }
}
