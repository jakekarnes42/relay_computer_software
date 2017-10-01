package temp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;


public class XorTest {

    @Test
    @DisplayName("Simple XOR")
    public void testSimpleXor() {
        short value1 = 1000;
        short value2 = 2000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     XOR CX, BX, AX          ; CX = AX ^ BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 ^ value2, computer.getCX(), "CX should have AX ^ BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Xoring register to itself, and storing in a different register")
    public void testSelfXorDifferentReg() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     XOR CX, AX, AX          ; CX = AX + AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value1 ^ value1, computer.getCX(), "CX should have AX ^ AX");
        assertEquals(0, computer.getCX(), "CX should be zero");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Double register by oring register to itself, and store into self")
    public void testSelfXorSameReg() {
        short value1 = 1000;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     XOR AX, AX, AX           ; AX = AX ^ AX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(0, computer.getAX(), "AX should be 0, since we XOR'd a value with itself");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Xoring offset alternating bit patterns")
    public void testAlternatingXor1() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = (short) ((char) 0b1010_1010_1010_1010);
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     XOR CX, BX, AX          ; CX = AX ^ BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 ^ value2, computer.getCX(), "CX should have AX ^ BX");
        assertEquals((short) ((char) 0xFFFF), computer.getCX(), "CX should have all 1's");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Xoring offset alternating bit patterns 2")
    public void testAlternatingXor2() {
        short value1 = (short) ((char) 0b1010_1010_1010_1010);
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     XOR CX, BX, AX          ; CX = AX ^ BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 ^ value2, computer.getCX(), "CX should have AX ^ BX");
        assertEquals((short) ((char) 0xFFFF), computer.getCX(), "CX should have all 1's");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Xoring zero with a value")
    public void testZeroXor() {
        short value1 = 0;
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     XOR CX, BX, AX          ; CX = AX ^ BX\r\n"
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
    @DisplayName("Xoring a value with zero")
    public void testZeroXor2() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = 0;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     XOR CX, BX, AX          ; CX = AX ^ BX\r\n"
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
    @DisplayName("Xoring all 1's with a value")
    public void testOnesXor() {
        short value1 = (short) ((char) 0b1111_1111_1111_1111);
        short value2 = 0b0101_0101_0101_0101;
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     XOR CX, BX, AX          ; CX = AX ^ BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 ^ value2, computer.getCX(), "CX should have AX ^ BX");
        assertEquals((short) ((char) 0b1010_1010_1010_1010), computer.getCX(), "CX should have ~BX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Xoring a value with all ones")
    public void testOnesXor2() {
        short value1 = 0b0101_0101_0101_0101;
        short value2 = (short) ((char) 0b1111_1111_1111_1111);
        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     XOR CX, BX, AX          ; CX = AX ^ BX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have its original value");
        assertEquals(value2, computer.getBX(), "BX should have its original value");
        assertEquals(value1 ^ value2, computer.getCX(), "CX should have AX ^ BX");
        assertEquals((short) ((char) 0b1010_1010_1010_1010), computer.getCX(), "CX should have ~AX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }
}
