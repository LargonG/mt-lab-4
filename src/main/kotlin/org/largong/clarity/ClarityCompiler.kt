package org.largong.clarity

import org.largong.clarity.grammar.LexerGrammar
import org.largong.clarity.grammar.ParserGrammar
import org.largong.clarity.grammar.atoms.StringAtom
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

class ClarityCompiler(
    private val lexer: LexerGrammar,
    private val parser: ParserGrammar,
    private val filename: String,
    private val packageName: String
) {
    // TODO скомпилировать файл для лексера по шаблону
    // TODO скомпилировать файл для парсера по шаблону

    enum class Template(val inner: String) {
        APPLY("apply"),
        ARG("arg"),
        FIRST("first"),
        FOLLOW("follow"),
        IS_NON_TERMINAL("isNonTerminal"),
        IS_TERMINAL("isTerminal"),
        LEXER("LexerTemplate"),
        PARSER("ParserTemplate"),
        RESULT("result"),
        TOKEN("token")
    }

    object Templates {
        private const val PREFIX = "src/main/resources"
        private const val SUFFIX = "template"

        private val cache: MutableMap<String, String> = mutableMapOf()

        fun getString(template: Template): String {
            val filename = "$PREFIX/${template.inner}.$SUFFIX"
            if (cache.containsKey(filename)) {
                return cache[filename]!!
            }
            val path = Path.of(filename)
                ?: throw IllegalStateException()
            val result = Files.readString(path, StandardCharsets.UTF_8)
            cache[filename] = result
            return result
        }
    }

    fun compileLexer(): String {
        val lexerFormat = Templates.getString(Template.LEXER)
        val tokens = compileTokens()
        return String.format(lexerFormat, packageName, filename, tokens)
    }

    private fun compileTokens(): String {
        val builder = StringBuilder()
        val tokenFormat = Templates.getString(Template.TOKEN)
        for (list in lexer.rules) {
            val t = list.value[0].atom
            builder.append(String.format(tokenFormat, list.key,
                if (t is StringAtom) "Regex.fromLiteral(\"$t\")" else "Regex(\"$t\")"))
        }
        return builder.toString()
    }
}