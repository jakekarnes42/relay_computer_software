// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/macro/MacroGrammar.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse.macro;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MacroGrammarParser}.
 */
public interface MacroGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MacroGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MacroGrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(MacroGrammarParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(MacroGrammarParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#startMacro}.
	 * @param ctx the parse tree
	 */
	void enterStartMacro(MacroGrammarParser.StartMacroContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#startMacro}.
	 * @param ctx the parse tree
	 */
	void exitStartMacro(MacroGrammarParser.StartMacroContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#endMacro}.
	 * @param ctx the parse tree
	 */
	void enterEndMacro(MacroGrammarParser.EndMacroContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#endMacro}.
	 * @param ctx the parse tree
	 */
	void exitEndMacro(MacroGrammarParser.EndMacroContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#other}.
	 * @param ctx the parse tree
	 */
	void enterOther(MacroGrammarParser.OtherContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#other}.
	 * @param ctx the parse tree
	 */
	void exitOther(MacroGrammarParser.OtherContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#macroName}.
	 * @param ctx the parse tree
	 */
	void enterMacroName(MacroGrammarParser.MacroNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#macroName}.
	 * @param ctx the parse tree
	 */
	void exitMacroName(MacroGrammarParser.MacroNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(MacroGrammarParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(MacroGrammarParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(MacroGrammarParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(MacroGrammarParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(MacroGrammarParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(MacroGrammarParser.CommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#macroStartTag}.
	 * @param ctx the parse tree
	 */
	void enterMacroStartTag(MacroGrammarParser.MacroStartTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#macroStartTag}.
	 * @param ctx the parse tree
	 */
	void exitMacroStartTag(MacroGrammarParser.MacroStartTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link MacroGrammarParser#macroEndTag}.
	 * @param ctx the parse tree
	 */
	void enterMacroEndTag(MacroGrammarParser.MacroEndTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link MacroGrammarParser#macroEndTag}.
	 * @param ctx the parse tree
	 */
	void exitMacroEndTag(MacroGrammarParser.MacroEndTagContext ctx);
}