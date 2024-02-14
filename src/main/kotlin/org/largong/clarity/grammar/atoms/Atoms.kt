package org.largong.clarity.grammar.atoms

sealed interface Atom

data class RegexAtom(val regex: Regex) : Atom

data class LexerAtom(val name: String) : Atom
data class ParserAtom(val name: String) : Atom
data object EmptyAtom : Atom
data object EOF : Atom
