package com.mack.brasilbois.enums;

import com.badlogic.gdx.Gdx;

public class SizePositionValues {


    public static final int CARD_SNAP_DISTANCE = 60;

    //texture sizes
    public static final int CARD_SIZE_X = 130;
    public static final int CARD_SIZE_Y = 182;

    //Friendly battlefields
    public static final int FIELD_CREATURE_ONE_X= 248;
    public static final int FIELD_CREATURE_ONE_Y = 226;

    public static final int FIELD_CREATURE_TWO_X= 403;
    public static final int FIELD_CREATURE_TWO_Y = 226;

    public static final int FIELD_CREATURE_THREE_X= 550;
    public static final int FIELD_CREATURE_THREE_Y = 226;

    public static final int FIELD_CREATURE_FOUR_X= 698;
    public static final int FIELD_CREATURE_FOUR_Y= 226;

    public static final int FIELD_CREATURE_FIVE_X= 846;
    public static final int FIELD_CREATURE_FIVE_Y = 226;

    public static final int FIELD_CREATURE_SIX_X= 996;
    public static final int FIELD_CREATURE_SIX_Y= 226;
    //Enemy battlefields
    public static final int FIELD_ENEMY_CREATURE_ONE_X= 248;
    public static final int FIELD_ENEMY_CREATURE_ONE_Y = 494;

    public static final int FIELD_ENEMY_CREATURE_TWO_X= 403;
    public static final int FIELD_ENEMY_CREATURE_TWO_Y = 494;

    public static final int FIELD_ENEMY_CREATURE_THREE_X= 550;
    public static final int FIELD_ENEMY_CREATURE_THREE_Y = 494;

    public static final int FIELD_ENEMY_CREATURE_FOUR_X= 698;
    public static final int FIELD_ENEMY_CREATURE_FOUR_Y= 494;

    public static final int FIELD_ENEMY_CREATURE_FIVE_X= 845;
    public static final int FIELD_ENEMY_CREATURE_FIVE_Y = 494;

    public static final int FIELD_ENEMY_CREATURE_SIX_X= 996;
    public static final int FIELD_ENEMY_CREATURE_SIX_Y= 494;


    public static final int PASS_TURN_LEFT_X= 1111;
    public static final int PASS_TURN_RIGHT_X= 1231;
    public static final int PASS_TURN_BOTTON_Y= 328;
    public static final int PASS_TURN_UPPER_Y= 384;



    //grimorio = deckPlace
    public static final int FRIENDLY_GRIMORIO_X = 1104;
    public static final int FRIENDLY_GRIMORIO_Y = 112;


    public static final int ENEMY_GRIMORIO_X = 15;
    public static final int ENEMY_GRIMORIO_Y = 426;

    //the place where we start to distribute the cards in the hand
    public static final int FRIENDLY_HAND_X= Gdx.graphics.getWidth()/2 + 150;
    public static final int FRIENDLY_HAND_Y = -90;

    public static final int ENEMY_HAND_X= Gdx.graphics.getWidth()/2 + 120;
    public static final int ENEMY_HAND_Y = Gdx.graphics.getHeight() - 30;


    public static final int PLAYER_MANA_X = 1060;
    public static final int PLAYER_MANA_Y = 60;

    public static final int ENEMY_MANA_X = 45;
    public static final int ENEMY_MANA_Y = 658;

    //distance between cards in hand
    public static final int HAND_OFFSET = 50;

    //size of the art inside the card
    public static final float THUMBNAIL_SIZE_X = 124;
    public static final float THUMBNAIL_SIZE_Y= 110;
    //to crop image in inkscape: object->clipping->set
    public static final float THUMBNAIL_OFFSET_X = 3;
    public static final float THUMBNAIL_OFFSET_Y = 69;

    public static final float PLAYER_HP_X = 108;
    public static final float PLAYER_HP_Y = 100;
    public static final float ENEMY_HP_X= 1090;
    public static final float ENEMY_HP_Y= 650;


    public static final float FRIENDLY_CARD_COUNTER_X = 1103;
    public static final float FRIENDLY_CARD_COUNTER_Y= 140;

    public static final float ENEMY_CARD_COUNTER_X = 18;
    public static final float ENEMY_CARD_COUNTER_Y = 450;

    public static float CARD_MANACOST_X = 112;
    public static float CARD_MANACOST_Y = 158;


    public static float CARD_DESCRIPTION_X = 7;
    public static float CARD_DESCRIPTION_Y= 61;

    public static float CARD_STATUS_X =54;
    public static float CARD_STATUS_Y =7;


    String palleton ="http://paletton.com/palette.php?uid=72P0u0kllllaFw0g0qFqFg0w0aF";
}
