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
        //doria && coach abilieties
        //BLUE
        buildAecio(ret);
        buildTemer(ret);
        buildDoria(ret);
        buildFrota(ret);
        buildCaveirao(ret);
        buildGerente(ret);
        buildCoach(ret);
        buildCoxinha(ret);
        buildLiberal(ret);



        //RED
        buildLula(ret);
        buildDilma(ret);
        buildPabloVitar(ret);
        buildJeanWillis(ret);
        buildDuvivier(ret);
        buildChicoBuarque(ret);
        buildFeminista(ret);
        buildProfessor(ret);
        buildVeterano(ret);
        buildCalouro(ret);

        //PURPLE
        buildChicoXavier(ret);
        buildDeus(ret);
        buildEtDeVarginha(ret);
        buildJesus(ret);
        buildZeDoCaixao(ret);
        buildChupaCu(ret);
        buildAcreBoy(ret);


        //VERDE-AMARELO
        buildBolsonaro(ret);
        buildMoro(ret);
        buildMiliciano(ret);
        BuildPM(ret);
        buildSaraWinter(ret);
        buildCrente(ret);
        buildSeuTio(ret);

        initializeMetaCards();

        return ret;
    }

    private static void buildTemer(List<Card> ret) {
        Texture tex = new Texture("Card_arts/azul/Temer.png");

        CreatureCard card = new CreatureCard("Temer", 8, 7, 1, Card.Faction.AZUL,
                Card.CardType.CRIATURA, Card.Tribo.POLITICO, tex, 7);


        //todo: vampirismo 50%

        ret.add(card);
    }


    private static void buildLiberal(List<Card> ret) {
        Texture tex = new Texture("Card_arts/azul/Liberal.png");

        CreatureCard card = new CreatureCard("Liberal", 2, 1, 1, Card.Faction.AZUL,
                Card.CardType.CRIATURA, Card.Tribo.GENTE_DE_BEM, tex, 1);

        ret.add(card);
    }

    private static void buildGerente(List<Card> ret) {
        Texture tex = new Texture("Card_arts/azul/Gerente.png");

        CreatureCard card = new CreatureCard("Gerente", 3, 3, 1, Card.Faction.AZUL,
                Card.CardType.CRIATURA, Card.Tribo.GENTE_DE_BEM, tex, 2);

        ret.add(card);
    }

    private static void buildFrota(List<Card> ret) {
        Texture tex = new Texture("Card_arts/azul/Doria.png");

        CreatureCard card = new CreatureCard("Doria", 4, 7, 1, Card.Faction.AZUL,
                Card.CardType.CRIATURA, Card.Tribo.POLITICO, tex, 4);

        //TAUNT

        ret.add(card);
    }

    private static void buildDoria(List<Card> ret) {
        Texture tex = new Texture("Card_arts/azul/Doria.png");

        CreatureCard card = new CreatureCard("Doria", 3, 14, 1, Card.Faction.AZUL,
                Card.CardType.CRIATURA, Card.Tribo.POLITICO, tex, 9);

        //TODO: QUANDO CAUSA DANO ABRE CNPJ

        ret.add(card);
    }

    private static void buildCoach(List<Card> ret) {
        Texture tex = new Texture("Card_arts/azul/Coach.png");

        CreatureCard card = new CreatureCard("Coach", 1, 1, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 2);

        //TODO: HERBA LIFE -> PARA CADA COACH GANHA 2/2

        ret.add(card);
    }

    private static void buildZeDoCaixao(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Ze do Caixao.png");

        CreatureCard card = new CreatureCard("Ze do Caixao", 2, 3, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 4);

        //TODO: TERROR: TODAS CRIATURAS INIMIGAS -1/-1
        ret.add(card);
    }

    private static void buildJesus(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Jesus.png");

        CreatureCard card = new CreatureCard("Jesus", 4, 2, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 6);

        //TODO: VOLTA 3 TURNOS DEPOIS DE MORRER +4/+4 RUSH ATROPELAR

        ret.add(card);
    }

    private static void buildEtDeVarginha(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Et de varginha.png");

        CreatureCard card = new CreatureCard("Et de Varginha", 2, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 2);

        //TODO: TAUNT/-1 DE DANO DE CRIATURAS

        ret.add(card);
    }

    private static void buildDeus(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/deus.png");

        CreatureCard card = new CreatureCard("Deus", 5, 5, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 6);

        //TODO: DILUVIO MATA TODAS AS CRIATURAS


        ret.add(card);
    }


    private static void buildChupaCu(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Chupa cu.png");

        CreatureCard card = new CreatureCard("Chupa cu", 4, 1, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 3);


        //TODO: ETHEREO
        ret.add(card);
    }

    private static void buildChicoXavier(List<Card> ret) {
        Texture tex = new Texture("Card_arts/roxa/Chico Xavier.png");

        CreatureCard card = new CreatureCard("Chico Xavier", 4, 8, 1, Card.Faction.ROXA,
                Card.CardType.CRIATURA, Card.Tribo.ILLUMINATI, tex, 7);

        //TODO: PSICOGRAFIA: BATTLECRY TRAS A ULTIMA CARTA QUE MORREU DE VOLTA EM FORMA ETHEREA (N PODE SER ATACADA POR CRIATURAS)

        ret.add(card);
    }

    private static void buildSeuTio(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/tio renato.jpeg");

        CreatureCard card = new CreatureCard("Seu tio", 2, 2, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.GENTE_DE_BEM, tex, 2);

        //TODO: PIADA DE CHURRASCO:CONSTRANGE A CRIATURA EM SUA FRENTE (-1,-1)

        ret.add(card);
    }

    private static void buildSaraWinter(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Sara Winter.png");

        CreatureCard card = new CreatureCard("Sara Winter", 4, 3, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 4);


        //TODO:DOIDA-PSICOPATA RUSH && SE MATAR A CRIATURA BATE NO HP DO PLAYER

        ret.add(card);
    }

    private static void BuildPM(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/PM.png");

        CreatureCard card = new CreatureCard("PM", 5, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 5);


        //TODO: TAUNT
        ret.add(card);
    }

    private static void buildMoro(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Moro.png");

        CreatureCard card = new CreatureCard("Moro", 2, 7, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 5);

        //TODO: IMPARCIAL -> SEM PARCIALIDADE TIRA CRIATURA ALVO DE JOGO

        ret.add(card);
    }

    private static void buildMiliciano(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Miliciano.png");

        CreatureCard card = new CreatureCard("Miliciano", 5, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.POLITICO, tex, 8);

        //TODO: TIRA 1 DE MANA PRA SEMPRE
        ret.add(card);
    }

    private static void buildCrente(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/crente.jpeg");

        CreatureCard card = new CreatureCard("Crente", 0, 3, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.GENTE_DE_BEM, tex, 0);

        //TODO: DIZIMO -> CONSOME TODO MANA E DA AQUILO DE DANO

        ret.add(card);
    }

    private static void buildBolsonaro(List<Card> ret) {
        Texture tex = new Texture("Card_arts/verdeAmarelo/Bolsonaro.png");

        CreatureCard card = new CreatureCard("Bolsonaro", 13, 5, 1, Card.Faction.VERDEAMARELO,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 1);


        //TODO: GENOCIDIO: -0/-2 QUANDO ENTRA, E 0/-2 CADA TURNO EM TODOS OS INIMIGOS E  -0/-1 QUANDO ENTRA, E 0/-1
        ret.add(card);
    }



    private static void buildProfessor(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/Professor.png");

        CreatureCard card = new CreatureCard("Professor", 2, 4, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 4);

        //TODO: BUFFA ALUNOS +3/+0

        ret.add(card);
    }

    private static void buildPabloVitar(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/pabloVitar.png");

        CreatureCard card = new CreatureCard("Pabllo Vittar", 4, 8, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, tex, 6);

        //TODO: ENGRAVIDAR DO LULA: CRIA UM ESTUDANTE EM CAMPO QDO CAUSA DANO
        ret.add(card);
    }

    private static void buildLula(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/lula.png");

        CreatureCard card = new CreatureCard("Lula", 8, 8, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, tex, 9);

        //FOME-ZERO: BUFFA TODAS AS CARTAS NO SEU DECK 2/2 MAS ELAS CUSTAM +1 DE MANA

        ret.add(card);
    }

    private static void buildJeanWillis(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/Jean Wyllys.png");

        CreatureCard card = new CreatureCard("Jean Wyllys", 4, 6, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.POLITICO, tex, 1);

        //TODO: CUSPE -> SPLASH DAMAGE DE 1 DE DANO NAS CRIATRAS ADJACENTES

        ret.add(card);
    }

    private static void buildDuvivier(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/duvivier.png");

        CreatureCard card = new CreatureCard("Duvivier", 5, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ARTISTA, tex, 4);

        //TODO:PORTA DOS FUNDOS -> ANULA HABILIDADES DE POLITICOS ENQTO VIVO


        ret.add(card);
    }

    private static void buildFeminista(List<Card> ret) {
        Texture tex = new Texture("Card_arts/vermelho/feminista.png");

        CreatureCard card = new CreatureCard("Feminista", 4, 5, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, tex, 3);

        ret.add(card);
    }

    private static void buildDilma(List<Card> ret) {

        Texture tex = new Texture("Card_arts/vermelho/dilma.png");

        CreatureCard card = new CreatureCard("Dilma", 7, 6, 1, Card.Faction.VERMELHA,
                Card.CardType.CRIATURA, Card.Tribo.POLITICO, tex, 9);

        //TODO: QDO MORRE COLOCA TEMER EM JOGO

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

        CreatureCard caveiraoCard  = new CreatureCard("Caveirao",3,5,1,
                Card.Faction.AZUL,Card.CardType.CRIATURA, Card.Tribo.GENTE_DE_BEM, caveirao,
                4);

        caveiraoCard.setAbilities(CreatureCard.Ability.BUFF_GENTE_DE_BEM);

        //TAUNT

        ret.add(caveiraoCard);
    }

    private static void buildAecio(List<Card> ret) {
        Texture aecio = new Texture("Card_arts/azul/aecio.png");

        CreatureCard aecioCard =  new CreatureCard("Aecio Neves",8,4,1,
                Card.Faction.AZUL,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, aecio,
                8);

        aecioCard.setAbilities(CreatureCard.Ability.BUFF_COCAINE);

        ret.add(aecioCard);
    }

    private static void buildCalouro(List<Card> ret) {
        Texture calouroFederal = new Texture("Card_arts/vermelho/calouro.png");

        CreatureCard calouro=  new CreatureCard("Calouro da federal",1,2,1,
                Card.Faction.VERMELHA,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, calouroFederal,
                1);
        //TODO EM 3 TURNOS VIRA UM VETERANO

        ret.add(calouro);
    }

    private static void buildVeterano(List<Card> ret) {
        Texture veteranoFederal = new Texture("Card_arts/vermelho/veteranoFederal.png");

        CreatureCard veterano  = new CreatureCard("Veterano da federal",3,5,1,
                Card.Faction.VERMELHA,Card.CardType.CRIATURA, Card.Tribo.ESTUDANTE, veteranoFederal,
                3);

        ret.add(veterano);
    }

    private static void buildAcreBoy(List<Card> ret) {
        Texture acreBoyArt = new Texture("Card_arts/roxa/meninoDoAcre.png");

        CreatureCard acreBoy =   new CreatureCard("Menino do acre",5,2,1,
                Card.Faction.ROXA,Card.CardType.CRIATURA, Card.Tribo.ILLUMINATI, acreBoyArt,
                4);

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
