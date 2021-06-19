package com.mack.brasilbois.service;

import com.badlogic.gdx.Gdx;
import com.mack.brasilbois.model.BattleField;
import com.mack.brasilbois.model.CreatureCard;
import com.mack.brasilbois.view.PlayScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BattleClient {

    private Socket socket;

    public void connectPlaySocket(){
        try {
            //socket = IO.socket("http://54.232.104.27:8080");
            socket =  IO.socket("http://localhost:8080");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void configSocketEvents() {
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


    /**
     *
     * @param b
     * @param currentCreature
     *
     * Send the data of the card just place to the server.
     */
    public void sendPlaceCardToServer(BattleField b, CreatureCard currentCreature) {
        String position = b.getBoardPlace().name();
        String cardId = currentCreature.getName();

        try {
            String jsonString = new JSONObject()
                    .put("position", position)
                    .put("cardName", cardId)
                    .toString();
            socket.emit("placeCard", jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void configSocketListeners() {
        socket.on("enemyCardPlaced", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                String data =  (String) args[0];
                //JSONObject json = new JSONObject(data);
                Gdx.app.log("SocketIO", "CardPlaced");
                Gdx.app.log("cardName & pos: ", data);
                PlayScreen.placeEnemyCard(data);
                //printJson(json);

            }
        });

    }
}
