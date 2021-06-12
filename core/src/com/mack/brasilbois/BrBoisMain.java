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

        connectSocket();
        configSocketEvents();
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

    //config listeners
    private void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){

            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");
            }
        }).on("socketId", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];

                String id = null;
                try {
                    id = data.getString("id");
                    Gdx.app.log("socketIO", "my id: " + id );
                } catch (JSONException e) {
                    Gdx.app.log("socketIO", "error getting ID" );
                }

            }
        }).on("newPlayer", new Emitter.Listener(){

            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Gdx.app.log("SocketIO", "received event socketId");
                String id = null;
                try {
                    id = data.getString("id");
                    Gdx.app.log("socketIO", "new player id: " + id );
                } catch (JSONException e) {
                    Gdx.app.log("socketIO", "error getting ID" );
                }

            }
        });
    }
    //connect to server
    private void connectSocket() {

        try {
            socket = IO.socket("http://localhost:8000");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }



}
