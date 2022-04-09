package com.mack.brasilbois.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mack.brasilbois.BrBoisMain

fun main(args : Array<String>) {

    val config = LwjglApplicationConfiguration()
    config.width = 1280
    config.height = 720
    config.title = "Brasil Bois"
    LwjglApplication(BrBoisMain(), config)
}