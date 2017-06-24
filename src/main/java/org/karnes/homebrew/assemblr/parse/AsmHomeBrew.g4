grammar AsmHomeBrew;

prog
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
   ;

noArgOperation
   : NOP
   | HALT
   ;

unaryOperation
   : aluOperation
   | ioOperation
   | jumpOperation
   ;

binaryOperation
   : movOperation
   | memoryOperation
   | stackOperation
   | callOperation
   ;

aluOperation
   : aluOpcode aluDestinationRegister
   ;

aluOpcode
   : ADD
   | INC
   | AND
   | OR
   | XOR
   | NOT
   | ROL
   ;


ioOperation
   : ioOpcode register
   ;

ioOpcode
    : BYIN
    | BYOUT
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


movOperation
   : MOV register ',' register
   ;

memoryOperation
   : loadOperation
   | storeOperation
   ;

loadOperation
   : LOAD register ',' value
   ;

storeOperation
   : STORE register ',' register
   ;

stackOperation
   : pushOperation
   | popOperation
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
   | IP
   | SP
   | RP
   | PC
   ;

aluDestinationRegister
   : AX
   | DX
   ;

stackRegister
   : SP
   | RP
   ;

assemblerDirective
   : jsExpression
   | assemblerOrgDirective
   | assemblerByteDeclaration
   | assemblerWordDeclaration
   ;

assemblerOrgDirective
    : ORG jsExpression
    ;

assemblerByteDeclaration
    : DB value
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
   | AND
   | OR
   | XOR
   | NOT
   | ROL
   | LOAD
   | STORE
   | PUSH
   | POP
   | CALL
   | BYIN
   | BYOUT
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
AND: A N D ;
OR: O R ;
XOR: X O R ;
NOT: N O T ;
ROL: R O L ;
LOAD: L O A D ;
STORE: S T O R E ;
PUSH: P U S H ;
POP: P O P ;
CALL: C A L L ;
BYIN: B Y I N ;
BYOUT: B Y O U T ;
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

IP
   : I P
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

DB
   : D B
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