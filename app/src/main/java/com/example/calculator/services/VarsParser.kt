package com.example.calculator.services

import android.widget.TextView

class VarsParser(private val input: TextView) {
    var hasPoint: Boolean = false

    fun parse(): List<String> {
        return input.text.split("; ").map {
            part -> part.substring(2, part.length)
        }
    }

    fun addNumber(str: String) {
        input.append(str)
    }

    fun addPoint() {
        if (!hasPoint) {
            input.append(".")
            hasPoint = true
        }
    }

    fun addMinus() {
        if (input.text[input.length() - 1] == '=') {
            input.append("-")
        }
    }
}