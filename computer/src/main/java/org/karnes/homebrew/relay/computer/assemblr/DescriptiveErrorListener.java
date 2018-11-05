package org.karnes.homebrew.relay.computer.assemblr;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

public class DescriptiveErrorListener extends BaseErrorListener {
    private List<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {


        String sourceName = recognizer.getInputStream().getSourceName();
        if (!sourceName.isEmpty()) {
            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
        }
        String errorMessage = sourceName + "line " + line + ":" + charPositionInLine + " " + msg;
        System.err.println(errorMessage);
        errors.add(errorMessage);
    }

    public List<String> getErrors() {
        return errors;
    }
}
