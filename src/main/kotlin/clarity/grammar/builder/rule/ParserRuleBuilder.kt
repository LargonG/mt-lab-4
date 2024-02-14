package org.example.clarity.grammar.builder.rule

import clarity.grammar.atoms.Atom
import org.example.clarity.grammar.atoms.CodeBlock
import org.example.clarity.grammar.atoms.Param
import org.example.clarity.grammar.rule.ParserRule

/**
 * Правило для parser
 */
data class ParserRuleBuilder(override val name: String) : RuleBuilder<Atom> {
    override val atoms: MutableList<Atom> = mutableListOf()
    var code: CodeBlock? = null
    val params: MutableList<Param> = mutableListOf()

    override fun toRule(): ParserRule = ParserRule(name, atoms, code, params)

    override fun toString(): String = "ParserRule(name=$name, values=$atoms)"
}

