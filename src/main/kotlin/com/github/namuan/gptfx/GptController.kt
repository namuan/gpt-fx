package com.github.namuan.gptfx

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

    private val chatViewModel = ChatViewModel()

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

    fun bindViewModel() {
        txtPrompt.textProperty().bindBidirectional(chatViewModel.prompt)
        btnSend.disableProperty().bindBidirectional(chatViewModel.disablePrompting)
        txtChat.textProperty().bindBidirectional(chatViewModel.chatHistory)
    }

    fun onBtnClearChatPressed() {
        clearChatLogs()
        txtChat.clear()
    }

    fun onSendPrompt() {
        chatViewModel.disableNewRequests()

        val chatContext = getChatLogsWith(chatViewModel.prompt.value).joinToString("\n")

        CompletableFuture.supplyAsync {
            val apiResponse = completionsApi(chatContext)
            val output: String = buildOutputFrom(chatViewModel.prompt.value, apiResponse)
            txtChat.appendText(output)
            file.appendText(output)

            storeInChatLogs(chatViewModel.prompt.value, apiResponse)
            chatViewModel.resetPrompt()
        }
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

    private fun Node.assignShortcuts(keyCode: KeyCode, trigger: Runnable) {
        scene.accelerators[KeyCodeCombination(keyCode, KeyCombination.CONTROL_ANY)] = trigger
    }
}