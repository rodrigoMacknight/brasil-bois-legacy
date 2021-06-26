package com.mack.brasilbois.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.mack.brasilbois.BrBoisMain;
import com.mack.brasilbois.model.eventsMapper.PlaceCardEvent;
import com.mack.brasilbois.service.BattleClient;
import com.mack.brasilbois.service.CardBuilder;
import com.mack.brasilbois.service.CardInteractor;
import com.mack.brasilbois.service.Tests;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.model.BattleField;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;
import com.mack.brasilbois.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen, InputProcessor {
    //distancia que uma carta precisa estar para as duas interagirem
    boolean drawAssistence = false;

    public static boolean gameStarted = false;
    public static boolean gameEnded = false;
    //connects to battle server
    public static BattleClient battleClient;
    //in order to access the game.batch
    private final BrBoisMain game;
    //loading comons textures
    private final Texture backGround;
    //reference to the layout of every card
    public static Texture cardBg;
    public static Texture mana;
    public static Texture emptyMana;
    public static Texture cardBack;
    public static Texture manaCost;
    public static Texture atkHolder;
    public static Texture cocaine;
    public static Texture cristo;
    public static Texture endTurn;


    public static Texture awaitBattle;
    public static Sound porradaSound;


    public static Vector2 playerHPPos;
    public static Vector2 enemyHPPos;


    public static Player player;
    public static Player enemy;

    public static List<BattleField> creatureHolders;
    public static List<BattleField> enemyCreatureHolders;

    public static Card currentCard; //holds the card to be dragged and dropped

    public static BitmapFont boardFont;
    public static BitmapFont cardFont;
    public static BitmapFont descFont;


    public PlayScreen(BrBoisMain game) {
        this.game = game;
        configSocketForPlay();

        //loading comum textures for the game
        awaitBattle = new Texture("Layout/lulaPhilip.png");
        backGround = new Texture("Layout/NewLayout.png");
        cardBg = new Texture("Layout/newCard.png");
        mana = new Texture("Layout/mana.png");
        emptyMana = new Texture("Layout/emptyMana.png");
        cardBack = new Texture("Layout/cardback.png");
        manaCost = new Texture("Layout/manaCost.png");
        atkHolder = new Texture("Layout/atkHolder.png");
        cocaine = new Texture("Layout/cocaine.png");
        cristo = new Texture("Layout/cristo.png");
        endTurn = new Texture("Layout/endTurn.png");
        //creating the fonts
        boardFont = new BitmapFont(Gdx.files.internal("Fonts/teste.fnt"));
        cardFont = new BitmapFont(Gdx.files.internal("Fonts/cardFontHolder.fnt"));
        descFont = new BitmapFont(Gdx.files.internal("Fonts/description.fnt"));

        //setting that this class is listening for input
        Gdx.input.setInputProcessor(this);

        //generating placeholder deck
        player = new Player(Tests.getTestDeck(20, false));
        enemy = new Player(Tests.getTestDeck(20, true));
        //sounds
        porradaSound = Gdx.audio.newSound(Gdx.files.internal("porrada2.mp3"));
        //set the player that owns that card for all the cards
        enemy.setOwner();
        player.setOwner();
        //initilize player hand
        player.grabCard(5);
        enemy.grabCard(6);
        //player.startTurn();

        //loads hpMaths
        playerHPPos = new Vector2(SizePositionValues.PLAYER_HP_X, SizePositionValues.PLAYER_HP_Y);
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
        if(gameEnded) {
            renderGameEnded();
        } else if (gameStarted) {
            renderBoard();
        }
        else renderAwaitingForPlayer();
        game.batch.end();

    }

    private void renderGameEnded() {
        //264 width

        if (player.health <= 0) {
            game.batch.draw(awaitBattle, 50, 50, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200);

            boardFont.draw(game.batch,"VOCÊ É O PERDEDOR!", ((Gdx.graphics.getWidth()/2) - 132) , Gdx.graphics.getHeight()/2 );
        } else {
            game.batch.draw(awaitBattle, 50, 50, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200);

            boardFont.draw(game.batch,"VOCÊ É O GANHANDOR!", 400 , 400 );
        }


    }

    private void renderAwaitingForPlayer() {
        game.batch.draw(awaitBattle, 50, 50, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200);

        boardFont.draw(game.batch,"Aguardando outro player", 60 , 60 );
    }

    private void renderBoard() {
        game.batch.draw(backGround, 0, 0);

        player.drawGrimorio(game.batch);
        player.drawHand(game.batch);


        enemy.drawEnemyHand(game.batch);
        enemy.drawGrimorio(game.batch);

        //creatureHolders.printAssitent();
        //draw cards and creatures on the table
        drawBoardPlace();
        //tiny dots in the middle of stuff to check its x,y positions easier
        if (drawAssistence) drawBattlefieldAssistant();
        drawMana();

        drawEndTurn();
        //drawing the hp
        boardFont.draw(game.batch, player.getHp(), SizePositionValues.PLAYER_HP_X, SizePositionValues.PLAYER_HP_Y);
        boardFont.draw(game.batch, enemy.getHp(), SizePositionValues.ENEMY_HP_X, SizePositionValues.ENEMY_HP_Y);
        //drawing the qtty of cards still in the deck
        boardFont.draw(game.batch, player.getDeck().size() + "", SizePositionValues.FRIENDLY_CARD_COUNTER_X, SizePositionValues.FRIENDLY_CARD_COUNTER_Y);
        boardFont.draw(game.batch, enemy.getDeck().size() + "", SizePositionValues.ENEMY_CARD_COUNTER_X, SizePositionValues.ENEMY_CARD_COUNTER_Y);

        if (currentCard != null) {
            //   game.batch.draw(new Texture("coxinha.jpg"), creatureHolders.get(0).getXy().x, creatureHolders.get(0).getXy().y);
            if (currentCard.getCurrentPlace().equals(Card.BoardPlace.HAND)) {
                currentCard.drawCardWithMana(game.batch);
            } else {
                currentCard.drawCardWithoutMana(game.batch);
            }

        }

    }

    private void drawEndTurn() {
        if (player.isPlaying()) {
            game.batch.draw(endTurn, SizePositionValues.PASS_TURN_LEFT_X, SizePositionValues.PASS_TURN_BOTTON_Y
                    , SizePositionValues.PASS_TURN_RIGHT_X - SizePositionValues.PASS_TURN_LEFT_X,
                    SizePositionValues.PASS_TURN_UPPER_Y - SizePositionValues.PASS_TURN_BOTTON_Y);
        } else {
            game.batch.draw(cocaine, SizePositionValues.PASS_TURN_LEFT_X, SizePositionValues.PASS_TURN_BOTTON_Y
                    , SizePositionValues.PASS_TURN_RIGHT_X - SizePositionValues.PASS_TURN_LEFT_X,
                    SizePositionValues.PASS_TURN_UPPER_Y - SizePositionValues.PASS_TURN_BOTTON_Y);
        }
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
                c.drawCardWithoutMana(game.batch);
                // game.batch.draw(PlayScreen.cardBg, c.getxPos(), c.getyPos(), Values.CARD_SIZE_X, Values.CARD_SIZE_Y);
                //game.batch.draw(c.getCardArt(), c.getxPos() + Values.THUMBNAIL_OFFSET_X, c.getyPos() + Values.THUMBNAIL_OFFSET_Y, Values.THUMBNAIL_SIZE_X, Values.THUMBNAIL_SIZE_Y);


            }
        }
        for (BattleField battleField : enemyCreatureHolders) {
            Card c = battleField.getCard();
            if (c != null) {
                //game.batch.draw(PlayScreen.cardBg, c.getxPos(), c.getyPos(), Values.CARD_SIZE_X, Values.CARD_SIZE_Y);
                //game.batch.draw(c.getCardArt(), c.getxPos() + Values.THUMBNAIL_OFFSET_X, c.getyPos() + Values.THUMBNAIL_OFFSET_Y, Values.THUMBNAIL_SIZE_X, Values.THUMBNAIL_SIZE_Y);
                c.drawCardWithoutMana(game.batch);
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
            checkPassTurn(screenX, ypsolon);
        }
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
        Vector2 mousePos = new Vector2(screenX, ipsolon);
        System.out.println("x: " + screenX);
        System.out.println("y: " + ipsolon);

        if (player.isPlaying()) {
            checkUserInput(mousePos);
        }

        return false;
    }

    private void checkUserInput(Vector2 mouse) {

        if (currentCard != null) {

            boolean wasPlaced = false;
            switch (currentCard.getCurrentPlace()) {
                //if the card was in the hand and is a  creature type
                //the user can place her in any battlefield
                case HAND:
                    for (BattleField b : creatureHolders) {

                        //if the user tryed to place a card
                        if (mouse.dst(b.getXy()) < 65) {
                            //se nao tinha carta antes
                            if (b.getCard() == null) {
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
                    boolean survived = CardInteractor.checkCardInteractions(mouse);
                    if (survived) {
                        currentCard.returnToLastPosition();
                    }
                    break;
            }
            currentCard = null; //SEMPRE DEPOIS DE TODA ANALISE DO QUE FAZER A CARTA SETAR A MEMORIA NULA
        }
    }


    private static void placeCardOnField(CreatureCard c, int where) {
        enemyCreatureHolders.get(where).setCard(c);
        c.setxPos(enemyCreatureHolders.get(where).getXy().x - (SizePositionValues.CARD_SIZE_X / 2));
        c.setyPos(enemyCreatureHolders.get(where).getXy().y - (SizePositionValues.CARD_SIZE_Y / 2));
    }


    public static void placeEnemyCard(String placeCardJson) {
        Gson gson = new Gson();
        PlaceCardEvent placeCardEvent = gson.fromJson(placeCardJson, PlaceCardEvent.class);

        //generate card
        CreatureCard creatureToPlace = (CreatureCard) CardBuilder.generateCardFromName(placeCardEvent.cardName);


        switch (placeCardEvent.position) {
            case FIELD_1:
                placeCardOnField(creatureToPlace, 5);
                break;
            case FIELD_2:
                placeCardOnField(creatureToPlace, 4);
                break;
            case FIELD_3:
                placeCardOnField(creatureToPlace, 3);
                break;
            case FIELD_4:
                placeCardOnField(creatureToPlace, 2);
                break;
            case FIELD_5:
                placeCardOnField(creatureToPlace, 1);
                break;
            case FIELD_6:
                placeCardOnField(creatureToPlace, 0);
                break;
        }
        enemy.useMana(creatureToPlace.getManaCost());

        if (creatureToPlace.hasSummoningAction()) {
            creatureToPlace.doSummoningAction(enemyCreatureHolders);
        }

    }


    private boolean placeCard(BattleField battlefield) {
        if (currentCard instanceof CreatureCard) {
            CreatureCard currentCreature = (CreatureCard) currentCard;
            //check if card mana cost higher than my mana cost
            if (player.getCurrentMana() >= currentCard.getManaCost()) {

                battlefield.setCard(currentCreature);

                if (currentCreature.hasSummoningAction()) {
                    currentCreature.doSummoningAction(creatureHolders);
                }
                currentCard.setCurrentPlace(battlefield.getBoardPlace());

                //places the texture on top of that battlefield
                currentCard.setxPos(battlefield.getXy().x - (SizePositionValues.CARD_SIZE_X / 2));
                currentCard.setyPos(battlefield.getXy().y - (SizePositionValues.CARD_SIZE_Y / 2));
                //subtract mana from the uesr
                player.useMana(currentCard.getManaCost());
                //seta enjoo de criatura
                currentCreature.setSick(true);

                battleClient.sendPlaceCardToServer(battlefield, currentCreature);
                currentCard = null; //solta a carta da mao
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private void checkHandInput(int screenX, int screenY) {
        boolean handGrabbed = false;

        for (int i = player.getHand().size() - 1; i >= 0; i--) {
            Card c = player.getHand().get(i);
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
            player.getHand().remove(currentCard);
        }
    }


    private void checkPassTurn(int x, int y) {
        if (x > SizePositionValues.PASS_TURN_LEFT_X && x < SizePositionValues.PASS_TURN_RIGHT_X) {
            if (y > SizePositionValues.PASS_TURN_BOTTON_Y && y < SizePositionValues.PASS_TURN_UPPER_Y) {
                //send end turn to server

                if (player.isPlaying()) {
                    battleClient.sendEndTurn();
                    player.setPlaying(false);
                    enemy.setPlaying(true);
                    //enemy.startTurn();
                    //unsickEnemyBattleFields();
                } else { //TODO: THIS WILL NEVER HAPPEN
                    enemy.setPlaying(false);
                    player.startTurn();
                    unsickBattleFields();
                }
            }
        }

    }

    public static void unsickBattleFields() {
        for (BattleField creatureHolder : creatureHolders) {
            if (creatureHolder.getCard() != null) {
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

    private void configSocketForPlay() {
        battleClient = new BattleClient();
        battleClient.connectPlaySocket();
        battleClient.configSocketListeners();
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
