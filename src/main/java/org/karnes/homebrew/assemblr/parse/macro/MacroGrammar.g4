grammar MacroGrammar;

program
   : (line? EOL) +
   ;

line
   : startMacro
   | endMacro
   | other
   ;

startMacro
    : macroStartTag macroName (','? param)+ comment?
    ;

endMacro
    : macroEndTag comment?
    ;

other
    : ANY_TEXT
    ;


macroName
    : '$'~NAME
    ;

param
   : name
   ;



name
   : NAME
   ;

comment
   : COMMENT
   ;

macroStartTag
   : MACRO
   ;

macroEndTag
   : ENDM
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

MACRO: M A C R O ;

ENDM: E N D M ;

NAME
   : [@#%a-zA-Z] [a-zA-Z0-9_]*
   ;

ANY_TEXT
    : ~ [\r\n]+
    ;

COMMENT
   : ';' ~ [\r\n]* -> skip
   ;
EOL
   : '\r'? '\n'
   ;
WS
   : [ \t] -> skip
   ;