package org.karnes.homebrew.relay.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.computer.assemblr.Assembler;
import org.karnes.homebrew.relay.computer.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;

public class AddTest {

    @Test
    @DisplayName("Simple ADD")
    public void testSimpleAdd() {
        short value1 = 1000;
        short value2 = 2000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     ADD CX, BX, AX          ; CX = AX + BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 + value2, computer.getCX(), "CX should have AX + BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by adding register to itself")
    public void testDoublingAdd() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     ADD CX, AX, AX          ; CX = AX + AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value1 + value1, computer.getCX(), "CX should have AX + AX");
        assertEquals(value1 * 2, computer.getCX(), "CX should have AX * 2");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by adding register to itself, and store into self")
    public void testSelfAdd() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     ADD AX, AX, AX          ; AX = AX + AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertNotEquals(value1, computer.getAX(), "AX should not have its original value");
        assertEquals(value1 + value1, computer.getAX(), "AX should have AX + AX");
        assertEquals(value1 * 2, computer.getAX(), "AX should have AX * 2");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("ADD with overflow")
    public void testAddOverflow() {
        short value1 = Short.MAX_VALUE;
        short value2 = 1;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     ADD CX, BX, AX          ; CX = AX + BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals((char) value1 + value2, (char) computer.getCX(), "CX should have AX + BX, when viewed as unsigned value");
        assertEquals(Short.MIN_VALUE, computer.getCX(), "CX should have roll over, when viewed as signed value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertTrue(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should not be set");
    }


    @Test
    @DisplayName("ADD zero and a number")
    public void testAddZeroNum() {
        short value1 = 0;
        short value2 = 1;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     ADD CX, BX, AX          ; CX = AX + BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 + value2, computer.getCX(), "CX should have AX + BX, when viewed as unsigned value");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("ADD zero and a number")
    public void testAddNumZero() {
        short value1 = 1;
        short value2 = 0;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     ADD CX, BX, AX          ; CX = AX + BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 + value2, computer.getCX(), "CX should have AX + BX, when viewed as unsigned value");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }
    

    @Test
    @DisplayName("ADD zero and zero")
    public void testAddZeroZero() {
        short value1 = 0;
        short value2 = 0;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     ADD CX, BX, AX          ; CX = AX + BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 + value2, computer.getCX(), "CX should have AX + BX, when viewed as unsigned value");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }
}
