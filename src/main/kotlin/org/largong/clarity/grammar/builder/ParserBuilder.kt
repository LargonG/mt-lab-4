package org.largong.clarity.grammar.builder

import org.largong.clarity.grammar.ParserGrammar
import org.largong.clarity.grammar.builder.rule.ParserRuleBuilder
import org.largong.clarity.grammar.rule.ParserRule
import org.largong.clarity.grammar.scripts.ApplyArg
import org.largong.clarity.grammar.scripts.Arg
import org.largong.clarity.grammar.scripts.Declaration

class ParserBuilder : GrammarBuilder<ParserRuleBuilder>() {
    class RuleDeclarationBuilder {
        val args: MutableList<Arg> = mutableListOf()
        val returns: MutableList<Arg> = mutableListOf()
    }

    override fun defaultValue(name: String): ParserRuleBuilder =
        ParserRuleBuilder(name)

    val arguments: MutableMap<String, RuleDeclarationBuilder> = mutableMapOf()

    val applyArgs: MutableList<ApplyArg> = mutableListOf()

    override fun build(): ParserGrammar {
        val result = rules.map { it.toRule() }
        val start = result[0]
        return ParserGrammar(
            start,
            result.groupBy(ParserRule::name),
            arguments.mapValues { Declaration(it.value.args, it.value.returns) }
            )
    }
}