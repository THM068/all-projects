package tornadofx

import javafx.stage.Stage
import tornadofx.*
class MyApp: App(MyView::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isFullScreen = true
        stage.maxHeight = 500.0
        stage.maxWidth = 500.0
    }
}
class MyView: View("Hello world") {
    override val root = vbox {
        label {
            text = "Hello world"
        }

        button {
            text = "Click me"

        }
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}