package com.mack.brasilbois.utils;

import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;

import static com.mack.brasilbois.model.Card.BoardPlace.*;
import static com.mack.brasilbois.view.PlayScreen.creatureHolders;
import static com.mack.brasilbois.view.PlayScreen.enemyCreatureHolders;

public class PlayerEventsExchanger {
    public static Card.BoardPlace getMirrorBattleField(Card.BoardPlace place) {
        switch (place) {
            case FIELD_1:
                return ENEMY_FIELD_6;
            case FIELD_2:
                return ENEMY_FIELD_5;
            case FIELD_3:
                return ENEMY_FIELD_4;
            case FIELD_4:
                return ENEMY_FIELD_3;
            case FIELD_5:
                return ENEMY_FIELD_2;
            case FIELD_6:
                return ENEMY_FIELD_1;
            case ENEMY_FIELD_1:
                return FIELD_6;
            case ENEMY_FIELD_2:
                return FIELD_5;
            case ENEMY_FIELD_3:
                return FIELD_4;
            case ENEMY_FIELD_4:
                return FIELD_3;
            case ENEMY_FIELD_5:
                return FIELD_2;
            case ENEMY_FIELD_6:
                return FIELD_1;




        }

        return null;
    }

    public static CreatureCard getEnemyCreatureCardAt(Card.BoardPlace mirrorBattleField) {
        CreatureCard creatureCard = enemyCreatureHolders.stream().
                filter(c -> c.getBoardPlace()==mirrorBattleField).findAny().orElse(null).getCard();

        return creatureCard;

    }
    public static CreatureCard getFriendlyCreatureCardAt(Card.BoardPlace mirrorBattleField) {
        CreatureCard creatureCard = creatureHolders.stream().
                filter(c -> c.getBoardPlace()==mirrorBattleField).findAny().orElse(null).getCard();

        return creatureCard;

    }

}
