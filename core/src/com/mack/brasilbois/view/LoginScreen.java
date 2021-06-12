package com.mack.brasilbois.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginScreen extends ApplicationAdapter{

    Stage stage;

    @Override
    public void create(){
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);
    }



}
