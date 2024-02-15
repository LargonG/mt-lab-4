package org.largong.clarity.grammar

import org.largong.clarity.grammar.rule.LexerRule

data class LexerGrammar(
    override val rules: Map<String, List<LexerRule>>
) : Grammar<LexerRule>