package org.karnes.homebrew.assemblr.parse.macro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacroDefinitionCollector {

    Pattern macroRegex = Pattern.compile("^\\s*MACRO\\s+" + //Macro keyword
            "(\\$\\w+)\\s+" + //Macro name Group 1
            "(@\\w+)?" + //Optional Param 1 Group 2
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 4
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 6
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 8
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 10
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 12
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 14
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 16
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 18
            "(\\s*,\\s*(@\\w+))?" + //Optional Param 2 Group 20
            "(\\s*;.+)?"); //Optional comment, last group

    Pattern endmRegex = Pattern.compile("\\s*ENDM\\s*(;.+)?");

    /**
     * The macros parsed from the source code.
     */
    private List<ParsedMacro> macroList = new ArrayList<>();

    /**
     * The lines in the source code outside of macros.
     */
    private List<String> nonMacroLines = new ArrayList<>();

    /**
     * The current macro being parsed. Null if not currently in a macro.
     */
    private ParsedMacro currentMacro = null;


    public void findMacros(String text) {

        try (Scanner codeScanner = new Scanner(text)) {
            while (codeScanner.hasNextLine()) {
                String line = codeScanner.nextLine();

                //Check if it's a new macro definition
                Matcher macroRegexMatcher = macroRegex.matcher(line);
                if (macroRegexMatcher.matches()) {
                    parseNewMacroDefinition(macroRegexMatcher);
                    continue;
                }

                //Check if it's a ending a macro
                Matcher endmRegexMatcher = endmRegex.matcher(line);
                if (endmRegexMatcher.matches()) {
                    parseENDM(endmRegexMatcher);
                    continue;
                }

                handleOtherLine(line);

            }
        }


        //Check that we didn't end inside a macro
        if (currentMacro != null) {
            throw new IllegalStateException("Finished parsing while still in a macro.");
        }

    }

    private void parseNewMacroDefinition(Matcher macroRegexMatcher) {
        //Check that we aren't already in a macro
        if (currentMacro != null) {
            throw new IllegalStateException("Nested macro defintions are not allowed");
        }

        String macroName = macroRegexMatcher.group(1);

        //Set up list for our parameters
        List<String> paramNames = new ArrayList<>();

        //Check if there are any parameters
        int groupCount = macroRegexMatcher.groupCount();
        if (groupCount > 1) {
            //Yes, there are parameters. Get the first
            String param1 = macroRegexMatcher.group(2);
            paramNames.add(param1);

            //If there are any other params, we'll add them with this loop.
            for (int i = 4; i <= groupCount; i += 2) {
                String param = macroRegexMatcher.group(i);
                if (param != null) {
                    paramNames.add(param);
                }
            }
        }

        //Create the new macro and track it.
        currentMacro = new ParsedMacro(macroName, paramNames);

    }

    private void parseENDM(Matcher endmRegexMatcher) {
        //Check that we didn't end outside a macro
        if (currentMacro == null) {
            throw new IllegalStateException("Found ENDM keyword outside of macro. Unable to complete a macro that hasn't started..");
        }

        //Our macro is complete. Add it to the list and clear the tracking
        macroList.add(currentMacro);
        currentMacro = null;
    }


    private void handleOtherLine(String line) {
        String lineWithEOL = line + "\r\n";
        if (currentMacro == null) {
            nonMacroLines.add(lineWithEOL);
        } else {
            currentMacro.addLine(lineWithEOL);
        }
    }

    /**
     * The macros parsed from the input source code.
     * This is only considered valid after {@link #findMacros(String)} has been called.
     *
     * @return the parsed macros.
     */
    public List<ParsedMacro> getMacroList() {
        return macroList;
    }


    /**
     * The source code lines outside of the macros.
     * This is only considered valid after {@link #findMacros(String)} has been called.
     *
     * @return the rest of the source code, excluding macro definitions
     */
    public List<String> getNonMacroLines() {
        return nonMacroLines;
    }
}
