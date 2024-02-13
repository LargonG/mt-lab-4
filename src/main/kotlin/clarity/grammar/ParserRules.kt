package clarity.grammar

import clarity.grammar.atoms.Atom
import clarity.grammar.rule.ParserRule

class ParserRules : GrammarRules<Atom, ParserRule>() {
    lateinit var startRule: ParserRule
    override fun defaultValue(name: String): ParserRule =
        ParserRule(name)
}