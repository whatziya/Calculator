package com.example.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {

    private val _equationText = MutableLiveData("")
    val equationText: MutableLiveData<String> = _equationText

    private val _resultText = MutableLiveData("0")
    val resultText: MutableLiveData<String> = _resultText
    fun onButtonClick(btn: String) {
        _equationText.value?.let {
            when (btn) {
                "AC" -> {
                    _equationText.value = ""
                    _resultText.value = "0"
                }

                "C" -> {
                    _equationText.value = _equationText.value?.dropLast(1)
                }

                "=" -> {
                    _equationText.value = _resultText.value
                }

                else -> {
                    if (_equationText.value!!.isNotEmpty() && _equationText.value!!.last() in listOf(
                            '+',
                            '-',
                            '*',
                            '/'
                        ) && btn in listOf(")")
                    ) {
                        return
                    } else if (_equationText.value!!.isNotEmpty() && btn == ")" && _equationText.value!!.last() in listOf(
                            '+',
                            '-',
                            '*',
                            '/'
                        )
                    ) {
                        return
                    } else if (_equationText.value!!.isNotEmpty() && _equationText.value!!.last() == ')' && btn in listOf(
                            "1",
                            "2",
                            "3",
                            "4",
                            "5",
                            "6",
                            "7",
                            "8",
                            "9",
                            "0",
                            "."
                        )
                    ) {
                        return
                    } else if (_equationText.value!!.isNotEmpty() && btn == "(" && _equationText.value!!.last() in listOf(
                            '1',
                            '2',
                            '3',
                            '4',
                            '5',
                            '6',
                            '7',
                            '8',
                            '9',
                            '0',
                            '.'
                        )
                    ) {
                        return
                    } else if (_equationText.value!!.isNotEmpty() && _equationText.value!!.last() == '(' && btn == ")") {
                        return
                    } else if (_equationText.value!!.isNotEmpty() && _equationText.value!!.last() == ')' && btn == "(") {
                        return
                    } else {
                        _equationText.value += btn
                    }
                }
            }

            try {
                _resultText.value = calculateResult(_equationText.value!!.toString())
            } catch (_: Exception) {
            }
        }
    }

    private fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var result = context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        if (result.endsWith(".0")) {
            result = result.replace(".0", "")
        }
        return result
    }
}