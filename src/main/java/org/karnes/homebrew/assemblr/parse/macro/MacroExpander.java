package org.karnes.homebrew.assemblr.parse.macro;

import java.util.List;

/**
 * Expands macros in assembly code.
 */
public class MacroExpander {


    public String expandMacros(String text) {

        MacroCollector macroCollector = new MacroCollector();

        List<ParsedMacro> parsedMacros = macroCollector.findMacros(text);

        String expandedText = expandTextWithMacros(text, parsedMacros);

        return expandedText;
    }

    private String expandTextWithMacros(String text, List<ParsedMacro> parsedMacros) {
        //TODO: complete this.
        return text;
    }


}
