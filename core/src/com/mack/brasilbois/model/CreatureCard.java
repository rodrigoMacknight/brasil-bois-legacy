package com.mack.brasilbois.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.service.CardBuilder;
import com.mack.brasilbois.utils.PlayerEventsExchanger;
import com.mack.brasilbois.view.PlayScreen;

import java.util.ArrayList;
import java.util.List;

import static com.mack.brasilbois.view.PlayScreen.creatureHolders;
import static com.mack.brasilbois.view.PlayScreen.enemyCreatureHolders;

public class CreatureCard extends Card {

    private int health;
    private int attack;
    //or totalHealth
    private int defense;
    private boolean targetable;
    public boolean isSelectable = true;
    // a card is sick in the first turn, and after atacking
    private boolean sick;
    private int attackBonus;
    private float  attackMultiplier;
    public int attackingAnimation = 0;
    public boolean fighted;
    public List<Status> cardStatus;

    public void setAtkBonus(int bonus){
        attackBonus = bonus;
    }

    public enum Status{
        COCAINE,
        BUFF_DEF
    }

    public enum Ability{
        STEALTH,
        BUFF_COCAINE,
        BUFF_GENTE_DE_BEM,
        ATRAPALHAR_O_TRANSITO
    }

    public boolean hasSummoningAction() {
        if(abilities.contains(Ability.BUFF_COCAINE)){
            return true;
        }
        if(abilities.contains(Ability.BUFF_GENTE_DE_BEM)){
            return true;
        }


            return false;

    }

    public void doSummoningAction(List<BattleField> creatureHolders) {
        //AECIO
        if(abilities.contains(Ability.BUFF_COCAINE)) {
            for (BattleField creatureHolder : creatureHolders) {
                if (creatureHolder.getCard() != null &&creatureHolder.getCard()!=this) {
                    creatureHolder.getCard().addStatus(Status.COCAINE);

                }

            }
        }

        //caveirao
        if(abilities.contains(Ability.BUFF_GENTE_DE_BEM)){
            for (BattleField creatureHolder : creatureHolders) {
                if (creatureHolder.getCard() != null &&
                        creatureHolder.getCard()!=this &&
                        creatureHolder.getCard().getFaction()==Faction.AZUL) {
                    creatureHolder.getCard().addStatus(Status.BUFF_DEF);

                }

            }
        }
    }

    public void doDeathAction() {
        //bloqueia um campo adversario

        if(abilities.contains(Ability.ATRAPALHAR_O_TRANSITO)) {
            atrapalharOtransitoAbility();
        }
    }

    private void atrapalharOtransitoAbility() {
        //TODO GET CHICO
        CreatureCard deadChico = (CreatureCard) CardBuilder.metaCards.get(0).getCopy();
        if (this.owner==PlayScreen.player) {
            for (BattleField creatureHolder : enemyCreatureHolders) {
                if (creatureHolder.getCard() == null) {
                    creatureHolder.setCard(deadChico);
                    deadChico.setxPos(creatureHolder.getXy().x - (SizePositionValues.CARD_SIZE_X / 2));
                    deadChico.setyPos(creatureHolder.getXy().y - (SizePositionValues.CARD_SIZE_Y / 2));
                    return;
                }

            }
        } else {
            for (int i = creatureHolders.size() - 1; i>=0 ;i--) {
                BattleField bt = creatureHolders.get(i);
                if (bt.getCard() == null) {
                    BoardPlace mirrorPlace = PlayerEventsExchanger.getMirrorBattleField(bt.getBoardPlace());
                    bt.setCard(deadChico);
                    deadChico.setxPos(bt.getXy().x - (SizePositionValues.CARD_SIZE_X / 2));
                    deadChico.setyPos(bt.getXy().y - (SizePositionValues.CARD_SIZE_Y / 2));
                    return;
                }

            }

        }
    }

    private void addStatus(Status status) {
        cardStatus.add(status);
        if(status==Status.COCAINE){
            this.attackMultiplier=1.5f;
            this.health = this.health/2;
            //mata quem tem 1 de vida
            if(this.health<=0){
                this.kill();
            }

        }
        if(status==Status.BUFF_DEF){
            this.health += 3;
        }
    }

