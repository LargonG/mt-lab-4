package calculator

import calculator.grammar.CalculatorLexer
import calculator.grammar.CalculatorParser
import kotlin.math.abs
import kotlin.test.Test

object CalculatorTests {
    private const val EPS = 1e-6

    private val tests = listOf(
        Pair("5 - 3 - 2", 0.0),
        Pair("0 + 219 + 352", 571.0),
        Pair("10 / 50", 0.2),
        Pair("10 / 50 / 2", 0.1),
        Pair("50 * 50 + (20 + 5 - 40) * 30 + 34 / 5", 2056.8),
    )

    @Test
    fun cases() {
        for (test in tests) {
            val lexer = CalculatorLexer(test.first)
            val parser = CalculatorParser(lexer)
            val res = parser.applyExpr()

            assert(abs(res.data.result - test.second) < EPS
            ) { "Calculation failed: excepted: ${test.second}; found: ${res.data.result}" }
        }
    }
}