package org.example.clarity.grammar.builder.rule

import clarity.grammar.atoms.Atom
import org.example.clarity.grammar.rule.Rule

/**
 * Представляет собой простое правило
 * [name]: [atoms];
 */
interface RuleBuilder<T : Atom> {
    val name: String
    val atoms: MutableList<T>

    fun toRule(): Rule<T>
}