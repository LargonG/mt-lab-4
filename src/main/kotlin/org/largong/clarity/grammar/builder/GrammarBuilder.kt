package org.largong.clarity.grammar.builder

import org.largong.clarity.grammar.Grammar
import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.builder.rule.RuleBuilder
import org.largong.clarity.grammar.rule.Rule

/**
 * Правила грамматики, то есть список из [RuleBuilder],
 * представлено в виде таблицы:
 * название правила [String] -> List<[RuleBuilder]>, можно получить в [rules]
 */
abstract class GrammarBuilder<T : Atom, F : RuleBuilder<T>> {
    val rules: MutableList<F> = mutableListOf()

    val ruleBuilder: F
        get() = rules.last()

    fun nextRule(name: String): F {
        rules.add(defaultValue(name))
        return rules.last()
    }

    abstract fun defaultValue(name: String): F

    abstract fun build(): Grammar<T, Rule<T>>
}