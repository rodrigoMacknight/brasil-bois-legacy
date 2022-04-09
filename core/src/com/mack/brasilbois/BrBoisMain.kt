package com.mack.brasilbois

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mack.brasilbois.model.Card
import com.mack.brasilbois.service.CardBuilder
import com.mack.brasilbois.view.PlayScreen
import java.util.*

class BrBoisMain : Game() {


    override fun create() {
        batch = SpriteBatch()
        //initialize game cards on memory
        allCards = CardBuilder.initializeCards()
        //create the gameStateManager
        setScreen(PlayScreen(this))
    }

    override fun render() {
        super.render()
    }

    companion object {

        fun getCardByName(cardName: String): Card? {
            return allCards!!.stream().filter { c: Card? -> c!!.name == cardName }.findAny().orElse(null)
        }
        lateinit var batch: SpriteBatch

        lateinit var allCards: List<Card>

        val TITLE = "Brasil Bois"
        val WIDTH = 1280
        val HEIGHT = 720
    }

}