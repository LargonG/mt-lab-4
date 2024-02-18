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
        Pair("1 * 0", 0.0),
        Pair("100 * 40", 4000.0),
        Pair("346324 + 52366 * 353 / 9563 - 5351 + 7563 - 4367 *5234 / 3456", 343855.3115530028),
        Pair("42", 42.0),
        Pair("83 * 5 - 10 / 4", 412.5),
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