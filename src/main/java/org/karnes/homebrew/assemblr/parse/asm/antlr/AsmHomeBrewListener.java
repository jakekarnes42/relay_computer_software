// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/asm/antlr/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse.asm.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AsmHomeBrewParser}.
 */
public interface AsmHomeBrewListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(AsmHomeBrewParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(AsmHomeBrewParser.ProgramContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#labelDefinition}.
	 * @param ctx the parse tree
	 */
	void enterLabelDefinition(AsmHomeBrewParser.LabelDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#labelDefinition}.
	 * @param ctx the parse tree
	 */
	void exitLabelDefinition(AsmHomeBrewParser.LabelDefinitionContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#returnOperation}.
	 * @param ctx the parse tree
	 */
	void enterReturnOperation(AsmHomeBrewParser.ReturnOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#returnOperation}.
	 * @param ctx the parse tree
	 */
	void exitReturnOperation(AsmHomeBrewParser.ReturnOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#clearOperation}.
	 * @param ctx the parse tree
	 */
	void enterClearOperation(AsmHomeBrewParser.ClearOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#clearOperation}.
	 * @param ctx the parse tree
	 */
	void exitClearOperation(AsmHomeBrewParser.ClearOperationContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#conditionalJumpOperation}.
	 * @param ctx the parse tree
	 */
	void enterConditionalJumpOperation(AsmHomeBrewParser.ConditionalJumpOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#conditionalJumpOperation}.
	 * @param ctx the parse tree
	 */
	void exitConditionalJumpOperation(AsmHomeBrewParser.ConditionalJumpOperationContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#binaryRegRegOperation}.
	 * @param ctx the parse tree
	 */
	void enterBinaryRegRegOperation(AsmHomeBrewParser.BinaryRegRegOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#binaryRegRegOperation}.
	 * @param ctx the parse tree
	 */
	void exitBinaryRegRegOperation(AsmHomeBrewParser.BinaryRegRegOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#binaryRegRegOpCode}.
	 * @param ctx the parse tree
	 */
	void enterBinaryRegRegOpCode(AsmHomeBrewParser.BinaryRegRegOpCodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#binaryRegRegOpCode}.
	 * @param ctx the parse tree
	 */
	void exitBinaryRegRegOpCode(AsmHomeBrewParser.BinaryRegRegOpCodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#binaryRegValOperation}.
	 * @param ctx the parse tree
	 */
	void enterBinaryRegValOperation(AsmHomeBrewParser.BinaryRegValOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#binaryRegValOperation}.
	 * @param ctx the parse tree
	 */
	void exitBinaryRegValOperation(AsmHomeBrewParser.BinaryRegValOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#binaryRegValOpCode}.
	 * @param ctx the parse tree
	 */
	void enterBinaryRegValOpCode(AsmHomeBrewParser.BinaryRegValOpCodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#binaryRegValOpCode}.
	 * @param ctx the parse tree
	 */
	void exitBinaryRegValOpCode(AsmHomeBrewParser.BinaryRegValOpCodeContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#ternaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterTernaryOperation(AsmHomeBrewParser.TernaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#ternaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitTernaryOperation(AsmHomeBrewParser.TernaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#aluTernaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterAluTernaryOperation(AsmHomeBrewParser.AluTernaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#aluTernaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitAluTernaryOperation(AsmHomeBrewParser.AluTernaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#aluTernaryOpcode}.
	 * @param ctx the parse tree
	 */
	void enterAluTernaryOpcode(AsmHomeBrewParser.AluTernaryOpcodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#aluTernaryOpcode}.
	 * @param ctx the parse tree
	 */
	void exitAluTernaryOpcode(AsmHomeBrewParser.AluTernaryOpcodeContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#opcode}.
	 * @param ctx the parse tree
	 */
	void enterOpcode(AsmHomeBrewParser.OpcodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#opcode}.
	 * @param ctx the parse tree
	 */
	void exitOpcode(AsmHomeBrewParser.OpcodeContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#assemblerStringDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAssemblerStringDeclaration(AsmHomeBrewParser.AssemblerStringDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#assemblerStringDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAssemblerStringDeclaration(AsmHomeBrewParser.AssemblerStringDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#macro}.
	 * @param ctx the parse tree
	 */
	void enterMacro(AsmHomeBrewParser.MacroContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#macro}.
	 * @param ctx the parse tree
	 */
	void exitMacro(AsmHomeBrewParser.MacroContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#macroParamValue}.
	 * @param ctx the parse tree
	 */
	void enterMacroParamValue(AsmHomeBrewParser.MacroParamValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#macroParamValue}.
	 * @param ctx the parse tree
	 */
	void exitMacroParamValue(AsmHomeBrewParser.MacroParamValueContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#macroName}.
	 * @param ctx the parse tree
	 */
	void enterMacroName(AsmHomeBrewParser.MacroNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#macroName}.
	 * @param ctx the parse tree
	 */
	void exitMacroName(AsmHomeBrewParser.MacroNameContext ctx);
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
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(AsmHomeBrewParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(AsmHomeBrewParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsmHomeBrewParser#parenString}.
	 * @param ctx the parse tree
	 */
	void enterParenString(AsmHomeBrewParser.ParenStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsmHomeBrewParser#parenString}.
	 * @param ctx the parse tree
	 */
	void exitParenString(AsmHomeBrewParser.ParenStringContext ctx);
}