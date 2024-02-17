/**
 * Генерация пакета
 */
package calculator.grammar

interface Lexer {
    fun nextToken(): TerminalContext
    fun hasNextToken(): Boolean
    fun lookToken(): TerminalContext?
}

/**
 * Название берётся из названия файла
 */
// <file-name>Lexer
class CalculatorLexer(val input: CharSequence): Lexer {
    private inner class RegexIterator: Iterator<MatchResult> {
       var left = 0

       override fun hasNext(): Boolean {
           return loop() != null
       }

       override fun next(): MatchResult {
           val match = loop() ?: throw NoSuchElementException()
           left = match.range.last + 1
           return match
       }

       private fun loop(): MatchResult? {
           while (true) {
               var match: MatchResult? = null
               var currentToken: Token? = null
               for (token in Token.entries) {
                   val current = token.regex.find(input, left) ?: continue
                   if (match == null ||
                       match.range.first > current.range.first
                   ) {
                       match = current
                       currentToken = token
                   }
               }

               if (match == null) return null
               if (currentToken == null) throw IllegalStateException()

               if (currentToken.skipped) {
                   left = match.range.last + 1
                   continue
               }

               return match
           }
       }
    }

    private val iterator = RegexIterator()
    private var buffer: MatchResult? = null

    override fun nextToken(): TerminalContext {
        val value: String =
        if (buffer == null) {
            iterator.next().value
        } else {
            val result = buffer
            buffer = null
            result?.value ?: throw NoSuchElementException()
        }

        for (it in Token.entries) {
            if (it.regex.matches(value)) {
                return TerminalContext(it, value)
            }
        }

        throw IllegalStateException()
    }

    override fun hasNextToken(): Boolean {
        return if (buffer == null) {
            iterator.hasNext()
        } else {
            true
        }
    }

    override fun lookToken(): TerminalContext? {
        if (buffer == null) {
            buffer = if (iterator.hasNext()) iterator.next() else null
        }

        val value = buffer?.value ?: return null

        for (it in Token.entries) {
            if (it.regex.matches(value)) {
                return TerminalContext(it, value)
            }
        }

        return null
    }
}

data class TerminalContext(val token: Token, val text: String)

/**
 * Генерация всех токенов в lexer
 */
enum class Token(val regex: Regex, val skipped: Boolean) {
    // <token-name>(if isString then Regex.fromLiteral(str) else Regex(pattern)),
    OPEN(Regex(Regex.escape("(")), false),
    CLOSE(Regex(Regex.escape(")")), false),
    ADD(Regex(Regex.escape("+")), false),
    SUB(Regex(Regex.escape("-")), false),
    MUL(Regex(Regex.escape("*")), false),
    DIV(Regex(Regex.escape("/")), false),
    NUMBER(Regex("[1-9][0-9]*|[0]"), false),
    SPACE(Regex("[ \t\r\n]+"), true),

    EOF(Regex("$"), false);
}
