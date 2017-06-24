package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.ForthRelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by jake on 4/25/17.
 */
public class DirectiveTest {

    @Test
    @DisplayName("Test a simple directive case")
    public void testSimpleDirective() {
        String code = ";Let's play with directives!\r\n"
                + "         JMP {1000}; Jump to some location\r\n"
                + "\r\n"
                + ";Some code in a memory location      \r\n"
                + "         ORG {1000}          ; Move code to line 1000        \r\n"
                + "         LOAD AX, {20*20}    ; load some value into AX        \r\n"
                + "         {var CELL = 2}      ; Assembler variable CELL = 2     \r\n"
                + "         {MY_CONST = 42}     ; Assembler constant = 422     \r\n"
                + "         LOAD BX, {CELL}     ; BX = CELL        \r\n"
                + "         LOAD CX, {CELL + 5} ; CX = CELL + 5     \r\n"
                + "         LOAD DX, {MY_CONST} ; DX = MY_CONST     \r\n"
                + "         HALT                ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        byte[] RAM = assembler.assemble(code);

        ForthRelayComputer computer = new ForthRelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((char) 20 * 20, computer.getAX(), "AX should have the result of \"20*20\"");
        assertEquals((char) 2, computer.getBX(), "BX should have the value of the CELL variable");
        assertEquals((char) 2 + 5, computer.getCX(), "CX should be have the value of CELL + 5");
        assertEquals((char) 42, computer.getDX(), "DX should be have the value of MY_CONST");
    }

    @Test
    @DisplayName("Test declaring a byte directly")
    public void testDeclareWord() {
        String code = ";Let's play with directives!\r\n"
                + " DW 0xABCD; Write word\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        byte[] RAM = assembler.assemble(code);

        assertEquals((byte) 0xAB, RAM[0], "Declare Word should set the high byte correctly");
        assertEquals((byte) 0xCD, RAM[1], "Declare Word should set the low byte correctly");
    }


}
