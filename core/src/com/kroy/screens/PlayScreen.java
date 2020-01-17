package com.kroy.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.*;

import com.kroy.modules.ShapeFactory;
import com.kroy.pathfinding.Agent;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;

import com.kroy.game.kroyGame;

import java.lang.Object;

public class PlayScreen implements Screen {
    public static final float TILE_SIZE = 64;
    private World world;
    private TiledMap map;
    private Box2DDebugRenderer boxRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;



    private kroyGame game;
    private static InputProcessor inputProcessor;
    public PlayScreen(kroyGame game){
        this.game = game;
        world = new World(new Vector2(0, 0), true);
        boxRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.zoom = 2f;
        viewport = new FitViewport(kroyGame.WIDTH / kroyGame.PPM, kroyGame.HEIGHT / kroyGame.PPM, camera);
        camera.position.set(viewport.getWorldWidth() /2, viewport.getWorldHeight()/2, 0);
        loadTiledMap();
    }

    public void loadTiledMap(){
        map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        loadObjects();
    }
    public void loadObjects(){
        MapObjects objects = map.getLayers().get("WALLS").getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            System.out.println(rectangle.getX());
            System.out.println(rectangle.getY());
            ShapeFactory.createRectangle(
                    new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2), // position
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2), // size
                    BodyDef.BodyType.StaticBody, world, 1f, false);
        }

    }


    public void InputHandler(){
        if(inputProcessor.keyDown('A')){

        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        handleInput();
        camera.update();
        viewport.update(kroyGame.WIDTH / kroyGame.PPM, kroyGame.HEIGHT / kroyGame.PPM);
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(camera.combined);
        boxRenderer.render(world, camera.combined);

        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
        System.out.println("Resize");
        viewport.update(width/kroyGame.PPM, height/kroyGame.PPM);
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

    public void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.zoom -= 0.01;
            System.out.println(camera.zoom);
        }
    }
}
