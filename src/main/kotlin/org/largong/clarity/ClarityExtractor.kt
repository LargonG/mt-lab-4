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
        parserBuilder.ruleBuilder.script = Script(ctx.text)
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

