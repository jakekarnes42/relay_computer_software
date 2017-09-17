package org.karnes.homebrew.assemblr.parse.macro;

import java.util.ArrayList;
import java.util.List;

public class ParsedMacro {
    private String name;
    private List<String> paramNames;
    private List<String> lines;

    public ParsedMacro(String name, List<String> paramNames) {
        this.name = name;
        this.paramNames = paramNames;
        lines = new ArrayList<>();
    }

    public void addLine(String lineText) {
        lines.add(lineText);
    }

    public String getName() {
        return name;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public List<String> getLines() {
        return lines;
    }
}
