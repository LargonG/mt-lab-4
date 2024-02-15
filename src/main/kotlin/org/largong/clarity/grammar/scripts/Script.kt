package org.largong.clarity.grammar.scripts

data class Script(val inner: String)

data class Declaration(val args: List<Arg>, val returns: List<Arg>)
data class Arg(val name: String, val type: String)
data class ApplyArg(val name: String)
