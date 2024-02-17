package org.largong

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.largong.clarity.ClarityCompiler
import org.largong.clarity.ClarityExtractor
import org.largong.clarity.ClarityLL1Validator
import org.largong.clarity.ClarityRuleExistsValidator
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Path

fun getLexer(input: Reader): ClarityLexer = ClarityLexer(CharStreams.fromReader(input))

fun getParser(lexer: ClarityLexer): ClarityParser = ClarityParser(CommonTokenStream(lexer))

fun main(args: Array<String>) {
    val inputFile = Path.of(args[0])
    val lexer = getLexer(Files.newBufferedReader(inputFile))
    val parser = getParser(lexer)

    val tree = parser.gram()
    val extractor = ClarityExtractor()
    ParseTreeWalker.DEFAULT.walk(extractor, tree)

    val lexerGrammar = extractor.getLexerGrammar()
    val parserGrammar = extractor.getParserGrammar()

    val allExists = ClarityRuleExistsValidator(lexerGrammar, parserGrammar)

    if (!allExists.validate()) {
        throw IllegalArgumentException("Not all states exist")
    }

    val ll1 = ClarityLL1Validator(parserGrammar)


    println(lexerGrammar)
    println(parserGrammar)
    if (!ll1.isLL1Grammar()) {
        throw IllegalArgumentException("Grammar is not LL1!")
    }


    val compiler = ClarityCompiler(
        ll1,
        lexerGrammar, parserGrammar,
        args[0].substringAfterLast("/").split(".")[0],
        extractor.getPackage())

    compiler.compile(args[1])
}