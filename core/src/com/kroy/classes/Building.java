package com.kroy.classes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Building extends Object {
    private float scale;
    // Own variables
    private int health;
    private boolean hit;
    private ArrayList<Alien> aliens;
    private float height, width, rotation;
    // LibGDX Attributes
    private Sprite sprite;
    private Texture texture;
    private Math MathsUtils;


    /**
     * Constructor class for Building
     * @param x - int x coordinate
     * @param y  - int y coordinate
     * @param health - health
     */
    public Building(int x, int y, int health, Texture texture, float rotation){
        this.texture = texture;
        setModel(texture);
        height = 64f;
        width = 64f;
        this.rotation = rotation;
        scale=1;
        position.set(new Vector3(x,y,0));
        this.health = health;
    }

    public Building(int x, int y, int health, Texture texture) {
        this.texture = texture;
        setModel(texture);
        height = 64f;
        width = 64f;
        this.rotation = rotation;
        position.set(new Vector3(x,y,0));
        scale=1;
        this.health = health;
    }

    public Building(int x, int y, int health, Texture texture, float rotation, float scale) {
        this.texture = texture;
        setModel(texture);
        height = texture.getHeight();
        width = texture.getWidth();
        this.scale = scale;
        this.rotation = rotation;
        position.set(new Vector3(x,y,0));
        this.health = health;
    }



    /**
     * Get X coordinate of the Building
     * @return float x
     */
    public int getX(){return (int) position.x;};

    /**
     * Get Y coordinate of the Building
     * @return float y
     */
    public int getY(){return (int) position.y;};


    /**
     * Collision function for Building and Projectiles
     * @param o - Projectile that is being tested
     * @return boolean for whether there is a collision or not
     */
    public boolean hasCollided(Projectile o){
        float x = position.x;
        float y = position.y;
        if (x < o.getX() + o.width && y < o.getY()  + o.height && x + width > o.getX()  && y + height > o.getY()) {
            o.getSprite().setColor(1, 0, 0, 0f);
            System.out.format("%d : %d | %d : %d | %d : %d \n ", (int) o.getX(), (int) o.getY(), x, y, x+64, y+64);
            return true;
        }
        return false;
    }


    /**
     * Update methodd for the Building, initiates the bullet collision
     * @param bullets - Projectiles from the Firetrucks
     */
    public void update(ArrayList<Projectile> bullets){
        for (Projectile bullet: bullets){
            hit = hasCollided(bullet);
            if (hit) health-=10;
        }
    }

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
        health-=dmg_taken;
    }

    public void render(SpriteBatch sp){
        super.render(sp, rotation, scale);
        hit = false;
    }

}
