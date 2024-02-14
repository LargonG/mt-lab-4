package org.largong.clarity.grammar.builder.rule

import org.largong.clarity.grammar.atoms.RegexAtom
import org.largong.clarity.grammar.rule.LexerRule

/**
 * Правило для lexer
 */
data class LexerRuleBuilder(override val name: String) : RuleBuilder<RegexAtom> {
    override val atoms: MutableList<RegexAtom> = mutableListOf()
    override fun toRule(): LexerRule = LexerRule(name, atoms)

    override fun toString(): String = "LexerRule(name=$name, atoms=$atoms)"
}