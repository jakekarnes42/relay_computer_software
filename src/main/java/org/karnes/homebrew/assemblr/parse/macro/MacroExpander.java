package org.karnes.homebrew.assemblr.parse.macro;

import java.util.List;

/**
 * Expands macros in assembly code.
 */
public class MacroExpander {


    public String expandMacros(String completeSource) {

        MacroCollector macroCollector = new MacroCollector();

        //Parse the macro definitions
        macroCollector.findMacros(completeSource);
        List<ParsedMacro> parsedMacros = macroCollector.getMacroList();
        List<String> nonMacroLines = macroCollector.getNonMacroLines();

        String expandedText = expandTextWithMacros(nonMacroLines, parsedMacros);

        //TODO: should return expandedText
        return completeSource;
    }

    private String expandTextWithMacros(List<String> text, List<ParsedMacro> parsedMacros) {
        //TODO: Should actually expand macros
        return null;
    }


}
