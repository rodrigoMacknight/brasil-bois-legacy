package com.mack.brasilbois.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.mack.brasilbois.BrBoisMain;
import com.mack.brasilbois.service.Tests;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.model.BattleField;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;
import com.mack.brasilbois.model.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PlayScreen implements Screen, InputProcessor {
    //distancia que uma carta precisa estar para as duas interagirem

    //in order to access the game.batch
    private BrBoisMain game;
    //loading comons textures
    private Texture backGround;
    //reference to the layout of every card
    public static Texture cardBg;
    public static Texture mana;
    public static Texture emptyMana;
    public static Texture cardBack;
    public static Texture manaCost;
    public static Texture atkHolder;
    public static Texture cocaine;
    public static Texture cristo;

    public static Vector2 playerHPPos;
    public static Vector2 enemyHPPos;


    private Socket socket;


    public static Player player;
    public static Player enemy;

    private static List<BattleField> creatureHolders;
    public static List<BattleField> enemyCreatureHolders;

    public static Card currentCard; //holds the card to be dragged and dropped

    int turno;

    public static BitmapFont boardFont;
    public static BitmapFont cardFont;
    public static BitmapFont descFont;





    private void configSocketForPlay() {
          connectPlaySocket();
          configSocketListeners();
    }

    private void configSocketListeners() {
        socket.on("enemyCardPlaced", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                String data =  (String) args[0];

                try {
                    JSONObject json = new JSONObject(data);
                    Gdx.app.log("SocketIO", "CardPlaced");
                    Gdx.app.log("cardName & pos: ", data);
                    placeEnemyCard(json);
                    printJson(json);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void printJson(JSONObject json) {
        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            Gdx.app.log("key: ", key);
            try {
                Gdx.app.log("value: ",  json.get(key).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void connectPlaySocket(){
        try {
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    //constructor
    public PlayScreen(BrBoisMain game) {
        this.game = game;
        configSocketForPlay();

        //loading comum textures for the game
        backGround = new Texture("Layout/NewLayout.png");
        cardBg = new Texture("Layout/newCard.png");
        mana = new Texture("Layout/mana.png");
        emptyMana = new Texture("Layout/emptyMana.png");
        cardBack = new Texture("Layout/cardback.png");
        manaCost = new Texture("Layout/manaCost.png");
        atkHolder = new Texture ("Layout/atkHolder.png");
        cocaine = new Texture("Layout/cocaine.png");
        cristo= new Texture("Layout/cristo.png");

        //creating the fonts
        boardFont = new BitmapFont(Gdx.files.internal("Fonts/teste.fnt"));
        cardFont = new BitmapFont(Gdx.files.internal("Fonts/cardFontHolder.fnt"));
        descFont = new BitmapFont(Gdx.files.internal("Fonts/description.fnt"));

        //setting that this class is listening for input
        Gdx.input.setInputProcessor(this);

        //generating placeholder deck
        player = new Player(Tests.getTestDeck(20, false));
        enemy = new Player(Tests.getTestDeck(20, true));

        //set the player that owns that card for all the cards
        enemy.setOwner();
        player.setOwner();
        //initilize player hand
        player.grabCard(5);
        enemy.grabCard(6);
        player.startTurn();

        //loads hpMaths
        playerHPPos = new Vector2(SizePositionValues.PLAYER_HP_X, SizePositionValues.PLAYER_HP_X);
        enemyHPPos = new Vector2(SizePositionValues.ENEMY_HP_X, SizePositionValues.ENEMY_HP_Y);

        //create the battlefields for the creatures
        creatureHolders = createCreatureHolders();
        enemyCreatureHolders = createEnemyCreatureHolders();

    }


    @Override
    public void show() {

        currentCard = null;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        game.batch.draw(backGround, 0, 0);

        player.drawGrimorio(game.batch);
        player.drawHand(game.batch);


        enemy.drawEnemyHand(game.batch);
        enemy.drawGrimorio(game.batch);

        //creatureHolders.printAssitent();
        //draw cards and creatures on the table
        drawBoardPlace();
        drawBattlefieldAssistant();
        drawMana();
        //drawing the hp
        boardFont.draw(game.batch, player.getHp(), SizePositionValues.PLAYER_HP_X, SizePositionValues.PLAYER_HP_Y);
        boardFont.draw(game.batch, enemy.getHp(), SizePositionValues.ENEMY_HP_X, SizePositionValues.ENEMY_HP_Y);
        //drawing the qtty of cards still in the deck
        boardFont.draw(game.batch, player.getDeck().size() + "", SizePositionValues.FRIENDLY_CARD_COUNTER_X, SizePositionValues.FRIENDLY_CARD_COUNTER_Y);
        boardFont.draw(game.batch, enemy.getDeck().size() + "", SizePositionValues.ENEMY_CARD_COUNTER_X, SizePositionValues.ENEMY_CARD_COUNTER_Y);

        if (currentCard != null) {
        //   game.batch.draw(new Texture("coxinha.jpg"), creatureHolders.get(0).getXy().x, creatureHolders.get(0).getXy().y);
            currentCard.drawWithMana(game.batch);


    }


        game.batch.end();

}

    private void drawMana() {

        int manaOffset = 20;

        for (int i = 0; i < player.getCurrentMana(); i++) {
            if (i < 5) {
                game.batch.draw(mana, SizePositionValues.PLAYER_MANA_X + (manaOffset * i), SizePositionValues.PLAYER_MANA_Y);
            } else {
                game.batch.draw(mana, SizePositionValues.PLAYER_MANA_X + (manaOffset * (i - 5)), SizePositionValues.PLAYER_MANA_Y - manaOffset);
            }
        }
        for (int i = player.getCurrentMana(); i < player.getTotalMana(); i++) {
            if (i < 5) {
                game.batch.draw(emptyMana, SizePositionValues.PLAYER_MANA_X + manaOffset * i, SizePositionValues.PLAYER_MANA_Y);
            } else {
                game.batch.draw(emptyMana, SizePositionValues.PLAYER_MANA_X + (manaOffset * (i - 5)), SizePositionValues.PLAYER_MANA_Y - manaOffset);
            }

        }

        for (int i = 0; i < enemy.getCurrentMana(); i++) {
            if (i < 5) {
                game.batch.draw(mana, SizePositionValues.ENEMY_MANA_X + (manaOffset * i), SizePositionValues.ENEMY_MANA_Y);
            } else {
                game.batch.draw(mana, SizePositionValues.ENEMY_MANA_X + (manaOffset * (i - 5)), SizePositionValues.ENEMY_MANA_Y - manaOffset);
            }
        }
        for (int i = enemy.getCurrentMana(); i < enemy.getTotalMana(); i++) {
            if (i < 5) {
                game.batch.draw(emptyMana, SizePositionValues.ENEMY_MANA_X + manaOffset * i, SizePositionValues.ENEMY_MANA_Y);
            } else {
                game.batch.draw(emptyMana, SizePositionValues.ENEMY_MANA_X + (manaOffset * (i - 5)), SizePositionValues.ENEMY_MANA_Y - manaOffset);
            }

        }


    }

    private void drawBattlefieldAssistant() {
        for (BattleField creatureHolder : creatureHolders) {
            Texture x = new Texture("Card_arts/coxinha.png");
            game.batch.draw(x, creatureHolder.getXy().x, creatureHolder.getXy().y, 5, 5);
            x.dispose();
        }
        for (BattleField creatureHolder : enemyCreatureHolders) {
            Texture x = new Texture("Card_arts/coxinha.png");
            game.batch.draw(x, creatureHolder.getXy().x, creatureHolder.getXy().y, 5, 5);
            x.dispose();
        }
    }

    private void drawBoardPlace() {
        for (BattleField battleField : creatureHolders) {
            Card c = battleField.getCard();
            if (c != null) {
                c.drawWithoutMana(game.batch);
               // game.batch.draw(PlayScreen.cardBg, c.getxPos(), c.getyPos(), Values.CARD_SIZE_X, Values.CARD_SIZE_Y);
                //game.batch.draw(c.getCardArt(), c.getxPos() + Values.THUMBNAIL_OFFSET_X, c.getyPos() + Values.THUMBNAIL_OFFSET_Y, Values.THUMBNAIL_SIZE_X, Values.THUMBNAIL_SIZE_Y);


        }
        }
        for (BattleField battleField : enemyCreatureHolders) {
            Card c = battleField.getCard();
            if (c != null) {
                //game.batch.draw(PlayScreen.cardBg, c.getxPos(), c.getyPos(), Values.CARD_SIZE_X, Values.CARD_SIZE_Y);
                //game.batch.draw(c.getCardArt(), c.getxPos() + Values.THUMBNAIL_OFFSET_X, c.getyPos() + Values.THUMBNAIL_OFFSET_Y, Values.THUMBNAIL_SIZE_X, Values.THUMBNAIL_SIZE_Y);
                c.drawWithoutMana(game.batch);
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        //checks the friendly grimorio for input
        //getGrimorioInput(screenX, screenY);
        //checks the friendly hand for input
        int ypsolon = (Gdx.graphics.getHeight() - screenY);

        if (player.isPlaying()) {

            checkHandInput(screenX, ypsolon);
            checkBattlefieldInput(screenX, ypsolon);
        } else {//enemy Playing
            checkEnemyHandInput(screenX, ypsolon);
        }
        checkPassTurn(screenX, ypsolon);
        return false;
    }

    private void checkBattlefieldInput(int screenX, int ypsolon) {

        for (BattleField creatureHolder : creatureHolders) {
            //if the field has a card
            if (creatureHolder.getCard() != null) {

                float x = creatureHolder.getXy().x;
                    float y = creatureHolder.getXy().y;
                    //if player clicks on the card on that battlefield
                    if (screenX > (x - (SizePositionValues.CARD_SIZE_X / 2)) && screenX < x + (SizePositionValues.CARD_SIZE_X / 2)) {
                        if (ypsolon > y - (SizePositionValues.CARD_SIZE_Y / 2) && ypsolon < y + (SizePositionValues.CARD_SIZE_Y / 2)) {
                            //player got the card on that field
                            currentCard = creatureHolder.getCard();
                     //       System.out.println("card <" + current.getName() + "> removed from " + current.getCurrentPlace());
                            creatureHolder.setCard(null);
                            break;
                        }
                }

            }
        }
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {


        if (currentCard != null) {
            currentCard.setxPos(screenX - currentCard.getCardSprite().getWidth() / 2);
            currentCard.setyPos(Gdx.graphics.getHeight() - (screenY + currentCard.getCardSprite().getHeight() / 2));
        }


        return false;
    }


    @Override
    public boolean
    touchUp(int screenX, int screenY, int pointer, int button) {

        int ipsolon = Gdx.graphics.getHeight() - screenY;
        //if the card was not dropped in any valid card position return it to the grimorio.
        Vector2 mousePos = new Vector2(screenX, screenY);
        System.out.println("x: " + screenX);
        System.out.println("y: " + ipsolon);

        if (player.isPlaying()) {
            checkUserInput(mousePos);


        } else {//enemy Playing
            checkEnemyInput(mousePos);
        }


        return false;
    }

    private void checkUserInput(Vector2 mouse ) {

        if (currentCard != null) {

            boolean wasPlaced = false;
            //where the card was
        //    System.out.println("card " + current.getName() +  " is on " + current.getCurrentPlace());

            switch (currentCard.getCurrentPlace()) {
                //if the card was in the hand and is a  creature type
                //the user can place her in any battlefield
                case HAND:
                    for (BattleField b : creatureHolders) {

                        //if the user tryed to place a card
                        if (mouse.dst(b.getXy()) < 65) {
                            //se nao tinha carta antes
                            if (b.getCard() == null){
                                wasPlaced = placeCard(b);
                                break;
                            }
                        }
                    }
                    if (!wasPlaced) {
                        currentCard.returnToLastPosition();
                     //   System.out.println("card <" + current.getName() + ">" + "was returned to last position");
                    }
                    break;
                case FIELD_1:
                case FIELD_2:
                case FIELD_3:
                case FIELD_4:
                case FIELD_5:
                case FIELD_6:
                    //the card was taken from a friendly battlefield

                    //boolean survived = //CardInteractor.checkCardInteractions(screenX, ipsolon);
                    boolean survived = checkCardInteractions(mouse);
                    if (survived) {
                        currentCard.returnToLastPosition();
                    }
                    break;
            }

            currentCard = null;
        }
    }

    private void placeEnemyCard(JSONObject json) {


    }

    private boolean placeCard( BattleField b) {
        if(currentCard instanceof CreatureCard) {
            CreatureCard currentCreature = (CreatureCard) currentCard;
            //check if card mana cost higher than my mana cost
            if (player.getCurrentMana() >= currentCard.getManaCost()) {

                b.setCard(currentCreature);

                if(currentCreature.hasDeployAction()){
                        currentCreature.doDeployAction(creatureHolders);
                }
                currentCard.setCurrentPlace(b.getBoardPlace());

                //places the texture on top of that battlefield
                currentCard.setxPos(b.getXy().x - (SizePositionValues.CARD_SIZE_X / 2));
                currentCard.setyPos(b.getXy().y - (SizePositionValues.CARD_SIZE_Y / 2));
                //subtract mana from the uesr
                player.useMana(currentCard.getManaCost());
                //seta enjoo de criatura
                currentCreature.setSick(true);

                sendPlaceCardToServer(b, currentCreature);

                return true;

            } else {
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     *
     * @param b
     * @param currentCreature
     *
     * Send the data of the card just place to the server.
     */
    private void sendPlaceCardToServer(BattleField b, CreatureCard currentCreature) {
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

    private void checkEnemyInput( Vector2 mouse) {

        if (currentCard != null) {
            boolean wasPlaced = false;
            //where the card was
            CreatureCard creature = (CreatureCard) currentCard;
            switch (currentCard.getCurrentPlace()) {
                case HAND:

                    for (BattleField b : enemyCreatureHolders) {

                        //if the user tryed to place a card
                        if (mouse.dst(b.getXy()) < 40) {

                            if (b.getCard() == null) {
                                if(enemy.getCurrentMana()>= currentCard.getManaCost()) {
                                    wasPlaced = true;
                                    b.setCard(creature);
                                    System.out.println("card <" + currentCard.getName() + "> was placed on " + b.getBoardPlace());
                                    currentCard.setCurrentPlace(b.getBoardPlace());
                                    currentCard.setxPos(b.getXy().x - (SizePositionValues.CARD_SIZE_X / 2));
                                    currentCard.setyPos(b.getXy().y - (SizePositionValues.CARD_SIZE_Y / 2));
                                    enemy.useMana(currentCard.getManaCost());
                                }
                            }
                        }
                    }
                    if (!wasPlaced) {
                        currentCard.returnToLastPosition();

                    }
                    break;
                case ENEMY_FIELD_1:
                case ENEMY_FIELD_2:
                case ENEMY_FIELD_3:
                case ENEMY_FIELD_4:
                case ENEMY_FIELD_5:
                case ENEMY_FIELD_6:
                    //the card was taken from a friendly battlefield
                    boolean survived = checkCardInteractions(mouse);
                    if (survived) {
                        currentCard.returnToLastPosition();
                    }else{
                        //TODO:KILL ANIMATION
                        currentCard = null;
                    }
                    break;
            }

            currentCard = null;
        }
    }

    //the friendly creature was just removed from a battlefield
    //must see where the user wants to place her and returns a boolean telling
    //if the card should return to its original position
    private boolean checkCardInteractions(Vector2 mousePosition) {

        CreatureCard creature = (CreatureCard) currentCard;

        if(creature.isSick()){
            //the card cant attack just return her
            return true;
        }
        //se bati no hp do inimigo
        if(mousePosition.dst(PlayScreen.enemyHPPos)<SizePositionValues.CARD_SNAP_DISTANCE){
            //attacked the player
            enemy.damage(creature.getAtkTotal());
            creature.setTargetable(true);
            creature.fighted = true;
            return  true;
        }
        //para cada inimigo no campo do inimigo
        for (BattleField creatureField : enemyCreatureHolders) {

            //soltei a carta perto de uma criatura inimiga
            if (mousePosition.dst(creatureField.getXy()) < SizePositionValues.CARD_SNAP_DISTANCE) {


                //se tem criatura e a criatura é passivel de ser target
                //battle!
                if (creatureField.getCard() != null && creatureField.getCard().isTargetable()) {
                    creature.damage(creatureField.getCard());
                    creature.fighted = true;
                    //se a criatura que defendeu morreu
                    if(creatureField.getCard().getHealth()<=0){
                        System.out.println("card "+ creatureField.getCard().getName() +  " died");

                        creatureField.setCard(null);
                    }

                    //morreu
                    if(creature.getHealth()<=0){
                        System.out.println("card "+ currentCard.getName() +  " died");


                        return false;
                    }else{
                        //it survived the battle
                        return true;
                    }
                }
            }
        }
    //nothing happened, survived
    return true;
    }


    private void checkHandInput(int screenX, int screenY) {
        boolean handGrabbed = false;

        cardGrabbed:
        for (int i = player.getHand().size()-1; i>=0; i--){
            Card c = player.getHand().get(i);
            if (c.isClicked(screenX, screenY)) {
                if (currentCard == null) {
                    currentCard = c;

                    handGrabbed = true;

                }
                break cardGrabbed;
            }

        }
        if (handGrabbed) {
            //tira carta da mão
            player.getHand().remove(currentCard);
        }
    }

    private void checkEnemyHandInput(int screenX, int screenY) {
        boolean handGrabbed = false;
        for (Card c : enemy.getHand()) {
            if (c.isClicked(screenX, screenY)) {
                if (currentCard == null) {
                    currentCard = c;

                    handGrabbed = true;

                }
                break;
            }

        }
        if (handGrabbed) {
            //tira carta da mão
            enemy.getHand().remove(currentCard);
        }
    }


    private void checkPassTurn(int x, int y) {
        if (x > SizePositionValues.PASS_TURN_LEFT_X && x < SizePositionValues.PASS_TURN_RIGHT_X) {
            if (y > SizePositionValues.PASS_TURN_BOTTON_Y && y < SizePositionValues.PASS_TURN_UPPER_Y) {

                if (player.isPlaying()) {
                    player.setPlaying(false);
                    enemy.startTurn();
                    unsickEnemyBattleFields();
                } else {
                    enemy.setPlaying(false);
                    player.startTurn();
                    unsickBattleFields();
                }
            }
        }

    }

    private void unsickBattleFields() {
        for (BattleField creatureHolder : creatureHolders) {
            if(creatureHolder.getCard()!=null ){
                creatureHolder.getCard().setSick(false);
            }
        }
    }

    private void unsickEnemyBattleFields() {
        for (BattleField creatureHolder : enemyCreatureHolders) {
            if(creatureHolder.getCard()!=null ){
                creatureHolder.getCard().setSick(false);
            }
        }
    }


    public static List<BattleField> getCreatureHolders() {
        return creatureHolders;
    }


    private List<BattleField> createCreatureHolders() {
        List<BattleField> ret = new ArrayList<BattleField>();
        BattleField one = new BattleField(new Vector2(SizePositionValues.FIELD_CREATURE_ONE_X, SizePositionValues.FIELD_CREATURE_ONE_Y),
                Card.BoardPlace.FIELD_1);
        ret.add(one);
        BattleField two = new BattleField(new Vector2(SizePositionValues.FIELD_CREATURE_TWO_X, SizePositionValues.FIELD_CREATURE_TWO_Y),
                Card.BoardPlace.FIELD_2);
        ret.add(two);
        BattleField three = new BattleField(new Vector2(SizePositionValues.FIELD_CREATURE_THREE_X, SizePositionValues.FIELD_CREATURE_THREE_Y),
                Card.BoardPlace.FIELD_3);
        ret.add(three);
        BattleField four = new BattleField(new Vector2(SizePositionValues.FIELD_CREATURE_FOUR_X, SizePositionValues.FIELD_CREATURE_FOUR_Y),
                Card.BoardPlace.FIELD_4);
        ret.add(four);
        BattleField five = new BattleField(new Vector2(SizePositionValues.FIELD_CREATURE_FIVE_X, SizePositionValues.FIELD_CREATURE_FIVE_Y),
                Card.BoardPlace.FIELD_5);
        ret.add(five);
        BattleField six = new BattleField(new Vector2(SizePositionValues.FIELD_CREATURE_SIX_X, SizePositionValues.FIELD_CREATURE_SIX_Y),
                Card.BoardPlace.FIELD_6);
        ret.add(six);

        return ret;
    }

    private List<BattleField> createEnemyCreatureHolders() {
        List<BattleField> ret = new ArrayList<BattleField>();

        BattleField one = new BattleField(new Vector2(SizePositionValues.FIELD_ENEMY_CREATURE_ONE_X, SizePositionValues.FIELD_ENEMY_CREATURE_ONE_Y),
                Card.BoardPlace.ENEMY_FIELD_1);
        ret.add(one);

        BattleField two = new BattleField(new Vector2(SizePositionValues.FIELD_ENEMY_CREATURE_TWO_X, SizePositionValues.FIELD_ENEMY_CREATURE_TWO_Y),
                Card.BoardPlace.ENEMY_FIELD_2);
        ret.add(two);

        BattleField three = new BattleField(new Vector2(SizePositionValues.FIELD_ENEMY_CREATURE_THREE_X, SizePositionValues.FIELD_ENEMY_CREATURE_THREE_Y),
                Card.BoardPlace.ENEMY_FIELD_3);
        ret.add(three);

        BattleField four = new BattleField(new Vector2(SizePositionValues.FIELD_ENEMY_CREATURE_FOUR_X, SizePositionValues.FIELD_ENEMY_CREATURE_FOUR_Y),
                Card.BoardPlace.ENEMY_FIELD_4);
        ret.add(four);

        BattleField five = new BattleField(new Vector2(SizePositionValues.FIELD_ENEMY_CREATURE_FIVE_X, SizePositionValues.FIELD_ENEMY_CREATURE_FIVE_Y),
                Card.BoardPlace.ENEMY_FIELD_5);
        ret.add(five);


        BattleField six = new BattleField(new Vector2(SizePositionValues.FIELD_ENEMY_CREATURE_SIX_X, SizePositionValues.FIELD_ENEMY_CREATURE_SIX_Y),
                Card.BoardPlace.ENEMY_FIELD_6);
        ret.add(six);


        return ret;
    }




    //bellow NOTHING REALLY MATTERS


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    @Override
    public void dispose() {
        backGround.dispose();
        cardBg.dispose();
        mana.dispose();
        emptyMana.dispose();
        cardBack.dispose();
        boardFont.dispose();
        manaCost.dispose();
        atkHolder.dispose();
        boardFont.dispose();
        cardFont.dispose();

    }
}
