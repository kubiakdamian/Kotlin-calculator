import tornadofx.CssBox
import tornadofx.Stylesheet
import tornadofx.px

class ButtonStyle : Stylesheet() {
    val multiplier = 1.5
    init {
        button {
            padding = CssBox(5.px, 5.px, 5.px, 5.px)
            minHeight = 50.px * multiplier
            maxHeight = 50.px * multiplier
            minWidth = 50.px * multiplier
            maxWidth = 50.px * multiplier
            fontSize = 15.px * multiplier
        }

        textField {
            minHeight = 40.px * multiplier
            maxHeight = 40.px * multiplier
            minWidth = 200.px * multiplier
            maxWidth = 200.px * multiplier
            fontSize = 15.px * multiplier
        }
    }
}