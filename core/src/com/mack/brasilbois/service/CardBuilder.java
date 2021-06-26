package com.mack.brasilbois.service;

import com.badlogic.gdx.graphics.Texture;
import com.mack.brasilbois.BrBoisMain;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardBuilder {

    //initialize all cards

    public static List<Card> metaCards;

    public static List<Card> initializeCards() {
        List<Card> ret = new ArrayList<>();

        //BLUE
        buildAecio(ret);
        buildCaveirao(ret);
        buildCoxinha(ret);

        //RED
        buildCalouro(ret);
        buildChicoBuarque(ret);
        buildDilma(ret);
        buildDuvivier(ret);
        buildFeminista(ret);
        buildJeanWillis(ret);
        buildLula(ret);
        buildPabloVitar(ret);
        buildProfessor(ret);
        buildVeterano(ret);

        //PURPLE
        buildChicoXavier(ret);
        buildChupaCu(ret);
        buildDeus(ret);
        buildEtDeVarginha(ret);
        buildJesus(ret);
        buildAcreBoy(ret);
        buildZeDoCaixao(ret);

        //VERDE-AMARELO
        buildBolsonaro(ret);
        buildCrente(ret);
        buildMiliciano(ret);
        buildMoro(ret);
        BuildPM(ret);
        buildSaraWinter(ret);
        buildTioRenato(ret);

        initializeMetaCards();

        return ret;
    }

    private static void buildZeDoCaixao(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Ze do Caixao.png");

        CreatureCard card = new CreatureCard("Ze do Caixao", 13, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildJesus(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Jesus.png");

        CreatureCard card = new CreatureCard("Jesus", 13, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildEtDeVarginha(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Et de varginha.png");

        CreatureCard card = new CreatureCard("Et de Varginha", 13, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildDeus(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/deus.png");

        CreatureCard card = new CreatureCard("Deus", 13, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }


    private static void buildChupaCu(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Chupa cu.png");

        CreatureCard card = new CreatureCard("Chupa cu", 13, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildChicoXavier(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Chico Xavier.png");

        CreatureCard card = new CreatureCard("Chico Xavier", 13, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildTioRenato(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/tio renato.jpeg");

        CreatureCard card = new CreatureCard("Tio Renato", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildSaraWinter(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Sara Winter.png");

        CreatureCard card = new CreatureCard("Sara Winter", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void BuildPM(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/PM.png");

        CreatureCard card = new CreatureCard("PM", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildMoro(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Moro.png");

        CreatureCard card = new CreatureCard("Moro", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildMiliciano(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Miliciano.png");

        CreatureCard card = new CreatureCard("Miliciano", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildCrente(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/crente.jpeg");

        CreatureCard card = new CreatureCard("Crente", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildBolsonaro(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Bolsonaro.png");

        CreatureCard card = new CreatureCard("Bolsonaro", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }



    private static void buildProfessor(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/Professor.png");

        CreatureCard card = new CreatureCard("Professor", 13, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildPabloVitar(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/pabloVitar.png");

        CreatureCard card = new CreatureCard("Pabllo Vittar", 13, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, tex, 1);

        ret.add(card);
    }

    private static void buildLula(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/lula.png");

        CreatureCard card = new CreatureCard("Lula", 13, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, tex, 1);

        ret.add(card);
    }

    private static void buildJeanWillis(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/Jean Wyllys.png");

        CreatureCard card = new CreatureCard("Jean Wyllys", 13, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, tex, 1);

        ret.add(card);
    }

    private static void buildDuvivier(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/duvivier.png");

        CreatureCard card = new CreatureCard("Duvivier", 13, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, tex, 1);

        ret.add(card);
    }

    private static void buildFeminista(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/feminista.png");

        CreatureCard card = new CreatureCard("Feminista", 13, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);

        ret.add(card);
    }

    private static void buildDilma(List<Card> ret) {

        Texture tex = new Texture("Card_arts/vermelho/dilma.png");

        CreatureCard card = new CreatureCard("Dilma", 13, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.POLITICO, tex, 1);

        ret.add(card);
    }


    private static void buildChicoBuarque(List<Card> ret) {

        Texture chicoTexture = new Texture("Card_arts/vermelho/chico_vivo.png");

        CreatureCard chicoBuarque = new CreatureCard("Chico Buarque", 1, 8,1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, chicoTexture, 1);


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
        Texture aecio = new Texture("Card_arts/azul/aecio.png");

        CreatureCard aecioCard =  new CreatureCard("Aecio Neves",10,5,1,
                Card.Faction.AZUL,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, aecio,
                1);

        aecioCard.setAbilities(CreatureCard.Ability.BUFF_COCAINE);

        ret.add(aecioCard);
    }

    private static void buildCalouro(List<Card> ret) {
        Texture calouroFederal = new Texture("Card_arts/vermelho/calouro.png");

        CreatureCard calouro=  new CreatureCard("Calouro da federal",2,3,1,
                Card.Faction.VERMELHA,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, calouroFederal,
                1);

        ret.add(calouro);
    }

    private static void buildVeterano(List<Card> ret) {
        Texture veteranoFederal = new Texture("Card_arts/vermelho/veteranoFederal.png");

        CreatureCard veterano  = new CreatureCard("Veterano da federal",3,6,1,
                Card.Faction.VERMELHA,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, veteranoFederal,
                1);

        ret.add(veterano);
    }

    private static void buildAcreBoy(List<Card> ret) {
        Texture acreBoyArt = new Texture("Card_arts/roxa/meninoDoAcre.png");

        CreatureCard acreBoy =   new CreatureCard("Menino do acre",4,2,1,
                Card.Faction.ROXA,Card.CardType.CRIATURA, Card.Tribo.ILLUMINATI, acreBoyArt,
                1);

        acreBoy.setAbilities(CreatureCard.Ability.STEALTH);

        ret.add(acreBoy);
    }

    private static void buildCoxinha(List<Card> ret) {
        Texture coxinhaArt = new Texture("Card_arts/azul/coxinha.png");

        Card coxinha = new CreatureCard("coxinha",2,3,1,
                Card.Faction.AZUL,Card.CardType.CRIATURA, Card.Tribo.GENTE_DE_BEM, coxinhaArt,
                1);
        ret.add(coxinha);
    }

    public static CreatureCard buildChicoBuarqueDead() {

        Texture chicoTexture = new Texture("Card_arts/vermelho/chico_morto.png");

        CreatureCard deadChico = new CreatureCard("Corpo do Chico", 0, 99,1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, chicoTexture, 1);
        deadChico.isSelectable = false;

        //chicoBuarque.setAbilities(CreatureCard.Ability.ATRAPALHAR_O_TRANSITO);

        return deadChico;

    }


    public static Card generateCardFromName(final String cardName) {
        List<Card> cardFromNameList =
                BrBoisMain.allCards.stream().
                        filter(card ->card.getName().equals(cardName)).collect(Collectors.toList());

        return cardFromNameList.get(0).getCopy();
    }

    private static void initializeMetaCards() {
        metaCards = new ArrayList<>();
        metaCards.add(buildChicoBuarqueDead());

    }
}
