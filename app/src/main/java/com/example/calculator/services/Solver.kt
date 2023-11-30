package com.example.calculator.services

import kotlin.math.ln
import kotlin.math.roundToInt
import kotlin.math.sqrt

class Solver() {
    private var expr: String = ""

    fun changeVarsToNumbers(expression: String, vars: List<String>): String {
        return expression
            .replace("x", vars[0])
            .replace("y", vars[1])
            .replace("z", vars[2])
    }

    fun solve(expression: String): Double {
        expr = expression
        changeBinMinusToPlusMinus()
        solveBrackets()

        if (expr.contains('+')) {
            return (solveSum() * 10000.0).roundToInt() / 10000.0
        }

        if (expr.contains('*') || expr.contains('/')) {
            return (solveMulAndDiv() * 10000.0).roundToInt() / 10000.0
        }

        if (!expr.contains('.')) {
            expr = "${expr}.0"
        }

        if (expr.contains("ln")) {
            val digit: Double = expr.substring(2, expr.length).toDouble()
            if (digit <= 0) throw Exception("Ln of neg num")
            return (ln(digit) * 10000.0).roundToInt() / 10000.0
        }

        if (expr.contains("√")) {
            val digit: Double = expr.substring(1, expr.length).toDouble()
            if (digit < 0) throw Exception("Root of neg num")
            return (sqrt(digit) * 10000.0).roundToInt() / 10000.0
        }

        return expr.toDouble()
    }

    private fun changeBinMinusToPlusMinus() {
        val charsBeforeBinMinus: Array<Char> =
            arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ')', 'x', 'y', 'z')
        var i: Int = 0
        while (i < expr.length) {
            if (expr[i] == '-' &&
                i > 0 && charsBeforeBinMinus.contains(expr[i - 1])
            ) {
                val subExprBefore: String = expr.substring(0, i)
                val subExprAfter: String = expr.substring(i, expr.length)
                expr = "$subExprBefore+$subExprAfter"
            }

            i++
        }
    }

    private fun solveBrackets() {
        val openBracketInds: ArrayList<Int> = ArrayList()
        var i: Int = 0
        while (i < expr.length) {
            if (expr[i] == '(') openBracketInds.add(i)
            if (expr[i] == ')') {
                val lastInd: Int = openBracketInds.removeLast()
                val res: Double = Solver().solve(expr.substring(lastInd + 1, i))

                val exprBeforeBrackets: String = expr.substring(0, lastInd)
                val exprAfterBrackets: String = expr.substring(i + 1, expr.length)
                expr = "$exprBeforeBrackets$res$exprAfterBrackets"

                i = lastInd
            }

            i++
        }
    }

    private fun solveSum(): Double {
        for (i: Int in expr.length - 1 downTo 0) {
            if (expr[i] == '+') {
                val expr1: Double = Solver().solve(expr.substring(0, i))
                val expr2: Double = Solver().solve(expr.substring(i + 1, expr.length))
                return expr1 + expr2
            }
        }

        throw Exception("Method: solveSum(): $expr")
    }

    private fun solveMulAndDiv(): Double {
        for (i: Int in expr.length - 1 downTo 0) {
            if (expr[i] == '*') {
                val expr1: Double = Solver().solve(expr.substring(0, i))
                val expr2: Double = Solver().solve(expr.substring(i + 1, expr.length))
                return expr1 * expr2
            } else if (expr[i] == '/') {
                val expr1: Double = Solver().solve(expr.substring(0, i))
                val expr2: Double = Solver().solve(expr.substring(i + 1, expr.length))
                if (expr2 == 0.0) throw Exception("Division by 0")
                return expr1 / expr2
            }
        }

        throw Exception("Method: solveMulAndDiv(): $expr")
    }
}