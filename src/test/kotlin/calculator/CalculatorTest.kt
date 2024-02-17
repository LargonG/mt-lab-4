package calculator

import calculator.grammar.CalculatorLexer
import calculator.grammar.CalculatorParser
import kotlin.test.Test

object Tests {
    val tests = listOf(
        "   1345   +   22134   +   325  +  4234  +   512423   ",
        "           14234       -  \n" +
                "2765754  -  3123  -  43452  -  53453",
        "557 + 789745 * 5344 - 1234 * (   125 - 3453 +  154  )",
        "55 * 66 / 77",
        "55 / 60 / 40",
        "10 + 5 / 2",
    )
    @Test
    fun cases() {
        for (test in tests) {
            println("running $test...")
            val lexer = CalculatorLexer(test)
            val parser = CalculatorParser(lexer)
            println(parser.applyExpr())
            println("$test is ok")
        }
    }
}