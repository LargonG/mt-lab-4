package clarity.grammar.builder

import clarity.grammar.atoms.Atom
import clarity.grammar.rule.ParserRule
import org.example.clarity.grammar.ParserGrammar

class ParserBuilder : GrammarBuilder<Atom, ParserRule>() {
    lateinit var startRule: ParserRule
    override fun defaultValue(name: String): ParserRule =
        ParserRule(name)

    override fun build(): ParserGrammar =
        ParserGrammar(startRule, rules)
}