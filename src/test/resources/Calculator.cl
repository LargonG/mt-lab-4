package calculator.grammar;

expr: sum exprCont;
exprCont: ADD sum exprCont;
exprCont: SUB sum exprCont;
exprCont: ;
sum: atom sumCont;
sumCont: MUL atom sumCont;
sumCont: DIV atom sumCont;
sumCont: ;
atom: OPEN expr CLOSE;
atom: NUMBER;

OPEN: '(';
CLOSE: ')';
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
NUMBER: "[1-9][0-9]*|[0]";
SPACE: "[ \t\r\n]+" -> skip;