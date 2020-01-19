package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.Constants;

public class Building extends Object {

    private boolean hit;
    private float height, width;

    /**
     *
     * @param x
     * @param y
     * @param health
     * @param texture
     * @param rotation
     * @param scale
     * @param world
     */
    public Building(int x, int y, int health, Texture texture, float rotation, float scale, World world) {
        super(new Vector3(x,
                y,0), world,
                BodyDef.BodyType.StaticBody, new Vector2(
                        texture.getWidth()*scale*Constants.PPM,
                        texture.getHeight()*scale*Constants.PPM),
                CollisionBits.BUILDING, (short) (CollisionBits.BUILDING | CollisionBits.PROJECTILE), (short) 2);

        setModel(texture);
        setHitpoints(health);

        height = texture.getHeight();
        if(health<=0)
            setImmortal(true);
        width = texture.getWidth();
        sprite.setPosition(body.getPosition().x/Constants.PPM*scale,body.getPosition().y/Constants.PPM*scale);
        this.scale = scale;
        setRotation(rotation);
    }



    /**
     * Get X coordinate of the Building
     * @return float x
     */
    public int getX(){return (int) body.getPosition().x;};

    /**
     * Get Y coordinate of the Building
     * @return float y
     */
    public int getY(){return (int) body.getPosition().y;};

    public void render(SpriteBatch sp){
        super.render(sp);
    }

}
