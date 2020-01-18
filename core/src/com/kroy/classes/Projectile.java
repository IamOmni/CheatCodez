package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kroy.game.Constants;


public class Projectile extends Entity {
    private Math MathsUtils;
    float  dx;
    public float width, height, angle, lifetimer;

    // LibGDX Attributes
 //   private Texture texture;

    /**
     *
     * @param x - int - X position of the projectile
     * @param y - int - Y positiong of the projectile
     */
    public Projectile(float x, float y, Texture texture, float angle) {
        //Vector3 position, World world, BodyDef.BodyType type, Vector2 size

        super(new Vector3(x, y, 0), texture, 1f, CollisionBits.PROJECTILE, (short) (CollisionBits.BUILDING), (short) 1);
        scale = 1f;
        lifetimer = 80;
        //body.setAngularVelocity(angle);
        body.setTransform(body.getWorldCenter(), angle);
        body.isBullet();
        body.isFixedRotation();
        this.angle = angle;

        Vector2 force = new Vector2(0, 5000);
        //body.setLinearVelocity(force.scl(2222222));

        //body.applyForceToCenter(body.getWorldVector(force.scl(500000)), true);
        System.out.println(texture.getHeight());
        System.out.println("PROJECTILE CREATED");

    }

    public Sprite getSprite() {
        return sprite;
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

    @Override
    public void update(float dt){

        System.out.println(String.format("%d", hitpoints));
        body.setTransform(
                body.getPosition().add(body.getWorldVector(new Vector2(0, 20)).scl(dt*Constants.PPM)), angle
        );
        super.update(dt);
        lifetimer-=1;
    }
    public void render(SpriteBatch batch){
        super.render(batch);
    }

}
