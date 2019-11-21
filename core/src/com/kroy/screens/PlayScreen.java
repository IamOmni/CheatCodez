package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.Firetruck;
import com.kroy.classes.Object;
import com.kroy.game.kroyGame;
import com.kroy.scenes.Hud;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private kroyGame game;
    private OrthographicCamera cam;
    private Viewport gamePort;
    private ArrayList<Object> objs = new ArrayList<Object>();
    private Texture background;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(kroyGame game){
        this.game   = game;
        background  = new Texture("bg-menu.png");
        cam         = new OrthographicCamera();
        gamePort    = new FitViewport(game.WIDTH, game.HEIGHT, cam);
        hud         = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
       // map = mapLoader.load("[filename].tmx");
       // renderer = new OrthogonalTiledMapRenderer(map);

        objs.add(new Firetruck(50, 50));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        //draw all objects
        for(Object i : objs) {
              game.batch.draw(i.model, i.getX(), i.getY());
            //i.update(delta);
        }
        game.batch.end();
        //draw HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
