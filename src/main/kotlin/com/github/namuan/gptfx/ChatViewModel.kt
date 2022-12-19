package com.github.namuan.gptfx

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class ChatViewModel {
    val prompt: StringProperty = SimpleStringProperty()
    val chatHistory: StringProperty = SimpleStringProperty()
    val disablePrompting: BooleanProperty = SimpleBooleanProperty()

    fun resetPrompt() {
        prompt.value = ""
        disablePrompting.set(false)
    }

    fun disableNewRequests() {
        disablePrompting.set(true)
    }
}