package org.largong.clarity.grammar.builder

import org.largong.clarity.grammar.ParserGrammar
import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.builder.rule.ParserRuleBuilder
import org.largong.clarity.grammar.rule.ParserRule

class ParserBuilder : GrammarBuilder<Atom, ParserRuleBuilder>() {
    override fun defaultValue(name: String): ParserRuleBuilder =
        ParserRuleBuilder(name)

    // val arguments: Map<String, Pair<Args, Args>>

    override fun build(): ParserGrammar {
        val result = rules.map { it.toRule() }
        val start = result[0]
        return ParserGrammar(start, result.groupBy(ParserRule::name))
    }
}