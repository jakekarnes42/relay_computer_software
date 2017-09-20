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
                + " DW 0xABCD; Write hex word\r\n"
                + " DW 1234; Write dec word\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        assertEquals((short) 0xABCD, RAM[0], "Declare Word should set the hex short into memory");
        assertEquals((short) 1234, RAM[1], "Declare Word should set the decimal short into memory");

    }

    @Test
    @DisplayName("Test declaring a word directly with JS values")
    public void testDeclareWordJS() {
        String code = ";Let's play with directives!\r\n"
                + " DW {8*8}; Write word, from JS value expression\r\n"
                + " DW {9*9}; Write word, from JS value expression\r\n"
                + " DW {var x = 10; x;}; Write word, from JS value expression\r\n"
                + " DW {x+10}; Write word, from JS value expression\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        assertEquals((short) 8 * 8, RAM[0], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 9 * 9, RAM[1], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 10, RAM[2], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 10 + 10, RAM[3], "Declare Word should evaluate the JS expressions and store result into memory");
    }


    @Test
    @DisplayName("Test declaring multiple words on a single line")
    public void testDeclareMultipleWord() {
        String code = ";Let's play with directives!\r\n"
                + " DW 42 {8*8} {9*9} {var x = 10; x;} {x+10}; Write multiple words to memory\r\n"
                + " DW 42, {var x = 10; x;}, {x+10}; Write multiple words to memory, with commas!\r\n"
                + " DW 73; Write word, from JS value expression\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        assertEquals((short) 42, RAM[0], "Declare Word should evaluate the literal expression and store result into memory");
        assertEquals((short) 8 * 8, RAM[1], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 9 * 9, RAM[2], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 10, RAM[3], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 10 + 10, RAM[4], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 42, RAM[5], "Declare Word should evaluate the literal expression and store result into memory");
        assertEquals((short) 10, RAM[6], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 10 + 10, RAM[7], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 73, RAM[8], "Declare Word should evaluate the literal expression and store result into memory");
    }

    @Test
    @DisplayName("Test $ mirror of code pointer")
    public void testCodePointerMirror() {
        String code = ";Let's play with directives!\r\n"
                + " DW {$}; Write current code pointer value, from JS value expression\r\n"
                + " DW {$}; Write current code pointer value, from JS value expression\r\n"
                + " DW {$}; Write current code pointer value, from JS value expression\r\n"
                + " DW {$}; Write current code pointer value, from JS value expression\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        assertEquals((short) 0, RAM[0], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 1, RAM[1], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 2, RAM[2], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 3, RAM[3], "Declare Word should evaluate the JS expressions and store result into memory");
    }

    @Test
    @DisplayName("Test ORG and DW together")
    public void testORGandDWcombo() {


        String code = ";Let's play with directives!\r\n"
                + " ORG {5 * 5} ; Moves the code pointer to memory location 25\r\n"
                + " DW 100      ; The value 100 is written into memory location 25\r\n"
                + " DW {1+2}    ; The value 3 is written into memory location 26\r\n"
                + " DW {$}      ; The value 27 is written into memory location 27\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        assertEquals((short) 0, RAM[0], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 100, RAM[25], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 3, RAM[26], "Declare Word should evaluate the JS expressions and store result into memory");
        assertEquals((short) 27, RAM[27], "Declare Word should evaluate the JS expressions and store result into memory");
    }


    @Test
    @DisplayName("Test declaring a word whose value is a label")
    public void testDWLabel() {
        String code = ";Let's declare a label!\r\n"
                + "         {FAR_LOC = 9000}    ; Set up a constant of where we'll write the label\r\n"
                + "         LOAD SP, 1000       ; Get the stack pointer far away\r\n"
                + "         LOAD RP, 2000       ; Get the return pointer far away\r\n"
                + "         LOAD BX, 100        ; B = 100\r\n"
                + "         LOAD CX, 777        ; C = 777\r\n"

                //"Manual" JMP by loading label address
                + "         {var tempLoc = $;}  ; Save the current code pointer\r\n"
                + "         ORG {FAR_LOC}       ; Move the code pointer far into memory\r\n"
                + "         DW LONGSUB          ; Write the label address in that location\r\n"
                + "         ORG {tempLoc;}      ; Move the code pointer back to where it was\r\n"
                + "         LOAD AX, {FAR_LOC}  ; Load the label address into memory\r\n"
                + "         FETCH PC, AX        ; 'Jump' to LONGSUB by loading the label into PC\r\n"

                + "\r\n"
                + ";LONGSUB C - B -> A                      \r\n"
                + "LONGSUB: PUSH SP, BX     ; Save B        \r\n"
                + "         PUSH SP, CX     ; Save C        \r\n"
                + "         NOT AX, BX      ; A = NOT B     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         INC AX, BX      ; A = B + 1     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         ADD AX, BX, CX  ; A = B + C     \r\n"
                + "         POP CX, SP      ; restore C     \r\n"
                + "         POP BX, SP      ; restore B     \r\n"
                + "         HALT            ; DONE\r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 677, computer.getAX(), "AX should have the result of CX - BX");
        assertEquals((short) 100, computer.getBX(), "BX should be restored to original value");
        assertEquals((short) 777, computer.getCX(), "CX should be restored to original value");
    }


    @Test
    @DisplayName("Test setting a variable in Javascript, and referencing it later")
    public void testRawJavascript() {
        String code = ";Let's declare a label!\r\n"
                + " {MY_VAL = 42}       ; Set up a constant\r\n"
                + " LOAD AX, {MY_VAL}   ; Load the constant into AX\r\n"
                + " HALT                ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 42, computer.getAX(), "AX should have the constant's value (42)");
    }

    @Test
    @DisplayName("Declaring a multi-letter String, that looks like a register")
    public void testDSRegString() {
        String testStr = "AX";

        String code = ";Let's declare a string!\r\n"
                + " DS \"" + testStr + "\"       ; Declare a string\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        for (int i = 0; i < testStr.length(); i++) {
            assertEquals((short) testStr.charAt(i), RAM[i], "The memory at address " + i + " should have the string's character.");
        }
    }

    @Test
    @DisplayName("Declaring a multi-letter String")
    public void testDSMultiLetter() {
        String testStr = "Test123";

        String code = ";Let's declare a string!\r\n"
                + " DS \"" + testStr + "\"       ; Declare a string\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        for (int i = 0; i < testStr.length(); i++) {
            assertEquals((short) testStr.charAt(i), RAM[i], "The memory at address " + i + " should have the string's character.");
        }
    }

    @Test
    @DisplayName("Declaring a one letter String")
    public void testDS1Letter() {
        String testStr = "!";

        String code = ";Let's declare a string!\r\n"
                + " DS \"" + testStr + "\"       ; Declare a string\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        for (int i = 0; i < testStr.length(); i++) {
            assertEquals((short) testStr.charAt(i), RAM[i], "The memory at address " + i + " should have the string's character.");
        }
    }

    @Test
    @DisplayName("Declaring a one letter String")
    public void testDS1Letter2() {
        String testStr = "@";

        String code = ";Let's declare a string!\r\n"
                + " DS \"" + testStr + "\"       ; Declare a string\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        for (int i = 0; i < testStr.length(); i++) {
            assertEquals((short) testStr.charAt(i), RAM[i], "The memory at address " + i + " should have the string's character.");
        }
    }


    @Test
    @DisplayName("Declaring a one letter String")
    public void testDS1Letter3() {
        String testStr = "1";

        String code = ";Let's declare a string!\r\n"
                + " DS \"" + testStr + "\"       ; Declare a string\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        for (int i = 0; i < testStr.length(); i++) {
            assertEquals((short) testStr.charAt(i), RAM[i], "The memory at address " + i + " should have the string's character.");
        }
    }

    @Test
    @DisplayName("Declaring a one letter String")
    public void testDS1Letter4() {
        String testStr = "A";

        String code = ";Let's declare a string!\r\n"
                + " DS \"" + testStr + "\"       ; Declare a string\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        for (int i = 0; i < testStr.length(); i++) {
            assertEquals((short) testStr.charAt(i), RAM[i], "The memory at address " + i + " should have the string's character.");
        }
    }

}
