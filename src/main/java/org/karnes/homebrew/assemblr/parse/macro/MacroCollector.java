package org.karnes.homebrew.assemblr.parse.macro;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.karnes.homebrew.assemblr.DescriptiveErrorListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MacroCollector extends MacroGrammarBaseVisitor {

    List<ParsedMacro> macroList = new ArrayList<>();

    /**
     * The current macro being parsed. Null if not currently in a macro
     */
    ParsedMacro currentMacro = null;


    public List<ParsedMacro> findMacros(String text) {
        ParseTree parseTree = parse(text);

        //Populates macroList during descent
        visit(parseTree);

        return macroList;
    }


    @Override
    public Object visitStartMacro(MacroGrammarParser.StartMacroContext ctx) {
        //Get properties from this macro
        String macroName = ctx.macroName().getText();
        List<String> paramNames = ctx.param().stream().map(p -> p.getText()).collect(Collectors.toList());

        //Capture the macro so we can add lines to it.
        currentMacro = new ParsedMacro(macroName, paramNames);

        return null;
    }

    @Override
    public Object visitEndMacro(MacroGrammarParser.EndMacroContext ctx) {
        if (currentMacro == null) {
            throw new IllegalStateException("Found ENDM outside of a macro.");
        }

        //Add the macro to our running list.
        macroList.add(currentMacro);

        //Clear the current placeholder for the next macro to come
        currentMacro = null;

        return null;
    }

    @Override
    public Object visitOther(MacroGrammarParser.OtherContext ctx) {
        if (currentMacro == null) {
            //Currently outside a macro, don't care
            return null;
        }

        //Get this line and add it to our current macro
        String lineText = ctx.getText();
        currentMacro.addLine(lineText);

        //No need to continue further.
        return null;
    }

    private ParseTree parse(String text) {
        //Get the parse tree from the text.
        CharStream input = CharStreams.fromString(text);
        DescriptiveErrorListener errorListener = new DescriptiveErrorListener();
        MacroGrammarLexer lexer = new MacroGrammarLexer(input);
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MacroGrammarParser parser = new MacroGrammarParser(tokens);
        parser.addErrorListener(errorListener);
        ParseTree parseTree = parser.program();

        //Error if invalid syntax found
        if (!errorListener.getErrors().isEmpty()) {
            throw new IllegalStateException("Found errors while parsing macro text. Errors: " + errorListener.getErrors());
        }

        return parseTree;
    }
}
