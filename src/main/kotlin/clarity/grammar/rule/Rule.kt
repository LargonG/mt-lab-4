package clarity.grammar.rule

import clarity.grammar.atoms.Atom

/**
 * Представляет собой простое правило
 * [name]: [atoms];
 */
interface Rule<T : Atom> {
    val name: String
    val atoms: MutableList<T>
}