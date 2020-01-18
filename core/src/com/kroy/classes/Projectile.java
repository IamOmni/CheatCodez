package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class Projectile extends Entity {
    private Math MathsUtils;
    float  dx;
    public float width, height;

    // LibGDX Attributes
 //   private Texture texture;

    /**
     *
     * @param v
     * @param x - int - X position of the projectile
     * @param y - int - Y positiong of the projectile
     */
    public Projectile(float x, float y, Texture texture, float angle) {
        //Vector3 position, World world, BodyDef.BodyType type, Vector2 size

        super(new Vector3(x, y, 0), texture, 1f, CollisionBits.PROJECTILE, (short) (CollisionBits.BUILDING), (short) 0);
        scale = 1f;
        //body.setAngularVelocity(angle);
        body.setTransform(body.getWorldCenter(), angle);
        Vector2 force = new Vector2(0, 500000000);
        body.applyForceToCenter(body.getWorldVector(force.scl(500000)), true);
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

    public void render(SpriteBatch batch){
        super.render(batch);
    }

}
