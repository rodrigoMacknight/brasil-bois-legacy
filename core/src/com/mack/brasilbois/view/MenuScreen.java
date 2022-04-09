package com.mack.brasilbois.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.mack.brasilbois.BrBoisMain;
import com.mack.brasilbois.enums.MenuValues;


public class MenuScreen implements Screen,InputProcessor {


    Texture bg;

    private Texture play;
    private Texture mountDeck;
    private Texture options;
    private Texture exit;
    int midX;
    int midY;
    private BrBoisMain game;



    public MenuScreen(BrBoisMain game) {
        this.game = game;
        bg = new Texture("MenuLayout/menubg.png");
        play = new Texture("MenuLayout/button_jogar.png");
        mountDeck= new Texture("MenuLayout/button_baralhos.png");
        exit = new Texture("MenuLayout/button_sair.png");
        options  = new Texture("MenuLayout/button_jogar.png");
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void show() {
        midX = Gdx.graphics.getWidth()/2;
        midY = Gdx.graphics.getHeight()/2;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.Companion.getBatch().begin();
        game.Companion.getBatch().draw(bg,0,0);
        game.Companion.getBatch().draw(play,midX - play.getWidth()/2,midY + 140);
        game.Companion.getBatch().draw(mountDeck,midX - mountDeck.getWidth()/2,midY + 60);
        game.Companion.getBatch().draw(options,midX - options.getWidth()/2,midY - 20);
        game.Companion.getBatch().draw(exit,midX - exit.getWidth()/2,midY - 100);

        game.Companion.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        play.dispose();
        mountDeck.dispose();
        options.dispose();
        exit.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int ipsolon = Gdx.graphics.getHeight() - screenY;
      //  System.out.println("x: "  + screenX );
        System.out.println("y: "  + ipsolon);
        checkButtonn(screenX, ipsolon);


        return false;
    }

    private void checkButtonn(int x, int y) {
        if( x>MenuValues.jogarXleft && x<MenuValues.jogarXright){
            if(y<MenuValues.jogarYup && y>MenuValues.jogarYdown){
                game.setScreen(new PlayScreen(game));
            }
        }
        if( x>MenuValues.baralhosXleft && x<MenuValues.baralhosXright){
            if(y<MenuValues.baralhosYup && y>MenuValues.baralhosYdown){
                //TODO montar deck
            }
        }
        if( x>MenuValues.optionsXleft && x<MenuValues.optionsXright){
            if(y<MenuValues.optionsYup && y>MenuValues.optionsYdown){
                //TODO options
            }
        }
        if( x>MenuValues.sairXleft && x<MenuValues.sairXright){
            if(y<MenuValues.sairYup && y>MenuValues.sairYdown){
                System.exit(0);
            }
        }

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
