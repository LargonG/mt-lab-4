package org.example.clarity.grammar

import clarity.grammar.atoms.Atom
import clarity.grammar.rule.ParserRule

data class ParserGrammar(
    val start: ParserRule,
    override val rules: Map<String, List<ParserRule>>
) : Grammar<Atom, ParserRule>