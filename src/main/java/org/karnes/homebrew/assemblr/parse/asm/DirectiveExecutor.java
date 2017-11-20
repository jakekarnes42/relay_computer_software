package org.karnes.homebrew.assemblr.parse.asm;

import org.karnes.homebrew.assemblr.parse.asm.antlr.AsmHomeBrewBaseVisitor;
import org.karnes.homebrew.assemblr.parse.asm.antlr.AsmHomeBrewParser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

import static org.karnes.homebrew.Util.literalToChar;

/**
 * Parent class which executes the assembler directives. Subclasses can re-use this functionality so they don't need to handle it themselves
 */
public class DirectiveExecutor extends AsmHomeBrewBaseVisitor<Void> {

    protected Map<String, Character> symbolTable = new HashMap<>();

    protected short[] memory = new short[Character.MAX_VALUE];

    //Woah! Let's use JavaScript. This is so terrible for so many reasons.
    //Yo dawg, I heard you like Javascript in your Java in your assembler in your assembly
    private ScriptEngineManager manager = new ScriptEngineManager();
    protected ScriptEngine engine = manager.getEngineByName("js");

    protected char lastJSResult = 0;

    protected char codePointer = 0;

    @Override
    public Void visitMacro(AsmHomeBrewParser.MacroContext ctx) {
        throw new IllegalStateException("Found un-expanded macro. This macro likely was not defined: " + ctx.macroName().getText());
    }

    @Override
    public Void visitAssemblerOrgDirective(AsmHomeBrewParser.AssemblerOrgDirectiveContext ctx) {
        //Move the codePointer to the specified location.
        try {
            //Evaluate JS
            visitJsExpression(ctx.jsExpression());

            //Update codePointer to whatever our result was
            codePointer = lastJSResult;
        } catch (Exception exc) {
            throw new IllegalStateException("Cannot handle ORG directive execution.", exc);
        }
        //Do not continue descent.
        return null;
    }


    @Override
    public Void visitAssemblerWordDeclaration(AsmHomeBrewParser.AssemblerWordDeclarationContext ctx) {
        ctx.value().stream() //Stream each word
                .map(this::getValue) //Map to value which we can actually store in mem
                .forEach(this::storeValueInMem); //Store each value into mem
        return null;
    }


    @Override
    public Void visitAssemblerStringDeclaration(AsmHomeBrewParser.AssemblerStringDeclarationContext ctx) {
        //Get the string
        String strValue = ctx.STRING().getText();

        //Substring to get rid of surrounding quotes
        strValue = strValue.substring(1, strValue.length() - 1);

        //Store each character in the string into memory
        strValue.chars().forEach(c -> storeValueInMem((char) c));

        return null;
    }

    @Override
    public Void visitJsExpression(AsmHomeBrewParser.JsExpressionContext ctx) {
        try {
            String jsExpr = ctx.getText();
            //Inject our code pointer into the $ variable within JS
            jsExpr = "$ = " + (int) codePointer + "; " + jsExpr;
            Object result = engine.eval(jsExpr);

            //Save our result
            if (result == null) {
                //Do nothing?
            } else if (result instanceof String) {
                String resultStr = (String) result;
                if (resultStr.length() == 1) {
                    lastJSResult = resultStr.charAt(0);
                } else {
                    lastJSResult = (char) (Integer.parseUnsignedInt(resultStr));
                }
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

    protected void storeValueInMem(char value) {
        //Cast to short and move on.
        storeValueInMem((short) value);
    }

    protected void storeValueInMem(short value) {
        //Store it
        memory[codePointer] = value;

        //Increment our codePointer.
        codePointer++;
    }


}
