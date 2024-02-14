package clarity.grammar.builder

import clarity.grammar.atoms.RegexAtom
import org.example.clarity.grammar.LexerGrammar
import org.example.clarity.grammar.builder.rule.LexerRuleBuilder
import org.example.clarity.grammar.rule.LexerRule


class LexerBuilder : GrammarBuilder<RegexAtom, LexerRuleBuilder>() {
    override fun defaultValue(name: String): LexerRuleBuilder =
        LexerRuleBuilder(name)

    override fun build(): LexerGrammar =
        LexerGrammar(rules.map { it.toRule() }.groupBy(LexerRule::name))
}