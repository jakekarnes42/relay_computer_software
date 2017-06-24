package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.ForthRelayComputer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jake on 4/25/17.
 */
public class SubtractionTest {

    @Test
    @DisplayName("Test a single subtraction case")
    public void testSimpleSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, 0xFF; Get the stack pointer far away\r\n"
                + "         LOAD RP, 0xFFF; Get the return pointer far away\r\n"
                + "         LOAD BX, 100; B = 100\r\n"
                + "         LOAD CX, 777; C = 777\r\n"
                + "         CALL RP, SUB; A = CX - BX\r\n"
                + "         HALT        ; DONE\r\n"
                + "\r\n"
                + ";SUB C - B -> A                      \r\n"
                + "SUB:     PUSH SP, BX ; Save B        \r\n"
                + "         PUSH SP, CX ; Save C        \r\n"
                + "         NOT AX      ; A = NOT B     \r\n"
                + "         MOV BX, AX  ; B = A         \r\n"
                + "         INC AX      ; A = B + 1     \r\n"
                + "         MOV BX, AX  ; B = A         \r\n"
                + "         ADD AX      ; A = B + C     \r\n"
                + "         POP CX, SP  ; restore C     \r\n"
                + "         POP BX, SP  ; restore B     \r\n"
                + "         POP PC, RP  ; return        \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        byte[] RAM = assembler.assemble(code);

        ForthRelayComputer computer = new ForthRelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((char) 677, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((char) 100, computer.getBX(), "BX should be restored to original value");
        assertEquals((char) 777, computer.getCX(), "CX should be restored to original value");
    }


    @Test
    @DisplayName("Test a single subtraction case with Javascript directive")
    public void testJSSimpleSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, {1000}; Get the stack pointer far away\r\n"
                + "         LOAD RP, {9000}; Get the return pointer far away\r\n"
                + "         LOAD BX, {987}; B = 987\r\n"
                + "         LOAD CX, {1578}; C = 1578\r\n"
                + "         CALL RP, SUB; A = CX - BX\r\n"
                + "         HALT        ; DONE\r\n"
                + "\r\n"
                + ";SUB C - B -> A                      \r\n"
                + "SUB:     PUSH SP, BX ; Save B        \r\n"
                + "         PUSH SP, CX ; Save C        \r\n"
                + "         NOT AX      ; A = NOT B     \r\n"
                + "         MOV BX, AX  ; B = A         \r\n"
                + "         INC AX      ; A = B + 1     \r\n"
                + "         MOV BX, AX  ; B = A         \r\n"
                + "         ADD AX      ; A = B + C     \r\n"
                + "         POP CX, SP  ; restore C     \r\n"
                + "         POP BX, SP  ; restore B     \r\n"
                + "         POP PC, RP  ; return        \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        byte[] RAM = assembler.assemble(code);

        ForthRelayComputer computer = new ForthRelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((char) 1578 - 987, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((char) 987, computer.getBX(), "BX should be restored to original value");
        assertEquals((char) 1578, computer.getCX(), "CX should be restored to original value");
    }


    @Test
    @DisplayName("Subtraction of negative numbers")
    public void testNegativeSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, 0xFF; Get the stack pointer far away\r\n"
                + "         LOAD RP, 0xFFF; Get the return pointer far away\r\n"
                + "         LOAD BX, -22; B = -22\r\n"
                + "         LOAD CX, -53; C = -53\r\n"
                + "         CALL RP, SUB; A = CX - BX\r\n"
                + "         HALT        ; DONE\r\n"
                + "\r\n"
                + ";SUB C - B -> A                      \r\n"
                + "SUB:     PUSH SP, BX ; Save B        \r\n"
                + "         PUSH SP, CX ; Save C        \r\n"
                + "         NOT AX      ; A = NOT B     \r\n"
                + "         MOV BX, AX  ; B = A         \r\n"
                + "         INC AX      ; A = B + 1     \r\n"
                + "         MOV BX, AX  ; B = A         \r\n"
                + "         ADD AX      ; A = B + C     \r\n"
                + "         POP CX, SP  ; restore C     \r\n"
                + "         POP BX, SP  ; restore B     \r\n"
                + "         POP PC, RP  ; return        \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        byte[] RAM = assembler.assemble(code);

        ForthRelayComputer computer = new ForthRelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((char) (-53 - -22), computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((char) -22, computer.getBX(), "BX should be restored to original value");
        assertEquals((char) -53, computer.getCX(), "CX should be restored to original value");
    }
}
