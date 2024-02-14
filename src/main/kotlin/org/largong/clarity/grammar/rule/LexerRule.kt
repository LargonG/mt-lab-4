package org.largong.clarity.grammar.rule

import org.largong.clarity.grammar.atoms.RegexAtom

data class LexerRule(
    override val name: String,
    override val atoms: List<RegexAtom>
) : Rule<RegexAtom>
