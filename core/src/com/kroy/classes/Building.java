package com.kroy.classes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.Constants;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Building extends Object {

    private boolean hit;
    private float height, width;


    /**
     * Constructor class for Building
     * @param x - int x coordinate
     * @param y  - int y coordinate
     * @param health - health
     */



    public Building(int x, int y, int health, Texture texture, float rotation, float scale, World world) {
        super(new Vector3(x,
                y,0), world,
                BodyDef.BodyType.StaticBody, new Vector2(
                        texture.getWidth()*scale*Constants.PPM,
                        texture.getHeight()*scale*Constants.PPM),
                CollisionBits.BUILDING, (short) (CollisionBits.BUILDING | CollisionBits.PROJECTILE), (short) 1);

        setModel(texture);
        height = texture.getHeight();
        hitpoints = health;
        width = texture.getWidth();
        sprite.setPosition(body.getPosition().x/Constants.PPM*scale,body.getPosition().y/Constants.PPM*scale);

        this.scale = scale;
        setRotation(rotation);
    }



    /**
     * Get X coordinate of the Building
     * @return float x
     */
    public int getX(){return (int) body.getPosition().x;};

    /**
     * Get Y coordinate of the Building
     * @return float y
     */
    public int getY(){return (int) body.getPosition().y;};


    /**
     * Draw method for the Building
     */
//    public void draw(){
//        sprite.draw(batch);
//
//        hit = false;
//    }

    /**
     * Helper method for taking damage
     * @param dmg_taken
     */
    private void take_dmg(int dmg_taken){
        hitpoints-=dmg_taken;
    }

    public void render(SpriteBatch sp){
        super.render(sp);
        hit = false;
    }

}