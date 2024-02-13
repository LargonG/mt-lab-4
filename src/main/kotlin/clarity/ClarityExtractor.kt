package clarity

import clarity.grammar.LexerRules
import clarity.grammar.ParserRules
import clarity.grammar.atoms.EmptyAtom
import clarity.grammar.atoms.LexerAtom
import clarity.grammar.atoms.ParserAtom
import clarity.grammar.atoms.RegexAtom
import clarity.grammar.rule.ParserRule
import org.largong.ClarityBaseListener
import org.largong.ClarityParser

class ClarityExtractor : ClarityBaseListener() {
    private val lexerRules = LexerRules()

    private val parserRules = ParserRules()

    private var _parsed = false

    private var started = true

    fun getLexer() = if (_parsed) lexerRules else throw IllegalStateException()
    fun getParser() = if (_parsed) parserRules else throw IllegalStateException()

    override fun enterGram(ctx: ClarityParser.GramContext?) {
        // если захотим сделать многопоточным (что в целом сделать очень сложно в контексте antlr4)
        _parsed = false
    }

    override fun exitGram(ctx: ClarityParser.GramContext?) {
        _parsed = true
    }

    override fun enterLexerRuleDeclaration(ctx: ClarityParser.LexerRuleDeclarationContext) {
        lexerRules.currentName = ctx.text
    }

    override fun enterLexerString(ctx: ClarityParser.LexerStringContext) {
        val text = ctx.text
        lexerRules.currentRule.atoms.add(
            RegexAtom(Regex.fromLiteral(text.substring(1 until text.length)))
        )
    }

    override fun enterLexerRegex(ctx: ClarityParser.LexerRegexContext) {
        val text = ctx.text
        lexerRules.currentRule.atoms.add(
            RegexAtom(Regex(text.substring(1 until text.length)))
        )
    }

    override fun enterParserRuleDeclaration(ctx: ClarityParser.ParserRuleDeclarationContext) {
        parserRules.currentName = ctx.text
    }

    override fun exitParserRuleDeclaration(ctx: ClarityParser.ParserRuleDeclarationContext?) {
        if (started) {
            parserRules.startRule = parserRules.currentRule as ParserRule
            started = false
        }
    }

    override fun enterParserRuleName(ctx: ClarityParser.ParserRuleNameContext) {
        parserRules.currentRule.atoms.add(ParserAtom(ctx.text))
    }

    override fun enterLexerRuleName(ctx: ClarityParser.LexerRuleNameContext) {
        parserRules.currentRule.atoms.add(LexerAtom(ctx.text))
    }

    override fun enterEmpty(ctx: ClarityParser.EmptyContext?) {
        parserRules.currentRule.atoms.add(EmptyAtom)
    }
}

