package org.largong.clarity.grammar.rule

import org.largong.clarity.grammar.atoms.Atom
import org.largong.clarity.grammar.scripts.Script

data class ParserRule(
    override val name: String,
    override val atoms: List<Atom>,
    val code: Script?,
) : Rule<Atom>
