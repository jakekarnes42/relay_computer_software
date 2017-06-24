package org.karnes.homebrew.assemblr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.karnes.homebrew.Util;
import org.karnes.homebrew.assemblr.parse.AsmHomeBrewParser;

import java.util.HashMap;
import java.util.Map;

import static org.karnes.homebrew.Util.literalToChar;


public class MachineCodeTranslator extends DirectiveExecutor {
    private final Map<String, Byte> stackMask;
    private final Map<String, Byte> registerMaskLast3;
    private final Map<String, Byte> registerMaskMid3;
    private final Map<String, Byte> instuctionMask;
    private final Map<String, Byte> aluRegisterDestinationMask;

    public MachineCodeTranslator(Map<String, Character> symbolTable) {
        this.symbolTable = symbolTable;

        //Set up masks
        instuctionMask = new HashMap<>();
        instuctionMask.put("MOV", (byte) 0b00_00000);
        instuctionMask.put("ADD", (byte) 0b0100_0_001);
        instuctionMask.put("INC", (byte) 0b0100_0_010);
        instuctionMask.put("AND", (byte) 0b0100_0_011);
        instuctionMask.put("OR", (byte) 0b0100_0_100);
        instuctionMask.put("XOR", (byte) 0b0100_0_101);
        instuctionMask.put("NOT", (byte) 0b0100_0_110);
        instuctionMask.put("ROL", (byte) 0b0100_0_111);
        instuctionMask.put("LOAD", (byte) 0b01010_000);
        instuctionMask.put("JMP", (byte) 0b01010111);
        instuctionMask.put("PUSH", (byte) 0b0110_0_000);
        instuctionMask.put("POP", (byte) 0b0111_0_000);
        instuctionMask.put("CALL", (byte) 0b1011_0_000);
        instuctionMask.put("HALT", (byte) 0b10000000);
        instuctionMask.put("JZ", (byte) 0b100_0_0001);
        instuctionMask.put("JNZ", (byte) 0b100_1_0001);
        instuctionMask.put("JNEG", (byte) 0b100_0_0010);
        instuctionMask.put("JNNEG", (byte) 0b100_1_0010);
        instuctionMask.put("JC", (byte) 0b100_0_0100);
        instuctionMask.put("JNC", (byte) 0b100_1_0100);
        instuctionMask.put("JO", (byte) 0b100_0_1000);
        instuctionMask.put("JNO", (byte) 0b100_1_1000);
        instuctionMask.put("NOP", (byte) 0b10011111);
        instuctionMask.put("BYIN", (byte) 0b1010_0_000);
        instuctionMask.put("BYOUT", (byte) 0b1010_1_000);
        instuctionMask.put("STORE", (byte) 0b11_000_000);

        registerMaskMid3 = new HashMap<>();
        registerMaskMid3.put("AX", (byte) 0b00_000_000);
        registerMaskMid3.put("BX", (byte) 0b00_001_000);
        registerMaskMid3.put("CX", (byte) 0b00_010_000);
        registerMaskMid3.put("DX", (byte) 0b00_011_000);
        registerMaskMid3.put("IP", (byte) 0b00_100_000);
        registerMaskMid3.put("SP", (byte) 0b00_101_000);
        registerMaskMid3.put("RP", (byte) 0b00_110_000);
        registerMaskMid3.put("PC", (byte) 0b00_111_000);

        registerMaskLast3 = new HashMap<>();
        registerMaskLast3.put("AX", (byte) 0b0000_000);
        registerMaskLast3.put("BX", (byte) 0b0000_001);
        registerMaskLast3.put("CX", (byte) 0b0000_010);
        registerMaskLast3.put("DX", (byte) 0b0000_011);
        registerMaskLast3.put("IP", (byte) 0b0000_100);
        registerMaskLast3.put("SP", (byte) 0b0000_101);
        registerMaskLast3.put("RP", (byte) 0b0000_110);
        registerMaskLast3.put("PC", (byte) 0b0000_111);

        aluRegisterDestinationMask = new HashMap<>();
        aluRegisterDestinationMask.put("AX", (byte) 0b0000_0_000);
        aluRegisterDestinationMask.put("DX", (byte) 0b0000_1_000);

        stackMask = new HashMap<>();
        stackMask.put("SP", (byte) 0b0000_0_000);
        stackMask.put("RP", (byte) 0b0000_1_000);
    }

