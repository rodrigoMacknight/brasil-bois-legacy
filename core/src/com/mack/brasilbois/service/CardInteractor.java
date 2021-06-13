package com.mack.brasilbois.service;

import com.badlogic.gdx.math.Vector2;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.model.BattleField;
import com.mack.brasilbois.model.Card;
import com.mack.brasilbois.model.CreatureCard;
import com.mack.brasilbois.model.Player;


import static com.mack.brasilbois.view.PlayScreen.currentCard;
import static com.mack.brasilbois.view.PlayScreen.enemy;
import static com.mack.brasilbois.view.PlayScreen.enemyCreatureHolders;
import static com.mack.brasilbois.view.PlayScreen.enemyHPPos;
import static com.mack.brasilbois.view.PlayScreen.porradaSound;

import java.util.List;


public class CardInteractor {

    //decides what to do based on where a released a card
    public static boolean checkCardInteractions(Vector2 mousePosition) {
        {
           // Vector2 mousePosition = new Vector2(screenX, ipsolon);
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
            for (BattleField enemyCreatureField : enemyCreatureHolders) {

                //soltei a carta perto de uma criatura inimiga
                if (mousePosition.dst(enemyCreatureField.getXy()) < SizePositionValues.CARD_SNAP_DISTANCE) {


                    //se tem criatura e a criatura é passivel de ser target
                    //battle!
                    if (enemyCreatureField.getCard() != null && enemyCreatureField.getCard().isTargetable()) {
                        playHitEffect();
                        //da dano
                        creature.damage(enemyCreatureField.getCard());
                        creature.fighted = true;
                        //se a criatura que defendeu morreu
                        if(enemyCreatureField.getCard().getHealth()<=0){
                            System.out.println("card "+ enemyCreatureField.getCard().getName() +  " died");

                            enemyCreatureField.killCurrentCard();
                        }

                        //se a criatura atacante morreu
                        if(creature.getHealth()<=0){
                            System.out.println("card "+ currentCard.getName() +  " died");
                            creature.kill();

                            return false;
                        }else{
                            //it survived the battle
                            currentCard.returnToLastPosition();
                            return true;
                        }
                    }
                }
            }
            //nothing happened, survived
            return true;
        }
    }


    private static void playHitEffect() {
        porradaSound.play();

        System.out.printf("vai tocar o som");

    }


}
