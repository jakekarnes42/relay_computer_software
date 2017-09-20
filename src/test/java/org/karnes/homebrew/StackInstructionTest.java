package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StackInstructionTest {

    @Test
    @DisplayName("Simple PUSH")
    public void testSimplePUSH() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;


        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD SP, " + value2 + " ; Move the SP pointer far into memory\r\n"
                + "     PUSH SP, AX             ; Push AX onto SP\r\n"
                + "     LOAD BX, " + value3 + " ; BX = value3\r\n"
                + "     LOAD RP, " + value4 + " ; Move the RP pointer far into memory\r\n"
                + "     PUSH RP, BX             ; Push BX onto RP\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check SP stack
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(0, RAM[value2], "SP's old value in memory should be blank");
        assertEquals(value2 - 1, computer.getSP(), "SP should be decremented");
        assertEquals(value1, RAM[computer.getSP()], "SP should point to AX's value");

        //Check RP stack
        assertEquals(value3, computer.getBX(), "BX should have the loaded value");
        assertEquals(0, RAM[value4], "RP's old value in memory should be blank");
        assertEquals(value4 - 1, computer.getRP(), "RP should be decremented");
        assertEquals(value3, RAM[computer.getRP()], "RP should point to BX's value");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Multiple PUSH")
    public void testMultiplePUSH() {
        short SPstart = 1000;
        short RPstart = 2000;

        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;
        short value5 = 5000;


        String code = " LOAD SP, " + SPstart + "; Get SP far into memory\r\n"
                + "     LOAD RP, " + RPstart + "; Get RP far into memory\r\n"
                + "     LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD BX, " + value2 + " ; BX = value2\r\n"
                + "     LOAD CX, " + value3 + " ; CX = value3\r\n"
                + "     LOAD DX, " + value4 + " ; DX = value4\r\n"
                + "     LOAD EX, " + value5 + " ; EX = value5\r\n"
                + "     PUSH SP, AX             ; Push AX onto SP\r\n"
                + "     PUSH SP, BX             ; Push BX onto SP\r\n"
                + "     PUSH SP, CX             ; Push CX onto SP\r\n"
                + "     PUSH SP, DX             ; Push DX onto SP\r\n"
                + "     PUSH SP, EX             ; Push EX onto SP\r\n"
                + "     PUSH RP, AX             ; Push AX onto RP\r\n"
                + "     PUSH RP, BX             ; Push BX onto RP\r\n"
                + "     PUSH RP, CX             ; Push CX onto RP\r\n"
                + "     PUSH RP, DX             ; Push DX onto RP\r\n"
                + "     PUSH RP, EX             ; Push EX onto RP\r\n"
                + "     HALT                    ; DONE\r\n";


        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check SP stack
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(0, RAM[SPstart], "SP's old value in memory should be blank");
        assertEquals(SPstart - 5, computer.getSP(), "SP should be decremented 5 times");
        assertEquals(value1, RAM[SPstart - 1], "SP BOS should point to AX's value");
        assertEquals(value5, RAM[computer.getSP()], "SP TOS should point to EX's value");

        //Check RP stack
        assertEquals(0, RAM[RPstart], "RP's old value in memory should be blank");
        assertEquals(RPstart - 5, computer.getRP(), "RP should be decremented 5 times");
        assertEquals(value1, RAM[RPstart - 1], "RP BOS should point to AX's value");
        assertEquals(value5, RAM[computer.getRP()], "SP TOS should point to EX's value");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Simple POP")
    public void testSimplePop() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;


        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD SP, " + value2 + " ; Move the SP pointer far into memory\r\n"
                + "     PUSH SP, AX             ; Push AX onto SP\r\n"
                + "     POP CX, SP              ; Pop off SP into CX\r\n"
                + "     LOAD BX, " + value3 + " ; BX = value3\r\n"
                + "     LOAD RP, " + value4 + " ; Move the RP pointer far into memory\r\n"
                + "     PUSH RP, BX             ; Push BX onto RP\r\n"
                + "     POP DX, RP              ; Pop off RP into DX\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check SP stack
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(0, RAM[value2], "SP's old value in memory should be blank");
        assertEquals(value2, computer.getSP(), "SP should return to its original value");
        assertEquals(value1, computer.getCX(), "CX should have AX's value");

        //Check RP stack
        assertEquals(value3, computer.getBX(), "BX should have the loaded value");
        assertEquals(0, RAM[value4], "RP's old value in memory should be blank");
        assertEquals(value4, computer.getRP(), "RP should return to its original value");
        assertEquals(value3, computer.getDX(), "DX should have BX's value");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Multiple POP")
    public void testMultiplePOP() {
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
                + "     PUSH SP, AX             ; Push AX onto SP\r\n"
                + "     PUSH SP, BX             ; Push BX onto SP\r\n"
                + "     PUSH SP, CX             ; Push CX onto SP\r\n"
                + "     PUSH SP, DX             ; Push DX onto SP\r\n"
                + "     PUSH SP, EX             ; Push EX onto SP\r\n"
                + "     PUSH RP, AX             ; Push AX onto RP\r\n"
                + "     PUSH RP, BX             ; Push BX onto RP\r\n"
                + "     PUSH RP, CX             ; Push CX onto RP\r\n"
                + "     PUSH RP, DX             ; Push DX onto RP\r\n"
                + "     PUSH RP, EX             ; Push EX onto RP\r\n"
                + "     POP AX, SP              ; Pop SP into AX\r\n"
                + "     POP BX, SP              ; Pop SP into BX\r\n"
                + "     POP CX, SP              ; Pop SP into CX\r\n"
                + "     POP DX, SP              ; Pop SP into DX\r\n"
                + "     POP EX, SP              ; Pop SP into EX\r\n"
                + "     POP AX, RP              ; Pop RP into AX\r\n"
                + "     POP BX, RP              ; Pop RP into BX\r\n"
                + "     POP CX, RP              ; Pop RP into CX\r\n"
                + "     POP DX, RP              ; Pop RP into DX\r\n"
                + "     POP EX, RP              ; Pop RP into EX\r\n"
                + "     HALT                    ; DONE\r\n";


        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(value5, computer.getAX(), "AX should have EX's originally loaded value");
        assertEquals(value4, computer.getBX(), "BX should have DX's originally loaded value");
        assertEquals(value3, computer.getCX(), "CX should have CX's originally loaded value");
        assertEquals(value2, computer.getDX(), "DX should have BX's originally loaded value");
        assertEquals(value1, computer.getEX(), "EX should have AX's originally loaded value");

        //Check SP stack
        assertEquals(0, RAM[value6], "SP's old value in memory should be blank");
        assertEquals(value6, computer.getSP(), "SP should return to its original value");

        //Check RP stack
        assertEquals(0, RAM[value7], "RP's old value in memory should be blank");
        assertEquals(value7, computer.getRP(), "RP should return to its original value");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("CALL and RET instruction combination")
    public void testCALLandRET() {
        String code = ";Let's subtract with a subroutine!\r\n"
                + "         LOAD SP, 1000   ; Get the stack pointer far away\r\n"
                + "         LOAD RP, 2000   ; Get the return pointer far away\r\n"
                + "         LOAD BX, 100    ; B = 100\r\n"
                + "         LOAD CX, 777    ; C = 777\r\n"
                + "         LOAD DX, -100   ; D = -100\r\n"
                + "         LOAD EX, 777    ; E = 777\r\n"
                + "         CALL RP, LONGSUB; A = CX - BX\r\n"
                + "         CALL SP, SMLSUB ; A = EX - DX\r\n"
                + "         HALT            ; DONE\r\n"
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
                + "         RET RP          ; return        \r\n"
                + "\r\n"
                + ";SMLSUB C - B -> A                       \r\n"
                + "SMLSUB:  PUSH RP, DX     ; Save D        \r\n"
                + "         PUSH RP, EX     ; Save E        \r\n"
                + "         SUB AX, EX, DX  ; A = E - D     \r\n"
                + "         POP EX, RP      ; restore E     \r\n"
                + "         POP DX, RP      ; restore D     \r\n"
                + "         RET SP          ; return        \r\n"
                + "\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        assertEquals((short) 100, computer.getBX(), "BX should be restored to original value");
        assertEquals((short) 777, computer.getCX(), "CX should be restored to original value");
        assertEquals((short) -100, computer.getDX(), "DX should be restored to original value");
        assertEquals((short) 777, computer.getEX(), "EX should be restored to original value");
        assertEquals((short) 877, computer.getAX(), "AX should have the result of EX - DX");
    }

    @Test
    @DisplayName("Nested CALL and RET instruction combination with RP pointer")
    public void testNestedCALLandRETRP() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;
        short value5 = 5000;
        short value6 = 6000;
        short value7 = 7000;

        String code = ";Let's use nested subroutines\r\n"
                + "     LOAD SP, 1000   ; Get the stack pointer far away\r\n"
                + "     LOAD RP, 2000   ; Get the return pointer far away\r\n"
                + "     CALL RP , LOADER        ; Call loading sub routine\r\n"
                + "     HALT                    ; DONE\r\n"
                + ";This is not efficient but a neat test of nested subroutines\r\n"
                + "LOADE:       LOAD EX, " + value5 + "     ; EX = value5\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADD:       LOAD DX, " + value4 + "     ; DX = value4\r\n"
                + "             CALL RP, LOADE              ; Call LOADE subroutine\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADC:       LOAD CX, " + value3 + "     ; CX = value3\r\n"
                + "             CALL RP, LOADD              ; Call LOADD subroutine\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADB:       LOAD BX, " + value2 + "     ; BX = value2\r\n"
                + "             CALL RP, LOADC              ; Call LOADC subroutine\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADA:       LOAD AX, " + value1 + "     ; AX = value1\r\n"
                + "             CALL RP, LOADB              ; Call LOADB subroutine\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADER:      CALL RP , LOADA         ;Call LOADA subroutine\r\n"
                + "             RET RP                    ; DONE\r\n";


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

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Nested CALL and RET instruction combination with SP pointer")
    public void testNestedCALLandRETSP() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;
        short value5 = 5000;
        short value6 = 6000;
        short value7 = 7000;

        String code = ";Let's use nested subroutines\r\n"
                + "     LOAD SP, 1000   ; Get the stack pointer far away\r\n"
                + "     CALL SP , LOADER        ; Call loading sub routine\r\n"
                + "     HALT                    ; DONE\r\n"
                + ";This is not efficient but a neat test of nested subroutines\r\n"
                + "LOADE:       LOAD EX, " + value5 + "     ; EX = value5\r\n"
                + "             RET SP                      ; DONE\r\n"
                + "LOADD:       LOAD DX, " + value4 + "     ; DX = value4\r\n"
                + "             CALL SP, LOADE              ; Call LOADE subroutine\r\n"
                + "             RET SP                      ; DONE\r\n"
                + "LOADC:       LOAD CX, " + value3 + "     ; CX = value3\r\n"
                + "             CALL SP, LOADD              ; Call LOADD subroutine\r\n"
                + "             RET SP                      ; DONE\r\n"
                + "LOADB:       LOAD BX, " + value2 + "     ; BX = value2\r\n"
                + "             CALL SP, LOADC              ; Call LOADC subroutine\r\n"
                + "             RET SP                      ; DONE\r\n"
                + "LOADA:       LOAD AX, " + value1 + "     ; AX = value1\r\n"
                + "             CALL SP, LOADB              ; Call LOADB subroutine\r\n"
                + "             RET SP                      ; DONE\r\n"
                + "LOADER:      CALL SP , LOADA         ;Call LOADA subroutine\r\n"
                + "             RET SP                    ; DONE\r\n";


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

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Nested CALL and RET instruction combination with both pointers")
    public void testNestedCALLandRETBoth() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;
        short value5 = 5000;


        String code = ";Let's use nested subroutines\r\n"
                + "     LOAD SP, 1000   ; Get the stack pointer far away\r\n"
                + "     LOAD RP, 2000   ; Get the stack pointer far away\r\n"
                + "     CALL SP , LOADER        ; Call loading sub routine\r\n"
                + "     HALT                    ; DONE\r\n"
                + ";This is not efficient but a neat test of nested subroutines\r\n"
                + "LOADE:       LOAD EX, " + value5 + "     ; EX = value5\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADD:       LOAD DX, " + value4 + "     ; DX = value4\r\n"
                + "             CALL RP, LOADE              ; Call LOADE subroutine\r\n"
                + "             RET SP                      ; DONE\r\n"
                + "LOADC:       LOAD CX, " + value3 + "     ; CX = value3\r\n"
                + "             CALL SP, LOADD              ; Call LOADD subroutine\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADB:       LOAD BX, " + value2 + "     ; BX = value2\r\n"
                + "             CALL RP, LOADC              ; Call LOADC subroutine\r\n"
                + "             RET SP                      ; DONE\r\n"
                + "LOADA:       LOAD AX, " + value1 + "     ; AX = value1\r\n"
                + "             CALL SP, LOADB              ; Call LOADB subroutine\r\n"
                + "             RET RP                      ; DONE\r\n"
                + "LOADER:      CALL RP , LOADA         ;Call LOADA subroutine\r\n"
                + "             RET SP                    ; DONE\r\n";


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

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Copy value from TOS of one stack to the other")
    public void testTOScopy() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;


        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD SP, " + value2 + " ; Move the SP pointer far into memory\r\n"
                + "     PUSH SP, AX             ; Push AX onto SP\r\n"
                + "     LOAD BX, " + value3 + " ; BX = value3\r\n"
                + "     LOAD RP, " + value4 + " ; Move the RP pointer far into memory\r\n"
                + "     PUSH RP, BX             ; Push BX onto RP\r\n"

                //Let's copy TOS SP to TOS RP
                + "     FETCH CX, SP            ; Fetch (not pop) value at TOS SP\r\n"
                + "     PUSH RP, CX            ; Push value onto TOS RP\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check SP stack
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(0, RAM[value2], "SP's old value in memory should be blank");
        assertEquals(value2 - 1, computer.getSP(), "SP should be decremented");
        assertEquals(value1, RAM[computer.getSP()], "SP should point to AX's value");

        //Check copy
        assertEquals(value1, computer.getCX(), "CX should have the copied value");

        //Check RP stack
        assertEquals(value3, computer.getBX(), "BX should have the loaded value");
        assertEquals(0, RAM[value4], "RP's old value in memory should be blank");
        assertEquals(value4 - 2, computer.getRP(), "RP should be decremented twice, since we pushed twice");
        assertEquals(value1, RAM[computer.getRP()], "RP should point to AX's value (the copy)");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Duplicate value from TOS")
    public void testTOSduplicate() {
        short value1 = 1000;
        short value2 = 2000;
        short value3 = 3000;
        short value4 = 4000;


        String code = " LOAD AX, " + value1 + " ; AX = value1\r\n"
                + "     LOAD SP, " + value2 + " ; Move the SP pointer far into memory\r\n"
                + "     PUSH SP, AX             ; Push AX onto SP\r\n"
                + "     FETCH BX, SP            ; BX = TOS SP = AX\r\n"
                + "     PUSH SP, BX             ; Push BX onto SP\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);
        computer.start();

        //Check SP stack
        assertEquals(value1, computer.getAX(), "AX should have the loaded value");
        assertEquals(0, RAM[value2], "SP's old value in memory should be blank");
        assertEquals(computer.getAX(), computer.getBX(), "BX should have AX's value");
        assertEquals(value2 - 2, computer.getSP(), "SP should be decremented twice, since we pushed twice");
        assertEquals(computer.getBX(), RAM[computer.getSP()], "TOS SP should point to BX's value (the duplicate)");
        assertEquals(computer.getAX(), RAM[computer.getSP() + 1], "TOS-1 SP should point to AX's value (the original)");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

}
