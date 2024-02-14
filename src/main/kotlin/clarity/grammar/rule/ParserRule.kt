package org.example.clarity.grammar.rule

import clarity.grammar.atoms.Atom
import org.example.clarity.grammar.atoms.CodeBlock
import org.example.clarity.grammar.atoms.Param

data class ParserRule(
    override val name: String,
    override val atoms: List<Atom>,
    val code: CodeBlock?,
    val params: List<Param>
) : Rule<Atom>
