package org.example.clarity.grammar

import clarity.grammar.atoms.Atom
import clarity.grammar.rule.Rule

interface Grammar<T : Atom, out F : Rule<T>> {
    val rules: Map<String, List<F>>
}