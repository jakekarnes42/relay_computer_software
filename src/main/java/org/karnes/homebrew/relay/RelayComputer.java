package org.karnes.homebrew.relay;

/**
 * A simple emulator for the Relay Computer. This doesn't actually perform all the functions and steps, rather can perform each of the instructions.
 */
public class RelayComputer {

    //1 bit status registers
    private boolean zero = false;
    private boolean carry = false;
    private boolean sign = false;

    // 8-bit registers
    private byte aRegister = 0;
    private byte bRegister = 0;
    private byte cRegister = 0;
    private byte dRegister = 0;
    private byte m1Register = 0;
    private byte m2Register = 0;
    private byte xRegister = 0;
    private byte yRegister = 0;
    private byte j1Register = 0;
    private byte j2Register = 0;
    private byte instRegister = 0;

    //16 bit registers. Using char since they are unsigned 16 bit integers
    private char pcRegister = 0;
    private char incRegister = 0;

    //Main Memory
    private byte[] mainMemory = new byte[(1 << 16)];

    //Indicator that the computer should stop
    private boolean halted = false;


    public void start() {
        while (!halted) {
            //Fetch: Loads the byte at memory address pointed to by PC, into the INST register.
            instRegister = mainMemory[pcRegister];

            //Increment: Increment the INC register, and move that into the PC register
            incRegister = (char) (pcRegister + 1);
            pcRegister = incRegister;

            //Execute
            executeCurrentInstruction();
        }
    }

