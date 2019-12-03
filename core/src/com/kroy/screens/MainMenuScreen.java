package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.kroyGame;

//import javax.xml.soap.Text;

public class MainMenuScreen implements Screen {
    final kroyGame game;

    OrthographicCamera camera;
    private Viewport gamePort;
    private Sprite plBtn        = new Sprite(new Texture("Menu_Assets/PLAYBUTTON.png"));
    private Sprite title        = new Sprite(new Texture("Menu_Assets/TITLE.png"));
    private Sprite settingsBtn  = new Sprite(new Texture("Menu_Assets/SETTINGS.png"));
    private Sprite helpBtn      = new Sprite(new Texture("Menu_Assets/HELP.png"));
    private Sprite background   = new Sprite(new Texture("Menu_Assets/BACKGROUND.png"));


    public MainMenuScreen(kroyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, kroyGame.WIDTH, kroyGame.HEIGHT);

        gamePort    = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        gamePort.apply();
        camera.position.set(game.WIDTH /2, game.HEIGHT/2, 0);
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
            game.batch.draw(background, 0, 0, gamePort.getScreenWidth(), gamePort.getScreenHeight());
            game.batch.draw(title, gamePort.getScreenWidth()/2 - title.getWidth()/5, 2 * gamePort.getScreenHeight()/3,   title.getWidth() / 2, title.getHeight()/2);

            game.batch.draw(plBtn, gamePort.getScreenWidth()/2 - plBtn.getWidth()/5, gamePort.getScreenHeight()/3,   2* plBtn.getWidth() / 5, 2* plBtn.getHeight()/5);
            game.batch.draw(settingsBtn, gamePort.getScreenWidth()/2 - settingsBtn.getWidth()/5, gamePort.getScreenHeight()/6,   2* settingsBtn.getWidth() / 5, 2* settingsBtn.getHeight()/5);
            game.batch.draw(helpBtn, gamePort.getScreenWidth()/14, gamePort.getScreenHeight()/20, helpBtn.getWidth()/2,helpBtn.getHeight()/2);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        camera.update();
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
