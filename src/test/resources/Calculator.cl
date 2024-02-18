package calculator.grammar;

expr [] returns [result: Double];
expr: sum <0.0, true> exprCont <sum[0].result> { result.result = exprCont[0].result };

exprCont [left: Double] returns [result: Double];
exprCont: ADD sum <left, true> exprCont <sum[0].result> { result.result = exprCont[0].result };
exprCont: SUB sum <left, false> exprCont <sum[0].result> { result.result = exprCont[0].result };
exprCont: { result.result = left };

sum [left: Double, add: Boolean] returns [result: Double];
sum: atom <1.0, true> sumCont <atom[0].result> {
    result.result =
    if (add)
        left + sumCont[0].result
    else
        left - sumCont[0].result
};

sumCont [left: Double] returns [result: Double];
sumCont: MUL atom <left, true> sumCont <atom[0].result> { result.result = sumCont[0].result };
sumCont: DIV atom <left, false> sumCont <atom[0].result> { result.result = sumCont[0].result };
sumCont: { result.result = left };

atom [left: Double, mul: Boolean] returns [result: Double];
atom: OPEN expr CLOSE {
    result.result =
    if (mul)
        left * expr[0].result
    else
        left / expr[0].result
};
atom: NUMBER {
    result.result =
    if (mul)
        left * Integer.parseInt(NUMBER[0].text).toDouble()
    else
        left / Integer.parseInt(NUMBER[0].text).toDouble()
};

OPEN: '(';
CLOSE: ')';
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
NUMBER: "[1-9][0-9]*|[0]";
SPACE: "[ \t\r\n]+" -> skip;