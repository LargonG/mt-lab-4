package org.example.clarity.grammar

import clarity.grammar.atoms.RegexAtom
import clarity.grammar.rule.LexerRule

class LexerGrammar(override val rules: Map<String, List<LexerRule>>) : Grammar<RegexAtom, LexerRule>