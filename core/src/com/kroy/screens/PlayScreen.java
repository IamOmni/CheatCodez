package com.kroy.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kroy.game.kroyGame;

public class PlayScreen implements Screen {
    public static final float TILE_SIZE = 64;

    private kroyGame game;

    public PlayScreen(kroyGame game){
        this.game = game;
    }

    public void getObjects(TiledMap tiledMap, World world, String layer){
        MapObjects objects = tiledMap.getLayers().get(layer).getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();

            //create a dynamic within the world body (also can be KinematicBody or StaticBody
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            Body body = world.createBody(bodyDef);

            //create a fixture for each body from the shape
            float density = 1f;
            Fixture fixture = body.createFixture(getShapeFromRectangle(rectangle),density);
            fixture.setFriction(0.1F);

            //setting the position of the body's origin. In this case with zero rotation
            body.setTransform(getTransformedCenterForRectangle(rectangle),0);
        }
    }

    public static Vector2 getTransformedCenterForRectangle(Rectangle rectangle){
        Vector2 center = new Vector2();
        rectangle.getCenter(center);
        return center.scl(1/TILE_SIZE);
    }

    public static Shape getShapeFromRectangle(Rectangle rectangle){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(rectangle.width*0.5F/ TILE_SIZE,rectangle.height*0.5F/ TILE_SIZE);
        return polygonShape;
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
