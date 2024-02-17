package org.largong.clarity.grammar.atoms

sealed interface Atom

data class RegexAtom(val inner: String): Atom {
    override fun toString(): String = inner
}
data class StringAtom(val inner: String): Atom {
    override fun toString(): String = inner
}

data class LexerAtom(val name: String): Atom
data class ParserAtom(val name: String, val args: String): Atom
data object EmptyAtom: Atom
data object EOF : Atom
