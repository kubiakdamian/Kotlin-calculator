import javafx.stage.Stage
import tornadofx.App

class Calculator : App(MainView::class, CalculatorStyle::class) {
    override fun start(stage: Stage) {
        Thread.currentThread().setUncaughtExceptionHandler { _, throwable -> println(throwable.message) }

        stage.isResizable = false
        stage.centerOnScreen()
        super.start(stage)
    }
}