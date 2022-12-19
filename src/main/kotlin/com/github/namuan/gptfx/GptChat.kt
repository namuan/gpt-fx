package com.github.namuan.gptfx


private var chatLog = mutableListOf<String>()

fun storeInChatLogs(vararg messages: String) {
    chatLog.addAll(messages)
}

fun getChatLogsWith(prompt: String): List<String> {
    return chatLog + prompt
}

fun clearChatLogs() {
    chatLog.clear()
}