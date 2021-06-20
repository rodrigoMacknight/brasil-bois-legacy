package com.mack.brasilbois.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.view.PlayScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Player {

    private static final int INIT_HP = 40;

    private int health;

    private Stack<Card> playerDeck;

    private List<Card> hand;

    boolean playing = false;


    private List<Card> graveYard;

    public int totalMana;
    private int currentMana;

    public Player(Stack<Card> playerDeck) {
        this.health = INIT_HP;
        this.playerDeck = playerDeck;
        this.graveYard = null;
        totalMana = 0;
        currentMana = 0;
        hand = new ArrayList<Card>();


    }

    public int checkDeckRemainingCards() {

        if (playerDeck != null) {
            return playerDeck.size();
        } else {
            //  System.out.println("ERRO DECK NÃO INICIALIZADO");
            return 0;
        }

    }


    public Stack<Card> getDeck() {
        return this.playerDeck;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public void grabCard(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            Card toAdd = playerDeck.pop();

            hand.add(toAdd);
            toAdd.setCurrentPlace(Card.BoardPlace.HAND);


        }
    }

    //draw cards in the grimorio
    public void drawGrimorio(SpriteBatch batch) {

        List<Card> temp = new ArrayList<Card>();
        if (!playerDeck.isEmpty()) {
            Card c = playerDeck.peek();
            batch.draw(PlayScreen.cardBack, c.getxPos(), c.getyPos(), c.getCardSprite().getWidth(), c.getCardSprite().getHeight());

        }
        playerDeck.addAll(temp);
    }


    public void drawHand(SpriteBatch batch) {
        int offset = 0;
        int cardSize = SizePositionValues.CARD_SIZE_X / 2;

        if (hand.size() > 0) {


            for (Card c : hand) {
                offset += SizePositionValues.HAND_OFFSET;
                c.setxPos(SizePositionValues.FRIENDLY_HAND_X + offset - (hand.size() * cardSize));
                c.setyPos(SizePositionValues.FRIENDLY_HAND_Y);

                c.drawCardWithMana(batch);
            }
        }
    }

    public void drawEnemyHand(SpriteBatch batch) {
        int offset = 0;
        int cardSize = SizePositionValues.CARD_SIZE_X / 2;

        if (hand.size() > 0) {

            if (hand.size() == 1) {
                Card c = hand.get(0);
                c.setxPos(SizePositionValues.ENEMY_HAND_X - c.getCardArt().getWidth());
                c.setyPos(SizePositionValues.ENEMY_HAND_Y);
                batch.draw(hand.get(0).getCardArt(), c.getxPos(), c.getyPos());
            } else {
                for (Card c : hand) {
                    offset += SizePositionValues.HAND_OFFSET;
                    c.setxPos(SizePositionValues.ENEMY_HAND_X + offset - (hand.size() * cardSize));
                    c.setyPos(SizePositionValues.ENEMY_HAND_Y);
                    batch.draw(PlayScreen.cardBg, c.getxPos(), c.getyPos(), SizePositionValues.CARD_SIZE_X, SizePositionValues.CARD_SIZE_Y);
                    //does not show the card to the other user
                    //batch.draw(hand.get(0).getCardArt(), c.getxPos(), c.getyPos());
                }
            }
        }

    }


    public void setOwner() {
        for (Card c : getDeck()) {
            c.setOwner(this);

        }
    }

    public void pickCard() {

        Card c = getDeck().pop();

        //TODO: animation
        getHand().add(c);
        c.setCurrentPlace(Card.BoardPlace.HAND);

    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;

    }

    public void startTurn() {
        //limite de 10 cartas na mão
        PlayScreen.unsickBattleFields();
        if (hand.size() < 10) {
            pickCard();
        } else {
            //queima uma carta
            //TODO: adicionar animação
            getDeck().pop();
        }

        setPlaying(true);

        if (totalMana < 10) {
            //adiciona 1 ao mana pool
            totalMana++;
            //restarta o mana pool
            currentMana = totalMana;
        }
    }

    public CharSequence getHp() {
        return health + "";
    }

    public int getTotalMana() {
        return totalMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void useMana(int manaCost) {
        this.currentMana -= manaCost;
    }

    public void damage(int damage){
        this.health-= damage;
    }

}
