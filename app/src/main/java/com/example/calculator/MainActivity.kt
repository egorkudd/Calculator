package com.example.calculator

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.calculator.services.InputBuilder
import com.example.calculator.services.Solver
import com.example.calculator.services.VarsParser

class MainActivity : ComponentActivity() {

    private lateinit var input: TextView
    private lateinit var vars: TextView
    private lateinit var output: TextView
    private var cntSolveTaps: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        input = findViewById(R.id.input)
        vars = findViewById(R.id.vars)
        output = findViewById(R.id.output)

        val inputBuilder: InputBuilder = InputBuilder(input, output)
        val varsParser: VarsParser = VarsParser(vars)
        val solver: Solver = Solver()
        setOnClickListenersToInput(inputBuilder, varsParser, solver)
    }

    private fun setOnClickListenersToInput(
        inputBuilder: InputBuilder,
        varsParser: VarsParser,
        solver: Solver
    ) {
        findViewById<TextView?>(R.id.btn_0).setOnClickListener { inputBuilder.addNumber("0") }
        findViewById<TextView?>(R.id.btn_1).setOnClickListener { inputBuilder.addNumber("1") }
        findViewById<TextView?>(R.id.btn_2).setOnClickListener { inputBuilder.addNumber("2") }
        findViewById<TextView?>(R.id.btn_3).setOnClickListener { inputBuilder.addNumber("3") }
        findViewById<TextView?>(R.id.btn_4).setOnClickListener { inputBuilder.addNumber("4") }
        findViewById<TextView?>(R.id.btn_5).setOnClickListener { inputBuilder.addNumber("5") }
        findViewById<TextView?>(R.id.btn_6).setOnClickListener { inputBuilder.addNumber("6") }
        findViewById<TextView?>(R.id.btn_7).setOnClickListener { inputBuilder.addNumber("7") }
        findViewById<TextView?>(R.id.btn_8).setOnClickListener { inputBuilder.addNumber("8") }
        findViewById<TextView?>(R.id.btn_9).setOnClickListener { inputBuilder.addNumber("9") }

        findViewById<TextView?>(R.id.btn_x).setOnClickListener { inputBuilder.addLetter("x") }
        findViewById<TextView?>(R.id.btn_y).setOnClickListener { inputBuilder.addLetter("y") }
        findViewById<TextView?>(R.id.btn_z).setOnClickListener { inputBuilder.addLetter("z") }

        findViewById<TextView?>(R.id.btn_sum).setOnClickListener { inputBuilder.addBinOper("+") }
        findViewById<TextView?>(R.id.btn_mul).setOnClickListener { inputBuilder.addBinOper("*") }
        findViewById<TextView?>(R.id.btn_div).setOnClickListener { inputBuilder.addBinOper("/") }

        findViewById<TextView?>(R.id.btn_sub).setOnClickListener { inputBuilder.addMinus() }

        findViewById<TextView?>(R.id.btn_sqrt).setOnClickListener { inputBuilder.addUnoOper("√(") }
        findViewById<TextView?>(R.id.btn_ln).setOnClickListener { inputBuilder.addUnoOper("ln(") }

        findViewById<TextView?>(R.id.btn_open_br).setOnClickListener { inputBuilder.addOpenBracket() }
        findViewById<TextView?>(R.id.btn_close_br).setOnClickListener { inputBuilder.addCloseBracket() }

        findViewById<TextView?>(R.id.btn_point).setOnClickListener { inputBuilder.addPoint() }

        findViewById<TextView?>(R.id.btn_c).setOnClickListener { inputBuilder.clearLastVal() }
        findViewById<TextView?>(R.id.btn_ac).setOnClickListener {
            inputBuilder.clear()
            vars.text = ""
            setOnClickListenersToInput(inputBuilder, varsParser, solver)
            cntSolveTaps = 0
        }

        findViewById<TextView?>(R.id.btn_equal).setOnClickListener {
            setOnClickListenersToVars(varsParser)
            when (cntSolveTaps) {
                0 -> vars.append("x=")
                1 -> {
                    vars.append("; y=")
                    varsParser.hasPoint = false
                }

                2 -> {
                    vars.append("; z=")
                    varsParser.hasPoint = false
                }

                else -> try {
                    val changeExpr: String = solver.changeVarsToNumbers(
                        input.text.toString(),
                        varsParser.parse()
                    )
                    output.text = solver.solve(changeExpr).toString()
                } catch (e: Exception) {
                    output.text = e.message
                }
            }

            cntSolveTaps++
        }
    }

    private fun setOnClickListenersToVars(varsParser: VarsParser) {
        findViewById<TextView?>(R.id.btn_0).setOnClickListener { varsParser.addNumber("0") }
        findViewById<TextView?>(R.id.btn_1).setOnClickListener { varsParser.addNumber("1") }
        findViewById<TextView?>(R.id.btn_2).setOnClickListener { varsParser.addNumber("2") }
        findViewById<TextView?>(R.id.btn_3).setOnClickListener { varsParser.addNumber("3") }
        findViewById<TextView?>(R.id.btn_4).setOnClickListener { varsParser.addNumber("4") }
        findViewById<TextView?>(R.id.btn_5).setOnClickListener { varsParser.addNumber("5") }
        findViewById<TextView?>(R.id.btn_6).setOnClickListener { varsParser.addNumber("6") }
        findViewById<TextView?>(R.id.btn_7).setOnClickListener { varsParser.addNumber("7") }
        findViewById<TextView?>(R.id.btn_8).setOnClickListener { varsParser.addNumber("8") }
        findViewById<TextView?>(R.id.btn_9).setOnClickListener { varsParser.addNumber("9") }

        findViewById<TextView?>(R.id.btn_point).setOnClickListener { varsParser.addPoint() }

        findViewById<TextView?>(R.id.btn_sub).setOnClickListener { varsParser.addMinus() }

        findViewById<TextView?>(R.id.btn_x).setOnClickListener { pass() }
        findViewById<TextView?>(R.id.btn_y).setOnClickListener { pass() }
        findViewById<TextView?>(R.id.btn_z).setOnClickListener { pass() }

        findViewById<TextView?>(R.id.btn_sum).setOnClickListener { pass() }
        findViewById<TextView?>(R.id.btn_mul).setOnClickListener { pass() }
        findViewById<TextView?>(R.id.btn_div).setOnClickListener { pass() }


        findViewById<TextView?>(R.id.btn_sqrt).setOnClickListener { pass() }
        findViewById<TextView?>(R.id.btn_ln).setOnClickListener { pass() }

        findViewById<TextView?>(R.id.btn_open_br).setOnClickListener { pass() }
        findViewById<TextView?>(R.id.btn_close_br).setOnClickListener { pass() }
    }

    private fun pass() {
        return
    }
}