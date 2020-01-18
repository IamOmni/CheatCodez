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
import com.kroy.game.Constants;
import com.kroy.screens.PlayScreen;


public class Object{
   // public Texture sprite.getTexture();
    protected float scale;
    public Vector3 position;
    public Sprite sprite;
    public int hitpoints = 0;
    public Body body;
    private float offsetX, offsetY;;



    /**
     * Constructor for Object class
     */


    public Object(Vector3 position, World world, BodyDef.BodyType type, Vector2 size, short cb, short mb, short gindex){
        sprite = new Sprite();

        final BodyDef bodyDef = new BodyDef();
        final PolygonShape shape = new PolygonShape();
        final FixtureDef fixtureDef = new FixtureDef();

        offsetX = (size.x/ Constants.PPM) /2;
        offsetY = (size.y/ Constants.PPM) /2;
        bodyDef.position.set(position.x/ Constants.PPM+offsetX, position.y/ Constants.PPM+offsetY);
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

    public void setRotation(float degree){
        body.setAngularVelocity(degree);
        sprite.setRotation(degree);
    }

    public void displayHealth(SpriteBatch sb){
        if(hitpoints > 0) {
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
        if(sprite.getTexture().toString().contains("Fire")) System.out.println(String.format("%d, %d", sprite.getRegionWidth(),sprite.getRegionHeight()));
        sprite.setRegionHeight(sprite.getTexture().getHeight());
        sprite.setRegionWidth(sprite.getTexture().getWidth());
        sprite.setPosition((body.getPosition().x-offsetX), (body.getPosition().y-offsetY));
////        sprite.setOrigin(body.getPosition().x , body.getPosition().y );
////        sprite.setCenter(body.getPosition().x, body.getPosition().y);
        sprite.setOrigin(sprite.getRegionWidth()/2, sprite.getRegionHeight()/2);

        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

        sb.draw(sprite.getTexture(),
                body.getPosition().x - sprite.getRegionWidth()/2,body.getPosition().y- sprite.getRegionHeight()/2,
                sprite.getRegionWidth()/2, sprite.getRegionHeight()/2,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                scale,scale,
                sprite.getRotation(),
                0,0,sprite.getTexture().getWidth(),sprite.getTexture().getHeight(),
                false,false);
    }


}
