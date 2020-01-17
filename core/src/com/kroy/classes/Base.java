package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

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
    public Base(int x, int y, int health, Texture texture, float rotation, float scale) {
        super(x, y, health, texture, rotation, scale);
        refillDelay= 5f;
        refillAmount=1;
    }

    public Base(int x, int y, int health, Texture texture, float rotation) {
        super(x, y, health, texture, rotation, 1);
        refillDelay= 5f;
        refillAmount=1;
    }

    public Base(int x, int y, int health, Texture texture) {
        super(x, y, health, texture, 0, 1);
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

    /**
     * Update methodd for the Building, initiates the bullet collision
     * @param bullets - Projectiles from the Firetrucks
     */
    public void update(ArrayList<Projectile> bullets, ArrayList<Firetruck> firetrucks){
        super.update(bullets);
        float dt = Gdx.graphics.getDeltaTime();

        refillDelay-=dt;
        if (refillDelay<0){
            for (Firetruck firetruck: firetrucks){
                float truckX = firetruck.getX();
                float truckY = firetruck.getY();
                float absDifX = MathsUtils.abs(truckX-super.getX());
                float absDifY = MathsUtils.abs(truckY-super.getY());
                float v = (float) Math.sqrt(absDifX * absDifX + absDifY * absDifY);

                if (v < 50) {
                    System.out.println("REFILLLLL");
                    refillDelay=5f;
                }

            }
        }

    }

}
