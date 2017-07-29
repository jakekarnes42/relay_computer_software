package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IncTest {

    @Test
    @DisplayName("Simple INC")
    public void testSimpleInc() {
        short value = 1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         INC BX, AX              ; BX = AX + 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(value + 1, computer.getBX(), "BX should have AX + 1");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Self INC")
    public void testSelfInc() {
        short value = 1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         INC AX, AX              ; AX++ \r\n"
                + "         HALT                    ; DONE \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(value + 1, computer.getAX(), "AX should have its original value, plus 1");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Negative INC")
    public void testNegativeInc() {
        short value = -1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         INC BX, AX              ; BX = AX + 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(value + 1, computer.getBX(), "BX should have AX + 1");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Overflow INC")
    public void testOverflowInc() {
        short value = Short.MAX_VALUE;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         INC BX, AX              ; BX = AX + 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals((char) value + 1, (char) computer.getBX(), "BX should have AX + 1, when viewed as an unsigned value");
        assertEquals(Short.MIN_VALUE, ((short) computer.getBX()), "BX should have smallest short value, when viewed as an signed value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertTrue(computer.getOverflowFlag(), "The overflow flag should be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Carry INC")
    public void testCarryInc() {
        int value = Character.MAX_VALUE;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         INC BX, AX              ; BX = AX + 1\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, (char) computer.getAX(), "AX should have its original value");
        assertEquals(0, computer.getBX(), "BX should be 0");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }


}
