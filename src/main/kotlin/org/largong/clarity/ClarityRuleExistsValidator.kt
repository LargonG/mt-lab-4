package org.largong.clarity

import org.largong.clarity.grammar.LexerGrammar
import org.largong.clarity.grammar.ParserGrammar
import org.largong.clarity.grammar.atoms.LexerAtom
import org.largong.clarity.grammar.atoms.ParserAtom

class ClarityRuleExistsValidator(
    private val lexer: LexerGrammar,
    private val parser: ParserGrammar,
) {
    fun validate(): Boolean {
        for (rules in parser.rules.values) {
            for (rule in rules) {
                for (atom in rule.atoms) {
                    if (atom is LexerAtom && !lexer.rules.containsKey(atom.name) ||
                        atom is ParserAtom && !parser.rules.containsKey(atom.name)
                    ) {
                        return false
                    }
                }
            }
        }

        return true
    }
}