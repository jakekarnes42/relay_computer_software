package org.karnes.homebrew.relay.computer.assemblr.parse.macro;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.karnes.homebrew.relay.computer.assemblr.DescriptiveErrorListener;
import org.karnes.homebrew.relay.computer.assemblr.parse.asm.antlr.AsmHomeBrewBaseVisitor;
import org.karnes.homebrew.relay.computer.assemblr.parse.asm.antlr.AsmHomeBrewLexer;
import org.karnes.homebrew.relay.computer.assemblr.parse.asm.antlr.AsmHomeBrewParser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Uses the grammar's lexing/parsing rules to find where macros are referenced.
 */
public class MacroReferenceFinder extends AsmHomeBrewBaseVisitor<ParsedMacro> {

    /**
     * Determines if the line is a macro reference. If so, the parsed macro information is returned. Otherwise, null is returned.
     *
     * @param line The line of source code which may or may not be a macro reference
     * @return The parsed macro information, or null if the line isn't a macro reference.
     */
    public ParsedMacro detectMacroReference(String line) {
        ParseTree parseTree = parse(line);

        ParsedMacro macro = visit(parseTree);

        return macro;
    }

    @Override
    public ParsedMacro visitMacro(AsmHomeBrewParser.MacroContext ctx) {
        String macroName = ctx.macroName().getText();
        List<String> params = ctx.macroParamValue().stream().map(v -> v.getText()).collect(Collectors.toList());
        ParsedMacro referencedMacro = new ParsedMacro(macroName, params);
        return referencedMacro;
    }

    private ParseTree parse(String line) {
        //Get the parse tree from the single line.
        CharStream input = CharStreams.fromString(line);
        DescriptiveErrorListener errorListener = new DescriptiveErrorListener();
        AsmHomeBrewLexer lexer = new AsmHomeBrewLexer(input);
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AsmHomeBrewParser parser = new AsmHomeBrewParser(tokens);
        parser.addErrorListener(errorListener);
        ParseTree parseTree = parser.program();

        //Error if invalid syntax found
        if (!errorListener.getErrors().isEmpty()) {
            throw new IllegalStateException("Found errors while parsing line:\" " + line + " Errors: " + errorListener.getErrors());
        }

        return parseTree;
    }

    @Override
    protected ParsedMacro aggregateResult(ParsedMacro aggregate, ParsedMacro nextResult) {
        if (aggregate != null) {
            return aggregate;
        }
        if (nextResult != null) {
            return nextResult;
        }
        return super.aggregateResult(aggregate, nextResult);
    }
}
