package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Alienbase extends Building{
    private int hitpoints;

    /**
     * Constructor class for Building
     *
     * @param x      - int x coordinate
     * @param y      - int y coordinate
     * @param health - health
     */
    public Alienbase(int x, int y, int health, Texture texture) {
        super(x, y, health, texture);
    }

    public void convertToBase(Landmark loc){
        position = loc.position;
        hitpoints = new Random().nextInt(20) + 20;
        setModel(new Texture("AlienBase.png"));
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }
}
