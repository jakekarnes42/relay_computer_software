package org.karnes.homebrew.relay.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.computer.assemblr.Assembler;
import org.karnes.homebrew.relay.computer.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;

public class NotTest {

    @Test
    @DisplayName("Simple NOT")
    public void testSimpleNot() {
        short value = 1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         NOT BX, AX              ; BX = ~AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(~value, computer.getBX(), "BX should have AX, with all the bits flipped");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set, since the original value is positive");
    }

    @Test
    @DisplayName("Self NOT")
    public void testSelfNot() {
        short value = 1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         NOT AX, AX              ; AX = ~AX \r\n"
                + "         HALT                    ; DONE \r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(~value, computer.getAX(), "AX should have its bits flipped");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set, since the original value was positive");
    }

    @Test
    @DisplayName("Negative NOT")
    public void testNegativeNot() {
        short value = -1000;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         NOT BX, AX              ; BX = ~AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(~value, computer.getBX(), "BX should have AX with its bits flipped");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set, since the original value was negative");
    }

    @Test
    @DisplayName("Zero NOT")
    public void testZeroNot() {
        short value = 0;
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         NOT BX, AX              ; BX = ~AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals((char) 0xFFFF, (char) computer.getBX(), "BX should have all one bits, when viewed as an unsigned value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set, since the highest bit is one");
    }

    @Test
    @DisplayName("All 1's NOT")
    public void testAllOnesNot() {
        short value = (short) ((char) 0xFFFF);
        String code = "     LOAD AX, " + value + "  ; AX = value\r\n"
                + "         NOT BX, AX              ; BX = ~AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals((char) 0, (char) computer.getBX(), "BX should have all zero bits, when viewed as an unsigned value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set, since the input is all ones, and we're flipping the bits.");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set, since the highest bit is zero");
    }

}
