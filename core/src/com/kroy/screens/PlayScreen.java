package com.kroy.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.kroy.modules.GraphLoader;
import com.kroy.modules.ShapeFactory;

import com.kroy.game.kroyGame;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayScreen implements Screen {
    public static final float TILE_SIZE = 64;
    private World world;
    MapGraph mapGraph;

    private kroyGame game;
    // Create Map to store all the coords
    Map<String, Coord> Coords = new HashMap<>();
    private static InputProcessor inputProcessor;


    public PlayScreen(kroyGame game){
        this.game = game;

        mapGraph = new MapGraph();
        world = new World(new Vector2(0,0), true);

        try {
            GraphLoader.loadGraph(Coords, mapGraph);
            System.out.println("Graph loaded successfully");
        }
        catch (IOException e){
            System.out.println("Error reading file..." + e.toString());
        }
    }




    public void getObjects(TiledMap tiledMap){
        MapObjects objects = tiledMap.getLayers().get("WALLS").getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
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
    public void render(float delta) {

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
