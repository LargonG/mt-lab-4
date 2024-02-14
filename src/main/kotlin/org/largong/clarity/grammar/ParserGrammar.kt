package org.largong.clarity.grammar

import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.rule.ParserRule

data class ParserGrammar(
    val start: ParserRule,
    override val rules: Map<String, List<ParserRule>>
) : Grammar<Atom, ParserRule>