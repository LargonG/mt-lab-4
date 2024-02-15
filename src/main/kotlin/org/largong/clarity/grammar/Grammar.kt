package org.largong.clarity.grammar

import org.largong.clarity.grammar.rule.Rule

interface Grammar<out F : Rule> {
    val rules: Map<String, List<F>>
}