package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.kroyGame;

import javax.xml.soap.Text;

public class MainMenuScreen implements Screen {
    final kroyGame game;

    OrthographicCamera cam;
    private Texture plBtn = new Texture("Play_Button.png");
    public MainMenuScreen(kroyGame game) {
        this.game = game;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, kroyGame.WIDTH, kroyGame.HEIGHT);
    }


    @Override
    public void show() {

    }

    public void handleInput(){
        if(Gdx.input.isTouched()){
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }

    @Override
    public void render(float delta) {
        handleInput();
        game.batch.begin();
            game.batch.draw(plBtn, game.WIDTH/2 - plBtn.getWidth()/2, game.HEIGHT/2);
        game.batch.end();

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

    }
}
