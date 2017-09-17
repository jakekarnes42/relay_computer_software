package org.karnes.homebrew.run;

import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

public class Runner {

    public static void main(String[] args) {
        String code = ";Let's subtract!\r\n"
                + "         LOAD SP, 1000; Get the stack pointer far away\r\n"
                + "         LOAD RP, 2000; Get the return pointer far away\r\n"
                + "         LOAD BX, 100; B = 100\r\n"
                + "         LOAD CX, 777; C = 777\r\n"
                + "         CALL RP, SUBME; A = CX - BX\r\n"
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
                + "         RET RP      ; return        \r\n"
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

    }
}
