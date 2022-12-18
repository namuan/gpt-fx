package com.github.namuan.gptfx

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val HUMAN = "\uD83D\uDC64"
private const val ROBOT = "\uD83E\uDD16"

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

    fun onSendPrompt(event: ActionEvent) {
        btnSend.isDisable = true
        val output: String = buildOutputFrom(txtPrompt.text, completionsApi(txtPrompt.text))

        txtChat.appendText(output)
        file.appendText(output)

        btnSend.isDisable = false
        txtPrompt.clear()
    }

    private fun buildOutputFrom(
        prompt: String,
        result: String
    ): String {
        val dateString = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())

        val output: String = """
    [${dateString}] ${HUMAN}: ${prompt}
                
    ${ROBOT}: ${result}
                
            """.trimIndent()
        return output
    }
}