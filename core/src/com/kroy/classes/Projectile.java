package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.kroyGame;

import java.util.Vector;


public class Projectile extends Entity {
    private Math MathsUtils;
    float  degree,  rotationSpeed, acceleration, deceleration, maxSpeed, dx, dy, radians, v;
    double xSpeed, ySpeed;
    public float width, height;

    // LibGDX Attributes
 //   private Texture texture;

    /**
     *
     * @param v
     * @param x - int - X position of the projectile
     * @param y - int - Y positiong of the projectile
     */
    public Projectile(float v, float x, float y, float degree) {
        super(new Vector3(x, y, 0), kroyGame.manager.get("bullet.png", Texture.class), 1f);

        this.deceleration = 0.9f;
        this.xSpeed=10;
        this.ySpeed=10;
        this.dx=5;
        this.dx=5;
        this.v = 0.7f;
        width= 50;
        height = 20;

        this.degree = degree;
        radians =  degree*((float)Math.PI/180);
        dx = (float) MathsUtils.cos(radians)*(float)xSpeed;
        dy = (float) MathsUtils.sin(radians)*(float)ySpeed;

        this.shoot();
    }

    /**
     * Update the position of the projectile
     * @param dt - delta time for the update function
     */
    public void update(float dt) {
        xSpeed = MathsUtils.abs(xSpeed);
        ySpeed = MathsUtils.abs(ySpeed);
        dx-=(dx/v) * deceleration *dt;
        dy-=(dy/v) * deceleration *dt;
        position.x+=dx;
        position.y+=dy;
        if (xSpeed<0.4) this.dispose();
        //System.out.println(dx);
        sprite.setPosition(position.x, position.y);
    }

    public Sprite getSprite() {
        return sprite;
    }


    /**
     * Shoot the projectile
     */
    public void shoot() {
        sprite.setPosition(this.position.x, this.position.y);
        sprite.setSize(80, 40);
        sprite.setRotation(this.degree);
        sprite.setCenter(40, 20);
    }

    /**
     * Draw the projectile to the screen
     */
    public void draw(SpriteBatch batch){
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        batch.begin();
        sprite.draw(batch);
        batch.end();

    }

    /**
     * Dispose
     */
    public void dispose(){
    }

    /**
     * Get x speed
     * @return xSpeed
     */
    public float getDx(){
        return dx;
    }

}
