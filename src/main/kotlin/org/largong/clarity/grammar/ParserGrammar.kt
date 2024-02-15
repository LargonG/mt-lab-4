package org.largong.clarity.grammar

import org.largong.clarity.grammar.rule.ParserRule
import org.largong.clarity.grammar.scripts.Declaration

data class ParserGrammar(
    val start: ParserRule,
    override val rules: Map<String, List<ParserRule>>,
    val arguments: Map<String, Declaration>
) : Grammar<ParserRule> {
    override fun toString(): String {
        val builder = StringBuilder().append("ParserGrammar(\n")
        builder.append(start).append("\nrules:\n")
        for (rule in rules) {
            builder.append("\t${rule.key} = ${rule.value}\n")
        }
        builder.append("\narguments:\n")
        for (arg in arguments) {
            builder.append("\t${arg.key} = ${arg.value}\n")
        }
        builder.append(")\n")
        return builder.toString()
    }
}