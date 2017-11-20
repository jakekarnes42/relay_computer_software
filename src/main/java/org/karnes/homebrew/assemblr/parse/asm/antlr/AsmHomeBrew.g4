grammar AsmHomeBrew;

program
   : (line? EOL) +
   ;

line
   : comment                        //Only a comment
   | labelDefinition  comment?      //A label definition by itself on a line.
   | instruction comment?           //Any of the instructions
   | assemblerDirective comment?    //One of the assembler directives
   | macro comment?                 //A reference to a macro (macro definitions handled elsewhere)
   ;

//One of the instructions to be translated into binary to execute.
instruction
   : labelDefinition? operation comment?
   ;

//A label definition that the symbol resolver will need to recognize
labelDefinition
   : label ':'
   ;

//One of the valid operations in the ISA
operation
   : noArgOperation
   | unaryOperation
   | binaryOperation
   | ternaryOperation
   ;

//No additional arguments needed.
noArgOperation
   : NOP
   | HALT
   | TIN
   | TOUT
   ;

//One argument needed
unaryOperation
   : ioOperation
   | returnOperation
   | jumpOperation
   | clearOperation
   ;

returnOperation
   : RET stackRegister
   ;

clearOperation
   : CLR register
   ;

ioOperation
   : ioOpcode register
   ;

ioOpcode
   : WRDIN
   | WRDOUT
   ;

jumpOperation
   : jumpOpcode value
   ;

jumpOpcode
   : JMP
   | JZ
   | JNZ
   | JS
   | JNS
   | JC
   | JNC
   | JO
   | JNO
   ;

// Two arguments needed
binaryOperation
   : aluBinaryOperation
   | binaryRegRegOperation
   | binaryRegValOperation
   | stackOperation
   ;

aluBinaryOperation
    : aluBinaryOpcode register ',' register
    ;

aluBinaryOpcode
    : INC
    | DEC
    | NOT
    ;


binaryRegRegOperation
    : binaryRegRegOpCode register ',' register
    ;

binaryRegRegOpCode
   : MOV
   | STORE
   | FETCH
   ;

binaryRegValOperation
   : binaryRegValOpCode register ',' value
   ;

binaryRegValOpCode
   : LOAD
   ;

stackOperation
   : pushOperation
   | popOperation
   | callOperation
   ;

pushOperation
   : PUSH stackRegister ',' register
   ;

popOperation
   : POP register ',' stackRegister
   ;

callOperation
   : CALL stackRegister ',' value
   ;


// Three arguments needed
ternaryOperation
    : aluTernaryOperation
    ;


aluTernaryOperation
   : aluTernaryOpcode register ',' register  ',' register
   ;

aluTernaryOpcode
   : ADD
   | AND
   | OR
   | XOR
   | CMP
   | SUB
   ;

// A value should result in a 16-bit value when resolved as a symbol or computed by the assembler.
value
   : label
   | number
   | jsExpression
   ;

// Not just any name, but one specifically that should get picked up by the symbol resolver.
label
   : name
   ;

//All of the valid registers.
register
   : AX
   | BX
   | CX
   | DX
   | EX
   | SP
   | RP
   | PC
   ;

//All of the stack registers.
stackRegister
   : SP
   | RP
   ;

//All of the valid opcodes.
opcode
   : MOV
   | CLR
   | ADD
   | INC
   | DEC
   | AND
   | OR
   | XOR
   | NOT
   | CMP
   | SUB
   | LOAD
   | FETCH
   | STORE
   | PUSH
   | POP
   | RET
   | CALL
   | WRDIN
   | WRDOUT
   | JMP
   | JZ
   | JNZ
   | JS
   | JNS
   | JC
   | JNC
   | JO
   | JNO
   | NOP
   | HALT
   | TIN
   | TOUT
   ;


assemblerDirective
   : jsExpression
   | assemblerOrgDirective
   | assemblerWordDeclaration
   | assemblerStringDeclaration
   ;

assemblerOrgDirective
    : ORG jsExpression
    ;

assemblerWordDeclaration
    : labelDefinition? DW (','? value)+
    ;

assemblerStringDeclaration
    : labelDefinition? DS STRING
    ;

