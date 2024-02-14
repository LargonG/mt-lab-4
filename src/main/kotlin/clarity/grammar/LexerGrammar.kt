package org.example.clarity.grammar

import clarity.grammar.atoms.RegexAtom
import org.example.clarity.grammar.rule.LexerRule

data class LexerGrammar(
    override val rules: Map<String, List<LexerRule>>
) : Grammar<RegexAtom, LexerRule>