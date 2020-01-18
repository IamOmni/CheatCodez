package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.Constants;

import java.util.ArrayList;

public class Base extends Building {
    private int dmg;
    private boolean destroyed;
    private int refillAmount;
    private float refillDelay;
    private Math MathsUtils;

    public int getRefillAmount() {
        return refillAmount;
    }

    public void setRefillAmount(int refillAmount) {
        this.refillAmount = refillAmount;
    }

    public float getRefillDelay() {
        return refillDelay;
    }

    public void getRefillDelay(int refillSpeed) {
        this.refillDelay = refillSpeed;
    }

    /**
     * Constructor class for Building
     *
     * @param x      - int x coordinate
     * @param y      - int y coordinate
     * @param health - health
     */
    public Base(int x, int y, int health, Texture texture, float rotation, float scale, World world) {
        super(x, y, health, texture, rotation, scale, world);
        refillDelay= 5f;
        refillAmount=1;
    }

    public Base(int x, int y, int health, Texture texture, float rotation, World world) {
        super(x, y, health, texture, rotation, 1, world);
        refillDelay= 5f;
        refillAmount=1;
    }

    public Base(int x, int y, int health, Texture texture, World world) {
        super(x, y, health, texture, 0, 1, world);
        refillDelay= 5f;
        refillAmount=1;
    }


    /**
     * @return int dmg - Returns damage done to base
     */
    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void updateModel(){};

    // Change to return int when implemented
    public void takeDmg(int dmgTaken){};

    public void destory(){
        this.destroyed=true;
    };


    public void update(ArrayList<Firetruck> firetrucks){
        float dt = Gdx.graphics.getDeltaTime();

        super.update(dt);

        refillDelay-=dt;
        if (refillDelay<0){
            for (Firetruck firetruck: firetrucks){
                Vector2 position1 = firetruck.body.getPosition();
                Vector2 position2 = body.getPosition();
                float v = position1.dst(position2)/ Constants.PPM;

                //System.out.println(String.format("V = %f", v));
                if (v < 10f) {
                    System.out.println("REFILLING");
                    firetruck.ammo+=5;
                    refillDelay=5f;
                }
            }
        }

    }



}
