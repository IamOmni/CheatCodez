package com.kroy.classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.kroy.screens.PlayScreen;


public class Object{
   // public Texture sprite.getTexture();
    protected float scale;

    public Sprite sprite;
    protected Vector3 position;
    public int hitpoints = 0;
    public Body body;
    private float offsetX, offsetY;


    /**
     * Constructor for Object class
     */


    public Object(Vector3 position, World world, BodyDef.BodyType type, Vector2 size){
        sprite = new Sprite();
        final BodyDef bodyDef = new BodyDef();
        final PolygonShape shape = new PolygonShape();
        final FixtureDef fixtureDef = new FixtureDef();

        this.position = position;
        offsetX = size.x/2;
        offsetY = size.y/2;
        bodyDef.position.set(position.x+offsetX, position.y+offsetY);
        bodyDef.type = type;
        shape.setAsBox(offsetX, offsetY);
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
     * @param - Texture for the Object
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

    public void displayHealth(SpriteBatch sb){
        if(hitpoints > 0) {
            System.out.println("hitpoints: " + hitpoints);
            PlayScreen.font.setColor(Color.RED);
            PlayScreen.font.getData().scale(0.25f);

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
        if (this.getModel().toString().contains("Firetruck")) System.out.println(String.format("%f | %f | %s",  body.getPosition().x, body.getPosition().y, this.getModel().toString()));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));


        sb.draw(sprite.getTexture(),
                sprite.getX()-offsetX,sprite.getY()-offsetY,
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                scale,scale,
                sprite.getRotation(),
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                false,false);
    }


}
