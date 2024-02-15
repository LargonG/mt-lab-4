package org.largong.clarity.grammar

import org.largong.clarity.grammar.rule.ParserRule
import org.largong.clarity.grammar.scripts.Declaration

data class ParserGrammar(
    val start: ParserRule,
    override val rules: Map<String, List<ParserRule>>,
    val arguments: Map<String, Declaration>
) : Grammar<ParserRule>