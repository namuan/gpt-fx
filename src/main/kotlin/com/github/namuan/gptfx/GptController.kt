package com.github.namuan.gptfx

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import java.util.concurrent.Executors


class GptController {
    @FXML
    private lateinit var btnClearChat: Button

    @FXML
    private lateinit var btnSend: Button

    @FXML
    private lateinit var txtPrompt: TextField

    @FXML
    private lateinit var txtChat: TextArea

    private val chatViewModel = ChatViewModel()

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
    }

    fun bindViewModel() {
        txtPrompt.textProperty().bindBidirectional(chatViewModel.prompt)
        btnSend.disableProperty().bindBidirectional(chatViewModel.disablePrompting)
        txtChat.textProperty().bindBidirectional(chatViewModel.chatHistory)
    }

    fun onBtnClearChatPressed() {
        chatViewModel.clearChatHistory()
    }

    fun onSendPrompt() {
        val chatContext: String = chatViewModel.getChatContext()

        val task = ApiTask(chatContext)
        task.setOnSucceeded {
            chatViewModel.updateChatContext(task.value)
            chatViewModel.enableNewRequests()
            txtChat.selectPositionCaret(txtChat.text.length)
        }
        task.setOnFailed {
            val errorMessage = task.exception.stackTraceToString()
            chatViewModel.updateChatContext(errorMessage)
            chatViewModel.enableNewRequests()
            txtChat.selectPositionCaret(txtChat.text.length)
        }

        chatViewModel.disableNewRequests()
        Executors.newSingleThreadExecutor().submit(task)
    }

    private fun Node.assignShortcuts(keyCode: KeyCode, trigger: Runnable) {
        scene.accelerators[KeyCodeCombination(keyCode, KeyCombination.CONTROL_ANY)] = trigger
    }
}