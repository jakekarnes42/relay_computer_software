package org.karnes.homebrew;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 20 * 20, computer.getAX(), "AX should have the result of \"20*20\"");
        assertEquals((short) 2, computer.getBX(), "BX should have the value of the CELL variable");
        assertEquals((short) 2 + 5, computer.getCX(), "CX should be have the value of CELL + 5");
        assertEquals((short) 42, computer.getDX(), "DX should be have the value of MY_CONST");
    }

    @Test
    @DisplayName("Test declaring a word directly")
    public void testDeclareWord() {
        String code = ";Let's play with directives!\r\n"
                + " DW 0xABCD; Write word\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        assertEquals((short) 0xABCD, RAM[0], "Declare Word should set the short into memory");

    }


}
