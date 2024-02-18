package expression.grammar;

expr: mul exprCont;

exprCont: ADD mul exprCont;
exprCont: SUB mul exprCont;
exprCont: ;

mul: atom mulCont;

mulCont: MUL atom mulCont;
mulCont: ;

atom: SUB atom;
atom: NUMBER;
atom: OPEN expr CLOSE;

atom: NAME apply;

apply: OPEN args CLOSE;
apply: ;

args: expr argsCont;
args: ;

argsCont: COMA expr argsCont;
argsCont: ;

OPEN: '(';
CLOSE: ')';
COMA: ',';
ADD: '+';
SUB: '-';
MUL: '*';
NUMBER: "[1-9][0-9]*|0";
NAME: "[a-zA-Z_][a-zA-Z0-9_]*";
SPACE: "[ \t\r\n]" -> skip;
