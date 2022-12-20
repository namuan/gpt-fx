package com.github.namuan.gptfx

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

class ApiKeyDialog {
    val root = VBox()
    val apiKeyPrompt = Label("Please enter your API key:")
    val apiKeyField = TextField()
    val submitButton = Button("Submit")
    val errorLabel = Label()

    fun showAndWait() {
        val dialogStage = Stage()

        root.padding = Insets(10.0)
        root.spacing = 10.0

        val inputRow = HBox()
        inputRow.spacing = 10.0
        inputRow.alignment = Pos.CENTER
        inputRow.children.addAll(apiKeyPrompt, apiKeyField, submitButton)

        errorLabel.textFill = Color.RED

        root.children.addAll(inputRow, errorLabel)

        submitButton.setOnAction {
            val apiKey = apiKeyField.text
            if (apiKey.isEmpty()) {
                errorLabel.text = "Please enter a valid API key"
            } else {
                saveApiKey(apiKey)
                dialogStage.close()
            }
        }

        dialogStage.title = "API Key"
        dialogStage.scene = Scene(root)
        dialogStage.showAndWait()
    }
}

