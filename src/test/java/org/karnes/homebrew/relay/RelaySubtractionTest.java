package org.karnes.homebrew.relay;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RelaySubtractionTest {

    @Test
    @DisplayName("Test a single subtraction case")
    public void testSimpleSubtraction() {
        byte a = 11;
        byte b = 6;
        RelayComputer compy = getSubtractionProgram(a, b);
        compy.start();
        assertEquals(((byte) (a - b)), compy.getARegister(), "The subtraction should be mathematically correct for a=" + a + " b=" + b);
    }

    @Test
    @DisplayName("Test all 5 bit subtractions")
    public void testAll5bitSubtraction() {

        for (byte a = -16; a <= 15; a++) {
            for (byte b = -16; b <= 15; b++) {
                RelayComputer compy = getSubtractionProgram(a, b);

                compy.start();
                assertEquals(((byte) (a - b)), compy.getARegister(), "The subtraction should be mathematically correct for a=" + a + " b=" + b);

            }
        }

    }


    /*
     * Preloads A and B registers, then provides RelayComputer which performs (A - B) -> A, modifying the C register
     */
    private static RelayComputer getSubtractionProgram(byte a, byte b) {
        RelayComputer compy = new RelayComputer();

        byte[] program = new byte[(1 << 16)];
        program[0] = (byte) 0b00010000; // MOV C A
        program[1] = (byte) 0b10000110; // A = NOT B
        program[2] = (byte) 0b00001000; // MOV B A
        program[3] = (byte) 0b10000010; // A = B + 1
        program[4] = (byte) 0b00001000; // MOV B A
        program[5] = (byte) 0b10000001; // A = B + C
        program[6] = (byte) 0b10101110; // PC = 0 | HLT
        compy.setMainMemory(program);

        compy.setARegister(a);
        compy.setBRegister(b);

        return compy;
    }

}
