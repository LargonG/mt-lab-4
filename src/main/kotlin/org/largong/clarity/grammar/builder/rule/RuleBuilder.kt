package org.largong.clarity.grammar.builder.rule

import org.largong.clarity.grammar.rule.Rule

/**
 * Представляет собой простое правило
 * [name]: expression;
 */
interface RuleBuilder {
    val name: String

    fun toRule(): Rule
}