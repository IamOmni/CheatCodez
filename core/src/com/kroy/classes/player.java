package com.kroy.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
public class player {
    // Our attributes
    float x,y, degree,  rotationSpeed, acceleration, decelleration, maxSpeed, dx, dy, radians;
    double xSpeed, ySpeed;
    private boolean left, right, up, down;
    // LibGDX Attributes
    private Sprite sprite;
    private Texture texture;
    private Math MathsUtils;
    private Rectangle r;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    public player(){
        // Init vars
        down=false;
        up=false;
        right=false;
        left=false;
        rotationSpeed = 50;
        acceleration = 40f;
        decelleration = 10f;
        maxSpeed = 400;
        batch = new SpriteBatch();
        texture = new Texture("firetruck.png");
        sprite = new Sprite(this.texture);
        sprite.setPosition(200, 200);
        sprite.setSize(100, 50);
        sprite.setRotation(0);
        sprite.setCenter(25, 50);
        //shapeRenderer = new ShapeRenderer(); Removed
        degree = 0;
        xSpeed = 0;
        ySpeed = 0;
        x = 100; // Starting pos
        y = 100; // Starting pos
        //r = new Rectangle(x , y, 100, 50);
    }
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
    private void resetDirections(){
        setUp(false);
        setRight(false);
        setLeft(false);
        setDown(false);
    }
    public void draw(){
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        sprite.setRotation(degree);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    public void setLeft(boolean a){ left=a; };
    public void setRight(boolean a){ right=a; };
    public void setDown(boolean a){ down=a; };
    public void setUp(boolean a){ up=a; };
    public void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            setUp(true);
            setLeft(false);
            setRight(false);
            setDown(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            setUp(false);
            setLeft(false);
            setRight(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            setUp(false);
            setLeft(true);
            setRight(false);
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            setUp(false);
            setLeft(false);
            setRight(false);
            setDown(true);
        } else {
            setUp(false); setLeft(false); setRight(false); setDown(false);
        }
    }
}