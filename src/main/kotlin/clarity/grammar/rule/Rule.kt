package org.example.clarity.grammar.rule

import clarity.grammar.atoms.Atom

interface Rule<out T : Atom> {
    val name: String
    val atoms: List<T>
}