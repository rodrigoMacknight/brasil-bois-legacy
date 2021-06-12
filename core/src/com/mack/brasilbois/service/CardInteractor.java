package com.mack.brasilbois.service;

import com.badlogic.gdx.math.Vector2;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.model.BattleField;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;
import com.mack.brasilbois.model.Player;

import java.util.List;


public class CardInteractor {

    //decides what to do based on where a released a card
    public boolean checkCardInteractions(int screenX, int ipsolon,
                                         Card currentCard, Vector2 enemyHPPos,
                                         Player enemy, List<BattleField> enemyCreatureHolders) {
        {
            Vector2 mousePosition = new Vector2(screenX, ipsolon);
            CreatureCard creature = (CreatureCard) currentCard;

            if(creature.isSick()){
                //the card cant attack just return her
                return true;
            }
            //posicao do mouse

            //se bati no hp do inimigo
            if(mousePosition.dst(enemyHPPos)< SizePositionValues.CARD_SNAP_DISTANCE){
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
    }
}
