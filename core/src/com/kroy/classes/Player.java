package com.kroy.classes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;


public class Player {
    // Our attributes
    float x,y, degree,  rotationSpeed, acceleration, decelleration, maxSpeed, dx, dy, radians, firedelay, v;
    double xSpeed, ySpeed;
    private boolean left, right, up, down;
    int height, width, health;
    private ArrayList<Projectile> bullets;

    // LibGDX Attributes
    private Sprite sprite;
    private Texture texture;
    private Math MathsUtils;
    private Rectangle r;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    /**
     * Contructor for player class
     */
    public Player(){
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
        health=500;
        batch = new SpriteBatch();
        texture = new Texture("firetruck.png");
        sprite = new Sprite(this.texture);
        sprite.setPosition(200, 200);
        sprite.setSize(width, height);
        sprite.setRotation(0);
        sprite.setCenter(25, 50);
        degree = 0;
        xSpeed = 0;
        ySpeed = 0;
        x = 100; // Starting pos
        y = 100; // Starting pos
        bullets = new ArrayList<Projectile>();
        firedelay = 0f;
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


    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Updater method for class player
     * @param dt - Delta time
     */

    public void update(float dt){
        handleInput();
        dt = .01f;
        if (degree > 360 ) degree = 0;
        if (degree < 0) degree= 360;


        if (left) { degree += rotationSpeed * dt; }
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

        xSpeed = MathsUtils.abs(xSpeed);
        ySpeed = MathsUtils.abs(ySpeed);

        radians =  degree*((float)Math.PI/180);
        dx = (float) MathsUtils.cos(radians)*(float)xSpeed*dt;
        dy = (float) MathsUtils.sin(radians)*(float)ySpeed*dt;
        float v = (float) Math.sqrt(dx*dx + dy*dy);


        System.out.println(String.format("X:%f | Y:%f | DX:%f | DY:%f | V:%f", x, y, dx, dy, v));
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
        sprite.setPosition(x, y);
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
     * Draw method for LibGDX
     */
    public void draw(){
        sprite.setRotation(degree);
        batch.begin();
        sprite.draw(batch);
        batch.end();
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
            setUp(true);
            setLeft(false);
            setRight(false);
            setDown(false);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            setUp(false);
            setLeft(false);
            setRight(true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            setUp(false);
            setLeft(true);
            setRight(false);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
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
        if (bullets.size()>4){
            if (bullets.get(0).getDx()<5){
                ArrayList<Projectile> temp = new ArrayList<Projectile>();
                bullets.get(0).dispose();
                for (int i=0; i<bullets.size()-1;i++){
                    temp.add(bullets.get(i+1));
                }
                bullets=temp;
            }
        }
        if (firedelay<0){
            if (bullets.size()<5){
                Projectile p = new Projectile(x, this.x, this.y, degree);
                bullets.add(p);
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

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public int getX() { return (int) this.x; }
    public int getY() { return (int) this.y; }
    public int getHeight() {return (int) this.height;};
    private void Control(){};
    private void CPU_defend(){};
    private void CPU_attack(){};
    private void return_to_base(){};

//    public void render(SpriteBatch sb){
//        float rot = (movement.y != 0? 90.0f :0.0f);
//        rot += (movement.y < 0? 180.0f : 0.0f);
//        System.out.println(movement.x + ", " + movement.y);
//        System.out.println(rot);
//        sb.draw(model,
//                position.x - model.getWidth(),position.y - model.getHeight(),
//                0,0,model.getWidth(),model.getHeight(),
//                4,4,
//                rot,
//                0,0,model.getWidth(),model.getHeight(),
//                false,false);
//    }
//
//    @Override
//    public void update(float dt){
//        System.out.println(hitpoints + ", " + hitpointCap);
//        status.update((float)hitpoints/(float)hitpointCap, (float)waterVol/(float)waterCap);
//
//        if (this.active) {
//            // This method would be moved to a render function
//            // These may need changing slightly
//            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                this.position.y += 1;
//            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                this.position.y -= 1;
//            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                this.position.x += 1;
//            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                this.position.x -= 1;
//            }
//
//
//        }
//        else{
//            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                if(hitpoints > 0)
//                    hitpoints -= 1;
//            }
//            super.update(dt);
//        }
//    }
}