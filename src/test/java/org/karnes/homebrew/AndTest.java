package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;

public class AndTest {

    @Test
    @DisplayName("Simple AND")
    public void testSimpleAnd() {
        short value1 = 1000;
        short value2 = 2000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     AND CX, BX, AX          ; CX = AX & BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 & value2, computer.getCX(), "CX should have AX & BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Anding register to itself, and storing in a different register")
    public void testSelfAndDifferentReg() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     AND CX, AX, AX          ; CX = AX + AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value1 & value1, computer.getCX(), "CX should have AX & AX");
        assertEquals(computer.getAX(), computer.getCX(), "CX should have the same value as AX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by anding register to itself, and store into self")
    public void testSelfAndSameReg() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     AND AX, AX, AX          ; AX = AX & AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Anding offset alternating bit patterns")
    public void testAlternatingAnd1() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = (short) ((char) 0b1010_1010_1010_1010);
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     AND CX, BX, AX          ; CX = AX & BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(0, computer.getCX(), "CX should have AX & BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Anding offset alternating bit patterns 2")
    public void testAlternatingAnd2() {
        short value1 = (short) ((char) 0b1010_1010_1010_1010);
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     AND CX, BX, AX          ; CX = AX & BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(0, computer.getCX(), "CX should have AX & BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Anding zero with a value")
    public void testZeroAnd() {
        short value1 = 0;
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     AND CX, BX, AX          ; CX = AX & BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(0, computer.getCX(), "CX should have AX & BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Anding a value with zero")
    public void testZeroAnd2() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = 0;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     AND CX, BX, AX          ; CX = AX & BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(0, computer.getCX(), "CX should have AX & BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Anding all 1's with a value")
    public void testOnesAnd() {
        short value1 = (short) ((char) 0b1111_1111_1111_1111);
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     AND CX, BX, AX          ; CX = AX & BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value2, computer.getCX(), "CX should have BX's value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Anding a value with all ones")
    public void testOnesAnd2() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = (short) ((char) 0b1111_1111_1111_1111);
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     AND CX, BX, AX          ; CX = AX & BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(0b0101_0101_0101_0101, computer.getCX(), "CX should have AX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }
}
