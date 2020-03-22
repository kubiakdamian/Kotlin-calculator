package operation

import exception.DivisionByZeroException
import exception.InvalidOperationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class CalculationTest {

    val objectUnderTest = Calculation()

    @Test
    fun shouldRealizeBasicOperations() {
        assertThatProperResultWasReturned("1+2.5", 3.5)
        assertThatProperResultWasReturned("5^2", 25.0)
        assertThatProperResultWasReturned("3*7", 21.0)
        assertThatProperResultWasReturned("8/2", 4.0)
        assertThatProperResultWasReturned("2.5-5", -2.5)
    }

    @Test
    fun shouldIgnoreWhiteSpaces() {
        assertThatProperResultWasReturned(" 1 +   2.5", 3.5)
        assertThatProperResultWasReturned("5^2    ", 25.0)
        assertThatProperResultWasReturned(" 3*7", 21.0)
        assertThatProperResultWasReturned("8  / 2", 4.0)
        assertThatProperResultWasReturned(" 2 . 5 - 5", -2.5)
    }

    @Test
    fun shouldRealizeCompoundOperation() {
        assertThatProperResultWasReturned("2 + 2 * 4", 10.0)
        assertThatProperResultWasReturned("2 + 2 + 4", 8.0)
        assertThatProperResultWasReturned(" 1 +   2.5 * 3", 8.5)
        assertThatProperResultWasReturned(" 1 +   2.5 * 3^2", 23.5)
        assertThatProperResultWasReturned("5^2+3", 28.0)
        assertThatProperResultWasReturned(" 3*7*3*2", 126.0)
    }

    @Test
    fun shouldThrowDivisionByZeroException() {
        assertThatResultThrowsDivisionByZeroException("1 / 0", "Division by zero")
        assertThatResultThrowsDivisionByZeroException("1 / (1-1)", "Division by zero")
        assertThatResultThrowsDivisionByZeroException("1 / ( 3 * 0)", "Division by zero")
        assertThatResultThrowsDivisionByZeroException("1+ 1/ (2 - 2)", "Division by zero")
    }

    @Test
    fun shouldThrowInvalidOperationException() {
        assertThatResultThrowsInvalidOperationException("2a*3", "Invalid number detected: 2a")
        assertThatResultThrowsInvalidOperationException("((2.4-(0.4 + 1#as)) * 3)^2", "Invalid number detected: 1#as")
        assertThatResultThrowsInvalidOperationException("((2.4-(0.4 + vd1)) * 3)^2", "Invalid number detected: vd1")
        assertThatResultThrowsInvalidOperationException("2* (3.4 + 6))", "An extra right parenthesis detected")
        assertThatResultThrowsInvalidOperationException("2* ((3.4 + 6)", "An extra left parenthesis detected")
        assertThatResultThrowsInvalidOperationException("1 -* 3", "Missing number between - and * operators")
        assertThatResultThrowsInvalidOperationException("1+2*3 /^ 3", "Missing number between / and ^ operators")
    }

    @Test
    fun shouldRealizeOperationWithBrackets() {
        assertThatProperResultWasReturned("((2 + 2 + 2) * 5)", 30.0)
        assertThatProperResultWasReturned("( 1 +   2.5) * 3^2", 31.5)
        assertThatProperResultWasReturned("((2.4-0.4) * 3)^2", 36.0)
        assertThatProperResultWasReturned("((2.4-(0.4 + 1)) * 3)^2", 9.0)
        assertThatProperResultWasReturned("(1+1)*(2+2)", 8.0)
        assertThatProperResultWasReturned("((2.4 - 0.4) * 3)^4", 1296.0)
        assertThatProperResultWasReturned("((2+2*(2+2))*(3*(2+2)))*3", 360.0)
    }

    @Test
    fun shouldRealizeOperationWithPercentage() {
        assertThatProperResultWasReturned("2*75%", 1.5)
        assertThatProperResultWasReturned("2*150%", 3.0)
        assertThatProperResultWasReturned("2*10000%", 200.0)
        assertThatProperResultWasReturned("(1+1)*50%", 1.0)
        assertThatProperResultWasReturned("((2+2*(2+2))*(3*75%))*3", 67.5)
    }

    private fun assertThatProperResultWasReturned(operation: String, expectedResult: Double) {
        assertEquals(objectUnderTest.calculate(operation), expectedResult)
    }

    private fun assertThatResultThrowsDivisionByZeroException(operation: String, expectedMessage: String) {
        val divisionByZeroException = assertThrows(DivisionByZeroException::class.java) { objectUnderTest.calculate(operation) }
        assertTrue(divisionByZeroException.message!!.contains(expectedMessage))
    }

    private fun assertThatResultThrowsInvalidOperationException(operation: String, expectedMessage: String) {
        val invalidOperationException = assertThrows(InvalidOperationException::class.java) { objectUnderTest.calculate(operation) }
        assertTrue(invalidOperationException.message!!.contains(expectedMessage))
    }
}