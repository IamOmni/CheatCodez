package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;


public class Object{
    private Sprite sprite;
    private Body body;
    public Object(Vector2 position){
        sprite = new Sprite();
        sprite.setPosition(position.x, position.y);
    }


    public void setTexture(Texture texture){
        this.sprite.setTexture(texture);
    }
    public void setBody(Body body){
        this.body = body;
    }
    public void setPosition(Vector2 position){
        sprite.setPosition(position.x, position.y);
    }
    public void setPosition(int x, int y){
        sprite.setPosition(x, y);
    }



    public Vector2 getPosition(){
        return new Vector2(sprite.getX(), sprite.getY());
    }
}
