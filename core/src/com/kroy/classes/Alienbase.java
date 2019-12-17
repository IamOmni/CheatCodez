package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Alienbase extends Building{
    private int hitpoints;
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
