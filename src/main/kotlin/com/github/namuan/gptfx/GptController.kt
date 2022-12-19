package com.github.namuan.gptfx

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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
        btnSend.scene.accelerators.put(KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY)) {
            btnSend.fire()
        }
        btnClearChat.scene.accelerators.put(KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_ANY)) {
            btnClearChat.fire()
        }
        txtPrompt.scene.accelerators.put(KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_ANY)) {
            txtPrompt.requestFocus()
        }
        txtChat.scene.accelerators.put(KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY)) {
            txtChat.copy()
        }
    }

    fun onBtnClearChatPressed() {
        clearChatLogs()
        txtChat.clear()
    }

    fun onSendPrompt() {
        btnSend.isDisable = true
        val chatContext = getChatLogsWith(txtPrompt.text).joinToString("\n")
        val apiResponse = completionsApi(chatContext)
        val output: String = buildOutputFrom(txtPrompt.text, apiResponse)
        txtChat.appendText(output)
        file.appendText(output)

        storeInChatLogs(txtPrompt.text, apiResponse)
        btnSend.isDisable = false
        txtPrompt.clear()
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