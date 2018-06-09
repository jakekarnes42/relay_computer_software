// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/asm/antlr/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse.asm.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AsmHomeBrewParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, MOV=3, CLR=4, ADD=5, INC=6, DEC=7, AND=8, OR=9, XOR=10, 
		NOT=11, CMP=12, SUB=13, LOAD=14, FETCH=15, STORE=16, PUSH=17, POP=18, 
		RET=19, CALL=20, WRDIN=21, WRDOUT=22, JMP=23, JZ=24, JNZ=25, JS=26, JNS=27, 
		JC=28, JNC=29, JO=30, JNO=31, NOP=32, HALT=33, TIN=34, TOUT=35, ORG=36, 
		DW=37, DS=38, AX=39, BX=40, CX=41, DX=42, EX=43, SP=44, RP=45, PC=46, 
		NAME=47, MACRO_NAME=48, NUMBER=49, DECIMAL=50, HEX=51, COMMENT=52, STRING=53, 
		PAREN_STRING=54, JAVASCRIPT=55, EOL=56, WS=57;
	public static final int
		RULE_program = 0, RULE_line = 1, RULE_instruction = 2, RULE_labelDefinition = 3, 
		RULE_operation = 4, RULE_noArgOperation = 5, RULE_unaryOperation = 6, 
		RULE_returnOperation = 7, RULE_clearOperation = 8, RULE_ioOperation = 9, 
		RULE_ioOpcode = 10, RULE_jumpOperation = 11, RULE_conditionalJumpOperation = 12, 
		RULE_jumpOpcode = 13, RULE_binaryOperation = 14, RULE_binaryRegRegOperation = 15, 
		RULE_binaryRegRegOpCode = 16, RULE_binaryRegValOperation = 17, RULE_binaryRegValOpCode = 18, 
		RULE_stackOperation = 19, RULE_pushOperation = 20, RULE_popOperation = 21, 
		RULE_callOperation = 22, RULE_ternaryOperation = 23, RULE_aluTernaryOperation = 24, 
		RULE_aluTernaryOpcode = 25, RULE_value = 26, RULE_label = 27, RULE_register = 28, 
		RULE_stackRegister = 29, RULE_opcode = 30, RULE_assemblerDirective = 31, 
		RULE_assemblerOrgDirective = 32, RULE_assemblerWordDeclaration = 33, RULE_assemblerStringDeclaration = 34, 
		RULE_macro = 35, RULE_macroParamValue = 36, RULE_jsExpression = 37, RULE_name = 38, 
		RULE_macroName = 39, RULE_number = 40, RULE_comment = 41, RULE_string = 42, 
		RULE_parenString = 43;
	public static final String[] ruleNames = {
		"program", "line", "instruction", "labelDefinition", "operation", "noArgOperation", 
		"unaryOperation", "returnOperation", "clearOperation", "ioOperation", 
		"ioOpcode", "jumpOperation", "conditionalJumpOperation", "jumpOpcode", 
		"binaryOperation", "binaryRegRegOperation", "binaryRegRegOpCode", "binaryRegValOperation", 
		"binaryRegValOpCode", "stackOperation", "pushOperation", "popOperation", 
		"callOperation", "ternaryOperation", "aluTernaryOperation", "aluTernaryOpcode", 
		"value", "label", "register", "stackRegister", "opcode", "assemblerDirective", 
		"assemblerOrgDirective", "assemblerWordDeclaration", "assemblerStringDeclaration", 
		"macro", "macroParamValue", "jsExpression", "name", "macroName", "number", 
		"comment", "string", "parenString"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "MOV", "CLR", "ADD", "INC", "DEC", "AND", "OR", "XOR", 
		"NOT", "CMP", "SUB", "LOAD", "FETCH", "STORE", "PUSH", "POP", "RET", "CALL", 
		"WRDIN", "WRDOUT", "JMP", "JZ", "JNZ", "JS", "JNS", "JC", "JNC", "JO", 
		"JNO", "NOP", "HALT", "TIN", "TOUT", "ORG", "DW", "DS", "AX", "BX", "CX", 
		"DX", "EX", "SP", "RP", "PC", "NAME", "MACRO_NAME", "NUMBER", "DECIMAL", 
		"HEX", "COMMENT", "STRING", "PAREN_STRING", "JAVASCRIPT", "EOL", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "AsmHomeBrew.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AsmHomeBrewParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<TerminalNode> EOL() { return getTokens(AsmHomeBrewParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(AsmHomeBrewParser.EOL, i);
		}
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << CLR) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << FETCH) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JS) | (1L << JNS) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT) | (1L << TIN) | (1L << TOUT) | (1L << ORG) | (1L << DW) | (1L << DS) | (1L << NAME) | (1L << MACRO_NAME) | (1L << COMMENT) | (1L << JAVASCRIPT))) != 0)) {
					{
					setState(88);
					line();
					}
				}

				setState(91);
				match(EOL);
				}
				}
				setState(94); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << CLR) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << FETCH) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JS) | (1L << JNS) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT) | (1L << TIN) | (1L << TOUT) | (1L << ORG) | (1L << DW) | (1L << DS) | (1L << NAME) | (1L << MACRO_NAME) | (1L << COMMENT) | (1L << JAVASCRIPT) | (1L << EOL))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public LabelDefinitionContext labelDefinition() {
			return getRuleContext(LabelDefinitionContext.class,0);
		}
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public AssemblerDirectiveContext assemblerDirective() {
			return getRuleContext(AssemblerDirectiveContext.class,0);
		}
		public MacroContext macro() {
			return getRuleContext(MacroContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		int _la;
		try {
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				comment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				labelDefinition();
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(98);
					comment();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(101);
				instruction();
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(102);
					comment();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(105);
				assemblerDirective();
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(106);
					comment();
					}
				}

				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(109);
				macro();
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(110);
					comment();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public LabelDefinitionContext labelDefinition() {
			return getRuleContext(LabelDefinitionContext.class,0);
		}
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_instruction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(115);
				labelDefinition();
				}
			}

			setState(118);
			operation();
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(119);
				comment();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelDefinitionContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public LabelDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterLabelDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitLabelDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitLabelDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelDefinitionContext labelDefinition() throws RecognitionException {
		LabelDefinitionContext _localctx = new LabelDefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_labelDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			label();
			setState(123);
			match(T__0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationContext extends ParserRuleContext {
		public NoArgOperationContext noArgOperation() {
			return getRuleContext(NoArgOperationContext.class,0);
		}
		public UnaryOperationContext unaryOperation() {
			return getRuleContext(UnaryOperationContext.class,0);
		}
		public BinaryOperationContext binaryOperation() {
			return getRuleContext(BinaryOperationContext.class,0);
		}
		public TernaryOperationContext ternaryOperation() {
			return getRuleContext(TernaryOperationContext.class,0);
		}
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_operation);
		try {
			setState(129);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOP:
			case HALT:
			case TIN:
			case TOUT:
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				noArgOperation();
				}
				break;
			case CLR:
			case RET:
			case WRDIN:
			case WRDOUT:
			case JMP:
			case JZ:
			case JNZ:
			case JS:
			case JNS:
			case JC:
			case JNC:
			case JO:
			case JNO:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				unaryOperation();
				}
				break;
			case MOV:
			case INC:
			case DEC:
			case NOT:
			case LOAD:
			case FETCH:
			case STORE:
			case PUSH:
			case POP:
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(127);
				binaryOperation();
				}
				break;
			case ADD:
			case AND:
			case OR:
			case XOR:
			case CMP:
			case SUB:
				enterOuterAlt(_localctx, 4);
				{
				setState(128);
				ternaryOperation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NoArgOperationContext extends ParserRuleContext {
		public TerminalNode NOP() { return getToken(AsmHomeBrewParser.NOP, 0); }
		public TerminalNode HALT() { return getToken(AsmHomeBrewParser.HALT, 0); }
		public TerminalNode TIN() { return getToken(AsmHomeBrewParser.TIN, 0); }
		public TerminalNode TOUT() { return getToken(AsmHomeBrewParser.TOUT, 0); }
		public NoArgOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noArgOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterNoArgOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitNoArgOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitNoArgOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NoArgOperationContext noArgOperation() throws RecognitionException {
		NoArgOperationContext _localctx = new NoArgOperationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_noArgOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NOP) | (1L << HALT) | (1L << TIN) | (1L << TOUT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperationContext extends ParserRuleContext {
		public IoOperationContext ioOperation() {
			return getRuleContext(IoOperationContext.class,0);
		}
		public ReturnOperationContext returnOperation() {
			return getRuleContext(ReturnOperationContext.class,0);
		}
		public JumpOperationContext jumpOperation() {
			return getRuleContext(JumpOperationContext.class,0);
		}
		public ConditionalJumpOperationContext conditionalJumpOperation() {
			return getRuleContext(ConditionalJumpOperationContext.class,0);
		}
		public ClearOperationContext clearOperation() {
			return getRuleContext(ClearOperationContext.class,0);
		}
		public UnaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperationContext unaryOperation() throws RecognitionException {
		UnaryOperationContext _localctx = new UnaryOperationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_unaryOperation);
		try {
			setState(138);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WRDIN:
			case WRDOUT:
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				ioOperation();
				}
				break;
			case RET:
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				returnOperation();
				}
				break;
			case JMP:
				enterOuterAlt(_localctx, 3);
				{
				setState(135);
				jumpOperation();
				}
				break;
			case JZ:
			case JNZ:
			case JS:
			case JNS:
			case JC:
			case JNC:
			case JO:
			case JNO:
				enterOuterAlt(_localctx, 4);
				{
				setState(136);
				conditionalJumpOperation();
				}
				break;
			case CLR:
				enterOuterAlt(_localctx, 5);
				{
				setState(137);
				clearOperation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnOperationContext extends ParserRuleContext {
		public TerminalNode RET() { return getToken(AsmHomeBrewParser.RET, 0); }
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public ReturnOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterReturnOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitReturnOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitReturnOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnOperationContext returnOperation() throws RecognitionException {
		ReturnOperationContext _localctx = new ReturnOperationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_returnOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(RET);
			setState(141);
			stackRegister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClearOperationContext extends ParserRuleContext {
		public TerminalNode CLR() { return getToken(AsmHomeBrewParser.CLR, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public ClearOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clearOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterClearOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitClearOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitClearOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClearOperationContext clearOperation() throws RecognitionException {
		ClearOperationContext _localctx = new ClearOperationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_clearOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(CLR);
			setState(144);
			register();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IoOperationContext extends ParserRuleContext {
		public IoOpcodeContext ioOpcode() {
			return getRuleContext(IoOpcodeContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public IoOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ioOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterIoOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitIoOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitIoOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IoOperationContext ioOperation() throws RecognitionException {
		IoOperationContext _localctx = new IoOperationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ioOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			ioOpcode();
			setState(147);
			register();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IoOpcodeContext extends ParserRuleContext {
		public TerminalNode WRDIN() { return getToken(AsmHomeBrewParser.WRDIN, 0); }
		public TerminalNode WRDOUT() { return getToken(AsmHomeBrewParser.WRDOUT, 0); }
		public IoOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ioOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterIoOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitIoOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitIoOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IoOpcodeContext ioOpcode() throws RecognitionException {
		IoOpcodeContext _localctx = new IoOpcodeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ioOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_la = _input.LA(1);
			if ( !(_la==WRDIN || _la==WRDOUT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpOperationContext extends ParserRuleContext {
		public TerminalNode JMP() { return getToken(AsmHomeBrewParser.JMP, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public JumpOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterJumpOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitJumpOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitJumpOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpOperationContext jumpOperation() throws RecognitionException {
		JumpOperationContext _localctx = new JumpOperationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_jumpOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			match(JMP);
			setState(152);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalJumpOperationContext extends ParserRuleContext {
		public JumpOpcodeContext jumpOpcode() {
			return getRuleContext(JumpOpcodeContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ConditionalJumpOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalJumpOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterConditionalJumpOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitConditionalJumpOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitConditionalJumpOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalJumpOperationContext conditionalJumpOperation() throws RecognitionException {
		ConditionalJumpOperationContext _localctx = new ConditionalJumpOperationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_conditionalJumpOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			jumpOpcode();
			setState(155);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpOpcodeContext extends ParserRuleContext {
		public TerminalNode JZ() { return getToken(AsmHomeBrewParser.JZ, 0); }
		public TerminalNode JNZ() { return getToken(AsmHomeBrewParser.JNZ, 0); }
		public TerminalNode JS() { return getToken(AsmHomeBrewParser.JS, 0); }
		public TerminalNode JNS() { return getToken(AsmHomeBrewParser.JNS, 0); }
		public TerminalNode JC() { return getToken(AsmHomeBrewParser.JC, 0); }
		public TerminalNode JNC() { return getToken(AsmHomeBrewParser.JNC, 0); }
		public TerminalNode JO() { return getToken(AsmHomeBrewParser.JO, 0); }
		public TerminalNode JNO() { return getToken(AsmHomeBrewParser.JNO, 0); }
		public JumpOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterJumpOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitJumpOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitJumpOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpOpcodeContext jumpOpcode() throws RecognitionException {
		JumpOpcodeContext _localctx = new JumpOpcodeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_jumpOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << JZ) | (1L << JNZ) | (1L << JS) | (1L << JNS) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryOperationContext extends ParserRuleContext {
		public BinaryRegRegOperationContext binaryRegRegOperation() {
			return getRuleContext(BinaryRegRegOperationContext.class,0);
		}
		public BinaryRegValOperationContext binaryRegValOperation() {
			return getRuleContext(BinaryRegValOperationContext.class,0);
		}
		public StackOperationContext stackOperation() {
			return getRuleContext(StackOperationContext.class,0);
		}
		public BinaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryOperationContext binaryOperation() throws RecognitionException {
		BinaryOperationContext _localctx = new BinaryOperationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_binaryOperation);
		try {
			setState(162);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MOV:
			case INC:
			case DEC:
			case NOT:
			case FETCH:
			case STORE:
				enterOuterAlt(_localctx, 1);
				{
				setState(159);
				binaryRegRegOperation();
				}
				break;
			case LOAD:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				binaryRegValOperation();
				}
				break;
			case PUSH:
			case POP:
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(161);
				stackOperation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryRegRegOperationContext extends ParserRuleContext {
		public BinaryRegRegOpCodeContext binaryRegRegOpCode() {
			return getRuleContext(BinaryRegRegOpCodeContext.class,0);
		}
		public List<RegisterContext> register() {
			return getRuleContexts(RegisterContext.class);
		}
		public RegisterContext register(int i) {
			return getRuleContext(RegisterContext.class,i);
		}
		public BinaryRegRegOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegRegOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegRegOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegRegOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegRegOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegRegOperationContext binaryRegRegOperation() throws RecognitionException {
		BinaryRegRegOperationContext _localctx = new BinaryRegRegOperationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_binaryRegRegOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			binaryRegRegOpCode();
			setState(165);
			register();
			setState(166);
			match(T__1);
			setState(167);
			register();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryRegRegOpCodeContext extends ParserRuleContext {
		public TerminalNode MOV() { return getToken(AsmHomeBrewParser.MOV, 0); }
		public TerminalNode STORE() { return getToken(AsmHomeBrewParser.STORE, 0); }
		public TerminalNode FETCH() { return getToken(AsmHomeBrewParser.FETCH, 0); }
		public TerminalNode INC() { return getToken(AsmHomeBrewParser.INC, 0); }
		public TerminalNode DEC() { return getToken(AsmHomeBrewParser.DEC, 0); }
		public TerminalNode NOT() { return getToken(AsmHomeBrewParser.NOT, 0); }
		public BinaryRegRegOpCodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegRegOpCode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegRegOpCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegRegOpCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegRegOpCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegRegOpCodeContext binaryRegRegOpCode() throws RecognitionException {
		BinaryRegRegOpCodeContext _localctx = new BinaryRegRegOpCodeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_binaryRegRegOpCode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << INC) | (1L << DEC) | (1L << NOT) | (1L << FETCH) | (1L << STORE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryRegValOperationContext extends ParserRuleContext {
		public BinaryRegValOpCodeContext binaryRegValOpCode() {
			return getRuleContext(BinaryRegValOpCodeContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public BinaryRegValOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegValOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegValOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegValOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegValOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegValOperationContext binaryRegValOperation() throws RecognitionException {
		BinaryRegValOperationContext _localctx = new BinaryRegValOperationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_binaryRegValOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			binaryRegValOpCode();
			setState(172);
			register();
			setState(173);
			match(T__1);
			setState(174);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryRegValOpCodeContext extends ParserRuleContext {
		public TerminalNode LOAD() { return getToken(AsmHomeBrewParser.LOAD, 0); }
		public BinaryRegValOpCodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegValOpCode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegValOpCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegValOpCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegValOpCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegValOpCodeContext binaryRegValOpCode() throws RecognitionException {
		BinaryRegValOpCodeContext _localctx = new BinaryRegValOpCodeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_binaryRegValOpCode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			match(LOAD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StackOperationContext extends ParserRuleContext {
		public PushOperationContext pushOperation() {
			return getRuleContext(PushOperationContext.class,0);
		}
		public PopOperationContext popOperation() {
			return getRuleContext(PopOperationContext.class,0);
		}
		public CallOperationContext callOperation() {
			return getRuleContext(CallOperationContext.class,0);
		}
		public StackOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stackOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterStackOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitStackOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitStackOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackOperationContext stackOperation() throws RecognitionException {
		StackOperationContext _localctx = new StackOperationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_stackOperation);
		try {
			setState(181);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PUSH:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				pushOperation();
				}
				break;
			case POP:
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				popOperation();
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(180);
				callOperation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PushOperationContext extends ParserRuleContext {
		public TerminalNode PUSH() { return getToken(AsmHomeBrewParser.PUSH, 0); }
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public PushOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pushOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterPushOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitPushOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitPushOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PushOperationContext pushOperation() throws RecognitionException {
		PushOperationContext _localctx = new PushOperationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_pushOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(PUSH);
			setState(184);
			stackRegister();
			setState(185);
			match(T__1);
			setState(186);
			register();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PopOperationContext extends ParserRuleContext {
		public TerminalNode POP() { return getToken(AsmHomeBrewParser.POP, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public PopOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_popOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterPopOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitPopOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitPopOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PopOperationContext popOperation() throws RecognitionException {
		PopOperationContext _localctx = new PopOperationContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_popOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(POP);
			setState(189);
			register();
			setState(190);
			match(T__1);
			setState(191);
			stackRegister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallOperationContext extends ParserRuleContext {
		public TerminalNode CALL() { return getToken(AsmHomeBrewParser.CALL, 0); }
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public CallOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterCallOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitCallOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitCallOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallOperationContext callOperation() throws RecognitionException {
		CallOperationContext _localctx = new CallOperationContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_callOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(CALL);
			setState(194);
			stackRegister();
			setState(195);
			match(T__1);
			setState(196);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TernaryOperationContext extends ParserRuleContext {
		public AluTernaryOperationContext aluTernaryOperation() {
			return getRuleContext(AluTernaryOperationContext.class,0);
		}
		public TernaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ternaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterTernaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitTernaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitTernaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TernaryOperationContext ternaryOperation() throws RecognitionException {
		TernaryOperationContext _localctx = new TernaryOperationContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_ternaryOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			aluTernaryOperation();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AluTernaryOperationContext extends ParserRuleContext {
		public AluTernaryOpcodeContext aluTernaryOpcode() {
			return getRuleContext(AluTernaryOpcodeContext.class,0);
		}
		public List<RegisterContext> register() {
			return getRuleContexts(RegisterContext.class);
		}
		public RegisterContext register(int i) {
			return getRuleContext(RegisterContext.class,i);
		}
		public AluTernaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aluTernaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAluTernaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAluTernaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAluTernaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AluTernaryOperationContext aluTernaryOperation() throws RecognitionException {
		AluTernaryOperationContext _localctx = new AluTernaryOperationContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_aluTernaryOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			aluTernaryOpcode();
			setState(201);
			register();
			setState(202);
			match(T__1);
			setState(203);
			register();
			setState(204);
			match(T__1);
			setState(205);
			register();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AluTernaryOpcodeContext extends ParserRuleContext {
		public TerminalNode ADD() { return getToken(AsmHomeBrewParser.ADD, 0); }
		public TerminalNode AND() { return getToken(AsmHomeBrewParser.AND, 0); }
		public TerminalNode OR() { return getToken(AsmHomeBrewParser.OR, 0); }
		public TerminalNode XOR() { return getToken(AsmHomeBrewParser.XOR, 0); }
		public TerminalNode CMP() { return getToken(AsmHomeBrewParser.CMP, 0); }
		public TerminalNode SUB() { return getToken(AsmHomeBrewParser.SUB, 0); }
		public AluTernaryOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aluTernaryOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAluTernaryOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAluTernaryOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAluTernaryOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AluTernaryOpcodeContext aluTernaryOpcode() throws RecognitionException {
		AluTernaryOpcodeContext _localctx = new AluTernaryOpcodeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_aluTernaryOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << CMP) | (1L << SUB))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public JsExpressionContext jsExpression() {
			return getRuleContext(JsExpressionContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_value);
		try {
			setState(212);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(209);
				label();
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(210);
				number();
				}
				break;
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 3);
				{
				setState(211);
				jsExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RegisterContext extends ParserRuleContext {
		public TerminalNode AX() { return getToken(AsmHomeBrewParser.AX, 0); }
		public TerminalNode BX() { return getToken(AsmHomeBrewParser.BX, 0); }
		public TerminalNode CX() { return getToken(AsmHomeBrewParser.CX, 0); }
		public TerminalNode DX() { return getToken(AsmHomeBrewParser.DX, 0); }
		public TerminalNode EX() { return getToken(AsmHomeBrewParser.EX, 0); }
		public TerminalNode SP() { return getToken(AsmHomeBrewParser.SP, 0); }
		public TerminalNode RP() { return getToken(AsmHomeBrewParser.RP, 0); }
		public TerminalNode PC() { return getToken(AsmHomeBrewParser.PC, 0); }
		public RegisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_register; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterRegister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitRegister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitRegister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegisterContext register() throws RecognitionException {
		RegisterContext _localctx = new RegisterContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_register);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AX) | (1L << BX) | (1L << CX) | (1L << DX) | (1L << EX) | (1L << SP) | (1L << RP) | (1L << PC))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StackRegisterContext extends ParserRuleContext {
		public TerminalNode SP() { return getToken(AsmHomeBrewParser.SP, 0); }
		public TerminalNode RP() { return getToken(AsmHomeBrewParser.RP, 0); }
		public StackRegisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stackRegister; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterStackRegister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitStackRegister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitStackRegister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackRegisterContext stackRegister() throws RecognitionException {
		StackRegisterContext _localctx = new StackRegisterContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_stackRegister);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			_la = _input.LA(1);
			if ( !(_la==SP || _la==RP) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpcodeContext extends ParserRuleContext {
		public TerminalNode MOV() { return getToken(AsmHomeBrewParser.MOV, 0); }
		public TerminalNode CLR() { return getToken(AsmHomeBrewParser.CLR, 0); }
		public TerminalNode ADD() { return getToken(AsmHomeBrewParser.ADD, 0); }
		public TerminalNode INC() { return getToken(AsmHomeBrewParser.INC, 0); }
		public TerminalNode DEC() { return getToken(AsmHomeBrewParser.DEC, 0); }
		public TerminalNode AND() { return getToken(AsmHomeBrewParser.AND, 0); }
		public TerminalNode OR() { return getToken(AsmHomeBrewParser.OR, 0); }
		public TerminalNode XOR() { return getToken(AsmHomeBrewParser.XOR, 0); }
		public TerminalNode NOT() { return getToken(AsmHomeBrewParser.NOT, 0); }
		public TerminalNode CMP() { return getToken(AsmHomeBrewParser.CMP, 0); }
		public TerminalNode SUB() { return getToken(AsmHomeBrewParser.SUB, 0); }
		public TerminalNode LOAD() { return getToken(AsmHomeBrewParser.LOAD, 0); }
		public TerminalNode FETCH() { return getToken(AsmHomeBrewParser.FETCH, 0); }
		public TerminalNode STORE() { return getToken(AsmHomeBrewParser.STORE, 0); }
		public TerminalNode PUSH() { return getToken(AsmHomeBrewParser.PUSH, 0); }
		public TerminalNode POP() { return getToken(AsmHomeBrewParser.POP, 0); }
		public TerminalNode RET() { return getToken(AsmHomeBrewParser.RET, 0); }
		public TerminalNode CALL() { return getToken(AsmHomeBrewParser.CALL, 0); }
		public TerminalNode WRDIN() { return getToken(AsmHomeBrewParser.WRDIN, 0); }
		public TerminalNode WRDOUT() { return getToken(AsmHomeBrewParser.WRDOUT, 0); }
		public TerminalNode JMP() { return getToken(AsmHomeBrewParser.JMP, 0); }
		public TerminalNode JZ() { return getToken(AsmHomeBrewParser.JZ, 0); }
		public TerminalNode JNZ() { return getToken(AsmHomeBrewParser.JNZ, 0); }
		public TerminalNode JS() { return getToken(AsmHomeBrewParser.JS, 0); }
		public TerminalNode JNS() { return getToken(AsmHomeBrewParser.JNS, 0); }
		public TerminalNode JC() { return getToken(AsmHomeBrewParser.JC, 0); }
		public TerminalNode JNC() { return getToken(AsmHomeBrewParser.JNC, 0); }
		public TerminalNode JO() { return getToken(AsmHomeBrewParser.JO, 0); }
		public TerminalNode JNO() { return getToken(AsmHomeBrewParser.JNO, 0); }
		public TerminalNode NOP() { return getToken(AsmHomeBrewParser.NOP, 0); }
		public TerminalNode HALT() { return getToken(AsmHomeBrewParser.HALT, 0); }
		public TerminalNode TIN() { return getToken(AsmHomeBrewParser.TIN, 0); }
		public TerminalNode TOUT() { return getToken(AsmHomeBrewParser.TOUT, 0); }
		public OpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpcodeContext opcode() throws RecognitionException {
		OpcodeContext _localctx = new OpcodeContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_opcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << CLR) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << FETCH) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JS) | (1L << JNS) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT) | (1L << TIN) | (1L << TOUT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssemblerDirectiveContext extends ParserRuleContext {
		public JsExpressionContext jsExpression() {
			return getRuleContext(JsExpressionContext.class,0);
		}
		public AssemblerOrgDirectiveContext assemblerOrgDirective() {
			return getRuleContext(AssemblerOrgDirectiveContext.class,0);
		}
		public AssemblerWordDeclarationContext assemblerWordDeclaration() {
			return getRuleContext(AssemblerWordDeclarationContext.class,0);
		}
		public AssemblerStringDeclarationContext assemblerStringDeclaration() {
			return getRuleContext(AssemblerStringDeclarationContext.class,0);
		}
		public AssemblerDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerDirective(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerDirective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerDirectiveContext assemblerDirective() throws RecognitionException {
		AssemblerDirectiveContext _localctx = new AssemblerDirectiveContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_assemblerDirective);
		try {
			setState(226);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				jsExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				assemblerOrgDirective();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(224);
				assemblerWordDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(225);
				assemblerStringDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssemblerOrgDirectiveContext extends ParserRuleContext {
		public TerminalNode ORG() { return getToken(AsmHomeBrewParser.ORG, 0); }
		public JsExpressionContext jsExpression() {
			return getRuleContext(JsExpressionContext.class,0);
		}
		public AssemblerOrgDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerOrgDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerOrgDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerOrgDirective(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerOrgDirective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerOrgDirectiveContext assemblerOrgDirective() throws RecognitionException {
		AssemblerOrgDirectiveContext _localctx = new AssemblerOrgDirectiveContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_assemblerOrgDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(ORG);
			setState(229);
			jsExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssemblerWordDeclarationContext extends ParserRuleContext {
		public TerminalNode DW() { return getToken(AsmHomeBrewParser.DW, 0); }
		public LabelDefinitionContext labelDefinition() {
			return getRuleContext(LabelDefinitionContext.class,0);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public AssemblerWordDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerWordDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerWordDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerWordDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerWordDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerWordDeclarationContext assemblerWordDeclaration() throws RecognitionException {
		AssemblerWordDeclarationContext _localctx = new AssemblerWordDeclarationContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_assemblerWordDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(231);
				labelDefinition();
				}
			}

			setState(234);
			match(DW);
			setState(239); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(236);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(235);
					match(T__1);
					}
				}

				setState(238);
				value();
				}
				}
				setState(241); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << NAME) | (1L << NUMBER) | (1L << JAVASCRIPT))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssemblerStringDeclarationContext extends ParserRuleContext {
		public TerminalNode DS() { return getToken(AsmHomeBrewParser.DS, 0); }
		public TerminalNode STRING() { return getToken(AsmHomeBrewParser.STRING, 0); }
		public LabelDefinitionContext labelDefinition() {
			return getRuleContext(LabelDefinitionContext.class,0);
		}
		public AssemblerStringDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerStringDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerStringDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerStringDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerStringDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerStringDeclarationContext assemblerStringDeclaration() throws RecognitionException {
		AssemblerStringDeclarationContext _localctx = new AssemblerStringDeclarationContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_assemblerStringDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(243);
				labelDefinition();
				}
			}

			setState(246);
			match(DS);
			setState(247);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MacroContext extends ParserRuleContext {
		public MacroNameContext macroName() {
			return getRuleContext(MacroNameContext.class,0);
		}
		public List<MacroParamValueContext> macroParamValue() {
			return getRuleContexts(MacroParamValueContext.class);
		}
		public MacroParamValueContext macroParamValue(int i) {
			return getRuleContext(MacroParamValueContext.class,i);
		}
		public MacroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterMacro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitMacro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitMacro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroContext macro() throws RecognitionException {
		MacroContext _localctx = new MacroContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_macro);
		try {
			setState(370);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(249);
				macroName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(250);
				macroName();
				setState(251);
				macroParamValue();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(253);
				macroName();
				setState(254);
				macroParamValue();
				setState(255);
				match(T__1);
				setState(256);
				macroParamValue();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(258);
				macroName();
				setState(259);
				macroParamValue();
				setState(260);
				match(T__1);
				setState(261);
				macroParamValue();
				setState(262);
				match(T__1);
				setState(263);
				macroParamValue();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(265);
				macroName();
				setState(266);
				macroParamValue();
				setState(267);
				match(T__1);
				setState(268);
				macroParamValue();
				setState(269);
				match(T__1);
				setState(270);
				macroParamValue();
				setState(271);
				match(T__1);
				setState(272);
				macroParamValue();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(274);
				macroName();
				setState(275);
				macroParamValue();
				setState(276);
				match(T__1);
				setState(277);
				macroParamValue();
				setState(278);
				match(T__1);
				setState(279);
				macroParamValue();
				setState(280);
				match(T__1);
				setState(281);
				macroParamValue();
				setState(282);
				match(T__1);
				setState(283);
				macroParamValue();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(285);
				macroName();
				setState(286);
				macroParamValue();
				setState(287);
				match(T__1);
				setState(288);
				macroParamValue();
				setState(289);
				match(T__1);
				setState(290);
				macroParamValue();
				setState(291);
				match(T__1);
				setState(292);
				macroParamValue();
				setState(293);
				match(T__1);
				setState(294);
				macroParamValue();
				setState(295);
				match(T__1);
				setState(296);
				macroParamValue();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(298);
				macroName();
				setState(299);
				macroParamValue();
				setState(300);
				match(T__1);
				setState(301);
				macroParamValue();
				setState(302);
				match(T__1);
				setState(303);
				macroParamValue();
				setState(304);
				match(T__1);
				setState(305);
				macroParamValue();
				setState(306);
				match(T__1);
				setState(307);
				macroParamValue();
				setState(308);
				match(T__1);
				setState(309);
				macroParamValue();
				setState(310);
				match(T__1);
				setState(311);
				macroParamValue();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(313);
				macroName();
				setState(314);
				macroParamValue();
				setState(315);
				match(T__1);
				setState(316);
				macroParamValue();
				setState(317);
				match(T__1);
				setState(318);
				macroParamValue();
				setState(319);
				match(T__1);
				setState(320);
				macroParamValue();
				setState(321);
				match(T__1);
				setState(322);
				macroParamValue();
				setState(323);
				match(T__1);
				setState(324);
				macroParamValue();
				setState(325);
				match(T__1);
				setState(326);
				macroParamValue();
				setState(327);
				match(T__1);
				setState(328);
				macroParamValue();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(330);
				macroName();
				setState(331);
				macroParamValue();
				setState(332);
				match(T__1);
				setState(333);
				macroParamValue();
				setState(334);
				match(T__1);
				setState(335);
				macroParamValue();
				setState(336);
				match(T__1);
				setState(337);
				macroParamValue();
				setState(338);
				match(T__1);
				setState(339);
				macroParamValue();
				setState(340);
				match(T__1);
				setState(341);
				macroParamValue();
				setState(342);
				match(T__1);
				setState(343);
				macroParamValue();
				setState(344);
				match(T__1);
				setState(345);
				macroParamValue();
				setState(346);
				match(T__1);
				setState(347);
				macroParamValue();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(349);
				macroName();
				setState(350);
				macroParamValue();
				setState(351);
				match(T__1);
				setState(352);
				macroParamValue();
				setState(353);
				match(T__1);
				setState(354);
				macroParamValue();
				setState(355);
				match(T__1);
				setState(356);
				macroParamValue();
				setState(357);
				match(T__1);
				setState(358);
				macroParamValue();
				setState(359);
				match(T__1);
				setState(360);
				macroParamValue();
				setState(361);
				match(T__1);
				setState(362);
				macroParamValue();
				setState(363);
				match(T__1);
				setState(364);
				macroParamValue();
				setState(365);
				match(T__1);
				setState(366);
				macroParamValue();
				setState(367);
				match(T__1);
				setState(368);
				macroParamValue();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MacroParamValueContext extends ParserRuleContext {
		public OpcodeContext opcode() {
			return getRuleContext(OpcodeContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ParenStringContext parenString() {
			return getRuleContext(ParenStringContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public MacroParamValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroParamValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterMacroParamValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitMacroParamValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitMacroParamValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroParamValueContext macroParamValue() throws RecognitionException {
		MacroParamValueContext _localctx = new MacroParamValueContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_macroParamValue);
		try {
			setState(377);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MOV:
			case CLR:
			case ADD:
			case INC:
			case DEC:
			case AND:
			case OR:
			case XOR:
			case NOT:
			case CMP:
			case SUB:
			case LOAD:
			case FETCH:
			case STORE:
			case PUSH:
			case POP:
			case RET:
			case CALL:
			case WRDIN:
			case WRDOUT:
			case JMP:
			case JZ:
			case JNZ:
			case JS:
			case JNS:
			case JC:
			case JNC:
			case JO:
			case JNO:
			case NOP:
			case HALT:
			case TIN:
			case TOUT:
				enterOuterAlt(_localctx, 1);
				{
				setState(372);
				opcode();
				}
				break;
			case AX:
			case BX:
			case CX:
			case DX:
			case EX:
			case SP:
			case RP:
			case PC:
				enterOuterAlt(_localctx, 2);
				{
				setState(373);
				register();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(374);
				string();
				}
				break;
			case PAREN_STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(375);
				parenString();
				}
				break;
			case NAME:
			case NUMBER:
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 5);
				{
				setState(376);
				value();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JsExpressionContext extends ParserRuleContext {
		public TerminalNode JAVASCRIPT() { return getToken(AsmHomeBrewParser.JAVASCRIPT, 0); }
		public JsExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterJsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitJsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitJsExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsExpressionContext jsExpression() throws RecognitionException {
		JsExpressionContext _localctx = new JsExpressionContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_jsExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
			match(JAVASCRIPT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(AsmHomeBrewParser.NAME, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MacroNameContext extends ParserRuleContext {
		public TerminalNode MACRO_NAME() { return getToken(AsmHomeBrewParser.MACRO_NAME, 0); }
		public MacroNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterMacroName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitMacroName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitMacroName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroNameContext macroName() throws RecognitionException {
		MacroNameContext _localctx = new MacroNameContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_macroName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			match(MACRO_NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(AsmHomeBrewParser.NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(AsmHomeBrewParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(COMMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(AsmHomeBrewParser.STRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParenStringContext extends ParserRuleContext {
		public TerminalNode PAREN_STRING() { return getToken(AsmHomeBrewParser.PAREN_STRING, 0); }
		public ParenStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenString; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterParenString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitParenString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitParenString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParenStringContext parenString() throws RecognitionException {
		ParenStringContext _localctx = new ParenStringContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_parenString);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(391);
			match(PAREN_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3;\u018c\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\5\2\\\n\2\3\2\6\2_\n\2\r\2\16\2`\3\3\3\3\3\3\5\3f\n\3\3"+
		"\3\3\3\5\3j\n\3\3\3\3\3\5\3n\n\3\3\3\3\3\5\3r\n\3\5\3t\n\3\3\4\5\4w\n"+
		"\4\3\4\3\4\5\4{\n\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\5\6\u0084\n\6\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\5\b\u008d\n\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\5\20\u00a5"+
		"\n\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\25\5\25\u00b8\n\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\5\34\u00d7\n\34\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3!\3!\5!\u00e5\n!\3\"\3\"\3\"\3#\5#\u00eb"+
		"\n#\3#\3#\5#\u00ef\n#\3#\6#\u00f2\n#\r#\16#\u00f3\3$\5$\u00f7\n$\3$\3"+
		"$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\5%\u0175\n%\3&\3&\3&\3&\3&\5&\u017c\n&\3\'\3\'"+
		"\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3-\2\2.\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVX\2\n\3\2\"%\3\2\27\30"+
		"\3\2\32!\6\2\5\5\b\t\r\r\21\22\5\2\7\7\n\f\16\17\3\2)\60\3\2./\3\2\5%"+
		"\2\u018d\2^\3\2\2\2\4s\3\2\2\2\6v\3\2\2\2\b|\3\2\2\2\n\u0083\3\2\2\2\f"+
		"\u0085\3\2\2\2\16\u008c\3\2\2\2\20\u008e\3\2\2\2\22\u0091\3\2\2\2\24\u0094"+
		"\3\2\2\2\26\u0097\3\2\2\2\30\u0099\3\2\2\2\32\u009c\3\2\2\2\34\u009f\3"+
		"\2\2\2\36\u00a4\3\2\2\2 \u00a6\3\2\2\2\"\u00ab\3\2\2\2$\u00ad\3\2\2\2"+
		"&\u00b2\3\2\2\2(\u00b7\3\2\2\2*\u00b9\3\2\2\2,\u00be\3\2\2\2.\u00c3\3"+
		"\2\2\2\60\u00c8\3\2\2\2\62\u00ca\3\2\2\2\64\u00d1\3\2\2\2\66\u00d6\3\2"+
		"\2\28\u00d8\3\2\2\2:\u00da\3\2\2\2<\u00dc\3\2\2\2>\u00de\3\2\2\2@\u00e4"+
		"\3\2\2\2B\u00e6\3\2\2\2D\u00ea\3\2\2\2F\u00f6\3\2\2\2H\u0174\3\2\2\2J"+
		"\u017b\3\2\2\2L\u017d\3\2\2\2N\u017f\3\2\2\2P\u0181\3\2\2\2R\u0183\3\2"+
		"\2\2T\u0185\3\2\2\2V\u0187\3\2\2\2X\u0189\3\2\2\2Z\\\5\4\3\2[Z\3\2\2\2"+
		"[\\\3\2\2\2\\]\3\2\2\2]_\7:\2\2^[\3\2\2\2_`\3\2\2\2`^\3\2\2\2`a\3\2\2"+
		"\2a\3\3\2\2\2bt\5T+\2ce\5\b\5\2df\5T+\2ed\3\2\2\2ef\3\2\2\2ft\3\2\2\2"+
		"gi\5\6\4\2hj\5T+\2ih\3\2\2\2ij\3\2\2\2jt\3\2\2\2km\5@!\2ln\5T+\2ml\3\2"+
		"\2\2mn\3\2\2\2nt\3\2\2\2oq\5H%\2pr\5T+\2qp\3\2\2\2qr\3\2\2\2rt\3\2\2\2"+
		"sb\3\2\2\2sc\3\2\2\2sg\3\2\2\2sk\3\2\2\2so\3\2\2\2t\5\3\2\2\2uw\5\b\5"+
		"\2vu\3\2\2\2vw\3\2\2\2wx\3\2\2\2xz\5\n\6\2y{\5T+\2zy\3\2\2\2z{\3\2\2\2"+
		"{\7\3\2\2\2|}\58\35\2}~\7\3\2\2~\t\3\2\2\2\177\u0084\5\f\7\2\u0080\u0084"+
		"\5\16\b\2\u0081\u0084\5\36\20\2\u0082\u0084\5\60\31\2\u0083\177\3\2\2"+
		"\2\u0083\u0080\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084\13"+
		"\3\2\2\2\u0085\u0086\t\2\2\2\u0086\r\3\2\2\2\u0087\u008d\5\24\13\2\u0088"+
		"\u008d\5\20\t\2\u0089\u008d\5\30\r\2\u008a\u008d\5\32\16\2\u008b\u008d"+
		"\5\22\n\2\u008c\u0087\3\2\2\2\u008c\u0088\3\2\2\2\u008c\u0089\3\2\2\2"+
		"\u008c\u008a\3\2\2\2\u008c\u008b\3\2\2\2\u008d\17\3\2\2\2\u008e\u008f"+
		"\7\25\2\2\u008f\u0090\5<\37\2\u0090\21\3\2\2\2\u0091\u0092\7\6\2\2\u0092"+
		"\u0093\5:\36\2\u0093\23\3\2\2\2\u0094\u0095\5\26\f\2\u0095\u0096\5:\36"+
		"\2\u0096\25\3\2\2\2\u0097\u0098\t\3\2\2\u0098\27\3\2\2\2\u0099\u009a\7"+
		"\31\2\2\u009a\u009b\5\66\34\2\u009b\31\3\2\2\2\u009c\u009d\5\34\17\2\u009d"+
		"\u009e\5\66\34\2\u009e\33\3\2\2\2\u009f\u00a0\t\4\2\2\u00a0\35\3\2\2\2"+
		"\u00a1\u00a5\5 \21\2\u00a2\u00a5\5$\23\2\u00a3\u00a5\5(\25\2\u00a4\u00a1"+
		"\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a3\3\2\2\2\u00a5\37\3\2\2\2\u00a6"+
		"\u00a7\5\"\22\2\u00a7\u00a8\5:\36\2\u00a8\u00a9\7\4\2\2\u00a9\u00aa\5"+
		":\36\2\u00aa!\3\2\2\2\u00ab\u00ac\t\5\2\2\u00ac#\3\2\2\2\u00ad\u00ae\5"+
		"&\24\2\u00ae\u00af\5:\36\2\u00af\u00b0\7\4\2\2\u00b0\u00b1\5\66\34\2\u00b1"+
		"%\3\2\2\2\u00b2\u00b3\7\20\2\2\u00b3\'\3\2\2\2\u00b4\u00b8\5*\26\2\u00b5"+
		"\u00b8\5,\27\2\u00b6\u00b8\5.\30\2\u00b7\u00b4\3\2\2\2\u00b7\u00b5\3\2"+
		"\2\2\u00b7\u00b6\3\2\2\2\u00b8)\3\2\2\2\u00b9\u00ba\7\23\2\2\u00ba\u00bb"+
		"\5<\37\2\u00bb\u00bc\7\4\2\2\u00bc\u00bd\5:\36\2\u00bd+\3\2\2\2\u00be"+
		"\u00bf\7\24\2\2\u00bf\u00c0\5:\36\2\u00c0\u00c1\7\4\2\2\u00c1\u00c2\5"+
		"<\37\2\u00c2-\3\2\2\2\u00c3\u00c4\7\26\2\2\u00c4\u00c5\5<\37\2\u00c5\u00c6"+
		"\7\4\2\2\u00c6\u00c7\5\66\34\2\u00c7/\3\2\2\2\u00c8\u00c9\5\62\32\2\u00c9"+
		"\61\3\2\2\2\u00ca\u00cb\5\64\33\2\u00cb\u00cc\5:\36\2\u00cc\u00cd\7\4"+
		"\2\2\u00cd\u00ce\5:\36\2\u00ce\u00cf\7\4\2\2\u00cf\u00d0\5:\36\2\u00d0"+
		"\63\3\2\2\2\u00d1\u00d2\t\6\2\2\u00d2\65\3\2\2\2\u00d3\u00d7\58\35\2\u00d4"+
		"\u00d7\5R*\2\u00d5\u00d7\5L\'\2\u00d6\u00d3\3\2\2\2\u00d6\u00d4\3\2\2"+
		"\2\u00d6\u00d5\3\2\2\2\u00d7\67\3\2\2\2\u00d8\u00d9\5N(\2\u00d99\3\2\2"+
		"\2\u00da\u00db\t\7\2\2\u00db;\3\2\2\2\u00dc\u00dd\t\b\2\2\u00dd=\3\2\2"+
		"\2\u00de\u00df\t\t\2\2\u00df?\3\2\2\2\u00e0\u00e5\5L\'\2\u00e1\u00e5\5"+
		"B\"\2\u00e2\u00e5\5D#\2\u00e3\u00e5\5F$\2\u00e4\u00e0\3\2\2\2\u00e4\u00e1"+
		"\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e3\3\2\2\2\u00e5A\3\2\2\2\u00e6"+
		"\u00e7\7&\2\2\u00e7\u00e8\5L\'\2\u00e8C\3\2\2\2\u00e9\u00eb\5\b\5\2\u00ea"+
		"\u00e9\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00f1\7\'"+
		"\2\2\u00ed\u00ef\7\4\2\2\u00ee\u00ed\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef"+
		"\u00f0\3\2\2\2\u00f0\u00f2\5\66\34\2\u00f1\u00ee\3\2\2\2\u00f2\u00f3\3"+
		"\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4E\3\2\2\2\u00f5\u00f7"+
		"\5\b\5\2\u00f6\u00f5\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8"+
		"\u00f9\7(\2\2\u00f9\u00fa\7\67\2\2\u00faG\3\2\2\2\u00fb\u0175\5P)\2\u00fc"+
		"\u00fd\5P)\2\u00fd\u00fe\5J&\2\u00fe\u0175\3\2\2\2\u00ff\u0100\5P)\2\u0100"+
		"\u0101\5J&\2\u0101\u0102\7\4\2\2\u0102\u0103\5J&\2\u0103\u0175\3\2\2\2"+
		"\u0104\u0105\5P)\2\u0105\u0106\5J&\2\u0106\u0107\7\4\2\2\u0107\u0108\5"+
		"J&\2\u0108\u0109\7\4\2\2\u0109\u010a\5J&\2\u010a\u0175\3\2\2\2\u010b\u010c"+
		"\5P)\2\u010c\u010d\5J&\2\u010d\u010e\7\4\2\2\u010e\u010f\5J&\2\u010f\u0110"+
		"\7\4\2\2\u0110\u0111\5J&\2\u0111\u0112\7\4\2\2\u0112\u0113\5J&\2\u0113"+
		"\u0175\3\2\2\2\u0114\u0115\5P)\2\u0115\u0116\5J&\2\u0116\u0117\7\4\2\2"+
		"\u0117\u0118\5J&\2\u0118\u0119\7\4\2\2\u0119\u011a\5J&\2\u011a\u011b\7"+
		"\4\2\2\u011b\u011c\5J&\2\u011c\u011d\7\4\2\2\u011d\u011e\5J&\2\u011e\u0175"+
		"\3\2\2\2\u011f\u0120\5P)\2\u0120\u0121\5J&\2\u0121\u0122\7\4\2\2\u0122"+
		"\u0123\5J&\2\u0123\u0124\7\4\2\2\u0124\u0125\5J&\2\u0125\u0126\7\4\2\2"+
		"\u0126\u0127\5J&\2\u0127\u0128\7\4\2\2\u0128\u0129\5J&\2\u0129\u012a\7"+
		"\4\2\2\u012a\u012b\5J&\2\u012b\u0175\3\2\2\2\u012c\u012d\5P)\2\u012d\u012e"+
		"\5J&\2\u012e\u012f\7\4\2\2\u012f\u0130\5J&\2\u0130\u0131\7\4\2\2\u0131"+
		"\u0132\5J&\2\u0132\u0133\7\4\2\2\u0133\u0134\5J&\2\u0134\u0135\7\4\2\2"+
		"\u0135\u0136\5J&\2\u0136\u0137\7\4\2\2\u0137\u0138\5J&\2\u0138\u0139\7"+
		"\4\2\2\u0139\u013a\5J&\2\u013a\u0175\3\2\2\2\u013b\u013c\5P)\2\u013c\u013d"+
		"\5J&\2\u013d\u013e\7\4\2\2\u013e\u013f\5J&\2\u013f\u0140\7\4\2\2\u0140"+
		"\u0141\5J&\2\u0141\u0142\7\4\2\2\u0142\u0143\5J&\2\u0143\u0144\7\4\2\2"+
		"\u0144\u0145\5J&\2\u0145\u0146\7\4\2\2\u0146\u0147\5J&\2\u0147\u0148\7"+
		"\4\2\2\u0148\u0149\5J&\2\u0149\u014a\7\4\2\2\u014a\u014b\5J&\2\u014b\u0175"+
		"\3\2\2\2\u014c\u014d\5P)\2\u014d\u014e\5J&\2\u014e\u014f\7\4\2\2\u014f"+
		"\u0150\5J&\2\u0150\u0151\7\4\2\2\u0151\u0152\5J&\2\u0152\u0153\7\4\2\2"+
		"\u0153\u0154\5J&\2\u0154\u0155\7\4\2\2\u0155\u0156\5J&\2\u0156\u0157\7"+
		"\4\2\2\u0157\u0158\5J&\2\u0158\u0159\7\4\2\2\u0159\u015a\5J&\2\u015a\u015b"+
		"\7\4\2\2\u015b\u015c\5J&\2\u015c\u015d\7\4\2\2\u015d\u015e\5J&\2\u015e"+
		"\u0175\3\2\2\2\u015f\u0160\5P)\2\u0160\u0161\5J&\2\u0161\u0162\7\4\2\2"+
		"\u0162\u0163\5J&\2\u0163\u0164\7\4\2\2\u0164\u0165\5J&\2\u0165\u0166\7"+
		"\4\2\2\u0166\u0167\5J&\2\u0167\u0168\7\4\2\2\u0168\u0169\5J&\2\u0169\u016a"+
		"\7\4\2\2\u016a\u016b\5J&\2\u016b\u016c\7\4\2\2\u016c\u016d\5J&\2\u016d"+
		"\u016e\7\4\2\2\u016e\u016f\5J&\2\u016f\u0170\7\4\2\2\u0170\u0171\5J&\2"+
		"\u0171\u0172\7\4\2\2\u0172\u0173\5J&\2\u0173\u0175\3\2\2\2\u0174\u00fb"+
		"\3\2\2\2\u0174\u00fc\3\2\2\2\u0174\u00ff\3\2\2\2\u0174\u0104\3\2\2\2\u0174"+
		"\u010b\3\2\2\2\u0174\u0114\3\2\2\2\u0174\u011f\3\2\2\2\u0174\u012c\3\2"+
		"\2\2\u0174\u013b\3\2\2\2\u0174\u014c\3\2\2\2\u0174\u015f\3\2\2\2\u0175"+
		"I\3\2\2\2\u0176\u017c\5> \2\u0177\u017c\5:\36\2\u0178\u017c\5V,\2\u0179"+
		"\u017c\5X-\2\u017a\u017c\5\66\34\2\u017b\u0176\3\2\2\2\u017b\u0177\3\2"+
		"\2\2\u017b\u0178\3\2\2\2\u017b\u0179\3\2\2\2\u017b\u017a\3\2\2\2\u017c"+
		"K\3\2\2\2\u017d\u017e\79\2\2\u017eM\3\2\2\2\u017f\u0180\7\61\2\2\u0180"+
		"O\3\2\2\2\u0181\u0182\7\62\2\2\u0182Q\3\2\2\2\u0183\u0184\7\63\2\2\u0184"+
		"S\3\2\2\2\u0185\u0186\7\66\2\2\u0186U\3\2\2\2\u0187\u0188\7\67\2\2\u0188"+
		"W\3\2\2\2\u0189\u018a\78\2\2\u018aY\3\2\2\2\27[`eimqsvz\u0083\u008c\u00a4"+
		"\u00b7\u00d6\u00e4\u00ea\u00ee\u00f3\u00f6\u0174\u017b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}