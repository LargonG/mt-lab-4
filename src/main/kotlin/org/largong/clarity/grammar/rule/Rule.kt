package org.largong.clarity.grammar.rule

import org.largong.clarity.grammar.atoms.Atom

interface Rule<out T : Atom> {
    val name: String
    val atoms: List<T>
}