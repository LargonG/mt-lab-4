grammar Clarity;

@header {
    package org.largong;
}

gram: rules EOF;

rules: ruleSpec*; // сюда нужно прикрепить ещё grammar и header
packageName: 'package' (LOWERCASE DOT)* LOWERCASE SEMI | ;
ruleSpec: (lexerRuleSpec | parserRuleSpec) SEMI;

lexerRuleSpec: lexerRuleDeclaration COLON lexerExpression;
parserRuleSpec: parserRuleDeclaration (paramsBlock? | COLON parserExpression code?);

lexerRuleDeclaration: UPPERCASE;
parserRuleDeclaration: LOWERCASE;

lexerRuleName: UPPERCASE;
parserRuleName: LOWERCASE;

parserExpression: notEmptyParserExpression+ | empty;

notEmptyParserExpression:
    parserRuleName
    | lexerRuleName
    ;
empty: ;

lexerExpression: (lexerString | lexerRegex)+;

lexerString: STRING;
lexerRegex: REGEX;

name: LOWERCASE | UPPERCASE;

paramsBlock: params returnsParams?;

params: '[' (param COMA)* param ']';
param: name COLON TYPE;
code: CODE;

returnsParams: 'returns' params;

fragment CAPITAL: [A-Z];
fragment LOWER: [a-z];
fragment DIGIT: [0-9];
fragment LETTER: CAPITAL | LOWER;
fragment LETTER_OR_DIGIT: LETTER | DIGIT;
fragment BACKSLESH: '\\';
fragment QUAT: '\'';
fragment DQUAT: '"';

TYPE: 'Int' | 'Long' | 'Float' | 'Double' | 'Char' | 'String' | 'Boolean' | 'Any' | 'Nothing';
CODE: '{' .*? '}';
REGEX: DQUAT (~["] | BACKSLESH DQUAT)*? DQUAT;
STRING: QUAT (~['] | BACKSLESH QUAT)*? QUAT;
LOWERCASE: LOWER LETTER_OR_DIGIT*;
UPPERCASE: CAPITAL LETTER_OR_DIGIT*;
SPACE: (' ' | '\t' | '\r' | '\n')+ -> skip;
DOT: '.';
COMA: ',';
COLON: ':';
SEMI: ';';