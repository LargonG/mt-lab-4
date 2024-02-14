package org.largong.clarity

import org.largong.ClarityBaseListener
import org.largong.ClarityParser
import org.largong.clarity.grammar.LexerGrammar
import org.largong.clarity.grammar.ParserGrammar
import org.largong.clarity.grammar.atoms.*
import org.largong.clarity.grammar.builder.LexerBuilder
import org.largong.clarity.grammar.builder.ParserBuilder

class ClarityExtractor : ClarityBaseListener() {
    private val lexerBuilder = LexerBuilder()

    private val parserBuilder = ParserBuilder()

    private var _parsed = false

    fun getLexerGrammar(): LexerGrammar =
        if (_parsed) lexerBuilder.build() else throw IllegalStateException()

    fun getParserGrammar(): ParserGrammar =
        if (_parsed) parserBuilder.build() else throw IllegalStateException()

    override fun enterGram(ctx: ClarityParser.GramContext?) {
        // если захотим сделать многопоточным (что в целом сделать очень сложно в контексте antlr4)
        _parsed = false
    }

    override fun exitGram(ctx: ClarityParser.GramContext?) {
        _parsed = true
    }

    override fun enterLexerRuleDeclaration(ctx: ClarityParser.LexerRuleDeclarationContext) {
        lexerBuilder.nextRule(ctx.text)
    }

    override fun enterLexerString(ctx: ClarityParser.LexerStringContext) {
        val text = ctx.text
        lexerBuilder.ruleBuilder.atoms.add(
            RegexAtom(Regex.fromLiteral(text.substring(1 until text.length)))
        )
    }

    override fun enterLexerRegex(ctx: ClarityParser.LexerRegexContext) {
        val text = ctx.text
        lexerBuilder.ruleBuilder.atoms.add(
            RegexAtom(Regex(text.substring(1 until text.length)))
        )
    }

    override fun enterParserRuleDeclaration(ctx: ClarityParser.ParserRuleDeclarationContext) {
        parserBuilder.nextRule(ctx.text)
    }

    override fun enterParserRuleName(ctx: ClarityParser.ParserRuleNameContext) {
        parserBuilder.ruleBuilder.atoms.add(ParserAtom(ctx.text))
    }

    override fun enterLexerRuleName(ctx: ClarityParser.LexerRuleNameContext) {
        parserBuilder.ruleBuilder.atoms.add(LexerAtom(ctx.text))
    }

    override fun enterEmpty(ctx: ClarityParser.EmptyContext?) {
        parserBuilder.ruleBuilder.atoms.add(EmptyAtom)
    }

    override fun enterCode(ctx: ClarityParser.CodeContext) {
        parserBuilder.ruleBuilder.code = CodeBlock(ctx.text)
    }

    override fun enterParam(ctx: ClarityParser.ParamContext) {
        val param = Param(ctx.name().text, ctx.TYPE().text)
//        if (parserBuilder.ruleBuilder.returnsBlock) {
//            parserBuilder.ruleBuilder.returnsParams.add(param)
//        } else {
//            parserBuilder.ruleBuilder.params.add(param)
//        }
    }

    override fun enterReturnsParams(ctx: ClarityParser.ReturnsParamsContext?) {
        // parserBuilder.ruleBuilder.returnsBlock = true
    }
}

