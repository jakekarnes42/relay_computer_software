package org.karnes.homebrew.assemblr.parse.asm;

import org.karnes.homebrew.assemblr.parse.asm.antlr.AsmHomeBrewParser;

import java.util.Map;

/*
 * This class tries to resolve symbols. We don't need to actually store the instructions themselves, only placeholders.
 *
 * All one-word instructions can have the same dummy value in memory. We really need to make sure the multi-word instructions are added in.
 *
 */
public class SymbolResolver extends DirectiveExecutor {

    @Override
    public Void visitInstruction(AsmHomeBrewParser.InstructionContext ctx) {
        //Check if this instruction is labelled
        if (ctx.lbl() != null) {
            //Store the label and the current codePointer to our symbol table.
            String labelText = ctx.lbl().label().getText();
            symbolTable.put(labelText, codePointer);
        }

        //Continue descent
        return super.visitInstruction(ctx);
    }

    @Override
    public Void visitNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitIoOperation(AsmHomeBrewParser.IoOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitClearOperation(AsmHomeBrewParser.ClearOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx) {
        //Jump Operation takes 2 words
        codePointer += 2;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitBinaryRegRegOperation(AsmHomeBrewParser.BinaryRegRegOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitBinaryRegValOperation(AsmHomeBrewParser.BinaryRegValOperationContext ctx) {
        //Jump Operation takes 2 words
        codePointer += 2;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitPushOperation(AsmHomeBrewParser.PushOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitPopOperation(AsmHomeBrewParser.PopOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitReturnOperation(AsmHomeBrewParser.ReturnOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitCallOperation(AsmHomeBrewParser.CallOperationContext ctx) {
        //CALL Operation takes 2 words
        codePointer += 2;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitTernaryOperation(AsmHomeBrewParser.TernaryOperationContext ctx) {
        //Takes one word
        codePointer++;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitAssemblerWordDeclaration(AsmHomeBrewParser.AssemblerWordDeclarationContext ctx) {
        //Calculate how many words are declared.
        int numWords = ctx.value().size();
        codePointer += numWords;

        //Don't continue descent
        return null;
    }

    @Override
    public Void visitAssemblerStringDeclaration(AsmHomeBrewParser.AssemblerStringDeclarationContext ctx) {
        //Calculate how many letters are declared.
        int numLetters = ctx.STRING().getText().length();
        codePointer += numLetters;

        //Don't continue descent
        return null;
    }

    public Map<String, Character> getSymbolTable() {
        return symbolTable;
    }
}
