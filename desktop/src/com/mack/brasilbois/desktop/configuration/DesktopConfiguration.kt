package com.mack.brasilbois.desktop.configuration

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

class DesktopConfiguration {
    companion object {
        fun getConfiguration(): Lwjgl3ApplicationConfiguration {
            val config = Lwjgl3ApplicationConfiguration()

            config.setTitle("Brasil Bois")

            return config
        }
    }
}