package com.mack.brasilbois.service;

import com.mack.brasilbois.BrBoisMain;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.model.Card;

import java.util.Random;
import java.util.Stack;


public class Tests {

    //returns a test Deck
    public static  Stack<Card> getTestDeck(int cardTotal,boolean enemy){
        Stack<Card> ret = new Stack<>();


        for (int i= 0;i<cardTotal;i++){
            Card c ;
            Random rand =  new Random();
            int allCardsCount = BrBoisMain.allCards.size();
            c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();

            if(enemy) {
                c.setxPos(SizePositionValues.ENEMY_GRIMORIO_X);
                c.setyPos(SizePositionValues.ENEMY_GRIMORIO_Y);

            }else{
                c.setxPos(SizePositionValues.FRIENDLY_GRIMORIO_X);
                c.setyPos(SizePositionValues.FRIENDLY_GRIMORIO_Y);
            }

            ret.push(c);
        }
        return ret;
    }



    public static Stack<Card> getRedDeck(int cardTotal, boolean enemy) {
        Stack<Card> ret = new Stack<>();
        for (int i= 0;i<cardTotal;i++){
            Card c ;
            Random rand =  new Random();
            int allCardsCount = BrBoisMain.allCards.size();
            c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            while (c.getFaction()!= Card.Faction.VERMELHA) {
                c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            }

            if(enemy) {
                c.setxPos(SizePositionValues.ENEMY_GRIMORIO_X);
                c.setyPos(SizePositionValues.ENEMY_GRIMORIO_Y);

            }else{
                c.setxPos(SizePositionValues.FRIENDLY_GRIMORIO_X);
                c.setyPos(SizePositionValues.FRIENDLY_GRIMORIO_Y);
            }

            ret.push(c);
        }
        return ret;
    }




    public static Stack<Card> getVerdeAmareloDeck(int cardTotal, boolean enemy) {
        Stack<Card> ret = new Stack<>();
        for (int i= 0;i<cardTotal;i++){
            Card c ;
            Random rand =  new Random();
            int allCardsCount = BrBoisMain.allCards.size();
            c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            while (c.getFaction()!= Card.Faction.VERDEAMARELO) {
                c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            }

            if(enemy) {
                c.setxPos(SizePositionValues.ENEMY_GRIMORIO_X);
                c.setyPos(SizePositionValues.ENEMY_GRIMORIO_Y);

            }else{
                c.setxPos(SizePositionValues.FRIENDLY_GRIMORIO_X);
                c.setyPos(SizePositionValues.FRIENDLY_GRIMORIO_Y);
            }

            ret.push(c);
        }
        return ret;
    }


    public static Stack<Card> getBlueDeck(int cardTotal, boolean enemy) {
        Stack<Card> ret = new Stack<>();
        for (int i= 0;i<cardTotal;i++){
            Card c ;
            Random rand =  new Random();
            int allCardsCount = BrBoisMain.allCards.size();
            c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            while (c.getFaction()!= Card.Faction.AZUL) {
                c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            }

            if(enemy) {
                c.setxPos(SizePositionValues.ENEMY_GRIMORIO_X);
                c.setyPos(SizePositionValues.ENEMY_GRIMORIO_Y);

            }else{
                c.setxPos(SizePositionValues.FRIENDLY_GRIMORIO_X);
                c.setyPos(SizePositionValues.FRIENDLY_GRIMORIO_Y);
            }

            ret.push(c);
        }
        return ret;
    }

    public static Stack<Card> getPurpleDeck(int cardTotal, boolean enemy) {
        Stack<Card> ret = new Stack<>();
        for (int i= 0;i<cardTotal;i++){
            Card c ;
            Random rand =  new Random();
            int allCardsCount = BrBoisMain.allCards.size();
            c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            while (c.getFaction()!= Card.Faction.ROXA) {
                c = BrBoisMain.allCards.get(rand.nextInt(allCardsCount)).getCopy();
            }

            if(enemy) {
                c.setxPos(SizePositionValues.ENEMY_GRIMORIO_X);
                c.setyPos(SizePositionValues.ENEMY_GRIMORIO_Y);

            }else{
                c.setxPos(SizePositionValues.FRIENDLY_GRIMORIO_X);
                c.setyPos(SizePositionValues.FRIENDLY_GRIMORIO_Y);
            }

            ret.push(c);
        }
        return ret;
    }

    public static  Stack<Card> getCaetanoTestDeck(int cardTotal,boolean enemy){
        Stack<Card> ret = new Stack<>();

        for (int i= 0;i<cardTotal;i++){
            Card c ;
            if (i%2 ==0) {
                 c = BrBoisMain.getCardByName("Chico Buarque").getCopy();
            } else {
                c = BrBoisMain.getCardByName("Aecio Neves").getCopy();
            }
            if(enemy) {
                c.setxPos(SizePositionValues.ENEMY_GRIMORIO_X);
                c.setyPos(SizePositionValues.ENEMY_GRIMORIO_Y);

            }else{
                c.setxPos(SizePositionValues.FRIENDLY_GRIMORIO_X);
                c.setyPos(SizePositionValues.FRIENDLY_GRIMORIO_Y);
            }

            ret.push(c);
        }
        return ret;
    }

}
