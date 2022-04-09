package com.mack.brasilbois.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mack.brasilbois.BrBoisMain

fun main(args : Array<String>) {

    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.apply {
        setTitle("Brasil Bois")
    }
    Lwjgl3Application(BrBoisMain(), config)
}