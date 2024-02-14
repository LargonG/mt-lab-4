package clarity

import clarity.grammar.atoms.EmptyAtom
import clarity.grammar.atoms.LexerAtom
import clarity.grammar.atoms.ParserAtom
import clarity.grammar.atoms.RegexAtom
import clarity.grammar.builder.LexerBuilder
import clarity.grammar.builder.ParserBuilder
import clarity.grammar.rule.ParserRule
import org.example.clarity.grammar.LexerGrammar
import org.example.clarity.grammar.ParserGrammar
import org.largong.ClarityBaseListener
import org.largong.ClarityParser

class ClarityExtractor : ClarityBaseListener() {
    private val lexerBuilder = LexerBuilder()

    private val parserBuilder = ParserBuilder()

    private var _parsed = false

    private var started = true

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
        lexerBuilder.currentName = ctx.text
    }

    override fun enterLexerString(ctx: ClarityParser.LexerStringContext) {
        val text = ctx.text
        lexerBuilder.currentRule.atoms.add(
            RegexAtom(Regex.fromLiteral(text.substring(1 until text.length)))
        )
    }

    override fun enterLexerRegex(ctx: ClarityParser.LexerRegexContext) {
        val text = ctx.text
        lexerBuilder.currentRule.atoms.add(
            RegexAtom(Regex(text.substring(1 until text.length)))
        )
    }

    override fun enterParserRuleDeclaration(ctx: ClarityParser.ParserRuleDeclarationContext) {
        parserBuilder.currentName = ctx.text
    }

    override fun exitParserRuleDeclaration(ctx: ClarityParser.ParserRuleDeclarationContext?) {
        if (started) {
            parserBuilder.startRule = parserBuilder.currentRule as ParserRule
            started = false
        }
    }

    override fun enterParserRuleName(ctx: ClarityParser.ParserRuleNameContext) {
        parserBuilder.currentRule.atoms.add(ParserAtom(ctx.text))
    }

    override fun enterLexerRuleName(ctx: ClarityParser.LexerRuleNameContext) {
        parserBuilder.currentRule.atoms.add(LexerAtom(ctx.text))
    }

    override fun enterEmpty(ctx: ClarityParser.EmptyContext?) {
        parserBuilder.currentRule.atoms.add(EmptyAtom)
    }
}

