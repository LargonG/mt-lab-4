package calculator.grammar;

expr [] returns [result: Double];
expr: sum <0.0, true> exprCont <sum[0].result> { result.result = exprCont[0].result };

exprCont [left: Double] returns [result: Double];
exprCont: ADD sum <left, true> exprCont <sum[0].result> { result.result = exprCont[0].result };
exprCont: SUB sum <left, false> exprCont <sum[0].result> { result.result = exprCont[0].result };
exprCont: { result.result = left };

sum [left: Double, add: Boolean] returns [result: Double];
sum: atom <1.0, "mul"> sumAfter <atom[0].result> {
    result.result = left +
    if (add)
        + sumAfter[0].result
    else
        - sumAfter[0].result
};

sumAfter [leftAtom: Double] returns [result: Double];
sumAfter: LOG sum <0.0, true> {
    result.result = kotlin.math.log(leftAtom, sum[0].result)
};
sumAfter: sumCont <leftAtom> {
    result.result = sumCont[0].result
};

sumCont [left: Double] returns [result: Double];
sumCont: MUL atom <left, "mul"> sumAfter <atom[0].result> { result.result = sumAfter[0].result };
sumCont: DIV atom <left, "div"> sumAfter <atom[0].result> { result.result = sumAfter[0].result };
sumCont: { result.result = left };

atom [left: Double, op: String] returns [result: Double];
atom: OPEN expr CLOSE {
    result.result =
    if (op == "mul")
        left * expr[0].result
    else
        left / expr[0].result
};
atom: NUMBER {
    result.result =
    if (op == "mul")
        left * Integer.parseInt(NUMBER[0].text).toDouble()
    else
        left / Integer.parseInt(NUMBER[0].text).toDouble()
};

OPEN: '(';
CLOSE: ')';
ADD: '+';
SUB: '-';
MUL: '*';
LOG: '//';
DIV: '/';
NUMBER: "[1-9][0-9]*|[0]";
SPACE: "[ \t\r\n]+" -> skip;