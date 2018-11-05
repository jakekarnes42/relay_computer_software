package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortSubtractionTest {


    @Test
    @DisplayName("Test a subtraction")
    public void testSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, 1000   ; Get the stack pointer far away\r\n"
                + "         LOAD RP, 2000   ; Get the return pointer far away\r\n"
                + "         LOAD BX, 100    ; B = 100\r\n"
                + "         LOAD CX, 777    ; C = 777\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 677, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 100, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 777, computer.getCX(), "CX should still have its original value");
    }


    @Test
    @DisplayName("Test a subtraction with JavaScript directive")
    public void testJSSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, {1000} ; Get the stack pointer far away\r\n"
                + "         LOAD RP, {2000} ; Get the return pointer far away\r\n"
                + "         LOAD BX, {987}  ; B = 987\r\n"
                + "         LOAD CX, {1578} ; C = 1578\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\r\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 1578 - 987, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 987, computer.getBX(), "BX should be restored to original value");
        assertEquals((short) 1578, computer.getCX(), "CX should be restored to original value");
    }

    @Test
    @DisplayName("Subtraction of negative numbers")
    public void testNegativeLongSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, 1000   ; Get the stack pointer far away\r\n"
                + "         LOAD RP, 2000   ; Get the return pointer far away\r\n"
                + "         LOAD BX, -22    ; B = -22\r\n"
                + "         LOAD CX, -53    ; C = -53\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) (-53 - -22), computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -22, computer.getBX(), "BX should be restored to original value");
        assertEquals((short) -53, computer.getCX(), "CX should be restored to original value");
    }
}
