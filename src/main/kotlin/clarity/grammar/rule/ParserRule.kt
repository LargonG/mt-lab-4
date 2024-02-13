package clarity.grammar.rule

import clarity.grammar.atoms.Atom

/**
 * Правило для parser
 */
data class ParserRule(override val name: String) : Rule<Atom> {
    override val atoms: MutableList<Atom> = mutableListOf()

    override fun toString(): String = "ParserRule(name=$name, values=$atoms)"
}

