package org.karnes.homebrew.assemblr;

import org.karnes.homebrew.assemblr.parse.AsmHomeBrewBaseVisitor;
import org.karnes.homebrew.assemblr.parse.AsmHomeBrewParser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

import static org.karnes.homebrew.Util.literalToChar;


public class DirectiveExecutor extends AsmHomeBrewBaseVisitor<Void> {

    protected Map<String, Character> symbolTable = new HashMap<>();

    protected short[] memory = new short[Character.MAX_VALUE];

    //Woah! Let's use JavaScript. This is so terrible for so many reasons.
    //Yo dawg, I heard you like Javascript in your Java in your assembler in your assembly
    private ScriptEngineManager manager = new ScriptEngineManager();
    protected ScriptEngine engine = manager.getEngineByName("js");

    protected char lastJSResult = 0;

    protected char counter = 0;

    @Override
    public Void visitAssemblerOrgDirective(AsmHomeBrewParser.AssemblerOrgDirectiveContext ctx) {
        //Move the counter to the specified location.

        try {
            //Evaluate JS
            visitJsExpression(ctx.jsExpression());

            //Update counter to whatever our result was
            counter = lastJSResult;
        } catch (Exception exc) {
            throw new IllegalStateException("Cannot handle ORG directive execution.", exc);
        }
        //Do not continue descent.
        return null;
    }


    @Override
    public Void visitAssemblerWordDeclaration(AsmHomeBrewParser.AssemblerWordDeclarationContext ctx) {
        for (AsmHomeBrewParser.ValueContext valueContext : ctx.value()) {
            //Get the value
            char value = getValue(valueContext);
            //Store it
            memory[counter] = (short) value;

            //Increment our counter.
            counter++;
        }
        return null;
    }

    @Override
    public Void visitJsExpression(AsmHomeBrewParser.JsExpressionContext ctx) {
        try {
            String jsExpr = ctx.getText();
            //Inject our code pointer into the $ variable within JS
            jsExpr = "$ = " + (int) counter + "; " + jsExpr;
            Object result = engine.eval(jsExpr);
            //Save our result
            if (result == null) {
                //Do nothing?
            } else if (result instanceof String) {
                lastJSResult = (char) (Integer.parseUnsignedInt(result.toString()));
            } else if (result instanceof Double) {
                lastJSResult = (char) ((Double) result).intValue();
            } else if (result instanceof Integer) {
                lastJSResult = (char) ((Integer) result).intValue();
            } else {
                throw new IllegalArgumentException("Cannot process JS result type: " + result);
            }
        } catch (Exception exc) {
            throw new IllegalStateException("Cannot handle javascript directive execution.", exc);
        }

        return null;
    }

    protected char getValue(AsmHomeBrewParser.ValueContext valueContext) {
        //Check if our memory target is a label
        if (valueContext.label() != null) {
            String targetLabel = valueContext.label().getText();
            //Make sure we have a value for this label
            if (!symbolTable.containsKey(targetLabel)) {
                throw new IllegalStateException("Cannot resolve target for target label: " + targetLabel);
            }
            return symbolTable.get(targetLabel);
        } else if (valueContext.number() != null) {
            String targetLiteralText = valueContext.number().getText();
            return literalToChar(targetLiteralText);
        } else {
            visitJsExpression(valueContext.jsExpression());
            return lastJSResult;
        }
    }
}
