package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;


public class Object{
   // public Texture sprite.getTexture();
    public Sprite sprite;
    protected Vector3 position;
    public float texRotation;
    public Body body;

    /**
     * Constructor for Object class
     */
    public Object() {
        sprite = new Sprite();
        position = new Vector3();
        position.x = 50;
        position.y = 50;

    }

    public Object(Vector3 position, World world, BodyDef.BodyType type, Vector2 size){
        sprite = new Sprite();
        final BodyDef bodyDef = new BodyDef();
        final PolygonShape shape = new PolygonShape();
        final FixtureDef fixtureDef = new FixtureDef();

        this.position = position;

        bodyDef.position.set(position.x, position.y);
        bodyDef.type = type;
        shape.setAsBox(size.x, size.y);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();

    }

    /**
     * Getter for Object sprite.getTexture()
     * @return sprite.getTexture()
     */
    public Texture getModel(){
        return sprite.getTexture();
    }

    /**
     * Update method for the Object class
     * @param dt
     */
    public void update(float dt){

    }

    /**
     * Set the sprite.getTexture() for the Object
     * @param sprite.getTexture() - Texture for the Object
     */
    public void setModel(Texture model) {
        this.sprite.setTexture(model);
    }

    /**
     * Getter for X attribute
     * @return float position.x;
     */
    public int getX(){ return (int) position.x; }

    /**
     * Getter for Y attribute
     * @return float positiion.y;
     */
    public int getY(){
        return (int) position.y;
    }

    public void rotate(float rotation){
        sprite.rotate(rotation);
    }

    /**
     * Render function for LibGDX
     * @param sb - SpriteBatch
     */
    public void render(SpriteBatch sb){
        sb.draw(sprite.getTexture(),
                position.x,position.y,
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                1,1,
                0f,
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                false,false);
    }

    public void render(SpriteBatch sb, float rotation, float scale){
        sb.draw(sprite.getTexture(),
                position.x,position.y,
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                scale,scale,
                0,
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                false,false);
    }


}
