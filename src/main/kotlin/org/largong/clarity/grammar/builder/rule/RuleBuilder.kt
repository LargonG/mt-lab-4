package org.largong.clarity.grammar.builder.rule

import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.rule.Rule

/**
 * Представляет собой простое правило
 * [name]: [atoms];
 */
interface RuleBuilder<T : Atom> {
    val name: String
    val atoms: MutableList<T>

    fun toRule(): Rule<T>
}