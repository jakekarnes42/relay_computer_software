package org.karnes.homebrew.emulator;

import java.util.HashMap;
import java.util.Map;

public class InstructionIdentifier {
    private static final Map<String, Short> instructionMask = new HashMap<>();
    private static final Map<String, Short> stackMask = new HashMap<>();
    private static final Map<String, Short> registerMaskLast3 = new HashMap<>();
    private static final Map<String, Short> registerMaskMid3 = new HashMap<>();
    private static final Map<String, Short> registerMaskFirst3 = new HashMap<>();
    private static final Map<String, Short> instructionFamilyMask = new HashMap<>();

    //Initialize the maps
    {


        instructionMask.put("NOP", (short) 0b0000_0000_0000_0000);
        instructionMask.put("HALT", (short) 0b1111_1111_1111_1111);
        instructionMask.put("MOV", (short) 0b1111_1111_11_000_000);
        instructionMask.put("INC", (short) 0b0001_0000_00_000_000);
        instructionMask.put("DEC", (short) 0b0001_0000_01_000_000);
        instructionMask.put("NOT", (short) 0b0001_0000_10_000_000);
        instructionMask.put("ROL", (short) 0b0001_0000_11_000_000);
        instructionMask.put("ADD", (short) 0b0001_1_00_000_000_000);
        instructionMask.put("AND", (short) 0b0001_1_01_000_000_000);
        instructionMask.put("OR", (short) 0b0001_1_10_000_000_000);
        instructionMask.put("XOR", (short) 0b0001_1_11_000_000_000);
        instructionMask.put("CMP", (short) 0b0001_0_10_000_000_000);
        instructionMask.put("SUB", (short) 0b0001_0_10_000_000_000);
        instructionMask.put("LOADI", (short) 0b0010_0001_0000_0_000);
        instructionMask.put("JMP", (short) 0b0010_0001_0000_0_111);
        instructionMask.put("JNEG", (short) 0b0000_1000_000_0_0001);
        instructionMask.put("JZ", (short) 0b0000_1000_000_0_0010);
        instructionMask.put("JC", (short) 0b0000_1000_000_0_0100);
        instructionMask.put("JO", (short) 0b0000_1000_000_0_1000);
        instructionMask.put("JNNEG", (short) 0b0000_1000_000_1_0001);
        instructionMask.put("JNZ", (short) 0b0000_1000_000_1_0010);
        instructionMask.put("JNC", (short) 0b0000_1000_000_1_0100);
        instructionMask.put("JNO", (short) 0b0000_1000_000_1_1000);
        instructionMask.put("STORE", (short) 0b0010_0010_00_000_000);
        instructionMask.put("LOAD", (short) 0b0010_0100_00_000_000);
        instructionMask.put("PUSH", (short) 0b0100_0000_0001_0_000);
        instructionMask.put("POP", (short) 0b0100_0000_0010_0_000);
        instructionMask.put("CALLI", (short) 0b0100_0000_0100_0_000);
        instructionMask.put("CALL", (short) 0b0100_0000_1000_0_000);
        instructionMask.put("WRDIN", (short) 0b0100_0000_0000_0_000);
        instructionMask.put("WRDOUT", (short) 0b0100_1000_0000_0_000);


        registerMaskFirst3.put("AX", (short) 0b0000000_000_000_000);
        registerMaskFirst3.put("BX", (short) 0b0000000_001_000_000);
        registerMaskFirst3.put("CX", (short) 0b0000000_010_000_000);
        registerMaskFirst3.put("DX", (short) 0b0000000_011_000_000);
        registerMaskFirst3.put("EX", (short) 0b0000000_100_000_000);
        registerMaskFirst3.put("SP", (short) 0b0000000_101_000_000);
        registerMaskFirst3.put("RP", (short) 0b0000000_110_000_000);
        registerMaskFirst3.put("PC", (short) 0b0000000_111_000_000);


        registerMaskMid3.put("AX", (short) 0b00000000_00_000_000);
        registerMaskMid3.put("BX", (short) 0b00000000_00_001_000);
        registerMaskMid3.put("CX", (short) 0b00000000_00_010_000);
        registerMaskMid3.put("DX", (short) 0b00000000_00_011_000);
        registerMaskMid3.put("EX", (short) 0b00000000_00_100_000);
        registerMaskMid3.put("SP", (short) 0b00000000_00_101_000);
        registerMaskMid3.put("RP", (short) 0b00000000_00_110_000);
        registerMaskMid3.put("PC", (short) 0b00000000_00_111_000);


        registerMaskLast3.put("AX", (short) 0b00000000_00000_000);
        registerMaskLast3.put("BX", (short) 0b00000000_00000_001);
        registerMaskLast3.put("CX", (short) 0b00000000_00000_010);
        registerMaskLast3.put("DX", (short) 0b00000000_00000_011);
        registerMaskLast3.put("EX", (short) 0b00000000_00000_100);
        registerMaskLast3.put("SP", (short) 0b00000000_00000_101);
        registerMaskLast3.put("RP", (short) 0b00000000_00000_110);
        registerMaskLast3.put("PC", (short) 0b00000000_00000_111);

        //Set up Stack Register map
        stackMask.put("SP", (short) 0b00000000_0000_0_000);
        stackMask.put("RP", (short) 0b00000000_0000_1_000);
    }
}
