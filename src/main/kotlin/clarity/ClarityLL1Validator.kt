package clarity

import clarity.grammar.atoms.Atom
import clarity.grammar.atoms.EOF
import clarity.grammar.atoms.EmptyAtom
import clarity.grammar.atoms.ParserAtom
import org.example.clarity.grammar.ParserGrammar

class ClarityLL1Validator(
    private val parser: ParserGrammar
) {
    // todo проверить, что все достижимые правила конечны


    private var _first = mutableMapOf<String, MutableSet<Atom>>()

    private var _follow = mutableMapOf<String, MutableSet<Atom>>()

    private var calculated = false

    val first: Map<String, Set<Atom>>
        get() {
            getFirstAndFollow()
            return _first
        }

    val follow: Map<String, Set<Atom>>
        get() {
            getFirstAndFollow()
            return _follow
        }

    private fun getFirstAndFollow() {
        if (calculated) return
        getFirst()
        getFollow()
        calculated = true
    }


    private fun getFirst() {
        var change = true
        while (change) {
            change = false
            for (rules in parser.rules.values) {
                for (rule in rules) {
                    val result = getFirst(rule.atoms)
                    _first.putIfAbsent(rule.name, mutableSetOf())
                    change = _first[rule.name]!!.addAll(result) || change
                }
            }
        }
    }

    private fun getFirst(atoms: List<Atom>): Set<Atom> {
        val result = mutableSetOf<Atom>()
        for (atom in atoms) {
            val next: Set<Atom> =
                if (atom is ParserAtom) {
                    _first[atom.name].orEmpty()
                } else {
                    mutableSetOf(atom)
                }

            result.remove(EmptyAtom)
            result.addAll(next)

            if (!next.contains(EmptyAtom)) {
                break
            }
        }

        if (atoms.isEmpty()) {
            result.add(EmptyAtom)
        }

        return result
    }


    private fun getFollow() {
        var change = true

        _follow[parser.start.name] = mutableSetOf(EOF)
        while (change) {
            change = false
            for (rules in parser.rules.values) {
                for (rule in rules) {
                    for (i in rule.atoms.indices) {
                        val atom = rule.atoms[i]
                        if (atom is ParserAtom) {
                            val newFollow = getFirst(rule.atoms.subList(i + 1, rule.atoms.size))
                            val edited = newFollow - EmptyAtom

                            _follow.putIfAbsent(atom.name, mutableSetOf())

                            val t1 = _follow[atom.name]!!.addAll(edited)
                            val t2 = newFollow.contains(EmptyAtom) &&
                                    _follow[atom.name]!!.addAll(_follow.getOrDefault(rule.name, setOf()))

                            change = t1 || t2 || change
                        }
                    }
                }
            }
        }
    }

    fun isLL1Grammar(): Boolean {
        println("\nfirst: ")
        for (i in first) {
            println("\t$i")
        }

        println("\nfollow: ")
        for (i in follow) {
            println("\t$i")
        }

        println()

        for (group in parser.rules) {
            for (a in group.value) {
                for (b in group.value) {
                    if (a.atoms == b.atoms) {
                        continue
                    }

                    val aFirst = getFirst(a.atoms)
                    val bFirst = getFirst(b.atoms)

                    if ((aFirst.any { bFirst.contains(it) })) {
                        println("Error 1:")
                        println("\t$a $aFirst")
                        println("\t$b $bFirst")
                        return false
                    }

                    if (aFirst.contains(EmptyAtom) &&
                        bFirst.any { follow[a.name]!!.contains(it) }
                    ) {
                        println("Error 2:")
                        println("\t$aFirst")
                        println("\t$bFirst")
                        println(follow[a.name])
                        return false
                    }
                }
            }
        }

        return true
    }
}