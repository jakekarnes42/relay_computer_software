package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;

public class OrTest {

    @Test
    @DisplayName("Simple OR")
    public void testSimpleOr() {
        short value1 = 1000;
        short value2 = 2000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     OR CX, BX, AX           ; CX = AX | BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 | value2, computer.getCX(), "CX should have AX | BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Oring register to itself, and storing in a different register")
    public void testSelfOrDifferentReg() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     OR CX, AX, AX           ; CX = AX + AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value1 | value1, computer.getCX(), "CX should have AX | AX");
        assertEquals(computer.getAX(), computer.getCX(), "CX should have the same value as AX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by oring register to itself, and store into self")
    public void testSelfOrSameReg() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     OR AX, AX, AX           ; AX = AX | AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
    @DisplayName("Double register by oring register to itself, and store into self")
    public void testSelfOrSameRegZero() {
        short value1 = 0;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     OR AX, AX, AX           ; AX = AX | AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by oring register to itself, and store into self")
    public void testSelfOrSameRegNegative() {
        short value1 = -1;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     OR AX, AX, AX           ; AX = AX | AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Double register by oring register to itself, and store into self, and Jump")
    public void testSelfOrWithJumpPos() {
        short value1 = 100;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     OR AX, AX, AX           ; AX = AX | AX\r\n"
                + "     JS ZLESS1         ; Test if it's negative\r\n"
                + "     LOAD BX,0           ; It is not negative (i.e. AX is 0 or positive) Load 0 into BX\r\n"
                + "     JMP ZLESS2          ; JMP to pushing onto the data stack\r\n"
                + "     ZLESS1: LOAD BX, -1         ; It is negative, load -1 into BX\r\n"
                + "     ZLESS2:\tMOV CX, BX         ; Copy BX value (either -1 or 0) into CX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(0, computer.getBX(), "BX should have 0 since AX is non-negative");
        assertEquals(computer.getBX(), computer.getCX(), "CX should have BX's value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by oring register to itself, and store into self, and Jump")
    public void testSelfOrWithJumpZero() {
        short value1 = 0;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     OR AX, AX, AX           ; AX = AX | AX\r\n"
                + "     JS ZLESS1         ; Test if it's negative\r\n"
                + "     LOAD BX,0           ; It is not negative (i.e. AX is 0 or positive) Load 0 into BX\r\n"
                + "     JMP ZLESS2          ; JMP to pushing onto the data stack\r\n"
                + "     ZLESS1: LOAD BX, -1         ; It is negative, load -1 into BX\r\n"
                + "     ZLESS2:\tMOV CX, BX         ; Copy BX value (either -1 or 0) into CX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(0, computer.getBX(), "BX should have 0 since AX is non-negative");
        assertEquals(computer.getBX(), computer.getCX(), "CX should have BX's value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by oring register to itself, and store into self, and Jump")
    public void testSelfOrWithJumpNegative() {
        short value1 = -1;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     OR AX, AX, AX           ; AX = AX | AX\r\n"
                + "     JS ZLESS1         ; Test if it's negative\r\n"
                + "     LOAD BX,0           ; It is not negative (i.e. AX is 0 or positive) Load 0 into BX\r\n"
                + "     JMP ZLESS2          ; JMP to pushing onto the data stack\r\n"
                + "     ZLESS1: LOAD BX, -1         ; It is negative, load -1 into BX\r\n"
                + "     ZLESS2:\tMOV CX, BX         ; Copy BX value (either -1 or 0) into CX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(-1, computer.getBX(), "BX should have -1 since AX is negative");
        assertEquals(computer.getBX(), computer.getCX(), "CX should have BX's value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }


    @Test
    @DisplayName("Oring offset alternating bit patterns")
    public void testAlternatingOr1() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = (short) ((char) 0b1010_1010_1010_1010);
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     OR CX, BX, AX           ; CX = AX | BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 | value2, computer.getCX(), "CX should have AX | BX");
        assertEquals((short) ((char) 0xFFFF), computer.getCX(), "CX should have all 1's");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Oring offset alternating bit patterns 2")
    public void testAlternatingOr2() {
        short value1 = (short) ((char) 0b1010_1010_1010_1010);
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     OR CX, BX, AX           ; CX = AX | BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 | value2, computer.getCX(), "CX should have AX | BX");
        assertEquals((short) ((char) 0xFFFF), computer.getCX(), "CX should have all 1's");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Oring zero with a value")
    public void testZeroOr() {
        short value1 = 0;
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     OR CX, BX, AX           ; CX = AX | BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(0b0101_0101_0101_0101, computer.getCX(), "CX should have BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Oring a value with zero")
    public void testZeroOr2() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = 0;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     OR CX, BX, AX           ; CX = AX | BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Oring all 1's with a value")
    public void testOnesOr() {
        short value1 = (short) ((char) 0b1111_1111_1111_1111);
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     OR CX, BX, AX           ; CX = AX | BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 | value2, computer.getCX(), "CX should have AX | BX");
        assertEquals((short) ((char) 0xFFFF), computer.getCX(), "CX should have all 1's");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Oring a value with all ones")
    public void testOnesOr2() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = (short) ((char) 0b1111_1111_1111_1111);
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     OR CX, BX, AX           ; CX = AX | BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 | value2, computer.getCX(), "CX should have AX | BX");
        assertEquals((short) ((char) 0xFFFF), computer.getCX(), "CX should have all 1's");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

}
