package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MemoryInstructionTest {

    @Test
    @DisplayName("Simple LOAD")
    public void testSimpleLoad() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;
        short value5 = 5000;
        short value6 = 6000;
        short value7 = 7000;

        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     LOAD CX, " + value3 + " ; CX = value3\r\n"
                + "     LOAD DX, " + value4 + " ; DX = value4\r\n"
                + "     LOAD EX, " + value5 + " ; EX = value5\r\n"
                + "     LOAD SP, " + value6 + " ; SP = value6\r\n"
                + "     LOAD RP, " + value7 + " ; RP = value7\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(value2, computer.getBX(), "BX should have the loaded value");
        assertEquals(value3, computer.getCX(), "CX should have the loaded value");
        assertEquals(value4, computer.getDX(), "DX should have the loaded value");
        assertEquals(value5, computer.getEX(), "EX should have the loaded value");
        assertEquals(value6, computer.getSP(), "FX should have the loaded value");
        assertEquals(value7, computer.getRP(), "GX should have the loaded value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Load label addresses into registers")
    public void testLabelLoad() {
        String code = " LABEL1: LOAD AX, LABEL1  ; AX = LABEL1_ADDR\r\n"
                + "     LABEL2: LOAD BX, LABEL2  ; BX = LABEL2_ADDR\r\n"
                + "     LABEL3: LOAD CX, LABEL3  ; CX = LABEL3_ADDR\r\n"
                + "     LABEL4: LOAD DX, LABEL4  ; DX = LABEL4_ADDR\r\n"
                + "     LABEL5: LOAD EX, LABEL5  ; EX = LABEL5_ADDR\r\n"
                + "     LABEL6: LOAD SP, LABEL6  ; SP = LABEL6_ADDR\r\n"
                + "     LABEL7: LOAD RP, LABEL7  ; RP = LABEL7_ADDR\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        //Offset by two because each LOAD instruction is two words wide in memory
        assertEquals(0, computer.getAX(), "AX should have the loaded value");
        assertEquals(2, computer.getBX(), "BX should have the loaded value");
        assertEquals(4, computer.getCX(), "CX should have the loaded value");
        assertEquals(6, computer.getDX(), "DX should have the loaded value");
        assertEquals(8, computer.getEX(), "EX should have the loaded value");
        assertEquals(10, computer.getSP(), "FX should have the loaded value");
        assertEquals(12, computer.getRP(), "GX should have the loaded value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Load label addresses into PC register directly to cause a jump")
    public void testLoadPCDirect() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;
        short value5 = 5000;
        short value6 = 6000;
        short value7 = 7000;

        String code = "     LOAD PC, LOAD_RG ; Jump to loading registers\r\n"
                + "DONE:    HALT                    ; DONE\r\n"
                + "LOAD_RG: LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "         LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "         LOAD CX, " + value3 + " ; CX = value3\r\n"
                + "         LOAD DX, " + value4 + " ; DX = value4\r\n"
                + "         LOAD EX, " + value5 + " ; EX = value5\r\n"
                + "         LOAD SP, " + value6 + " ; SP = value6\r\n"
                + "         LOAD RP, " + value7 + " ; RP = value7\r\n"
                + "         LOAD PC, DONE ; Jump to HALT instruction\r\n";


        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(value2, computer.getBX(), "BX should have the loaded value");
        assertEquals(value3, computer.getCX(), "CX should have the loaded value");
        assertEquals(value4, computer.getDX(), "DX should have the loaded value");
        assertEquals(value5, computer.getEX(), "EX should have the loaded value");
        assertEquals(value6, computer.getSP(), "FX should have the loaded value");
        assertEquals(value7, computer.getRP(), "GX should have the loaded value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }


    @Test
    @DisplayName("Simple STORE")
    public void testSimpleSTORE() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;
        short value5 = 5000;
        short value6 = 6000;
        short value7 = 7000;

        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     LOAD CX, " + value3 + " ; CX = value3\r\n"
                + "     LOAD DX, " + value4 + " ; DX = value4\r\n"
                + "     LOAD EX, " + value5 + " ; EX = value5\r\n"
                + "     LOAD SP, " + value6 + " ; SP = value6\r\n"
                + "     LOAD RP, " + value7 + " ; RP = value7\r\n"
                + "     STORE AX, BX            ; Store BX into mem[AX]\r\n"
                + "     STORE BX, CX            ; Store CX into mem[BX]\r\n"
                + "     STORE CX, DX            ; Store DX into mem[CX]\r\n"
                + "     STORE DX, EX            ; Store EX into mem[DX]\r\n"
                + "     STORE EX, SP            ; Store SP into mem[EX]\r\n"
                + "     STORE SP, RP            ; Store RP into mem[SP]\r\n"
                + "     STORE RP, AX            ; Store AX into mem[RP]\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(value2, computer.getBX(), "BX should have the loaded value");
        assertEquals(value3, computer.getCX(), "CX should have the loaded value");
        assertEquals(value4, computer.getDX(), "DX should have the loaded value");
        assertEquals(value5, computer.getEX(), "EX should have the loaded value");
        assertEquals(value6, computer.getSP(), "FX should have the loaded value");
        assertEquals(value7, computer.getRP(), "GX should have the loaded value");

        //Check Memory
        assertEquals(value2, RAM[value1], "The BX value should be stored in the memory address pointed to by AX");
        assertEquals(value3, RAM[value2], "The CX value should be stored in the memory address pointed to by BX");
        assertEquals(value4, RAM[value3], "The DX value should be stored in the memory address pointed to by CX");
        assertEquals(value5, RAM[value4], "The EX value should be stored in the memory address pointed to by DX");
        assertEquals(value6, RAM[value5], "The SP value should be stored in the memory address pointed to by EX");
        assertEquals(value7, RAM[value6], "The RP value should be stored in the memory address pointed to by SP");
        assertEquals(value1, RAM[value7], "The AX value should be stored in the memory address pointed to by RP");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Simple FETCH")
    public void testSimpleFetch() {
        short value1 = 1000;
        short value2 = 2000;

        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     STORE AX, BX            ; Store BX into mem[AX]\r\n"
                + "     FETCH CX, AX            ; Store mem[AX] into CX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(value2, computer.getBX(), "BX should have the loaded value");
        assertEquals(value2, computer.getCX(), "CX should have the loaded value");

        //Check Memory
        assertEquals(value2, RAM[value1], "The BX value should be stored in the memory address pointed to by AX");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }


}
