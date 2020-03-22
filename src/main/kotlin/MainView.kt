import javafx.scene.control.TextField
import tornadofx.*

class MainView : View() {

    var result: TextField by singleAssign()
    var calculatorContent = CalculatorContent()

    override val root = vbox{
        hbox {
            result = textfield()
            result.isEditable = false
        }
        hbox {
            button("x2").setOnAction {
                println("Button 1 Pressed")
            }
            button("pier").setOnAction {
                println("Button 2 Pressed")
            }
            button("/").setOnAction {
                calculatorContent.addSignToResult("/")
                updateResultText()
            }
            button("<-").setOnAction {
                removeLastChar()
            }
        }
        hbox {
            button("7").setOnAction {
                calculatorContent.addSignToResult("7")
                updateResultText()
            }
            button("8").setOnAction {
                calculatorContent.addSignToResult("8")
                updateResultText()
            }
            button("9").setOnAction {
                calculatorContent.addSignToResult("9")
                updateResultText()
            }
            button("X").setOnAction {
                calculatorContent.addSignToResult("*")
                updateResultText()
            }
        }
        hbox {
            button("4").setOnAction {
                calculatorContent.addSignToResult("4")
                updateResultText()
            }
            button("5").setOnAction {
                calculatorContent.addSignToResult("5")
                updateResultText()
            }
            button("6").setOnAction {
                calculatorContent.addSignToResult("6")
                updateResultText()
            }
            button("-").setOnAction {
                calculatorContent.addSignToResult("-")
                updateResultText()
            }
        }
        hbox {
            button("1").setOnAction {
                calculatorContent.addSignToResult("1")
                updateResultText()
            }
            button("2").setOnAction {
                calculatorContent.addSignToResult("2")
                updateResultText()
            }
            button("3").setOnAction {
                calculatorContent.addSignToResult("3")
                updateResultText()
            }
            button("+").setOnAction {
                calculatorContent.addSignToResult("+")
                updateResultText()
            }
        }
        hbox {
            button("C").setOnAction {
                println("Button 1 Pressed")
            }
            button("0").setOnAction {
                calculatorContent.addSignToResult("0")
                updateResultText()
            }
            button("/").setOnAction {
                calculatorContent.addSignToResult("/")
                updateResultText()
            }
            button("=").setOnAction {
                println("Button 4 Pressed")
            }
        }
    }

    private fun updateResultText() {
        result.text = calculatorContent.result
    }

    private fun removeLastChar() {
        calculatorContent.result = calculatorContent.result.substring(0, calculatorContent.result.length - 1)
        updateResultText()
    }
}