    private void executeCurrentInstruction() {
        switch (instRegister) {
            /*
             * MOV destReg srcReg
             * If dest and srcReg are the same, zero that register.
             */
            case 0b00000000: //MOV A A | CLEAR A
                aRegister = 0;
                break;
            case 0b00000001: //MOV A B
                aRegister = bRegister;
                break;
            case 0b00000010: //MOV A C
                aRegister = cRegister;
                break;
            case 0b00000011: //MOV A D
                aRegister = dRegister;
                break;
            case 0b00000100: //MOV A M1
                aRegister = m1Register;
                break;
            case 0b00000101: //MOV A M2
                aRegister = m2Register;
                break;
            case 0b00000110: //MOV A X
                aRegister = xRegister;
                break;
            case 0b00000111: //MOV A Y
                aRegister = yRegister;
                break;
            case 0b00001000: //MOV B A
                bRegister = aRegister;
                break;
            case 0b00001001: //MOV B B | CLEAR B
                bRegister = 0;
                break;
            case 0b00001010: //MOV B C
                bRegister = cRegister;
                break;
            case 0b00001011: //MOV B D
                bRegister = dRegister;
                break;
            case 0b00001100: //MOV B M1
                bRegister = m1Register;
                break;
            case 0b00001101: //MOV B M2
                bRegister = m2Register;
                break;
            case 0b00001110: //MOV B X
                bRegister = xRegister;
                break;
            case 0b00001111: //MOV B Y
                bRegister = yRegister;
                break;
            case 0b00010000: //MOV C A
                cRegister = aRegister;
                break;
            case 0b00010001: //MOV C B
                cRegister = bRegister;
                break;
            case 0b00010010: //MOV C C | CLEAR C
                cRegister = 0;
                break;
            case 0b00010011: //MOV C D
                cRegister = dRegister;
                break;
            case 0b00010100: //MOV C M1
                cRegister = m1Register;
                break;
            case 0b00010101: //MOV C M2
                cRegister = m2Register;
                break;
            case 0b00010110: //MOV C X
                cRegister = xRegister;
                break;
            case 0b00010111: //MOV C Y
                cRegister = yRegister;
                break;
            case 0b00011000: //MOV D A
                dRegister = aRegister;
                break;
            case 0b00011001: //MOV D B
                dRegister = bRegister;
                break;
            case 0b00011010: //MOV D C
                dRegister = cRegister;
                break;
            case 0b00011011: //MOV D D | CLEAR D
                dRegister = 0;
                break;
            case 0b00011100: //MOV D M1
                dRegister = m1Register;
                break;
            case 0b00011101: //MOV D M2
                dRegister = m2Register;
                break;
            case 0b00011110: //MOV D X
                dRegister = xRegister;
                break;
            case 0b00011111: //MOV D Y
                dRegister = yRegister;
                break;
            case 0b00100000: //MOV M1 A
                m1Register = aRegister;
                break;
            case 0b00100001: //MOV M1 B
                m1Register = bRegister;
                break;
            case 0b00100010: //MOV M1 C
                m1Register = cRegister;
                break;
            case 0b00100011: //MOV M1 D
                m1Register = dRegister;
                break;
            case 0b00100100: //MOV M1 M1 | CLEAR M1
                m1Register = 0;
                break;
            case 0b00100101: //MOV M1 M2
                m1Register = m2Register;
                break;
            case 0b00100110: //MOV M1 X
                m1Register = xRegister;
                break;
            case 0b00100111: //MOV M1 Y
                m1Register = yRegister;
                break;
            case 0b00101000: //MOV M2 A
                m2Register = aRegister;
                break;
            case 0b00101001: //MOV M2 B
                m2Register = bRegister;
                break;
            case 0b00101010: //MOV M2 C
                m2Register = cRegister;
                break;
            case 0b00101011: //MOV M2 D
                m2Register = dRegister;
                break;
            case 0b00101100: //MOV M2 M1
                m2Register = m1Register;
                break;
            case 0b00101101: //MOV M2 M2 | CLEAR M2
                m2Register = 0;
                break;
            case 0b00101110: //MOV M2 X
                m2Register = xRegister;
                break;
            case 0b00101111: //MOV M2 Y
                m2Register = yRegister;
                break;
            case 0b00110000: //MOV X A
                xRegister = aRegister;
                break;
            case 0b00110001: //MOV X B
                xRegister = bRegister;
                break;
            case 0b00110010: //MOV X C
                xRegister = cRegister;
                break;
            case 0b00110011: //MOV X D
                xRegister = dRegister;
                break;
            case 0b00110100: //MOV X M1
                xRegister = m1Register;
                break;
            case 0b00110101: //MOV X M2
                xRegister = m2Register;
                break;
            case 0b00110110: //MOV X X | CLEAR X
                xRegister = 0;
                break;
            case 0b00110111: //MOV X Y
                xRegister = yRegister;
                break;
            case 0b00111000: //MOV Y A
                yRegister = aRegister;
                break;
            case 0b00111001: //MOV Y B
                yRegister = bRegister;
                break;
            case 0b00111010: //MOV Y C
                yRegister = cRegister;
                break;
            case 0b00111011: //MOV Y D
                yRegister = dRegister;
                break;
            case 0b00111100: //MOV Y M1
                yRegister = m1Register;
                break;
            case 0b00111101: //MOV Y M2
                yRegister = m2Register;
                break;
            case 0b00111110: //MOV Y X
                yRegister = xRegister;
                break;
            case 0b00111111: //MOV Y Y | CLEAR Y
                yRegister = 0;
                break;

            /*
             * There is no 000 ALU function code. This is when the ALU is "off"
             * This is NOT how Harry's instruction set works. For his, 111 is "off"
             * All ALU function codes are incremented by one (compared to Harry's)
             */
            case (byte) 0b10000000: //NOT USED. ALU has no assigned value for this
                throw new IllegalStateException("There is no 000 ALU function. Did you forget to convert from Harry's instructions?");
            case (byte) 0b10001000: //NOT USED. ALU has no assigned value for this
                throw new IllegalStateException("There is no 000 ALU function. Did you forget to convert from Harry's instructions?");

            /*
             * Addition of B and C into either A or D.
             */
            case (byte) 0b10000001: // A = B + C
                aRegister = (byte) (bRegister + cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (aRegister == 0);
                sign = (aRegister < 0);
                break;
            case (byte) 0b10001001: // D = B + C
                dRegister = (byte) (bRegister + cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (dRegister == 0);
                sign = (dRegister < 0);
                break;

            /*
             * Increment B into A or B.
             */
            case (byte) 0b10000010: // A = INC B
                aRegister = (byte) (bRegister + 1);
                carry = (bRegister + 1) > Byte.MAX_VALUE || (bRegister + 1) < Byte.MIN_VALUE;
                zero = (aRegister == 0);
                sign = (aRegister < 0);
                break;
            case (byte) 0b10001010: // D = INC B
                dRegister = (byte) (bRegister + 1);
                carry = (bRegister + 1) > Byte.MAX_VALUE || (bRegister + 1) < Byte.MIN_VALUE;
                zero = (dRegister == 0);
                sign = (dRegister < 0);
                break;

            /*
             * Bitwise AND of B and C into A or D.
             */
            case (byte) 0b10000011: // A = B AND C
                aRegister = (byte) (bRegister & cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (aRegister == 0);
                sign = (aRegister < 0);
                break;
            case (byte) 0b10001011: // D = B AND C
                dRegister = (byte) (bRegister & cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (dRegister == 0);
                sign = (dRegister < 0);
                break;

            /*
             * Bitwise OR of B and C into A or D
             */
            case (byte) 0b10000100: // A = B OR C
                aRegister = (byte) (bRegister | cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (aRegister == 0);
                sign = (aRegister < 0);
                break;
            case (byte) 0b10001100: // D = B OR C
                dRegister = (byte) (bRegister | cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (dRegister == 0);
                sign = (dRegister < 0);
                break;

            /*
             * XOR of B and C into A or D
             */
            case (byte) 0b10000101: // A = B XOR C
                aRegister = (byte) (bRegister ^ cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (aRegister == 0);
                sign = (aRegister < 0);
                break;
            case (byte) 0b10001101: // D = B XOR C
                dRegister = (byte) (bRegister ^ cRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (dRegister == 0);
                sign = (dRegister < 0);
                break;

            /*
             * Negation of B into A or D
             */
            case (byte) 0b10000110: // A = NOT B
                aRegister = (byte) (~bRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (aRegister == 0);
                sign = (aRegister < 0);
                break;
            case (byte) 0b10001110: // D = NOT B
                dRegister = (byte) (~bRegister);
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (dRegister == 0);
                sign = (dRegister < 0);
                break;

            /*
             * Left Rotation of B by one bit into A or D
             */
            case (byte) 0b10000111: // A = ROL B
                aRegister = (byte) (((bRegister & 0xff) << 1) | ((bRegister & 0xff) >>> (8 - 1)));
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (aRegister == 0);
                sign = (aRegister < 0);
                break;
            case (byte) 0b10001111: // D = ROL B
                dRegister = (byte) (((bRegister & 0xff) << 1) | ((bRegister & 0xff) >>> (8 - 1)));
                carry = (bRegister + cRegister) > Byte.MAX_VALUE || (bRegister + cRegister) < Byte.MIN_VALUE;
                zero = (dRegister == 0);
                sign = (dRegister < 0);
                break;

            /*
             * SETAB. Load a 5 bit, sign extended value into A or B directly.
             */
            case 0b01000000: // A = 0
                aRegister = 0;
                break;
            case 0b01000001: // A = 1
                aRegister = 1;
                break;
            case 0b01000010: // A = 2
                aRegister = 2;
                break;
            case 0b01000011: // A = 3
                aRegister = 3;
                break;
            case 0b01000100: // A = 4
                aRegister = 4;
                break;
            case 0b01000101: // A = 5
                aRegister = 5;
                break;
            case 0b01000110: // A = 6
                aRegister = 6;
                break;
            case 0b01000111: // A = 7
                aRegister = 7;
                break;
            case 0b01001000: // A = 8
                aRegister = 8;
                break;
            case 0b01001001: // A = 9
                aRegister = 9;
                break;
            case 0b01001010: // A = 10
                aRegister = 10;
                break;
            case 0b01001011: // A = 11
                aRegister = 11;
                break;
            case 0b01001100: // A = 12
                aRegister = 12;
                break;
            case 0b01001101: // A = 13
                aRegister = 13;
                break;
            case 0b01001110: // A = 14
                aRegister = 14;
                break;
            case 0b01001111: // A = 15
                aRegister = 15;
                break;
            case 0b01011111: // A = -1
                aRegister = -1;
                break;
            case 0b01011110: // A = -2
                aRegister = -2;
                break;
            case 0b01011101: // A = -3
                aRegister = -3;
                break;
            case 0b01011100: // A = -4
                aRegister = -4;
                break;
            case 0b01011011: // A = -5
                aRegister = -5;
                break;
            case 0b01011010: // A = -6
                aRegister = -6;
                break;
            case 0b01011001: // A = -7
                aRegister = -7;
                break;
            case 0b01011000: // A = -8
                aRegister = -8;
                break;
            case 0b01010111: // A = -9
                aRegister = -9;
                break;
            case 0b01010110: // A = -10
                aRegister = -10;
                break;
            case 0b01010101: // A = -11
                aRegister = -11;
                break;
            case 0b01010100: // A = -12
                aRegister = -12;
                break;
            case 0b01010011: // A = -13
                aRegister = -13;
                break;
            case 0b01010010: // A = -14
                aRegister = -14;
                break;
            case 0b01010001: // A = -15
                aRegister = -15;
                break;
            case 0b01010000: // A = -16
                aRegister = -16;
                break;
            case 0b01100000: // B = 0
                bRegister = 0;
                break;
            case 0b01100001: // B = 1
                bRegister = 1;
                break;
            case 0b01100010: // B = 2
                bRegister = 2;
                break;
            case 0b01100011: // B = 3
                bRegister = 3;
                break;
            case 0b01100100: // B = 4
                bRegister = 4;
                break;
            case 0b01100101: // B = 5
                bRegister = 5;
                break;
            case 0b01100110: // B = 6
                bRegister = 6;
                break;
            case 0b01100111: // B = 7
                bRegister = 7;
                break;
            case 0b01101000: // B = 8
                bRegister = 8;
                break;
            case 0b01101001: // B = 9
                bRegister = 9;
                break;
            case 0b01101010: // B = 10
                bRegister = 10;
                break;
            case 0b01101011: // B = 11
                bRegister = 11;
                break;
            case 0b01101100: // B = 12
                bRegister = 12;
                break;
            case 0b01101101: // B = 13
                bRegister = 13;
                break;
            case 0b01101110: // B = 14
                bRegister = 14;
                break;
            case 0b01101111: // B = 15
                bRegister = 15;
                break;
            case 0b01111111: // B = -1
                bRegister = -1;
                break;
            case 0b01111110: // B = -2
                bRegister = -2;
                break;
            case 0b01111101: // B = -3
                bRegister = -3;
                break;
            case 0b01111100: // B = -4
                bRegister = -4;
                break;
            case 0b01111011: // B = -5
                bRegister = -5;
                break;
            case 0b01111010: // B = -6
                bRegister = -6;
                break;
            case 0b01111001: // B = -7
                bRegister = -7;
                break;
            case 0b01111000: // B = -8
                bRegister = -8;
                break;
            case 0b01110111: // B = -9
                bRegister = -9;
                break;
            case 0b01110110: // B = -10
                bRegister = -10;
                break;
            case 0b01110101: // B = -11
                bRegister = -11;
                break;
            case 0b01110100: // B = -12
                bRegister = -12;
                break;
            case 0b01110011: // B = -13
                bRegister = -13;
                break;
            case 0b01110010: // B = -14
                bRegister = -14;
                break;
            case 0b01110001: // B = -15
                bRegister = -15;
                break;
            case 0b01110000: // B = -16
                bRegister = -16;
                break;

            /*
             * Increment XY as a 16 bit value. Overflow ignored.
             */
            case (byte) 0b10110000: // INC XY
                char xy = twoBytesToChar(xRegister, yRegister); //Combine X and Y into a single value
                xy += 1; // increment
                xRegister = (byte) ((xy >> 8) & 0xFF); //Get back out the new X byte
                yRegister = (byte) (xy & 0xFF); //Get back out the new Y byte
                break;

            /*
             * Load byte into A, B, C, or D from the memory address pointed to by M
             */
            case (byte) 0b10010000: // LOAD A
                aRegister = mainMemory[twoBytesToChar(m1Register, m2Register)];
                break;
            case (byte) 0b10010001: // LOAD B
                bRegister = mainMemory[twoBytesToChar(m1Register, m2Register)];
                break;
            case (byte) 0b10010010: // LOAD C
                cRegister = mainMemory[twoBytesToChar(m1Register, m2Register)];
                break;
            case (byte) 0b10010011: // LOAD D
                dRegister = mainMemory[twoBytesToChar(m1Register, m2Register)];
                break;

            /*
             * Store byte from A, B, C, or D into the memory address pointed to by M
             */
            case (byte) 0b10011000: // STORE A
                mainMemory[twoBytesToChar(m1Register, m2Register)] = aRegister;
                break;
            case (byte) 0b10011001: // STORE B
                mainMemory[twoBytesToChar(m1Register, m2Register)] = aRegister;
                break;
            case (byte) 0b10011010: // STORE C
                mainMemory[twoBytesToChar(m1Register, m2Register)] = aRegister;
                break;
            case (byte) 0b10011011: // STORE D
                mainMemory[twoBytesToChar(m1Register, m2Register)] = aRegister;
                break;

            /*
             * MOV16 bit value
             */
            case (byte) 0b10100000: // XY = M
                xRegister = m1Register;
                yRegister = m2Register;
                break;
            case (byte) 0b10100010: // XY = XY | NOP? (not specified)
                xRegister = xRegister;
                yRegister = yRegister;
                break;
            case (byte) 0b10100100: // XY = J
                xRegister = j1Register;
                yRegister = j2Register;
                break;
            case (byte) 0b10100110: // XY = 0 | HLT
                xRegister = 0;
                yRegister = 0;
                halted = true;
                break;
            case (byte) 0b10101000: // PC = M | JMP M
                pcRegister = twoBytesToChar(m1Register, m2Register);
                break;
            case (byte) 0b10101010: // PC = XY | JMP XY |Simple RETURN when combined with CALL?
                pcRegister = twoBytesToChar(xRegister, yRegister);
                break;
            case (byte) 0b10101100: // PC = J | JMP J
                pcRegister = twoBytesToChar(j1Register, j2Register);
                break;
            case (byte) 0b10101110: // PC = 0 | HLT
                pcRegister = 0;
                halted = true;
                break;

            /*
             * SETM. Sets the following two bytes into the high and low M registers.
             * Most of the SETM instructions weren't specified.
             *
             * These are all the variant's of Harry's GOTO function
             */
            case (byte) 0b11000000: //SETM <byte> <byte>
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                break;
            case (byte) 0b11000001: //SETM <byte> <byte> and XY = PC (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                break;
            case (byte) 0b11000010: //SETM <byte> <byte> and JMP J if not zero (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if zero flag is set, if not, JUMP!
                if (!zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11000011: //SETM <byte> <byte> and XY = PC and JMP J if not zero (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                //Check if zero flag is set, if not, JUMP!
                if (!zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11000100: //SETM <byte> <byte> and JMP J if zero (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if zero flag is set, if so, JUMP!
                if (zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11000101: //SETM <byte> <byte> and XY = PC and JMP J if zero (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if zero flag is set, if so, JUMP!
                if (zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11000111: //SETM <byte> <byte> and XY = PC and JMP J unconditional (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if zero flag is set or not set, JUMP! (Clearly not needed but following hardware logic)
                if (zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001000: //SETM <byte> <byte> and JMP J if not carry (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if carry flag is set, if not, JUMP!
                if (!carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001001: //SETM <byte> <byte> and XY = PC and JMP J if not carry (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if carry flag is set, if not, JUMP!
                if (!carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001010: //SETM <byte> <byte> and JMP J if (not carry or not zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if carry flag is not set or zero flag is not set, JUMP!
                if (!carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001011: //SETM <byte> <byte> and XY = PC and JMP J if (not carry or not zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if carry flag is not set or zero flag is not set, JUMP!
                if (!carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001100: //SETM <byte> <byte> and JMP J if (not carry or zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if carry flag is not set or zero flag is set, JUMP!
                if (!carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001101: //SETM <byte> <byte> and XY = PC JMP J if (not carry or zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if carry flag is not set or zero flag is set, JUMP!
                if (!carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001110: //SETM <byte> <byte> JMP J unconditional (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // (Following hardware logic)
                if (!carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11001111: //SETM <byte> <byte> and XY = PC JMP J unconditional (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // (Following hardware logic)
                if (!carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010000: //SETM <byte> <byte> and JMP J if sign (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if sign flag is set, if not, JUMP!
                if (sign) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010001: //SETM <byte> <byte> and XY = PC and JMP J if sign (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set, if not, JUMP!
                if (sign) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010010: //SETM <byte> <byte> and JMP J if (sign or not zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or zero flag is not set, JUMP!
                if (sign || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010011: //SETM <byte> <byte> and XY = PC and JMP J if (sign or not zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or zero flag is not set, JUMP!
                if (sign || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010100: //SETM <byte> <byte> and JMP J if (sign or zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or zero flag is set, JUMP!
                if (sign || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010101: //SETM <byte> <byte> and XY = PC JMP J if (sign or zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or zero flag is set, JUMP!
                if (sign || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010110: //SETM <byte> <byte> JMP J unconditional (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // (Following hardware logic)
                if (sign || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11010111: //SETM <byte> <byte> and XY = PC JMP J unconditional (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // (Following hardware logic)
                if (sign || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011000: //SETM <byte> <byte> and JMP J if sign or not carry (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if sign flag is set or carry is not set, JUMP!
                if (sign || !carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011001: //SETM <byte> <byte> and XY = PC and JMP J if sign or not carry (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or carry is not set JUMP!
                if (sign || !carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011010: //SETM <byte> <byte> and JMP J if (sign or or not carry not zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or carry is not set or zero flag is not set, JUMP!
                if (sign || !carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011011: //SETM <byte> <byte> and XY = PC and JMP J if (sign or or not carry not zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or carry is not set or zero flag is not set, JUMP!
                if (sign || !carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011100: //SETM <byte> <byte> and JMP J if (sign or not carry or zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or carry is not set or zero flag is set, JUMP!
                if (sign || !carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011101: //SETM <byte> <byte> and XY = PC JMP J if (sign or not carry or zero) (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or carry is not set or zero flag is set, JUMP!
                if (sign || !carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011110: //SETM <byte> <byte> JMP J unconditional (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // (Following hardware logic)
                if (sign || !carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11011111: //SETM <byte> <byte> and XY = PC JMP J unconditional (not specified)
                m1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                m2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // (Following hardware logic)
                if (sign || !carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11100000: //SETJ <byte> <byte>
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                break;
            case (byte) 0b11100001: //SETJ <byte> <byte> and XY = PC
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                break;
            case (byte) 0b11100010: //SETJ <byte> <byte> and JMP J if not zero | BNZ "Branch Not Zero"
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if zero flag is set, if not, JUMP!
                if (!zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11100011: //SETJ <byte> <byte> and XY = PC and JMP J if not zero
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                //Check if zero flag is set, if not, JUMP!
                if (!zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11100100: //SETJ <byte> <byte> and JMP J if zero | BZ "Branch Zero"
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if zero flag is set, if so, JUMP!
                if (zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11100101: //SETJ <byte> <byte> and XY = PC and JMP J if zero
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if zero flag is set, if so, JUMP!
                if (zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11100111: //SETJ <byte> <byte> and XY = PC and JMP J unconditional
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if zero flag is set or not set, JUMP! (Clearly not needed but following hardware logic)
                if (zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101000: //SETJ <byte> <byte> and JMP J if not carry | BNC "Branch Not Carry"
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if carry flag is set, if not, JUMP!
                if (!carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101001: //SETJ <byte> <byte> and XY = PC and JMP J if not carry
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if carry flag is set, if not, JUMP!
                if (!carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101010: //SETJ <byte> <byte> and JMP J if (not carry or not zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if carry flag is not set or zero flag is not set, JUMP!
                if (!carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101011: //SETJ <byte> <byte> and XY = PC and JMP J if (not carry or not zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if carry flag is not set or zero flag is not set, JUMP!
                if (!carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101100: //SETJ <byte> <byte> and JMP J if (not carry or zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if carry flag is not set or zero flag is set, JUMP!
                if (!carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101101: //SETJ <byte> <byte> and XY = PC JMP J if (not carry or zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if carry flag is not set or zero flag is set, JUMP!
                if (!carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101110: //SETJ <byte> <byte> JMP J unconditional
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // (Following hardware logic)
                if (!carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11101111: //SETJ <byte> <byte> and XY = PC JMP J unconditional
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // (Following hardware logic)
                if (!carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110000: //SETJ <byte> <byte> and JMP J if sign
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if sign flag is set, if not, JUMP!
                if (sign) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110001: //SETJ <byte> <byte> and XY = PC and JMP J if sign
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set, if not, JUMP!
                if (sign) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110010: //SETJ <byte> <byte> and JMP J if (sign or not zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or zero flag is not set, JUMP!
                if (sign || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110011: //SETJ <byte> <byte> and XY = PC and JMP J if (sign or not zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or zero flag is not set, JUMP!
                if (sign || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110100: //SETJ <byte> <byte> and JMP J if (sign or zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or zero flag is set, JUMP!
                if (sign || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110101: //SETJ <byte> <byte> and XY = PC JMP J if (sign or zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or zero flag is set, JUMP!
                if (sign || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110110: //SETJ <byte> <byte> JMP J unconditional
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // (Following hardware logic)
                if (sign || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11110111: //SETJ <byte> <byte> and XY = PC JMP J unconditional
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // (Following hardware logic)
                if (sign || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111000: //SETJ <byte> <byte> and JMP J if sign or not carry
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                //Check if sign flag is set or carry is not set, JUMP!
                if (sign || !carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111001: //SETJ <byte> <byte> and XY = PC and JMP J if sign or not carry
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or carry is not set JUMP!
                if (sign || !carry) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111010: //SETJ <byte> <byte> and JMP J if (sign or or not carry not zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or carry is not set or zero flag is not set, JUMP!
                if (sign || !carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111011: //SETJ <byte> <byte> and XY = PC and JMP J if (sign or or not carry not zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or carry is not set or zero flag is not set, JUMP!
                if (sign || !carry || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111100: //SETJ <byte> <byte> and JMP J if (sign or not carry or zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // Check if sign flag is set or carry is not set or zero flag is set, JUMP!
                if (sign || !carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111101: //SETJ <byte> <byte> and XY = PC JMP J if (sign or not carry or zero)
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // Check if sign flag is set or carry is not set or zero flag is set, JUMP!
                if (sign || !carry || zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111110: //SETJ <byte> <byte> JMP J unconditional
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                // (Following hardware logic)
                if (sign || !carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;
            case (byte) 0b11111111: //SETJ <byte> <byte> and XY = PC JMP J unconditional
                j1Register = mainMemory[pcRegister]; //The PC is already pointing to our first byte to load.
                pcRegister += 1; //Increment PC
                j2Register = mainMemory[pcRegister]; //Get the second byte
                pcRegister += 1; //Increment PC again, it is now pointing to the next instruction
                xRegister = (byte) ((pcRegister >> 8) & 0xFF); //Get X byte
                yRegister = (byte) (pcRegister & 0xFF); //Get Y byte
                // (Following hardware logic)
                if (sign || !carry || zero || !zero) {
                    pcRegister = twoBytesToChar(j1Register, j2Register);
                }
                break;

            default:
                throw new IllegalStateException("Unable to decode instruction: " + instRegister);
        }

    }

    public static char twoBytesToChar(byte a, byte b) {
        return (char) ((a << 8) | (b & 0xff));
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

    public void setARegister(byte aRegister) {
        this.aRegister = aRegister;
    }

    public void setBRegister(byte bRegister) {
        this.bRegister = bRegister;
    }

    public void setCRegister(byte cRegister) {
        this.cRegister = cRegister;
    }

    public void setDRegister(byte dRegister) {
        this.dRegister = dRegister;
    }

    public void setM1Register(byte m1Register) {
        this.m1Register = m1Register;
    }

    public void setM2Register(byte m2Register) {
        this.m2Register = m2Register;
    }

    public void setXRegister(byte xRegister) {
        this.xRegister = xRegister;
    }

    public void setYRegister(byte yRegister) {
        this.yRegister = yRegister;
    }

    public byte getARegister() {
        return aRegister;
    }

    public byte getBRegister() {
        return bRegister;
    }

    public byte getCRegister() {
        return cRegister;
    }

    public byte getDRegister() {
        return dRegister;
    }

    public byte getM1Register() {
        return m1Register;
    }

    public byte getM2Register() {
        return m2Register;
    }

    public byte getXRegister() {
        return xRegister;
    }

    public byte getYRegister() {
        return yRegister;
    }

    public byte getJ1Register() {
        return j1Register;
    }

    public byte getJ2Register() {
        return j2Register;
    }

    public byte getInstRegister() {
        return instRegister;
    }

    public char getPcRegister() {
        return pcRegister;
    }

    public char getIncRegister() {
        return incRegister;
    }

    public byte[] getMainMemory() {
        return mainMemory;
    }

    public void setMainMemory(byte[] mainMemory) {
        this.mainMemory = mainMemory;
    }

    public boolean isHalted() {
        return halted;
    }
}
