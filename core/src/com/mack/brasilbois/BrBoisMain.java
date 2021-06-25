package com.mack.brasilbois;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.service.CardBuilder;
import com.mack.brasilbois.view.PlayScreen;

import org.json.JSONException;
import org.json.JSONObject;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.List;



//classe que controla o game inteiro.
//aqui se controla a transicao de telas do game e chama o render
//e update das screen que estao ativas
public class BrBoisMain extends Game {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final String TITLE = "Brasil Bois";
    //NICE TUTS
    //https://libgdx.info/buttons-scene2d/

    public static List<Card> allCards;
    //where we draw stuff
    public SpriteBatch batch;

    private Socket socket;
    @Override
    public void create() {

        batch = new SpriteBatch();
        //initialize game cards on memory
        allCards = CardBuilder.initializeCards();
        //create the gameStateManager
        setScreen(new PlayScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    public static Card getCardByName(String cardName) {
       return allCards.stream().filter(c -> c.getName().equals(cardName)).findAny().orElse(null);
    }


}
