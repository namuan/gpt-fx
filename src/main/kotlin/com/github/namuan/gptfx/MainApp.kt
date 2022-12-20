package com.github.namuan.gptfx

import javafx.application.Application
import javafx.stage.Stage

class MainApp : Application() {
    private fun showApiKeyDialog() {
        val dialog = ApiKeyDialog()
        dialog.showAndWait()
    }

    private fun showMainView(stage: Stage) {
        GptView("GPT").setup(stage)
        stage.show()
    }

    override fun start(stage: Stage) {
        setupConfig()
        if (loadApiKey().isBlank()) {
            showApiKeyDialog()
        }
        showMainView(stage)
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}