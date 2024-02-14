package clarity

import clarity.grammar.atoms.LexerAtom
import clarity.grammar.atoms.ParserAtom
import org.example.clarity.grammar.LexerGrammar
import org.example.clarity.grammar.ParserGrammar

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