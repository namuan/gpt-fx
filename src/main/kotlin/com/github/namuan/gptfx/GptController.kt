package com.github.namuan.gptfx

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CompletableFuture

private const val HUMAN = "🗣"
private const val ROBOT = "🤖"

class GptController {
    @FXML
    private lateinit var btnClearChat: Button

    @FXML
    private lateinit var btnSend: Button

    @FXML
    private lateinit var txtPrompt: TextField

    @FXML
    private lateinit var txtChat: TextArea

    private val file: File = File(applicationDirectory, "chat.txt")

    fun initialize() {
        file.parentFile.mkdirs()
    }

    fun bindShortcuts() {
        btnSend.assignShortcuts(KeyCode.S) {
            btnSend.fire()
        }
        btnClearChat.assignShortcuts(KeyCode.X) {
            btnClearChat.fire()
        }
        txtPrompt.assignShortcuts(KeyCode.I) {
            txtPrompt.requestFocus()
        }
        txtChat.assignShortcuts(KeyCode.C) {
            txtChat.copy()
        }
    }

    private fun Node.assignShortcuts(keyCode: KeyCode, trigger: () -> Unit) {
        scene.accelerators.put(KeyCodeCombination(keyCode, KeyCombination.CONTROL_ANY), trigger)
    }

    fun onBtnClearChatPressed() {
        clearChatLogs()
        txtChat.clear()
    }

    fun onSendPrompt() {
        btnSend.isDisable = true
        val chatContext = getChatLogsWith(txtPrompt.text).joinToString("\n")

        CompletableFuture.supplyAsync({
            Thread.sleep(5000)
            val apiResponse = completionsApi(chatContext)
            val output: String = buildOutputFrom(txtPrompt.text, apiResponse)
            txtChat.appendText(output)
            file.appendText(output)

            storeInChatLogs(txtPrompt.text, apiResponse)
            btnSend.isDisable = false
            txtPrompt.clear()
        }, {
            Platform.runLater(it)
        })
    }

    private fun buildOutputFrom(
        prompt: String,
        result: String
    ): String {
        val dateString = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())

        return """

[${dateString}] ${HUMAN}: $prompt

${ROBOT}: $result
---""".trimIndent()
    }
}