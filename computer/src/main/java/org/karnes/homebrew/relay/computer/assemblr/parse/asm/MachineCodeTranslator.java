package org.karnes.homebrew.relay.computer.assemblr.parse.asm;

import org.karnes.homebrew.relay.computer.assemblr.parse.asm.antlr.AsmHomeBrewParser;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.computer.emulator.register.RegisterName;
import org.karnes.homebrew.relay.computer.emulator.register.StackRegisterName;
import org.karnes.homebrew.relay.computer.emulator.isa.*;

import java.util.Map;

/**
 * This class translates the assembly-language instructions into machine code.
 */
public class MachineCodeTranslator extends DirectiveExecutor {

    public MachineCodeTranslator(Map<String, FixedBitSet> symbolTable) {
        this.symbolTable = symbolTable;
    }


    //No arg Operations
    @Override
    public Void visitNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx) {
        //Get instruction
        String name = ctx.getText();
        Instruction instruction;
        switch (name) {
            case "NOP":
                instruction = new NOPInstruction();
                break;
            case "HALT":
                instruction = new HALTInstruction();
                break;
            case "TIN":
                instruction = new TINInstruction();
                break;
            case "TOUT":
                instruction = new TOUTInstruction();
                break;
            default:
                throw new IllegalStateException("Unexpected no arg instruction: " + name);
        }

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }

    //Unary Operations

    @Override
    public Void visitIoOperation(AsmHomeBrewParser.IoOperationContext ctx) {
        //Get register
        RegisterName register = getRegister(ctx.register());

        //Get instruction
        String name = ctx.ioOpcode().getText();
        Instruction instruction;
        switch (name) {
            case "WRDIN":
                instruction = new WRDINInstruction(register);
                break;
            case "WRDOUT":
                instruction = new WRDOUTInstruction(register);
                break;
            default:
                throw new IllegalStateException("Unexpected IO instruction: " + name);
        }

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }


    @Override
    public Void visitClearOperation(AsmHomeBrewParser.ClearOperationContext ctx) {
        //A CLR instruction is an alais for a MOV instruction
        RegisterName register = getRegister(ctx.register());
        MOVInstruction instruction = new MOVInstruction(register, register);

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }

    @Override
    public Void visitReturnOperation(AsmHomeBrewParser.ReturnOperationContext ctx) {
        StackRegisterName stackRegister = getStackRegister(ctx.stackRegister());
        RETInstruction instruction = new RETInstruction(stackRegister);

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }


    @Override
    public Void visitJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx) {
        //Get instruction
        JMPInstruction instruction = new JMPInstruction();

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //Get memory target
        FixedBitSet memoryTarget = getValue(ctx.value());

        //Store target into memory and increment code pointer
        storeValueInMem(memoryTarget);

        //No need to continue descent
        return null;
    }

    @Override
    public Void visitConditionalJumpOperation(AsmHomeBrewParser.ConditionalJumpOperationContext ctx) {
        String opcodeStr = ctx.jumpOpcode().getText();
        ConditionalJumpType jumpType = ConditionalJumpType.valueOf(opcodeStr);
        ConditionalJMPInstruction instruction = new ConditionalJMPInstruction(jumpType);

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //Get memory target
        FixedBitSet memoryTarget = getValue(ctx.value());

        //Store target into memory and increment code pointer
        storeValueInMem(memoryTarget);

        //No need to continue descent
        return null;
    }

    //Binary Operations

    @Override
    public Void visitBinaryRegRegOperation(AsmHomeBrewParser.BinaryRegRegOperationContext ctx) {
        //Get registers
        RegisterName dest = getRegister(ctx.register(0));
        RegisterName src = getRegister(ctx.register(1));

        //Get instruction
        String name = ctx.binaryRegRegOpCode().getText();
        Instruction instruction;
        switch (name) {
            case "MOV":
                instruction = new MOVInstruction(dest, src);
                break;
            case "STORE":
                instruction = new STOREInstruction(dest, src);
                break;
            case "FETCH":
                instruction = new FETCHInstruction(dest, src);
                break;
            case "INC":
                instruction = new INCInstruction(dest, src);
                break;
            case "DEC":
                instruction = new DECInstruction(dest, src);
                break;
            case "NOT":
                instruction = new NOTInstruction(dest, src);
                break;
            default:
                throw new IllegalStateException("Unexpected Register-Register instruction: " + name);
        }


        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }

    @Override
    public Void visitBinaryRegValOperation(AsmHomeBrewParser.BinaryRegValOperationContext ctx) {
        //LOAD is the only instruction in this category
        RegisterName register = getRegister(ctx.register());
        LOADInstruction instruction = new LOADInstruction(register);

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //Get memory target
        FixedBitSet memoryTarget = getValue(ctx.value());

        //Store target into memory and increment code pointer
        storeValueInMem(memoryTarget);

        //No need to continue descent
        return null;
    }

    @Override
    public Void visitPushOperation(AsmHomeBrewParser.PushOperationContext ctx) {
        StackRegisterName stackRegister = getStackRegister(ctx.stackRegister());
        RegisterName register = getRegister(ctx.register());
        PUSHInstruction instruction = new PUSHInstruction(stackRegister, register);

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }

    @Override
    public Void visitPopOperation(AsmHomeBrewParser.PopOperationContext ctx) {
        StackRegisterName stackRegister = getStackRegister(ctx.stackRegister());
        RegisterName register = getRegister(ctx.register());
        POPInstruction instruction = new POPInstruction(register, stackRegister);

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }


    @Override
    public Void visitCallOperation(AsmHomeBrewParser.CallOperationContext ctx) {
        StackRegisterName stackRegister = getStackRegister(ctx.stackRegister());
        CALLInstruction instruction = new CALLInstruction(stackRegister);

        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //Get memory target
        FixedBitSet memoryTarget = getValue(ctx.value());

        //Store target into memory and increment code pointer
        storeValueInMem(memoryTarget);

        //No need to continue descent
        return null;
    }

    //Ternary Operations


    @Override
    public Void visitAluTernaryOperation(AsmHomeBrewParser.AluTernaryOperationContext ctx) {
        //Get registers
        RegisterName dest = getRegister(ctx.register(0));
        RegisterName src2 = getRegister(ctx.register(1));
        RegisterName src1 = getRegister(ctx.register(2));

        //Get instruction
        String name = ctx.aluTernaryOpcode().getText();
        Instruction instruction;
        switch (name) {
            case "ADD":
                instruction = new ADDInstruction(dest, src2, src1);
                break;
            case "AND":
                instruction = new ANDInstruction(dest, src2, src1);
                break;
            case "OR":
                instruction = new ORInstruction(dest, src2, src1);
                break;
            case "XOR":
                instruction = new XORInstruction(dest, src2, src1);
                break;
            case "CMP": //CMP is an alias for SUB
            case "SUB":
                instruction = new SUBInstruction(dest, src2, src1);
                break;
            default:
                throw new IllegalStateException("Unexpected ALU ternary instruction: " + name);
        }


        //Store instruction into memory and increment code pointer
        storeValueInMem(instruction);

        //No need to continue descent
        return null;
    }

    private RegisterName getRegister(AsmHomeBrewParser.RegisterContext ctx) {
        String regStr = ctx.getText();
        RegisterName register = RegisterName.valueOf(regStr);
        return register;
    }

    private StackRegisterName getStackRegister(AsmHomeBrewParser.StackRegisterContext ctx) {
        String regStr = ctx.getText();
        StackRegisterName register = StackRegisterName.valueOf(regStr);
        return register;
    }

    private void storeValueInMem(Instruction instruction) {
        FixedBitSet binary = instruction.toBinary();
        storeValueInMem(binary);
    }

    public FixedBitSet[] getMemory() {
        return memory;
    }
}
