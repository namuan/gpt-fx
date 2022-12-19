package com.github.namuan.gptfx

import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class GptScreen(private val title: String) {
    fun setup(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("gpt-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 800.0, 600.0)
        HelloApplication::class.java.getResource("/styles.css")?.toExternalForm()?.let {
            scene.stylesheets.add(it)
        }
        stage.title = title
        stage.scene = scene
        val image = Image("app.png")
        stage.icons.add(image)

        loadPosition(stage)

        stage.setOnCloseRequest { _ ->
            savePosition(stage)
        }

        val gptController = fxmlLoader.getController<GptController>()
        gptController.bindShortcuts()
    }
}