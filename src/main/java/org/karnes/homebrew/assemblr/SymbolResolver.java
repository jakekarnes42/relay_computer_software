package org.karnes.homebrew.assemblr;

import org.karnes.homebrew.assemblr.parse.AsmHomeBrewBaseVisitor;
import org.karnes.homebrew.assemblr.parse.AsmHomeBrewParser;

import java.util.HashMap;
import java.util.Map;

/*
 * This class tries to resolve symbols. We don't need to actually store the instructions themselves, only placeholders.
 *
 * All one-byte instructions can have the same dummy value in memory. We really need to make sure the multi-byte instructions are added in.
 *
 * This is good enough for now while there aren't assembler directives.
 */
public class SymbolResolver extends DirectiveExecutor {

    @Override
    public Void visitInstruction(AsmHomeBrewParser.InstructionContext ctx) {
        //Check if this instruction is labelled
        if (ctx.lbl() != null) {
            //Store the label and the current counter to our symbol table.
            String labelText = ctx.lbl().label().getText();
            symbolTable.put(labelText, counter);
        }


        //Continue descent
        return super.visitInstruction(ctx);
    }

    @Override
    public Void visitLoadOperation(AsmHomeBrewParser.LoadOperationContext ctx) {
        //Memory operation takes 3 bytes
        counter += 3;

        //Don't continue descent.
        return null;
    }


    @Override
    public Void visitJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx) {
        //Jump Operation takes 3 bytes
        counter += 3;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitCallOperation(AsmHomeBrewParser.CallOperationContext ctx) {
        //Jump Operation takes 3 bytes
        counter += 3;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx) {
        //Takes one byte
        counter++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitAluOperation(AsmHomeBrewParser.AluOperationContext ctx) {
        //Takes one byte
        counter++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitIoOperation(AsmHomeBrewParser.IoOperationContext ctx) {
        //Takes one byte
        counter++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitMovOperation(AsmHomeBrewParser.MovOperationContext ctx) {
        //Takes one byte
        counter++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitStackOperation(AsmHomeBrewParser.StackOperationContext ctx) {
        //Takes one byte
        counter++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitStoreOperation(AsmHomeBrewParser.StoreOperationContext ctx) {
        //Takes one byte
        counter++;

        //Don't continue descent
        return null;
    }

    public Map<String, Character> getSymbolTable() {
        return symbolTable;
    }
}
