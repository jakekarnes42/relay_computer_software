package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovTest {

    @Test
    @DisplayName("Simple MOV")
    public void testSimpleMov() {
        short value = 1000;
        String code = ";Simply MOV from one reg to another\r\n"
                + "         LOAD AX, " + value + "  ; AX = value\r\n"
                + "         MOV BX, AX              ; BX = AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(computer.getAX(), computer.getBX(), "BX should have the same value as AX");
    }

    @Test
    @DisplayName("Chain MOV")
    public void testChainMov() {
        short value = 1000;
        String code = ";MOV value through all non-PC registers\r\n"
                + "         LOAD AX, " + value + "  ; AX = value\r\n"
                + "         MOV BX, AX              ; BX = AX\r\n"
                + "         MOV CX, BX              ; CX = BX\r\n"
                + "         MOV DX, CX              ; DX = CX\r\n"
                + "         MOV EX, DX              ; EX = DX\r\n"
                + "         MOV SP, EX              ; SP = EX\r\n"
                + "         MOV RP, SP              ; RP = SP\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(value, computer.getAX(), "AX should have its original value");
        assertEquals(computer.getAX(), computer.getBX(), "BX should have the same value as AX");
        assertEquals(computer.getBX(), computer.getCX(), "CX should have the same value as BX");
        assertEquals(computer.getCX(), computer.getDX(), "DX should have the same value as CX");
        assertEquals(computer.getDX(), computer.getEX(), "EX should have the same value as DX");
        assertEquals(computer.getEX(), computer.getSP(), "SP should have the same value as EX");
        assertEquals(computer.getSP(), computer.getRP(), "RP should have the same value as SP");
        assertEquals(computer.getRP(), computer.getAX(), "AX should have the same value as RP");
    }

    @Test
    @DisplayName("Clear register 1")
    public void testClearRegister1() {
        short value = 1000;
        String code = ";Clear the register by moving it to itself\r\n"
                + "         LOAD AX, " + value + "  ; AX = value\r\n"
                + "         MOV  AX , AX             ; clear AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(0, computer.getAX(), "AX should have its value cleared");

    }

    @Test
    @DisplayName("Clear register 2")
    public void testClearRegister2() {
        short value = 1000;
        String code = ";Clear the register by moving it to itself\r\n"
                + "         LOAD AX, " + value + "  ; AX = value\r\n"
                + "         CLR AX             ; clear AX\r\n"
                + "         HALT                    ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals(0, computer.getAX(), "AX should have its value cleared");

    }


}
