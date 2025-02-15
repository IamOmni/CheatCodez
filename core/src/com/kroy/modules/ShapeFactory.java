package com.kroy.modules;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.classes.CollisionBits;
import com.kroy.game.Constants;

public class ShapeFactory {
    private static int PPM;

    /**
     * Create rectangle in box2d
     * @param position - Vector of the position
     * @param size - Size of the rectanlge
     * @param type - type of body
     * @param world - world
     * @param density - desity of the object
     * @param sensor - sensor
     * @return body;
     */
    public static Body createRectangle(final Vector2 position, final Vector2 size, final BodyDef.BodyType type, final World world, float density, final boolean sensor) {
        PPM=4;
        // define body
        final BodyDef bdef = new BodyDef();
        bdef.position.set(position.x / Constants.PPM, position.y / Constants.PPM);
        bdef.type = type;
        final Body body = world.createBody(bdef);

        // define fixture
        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / Constants.PPM, size.y / Constants.PPM);
        final FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = density;
        fdef.isSensor = sensor;
        fdef.filter.categoryBits = CollisionBits.WALL;
        fdef.filter.maskBits = CollisionBits.WALL ;
        fdef.filter.groupIndex = 1;
        body.createFixture(fdef);
        shape.dispose();

        return body;
    }
}
