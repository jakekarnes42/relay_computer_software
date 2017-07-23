package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongSubtractionTest {

    @Test
    @DisplayName("Test a long subtraction")
    public void testLongSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, 1000; Get the stack pointer far away\r\n"
                + "         LOAD RP, 2000; Get the return pointer far away\r\n"
                + "         LOAD BX, 100; B = 100\r\n"
                + "         LOAD CX, 777; C = 777\r\n"
                + "         CALL RP, LONGSUB; A = CX - BX\r\n"
                + "         HALT        ; DONE\r\n"
                + "\r\n"
                + ";LONGSUB C - B -> A                      \r\n"
                + "LONGSUB: PUSH SP, BX     ; Save B        \r\n"
                + "         PUSH SP, CX     ; Save C        \r\n"
                + "         NOT AX, BX      ; A = NOT B     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         INC AX, BX      ; A = B + 1     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         ADD AX, BX, CX  ; A = B + C     \r\n"
                + "         POP CX, SP      ; restore C     \r\n"
                + "         POP BX, SP      ; restore B     \r\n"
                + "         RET RP          ; return        \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 677, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 100, computer.getBX(), "BX should be restored to original value");
        assertEquals((short) 777, computer.getCX(), "CX should be restored to original value");
    }


    @Test
    @DisplayName("Test a long subtraction with JavaScript directive")
    public void testJSLongSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, {1000} ; Get the stack pointer far away\r\n"
                + "         LOAD RP, {9000} ; Get the return pointer far away\r\n"
                + "         LOAD BX, {987}  ; B = 987\r\n"
                + "         LOAD CX, {1578} ; C = 1578\r\n"
                + "         CALL RP, LONGSUB; A = CX - BX\r\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n"
                + ";LONGSUB C - B -> A                      \r\n"
                + "LONGSUB: PUSH SP, BX     ; Save B        \r\n"
                + "         PUSH SP, CX     ; Save C        \r\n"
                + "         NOT AX, BX      ; A = NOT B     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         INC AX, BX      ; A = B + 1     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         ADD AX, BX, CX  ; A = B + C     \r\n"
                + "         POP CX, SP      ; restore C     \r\n"
                + "         POP BX, SP      ; restore B     \r\n"
                + "         RET RP          ; return        \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 1578 - 987, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 987, computer.getBX(), "BX should be restored to original value");
        assertEquals((short) 1578, computer.getCX(), "CX should be restored to original value");
    }

    @Test
    @DisplayName("Long subtraction of negative numbers")
    public void testNegativeLongSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, 1000; Get the stack pointer far away\r\n"
                + "         LOAD RP, 2000; Get the return pointer far away\r\n"
                + "         LOAD BX, -22; B = -22\r\n"
                + "         LOAD CX, -53; C = -53\r\n"
                + "         CALL RP, LONGSUB; A = CX - BX\r\n"
                + "         HALT        ; DONE\r\n"
                + "\r\n"
                + ";LONGSUB C - B -> A                      \r\n"
                + "LONGSUB: PUSH SP, BX     ; Save B        \r\n"
                + "         PUSH SP, CX     ; Save C        \r\n"
                + "         NOT AX, BX      ; A = NOT B     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         INC AX, BX      ; A = B + 1     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         ADD AX, BX, CX  ; A = B + C     \r\n"
                + "         POP CX, SP      ; restore C     \r\n"
                + "         POP BX, SP      ; restore B     \r\n"
                + "         RET RP          ; return        \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) (-53 - -22), computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -22, computer.getBX(), "BX should be restored to original value");
        assertEquals((short) -53, computer.getCX(), "CX should be restored to original value");
    }
}
