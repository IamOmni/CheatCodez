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

    /**
     * Constructor for Firetruck object
     * @param mapGraph - MapGraph object for traversal
     * @param start - Starting coord for the traversal
     */
    public Firetruck(MapGraph mapGraph, Coord start, int ufid, AssetManager manager){
        super(mapGraph, start, manager.get("Firetruck.png", Texture.class));
        waterCap =  new Random().nextInt(20);
        waterVol = waterCap;
        hitpointCap =  new Random().nextInt(20);
        hitpoints = hitpointCap;

        status = new StatusButton(ufid, manager);
        model = manager.get("Firetruck.png", Texture.class);
        setMovement(new Vector3(60,60,0)); // Change to new Vector3(position)?
        position.x = 60;
        position.y = 60;

        status = new StatusButton(ufid, manager);
        setMovement(new Vector3(10,0,0)); // Change to new Vector3(position)?



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

        if(left) {
            rotation += 1f;
            rotate(rotation);
        }
        if(right)
        {
            rotation -= 1f;
            rotate(-rotation);
        }
        if(up)
        {
            position.y += (Math.cos(Math.toRadians(rotation)) * 5);
            position.x -= (Math.sin(Math.toRadians(rotation)) * 5);
        }
        if(down)
        {
            position.y -= (Math.cos(Math.toRadians(rotation)) * 5);
            position.x += (Math.sin(Math.toRadians(rotation)) * 5);
        }
    }
    public void oldUpdate(float dt){
      //  System.out.println("Firetruck update function called!");
        dt = .01f;
        if (degree > 360 ) degree = 0;
        if (degree < 0) degree= 360;


        if (left) {  degree += rotationSpeed * dt; }
        if (right) { degree -= rotationSpeed * dt; }

        if (up) {
            xSpeed+=acceleration * dt;
            ySpeed+=acceleration * dt;
        }

        if (down){
            ySpeed-=decelleration*dt*10;
            xSpeed-=decelleration*dt*10;
        }

        if (up==false && down==false){
            if ((ySpeed-decelleration*dt*3)>0) ySpeed-=decelleration*dt*3;
            if ((xSpeed-decelleration*dt*3)>0) xSpeed-=decelleration*dt*3;
        }


        xSpeed = Math.abs(xSpeed);
        ySpeed = Math.abs(ySpeed);

        radians =  degree*((float)Math.PI/180);
        dx = (float) Math.cos(radians)*(float)xSpeed*dt;
        dy = (float) Math.sin(radians)*(float)ySpeed*dt;
        float v = (float) Math.sqrt(dx*dx + dy*dy);


       // System.out.println(String.format("X:%f | Y:%f | DX:%f | DY:%f | V:%f", x, y, dx, dy, v));
        if (v>0.003){
            dx-=(dx/v) * decelleration*dt;
            dy-=(dy/v) * decelleration*dt;
            x+=dx;
            y+=dy;
        } else {
            x=x;
            y=y;
        }

        if (v>maxSpeed){
            dx = (dx/v)*maxSpeed;
            dy = (dy/v)*maxSpeed;
            x+=dx;
            y+=dy;
        }

        resetDirections();

        position.set(x, y,0);
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
    public void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            System.out.println("W key is pressed");
            setUp(true);
            setLeft(false);
            setRight(false);
            setDown(false);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            System.out.println("D key is pressed");
            setUp(false);
            setLeft(false);
            setRight(true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            System.out.println("A key is pressed");
            setUp(false);
            setLeft(true);
            setRight(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            System.out.println("S key is pressed");
            setUp(false);
            setLeft(false);
            setRight(false);
            setDown(true);
        }
        else {
            setUp(false); setLeft(false); setRight(false); setDown(false);
        }
    }

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
        super.render(batch, this.degree, 0.5f);


//    public void render(SpriteBatch sb){
//        super.render(sb, rotation, 1);
  }



}
