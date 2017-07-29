// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AsmHomeBrewParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AsmHomeBrewVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(AsmHomeBrewParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine(AsmHomeBrewParser.LineContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(AsmHomeBrewParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#lbl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLbl(AsmHomeBrewParser.LblContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperation(AsmHomeBrewParser.OperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#noArgOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#unaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperation(AsmHomeBrewParser.UnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#ioOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIoOperation(AsmHomeBrewParser.IoOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#returnOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnOperation(AsmHomeBrewParser.ReturnOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#clearOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClearOperation(AsmHomeBrewParser.ClearOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#ioOpcode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIoOpcode(AsmHomeBrewParser.IoOpcodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#jumpOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#jumpOpcode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpOpcode(AsmHomeBrewParser.JumpOpcodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#binaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOperation(AsmHomeBrewParser.BinaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#binaryRegRegOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryRegRegOperation(AsmHomeBrewParser.BinaryRegRegOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#binaryRegRegOpCode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryRegRegOpCode(AsmHomeBrewParser.BinaryRegRegOpCodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#binaryRegValOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryRegValOperation(AsmHomeBrewParser.BinaryRegValOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#binaryRegValOpCode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryRegValOpCode(AsmHomeBrewParser.BinaryRegValOpCodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#stackOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStackOperation(AsmHomeBrewParser.StackOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#pushOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPushOperation(AsmHomeBrewParser.PushOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#popOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPopOperation(AsmHomeBrewParser.PopOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#callOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallOperation(AsmHomeBrewParser.CallOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#ternaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryOperation(AsmHomeBrewParser.TernaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#aluTernaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAluTernaryOperation(AsmHomeBrewParser.AluTernaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#aluTernaryOpcode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAluTernaryOpcode(AsmHomeBrewParser.AluTernaryOpcodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(AsmHomeBrewParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(AsmHomeBrewParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#register}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegister(AsmHomeBrewParser.RegisterContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#stackRegister}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStackRegister(AsmHomeBrewParser.StackRegisterContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#assemblerDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssemblerDirective(AsmHomeBrewParser.AssemblerDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#assemblerOrgDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssemblerOrgDirective(AsmHomeBrewParser.AssemblerOrgDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#assemblerWordDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssemblerWordDeclaration(AsmHomeBrewParser.AssemblerWordDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#jsExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsExpression(AsmHomeBrewParser.JsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(AsmHomeBrewParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(AsmHomeBrewParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(AsmHomeBrewParser.CommentContext ctx);
}