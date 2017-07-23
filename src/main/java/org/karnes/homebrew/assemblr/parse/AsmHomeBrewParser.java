// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse;
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
		T__0=1, T__1=2, MOV=3, ADD=4, INC=5, DEC=6, AND=7, OR=8, XOR=9, NOT=10, 
		ROL=11, CMP=12, SUB=13, LOAD=14, LOADI=15, STORE=16, PUSH=17, POP=18, 
		RET=19, CALL=20, WRDIN=21, WRDOUT=22, JMP=23, JZ=24, JNZ=25, JNEG=26, 
		JNNEG=27, JC=28, JNC=29, JO=30, JNO=31, NOP=32, HALT=33, ORG=34, AX=35, 
		BX=36, CX=37, DX=38, EX=39, SP=40, RP=41, PC=42, DW=43, NAME=44, NUMBER=45, 
		DECIMAL=46, HEX=47, COMMENT=48, STRING=49, JAVASCRIPT=50, EOL=51, WS=52;
	public static final int
		RULE_program = 0, RULE_line = 1, RULE_instruction = 2, RULE_lbl = 3, RULE_operation = 4, 
		RULE_noArgOperation = 5, RULE_unaryOperation = 6, RULE_ioOperation = 7, 
		RULE_retOperation = 8, RULE_ioOpcode = 9, RULE_jumpOperation = 10, RULE_jumpOpcode = 11, 
		RULE_binaryOperation = 12, RULE_binaryRegRegOperation = 13, RULE_binaryRegRegOpCode = 14, 
		RULE_binaryRegValOperation = 15, RULE_binaryRegValOpCode = 16, RULE_stackOperation = 17, 
		RULE_pushOperation = 18, RULE_popOperation = 19, RULE_callOperation = 20, 
		RULE_ternaryOperation = 21, RULE_aluTernaryOperation = 22, RULE_aluTernaryOpcode = 23, 
		RULE_value = 24, RULE_label = 25, RULE_register = 26, RULE_stackRegister = 27, 
		RULE_assemblerDirective = 28, RULE_assemblerOrgDirective = 29, RULE_assemblerWordDeclaration = 30, 
		RULE_jsExpression = 31, RULE_name = 32, RULE_number = 33, RULE_comment = 34, 
		RULE_opcode = 35;
	public static final String[] ruleNames = {
		"program", "line", "instruction", "lbl", "operation", "noArgOperation", 
		"unaryOperation", "ioOperation", "retOperation", "ioOpcode", "jumpOperation", 
		"jumpOpcode", "binaryOperation", "binaryRegRegOperation", "binaryRegRegOpCode", 
		"binaryRegValOperation", "binaryRegValOpCode", "stackOperation", "pushOperation", 
		"popOperation", "callOperation", "ternaryOperation", "aluTernaryOperation", 
		"aluTernaryOpcode", "value", "label", "register", "stackRegister", "assemblerDirective", 
		"assemblerOrgDirective", "assemblerWordDeclaration", "jsExpression", "name", 
		"number", "comment", "opcode"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "MOV", "ADD", "INC", "DEC", "AND", "OR", "XOR", "NOT", 
		"ROL", "CMP", "SUB", "LOAD", "LOADI", "STORE", "PUSH", "POP", "RET", "CALL", 
		"WRDIN", "WRDOUT", "JMP", "JZ", "JNZ", "JNEG", "JNNEG", "JC", "JNC", "JO", 
		"JNO", "NOP", "HALT", "ORG", "AX", "BX", "CX", "DX", "EX", "SP", "RP", 
		"PC", "DW", "NAME", "NUMBER", "DECIMAL", "HEX", "COMMENT", "STRING", "JAVASCRIPT", 
		"EOL", "WS"
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
			setState(76); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << ROL) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << LOADI) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JNEG) | (1L << JNNEG) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT) | (1L << ORG) | (1L << DW) | (1L << NAME) | (1L << COMMENT) | (1L << JAVASCRIPT))) != 0)) {
					{
					setState(72);
					line();
					}
				}

				setState(75);
				match(EOL);
				}
				}
				setState(78); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << ROL) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << LOADI) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JNEG) | (1L << JNNEG) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT) | (1L << ORG) | (1L << DW) | (1L << NAME) | (1L << COMMENT) | (1L << JAVASCRIPT) | (1L << EOL))) != 0) );
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
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public AssemblerDirectiveContext assemblerDirective() {
			return getRuleContext(AssemblerDirectiveContext.class,0);
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
			setState(86);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case COMMENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				comment();
				}
				break;
			case MOV:
			case ADD:
			case INC:
			case DEC:
			case AND:
			case OR:
			case XOR:
			case NOT:
			case ROL:
			case CMP:
			case SUB:
			case LOAD:
			case LOADI:
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
			case JNEG:
			case JNNEG:
			case JC:
			case JNC:
			case JO:
			case JNO:
			case NOP:
			case HALT:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				instruction();
				}
				break;
			case ORG:
			case DW:
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				assemblerDirective();
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(83);
					comment();
					}
				}

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

	public static class InstructionContext extends ParserRuleContext {
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public LblContext lbl() {
			return getRuleContext(LblContext.class,0);
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
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(88);
				lbl();
				}
			}

			setState(91);
			operation();
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT) {
				{
				setState(92);
				comment();
				}
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

	public static class LblContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public LblContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lbl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterLbl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitLbl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitLbl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LblContext lbl() throws RecognitionException {
		LblContext _localctx = new LblContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_lbl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			label();
			setState(96);
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
			setState(102);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOP:
			case HALT:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				noArgOperation();
				}
				break;
			case RET:
			case WRDIN:
			case WRDOUT:
			case JMP:
			case JZ:
			case JNZ:
			case JNEG:
			case JNNEG:
			case JC:
			case JNC:
			case JO:
			case JNO:
				enterOuterAlt(_localctx, 2);
				{
				setState(99);
				unaryOperation();
				}
				break;
			case MOV:
			case INC:
			case DEC:
			case NOT:
			case ROL:
			case LOAD:
			case LOADI:
			case STORE:
			case PUSH:
			case POP:
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(100);
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
				setState(101);
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
			setState(104);
			_la = _input.LA(1);
			if ( !(_la==NOP || _la==HALT) ) {
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
		public RetOperationContext retOperation() {
			return getRuleContext(RetOperationContext.class,0);
		}
		public JumpOperationContext jumpOperation() {
			return getRuleContext(JumpOperationContext.class,0);
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
			setState(109);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WRDIN:
			case WRDOUT:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				ioOperation();
				}
				break;
			case RET:
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				retOperation();
				}
				break;
			case JMP:
			case JZ:
			case JNZ:
			case JNEG:
			case JNNEG:
			case JC:
			case JNC:
			case JO:
			case JNO:
				enterOuterAlt(_localctx, 3);
				{
				setState(108);
				jumpOperation();
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
		enterRule(_localctx, 14, RULE_ioOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			ioOpcode();
			setState(112);
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

	public static class RetOperationContext extends ParserRuleContext {
		public TerminalNode RET() { return getToken(AsmHomeBrewParser.RET, 0); }
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public RetOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_retOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterRetOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitRetOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitRetOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetOperationContext retOperation() throws RecognitionException {
		RetOperationContext _localctx = new RetOperationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_retOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(RET);
			setState(115);
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
		enterRule(_localctx, 18, RULE_ioOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
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
		public JumpOpcodeContext jumpOpcode() {
			return getRuleContext(JumpOpcodeContext.class,0);
		}
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
		enterRule(_localctx, 20, RULE_jumpOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			jumpOpcode();
			setState(120);
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
		public TerminalNode JMP() { return getToken(AsmHomeBrewParser.JMP, 0); }
		public TerminalNode JZ() { return getToken(AsmHomeBrewParser.JZ, 0); }
		public TerminalNode JNZ() { return getToken(AsmHomeBrewParser.JNZ, 0); }
		public TerminalNode JNEG() { return getToken(AsmHomeBrewParser.JNEG, 0); }
		public TerminalNode JNNEG() { return getToken(AsmHomeBrewParser.JNNEG, 0); }
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
		enterRule(_localctx, 22, RULE_jumpOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JNEG) | (1L << JNNEG) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO))) != 0)) ) {
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
		enterRule(_localctx, 24, RULE_binaryOperation);
		try {
			setState(127);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MOV:
			case INC:
			case DEC:
			case NOT:
			case ROL:
			case LOAD:
			case STORE:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				binaryRegRegOperation();
				}
				break;
			case LOADI:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				binaryRegValOperation();
				}
				break;
			case PUSH:
			case POP:
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
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
		enterRule(_localctx, 26, RULE_binaryRegRegOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			binaryRegRegOpCode();
			setState(130);
			register();
			setState(131);
			match(T__1);
			setState(132);
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
		public TerminalNode INC() { return getToken(AsmHomeBrewParser.INC, 0); }
		public TerminalNode DEC() { return getToken(AsmHomeBrewParser.DEC, 0); }
		public TerminalNode NOT() { return getToken(AsmHomeBrewParser.NOT, 0); }
		public TerminalNode ROL() { return getToken(AsmHomeBrewParser.ROL, 0); }
		public TerminalNode STORE() { return getToken(AsmHomeBrewParser.STORE, 0); }
		public TerminalNode LOAD() { return getToken(AsmHomeBrewParser.LOAD, 0); }
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
		enterRule(_localctx, 28, RULE_binaryRegRegOpCode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << INC) | (1L << DEC) | (1L << NOT) | (1L << ROL) | (1L << LOAD) | (1L << STORE))) != 0)) ) {
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
		enterRule(_localctx, 30, RULE_binaryRegValOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			binaryRegValOpCode();
			setState(137);
			register();
			setState(138);
			match(T__1);
			setState(139);
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
		public TerminalNode LOADI() { return getToken(AsmHomeBrewParser.LOADI, 0); }
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
		enterRule(_localctx, 32, RULE_binaryRegValOpCode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(LOADI);
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
		enterRule(_localctx, 34, RULE_stackOperation);
		try {
			setState(146);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PUSH:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				pushOperation();
				}
				break;
			case POP:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				popOperation();
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
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
		enterRule(_localctx, 36, RULE_pushOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(PUSH);
			setState(149);
			stackRegister();
			setState(150);
			match(T__1);
			setState(151);
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
		enterRule(_localctx, 38, RULE_popOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(POP);
			setState(154);
			register();
			setState(155);
			match(T__1);
			setState(156);
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
		enterRule(_localctx, 40, RULE_callOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(CALL);
			setState(159);
			stackRegister();
			setState(160);
			match(T__1);
			setState(161);
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
		enterRule(_localctx, 42, RULE_ternaryOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
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
		enterRule(_localctx, 44, RULE_aluTernaryOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			aluTernaryOpcode();
			setState(166);
			register();
			setState(167);
			match(T__1);
			setState(168);
			register();
			setState(169);
			match(T__1);
			setState(170);
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
		enterRule(_localctx, 46, RULE_aluTernaryOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
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
		enterRule(_localctx, 48, RULE_value);
		try {
			setState(177);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				label();
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				number();
				}
				break;
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
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
		enterRule(_localctx, 50, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
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
		enterRule(_localctx, 52, RULE_register);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
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
		enterRule(_localctx, 54, RULE_stackRegister);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
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
		enterRule(_localctx, 56, RULE_assemblerDirective);
		try {
			setState(188);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				jsExpression();
				}
				break;
			case ORG:
				enterOuterAlt(_localctx, 2);
				{
				setState(186);
				assemblerOrgDirective();
				}
				break;
			case DW:
				enterOuterAlt(_localctx, 3);
				{
				setState(187);
				assemblerWordDeclaration();
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
		enterRule(_localctx, 58, RULE_assemblerOrgDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(ORG);
			setState(191);
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
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
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
		enterRule(_localctx, 60, RULE_assemblerWordDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(DW);
			setState(194);
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
		enterRule(_localctx, 62, RULE_jsExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
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
		enterRule(_localctx, 64, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
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
		enterRule(_localctx, 66, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
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
		enterRule(_localctx, 68, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
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

	public static class OpcodeContext extends ParserRuleContext {
		public TerminalNode MOV() { return getToken(AsmHomeBrewParser.MOV, 0); }
		public TerminalNode ADD() { return getToken(AsmHomeBrewParser.ADD, 0); }
		public TerminalNode INC() { return getToken(AsmHomeBrewParser.INC, 0); }
		public TerminalNode DEC() { return getToken(AsmHomeBrewParser.DEC, 0); }
		public TerminalNode AND() { return getToken(AsmHomeBrewParser.AND, 0); }
		public TerminalNode OR() { return getToken(AsmHomeBrewParser.OR, 0); }
		public TerminalNode XOR() { return getToken(AsmHomeBrewParser.XOR, 0); }
		public TerminalNode NOT() { return getToken(AsmHomeBrewParser.NOT, 0); }
		public TerminalNode ROL() { return getToken(AsmHomeBrewParser.ROL, 0); }
		public TerminalNode CMP() { return getToken(AsmHomeBrewParser.CMP, 0); }
		public TerminalNode SUB() { return getToken(AsmHomeBrewParser.SUB, 0); }
		public TerminalNode LOAD() { return getToken(AsmHomeBrewParser.LOAD, 0); }
		public TerminalNode LOADI() { return getToken(AsmHomeBrewParser.LOADI, 0); }
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
		public TerminalNode JNEG() { return getToken(AsmHomeBrewParser.JNEG, 0); }
		public TerminalNode JNNEG() { return getToken(AsmHomeBrewParser.JNNEG, 0); }
		public TerminalNode JC() { return getToken(AsmHomeBrewParser.JC, 0); }
		public TerminalNode JNC() { return getToken(AsmHomeBrewParser.JNC, 0); }
		public TerminalNode JO() { return getToken(AsmHomeBrewParser.JO, 0); }
		public TerminalNode JNO() { return getToken(AsmHomeBrewParser.JNO, 0); }
		public TerminalNode NOP() { return getToken(AsmHomeBrewParser.NOP, 0); }
		public TerminalNode HALT() { return getToken(AsmHomeBrewParser.HALT, 0); }
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
		enterRule(_localctx, 70, RULE_opcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << ROL) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << LOADI) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JNEG) | (1L << JNNEG) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT))) != 0)) ) {
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\66\u00d1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\5\2L\n\2\3\2\6\2O\n\2\r\2\16\2P\3\3"+
		"\3\3\3\3\3\3\5\3W\n\3\5\3Y\n\3\3\4\5\4\\\n\4\3\4\3\4\5\4`\n\4\3\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\5\6i\n\6\3\7\3\7\3\b\3\b\3\b\5\bp\n\b\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\5\16\u0082\n"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3"+
		"\22\3\23\3\23\3\23\5\23\u0095\n\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\5\32\u00b4\n\32\3\33\3\33\3\34"+
		"\3\34\3\35\3\35\3\36\3\36\3\36\5\36\u00bf\n\36\3\37\3\37\3\37\3 \3 \3"+
		" \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3%\2\2&\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH\2\n\3\2\"#\3\2\27\30\3\2\31!\7"+
		"\2\5\5\7\b\f\r\20\20\22\22\5\2\6\6\t\13\16\17\3\2%,\3\2*+\3\2\5#\2\u00c0"+
		"\2N\3\2\2\2\4X\3\2\2\2\6[\3\2\2\2\ba\3\2\2\2\nh\3\2\2\2\fj\3\2\2\2\16"+
		"o\3\2\2\2\20q\3\2\2\2\22t\3\2\2\2\24w\3\2\2\2\26y\3\2\2\2\30|\3\2\2\2"+
		"\32\u0081\3\2\2\2\34\u0083\3\2\2\2\36\u0088\3\2\2\2 \u008a\3\2\2\2\"\u008f"+
		"\3\2\2\2$\u0094\3\2\2\2&\u0096\3\2\2\2(\u009b\3\2\2\2*\u00a0\3\2\2\2,"+
		"\u00a5\3\2\2\2.\u00a7\3\2\2\2\60\u00ae\3\2\2\2\62\u00b3\3\2\2\2\64\u00b5"+
		"\3\2\2\2\66\u00b7\3\2\2\28\u00b9\3\2\2\2:\u00be\3\2\2\2<\u00c0\3\2\2\2"+
		">\u00c3\3\2\2\2@\u00c6\3\2\2\2B\u00c8\3\2\2\2D\u00ca\3\2\2\2F\u00cc\3"+
		"\2\2\2H\u00ce\3\2\2\2JL\5\4\3\2KJ\3\2\2\2KL\3\2\2\2LM\3\2\2\2MO\7\65\2"+
		"\2NK\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\3\3\2\2\2RY\5F$\2SY\5\6\4"+
		"\2TV\5:\36\2UW\5F$\2VU\3\2\2\2VW\3\2\2\2WY\3\2\2\2XR\3\2\2\2XS\3\2\2\2"+
		"XT\3\2\2\2Y\5\3\2\2\2Z\\\5\b\5\2[Z\3\2\2\2[\\\3\2\2\2\\]\3\2\2\2]_\5\n"+
		"\6\2^`\5F$\2_^\3\2\2\2_`\3\2\2\2`\7\3\2\2\2ab\5\64\33\2bc\7\3\2\2c\t\3"+
		"\2\2\2di\5\f\7\2ei\5\16\b\2fi\5\32\16\2gi\5,\27\2hd\3\2\2\2he\3\2\2\2"+
		"hf\3\2\2\2hg\3\2\2\2i\13\3\2\2\2jk\t\2\2\2k\r\3\2\2\2lp\5\20\t\2mp\5\22"+
		"\n\2np\5\26\f\2ol\3\2\2\2om\3\2\2\2on\3\2\2\2p\17\3\2\2\2qr\5\24\13\2"+
		"rs\5\66\34\2s\21\3\2\2\2tu\7\25\2\2uv\58\35\2v\23\3\2\2\2wx\t\3\2\2x\25"+
		"\3\2\2\2yz\5\30\r\2z{\5\62\32\2{\27\3\2\2\2|}\t\4\2\2}\31\3\2\2\2~\u0082"+
		"\5\34\17\2\177\u0082\5 \21\2\u0080\u0082\5$\23\2\u0081~\3\2\2\2\u0081"+
		"\177\3\2\2\2\u0081\u0080\3\2\2\2\u0082\33\3\2\2\2\u0083\u0084\5\36\20"+
		"\2\u0084\u0085\5\66\34\2\u0085\u0086\7\4\2\2\u0086\u0087\5\66\34\2\u0087"+
		"\35\3\2\2\2\u0088\u0089\t\5\2\2\u0089\37\3\2\2\2\u008a\u008b\5\"\22\2"+
		"\u008b\u008c\5\66\34\2\u008c\u008d\7\4\2\2\u008d\u008e\5\62\32\2\u008e"+
		"!\3\2\2\2\u008f\u0090\7\21\2\2\u0090#\3\2\2\2\u0091\u0095\5&\24\2\u0092"+
		"\u0095\5(\25\2\u0093\u0095\5*\26\2\u0094\u0091\3\2\2\2\u0094\u0092\3\2"+
		"\2\2\u0094\u0093\3\2\2\2\u0095%\3\2\2\2\u0096\u0097\7\23\2\2\u0097\u0098"+
		"\58\35\2\u0098\u0099\7\4\2\2\u0099\u009a\5\66\34\2\u009a\'\3\2\2\2\u009b"+
		"\u009c\7\24\2\2\u009c\u009d\5\66\34\2\u009d\u009e\7\4\2\2\u009e\u009f"+
		"\58\35\2\u009f)\3\2\2\2\u00a0\u00a1\7\26\2\2\u00a1\u00a2\58\35\2\u00a2"+
		"\u00a3\7\4\2\2\u00a3\u00a4\5\62\32\2\u00a4+\3\2\2\2\u00a5\u00a6\5.\30"+
		"\2\u00a6-\3\2\2\2\u00a7\u00a8\5\60\31\2\u00a8\u00a9\5\66\34\2\u00a9\u00aa"+
		"\7\4\2\2\u00aa\u00ab\5\66\34\2\u00ab\u00ac\7\4\2\2\u00ac\u00ad\5\66\34"+
		"\2\u00ad/\3\2\2\2\u00ae\u00af\t\6\2\2\u00af\61\3\2\2\2\u00b0\u00b4\5\64"+
		"\33\2\u00b1\u00b4\5D#\2\u00b2\u00b4\5@!\2\u00b3\u00b0\3\2\2\2\u00b3\u00b1"+
		"\3\2\2\2\u00b3\u00b2\3\2\2\2\u00b4\63\3\2\2\2\u00b5\u00b6\5B\"\2\u00b6"+
		"\65\3\2\2\2\u00b7\u00b8\t\7\2\2\u00b8\67\3\2\2\2\u00b9\u00ba\t\b\2\2\u00ba"+
		"9\3\2\2\2\u00bb\u00bf\5@!\2\u00bc\u00bf\5<\37\2\u00bd\u00bf\5> \2\u00be"+
		"\u00bb\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bd\3\2\2\2\u00bf;\3\2\2\2"+
		"\u00c0\u00c1\7$\2\2\u00c1\u00c2\5@!\2\u00c2=\3\2\2\2\u00c3\u00c4\7-\2"+
		"\2\u00c4\u00c5\5\62\32\2\u00c5?\3\2\2\2\u00c6\u00c7\7\64\2\2\u00c7A\3"+
		"\2\2\2\u00c8\u00c9\7.\2\2\u00c9C\3\2\2\2\u00ca\u00cb\7/\2\2\u00cbE\3\2"+
		"\2\2\u00cc\u00cd\7\62\2\2\u00cdG\3\2\2\2\u00ce\u00cf\t\t\2\2\u00cfI\3"+
		"\2\2\2\16KPVX[_ho\u0081\u0094\u00b3\u00be";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}