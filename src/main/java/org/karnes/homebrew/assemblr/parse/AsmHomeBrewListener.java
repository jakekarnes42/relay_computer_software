// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AsmHomeBrewParser}.
 */
public interface AsmHomeBrewListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(AsmHomeBrewParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(AsmHomeBrewParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(AsmHomeBrewParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(AsmHomeBrewParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(AsmHomeBrewParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(AsmHomeBrewParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#lbl}.
	 * @param ctx the parse tree
	 */
	void enterLbl(AsmHomeBrewParser.LblContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#lbl}.
	 * @param ctx the parse tree
	 */
	void exitLbl(AsmHomeBrewParser.LblContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(AsmHomeBrewParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(AsmHomeBrewParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#noArgOperation}.
	 * @param ctx the parse tree
	 */
	void enterNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#noArgOperation}.
	 * @param ctx the parse tree
	 */
	void exitNoArgOperation(AsmHomeBrewParser.NoArgOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#unaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperation(AsmHomeBrewParser.UnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#unaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperation(AsmHomeBrewParser.UnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#binaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOperation(AsmHomeBrewParser.BinaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#binaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOperation(AsmHomeBrewParser.BinaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#aluOperation}.
	 * @param ctx the parse tree
	 */
	void enterAluOperation(AsmHomeBrewParser.AluOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#aluOperation}.
	 * @param ctx the parse tree
	 */
	void exitAluOperation(AsmHomeBrewParser.AluOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#aluOpcode}.
	 * @param ctx the parse tree
	 */
	void enterAluOpcode(AsmHomeBrewParser.AluOpcodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#aluOpcode}.
	 * @param ctx the parse tree
	 */
	void exitAluOpcode(AsmHomeBrewParser.AluOpcodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#ioOperation}.
	 * @param ctx the parse tree
	 */
	void enterIoOperation(AsmHomeBrewParser.IoOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#ioOperation}.
	 * @param ctx the parse tree
	 */
	void exitIoOperation(AsmHomeBrewParser.IoOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#ioOpcode}.
	 * @param ctx the parse tree
	 */
	void enterIoOpcode(AsmHomeBrewParser.IoOpcodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#ioOpcode}.
	 * @param ctx the parse tree
	 */
	void exitIoOpcode(AsmHomeBrewParser.IoOpcodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#jumpOperation}.
	 * @param ctx the parse tree
	 */
	void enterJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#jumpOperation}.
	 * @param ctx the parse tree
	 */
	void exitJumpOperation(AsmHomeBrewParser.JumpOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#jumpOpcode}.
	 * @param ctx the parse tree
	 */
	void enterJumpOpcode(AsmHomeBrewParser.JumpOpcodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#jumpOpcode}.
	 * @param ctx the parse tree
	 */
	void exitJumpOpcode(AsmHomeBrewParser.JumpOpcodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#movOperation}.
	 * @param ctx the parse tree
	 */
	void enterMovOperation(AsmHomeBrewParser.MovOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#movOperation}.
	 * @param ctx the parse tree
	 */
	void exitMovOperation(AsmHomeBrewParser.MovOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#memoryOperation}.
	 * @param ctx the parse tree
	 */
	void enterMemoryOperation(AsmHomeBrewParser.MemoryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#memoryOperation}.
	 * @param ctx the parse tree
	 */
	void exitMemoryOperation(AsmHomeBrewParser.MemoryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#loadOperation}.
	 * @param ctx the parse tree
	 */
	void enterLoadOperation(AsmHomeBrewParser.LoadOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#loadOperation}.
	 * @param ctx the parse tree
	 */
	void exitLoadOperation(AsmHomeBrewParser.LoadOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#storeOperation}.
	 * @param ctx the parse tree
	 */
	void enterStoreOperation(AsmHomeBrewParser.StoreOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#storeOperation}.
	 * @param ctx the parse tree
	 */
	void exitStoreOperation(AsmHomeBrewParser.StoreOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#stackOperation}.
	 * @param ctx the parse tree
	 */
	void enterStackOperation(AsmHomeBrewParser.StackOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#stackOperation}.
	 * @param ctx the parse tree
	 */
	void exitStackOperation(AsmHomeBrewParser.StackOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#pushOperation}.
	 * @param ctx the parse tree
	 */
	void enterPushOperation(AsmHomeBrewParser.PushOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#pushOperation}.
	 * @param ctx the parse tree
	 */
	void exitPushOperation(AsmHomeBrewParser.PushOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#popOperation}.
	 * @param ctx the parse tree
	 */
	void enterPopOperation(AsmHomeBrewParser.PopOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#popOperation}.
	 * @param ctx the parse tree
	 */
	void exitPopOperation(AsmHomeBrewParser.PopOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#callOperation}.
	 * @param ctx the parse tree
	 */
	void enterCallOperation(AsmHomeBrewParser.CallOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#callOperation}.
	 * @param ctx the parse tree
	 */
	void exitCallOperation(AsmHomeBrewParser.CallOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(AsmHomeBrewParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(AsmHomeBrewParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(AsmHomeBrewParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(AsmHomeBrewParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#register}.
	 * @param ctx the parse tree
	 */
	void enterRegister(AsmHomeBrewParser.RegisterContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#register}.
	 * @param ctx the parse tree
	 */
	void exitRegister(AsmHomeBrewParser.RegisterContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#aluDestinationRegister}.
	 * @param ctx the parse tree
	 */
	void enterAluDestinationRegister(AsmHomeBrewParser.AluDestinationRegisterContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#aluDestinationRegister}.
	 * @param ctx the parse tree
	 */
	void exitAluDestinationRegister(AsmHomeBrewParser.AluDestinationRegisterContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#stackRegister}.
	 * @param ctx the parse tree
	 */
	void enterStackRegister(AsmHomeBrewParser.StackRegisterContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#stackRegister}.
	 * @param ctx the parse tree
	 */
	void exitStackRegister(AsmHomeBrewParser.StackRegisterContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#assemblerDirective}.
	 * @param ctx the parse tree
	 */
	void enterAssemblerDirective(AsmHomeBrewParser.AssemblerDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#assemblerDirective}.
	 * @param ctx the parse tree
	 */
	void exitAssemblerDirective(AsmHomeBrewParser.AssemblerDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#assemblerOrgDirective}.
	 * @param ctx the parse tree
	 */
	void enterAssemblerOrgDirective(AsmHomeBrewParser.AssemblerOrgDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#assemblerOrgDirective}.
	 * @param ctx the parse tree
	 */
	void exitAssemblerOrgDirective(AsmHomeBrewParser.AssemblerOrgDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#assemblerByteDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAssemblerByteDeclaration(AsmHomeBrewParser.AssemblerByteDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#assemblerByteDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAssemblerByteDeclaration(AsmHomeBrewParser.AssemblerByteDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#assemblerWordDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAssemblerWordDeclaration(AsmHomeBrewParser.AssemblerWordDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#assemblerWordDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAssemblerWordDeclaration(AsmHomeBrewParser.AssemblerWordDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#jsExpression}.
	 * @param ctx the parse tree
	 */
	void enterJsExpression(AsmHomeBrewParser.JsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#jsExpression}.
	 * @param ctx the parse tree
	 */
	void exitJsExpression(AsmHomeBrewParser.JsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(AsmHomeBrewParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(AsmHomeBrewParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(AsmHomeBrewParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(AsmHomeBrewParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(AsmHomeBrewParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(AsmHomeBrewParser.CommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#opcode}.
	 * @param ctx the parse tree
	 */
	void enterOpcode(AsmHomeBrewParser.OpcodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#opcode}.
	 * @param ctx the parse tree
	 */
	void exitOpcode(AsmHomeBrewParser.OpcodeContext ctx);
}