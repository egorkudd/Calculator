package com.example.calculator.services

import android.widget.TextView
import com.example.calculator.enums.InputToken

class InputBuilder(private var input: TextView, private var output: TextView) {

    private var cntOpenBrackets: Int = 0
    private var hasPoint: Boolean = false
    private val inputVals: MutableList<InputToken> = mutableListOf(InputToken.OPEN_BRACKET)

    fun addNumber(str: String) {
        if (isCompletedPoly()) {
            input.append("*")
            hasPoint = false
        }

        input.append(str)
        inputVals.add(InputToken.NUMBER)
    }

    fun addLetter(str: String) {
        if (inputVals[inputVals.size - 1] == InputToken.POINT) return
        if (isPossibleToCompletePoly()) {
            input.append("*")
            hasPoint = false
        }

        input.append(str)
        inputVals.add(InputToken.LETTER)
    }

    fun addBinOper(str: String) {
        if (inputVals[inputVals.size - 1] == InputToken.POINT) return

        if (isPossibleToCompletePoly()) {
            input.append(str)
            inputVals.add(InputToken.BIN_OP)
            hasPoint = false
        }
    }

    fun addMinus() {
        if (inputVals[inputVals.size - 1] == InputToken.POINT) return

        if (isPossibleToCompletePoly() ||
            inputVals[inputVals.size - 1] == InputToken.OPEN_BRACKET ||
            inputVals[inputVals.size - 1] == InputToken.UNO_OP
        ) {
            input.append("-")
            inputVals.add(InputToken.BIN_OP)
            hasPoint = false
        }
    }

    fun addUnoOper(str: String) {
        if (inputVals[inputVals.size - 1] == InputToken.POINT) return

        if (isPossibleToCompletePoly()) {
            input.append("*")
            hasPoint = false
        }

        input.append(str)
        inputVals.add(InputToken.UNO_OP)
        cntOpenBrackets++
    }

    fun addOpenBracket() {
        if (inputVals[inputVals.size - 1] == InputToken.POINT) return

        if (isPossibleToCompletePoly()) {
            input.append("*")
            hasPoint = false
        }

        cntOpenBrackets++
        input.append("(")
        inputVals.add(InputToken.OPEN_BRACKET)
    }

    fun addCloseBracket() {
        if (cntOpenBrackets == 0 || inputVals[inputVals.size - 1] == InputToken.POINT) return
        if (isPossibleToCompletePoly()) {
            cntOpenBrackets--
            input.append(")")
            inputVals.add(InputToken.CLOSE_BRACKET)
            hasPoint = false
        }
    }

    fun addPoint() {
        if (inputVals[inputVals.size - 1] == InputToken.NUMBER && !hasPoint) {
            input.append(".")
            inputVals.add(InputToken.POINT)
            hasPoint = true
        }
    }

    private fun isCompletedPoly(): Boolean {
        return inputVals[inputVals.size - 1] == InputToken.LETTER ||
                inputVals[inputVals.size - 1] == InputToken.CLOSE_BRACKET
    }

    private fun isPossibleToCompletePoly(): Boolean {
        return inputVals[inputVals.size - 1] == InputToken.NUMBER || isCompletedPoly()
    }

    fun clearLastVal() {
        if (input.length() == 0) return
        val removedVal = inputVals.removeLast()
        if (removedVal == InputToken.UNO_OP && input.text.endsWith("ln(")) {
            input.text = input.text.subSequence(0, input.length() - 3)
        } else if (removedVal == InputToken.UNO_OP && input.text.endsWith("√(")) {
            input.text = input.text.subSequence(0, input.length() - 2)
        } else if (removedVal == InputToken.UNO_OP) {
            input.text = input.text.subSequence(0, input.length() - 1)
            hasPoint = false
        } else if (removedVal == InputToken.CLOSE_BRACKET) {
            input.text = input.text.subSequence(0, input.length() - 1)
            cntOpenBrackets++
        } else {
            input.text = input.text.subSequence(0, input.length() - 1)
        }
    }

    fun clear() {
        input.text = ""
        output.text = ""
        inputVals.clear()
        inputVals.add(InputToken.OPEN_BRACKET)
        hasPoint = false
    }
}