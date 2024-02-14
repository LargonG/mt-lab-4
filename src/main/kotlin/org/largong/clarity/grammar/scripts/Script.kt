package org.largong.clarity.grammar.scripts

data class Script(val code: String)

data class Declaration(val args: List<Arg>, val returns: List<Arg>)
data class Arg(val name: String, val type: String)
