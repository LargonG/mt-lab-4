package clarity.grammar.rule

import clarity.grammar.atoms.RegexAtom

/**
 * Правило для lexer
 */
data class LexerRule(override val name: String) : Rule<RegexAtom> {
    override val atoms: MutableList<RegexAtom> = mutableListOf()

    override fun toString(): String = "LexerRule(name=$name, atoms=$atoms)"
}