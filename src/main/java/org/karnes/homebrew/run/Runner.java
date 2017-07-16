package org.karnes.homebrew.run;

import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

/**
 * Created by jake on 4/24/17.
 */
public class Runner {

    public static void main(String[] args) {
        String code = ";Let's subtract!\r\n"
                + "         LOADI SP, 1000; Get the stack pointer far away\r\n"
                + "         LOADI RP, 2000; Get the return pointer far away\r\n"
                + "         LOADI BX, 100; B = 100\r\n"
                + "         LOADI CX, 777; C = 777\r\n"
                + "         CALLI RP, SUBME; A = CX - BX\r\n"
                + "         HALT        ; DONE\r\n"
                + "\r\n"
                + ";SUBME C - B -> A                      \r\n"
                + "SUBME:   PUSH SP, BX     ; Save B        \r\n"
                + "         PUSH SP, CX     ; Save C        \r\n"
                + "         NOT AX, BX      ; A = NOT B     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         INC AX, BX      ; A = B + 1     \r\n"
                + "         MOV BX, AX      ; B = A         \r\n"
                + "         ADD AX, BX, CX  ; A = B + C     \r\n"
                + "         POP CX, SP      ; restore C     \r\n"
                + "         POP BX, SP      ; restore B     \r\n"
                + "         POP PC, RP      ; return        \r\n"
                + "\r\n";


//        String code = ";Let's play with directives!\r\n"
//                + "         JMP {1000}; Jump to some location\r\n"
//                + "\r\n"
//                + ";Some code in a memory location      \r\n"
//                + "         ORG {1000}          ; Move code to line 1000        \r\n"
//                + "         LOAD AX, {20*20}    ; load some value into AX        \r\n"
//                + "         {var CELL = 2; CELL}      ; Assembler variable CELL = 2     \r\n"
//                + "         LOAD BX, {CELL}     ; BX = CELL        \r\n"
//                + "         LOAD CX, {parseInt(CELL + 5)} ; CX = CELL + 5     \r\n"
//                + "         HALT        ; DONE\r\n"
//                + "\r\n";
//
        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();
        System.out.println("AX = " + (int) computer.getAX());
        System.out.println("BX = " + (int) computer.getBX());
        System.out.println("CX = " + (int) computer.getCX());

//        char val1 = Character.MAX_VALUE - 1;
//        short output = (short) (((val1 & 0xffff) << 1) | ((val1 & 0xffff) >>> (16 - 1)));
//        boolean carry = false;
//        boolean overflow = false;
//        boolean zero = output == 0;
//        boolean sign = output < 0;
//        short newValue = output;
//        System.out.println("Carry = " + carry + " Overflow = " + overflow + " Zero = " + zero + " sign = " + sign + " New Value = " + newValue + " Binary = " + Integer.toBinaryString(0xFFFF & newValue));

    }
}
