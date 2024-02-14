package clarity.grammar.builder

import clarity.grammar.atoms.RegexAtom
import clarity.grammar.rule.LexerRule
import org.example.clarity.grammar.LexerGrammar


class LexerBuilder : GrammarBuilder<RegexAtom, LexerRule>() {
    override fun defaultValue(name: String): LexerRule =
        LexerRule(name)

    override fun build(): LexerGrammar =
        LexerGrammar(rules)
}