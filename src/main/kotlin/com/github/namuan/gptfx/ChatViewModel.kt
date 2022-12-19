package com.github.namuan.gptfx

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import java.text.SimpleDateFormat
import java.util.*

private const val HUMAN = "🗣"
private const val ROBOT = "🤖"

class ChatViewModel {
    val prompt: StringProperty = SimpleStringProperty()
    val chatHistory: StringProperty = SimpleStringProperty()
    val disablePrompting: BooleanProperty = SimpleBooleanProperty()

    fun resetPrompt() {
        prompt.set("")
        disablePrompting.set(false)
    }

    fun disableNewRequests() {
        disablePrompting.set(true)
    }

    fun getChatContext(): String {
        return safeChatHistory() + safePrompt()
    }

    private fun safeChatHistory() = chatHistory.get().orEmpty()

    private fun safePrompt() = prompt.get().orEmpty()

    fun updateChatContext(promptResponse: String) {
        chatHistory.set(safeChatHistory() + buildOutputFrom(safePrompt(), promptResponse))
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
