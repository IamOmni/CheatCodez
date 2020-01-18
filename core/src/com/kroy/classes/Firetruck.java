package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.kroy.game.Constants;
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
    public int ufid;
    public int mDriveDirection = DRIVE_DIRECTION_NONE;
    public int mTurnDirection = TURN_DIRECTION_NONE;

    /**
     * Constructor for Firetruck object
     * @param mapGraph - MapGraph object for traversal
     * @param start - Starting coord for the traversal
     */
    public Firetruck(MapGraph mapGraph, Coord start, int ufid, AssetManager manager){
        super(mapGraph, start, manager.get("Firetruck.png", Texture.class), 0.15f, CollisionBits.FIRETRUCK, (short) (CollisionBits.WALL), (short) 1);
        this.ufid = ufid;
        waterCap =  new Random().nextInt(20) + 5;
        waterVol = waterCap;
        hitpointCap =  new Random().nextInt(20) + 5;
        hitpoints = hitpointCap;
        this.scale = 0.15f;
        status = new StatusButton(ufid, manager);
        model = manager.get("Firetruck.png", Texture.class);

        status = new StatusButton(ufid, manager);

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

    public int getAmmo(){
        return ammo;
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

        if (!baseVector.isZero()){

            body.applyForceToCenter(body.getWorldVector(baseVector.scl(80000)), true);
        }

        firedelay-=dt;

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
     * Function to shoot a Projectile
     */
    public void fire_water(){

    }

    public int getX() { return (int) this.x; }
    public int getY() { return (int) this.y; }
    public int getHeight() {return (int) this.height;};


    public float getFiredelay() {
        return firedelay;
    }

    public void setFiredelay(float firedelay) {
        this.firedelay = firedelay;
    }

    public void render(SpriteBatch batch){

        update(1f);
        super.render(batch);
    }

    public Projectile createProjectile(){
        ammo -= 1;
        return new Projectile(body.getPosition().x, body.getPosition().y, kroyGame.manager.get("bullet.png"), body.getAngle());
    }

    @Override
    public void displayHealth(SpriteBatch sb){
        if(hitpoints > 0) {
            PlayScreen.font.setColor(Color.RED);
            PlayScreen.font.getData().scale(0.25f);

            String text = String.format("%d HP / %d WP", hitpoints, ammo);
            final GlyphLayout layout = new GlyphLayout(PlayScreen.font, text);
            PlayScreen.font.draw(sb, text, body.getPosition().x - layout.width/2, body.getPosition().y + 1 * getOffsets().y + PlayScreen.font.getLineHeight());
        }

    }

}
