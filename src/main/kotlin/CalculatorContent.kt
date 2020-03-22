import exception.TooManyOperatorsException

class CalculatorContent {

    val operators = "+-*/"

    var result = String()

    fun addSignToResult(sign: String) {
        if (operators.contains(sign)) {
            checkIfLastCharIsOperator()
        }

        result += sign
    }

    private fun checkIfLastCharIsOperator() {
        println(result.substring(result.length - 1))
        if (operators.contains(result.substring(result.length - 1))) {
            throw TooManyOperatorsException("Can't add another operator")
        }
    }
}