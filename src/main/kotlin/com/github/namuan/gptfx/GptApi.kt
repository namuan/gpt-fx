package com.github.namuan.gptfx

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
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

val openAiApiKey: String? = System.getenv("OPENAI_API_KEY")


fun completionsApi(prompt: String): String {
    assert(openAiApiKey != null) { "OPENAI_API_KEY is not set" }

    val openaiRequest = CompletionRequest(
        model = "text-davinci-003",
        prompt = prompt,
        temperature = 0.1
    )
    val request = HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer $openAiApiKey")
        .uri(URI.create("$OPENAI_BASE/v1/completions"))
        .POST(ofString(gson.toJson(openaiRequest)))
        .build()

    val response = newHttpClient().send(request, ofString())

    val completionResponse = gson.fromJson(response.body(), CompletionResponse::class.java)

    return completionResponse.choices.firstOrNull()?.text?.trim() ?: ""
}
