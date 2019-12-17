package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.Button;
import com.kroy.game.kroyGame;

//import javax.xml.soap.Text;

public class MainMenuScreen implements Screen, InputProcessor {
    final kroyGame game;

    OrthographicCamera camera;
    private Viewport gamePort;

    private Button play, help, settings;

    private Sprite title        = new Sprite(new Texture("Menu_Assets/TITLE.png"));
    private Sprite background   = new Sprite(new Texture("Menu_Assets/BACKGROUND.png"));


    public MainMenuScreen(kroyGame game) {

        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, kroyGame.WIDTH, kroyGame.HEIGHT);
        float width, height;
        width  = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        gamePort    = new FitViewport(width, height, camera);
        gamePort.apply();
        camera.position.set(game.WIDTH /2, game.HEIGHT/2, 0);


        /**
         * Create buttons for menu use
         */
        play = new Button(0.43, 0.57, 0.35, 0.45, "Menu_Assets/PLAYBUTTON.png");

        double hTw_ratio = Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
        help = new Button(0.1 * Gdx.graphics.getHeight()/Gdx.graphics.getWidth(), 0.2  * Gdx.graphics.getHeight()/Gdx.graphics.getWidth(), 0.1, 0.2, "Menu_Assets/HELP.png");

        settings = new Button(0.4, 0.6, 0.2, 0.3, "Menu_Assets/SETTINGS.png");

        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
            game.batch.draw(background, 0, 0, gamePort.getScreenWidth(), gamePort.getScreenHeight());
            game.batch.draw(title, gamePort.getScreenWidth()/2 - title.getWidth()/5, 2 * gamePort.getScreenHeight()/3,   title.getWidth() / 2, title.getHeight()/2);
        game.batch.end();

        play.render(game.batch);
        settings.render(game.batch);
        help.render(game.batch);
    }

    /**
     * function runs on resize of window
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        play.updateRatio(Gdx.graphics.getWidth()/kroyGame.WIDTH, Gdx.graphics.getHeight()/kroyGame.HEIGHT);
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

        title.getTexture().dispose();
        settings.getSprite().getTexture().dispose();
        play.getSprite().getTexture().dispose();
        help.getSprite().getTexture().dispose();
        background.getTexture().dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // coords for ScreenX and Y start from top left not top right so this flips the height around to match the render coords
        screenY = Gdx.graphics.getHeight() - screenY;
        if(play.hasBeenClicked(screenX, screenY))
        {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
        if(settings.hasBeenClicked(screenX, screenY)) {
            game.setScreen(new ScoresScreen(game));
            System.out.println("Settings Button hit");
        }
        if(help.hasBeenClicked(screenX, screenY)){
            System.out.println("Help Button hit");
        }
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE){
            dispose();
            Gdx.app.exit();
        }
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
    public boolean scrolled(int amount) {
        return false;
    }
}
