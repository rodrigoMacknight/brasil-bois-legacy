package com.mack.brasilbois.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.mack.brasilbois.BrBoisMain
import com.mack.brasilbois.desktop.configuration.DesktopConfiguration

fun main(args: Array<String>) {
    Lwjgl3Application(BrBoisMain(), DesktopConfiguration.getConfiguration())
}