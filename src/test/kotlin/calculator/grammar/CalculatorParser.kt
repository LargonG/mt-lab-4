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


    // rules
    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applyExpr(): Node<Result> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("expr", Result(), mutableListOf())
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
                val sum = mutableListOf<Result>()
                val exprCont = mutableListOf<Result>()


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
                sum.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont())
                exprCont.add(root.children.last().data as Result)



                // code
                
            }
            // FIRST
            Token.NUMBER -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val sum = mutableListOf<Result>()
                val exprCont = mutableListOf<Result>()


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
                sum.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont())
                exprCont.add(root.children.last().data as Result)



                // code
                
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applyExprCont(): Node<Result> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("exprCont", Result(), mutableListOf())
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
                val sum = mutableListOf<Result>()
                val exprCont = mutableListOf<Result>()


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
                sum.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont())
                exprCont.add(root.children.last().data as Result)



                // code
                
            }
            // FIRST
            Token.SUB -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val SUB = mutableListOf<Result>()
                val sum = mutableListOf<Result>()
                val exprCont = mutableListOf<Result>()


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
                sum.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applyExprCont())
                exprCont.add(root.children.last().data as Result)



                // code
                
            }
            // FOLLOW
            Token.EOF -> {
                // do nothing
            }
            // FOLLOW
            Token.CLOSE -> {
                // do nothing
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applySum(): Node<Result> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("sum", Result(), mutableListOf())
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
                val atom = mutableListOf<Result>()
                val sumCont = mutableListOf<Result>()


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
                atom.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont())
                sumCont.add(root.children.last().data as Result)



                // code
                
            }
            // FIRST
            Token.NUMBER -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val atom = mutableListOf<Result>()
                val sumCont = mutableListOf<Result>()


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
                atom.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont())
                sumCont.add(root.children.last().data as Result)



                // code
                
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applySumCont(): Node<Result> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("sumCont", Result(), mutableListOf())
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
                val atom = mutableListOf<Result>()
                val sumCont = mutableListOf<Result>()


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
                atom.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont())
                sumCont.add(root.children.last().data as Result)



                // code
                
            }
            // FIRST
            Token.DIV -> {
                var nextContext: TerminalContext

                // rules
                // (val <rule-name> = mutableListOf<<rule-name>Result>())*
                val DIV = mutableListOf<Result>()
                val atom = mutableListOf<Result>()
                val sumCont = mutableListOf<Result>()


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
                atom.add(root.children.last().data as Result)

                /*
                    root.children.add(apply<rule-name>(<args>))
                    <rule-name>.add(root.children.last().data)
                */
                root.children.add(applySumCont())
                sumCont.add(root.children.last().data as Result)



                // code
                
            }
            // FOLLOW
            Token.ADD -> {
                // do nothing
            }
            // FOLLOW
            Token.SUB -> {
                // do nothing
            }
            // FOLLOW
            Token.EOF -> {
                // do nothing
            }
            // FOLLOW
            Token.CLOSE -> {
                // do nothing
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }

    // apply<rule-name>(<args>): Node<<rule-name>Result>
    fun applyAtom(): Node<Result> {
        val context = lexer.lookToken() ?: throw IllegalStateException()
        val root = Node("atom", Result(), mutableListOf())
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
                val expr = mutableListOf<Result>()
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
                expr.add(root.children.last().data as Result)

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
                
            }


            else -> throw IllegalStateException("No excepted: ${context.token}")
        }

        return root
    }


}
