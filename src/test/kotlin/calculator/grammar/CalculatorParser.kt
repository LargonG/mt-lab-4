/**
 * Генерация пакета
 */
package calculator.grammar

// <file-name> Parser
class CalculatorParser(private val lexer: Lexer) {
    data class Node<T: RuleResult>(
        val name: String,
        val data: T,
        val children: MutableList<Node<*>> = mutableListOf(),
    )

    sealed interface RuleResult

    data class Result(var text: String = ""): RuleResult

    // rules-result
    // <rule-name> = "" if Terminal else name
    // class <rule-name>Result((<field>: <type>)*): RuleResult
    data class ExprResult(var result: Double = 0.0): RuleResult

    data class ExprContResult(var result: Double = 0.0): RuleResult

    data class SumResult(var result: Double = 0.0): RuleResult

    data class SumContResult(var result: Double = 0.0): RuleResult

    data class AtomResult(var result: Double = 0.0): RuleResult


    // rules
    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applyExpr(): Node<ExprResult> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("expr", ExprResult(), mutableListOf())
        val result = root.data
        when (context.token) {
            /*
                for it in first:
                    <first>(it)

                if (first.contains(EMPTY)) {
                     for it in follow:
                         <follow>(it)
                 }
             */

            // FIRST
            Token.OPEN -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val sum = mutableListOf<SumResult>()
                val exprCont = mutableListOf<ExprContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySum())
                sum.add(root.children.last().data as SumResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont(sum[0].result))
                exprCont.add(root.children.last().data as ExprContResult)



