package org.largong.clarity.grammar.builder.rule

import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.rule.LexerRule

/**
 * Правило для lexer
 */
data class LexerRuleBuilder(override val name: String) : RuleBuilder {
    var atom: Atom? = null
    override fun toRule(): LexerRule = LexerRule(name, atom ?: throw IllegalStateException())

    override fun toString(): String = "LexerRule(name=$name, atom=$atom)"
}