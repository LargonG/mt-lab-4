package org.largong.clarity

import org.largong.ClarityBaseListener
import org.largong.ClarityParser
import org.largong.clarity.grammar.LexerGrammar
import org.largong.clarity.grammar.ParserGrammar
import org.largong.clarity.grammar.atoms.*
import org.largong.clarity.grammar.builder.LexerBuilder
import org.largong.clarity.grammar.builder.ParserBuilder
import org.largong.clarity.grammar.scripts.Arg
import org.largong.clarity.grammar.scripts.Script

class ClarityExtractor : ClarityBaseListener() {
    private val lexerBuilder = LexerBuilder()

    private val parserBuilder = ParserBuilder()

    private var returns = false

    private var _parsed = false

    private var packageName = ""

    fun getLexerGrammar(): LexerGrammar =
        if (_parsed) lexerBuilder.build() else throw IllegalStateException()

    fun getParserGrammar(): ParserGrammar =
        if (_parsed) parserBuilder.build() else throw IllegalStateException()

    fun getPackage(): String =
        if (_parsed) packageName else throw IllegalStateException()

    override fun enterGram(ctx: ClarityParser.GramContext?) {
        // если захотим сделать многопоточным (что в целом сделать очень сложно в контексте antlr4)
        _parsed = false
    }

    override fun exitGram(ctx: ClarityParser.GramContext?) {
        _parsed = true
    }

    override fun exitPackageName(ctx: ClarityParser.PackageNameContext) {
        packageName = ctx.text.substringAfter("package").substringBefore(";").trim()
    }

    override fun enterLexerRuleDeclaration(ctx: ClarityParser.LexerRuleDeclarationContext) {
        lexerBuilder.nextRule(ctx.text)
    }

    override fun enterLexerString(ctx: ClarityParser.LexerStringContext) {
        val text = ctx.text
        lexerBuilder.ruleBuilder.atom =
            StringAtom(text.substring(1 until text.length - 1))
    }

    override fun enterLexerRegex(ctx: ClarityParser.LexerRegexContext) {
        val text = ctx.text
        lexerBuilder.ruleBuilder.atom =
            RegexAtom(text.substring(1 until text.length - 1))
    }

    override fun enterAction(ctx: ClarityParser.ActionContext) {
        val text = ctx.text
        if (text == "skip") {
            lexerBuilder.ruleBuilder.skipped = true
        }
    }

    override fun enterParserRuleDeclaration(ctx: ClarityParser.ParserRuleDeclarationContext) {
        parserBuilder.nextRule(ctx.text)
    }

    override fun enterParserRuleApply(ctx: ClarityParser.ParserRuleApplyContext) {
        val args = ctx.apply()?.text ?: ""
        parserBuilder.ruleBuilder.atoms.add(
            ParserAtom(
                ctx.parserRuleName()!!.text,
                args.substringAfter("<").substringBefore(">")))
    }

    override fun enterLexerRuleName(ctx: ClarityParser.LexerRuleNameContext) {
        parserBuilder.ruleBuilder.atoms.add(LexerAtom(ctx.text))
    }

    override fun enterEmpty(ctx: ClarityParser.EmptyContext?) {
        parserBuilder.ruleBuilder.atoms.add(EmptyAtom)
    }

    override fun enterCode(ctx: ClarityParser.CodeContext) {
        parserBuilder.ruleBuilder.script = Script(
            ctx.text.substring(1 until ctx.text.length - 1).trimMargin().trim())
    }

    override fun enterArg(ctx: ClarityParser.ArgContext) {
        val arg = Arg(ctx.name()!!.text, ctx.TYPE()!!.text)
        val name = parserBuilder.ruleBuilder.name
        parserBuilder.arguments.putIfAbsent(name, ParserBuilder.RuleDeclarationBuilder())
        if (!returns) {
            parserBuilder.arguments[name]!!.args.add(arg)
        } else {
            parserBuilder.arguments[name]!!.returns.add(arg)
        }
    }

    override fun enterReturnArgs(ctx: ClarityParser.ReturnArgsContext?) {
        returns = true
    }

    override fun exitReturnArgs(ctx: ClarityParser.ReturnArgsContext?) {
        returns = false
    }

    override fun exitDeclaration(ctx: ClarityParser.DeclarationContext?) {
        parserBuilder.rules.removeLast()
    }
}

