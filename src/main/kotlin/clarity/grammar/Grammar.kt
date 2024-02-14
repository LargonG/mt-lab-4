package org.example.clarity.grammar

import clarity.grammar.atoms.Atom
import org.example.clarity.grammar.rule.Rule

interface Grammar<T : Atom, out F : Rule<T>> {
    val rules: Map<String, List<F>>
}