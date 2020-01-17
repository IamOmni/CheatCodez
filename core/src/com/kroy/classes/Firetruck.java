package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.kroy.game.kroyGame;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import com.kroy.screens.PlayScreen;


import java.util.ArrayList;
import java.util.Random;
public class Firetruck extends Entity {
    private int waterVol, waterCap, waterDmg, waterRange;
    public StatusButton status;
    private boolean active;

    // Our attributes
    float x,y, degree,  rotationSpeed, rotation, acceleration, decelleration, maxSpeed, dx, dy, radians, firedelay, v;
    double xSpeed, ySpeed;
    private boolean left, right, up, down;
    int height, width, health, ammo;
    private ArrayList<Projectile> bullets;
    private Texture model;
    public static final int DRIVE_DIRECTION_NONE = 0;
    public static final int DRIVE_DIRECTION_FORWARD = 1;
    public static final int DRIVE_DIRECTION_BACKWARD = 2;

    public static final int TURN_DIRECTION_NONE = 0;
    public static final int TURN_DIRECTION_LEFT = 1;
    public static final int TURN_DIRECTION_RIGHT = 2;

    public int mDriveDirection = DRIVE_DIRECTION_NONE;
    public int mTurnDirection = TURN_DIRECTION_NONE;

    /**
     * Constructor for Firetruck object
     * @param mapGraph - MapGraph object for traversal
     * @param start - Starting coord for the traversal
     */
    public Firetruck(MapGraph mapGraph, Coord start, int ufid, AssetManager manager){
        super(mapGraph, start, manager.get("Firetruck.png", Texture.class), 0.2f);
        waterCap =  new Random().nextInt(20);
        waterVol = waterCap;
        hitpointCap =  new Random().nextInt(20);
        hitpoints = hitpointCap;
        this.scale = 0.2f;
        status = new StatusButton(ufid, manager);
        model = manager.get("Firetruck.png", Texture.class);

        status = new StatusButton(ufid, manager);
        //s(new Vector3(10,0,0)); // Change to new Vector3(position)?



        // FROM PLAYER
        // Init vars
        down=false;
        up=false;
        right=false;
        left=false;
        rotationSpeed = 120;
        acceleration = 200f;
        decelleration = 30f;
        maxSpeed = 200;
        height=50;
        width=100;
        rotation = 0.0f;
        health=500;
        degree = 0;
        xSpeed = 0;
        ySpeed = 0;


        bullets = new ArrayList<Projectile>();
        firedelay = 0f;
        ammo=50;

    }

    public int getWaterVol() {
        return waterVol;
    }

    public void setWaterVol(int waterVol) {
        this.waterVol = waterVol;
    }

    public int getWaterDmg() {
        return waterDmg;
    }

    public void setWaterDmg(int waterDmg) {
        this.waterDmg = waterDmg;
    }

    public int getWaterRange() {
        return waterRange;
    }

    public void setWaterRange(int waterRange) {
        this.waterRange = waterRange;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void Control(){};
    public void CPUAttack(){};
    public void CPUDefent(){};
    public void fireWater(){};
    public void getWaterLevel(){};
    public void returnToBase(){};

    /**
     *
     *
     */
    public void refill(){

    }

    /**
     * Fetches all bullets from specific firetruck
     * @return bullets - ArrayList<Projectile>
     */
    public ArrayList<Projectile> getBullets() {
        return bullets;
    }

    /**
     * Helper funciton for collision detection
     * @return int - v
     */
    public float getV() { return v; }


    /**
     * Helper function
     * @return float - Degree, the degree in which the player is facing
     */
    public float getDegree() { return degree; }

    /**
     * Get current health of the player
     * @return int - health
     */
    public int getHealth(){ return health; }


    /**
     * Updater method for class player
     * @param dt - Delta time
     */
    public void update(float dt){
        Vector2 baseVector = new Vector2();

        if(mTurnDirection == TURN_DIRECTION_RIGHT){
            body.setAngularVelocity(-2.0f);
        }
        else if(mTurnDirection == TURN_DIRECTION_LEFT){
            body.setAngularVelocity(2.0f);
        }
        else{
            body.setAngularVelocity(0.0f);
        }

        if(mDriveDirection == DRIVE_DIRECTION_FORWARD) {
            baseVector.set(0, 120f);
        } else if (mDriveDirection== DRIVE_DIRECTION_BACKWARD){
            baseVector.set(0,-120f);
        }

        if (!baseVector.isZero()){System.out.print("UPDATING");
            body.applyForceToCenter(baseVector, true);
            body.applyForce(baseVector, new Vector2(100,0), true);
            body.applyAngularImpulse(100f, true);
            body.setLinearVelocity(body.getWorldVector(baseVector).scl(4));
        }

    }


    /**
     * Method to reset directions of the player
     */
    private void resetDirections(){
        setUp(false);
        setRight(false);
        setLeft(false);
        setDown(false);
    }

    /**
     * Set left attribute
     * @param a
     */
    public void setLeft(boolean a){ left=a; };
    /**
     * Set right attribute
     * @param a
     */
    public void setRight(boolean a){ right=a; };
    /**
     * Set down attribute
     * @param a
     */
    public void setDown(boolean a){ down=a; };
    /**
     * Set up attribute
     * @param a
     */
    public void setUp(boolean a){ up=a; };

    /**
     * Handle input function for setting direction
     */





    /**
     * Function to shoot a Projectile
     */
    public void fire_water(){
        if (bullets.size()>20){
            if (bullets.get(0).getDx()<5){
                ArrayList<Projectile> temp = new ArrayList<Projectile>();
                bullets.get(0).dispose();
                for (int i=0; i<bullets.size()-1;i++){
                    temp.add(bullets.get(i+1));
                }
                bullets=temp;
            }
        }
        if (firedelay<0 && ammo>0){
            if (bullets.size()<5){
                Projectile p = new Projectile(x, this.x, this.y, degree);
                bullets.add(p);
                ammo-=1;
            }
            firedelay=0.3f;
        }
    }

    /**
     * Collision detection for the
     * @param o
     * @return
     */
    public boolean hasCollided(Projectile o){
        if (x < o.getX() + o.width && y < o.getY()  + o.height && x + width > o.getX()  && y + height > o.getY()) {
            o.getSprite().setColor(1, 0, 0, 0f);
            //System.out.format("%d : %d | %d : %d | %d : %d \n ", (int) o.x, (int) o.y, x, y, x+64, y+64);
            System.out.format("%d \n", health);
            return true;
        }
        return false;
    }

    public int getX() { return (int) this.x; }
    public int getY() { return (int) this.y; }
    public int getHeight() {return (int) this.height;};


    public void render(SpriteBatch batch){
        update(1f);
        super.render(batch);
//    public void render(SpriteBatch sb){
//        super.render(sb, rotation, 1);
    }



}
