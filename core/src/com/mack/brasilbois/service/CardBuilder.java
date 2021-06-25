package com.mack.brasilbois.service;

import com.badlogic.gdx.graphics.Texture;
import com.mack.brasilbois.BrBoisMain;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sun.font.CreatedFontTracker;

public class CardBuilder {

    //initialize all cards

    public static List<Card> metaCards;

    public static List<Card> initializeCards() {
        List<Card> ret = new ArrayList<Card>();

        buildCoxinha(ret);

        buildAcreBoy(ret);

        buildVeterano(ret);

        buildCalouro(ret);

        buildAecio(ret);

        buildCaveirao(ret);

        buildChicoBuarque(ret);

        initializeMetaCards(metaCards);

        return ret;
    }

    private static void initializeMetaCards(List<Card> metaCards) {
        metaCards = new ArrayList<>();
        metaCards.add(buildChicoBuarqueDead());

    }


    public static CreatureCard buildChicoBuarqueDead() {

        Texture chicoTexture = new Texture("Card_arts/chico_morto.png");

        CreatureCard deadChico = new CreatureCard("Corpo do Chico", 0, 99,1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.MUSICO, chicoTexture, 1);
        deadChico.isSelectable = false;

        //chicoBuarque.setAbilities(CreatureCard.Ability.ATRAPALHAR_O_TRANSITO);

       return deadChico;

    }



    private static void buildChicoBuarque(List<Card> ret) {

        Texture chicoTexture = new Texture("Card_arts/chico_vivo.png");

        CreatureCard chicoBuarque = new CreatureCard("Chico Buarque", 1, 8,1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.MUSICO, chicoTexture, 1);


        chicoBuarque.setAbilities(CreatureCard.Ability.ATRAPALHAR_O_TRANSITO);

        ret.add(chicoBuarque);

    }

    private static void buildCaveirao(List<Card> ret) {
        Texture caveirao = new Texture("Card_arts/caveirao.png");

        CreatureCard caveiraoCard  = new CreatureCard("Caveirao",1,6,1,
                Card.Faction.AZUL,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, caveirao,
                1);

        caveiraoCard.setAbilities(CreatureCard.Ability.BUFF_GENTE_DE_BEM);

        ret.add(caveiraoCard);
    }

    private static void buildAecio(List<Card> ret) {
        Texture aecio = new Texture("Card_arts/aecio.png");

        CreatureCard aecioCard =  new CreatureCard("Aecio Neves",10,5,1,
                Card.Faction.AZUL,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, aecio,
                1);

        aecioCard.setAbilities(CreatureCard.Ability.BUFF_COCAINE);

        ret.add(aecioCard);
    }

    private static void buildCalouro(List<Card> ret) {
        Texture calouroFederal = new Texture("Card_arts/calouro.png");

        CreatureCard calouro=  new CreatureCard("Calouro da federal",2,3,1,
                Card.Faction.VERMELHA,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, calouroFederal,
                1);

        ret.add(calouro);
    }

    private static void buildVeterano(List<Card> ret) {
        Texture veteranoFederal = new Texture("Card_arts/veteranoFederal.png");

        CreatureCard veterano  = new CreatureCard("Veterano da federal",3,6,1,
                Card.Faction.VERMELHA,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, veteranoFederal,
                1);

        ret.add(veterano);
    }

    private static void buildAcreBoy(List<Card> ret) {
        Texture acreBoyArt = new Texture("Card_arts/meninoDoAcre.png");

        CreatureCard acreBoy =   new CreatureCard("Menino do acre",4,2,1,
                Card.Faction.MACONARIA,Card.CardType.CRIATURA, Card.Tribo.ILLUMINATI, acreBoyArt,
                1);

        acreBoy.setAbilities(CreatureCard.Ability.STEALTH);

        ret.add(acreBoy);
    }

    private static void buildCoxinha(List<Card> ret) {
        Texture coxinhaArt = new Texture("Card_arts/coxinha.png");

        Card coxinha = new CreatureCard("coxinha",2,3,1,
                Card.Faction.AZUL,Card.CardType.CRIATURA, Card.Tribo.GENTE_DE_BEM, coxinhaArt,
                1);
        ret.add(coxinha);
    }

    public static Card generateCardFromName(final String cardName) {
        List<Card> cardFromNameList =
                BrBoisMain.allCards.stream().
                filter(card ->card.getName().equals(cardName)).collect(Collectors.toList());

        Card card = cardFromNameList.get(0).getCopy();
        return card;
    }
}
