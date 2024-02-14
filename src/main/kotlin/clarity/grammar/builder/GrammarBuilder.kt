package clarity.grammar.builder

import clarity.grammar.atoms.Atom
import clarity.grammar.rule.Rule
import org.example.clarity.grammar.Grammar

/**
 * Правила грамматики, то есть список из [Rule],
 * представлено в виде таблицы:
 * название правила [String] -> List<[Rule]>, можно получить в [rules]
 */
abstract class GrammarBuilder<T : Atom, F : Rule<T>> {
    private var _rules = mutableMapOf<String, MutableList<F>>()
    val rules: Map<String, List<F>>
        get() = _rules

    private var _currentName = ""

    var currentName: String
        get() = _currentName
        set(value) {
            _currentName = value
            _rules.putIfAbsent(value, mutableListOf())
            _rules[value]!!.add(defaultValue(value))
        }

    private fun ruleBlock(name: String) = rules[name]!!

    val currentRule: Rule<T>
        get() = ruleBlock(currentName).last()

    abstract fun defaultValue(name: String): F

    abstract fun build(): Grammar<T, F>
}