/*
 * A macro reference. This is where a macro should be expanded into. This is NOT the definition of a new macro. That is handled outsied the ANTLR grammar
 * Up to 10 parameters are accepted. Needed to list them explictly because ANTLR doesn't have the notion of {10} like regex.
 */
macro
    : macroName
    | macroName macroParamValue
    | macroName macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue
    | macroName macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue ',' macroParamValue
    ;

//The acceptable parameters into a macro.
macroParamValue
    : opcode
    | register
    | string
    | parenString
    | value
    ;

jsExpression
    : JAVASCRIPT
    ;

name
   : NAME
   ;

macroName
    : MACRO_NAME
    ;

number
   : NUMBER
   ;

comment
   : COMMENT
   ;

string
   : STRING
   ;

parenString
   : PAREN_STRING
   ;



fragment A
   : ('a' | 'A')
   ;


fragment B
   : ('b' | 'B')
   ;


fragment C
   : ('c' | 'C')
   ;


fragment D
   : ('d' | 'D')
   ;


fragment E
   : ('e' | 'E')
   ;


fragment F
   : ('f' | 'F')
   ;


fragment G
   : ('g' | 'G')
   ;


fragment H
   : ('h' | 'H')
   ;


fragment I
   : ('i' | 'I')
   ;


fragment J
   : ('j' | 'J')
   ;


fragment K
   : ('k' | 'K')
   ;


fragment L
   : ('l' | 'L')
   ;


fragment M
   : ('m' | 'M')
   ;


fragment N
   : ('n' | 'N')
   ;


fragment O
   : ('o' | 'O')
   ;


fragment P
   : ('p' | 'P')
   ;


fragment Q
   : ('q' | 'Q')
   ;


fragment R
   : ('r' | 'R')
   ;


fragment S
   : ('s' | 'S')
   ;


fragment T
   : ('t' | 'T')
   ;


fragment U
   : ('u' | 'U')
   ;


fragment V
   : ('v' | 'V')
   ;


fragment W
   : ('w' | 'W')
   ;


fragment X
   : ('x' | 'X')
   ;


fragment Y
   : ('y' | 'Y')
   ;


fragment Z
   : ('z' | 'Z')
   ;

/*
* opcodes defined
*/
MOV: M O V ;
CLR: C L R ;
ADD: A D D ;
INC: I N C ;
DEC: D E C ;
AND: A N D ;
OR: O R ;
XOR: X O R ;
NOT: N O T ;
CMP: C M P;
SUB: S U B;
LOAD: L O A D;
FETCH: F E T C H ;
STORE: S T O R E ;
PUSH: P U S H ;
POP: P O P ;
RET: R E T;
CALL: C A L L ;
WRDIN: W R D I N ;
WRDOUT: W R D O U T ;
JMP: J M P ;
JZ: J Z ;
JNZ: J N Z ;
JS: J S ;
JNS: J N S;
JC: J C;
JNC: J N C ;
JO: J O ;
JNO: J N O ;
NOP: N O P ;
HALT: H A L T ;
TIN: T I N ;
TOUT: T O U T ;

// Assembler directive ops
ORG: O R G ;
DW: D W ;
DS: D S ;

/*
 * Registers
 */
AX
   : A X
   ;

BX
   : B X
   ;

CX
   : C X
   ;

DX
   : D X
   ;

EX
   : E X
   ;
SP
   : S P
   ;

RP
   : R P
   ;

PC
   : P C
   ;


NAME
   : [a-zA-Z] [a-zA-Z0-9_]*
   ;

MACRO_NAME
   : '$' [a-zA-Z0-9_]+
   ;

NUMBER
   : DECIMAL
   | HEX
   ;
DECIMAL
   : '-'? [0-9] +
   ;
HEX
   : '-'? [0] X [0-9a-fA-F] +
   ;
COMMENT
   : ';' ~ [\r\n]* -> skip
   ;
STRING
   : '"' ~ [\r\n]* '"'
   ;

PAREN_STRING
   : '(' ~ [\r\n]* ')'
   ;

JAVASCRIPT
    : '{' .*? '}'
    ;
EOL
   : '\r'? '\n'
   ;
WS
   : [ \t] -> skip
   ;