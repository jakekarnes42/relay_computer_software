package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;

public class RolTest {

    @Test
    @DisplayName("Simple ROL")
    public void testSimpleRol() {
        short value = 0b0000_0000_0000_0001;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         ROL BX, AX              ; BX = ROL AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals((char) 0b0000_0000_0000_0010, computer.getBX(), "BX should have AX, with bits rolled left");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Alternating ROL")
    public void testAltRol() {
        short value = 0b0101_0101_0101_0101;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         ROL BX, AX              ; BX = ROL AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals((char) 0b1010_1010_1010_1010, (char) computer.getBX(), "BX should have AX, with bits rolled left");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Alternating ROL 2")
    public void testAltRol2() {
        short value = (short) ((char) 0b1010_1010_1010_1010);
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         ROL BX, AX              ; BX = ROL AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(0b0101_0101_0101_0101, computer.getBX(), "BX should have AX, with bits rolled left");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }


    @Test
    @DisplayName("Self ROL")
    public void testSelfRol() {
        short value = 0b0000_0000_0000_0001;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         ROL AX, AX              ; AX = ROL AX \r\n"
                + "         HALT                    ; DONE \r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(0b0000_0000_0000_0010, computer.getAX(), "AX should have its bits rolled");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Highest ROL")
    public void testHighestRol() {
        short value = (short) ((char) 0b1000_0000_0000_0000);
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         ROL BX, AX              ; BX = ROL AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(0b0000_0000_0000_0001, computer.getBX(), "BX should have AX with its bits rolled left");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Zero ROL")
    public void testZeroRol() {
        short value = 0;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         ROL BX, AX              ; BX = ROL AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(0, computer.getBX(), "BX should also be zero");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should be set, since the highest bit is one");
    }

    @Test
    @DisplayName("Ones ROL")
    public void testOnesRol() {
        short value = (short) 0xFFFF;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         ROL BX, AX              ; BX = ROL AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals((short) ((char) 0xFFFF), computer.getBX(), "BX should also be 1's");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set, since the highest bit is one");
    }
}
