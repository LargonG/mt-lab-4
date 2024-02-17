package org.largong.clarity

import org.largong.clarity.grammar.LexerGrammar
import org.largong.clarity.grammar.ParserGrammar
import org.largong.clarity.grammar.atoms.*
import org.largong.clarity.grammar.rule.ParserRule
import org.largong.clarity.grammar.scripts.Arg
import org.largong.clarity.grammar.scripts.Declaration
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

class ClarityCompiler(
    private val validator: ClarityLL1Validator,
    private val lexer: LexerGrammar,
    private val parser: ParserGrammar,
    private val filename: String,
    private val packageName: String
) {
    // TODO скомпилировать файл для лексера по шаблону
    // TODO скомпилировать файл для парсера по шаблону

    private val defaultValues = mapOf(
        Pair("Int", "0"),
        Pair("Long", "0"),
        Pair("Float", "0f"),
        Pair("Double", "0.0"),
        Pair("Char", "' '"),
        Pair("String", "\"\""),
        Pair("Boolean", "false"),
    )

    enum class Template(val inner: String) {
        APPLY("apply"),
        FIRST("first"),
        FOLLOW("follow"),
        IS_NON_TERMINAL("isNonTerminal"),
        IS_TERMINAL("isTerminal"),
        LEXER("LexerTemplate"),
        PARSER("ParserTemplate"),
        RESULT("result"),
        TOKEN("token"),
        VAR("variable")
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

    fun compile(directory: String) {
        val packageDirectory = packageName.replace('.', '/')
        val source = "$directory/$packageDirectory"
        val sourcePath = Path.of(source)
        val lexerPath = Path.of("$source/${filename}Lexer.kt")
        val parserPath = Path.of("$source/${filename}Parser.kt")

        if (!Files.exists(sourcePath)) {
            Files.createDirectories(sourcePath)
        }

        Files.newBufferedWriter(
            lexerPath
        ).use { file ->
            file.write(compileLexer())
        }

        Files.newBufferedWriter(
            parserPath
        ).use { file ->
            file.write(compileParser())
        }

    }

    fun compileParser(): String {
        val parserFormat = Templates.getString(Template.PARSER)
        val results = compileResults()
        val rules = compileRules()

        return String.format(
            parserFormat,
            packageName,
            filename,
            results,
            rules
        )
    }

    private fun compileResults(): String {
        val resultTemplate = Templates.getString(Template.RESULT)
        return parser.arguments.entries.joinToString("\n") {
            val name = it.key.replaceFirstChar { symbol -> symbol.uppercaseChar() }
            String.format(resultTemplate, name, compileArgs(it.value.returns)) }
    }

    private fun compileArgs(args: List<Arg>, prefix: String = "var ",): String
        = args.joinToString(", ") { "$prefix${it.name}: ${it.type} = ${defaultValue(it.type)}" }

    private fun defaultValue(type: String): String = defaultValues[type]!!

    private fun compileRules(): String {
        val builder = StringBuilder()
        for (rule in parser.rules) {
            builder.append(compileApply(
                validator, rule.value,
                parser.arguments[rule.key],
                rule.key,
                validator.first[rule.key]!!,
                validator.follow[rule.key]!!))
        }
        return builder.toString()
    }

    private fun compileApply(
        validator: ClarityLL1Validator,
        rules: List<ParserRule>,
        declaration: Declaration?,
        ruleName: String,
        first: Set<Atom>,
        follow: Set<Atom>): String {
        val apply = Templates.getString(Template.APPLY)
        val capitalRuleName = ruleName.replaceFirstChar { it.uppercaseChar() }
        val resultName = if (declaration == null) "" else capitalRuleName
        val args = compileArgs(declaration?.args ?: listOf(), prefix = "")
        val cases = compileCases(validator, rules, first, follow)

        return String.format(apply, capitalRuleName, args, resultName, ruleName, resultName, cases)
    }


    private fun compileCases(validator: ClarityLL1Validator,
                             rules: List<ParserRule>,
                             first: Set<Atom>,
                             follow: Set<Atom>): String {
        val builder = StringBuilder()
        for (rule in rules) {
            for (it in validator.getFirst(rule.atoms)) {
                if (it is EmptyAtom) {
                    continue
                }
                builder.append(compileFirst(it as LexerAtom, rule))
            }
        }

        if (first.contains(EmptyAtom)) {
            for (it in follow) {
                val name =
                    when (it) {
                        is EOF -> "EOF"
                        is LexerAtom -> it.name
                        else -> throw IllegalStateException()
                    }
                builder.append(compileFollow(name))
            }
        }

        return builder.toString()
    }

    private fun compileFirst(start: LexerAtom,
                             rule: ParserRule): String {
        val first = Templates.getString(Template.FIRST)
        val variables = compileVariables(rule.atoms)
        val actions = compileCaseActions(rule.atoms)
        val code = if (rule.code == null) "" else rule.code.inner
        return String.format(first, start.name, variables, actions, code)
    }

    private fun compileVariables(line: List<Atom>): String {
        val builder = StringBuilder()
        for (it in line) {
            builder.append(compileVariable(it))
        }
        return builder.toString()
    }

    private fun compileVariable(atom: Atom): String {
        val variable = Templates.getString(Template.VAR)
        return if (atom is LexerAtom) {
            String.format(variable, atom.name, "")
        } else if (atom is ParserAtom) {
            val resultName =
            if (parser.arguments.containsKey(atom.name)) {
                atom.name.replaceFirstChar { it.uppercaseChar() }
            } else {
                ""
            }
            String.format(variable, atom.name, resultName)
        } else {
            throw IllegalStateException()
        }
    }

    private fun compileCaseActions(atoms: List<Atom>): String {
        val builder = StringBuilder()
        for (it in atoms) {
            when (it) {
                is LexerAtom -> {
                    builder.append(compileTerminal(it))
                }

                is ParserAtom -> {
                    builder.append(compileNonTerminal(it))
                }

                else -> throw IllegalStateException()
            }
        }
        return builder.toString()
    }

    private fun compileTerminal(terminal: LexerAtom): String {
        val isTerminal = Templates.getString(Template.IS_TERMINAL)
        return String.format(isTerminal, terminal.name, terminal.name)
    }

    private fun compileNonTerminal(rule: ParserAtom): String {
        val isNonTerminal = Templates.getString(Template.IS_NON_TERMINAL)
        val capitalName = rule.name.replaceFirstChar { it.uppercaseChar() }
        return String.format(isNonTerminal,
            capitalName,
            rule.args.joinToString(", ") { it.name },
            rule.name,
            if (parser.arguments.containsKey(rule.name)) capitalName else "")
    }

    private fun compileFollow(name: String): String {
        val followTemplate = Templates.getString(Template.FOLLOW)
        return String.format(followTemplate, name)
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
            val rule = list.value[0]
            val atom = rule.atom
            builder.append(String.format(tokenFormat, list.key,
                if (atom is StringAtom) "Regex(Regex.escape(\"$atom\"))" else "Regex(\"$atom\")",
                rule.skipped))
        }
        return builder.toString()
    }
}