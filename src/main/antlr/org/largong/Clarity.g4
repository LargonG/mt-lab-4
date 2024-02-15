grammar Clarity;

@header {
    package org.largong;
}

gram: rules EOF;

rules: ruleSpec*; // сюда нужно прикрепить ещё grammar и header
packageName: 'package' (LOWERCASE DOT)* LOWERCASE SEMI | ;
ruleSpec: (lexerRuleSpec | parserRuleSpec) SEMI;

lexerRuleSpec: lexerRuleDeclaration COLON lexerExpression;
parserRuleSpec: parserRuleDeclaration (declaration? | COLON parserExpression code?);

lexerRuleDeclaration: UPPERCASE;
parserRuleDeclaration: LOWERCASE;

lexerRuleName: UPPERCASE;
parserRuleName: LOWERCASE;

parserRuleApply: parserRuleName apply?;

parserExpression: notEmptyParserExpression+ | empty;

notEmptyParserExpression
    : parserRuleApply
    | lexerRuleName
    ;
empty: ;

lexerExpression: (lexerString | lexerRegex)+;

lexerString: STRING;
lexerRegex: REGEX;

name: LOWERCASE | UPPERCASE;
varname: LOWERCASE | UPPERCASE | VARNAME;

declaration: args returnArgs?;

args: '[' (arg COMA)* arg? ']';
arg: name COLON TYPE;
code: CODE;

apply: '[' (varname COMA)* varname? ']';

returnArgs: 'returns' args;

fragment CAPITAL: [A-Z];
fragment LOWER: [a-z];
fragment DIGIT: [0-9];
fragment LETTER: CAPITAL | LOWER;
fragment ANY_LETTER: LETTER | '_';
fragment LETTER_OR_DIGIT: LETTER | DIGIT;
fragment SYMBOL: ANY_LETTER | '[' | ']';
fragment BACKSLESH: '\\';
fragment QUAT: '\'';
fragment DQUAT: '"';

TYPE: 'Int' | 'Long' | 'Float' | 'Double' | 'Char' | 'String' | 'Boolean' | 'Any' | 'Nothing';
CODE: '{' .*? '}';
REGEX: DQUAT (~["] | BACKSLESH DQUAT)* DQUAT;
STRING: QUAT (~['] | BACKSLESH QUAT)* QUAT;
VARNAME: ANY_LETTER SYMBOL* ('.' ANY_LETTER SYMBOL*)+;
LOWERCASE: LOWER LETTER_OR_DIGIT*;
UPPERCASE: CAPITAL LETTER_OR_DIGIT*;
SPACE: (' ' | '\t' | '\r' | '\n')+ -> skip;
DOT: '.';
COMA: ',';
COLON: ':';
SEMI: ';';