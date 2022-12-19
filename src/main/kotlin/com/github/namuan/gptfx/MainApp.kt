package com.github.namuan.gptfx

import javafx.application.Application
import javafx.stage.Stage

class MainApp : Application() {
    override fun start(stage: Stage) {
        setupConfig()
        GptView("GPT").setup(stage)
        stage.show()
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}