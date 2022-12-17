package com.github.namuan.gptfx

import javafx.application.Application
import javafx.stage.Stage

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        GptScreen("GPT").setup(stage)
        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}