    @Override
    public Void visitMovOperation(AsmHomeBrewParser.MovOperationContext ctx) {
        byte instruction = (byte) (instuctionMask.get("MOV") | getMidRegisterMask(ctx.register(0)) | getLastRegisterMask(ctx.register(1)));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitAluOperation(AsmHomeBrewParser.AluOperationContext ctx) {
        byte instruction = (byte) (getOpcodeMask(ctx.aluOpcode()) | getALURegisterMask(ctx.aluDestinationRegister()));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitLoadOperation(AsmHomeBrewParser.LoadOperationContext ctx) {
        //Get instruction
        byte instruction = (byte) (instuctionMask.get("LOAD") | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;

        //Get memory target
        char memoryTarget = getValue(ctx.value());

        //Store memory target into memory
        memory[counter + 1] = Util.getHighByteFromChar(memoryTarget);
        memory[counter + 2] = Util.getLowByteFromChar(memoryTarget);

        //Increment our counter.
        counter += 3;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx) {
        //Get instruction
        byte instruction = getOpcodeMask(ctx.jumpOpcode());

        //Store instruction into memory
        memory[counter] = instruction;

        //Get memory target
        char memoryTarget = getValue(ctx.value());

        //Store memory target into memory
        memory[counter + 1] = Util.getHighByteFromChar(memoryTarget);
        memory[counter + 2] = Util.getLowByteFromChar(memoryTarget);

        //Increment our counter.
        counter += 3;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitPushOperation(AsmHomeBrewParser.PushOperationContext ctx) {
        byte instruction = (byte) (instuctionMask.get("PUSH") | getStackMask(ctx.stackRegister()) | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitPopOperation(AsmHomeBrewParser.PopOperationContext ctx) {
        byte instruction = (byte) (instuctionMask.get("POP") | getStackMask(ctx.stackRegister()) | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitCallOperation(AsmHomeBrewParser.CallOperationContext ctx) {
        //Get instruction
        byte instruction = (byte) (instuctionMask.get("CALL") | getStackMask(ctx.stackRegister()));

        //Store instruction into memory
        memory[counter] = instruction;

        //Get memory target
        char memoryTarget = getValue(ctx.value());

        //Store memory target into memory
        memory[counter + 1] = Util.getHighByteFromChar(memoryTarget);
        memory[counter + 2] = Util.getLowByteFromChar(memoryTarget);

        //Increment our counter.
        counter += 3;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx) {
        byte instruction = getOpcodeMask(ctx);

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitIoOperation(AsmHomeBrewParser.IoOperationContext ctx) {
        byte instruction = (byte) (getOpcodeMask(ctx.ioOpcode()) | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitStoreOperation(AsmHomeBrewParser.StoreOperationContext ctx) {
        byte instruction = (byte) (instuctionMask.get("STORE") | getMidRegisterMask(ctx.register(0)) | getLastRegisterMask(ctx.register(1)));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }


    private byte getMidRegisterMask(AsmHomeBrewParser.RegisterContext ctx) {
        return registerMaskMid3.get(ctx.getText().toUpperCase());
    }

    private byte getLastRegisterMask(AsmHomeBrewParser.RegisterContext ctx) {
        return registerMaskLast3.get(ctx.getText().toUpperCase());
    }

    private byte getStackMask(AsmHomeBrewParser.StackRegisterContext ctx) {
        return stackMask.get(ctx.getText().toUpperCase());
    }

    private byte getALURegisterMask(AsmHomeBrewParser.AluDestinationRegisterContext ctx) {
        return aluRegisterDestinationMask.get(ctx.getText().toUpperCase());
    }

    private byte getOpcodeMask(ParserRuleContext ctx) {
        return instuctionMask.get(ctx.getText().toUpperCase());
    }


    public byte[] getMemory() {
        return memory;
    }
}
