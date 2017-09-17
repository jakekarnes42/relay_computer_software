// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/macro/MacroGrammar.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse.macro;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MacroGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MacroGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MacroGrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine(MacroGrammarParser.LineContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#startMacro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStartMacro(MacroGrammarParser.StartMacroContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#endMacro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndMacro(MacroGrammarParser.EndMacroContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#other}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOther(MacroGrammarParser.OtherContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#macroName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroName(MacroGrammarParser.MacroNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MacroGrammarParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(MacroGrammarParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(MacroGrammarParser.CommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#macroStartTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroStartTag(MacroGrammarParser.MacroStartTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link MacroGrammarParser#macroEndTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroEndTag(MacroGrammarParser.MacroEndTagContext ctx);
}