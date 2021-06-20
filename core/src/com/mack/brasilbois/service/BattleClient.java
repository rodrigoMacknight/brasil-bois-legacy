package com.mack.brasilbois.service;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.mack.brasilbois.model.BattleField;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;
import com.mack.brasilbois.model.eventsMapper.CreatureBattleEvent;
import com.mack.brasilbois.model.eventsMapper.PlaceCardEvent;
import com.mack.brasilbois.utils.PlayerEventsExchanger;
import com.mack.brasilbois.view.PlayScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.mack.brasilbois.view.PlayScreen.enemy;
import static com.mack.brasilbois.view.PlayScreen.player;

public class BattleClient {

    private Socket socket;

    public void connectPlaySocket() {
        try {
            //socket = IO.socket("http://54.232.104.27:8080");
            if (socket == null) {
                socket = IO.socket("http://54.232.104.27:8080");
                socket.connect();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");
            }
        }).on("socketId", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];


                try {

                    Gdx.app.log("socketIO", "my id: " + data.getString("id"));
                } catch (JSONException e) {
                    Gdx.app.log("socketIO", "error getting ID");
                }

            }
        }).on("newPlayer", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Gdx.app.log("SocketIO", "received event socketId");
                String id = null;
                try {
                    id = data.getString("id");
                    Gdx.app.log("socketIO", "new player id: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("socketIO", "error getting ID");
                }

            }
        });
    }

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

    public void sendEndTurn() {
        socket.emit("endTurn", socket.id());
    }

    public void sendAttackEnemyCreature(Card.BoardPlace friendlyBoard, Card.BoardPlace enemyBoard) {
        try {
            String jsonString = new JSONObject()
                    .put("friendlyBoard", friendlyBoard.name())
                    .put("enemyBoardPlace", enemyBoard.name())
                    .toString();
            socket.emit("attackCreature", jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void sendAttackEnemyHP(String position) {
        try {
            String jsonString = new JSONObject()
                    .put("position", position)
                    .toString();
            socket.emit("attackHp", jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void configSocketListeners() {
        socket.on("enemyCardPlaced", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                String data = (String) args[0];
                //JSONObject json = new JSONObject(data);
                Gdx.app.log("SocketIO", "EnemyCardPlaced");
                Gdx.app.log("cardName & pos: ", data);
                PlayScreen.placeEnemyCard(data);
                //printJson(json);

            }
        });

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("socketIo", "Connected");
            }
        });

        socket.on("socketId", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("alo");
                JSONObject data = (JSONObject) args[0];
                String id = null;
                try {
                    id = data.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gdx.app.log("socketIo", "my id:" + id);
            }
        });

        socket.on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String id = null;
                try {
                    PlayScreen.gameStarted = true;
                    player.startTurn();
                    id = data.getString("id");
                    socket.emit("playerFound");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gdx.app.log("socketIo", "new player connected:" + id);
            }
        });
        //FOUND A PLAYER TO PLAY WITH -
        //HE WILL START THE GAME (CONNECTED TO SERVER FIRST) SO YOU HAVE 1 MORE MANA
        socket.on("playerFound", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                enemy.setPlaying(true);
                player.totalMana++;
                PlayScreen.gameStarted = true;
            }
        });

        socket.on("enemyAttackedHp", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data = (String) args[0];

                handleEnemyDmg(data);
            }
        });

        socket.on("enemyAttackedCreature", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data = (String) args[0];
                handleBattle(data);
            }
        });

        socket.on("endEnemyTurn", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("Endturn", "Enemy ended turn");

                if(enemy.isPlaying()) {
                    player.startTurn();
                }
            }
        });

    }

    private void handleEnemyDmg(String data) {
        Gson gson = new Gson();
        PlaceCardEvent place = gson.fromJson(data, PlaceCardEvent.class);

        CreatureCard creature = PlayerEventsExchanger.getEnemyCreatureCardAt(PlayerEventsExchanger.getMirrorBattleField(place.position));
        //TODO: CARD ANIMATION
        creature.attackingAnimation = 120;
        CardInteractor.playHitEffect();
        player.damage(creature.getAtkTotal());

    }

    private void handleBattle(String battleEvent) {
        Gson gson = new Gson();
        CreatureBattleEvent place = gson.fromJson(battleEvent, CreatureBattleEvent.class);
        //in the event send the enemy is the friend from this player
        Card.BoardPlace aux = place.enemyBoardPlace;
        place.enemyBoardPlace = PlayerEventsExchanger.getMirrorBattleField(place.friendlyBoard);
        place.friendlyBoard = PlayerEventsExchanger.getMirrorBattleField(aux);

        CreatureCard friendlyCreature = PlayerEventsExchanger.getFriendlyCreatureCardAt
                (place.friendlyBoard);


        CreatureCard enemyCreature = PlayerEventsExchanger.getEnemyCreatureCardAt
                (place.enemyBoardPlace);

        //TODO: CARD ANIMATION
        CardInteractor.battleTheseCards(friendlyCreature, enemyCreature);


    }


}
