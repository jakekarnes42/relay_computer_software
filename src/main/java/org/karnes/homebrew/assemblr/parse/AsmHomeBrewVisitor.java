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
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(AsmHomeBrewParser.ProgContext ctx);
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
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#binaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOperation(AsmHomeBrewParser.BinaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#aluOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAluOperation(AsmHomeBrewParser.AluOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#aluOpcode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAluOpcode(AsmHomeBrewParser.AluOpcodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#ioOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIoOperation(AsmHomeBrewParser.IoOperationContext ctx);
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
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#movOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMovOperation(AsmHomeBrewParser.MovOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#memoryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemoryOperation(AsmHomeBrewParser.MemoryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#loadOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadOperation(AsmHomeBrewParser.LoadOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#storeOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoreOperation(AsmHomeBrewParser.StoreOperationContext ctx);
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
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#aluDestinationRegister}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAluDestinationRegister(AsmHomeBrewParser.AluDestinationRegisterContext ctx);
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
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#assemblerByteDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssemblerByteDeclaration(AsmHomeBrewParser.AssemblerByteDeclarationContext ctx);
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
	/**
	 * Visit a parse tree produced by {@link AsmHomeBrewParser#opcode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpcode(AsmHomeBrewParser.OpcodeContext ctx);
}