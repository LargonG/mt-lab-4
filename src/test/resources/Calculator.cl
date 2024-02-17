package calculator.grammar;

expr [] returns [result: Double];
expr: sum exprCont <sum[0].result> { result.result = exprCont[0].result };

exprCont [left: Double] returns [result: Double];
exprCont: ADD sum exprCont <sum[0].result> { result.result = left + exprCont[0].result };
exprCont: SUB sum exprCont <sum[0].result> { result.result = left - exprCont[0].result };
exprCont: { result.result = left };

sum [] returns [result: Double];
sum: atom sumCont <atom[0].result> { result.result = sumCont[0].result };

sumCont [left: Double] returns [result: Double];
sumCont: MUL atom sumCont <atom[0].result> { result.result = left * sumCont[0].result };
sumCont: DIV atom sumCont <atom[0].result> { result.result = left / sumCont[0].result };
sumCont: { result.result = left };

atom [] returns [result: Double];
atom: OPEN expr CLOSE { result.result = expr[0].result };
atom: NUMBER { result.result = Integer.parseInt(NUMBER[0].text).toDouble() };

OPEN: '(';
CLOSE: ')';
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
NUMBER: "[1-9][0-9]*|[0]";
SPACE: "[ \t\r\n]+" -> skip;