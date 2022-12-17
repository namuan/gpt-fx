package com.github.namuan.gptfx

import com.google.gson.Gson
import javafx.stage.Stage
import java.io.File

private val propertiesFile = "$applicationDirectory/app_properties.json"

data class WindowPosition(val x: Double, val y: Double, val width: Double, val height: Double)

data class ApplicationProperties(val windowPosition: WindowPosition)

fun savePosition(stage: Stage) {
    val windowPosition = WindowPosition(stage.x, stage.y, stage.width, stage.height)
    val applicationProperties = ApplicationProperties(windowPosition = windowPosition)
    val toJson = Gson().toJson(applicationProperties)
    File(propertiesFile).writeText(toJson)
}

fun loadPosition(stage: Stage) {
    if (File(propertiesFile).exists()) {
        val applicationProperties = Gson().fromJson(File(propertiesFile).readText(), ApplicationProperties::class.java)
        stage.x = applicationProperties.windowPosition.x
        stage.y = applicationProperties.windowPosition.y
        stage.width = applicationProperties.windowPosition.width
        stage.height = applicationProperties.windowPosition.height
    }
}