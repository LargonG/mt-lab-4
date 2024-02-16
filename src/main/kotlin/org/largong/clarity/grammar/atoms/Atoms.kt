package org.largong.clarity.grammar.atoms

import org.largong.clarity.grammar.scripts.ApplyArg

sealed interface Atom

data class RegexAtom(val inner: Regex) : Atom
data class StringAtom(val inner: String): Atom

data class LexerAtom(val name: String) : Atom
data class ParserAtom(val name: String, val args: List<ApplyArg>) : Atom
data object EmptyAtom : Atom
data object EOF : Atom