    public void kill() {
        switch(getCurrentPlace()){
            case FIELD_1:
                PlayScreen.getCreatureHolders().get(0).setCard(null);
                break;
            case FIELD_2:
                PlayScreen.getCreatureHolders().get(1).setCard(null);
                break;
            case FIELD_3:
                PlayScreen.getCreatureHolders().get(2).setCard(null);
                break;
            case FIELD_4:
                PlayScreen.getCreatureHolders().get(3).setCard(null);
                break;
            case FIELD_5:
                PlayScreen.getCreatureHolders().get(4).setCard(null);
                break;
            case FIELD_6:
                PlayScreen.getCreatureHolders().get(5).setCard(null);
                break;
            case ENEMY_FIELD_1:
                enemyCreatureHolders.get(0).setCard(null);
                break;
            case ENEMY_FIELD_2:
                enemyCreatureHolders.get(1).setCard(null);
                break;
            case ENEMY_FIELD_3:
                enemyCreatureHolders.get(2).setCard(null);
                break;
            case ENEMY_FIELD_4:
                enemyCreatureHolders.get(3).setCard(null);
                break;
            case ENEMY_FIELD_5:
                enemyCreatureHolders.get(4).setCard(null);
                break;
            case ENEMY_FIELD_6:
                enemyCreatureHolders.get(5).setCard(null);
                break;
        }

        if(hasDeathAbility()) {
           // if(this.owner==PlayScreen.player) {
                doDeathAction();
            //}
        }
    }

    private boolean hasDeathAbility() {
        if(abilities.contains(Ability.ATRAPALHAR_O_TRANSITO)){
            return true;
        }
        return false;
    }


