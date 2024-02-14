package org.largong.clarity.grammar.scripts

data class Script(val code: String)

data class Args(val args: List<Arg>)
data class Arg(val name: String, val type: String)
