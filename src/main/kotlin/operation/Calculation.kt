package operation

import com.google.common.base.CharMatcher
import exception.InvalidOperationException
import java.util.regex.Pattern

@Suppress("NAME_SHADOWING")
class Calculation {
    private val REGEX = "[-+*/^]"
    private val rpn = RPN()

    fun calculate(operation: String?): Double {
        var operation = operation
        if (operation == null || operation == "") {
            throw IllegalArgumentException("Invalid input")
        }

        operation = operation.replace("\\s+".toRegex(), "")
        checkIfTwoOperandsAppearSideBySide(operation)
        checkIfOperationContainsInvalidNumbers(operation)
        checkIfBracketsInOperationAreValid(operation)

        while (operation!!.contains("%")) {
            operation = removePercentage(operation)
        }
        while (operation!!.contains("(")) {
            operation = removeMiddleBrackets(operation)
        }

        return rpn.compute(operation)
    }

    private fun removeMiddleBrackets(operation: String): String {
        var operation = operation
        val indexOfOpeningBracket = operation.lastIndexOf("(") + 1
        val tempString = operation.substring(indexOfOpeningBracket)
        val indexOfClosingBracket = tempString.indexOf(")")
        val insideBracketsResult = tempString.substring(0, indexOfClosingBracket)
        val numbers = insideBracketsResult.split(REGEX.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var result = 0.0

        if (checkIfAllElementsAreNumbers(numbers)) {
            result = rpn.compute(insideBracketsResult)
        }
        operation = operation.replace(operation.substring(indexOfOpeningBracket - 1, indexOfClosingBracket + indexOfOpeningBracket + 1), result.toString())

        return operation
    }

    private fun checkIfAllElementsAreNumbers(array: Array<String>): Boolean {
        var hasAllNumbers = true

        for (number in array) {
            if (!isNumber(number)) {
                hasAllNumbers = false
                break
            }
        }

        return hasAllNumbers
    }

    private fun removePercentage(operation: String): String {
        var operation = operation
        val indexOfPercentage = operation.indexOf("%") + 1
        var tempString = operation.substring(0, indexOfPercentage)
        tempString = getLastNumberFromString(tempString)
        val indexOfNumber = indexOfPercentage - tempString.length
        val convertedNumber = java.lang.Double.parseDouble(tempString) / 100
        tempString = convertedNumber.toString()
        operation = operation.replace(operation.substring(indexOfNumber - 1, indexOfPercentage), tempString)

        return operation
    }

    private fun getLastNumberFromString(operation: String): String {
        val p = Pattern.compile("[0-9]+")
        val m = p.matcher(operation)
        var result = ""
        while (m.find()) {
            result = m.group()
        }

        return result
    }

    private fun checkIfOperationContainsInvalidNumbers(operation: String) {
        var operation = operation
        operation = operation.replace("[()%]".toRegex(), "")
        val numbers = operation.split(REGEX.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (number in numbers) {
            if (!isNumber(number)) {
                throw InvalidOperationException("Invalid number detected: $number")
            }
        }
    }

    private fun checkIfBracketsInOperationAreValid(operation: String) {
        if (countOccurrencesOfCharacter(operation, '(') > countOccurrencesOfCharacter(operation, ')')) {
            throw InvalidOperationException("An extra left parenthesis detected")
        } else if (countOccurrencesOfCharacter(operation, '(') < countOccurrencesOfCharacter(operation, ')')) {
            throw InvalidOperationException("An extra right parenthesis detected")
        }
    }

    private fun checkIfTwoOperandsAppearSideBySide(operation: String) {
        var signs: String
        for (i in 0 until operation.length - 2) {
            signs = operation.substring(i, i + 2)
            if (signs.matches("[-+*/^]+".toRegex())) {
                throw InvalidOperationException("Missing number between " + signs[0] + " and " + signs[1] + " operators")
            }
        }
    }

    private fun isNumber(str: String): Boolean {
        try {
            java.lang.Double.parseDouble(str)
            return true
        } catch (e: Exception) {
            return false
        }

    }

    private fun countOccurrencesOfCharacter(operation: String, symbol: Char): Int {
        return CharMatcher.`is`(symbol).countIn(operation)
    }
}