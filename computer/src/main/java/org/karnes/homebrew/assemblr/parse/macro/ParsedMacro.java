package org.karnes.homebrew.assemblr.parse.macro;

import java.util.ArrayList;
import java.util.List;

/**
 * Data holder for a macro that has been analyzed into its components. This may hold data for a macro definition or a reference.
 */
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

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "ParsedMacro{" +
                "name='" + name + '\'' +
                ", paramNames=" + paramNames +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsedMacro that = (ParsedMacro) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (paramNames != null ? !paramNames.equals(that.paramNames) : that.paramNames != null) return false;
        return lines != null ? lines.equals(that.lines) : that.lines == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (paramNames != null ? paramNames.hashCode() : 0);
        result = 31 * result + (lines != null ? lines.hashCode() : 0);
        return result;
    }
}
