package com.kroy.classes;

import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;

        import javax.swing.*;

class Projectile extends Entity {

    //
    float x, y, degree,  rotationSpeed, acceleration, decelleration, maxSpeed, dx, dy, radians, v;
    double xSpeed, ySpeed;
    private Math MathsUtils;
    // LibGDX Attributes
    private Texture texture;

    public Sprite getSprite() {
        return sprite;
    }

    private Sprite sprite;

    /**
     *
     * @param x - int - X position of the projectile
     * @param y - int - Y positiong of the projectile
     */
    public Projectile(float x, float y, float degree, float v) {
        this.x = x;
        this.y = y;
        //this.speed = 5;
        this.decelleration = 0.9f;
        this.xSpeed=10;
        this.ySpeed=10;
        this.dx=5;
        this.dx=5;
        this.v = 0.5f;
        //System
        //System.out.format("V: %d", v);
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


        dx-=(dx/v) * decelleration*dt;
        dy-=(dy/v) * decelleration*dt;

        x+=dx;
        y+=dy;
        if (xSpeed<0.4) this.dispose();
        //System.out.println(dx);
        sprite.setPosition(x, y);


    }

    /**
     * Shoot the projectile
     */
    public void shoot() {
        texture = new Texture("bullet.png");
        sprite = new Sprite(this.texture);
        sprite.setPosition(this.x, this.y);
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
        texture.dispose();
        sprite.getTexture().dispose();
    }

    /**
     * Get x speed
     * @return xSpeed
     */
    public float getDx(){
        return dx;
    }



}
