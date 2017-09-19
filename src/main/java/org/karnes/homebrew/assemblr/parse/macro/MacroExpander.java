package org.karnes.homebrew.assemblr.parse.macro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expands macros in assembly code.
 */
public class MacroExpander {


    public String expandMacros(String completeSource) {

        MacroDefinitionFinder macroDefinitionFinder = new MacroDefinitionFinder();

        //Parse the macro definitions
        macroDefinitionFinder.findMacros(completeSource);

        List<ParsedMacro> parsedMacros = macroDefinitionFinder.getMacroList();
        List<String> nonMacroLines = macroDefinitionFinder.getNonMacroLines();

        List<String> expandedTextLines = expandTextWithMacros(nonMacroLines, parsedMacros);

        String expandedText = expandedTextLines.stream().collect(Collectors.joining());

        return expandedText;
    }

    private List<String> expandTextWithMacros(List<String> text, List<ParsedMacro> parsedMacros) {

        //Flip the ordering so that expansion works correctly with nested macros.
        Collections.reverse(parsedMacros);

        for (ParsedMacro macro : parsedMacros) {
            //Expand text
            List<String> newText = expandTextLines(text, macro);
            //Replace with expanded text.
            text = newText;

        }
        return text;
    }

    private List<String> expandTextLines(List<String> text, ParsedMacro macro) {
        //Expand text
        List<String> newText = new ArrayList<>(text.size());
        for (String line : text) {
            List<String> expandMacroLines = expandMacroLine(macro, line);
            newText.addAll(expandMacroLines);
        }
        return newText;
    }

    private List<String> expandMacroLine(ParsedMacro macro, String line) {
        String macroName = macro.getName();

        //The expanded version of the line.
        List<String> newText = new ArrayList<>();


        ParsedMacro macroReference = new MacroReferenceFinder().detectMacroReference(line);
        if (macroReference != null && macroReference.getName().equals(macroName)) {
            //Found a reference to this macro!

            //Check the parameter lengths
            List<String> macroParamNames = macro.getParamNames();
            List<String> referenceParamNames = macroReference.getParamNames();
            if (macroParamNames.size() != referenceParamNames.size()) {
                throw new IllegalStateException("Found a macro reference to macro " + macroName + " with "
                        + referenceParamNames.size() + " parameters. Expected "
                        + macroParamNames.size() + " for that macro.");
            }

            //Expand this macro with parameter replacement.
            for (String macroBodyLine : macro.getLines()) {

                //Parameter replacement
                for (int i = 0; i < macroParamNames.size(); i++) {
                    String macroParam = macroParamNames.get(i);
                    String referenceParam = referenceParamNames.get(i);
                    macroBodyLine = macroBodyLine.replaceAll(macroParam, referenceParam);
                }

                //Add this to our text
                newText.add(macroBodyLine);
            }

        } else {
            //Not a reference to our macro, don't change it.
            newText.add(line);
        }

        return newText;
    }


}
