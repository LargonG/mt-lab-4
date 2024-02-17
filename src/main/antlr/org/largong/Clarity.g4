grammar Clarity;

@header {
    package org.largong;
}

gram: packageName rules EOF;

rules: ruleSpec*; // сюда нужно прикрепить ещё grammar и header
packageName: 'package' ((LOWERCASE DOT)* LOWERCASE | VARNAME) SEMI | ;
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

lexerExpression: (lexerString | lexerRegex) ('->' action)?;

action: SKIP_WORD;

lexerString: STRING;
lexerRegex: REGEX;

name: LOWERCASE | UPPERCASE;

declaration: args returnArgs?;

args: '[' (arg COMA)* arg? ']';
arg: name COLON TYPE;
code: CODE;

apply: VARNAME;

returnArgs: 'returns' args;

fragment CAPITAL: [A-Z];
fragment LOWER: [a-z];
fragment DIGIT: [0-9];
fragment LETTER: CAPITAL | LOWER;
fragment ANY_LETTER: LETTER | '_';
fragment LETTER_OR_DIGIT: LETTER | DIGIT;
fragment CHAR: ANY_LETTER | LETTER_OR_DIGIT;
fragment BACKSLESH: '\\';
fragment QUAT: '\'';
fragment DQUAT: '"';

SKIP_WORD: 'skip';
TYPE: 'Int' | 'Long' | 'Float' | 'Double' | 'Char' | 'String' | 'Boolean';
CODE: '{' .*? '}';
REGEX: DQUAT (~["] | BACKSLESH DQUAT)* DQUAT;
STRING: QUAT (~['] | BACKSLESH QUAT)* QUAT;
LOWERCASE: LOWER LETTER_OR_DIGIT*;
UPPERCASE: CAPITAL LETTER_OR_DIGIT*;
VARNAME: '<' .*? '>';
SPACE: (' ' | '\t' | '\r' | '\n')+ -> skip;
DOT: '.';
COMA: ',';
COLON: ':';
SEMI: ';';
ARROW: '->';