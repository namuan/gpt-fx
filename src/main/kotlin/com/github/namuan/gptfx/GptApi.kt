package com.github.namuan.gptfx

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import javafx.concurrent.Task
import mu.KotlinLogging
import java.net.URI
import java.net.http.HttpClient.newHttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers.ofString
import java.net.http.HttpResponse.BodyHandlers.ofString

private const val OPENAI_BASE = "https://api.openai.com"

data class CompletionRequest(
    val model: String,
    val prompt: String,
    val temperature: Double,
    @SerializedName("max_tokens") val maxTokens: Int = 300,
    @SerializedName("top_p") val topP: Double = 1.0,
    val stream: Boolean = false
)

data class Choice(val text: String)

data class CompletionResponse(val choices: List<Choice>)

val gson: Gson = Gson()

private val logger = KotlinLogging.logger {}

fun completionsApi(prompt: String): String {
    val openaiRequest = CompletionRequest(
        model = "text-davinci-003",
        prompt = prompt,
        temperature = 0.1
    )
    val requestBody = gson.toJson(openaiRequest)
    logger.debug { "JSON Request: $requestBody" }
    val request = HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer ${loadApiKey()}")
        .uri(URI.create("$OPENAI_BASE/v1/completions"))
        .POST(ofString(requestBody))
        .build()

    val response = newHttpClient().send(request, ofString())
    val responseBody = response.body()
    logger.debug { "JSON Response: $responseBody" }
    val completionResponse = gson.fromJson(responseBody, CompletionResponse::class.java)

    return completionResponse.choices.firstOrNull()?.text?.trim() ?: responseBody
}

class ApiTask(val chatContext: String) : Task<String>() {
    override fun call(): String {
        return completionsApi(chatContext)
    }
}