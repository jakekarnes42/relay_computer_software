package temp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.*;

public class CmpTest {


    @Test
    @DisplayName("Test comparison. A=C-B. B is less than C.")
    public void testComparison() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, 150    ; B = 150\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
    @DisplayName("Test comparison. A=C-B. B is greater than C.")
    public void testReverseComparison() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, 250    ; B = 250\r\n"
                + "         LOAD CX, 150    ; C = 150\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
    @DisplayName("Test comparison. A=C-B. B is less than C. Values are negative")
    public void testNegativeComparison() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, -250    ; B = -250\r\n"
                + "         LOAD CX, -150    ; C = -150\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 100, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -250, computer.getBX(), "BX should still have its original value");
        assertEquals((short) -150, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set since CX is greater than BX");
        assertTrue(computer.getOverflowFlag(), "The overflow flag should be set, since the output's sign doesn't match the inputs' signs");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set, since the result is positive");
    }

    @Test
    @DisplayName("Test comparison. A=C-B. B is greater than C. Values are negative")
    public void testNegativeComparison2() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, -150    ; B = -150\r\n"
                + "         LOAD CX, -250    ; C = -250\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) -100, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -150, computer.getBX(), "BX should still have its original value");
        assertEquals((short) -250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set since CX is less than BX");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set, since the output's sign matches the inputs' signs");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set, since the result is negative");
    }

    @Test
    @DisplayName("Test comparison. A=C-B. B is less than C. B is negative")
    public void testNegativeComparison3() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, -150   ; B = -150\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 400, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -150, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Test comparison. A=C-B. B is less than C. C is negative")
    public void testNegativeComparison4() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, 150   ; B = 150\r\n"
                + "         LOAD CX, -250    ; C = -250\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) -400, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 150, computer.getBX(), "BX should still have its original value");
        assertEquals((short) -250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }


    @Test
    @DisplayName("Test comparison. A=C-B. B is equal to C.")
    public void testEqualsComparison() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, 250    ; B = 250\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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

    @Test
    @DisplayName("Test comparison. A=C-B. B is less than C. Large values.")
    public void testComparisonLarge() {
        String code = ";Let's compare!\r\n"
                + "         LOAD BX, -32768 ; B = -32768\r\n"
                + "         LOAD CX, 32767  ; C = 32767\r\n"
                + "         CMP AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) -1, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -32768, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 32767, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

    @Test
    @DisplayName("Test subtraction. A=C-B. B is less than C.")
    public void testSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, 150    ; B = 150\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
    @DisplayName("Test subtraction. A=C-B. B is greater than C.")
    public void testReverseSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, 250    ; B = 250\r\n"
                + "         LOAD CX, 150    ; C = 150\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
    @DisplayName("Test subtraction. A=C-B. B is less than C. Values are negative")
    public void testNegativeSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, -250    ; B = -250\r\n"
                + "         LOAD CX, -150    ; C = -150\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 100, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -250, computer.getBX(), "BX should still have its original value");
        assertEquals((short) -150, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set since CX is greater than BX");
        assertTrue(computer.getOverflowFlag(), "The overflow flag should be set, since the output's sign doesn't match the inputs' signs");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set, since the result is positive");
    }

    @Test
    @DisplayName("Test subtraction. A=C-B. B is greater than C. Values are negative")
    public void testNegativeSubtraction2() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, -150    ; B = -150\r\n"
                + "         LOAD CX, -250    ; C = -250\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) -100, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -150, computer.getBX(), "BX should still have its original value");
        assertEquals((short) -250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set since CX is less than BX");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set, since the output's sign matches the inputs' signs");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set, since the result is negative");
    }

    @Test
    @DisplayName("Test subtraction. A=C-B. B is less than C. B is negative")
    public void testNegativeSubtraction3() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, -150   ; B = -150\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 400, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -150, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Test subtraction. A=C-B. B is less than C. C is negative")
    public void testNegativeSubtraction4() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, 150   ; B = 150\r\n"
                + "         LOAD CX, -250    ; C = -250\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) -400, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 150, computer.getBX(), "BX should still have its original value");
        assertEquals((short) -250, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }


    @Test
    @DisplayName("Test subtraction. A=C-B. B is equal to C.")
    public void testEqualsSubtraction() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, 250    ; B = 250\r\n"
                + "         LOAD CX, 250    ; C = 250\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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

    @Test
    @DisplayName("Test subtraction. A=C-B. B is less than C. Large values.")
    public void testSubtractionLarge() {
        String code = ";Let's subtract!\r\n"
                + "         LOAD BX, -32768 ; B = -32768\r\n"
                + "         LOAD CX, 32767  ; C = 32767\r\n"
                + "         SUB AX, CX, BX  ; AX = CX - BX\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) -1, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) -32768, computer.getBX(), "BX should still have its original value");
        assertEquals((short) 32767, computer.getCX(), "CX should still have its original value");

        //Check condition registers
        assertTrue(computer.getCarryFlag(), "The carry flag should be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertTrue(computer.getSignFlag(), "The sign flag should be set");
    }

}
