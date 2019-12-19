package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kroy.game.kroyGame;

import java.util.ArrayList;

public class ScoresScreen implements Screen, InputProcessor {
    private kroyGame game;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Sprite background;

    private int width, height;
    private int ratioW, ratioH;

    private BitmapFont font;

    public ScoresScreen(kroyGame game){
        this.game   = game;
        background  = new Sprite(new Texture("Menu_Assets/BACKGROUND.png"));
        width  = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        ratioW = Gdx.graphics.getWidth()/game.WIDTH;
        ratioH = Gdx.graphics.getHeight()/game.HEIGHT;

        camera = new OrthographicCamera(width,height);
        viewport     = new FitViewport(width, height, camera);
        viewport.apply();
        camera.position.set(game.WIDTH/2 , game.HEIGHT/2, 0);
        font = new BitmapFont();
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.begin();
        game.batch.draw(background, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        game.batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setColor(Color.WHITE);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(new Color(0.75f, 0.75f, 0.75f, 0.5f));
        game.shapeRenderer.rect(width/6, height/12, 2 * width/3, 2 * height/3);
        game.shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    @Override
    public void resize(int width, int height) {

        this.height = height;
        this.width  = width;
        ratioW = width/game.WIDTH;
        ratioH = height/game.HEIGHT;


        viewport.update(width, height);
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
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE)
            {
            dispose();
            game.setScreen(new MainMenuScreen(game));

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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
