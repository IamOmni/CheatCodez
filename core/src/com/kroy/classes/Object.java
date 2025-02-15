package com.kroy.classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.kroy.game.Constants;
import com.kroy.screens.PlayScreen;


public class Object{
    protected float scale;
    public Vector3 position;
    public Sprite sprite;
    private int hitpoints = 0;
    public Body body;
    private float offsetX, offsetY;;
    private boolean immortal;


    /**
     * Constructor for Object class
     */

    public Vector2 getOffsets(){
        return new Vector2(offsetX, offsetY);
    }


    /**
     * Constructor for the Object class
     * @param position - Position of the object
     * @param world - Box2D world
     * @param type - Type of the body
     * @param size - Size of the object
     * @param cb - Collision bits (what is the object for box2d reference)
     * @param mb - Mask bits (what can the object collide with)
     * @param gindex - Can the object collide/not collide with the mask
     */
    public Object(Vector3 position, World world, BodyDef.BodyType type, Vector2 size, short cb, short mb, short gindex){
        sprite = new Sprite();

        final BodyDef bodyDef = new BodyDef();
        final PolygonShape shape = new PolygonShape();
        final FixtureDef fixtureDef = new FixtureDef();

        offsetX = (size.x/ Constants.PPM) /2;
        offsetY = (size.y/ Constants.PPM) /2;

        bodyDef.position.set(position.x*Constants.PPM/ Constants.PPM+offsetX, position.y*Constants.PPM/ Constants.PPM+offsetY);
        bodyDef.type = type;
        shape.setAsBox(offsetX, offsetY);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.filter.categoryBits = cb;
        fixtureDef.filter.maskBits = mb;
        fixtureDef.filter.groupIndex = gindex;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();

        immortal = false;

    }


    /**
     * Update method for the Object class
     * @param dt
     */
    public void update(float dt){

    }
    public void setHitpoints(int hitpoints){
        this.hitpoints = hitpoints;
    }
    public int getHitpoints(){
        return hitpoints;
    }
    public void kill(){
        if(!immortal)
            hitpoints = 0;
    }

    public void takeDamage(float dmg){
        this.hitpoints -= dmg;
    }

    /**
     * Set the sprite.getTexture() for the Object
     * @param - Texture for the Object
     */
    public void setModel(Texture model) {
        this.sprite.setTexture(model);
    }

    public void setRotation(float degree){
        body.setAngularVelocity(degree);
        sprite.setRotation(degree);
    }

    /**
     * Display health of the object
     * @param sb - Game sprite batch
     */
    public void displayHealth(SpriteBatch sb){

        if(!immortal) {
            PlayScreen.font.setColor(Color.RED);

            String text = String.format("%d HP", hitpoints);
            final GlyphLayout layout = new GlyphLayout(PlayScreen.font, text);
            PlayScreen.font.draw(sb, text, body.getPosition().x - layout.width/2, body.getPosition().y + 1 * offsetY + PlayScreen.font.getLineHeight());

        }
    }

    /**
     * Render function for LibGDX
     * @param sb - SpriteBatch
     */
    public void render(SpriteBatch sb){
        sprite.setRegionHeight(sprite.getTexture().getHeight());
        sprite.setRegionWidth(sprite.getTexture().getWidth());
        sprite.setPosition((body.getPosition().x-offsetX), (body.getPosition().y-offsetY));
        sprite.setOrigin(sprite.getRegionWidth()/2, sprite.getRegionHeight()/2);

        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.setX(body.getPosition().x - sprite.getRegionWidth()/2);
        sprite.setY(body.getPosition().y- sprite.getRegionHeight()/2);
        sb.draw(sprite.getTexture(),
                body.getPosition().x - sprite.getRegionWidth()/2,body.getPosition().y- sprite.getRegionHeight()/2,
                sprite.getRegionWidth()/2, sprite.getRegionHeight()/2,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                scale,scale,
                sprite.getRotation(),
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                false,false);
    }


    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }
}
