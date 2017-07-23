package org.karnes.homebrew.assemblr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.karnes.homebrew.assemblr.parse.AsmHomeBrewLexer;
import org.karnes.homebrew.assemblr.parse.AsmHomeBrewParser;

import java.util.Map;

public class Assembler {


    public short[] assemble(String text) {
        //Expand Macros
        //text = expandMacros(text);

        //Get the parse tree
        ParseTree parseTree = parse(text);

        //Symbol resolution
        Map<String, Character> symbolTable = resolveSymbols(parseTree);


        return convertToMachineCode(parseTree, symbolTable);
    }

    private Map<String, Character> resolveSymbols(ParseTree parseTree) {
        //Resolve symbols
        SymbolResolver translator = new SymbolResolver();
        translator.visit(parseTree);

        return translator.getSymbolTable();
    }

    private short[] convertToMachineCode(ParseTree parseTree, Map<String, Character> symbolTable) {
        //Convert to machine code
        MachineCodeTranslator translator = new MachineCodeTranslator(symbolTable);
        translator.visit(parseTree);

        return translator.getMemory();
    }

    private ParseTree parse(String text) {
        //Get the parse tree from the text.
        CharStream input = CharStreams.fromString(text);
        DescriptiveErrorListener errorListener = new DescriptiveErrorListener();
        AsmHomeBrewLexer lexer = new AsmHomeBrewLexer(input);
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AsmHomeBrewParser parser = new AsmHomeBrewParser(tokens);
        parser.addErrorListener(errorListener);
        ParseTree parseTree = parser.program();

        //Error if invalid syntax found
        if (!errorListener.getErrors().isEmpty()) {
            System.exit(1);
        }

        return parseTree;
    }


}
