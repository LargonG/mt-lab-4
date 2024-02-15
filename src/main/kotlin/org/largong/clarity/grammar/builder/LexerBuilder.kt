package org.largong.clarity.grammar.builder

import org.largong.clarity.grammar.LexerGrammar
import org.largong.clarity.grammar.builder.rule.LexerRuleBuilder
import org.largong.clarity.grammar.rule.LexerRule


class LexerBuilder : GrammarBuilder<LexerRuleBuilder>() {
    override fun defaultValue(name: String): LexerRuleBuilder =
        LexerRuleBuilder(name)

    override fun build(): LexerGrammar =
        LexerGrammar(rules.map { it.toRule() }.groupBy(LexerRule::name))
}