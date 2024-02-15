package org.largong.clarity.grammar.builder.rule

import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.rule.ParserRule
import org.largong.clarity.grammar.scripts.Script

/**
 * Правило для parser
 */
data class ParserRuleBuilder(override val name: String) : RuleBuilder {
    val atoms: MutableList<Atom> = mutableListOf()
    var script: Script? = null

    override fun toRule(): ParserRule = ParserRule(name, atoms, script)

    override fun toString(): String = "ParserRule(name=$name, values=$atoms)"
}

