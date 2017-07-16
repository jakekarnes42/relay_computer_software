grammar AsmHomeBrew;

program
   : (line? EOL) +
   ;

line
   : comment
   | instruction
   | assemblerDirective comment?
   ;

instruction
   : lbl? operation comment?
   ;

lbl
   : label ':'
   ;

operation
   : noArgOperation
   | unaryOperation
   | binaryOperation
   | ternaryOperation
   ;

noArgOperation
   : NOP
   | HALT
   ;

unaryOperation
   : ioOperation
   | jumpOperation
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
   | JNEG
   | JNNEG
   | JC
   | JNC
   | JO
   | JNO
   ;


binaryOperation
   : binaryRegRegOperation
   | binaryRegValOperation
   | stackOperation
   ;


binaryRegRegOperation
    : binaryRegRegOpCode register ',' register
    ;

binaryRegRegOpCode
   : MOV
   | INC
   | DEC
   | NOT
   | ROL
   | STORE
   | LOAD
   ;

binaryRegValOperation
   : binaryRegValOpCode register ',' value
   ;

binaryRegValOpCode
   : LOADI
   ;

stackOperation
   : pushOperation
   | popOperation
   | callOperation
   | callIOperation
   ;

pushOperation
   : PUSH stackRegister ',' register
   ;

popOperation
   : POP register ',' stackRegister
   ;

callIOperation
   : CALLI stackRegister ',' value
   ;

callOperation
   : CALL stackRegister ',' register
   ;


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



value
   : label
   | number
   | jsExpression
   ;

label
   : name
   ;


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


stackRegister
   : SP
   | RP
   ;

assemblerDirective
   : jsExpression
   | assemblerOrgDirective
   | assemblerWordDeclaration
   ;

assemblerOrgDirective
    : ORG jsExpression
    ;

assemblerWordDeclaration
    : DW value
    ;

jsExpression
    : JAVASCRIPT
    ;


name
   : NAME
   ;

number
   : NUMBER
   ;

comment
   : COMMENT
   ;

opcode
   : MOV
   | ADD
   | INC
   | DEC
   | AND
   | OR
   | XOR
   | NOT
   | ROL
   | CMP
   | SUB
   | LOAD
   | LOADI
   | STORE
   | PUSH
   | POP
   | CALL
   | CALLI
   | WRDIN
   | WRDOUT
   | JMP
   | JZ
   | JNZ
   | JNEG
   | JNNEG
   | JC
   | JNC
   | JO
   | JNO
   | NOP
   | HALT
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
* opcodes
*/

MOV: M O V ;
ADD: A D D ;
INC: I N C ;
DEC: D E C ;
AND: A N D ;
OR: O R ;
XOR: X O R ;
NOT: N O T ;
ROL: R O L;
CMP: C M P;
SUB: S U B;
LOAD: L O A D ;
LOADI: L O A D I;
STORE: S T O R E ;
PUSH: P U S H ;
POP: P O P ;
CALL: C A L L ;
CALLI: C A L L I;
WRDIN: W R D I N ;
WRDOUT: W R D O U T ;
JMP: J M P ;
JZ: J Z ;
JNZ: J N Z ;
JNEG: J N E G ;
JNNEG: J N N E G;
JC: J C;
JNC: J N C ;
JO: J O ;
JNO: J N O ;
NOP: N O P ;
HALT: H A L T ;

// Assembler directive ops
ORG: O R G ;

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

DW
   : D W
   ;

NAME
   : [a-zA-Z] [a-zA-Z0-9]*
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
   : '"' ~ ["]* '"'
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