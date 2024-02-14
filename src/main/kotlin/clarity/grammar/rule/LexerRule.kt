package org.example.clarity.grammar.rule

import clarity.grammar.atoms.RegexAtom

data class LexerRule(
    override val name: String,
    override val atoms: List<RegexAtom>
) : Rule<RegexAtom>
