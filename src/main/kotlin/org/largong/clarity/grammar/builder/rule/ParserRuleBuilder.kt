package org.largong.clarity.grammar.builder.rule

import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.atoms.CodeBlock
import org.largong.clarity.grammar.rule.ParserRule

/**
 * Правило для parser
 */
data class ParserRuleBuilder(override val name: String) : RuleBuilder<Atom> {
    override val atoms: MutableList<Atom> = mutableListOf()
    var code: CodeBlock? = null

    override fun toRule(): ParserRule = ParserRule(name, atoms, code)

    override fun toString(): String = "ParserRule(name=$name, values=$atoms)"
}

