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
    public float width, height, angle;

    // LibGDX Attributes
 //   private Texture texture;

    /**
     *
     * @param x - int - X position of the projectile
     * @param y - int - Y positiong of the projectile
     */
    public Projectile(float x, float y, Texture texture, float angle) {
        //Vector3 position, World world, BodyDef.BodyType type, Vector2 size

        super(new Vector3(x, y, 0), texture, 1f, CollisionBits.PROJECTILE, (short) (CollisionBits.BUILDING), (short) -1);
        scale = 1f;
        this.angle = angle;
        //body.setAngularVelocity(angle);
        body.setTransform(body.getWorldCenter(), angle);
        body.isBullet();
        body.isFixedRotation();
        this.angle = angle;

        Vector2 force = new Vector2(0, 5000);

        hitpoints = 100;

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


    public void render(SpriteBatch batch){
        super.render(batch);
    }

    public void update(float dt){
//        body.setTransform(
//                body.getPosition().add(body.getWorldVector(new Vector2(0, 20).scl(dt * Constants.PPM))),angle
//        );
        body.setTransform(body.getPosition(),angle);
        body.setLinearVelocity(body.getWorldVector(new Vector2(0, 2000000000).scl(dt * Constants.PPM)));
        hitpoints--;
        super.update(dt);
    }

}