    public List<Ability> abilities;


    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public boolean isSick() {
        return sick;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public void damage(CreatureCard card) {
        this.health-= card.getAtkTotal();
        int currentHealth = card.getHealth();
        card.setHealth(currentHealth - this.getAtkTotal());
        //tira stealth
        if(this.targetable==false){
            this.targetable = true;
        }
    }

    public CreatureCard(String name, int attack, int defense, int photo, Faction faction, CardType cardType, Tribo org, Texture cardArt, int manaCost) {
        super(name,   photo, faction, cardType, org, cardArt, manaCost);
        this.attack = attack;
        this.defense = defense;
        this.health = defense;

        cardStatus = new ArrayList<Status>();
        targetable = true;
        abilities = new ArrayList<Ability>();
        attackBonus =0;
        attackMultiplier = 1;
    }

    @Override
    public CreatureCard getCopy() {
        CreatureCard c = new CreatureCard(this.name,this.attack,this.defense,this.photo,this.faction,this.cardType,this.org,this.cardArt,this.manaCost);
        for (Ability ability : abilities) {
            c.setAbilities(ability);
        }
        return c;
    }

    private void drawAtkAndDef(SpriteBatch batch) {
        if (this.getCardType() == CardType.CRIATURA) {
            int offset = 0;
            if(this.getAtkTotal()>=10){
                offset = -8;
            }

            batch.draw(PlayScreen.atkHolder, this.getxPos() - 10, this.getyPos() - 10);
            batch.draw(PlayScreen.atkHolder, this.getxPos() + SizePositionValues.CARD_SIZE_X - 18, this.getyPos() - 10);
            //TODO: MAYBE draw something different when card is sick
            if (isSick()) {
                PlayScreen.cardFont.draw(batch, this.getAtkTotal() + "", this.getxPos() - 2 + offset, this.getyPos() + 15);
            } else {
                PlayScreen.cardFont.draw(batch, this.getAtkTotal() + "", this.getxPos() - 2 + offset, this.getyPos() + 15);
            }
            PlayScreen.cardFont.draw(batch, this.getHealth() + "", this.getxPos() + SizePositionValues.CARD_SIZE_X - 8, this.getyPos() + 15);
        }
    }
    @Override
    public void drawCardWithMana(SpriteBatch batch) {
        drawCardWithoutMana(batch);
        //draw mana cost
        batch.draw(PlayScreen.manaCost, this.getxPos() + SizePositionValues.CARD_MANACOST_X, this.getyPos() + SizePositionValues.CARD_MANACOST_Y);
        PlayScreen.cardFont.draw(batch, this.getManaCost() + "", this.getxPos() + SizePositionValues.CARD_MANACOST_X + 5, this.getyPos() + SizePositionValues.CARD_MANACOST_Y + 25);
        PlayScreen.descFont.draw(batch, this.name, this.getxPos() + SizePositionValues.CARD_DESCRIPTION_X, this.getyPos() + SizePositionValues.CARD_DESCRIPTION_Y);
        drawAtkAndDef(batch);
    }


    @Override
    public void drawCardWithoutMana(SpriteBatch batch) {
        //System.out.println("drawCard" + this.getName());


        if (this.isSick()) {
            // Color color =  batch.getColor();
            batch.setColor(0.5f,1,0.5f,1f);
        } else {
            if (attackingAnimation > 0) {

                Color c = batch.getColor();
                attackingAnimation--;

                float gb = (float) 1 / attackingAnimation;
                batch.setColor(c.r, gb, (float) gb, 1f);
            }
        }


        if(!this.targetable){
            Color c =  batch.getColor();
            batch.setColor(c.r,c.g,c.b,0.4f);
        }


        batch.draw(PlayScreen.cardBg, this.getxPos(), this.getyPos(), SizePositionValues.CARD_SIZE_X, SizePositionValues.CARD_SIZE_Y);
        batch.draw(this.getCardArt(), this.getxPos() + SizePositionValues.THUMBNAIL_OFFSET_X, this.getyPos() + SizePositionValues.THUMBNAIL_OFFSET_Y, SizePositionValues.THUMBNAIL_SIZE_X, SizePositionValues.THUMBNAIL_SIZE_Y);
        //draw card description
        PlayScreen.descFont.draw(batch, this.name, this.getxPos() + SizePositionValues.CARD_DESCRIPTION_X, this.getyPos() + SizePositionValues.CARD_DESCRIPTION_Y);

        //draw abilities list
        int abilityCounter = 0;
        for (Ability ability : abilities) {
            abilityCounter++;

            PlayScreen.descFont.draw(batch, ability.toString().toLowerCase(), this.getxPos() + SizePositionValues.CARD_DESCRIPTION_X, this.getyPos() + SizePositionValues.CARD_DESCRIPTION_Y - (13 * abilityCounter));
        }

        drawAtkAndDef(batch);
        if(cardStatus.size()>0) {
            drawStatus(batch);
        }
        //return batch to default
        Color c =  batch.getColor();
        batch.setColor(1,1,1,1f);

    }
    //draw the current buffs the card have
    private void drawStatus(Batch batch) {

        for (Status status : cardStatus) {

            switch (status){
                case COCAINE:
                    batch.draw(PlayScreen.cocaine, this.getxPos() + SizePositionValues.CARD_STATUS_X , this.getyPos() + SizePositionValues.CARD_STATUS_Y);
                    break;
                case BUFF_DEF:
                    batch.draw(PlayScreen.cristo, this.getxPos() + SizePositionValues.CARD_STATUS_X - 15, this.getyPos() + SizePositionValues.CARD_STATUS_Y);
                    break;
            }

        }
    }

    public List<Ability> getAbilities(){
        return this.abilities;
    }

    public void setAbilities(Ability ability){
        switch (ability){
            case STEALTH:
                this.targetable = false;
                abilities.add(ability);
                break;
            case BUFF_COCAINE:
                abilities.add(ability);
                break;
            case BUFF_GENTE_DE_BEM:
                abilities.add(ability);
                break;
            default:
                abilities.add(ability);
                break;
        }

    }
    public boolean isTargetable(){
        return targetable;
    }

    public void setTargetable(boolean isTargetable){
        this.targetable= isTargetable;
    }


    public int getAtkTotal(){
        int total = (int)((attack + attackBonus) * attackMultiplier);
        return total;
    }
}
