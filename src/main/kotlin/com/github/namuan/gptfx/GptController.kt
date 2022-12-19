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
import java.util.concurrent.CompletableFuture


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
        val chatContext: String = chatViewModel.getChatContext()

        CompletableFuture.supplyAsync {
            val apiResponse = completionsApi(chatContext)
            chatViewModel.updateChatContext(apiResponse)
            chatViewModel.resetPrompt()
        }
    }

    private fun Node.assignShortcuts(keyCode: KeyCode, trigger: Runnable) {
        scene.accelerators[KeyCodeCombination(keyCode, KeyCombination.CONTROL_ANY)] = trigger
    }
}