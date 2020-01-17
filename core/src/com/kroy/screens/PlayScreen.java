package com.kroy.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.*;

import com.kroy.classes.Object;
import com.kroy.modules.ShapeFactory;
import com.kroy.pathfinding.Agent;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;

import com.kroy.game.kroyGame;

public class PlayScreen implements Screen {
    public static final float TILE_SIZE = 64;
    private World world;

    private kroyGame game;

    public PlayScreen(kroyGame game){
        this.game = game;
    }



    public void getObjects(TiledMap tiledMap){
        MapObjects objects = tiledMap.getLayers().get("WALLS").getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            ShapeFactory.createRectangle(
                    new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2), // position
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2), // size
                    BodyDef.BodyType.StaticBody, world, 1f, false);        }
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
