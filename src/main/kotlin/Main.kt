package org.example

import clarity.ClarityExtractor
import clarity.ClarityLL1Validator
import clarity.ClarityRuleExistsValidator
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.largong.ClarityLexer
import org.largong.ClarityParser
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

    val allExists = ClarityRuleExistsValidator(extractor.getLexer(), extractor.getParser())
    println(allExists.validate())

    val ll1 = ClarityLL1Validator(extractor.getParser())
    println(ll1.isLL1Grammar())
}