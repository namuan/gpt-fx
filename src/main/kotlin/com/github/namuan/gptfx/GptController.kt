package com.github.namuan.gptfx

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val HUMAN = "🗣"
private const val ROBOT = "🤖"

class GptController {
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

    fun onSendPrompt() {
        btnSend.isDisable = true
        val chatContext = getListWith(txtPrompt.text).joinToString("\n")
        val apiResponse = completionsApi(chatContext)
        val output: String = buildOutputFrom(txtPrompt.text, apiResponse)
        txtChat.appendText(output)
        file.appendText(output)

        saveToList(txtPrompt.text, apiResponse)
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