package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;

public class DecTest {

    @Test
    @DisplayName("Simple DEC")
    public void testSimpleDec() {
        short value = 1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         DEC BX, AX              ; BX = AX - 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(value - 1, computer.getBX(), "BX should have AX - 1");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Self DEC")
    public void testSelfDec() {
        short value = 1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         DEC AX, AX              ; AX-- \r\n"
                + "         HALT                    ; DONE \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(value - 1, computer.getAX(), "AX should have its original value, minus 1");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Negative DEC")
    public void testNegativeDec() {
        short value = -1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         DEC BX, AX              ; BX = AX - 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(value - 1, computer.getBX(), "BX should have AX - 1");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Underflow DeC")
    public void testUnderflowDec() {
        short value = Short.MIN_VALUE;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         DEC BX, AX              ; BX = AX - 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals((char) value - 1, (char) computer.getBX(), "BX should have AX - 1, when viewed as an unsigned value");
        assertEquals(Short.MAX_VALUE, computer.getBX(), "BX should have largest short value, when viewed as an signed value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertTrue(computer.getOverflowFlag(), "The overflow flag should be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Zero DEC")
    public void testCarryInc() {
        int value = 0;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         DEC BX, AX              ; BX = AX - 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, (char) computer.getAX(), "AX should have its original value");
        assertEquals(Character.MAX_VALUE, (char) computer.getBX(), "BX should roll to the largest unsigned value");
        assertEquals(-1, computer.getBX(), "BX should be smallest negative value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }


}
