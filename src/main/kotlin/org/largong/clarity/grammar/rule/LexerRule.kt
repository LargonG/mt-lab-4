package org.largong.clarity.grammar.rule

import org.largong.clarity.grammar.atoms.Atom

data class LexerRule(
    override val name: String,
    val atom: Atom
) : Rule
