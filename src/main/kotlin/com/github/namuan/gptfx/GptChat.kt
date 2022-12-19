package com.github.namuan.gptfx


private var chatLog = mutableListOf<String>()

fun saveToList(vararg messages: String) {
    chatLog.addAll(messages)
}

fun getListWith(prompt: String): List<String> {
    return chatLog + prompt
}
