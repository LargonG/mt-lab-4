package org.largong.clarity.grammar

import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.rule.Rule

interface Grammar<T : Atom, out F : Rule<T>> {
    val rules: Map<String, List<F>>
}