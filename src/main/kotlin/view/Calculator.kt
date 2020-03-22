package view

import javafx.stage.Stage
import tornadofx.App

class Calculator : App(MainView::class, CalculatorStyle::class) {
    override fun start(stage: Stage) {
        exceptionHandler()

        stage.isResizable = false
        stage.centerOnScreen()
        super.start(stage)
    }

    private fun exceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler { _, throwable -> println(throwable.message) }
    }
}