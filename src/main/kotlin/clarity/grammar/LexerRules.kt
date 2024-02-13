package clarity.grammar

import clarity.grammar.atoms.RegexAtom
import clarity.grammar.rule.LexerRule


class LexerRules : GrammarRules<RegexAtom, LexerRule>() {
    override fun defaultValue(name: String): LexerRule =
        LexerRule(name)
}