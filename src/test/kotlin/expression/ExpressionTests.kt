package expression

import expression.grammar.ExpressionLexer
import expression.grammar.ExpressionParser
import guru.nidi.graphviz.attribute.Label
import guru.nidi.graphviz.attribute.Rank
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory.graph
import guru.nidi.graphviz.model.Factory.node
import guru.nidi.graphviz.model.Graph
import guru.nidi.graphviz.model.Node
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.absoluteValue
import kotlin.random.Random
import kotlin.test.Test

object ExpressionTests {

    private val tests = listOf(
        "(1+2)*sin(-3*(7-4)+2)", // пример
        "42", // число
        "109 + 10", // сумма
        "6-7876", // разность
        "56", // другое число
        "42 * 9", // произведение
        "5 * 2 + 10", // произведение с суммой
        "-105234", // отрицательное число
        "--1543", // двойной унарный минус
        "-------5674323", // больше унарных минусов
        "---7 - 565585646453 --- 74575674 ---- 36574432", // унарные минусы с вычитанием
        "-2 + 4 * 5 + 3", // унарный минус в начале + выражение
        "---4", // ещё унарные минусы
        "sin(1)", // функция
        "f(300)", // ещё функция
        "lambda(sin(2))", // функция от функции
        "sin(cos(1))", // функция от функции
        "sin(1)*sin(2)+cos(1)*cos(2)", // выражение с функциями
        "(1+2)*sin(-3*(7-4)+2)+50-10*5", // усложнённый пример
        "(((5 * 3) + 2 * (5 + -3)) + 599 * (100 + 43245) - 154 + (1200 - 200)) - (500 + 10000)", // случайное сложное выражение
        "   -  (100 + 2)    ", // пробелы
        "                  -          (                  100 + 5 - (55  --     2313    +     5345      *           43    )    *   5)         ", // больше пробелов
        "1 - 2 - 3 - 4 - 5", // проверка на право ассоциативность минуса
        "calc(502, 6345, sin(5,2353, 6456))", // функция с множеством аргументов от функции
        "f(100, 400, 52)", // функция с множеством аргументов
        "2353  + 546 * iytrewr(1 * 5346 + 56456, 2 + -2453 * 457)", // функция с множеством аргументов с выражениями
        "hello(my(dear(4324565, 45, 6546, 124345 ), world(349, 354)), welcome(356), to(456, 23), the(chat(5467)))", // функция от функции от функции от функции ....
        "hello(my(dear(4324565,         sin(2345),         6546        , 124345 ), world(      349, 354      )), welcome(          356           ), to(             456                   ,        23           ), the(chat(    5467    , 5646854   )))", // пробелы, табы в функциях от нескольких переменных
        "zero", // нуль арная функция
        "variable + x + y - z * t", // переменные
        "someOtherFunction(hello + 5, y - x, 1 * 2 + 6 - 7)", // функция от нескольких переменных
        "zero + one(5) + two(3, sin(x))", // много функций
        "f()",
        "g() + 5",
        "g() + y(123) - u(r))"
    )

    private val iterations = 100

    private val charPool = ('a'..'z') + ('A'..'Z')

    @Test
    fun cases() {
        for (i in tests.indices) {
            val test = tests[i]
            val lexer = ExpressionLexer(test)
            val parser = ExpressionParser(lexer)
            val res = parser.applyExpr()

            saveToFile(res.visualize(), "out/cases/", test, "$i")
        }
    }

    @Test
    fun random() {
        for (i in 1..iterations) {
            val expr = generateRandomArithmetics(5, Random)
            val lexer = ExpressionLexer(expr.toSuffixString())
            val parser = ExpressionParser(lexer)
            val res = parser.applyExpr()

            saveToFile(res.visualize(), "out/random/", expr.toSuffixString(), "$i")
        }
    }

    private fun saveToFile(myGraph: Graph, dir: String, expr: String, filename: String) {
        Graphviz
            .fromGraph(myGraph)
            .render(Format.SVG)
            .toFile(Path.of("$dir/$filename.svg").toFile())

        val path = Path.of(dir)
        if (!Files.exists(path)) {
            Files.createDirectories(path)
        }
        Files.writeString(Path.of("$dir/$filename.txt"), expr)
    }

    private fun ExpressionParser.Node<*>.visualize(): Graph
        = this.visualize(
            null,
            graph("expression graph")
                .directed()
                .strict()
                .graphAttr().with(Rank.dir(Rank.RankDir.BOTTOM_TO_TOP)),
            mutableMapOf()
        )



    private fun ExpressionParser.Node<*>.visualize(parent: Node?, graph: Graph, count: MutableMap<String, Int>): Graph {
        if (!count.containsKey(this.name)) {
            count[this.name] = 0
        }
        var myNode = node("${this.name}${count[this.name]}")

        myNode = if (this.children.isEmpty() && (this.data as ExpressionParser.Result).text.isNotEmpty()) {
            myNode.with(Label.of(this.data.text))
        } else {
            myNode.with(Label.of(this.name))
        }

        if (parent != null) {
            myNode = myNode.link(parent.name().value())
        }

        count[this.name] = count[this.name]!! + 1
        var myGraph = graph.with(myNode)
        for (child in children) {
            myGraph = child.visualize(myNode, myGraph, count)
        }
        return myGraph
    }

    private fun generateRandomArithmetics(depth: Int, random: Random): Arithmetics =
        when (val key = random.nextInt().absoluteValue % (if (depth == 0) 1 else 6)) {
            0 -> Const(BigInteger((random.nextInt().absoluteValue % 100).toString()))
            1 -> UnaryOperation(Negate, generateRandomArithmetics(depth - 1, random))
            2, 3, 4 -> {
                val op: BinaryOperator = when (key) {
                    2 -> Add
                    3 -> Sub
                    4 -> Mul
                    else -> throw IllegalStateException()
                }
                BinaryOperation(
                    op,
                    generateRandomArithmetics(depth - 1, random),
                    generateRandomArithmetics(depth - 1, random)
                )
            }
            5 -> Function(randomString(3, random),
                generateRandomList(
                    depth - 1,
                    random.nextInt().absoluteValue % 6,
                    random))
            else -> throw IllegalStateException("value = $key")
        }

    private fun randomString(len: Int, random: Random): String = (1..len)
        .map { random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")

    private fun generateRandomList(depth: Int, len: Int, random: Random): List<Arithmetics> {
        val result = mutableListOf<Arithmetics>()
        for (i in 1..len) {
            result.add(generateRandomArithmetics(depth, random))
        }
        return result
    }

}