                // code
                result.result = exprCont[0].result
            }
            // FIRST
            Token.NUMBER -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val sum = mutableListOf<SumResult>()
                val exprCont = mutableListOf<ExprContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySum())
                sum.add(root.children.last().data as SumResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont(sum[0].result))
                exprCont.add(root.children.last().data as ExprContResult)



                // code
                result.result = exprCont[0].result
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applyExprCont(left: Double = 0.0): Node<ExprContResult> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("exprCont", ExprContResult(), mutableListOf())
        val result = root.data
        when (context.token) {
            /*
                for it in first:
                    <first>(it)

                if (first.contains(EMPTY)) {
                     for it in follow:
                         <follow>(it)
                 }
             */

            // FIRST
            Token.ADD -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val ADD = mutableListOf<Result>()
                val sum = mutableListOf<SumResult>()
                val exprCont = mutableListOf<ExprContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                */
                nextContext = lexer.lookToken() ?: throw IllegalStateException()
                assert(nextContext.token == Token.ADD)
                ADD.add(Result(nextContext.text))
                root.children.add(Node(nextContext.token.name, Result(nextContext.text)))
                lexer.nextToken()

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySum())
                sum.add(root.children.last().data as SumResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont(sum[0].result))
                exprCont.add(root.children.last().data as ExprContResult)



                // code
                result.result = left + exprCont[0].result
            }
            // FIRST
            Token.SUB -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val SUB = mutableListOf<Result>()
                val sum = mutableListOf<SumResult>()
                val exprCont = mutableListOf<ExprContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                */
                nextContext = lexer.lookToken() ?: throw IllegalStateException()
                assert(nextContext.token == Token.SUB)
                SUB.add(Result(nextContext.text))
                root.children.add(Node(nextContext.token.name, Result(nextContext.text)))
                lexer.nextToken()

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySum())
                sum.add(root.children.last().data as SumResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont(sum[0].result))
                exprCont.add(root.children.last().data as ExprContResult)



                // code
                result.result = left - exprCont[0].result
            }
            // FOLLOW
            Token.EOF -> {
                // code
                result.result = left
            }
            // FOLLOW
            Token.CLOSE -> {
                // code
                result.result = left
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applySum(): Node<SumResult> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("sum", SumResult(), mutableListOf())
        val result = root.data
        when (context.token) {
            /*
                for it in first:
                    <first>(it)

                if (first.contains(EMPTY)) {
                     for it in follow:
                         <follow>(it)
                 }
             */

            // FIRST
            Token.OPEN -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val atom = mutableListOf<AtomResult>()
                val sumCont = mutableListOf<SumContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyAtom())
                atom.add(root.children.last().data as AtomResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont(atom[0].result))
                sumCont.add(root.children.last().data as SumContResult)



                // code
                result.result = sumCont[0].result
            }
            // FIRST
            Token.NUMBER -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val atom = mutableListOf<AtomResult>()
                val sumCont = mutableListOf<SumContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyAtom())
                atom.add(root.children.last().data as AtomResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont(atom[0].result))
                sumCont.add(root.children.last().data as SumContResult)



                // code
                result.result = sumCont[0].result
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applySumCont(left: Double = 0.0): Node<SumContResult> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("sumCont", SumContResult(), mutableListOf())
        val result = root.data
        when (context.token) {
            /*
                for it in first:
                    <first>(it)

                if (first.contains(EMPTY)) {
                     for it in follow:
                         <follow>(it)
                 }
             */

            // FIRST
            Token.MUL -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val MUL = mutableListOf<Result>()
                val atom = mutableListOf<AtomResult>()
                val sumCont = mutableListOf<SumContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                */
                nextContext = lexer.lookToken() ?: throw IllegalStateException()
                assert(nextContext.token == Token.MUL)
                MUL.add(Result(nextContext.text))
                root.children.add(Node(nextContext.token.name, Result(nextContext.text)))
                lexer.nextToken()

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyAtom())
                atom.add(root.children.last().data as AtomResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont(atom[0].result))
                sumCont.add(root.children.last().data as SumContResult)



                // code
                result.result = left * sumCont[0].result
            }
            // FIRST
            Token.DIV -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val DIV = mutableListOf<Result>()
                val atom = mutableListOf<AtomResult>()
                val sumCont = mutableListOf<SumContResult>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                */
                nextContext = lexer.lookToken() ?: throw IllegalStateException()
                assert(nextContext.token == Token.DIV)
                DIV.add(Result(nextContext.text))
                root.children.add(Node(nextContext.token.name, Result(nextContext.text)))
                lexer.nextToken()

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyAtom())
                atom.add(root.children.last().data as AtomResult)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont(atom[0].result))
                sumCont.add(root.children.last().data as SumContResult)



                // code
                result.result = left / sumCont[0].result
            }
            // FOLLOW
            Token.ADD -> {
                // code
                result.result = left
            }
            // FOLLOW
            Token.SUB -> {
                // code
                result.result = left
            }
            // FOLLOW
            Token.EOF -> {
                // code
                result.result = left
            }
            // FOLLOW
            Token.CLOSE -> {
                // code
                result.result = left
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applyAtom(): Node<AtomResult> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("atom", AtomResult(), mutableListOf())
        val result = root.data
        when (context.token) {
            /*
                for it in first:
                    <first>(it)

                if (first.contains(EMPTY)) {
                     for it in follow:
                         <follow>(it)
                 }
             */

            // FIRST
            Token.OPEN -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val OPEN = mutableListOf<Result>()
                val expr = mutableListOf<ExprResult>()
                val CLOSE = mutableListOf<Result>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                */
                nextContext = lexer.lookToken() ?: throw IllegalStateException()
                assert(nextContext.token == Token.OPEN)
                OPEN.add(Result(nextContext.text))
                root.children.add(Node(nextContext.token.name, Result(nextContext.text)))
                lexer.nextToken()

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExpr())
                expr.add(root.children.last().data as ExprResult)

                /*
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                */
                nextContext = lexer.lookToken() ?: throw IllegalStateException()
                assert(nextContext.token == Token.CLOSE)
                CLOSE.add(Result(nextContext.text))
                root.children.add(Node(nextContext.token.name, Result(nextContext.text)))
                lexer.nextToken()



                // code
                result.result = expr[0].result
            }
            // FIRST
            Token.NUMBER -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val NUMBER = mutableListOf<Result>()


                /*
                if (isTerminal) {
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                } else {
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                }
                */
                /*
                    assert(terminal)
                    <rule-name>.add(<Result>(context.text))
                    lexer.nextToken()
                */
                nextContext = lexer.lookToken() ?: throw IllegalStateException()
                assert(nextContext.token == Token.NUMBER)
                NUMBER.add(Result(nextContext.text))
                root.children.add(Node(nextContext.token.name, Result(nextContext.text)))
                lexer.nextToken()



                // code
                result.result = Integer.parseInt(NUMBER[0].text).toDouble()
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }


}
