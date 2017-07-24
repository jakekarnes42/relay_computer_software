package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CmpTest {

    @Test
    @DisplayName("Test a comparison")
    public void testComparison() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, 150    ; B = 150\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 100, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 150, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set since CX is greater than BX");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Test a reverse comparison")
    public void testReverseComparison() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, 250    ; B = 250\r\n"
                + "         LOAD CX, 150    ; C = 150\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) -100, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 250, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 150, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set since CX is less than BX");
        assertTrue(computer.getOverflowFlag(), "The overflow flag should be set, since the output's sign doesn't match the inputs' signs");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set, since the result is negative");
    }

    @Test
    @DisplayName("Comparison of equal values")
    public void testEqualsComparison() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, 250    ; B = 250\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 0, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 250, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set since CX is equal to BX");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set, since the output's sign matches the inputs' signs");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

}
