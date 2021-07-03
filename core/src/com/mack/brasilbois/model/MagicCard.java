package com.mack.brasilbois.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mack.brasilbois.enums.CardNames;
import com.mack.brasilbois.enums.SizePositionValues;
import com.mack.brasilbois.view.PlayScreen;

public class MagicCard extends Card {

    public enum MagicType {
        ATACK, //ENEMY CREATURES, HP
        BUFF, //FRIENDLY CREATURES
        HEAL //FRIENDLY CREATURES, HERO
    }

    public MagicType type;






    public MagicCard(String name, int photo, Faction faction, Card.CardType magic, Texture cardArt, int manaCost, MagicType type) {
        super(name, photo, faction, magic, null, cardArt, manaCost);
        this.type = type;


    }

    @Override
    public void drawCardWithMana(SpriteBatch batch) {
        drawCardWithoutMana(batch);
        //draw mana cost
        batch.draw(PlayScreen.manaCost, this.getxPos() + SizePositionValues.CARD_MANACOST_X, this.getyPos() + SizePositionValues.CARD_MANACOST_Y);
        PlayScreen.cardFont.draw(batch, this.getManaCost() + "", this.getxPos() + SizePositionValues.CARD_MANACOST_X + 5, this.getyPos() + SizePositionValues.CARD_MANACOST_Y + 25);
        PlayScreen.descFont.draw(batch, this.name, this.getxPos() + SizePositionValues.CARD_DESCRIPTION_X, this.getyPos() + SizePositionValues.CARD_DESCRIPTION_Y);
    }


    @Override
    public Card getCopy() {

        MagicCard c = new MagicCard(this.name,this.photo,this.faction,this.cardType, this.cardArt, this.manaCost, this.type);

        return c;
    }

    public void doMagicTrick(CreatureCard creatureCard) {
        switch (name) {
            case CardNames.BONE_DA_CUT:
                creatureCard.setHealth(creatureCard.getHealth() + 4);

        }
    }
}
