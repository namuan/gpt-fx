package com.github.namuan.gptfx

import javafx.fxml.FXML
import javafx.scene.control.TextArea
import java.io.File

class GptController {
    private val file: File = File(applicationDirectory, "notes.txt")

    @FXML
    private lateinit var notesTextArea: TextArea

    fun initialize() {
        file.parentFile.mkdirs()

        if (file.exists()) {
            notesTextArea.text = file.readText()
            notesTextArea.positionCaret(notesTextArea.text.length)
        }
    }

    fun onKeyPressed() {
        val notes = notesTextArea.text
        file.writeText(notes)
    }
}