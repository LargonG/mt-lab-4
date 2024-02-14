package clarity.grammar.builder

import clarity.grammar.atoms.Atom
import org.example.clarity.grammar.ParserGrammar
import org.example.clarity.grammar.builder.rule.ParserRuleBuilder
import org.example.clarity.grammar.rule.ParserRule

class ParserBuilder : GrammarBuilder<Atom, ParserRuleBuilder>() {
    override fun defaultValue(name: String): ParserRuleBuilder =
        ParserRuleBuilder(name)

    override fun build(): ParserGrammar {
        val result = rules.map { it.toRule() }
        val start = result[0]
        return ParserGrammar(start, result.groupBy(ParserRule::name))
    }
}