package calculator

import calculator.grammar.CalculatorLexer
import calculator.grammar.CalculatorParser
import kotlin.test.Test

object Tests {
    val tests = listOf(
        "5 - 3 - 2"
    )
    @Test
    fun cases() {
        for (test in tests) {
            println("running $test...")
            val lexer = CalculatorLexer(test)
            val parser = CalculatorParser(lexer)
            val res = parser.applyExpr()
            println("$test is ok")
            println(res.data)
        }
    }
}