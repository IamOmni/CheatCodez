package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.Button;
import com.kroy.game.kroyGame;

//import javax.xml.soap.Text;

public class MainMenuScreen implements Screen, InputProcessor {
    final kroyGame game;

    OrthographicCamera camera;
    private Viewport gamePort;

    private Button play, help, settings;

    private Sprite title;
    private Sprite background;
    float width, height;


    public MainMenuScreen(kroyGame game) {

        this.game = game;
        if(game.manager.isLoaded("Menu_Assets/TITLE.png", Texture.class)) {
            title = new Sprite(game.manager.get("Menu_Assets/TITLE.png", Texture.class));
            background = new Sprite(game.manager.get("Menu_Assets/BACKGROUND.png", Texture.class));
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, kroyGame.WIDTH, kroyGame.HEIGHT);
        width  = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        gamePort    = new StretchViewport(width, height, camera);
        gamePort.apply();
        camera.position.set(game.WIDTH /2, game.HEIGHT/2, 0);


        /**
         * Create buttons for menu use
         */
        play = new Button(0.4*width, 0.6*width, 0.5*height, 0.56*height, game.manager.get("Menu_Assets/PLAYBUTTON.png", Texture.class));

        double hTw_ratio = Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
        help = new Button(0.1 *width  * Gdx.graphics.getHeight()/Gdx.graphics.getWidth(), 0.2  *width * Gdx.graphics.getHeight()/Gdx.graphics.getWidth(), 0.1*height, 0.2*height, game.manager.get("Menu_Assets/HELP.png", Texture.class));

        settings = new Button(0.4*width, 0.6*width, 0.4*height, 0.46*height, game.manager.get("Menu_Assets/SETTINGS.png", Texture.class));

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

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // coords for ScreenX and Y start from top left not top right so this flips the height around to match the render coords

        if(play.hasBeenClicked(screenX, screenY, true))
        {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
        if(settings.hasBeenClicked(screenX, screenY, true)) {
            game.setScreen(new ScoresScreen(game));
            //System.out.println("Settings Button hit");
        }
        if(help.hasBeenClicked(screenX, screenY, true)){
            //System.out.println("Help Button hit");
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
