package org.karnes.homebrew.assemblr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.karnes.homebrew.assemblr.parse.AsmHomeBrewParser;
import org.karnes.homebrew.emulator.InstructionOpcode;
import org.karnes.homebrew.emulator.Register;

import java.util.Map;

/*
 * This class translates the assembly-language instructions into machine code.
 */
public class MachineCodeTranslator extends DirectiveExecutor {

    public MachineCodeTranslator(Map<String, Character> symbolTable) {
        this.symbolTable = symbolTable;
    }


    //No arg Operations
    @Override
    public Void visitNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx) {
        //Get instruction
        short instruction = getOpcodeMask(ctx);

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    //Unary Operations

    @Override
    public Void visitIoOperation(AsmHomeBrewParser.IoOperationContext ctx) {
        //Get instruction
        short instruction = (short) (getOpcodeMask(ctx.ioOpcode()) | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitClearOperation(AsmHomeBrewParser.ClearOperationContext ctx) {
        //Get instruction
        short instruction = (short) (getOpcodeMask("CLR") | getMidRegisterMask(ctx.register()) | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitReturnOperation(AsmHomeBrewParser.ReturnOperationContext ctx) {
        //Get instruction
        short instruction = (short) (getOpcodeMask("RET") | getStackMask(ctx.stackRegister()) | getLastRegisterMask("PC"));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx) {
        //Get instruction
        short instruction = getOpcodeMask(ctx.jumpOpcode());

        //Store instruction into memory
        memory[counter] = instruction;

        //Get memory target
        char memoryTarget = getValue(ctx.value());

        //Store memory target into memory
        memory[counter + 1] = (short) memoryTarget;

        //Increment our counter.
        counter += 2;
        //No need to continue descent
        return null;
    }

    //Binary Operations

    @Override
    public Void visitBinaryRegRegOperation(AsmHomeBrewParser.BinaryRegRegOperationContext ctx) {
        short instruction = (short) (getOpcodeMask(ctx.binaryRegRegOpCode()) | getMidRegisterMask(ctx.register(0)) | getLastRegisterMask(ctx.register(1)));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitBinaryRegValOperation(AsmHomeBrewParser.BinaryRegValOperationContext ctx) {
        //Get instruction
        short instruction = (short) (getOpcodeMask(ctx.binaryRegValOpCode()) | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;

        //Get memory target
        char memoryTarget = getValue(ctx.value());

        //Store memory target into memory
        memory[counter + 1] = (short) memoryTarget;

        //Increment our counter.
        counter += 2;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitPushOperation(AsmHomeBrewParser.PushOperationContext ctx) {
        short instruction = (short) (getOpcodeMask("PUSH") | getStackMask(ctx.stackRegister()) | getLastRegisterMask(ctx.register()));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    @Override
    public Void visitPopOperation(AsmHomeBrewParser.PopOperationContext ctx) {
        short instruction = (short) (getOpcodeMask("POP") | getStackMask(ctx.stackRegister()) | getLastRegisterMask(ctx.register()));

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
        short instruction = (short) (getOpcodeMask("CALL") | getStackMask(ctx.stackRegister()));

        //Store instruction into memory
        memory[counter] = instruction;

        //Get memory target
        char memoryTarget = getValue(ctx.value());

        //Store memory target into memory
        memory[counter + 1] = (short) memoryTarget;

        //Increment our counter.
        counter += 2;
        //No need to continue descent
        return null;
    }

    //Ternary Operations


    @Override
    public Void visitAluTernaryOperation(AsmHomeBrewParser.AluTernaryOperationContext ctx) {
        short instruction = (short) (getOpcodeMask(ctx.aluTernaryOpcode()) | getFirstRegisterMask(ctx.register(0)) | getMidRegisterMask(ctx.register(1)) | getLastRegisterMask(ctx.register(2)));

        //Store instruction into memory
        memory[counter] = instruction;
        //Increment our counter.
        counter++;
        //No need to continue descent
        return null;
    }

    private short getFirstRegisterMask(AsmHomeBrewParser.RegisterContext ctx) {
        String registerStr = ctx.getText().toUpperCase();
        return Register.valueOf(registerStr).getFirstPositionInstructionMask();
    }

    private short getMidRegisterMask(AsmHomeBrewParser.RegisterContext ctx) {
        String registerStr = ctx.getText().toUpperCase();
        return Register.valueOf(registerStr).getSecondPositionInstructionMask();
    }

    private short getLastRegisterMask(AsmHomeBrewParser.RegisterContext ctx) {
        String registerStr = ctx.getText().toUpperCase();
        return getLastRegisterMask(registerStr);
    }

    private short getLastRegisterMask(String registerStr) {
        return Register.valueOf(registerStr).getThirdPositionInstructionMask();
    }

    private short getStackMask(AsmHomeBrewParser.StackRegisterContext ctx) {
        String registerStr = ctx.getText().toUpperCase();
        return Register.valueOf(registerStr).getStackRegisterInstructionMask();
    }

    private short getOpcodeMask(ParserRuleContext ctx) {
        String opcodeName = ctx.getText().toUpperCase();
        return getOpcodeMask(opcodeName);
    }

    private short getOpcodeMask(String opcodeName) {
        return InstructionOpcode.valueOf(opcodeName).getInstructionOpcodeMask();
    }


    public short[] getMemory() {
        return memory;
    }
}
