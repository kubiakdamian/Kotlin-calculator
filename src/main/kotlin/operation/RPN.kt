package operation

import exception.DivisionByZeroException
import java.util.*

@Suppress("NAME_SHADOWING")
class RPN {

    @Throws(ArithmeticException::class, EmptyStackException::class)
    fun compute(expr: String): Double {
        var expr = expr

        val stack = Stack<Double>()
        expr = expr.replace("\\s+".toRegex(), "")

        val numbers = expr.split("[-+*/^]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var operands = expr.split("[\\d.]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        operands = removeEmptyChars(operands)

        val combinedData = combineTwoArrays(numbers, operands)
        val dataConvertedToRPN = convertInfixToRPN(combinedData)
        val finalString = convertArrayToString(dataConvertedToRPN)

        for (token in finalString.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            when (token) {
                "+" -> stack.push(stack.pop() + stack.pop())
                "-" -> stack.push(-stack.pop() + stack.pop())
                "*" -> stack.push(stack.pop() * stack.pop())
                "/" -> {
                    val divisor = stack.pop()
                    if (divisor == 0.0) {
                        throw DivisionByZeroException("Division by zero")
                    }
                    stack.push(stack.pop() / divisor)
                }
                "^" -> {
                    val exponent = stack.pop()
                    stack.push(Math.pow(stack.pop(), exponent))
                }
                else -> stack.push(java.lang.Double.parseDouble(token))
            }
        }

        return stack.pop()
    }

    private fun convertInfixToRPN(infixNotation: Array<String>): Array<String> {
        val operands = HashMap<String, Int>()
        operands["/"] = 5
        operands["*"] = 5
        operands["+"] = 4
        operands["-"] = 4
        operands["^"] = 6

        val L = ArrayList<String>()
        val S = Stack<String>()

        for (token in infixNotation) {
            if (operands.containsKey(token)) {
                while (!S.empty() && operands[token]!! <= operands[S.peek()]!!) {
                    L.add(S.pop())
                }
                S.push(token)
                continue
            }
            if (isNumber(token)) {
                L.add(token)
                continue
            }
            throw IllegalArgumentException("Invalid input")
        }
        while (!S.isEmpty()) {
            L.add(S.pop())
        }

        return L.toTypedArray()
    }

    private fun isNumber(str: String): Boolean {
        return try {
            java.lang.Double.valueOf(str)
            true
        } catch (e: Exception) {
            false
        }

    }

    private fun removeEmptyChars(operands: Array<String>): Array<String> {
        val operandsList = ArrayList<String>()
        for (operand in operands) {
            if (operand != "") {
                operandsList.add(operand)
            }
        }

        return operandsList.toTypedArray()
    }

    private fun combineTwoArrays(numbers: Array<String>, operands: Array<String>): Array<String> {
        val combined = ArrayList<String>()
        for (i in numbers.indices) {
            combined.add(numbers[i])
            if (i < operands.size) {
                combined.add(operands[i])
            }
        }

        return combined.toTypedArray()
    }

    private fun convertArrayToString(array: Array<String>): String {
        val builder = StringBuilder()
        for (element in array) {
            builder.append(element).append(" ")
        }

        return builder.toString().trim { it <= ' ' }
    }
}