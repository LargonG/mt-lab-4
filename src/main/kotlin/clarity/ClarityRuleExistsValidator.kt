package clarity

import clarity.grammar.LexerRules
import clarity.grammar.ParserRules
import clarity.grammar.atoms.LexerAtom
import clarity.grammar.atoms.ParserAtom

class ClarityRuleExistsValidator(
    val lexerRules: LexerRules,
    val parserRules: ParserRules,
) {
    fun validate(): Boolean {
        for (rules in parserRules.rules.values) {
            for (rule in rules) {
                for (atom in rule.atoms) {
                    if (atom is LexerAtom) {
                        if (!lexerRules.rules.containsKey(atom.name)) {
                            return false
                        }
                    } else if (atom is ParserAtom) {
                        if (!parserRules.rules.containsKey(atom.name)) {
                            return false
                        }
                    }
                }
            }
        }

        return true
    }
}