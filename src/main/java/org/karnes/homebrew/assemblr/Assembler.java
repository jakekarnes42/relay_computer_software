package org.karnes.homebrew.assemblr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.karnes.homebrew.assemblr.parse.asm.MachineCodeTranslator;
import org.karnes.homebrew.assemblr.parse.asm.SymbolResolver;
import org.karnes.homebrew.assemblr.parse.asm.antlr.AsmHomeBrewLexer;
import org.karnes.homebrew.assemblr.parse.asm.antlr.AsmHomeBrewParser;
import org.karnes.homebrew.assemblr.parse.macro.MacroExpander;

import java.util.Map;

/**
 * Assembles the source assembly code into a binary executable that can be loaded into the relay computer's ram.
 * <br>
 * The assembly process encompasses macro expansion, symbol resolution, and machine code translation.
 *
 */
public class Assembler {

    private String originalCode;
    private String expandedCodeWithMacros;
    private Map<String, Character> symbolTable;
    private short[] binaryOutput;

    public Assembler(String originalCode) {
        this.originalCode = originalCode;
    }

    public short[] assemble() {
        //Expand Macros
        expandedCodeWithMacros = expandMacros(originalCode);

        //Get the parse tree
        ParseTree parseTree = parse(expandedCodeWithMacros);

        //Symbol resolution
        symbolTable = resolveSymbols(parseTree);

        binaryOutput = convertToMachineCode(parseTree, symbolTable);
        return binaryOutput;
    }

    private String expandMacros(String text) {
        MacroExpander expander = new MacroExpander();
        String expandedText = expander.expandMacros(text);
        return expandedText;
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
            throw new IllegalStateException("Found errors while parsing text. Errors: " + errorListener.getErrors());
        }

        return parseTree;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public String getExpandedCodeWithMacros() {
        return expandedCodeWithMacros;
    }

    public Map<String, Character> getSymbolTable() {
        return symbolTable;
    }

    public short[] getBinaryOutput() {
        return binaryOutput;
    }
}
