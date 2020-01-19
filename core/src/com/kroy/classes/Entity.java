package com.kroy.classes;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import com.kroy.game.Constants;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import com.kroy.screens.PlayScreen;
import com.kroy.states.PlayState;

public class Entity extends Object{
    protected int hitpointCap, speedMove;
    MapGraph mapGraph;

    float speed = 5f;
    Coord previous;
    Queue<Coord> pathQueue = new Queue<>();
    public GraphPath<Coord> graphPath;


    public Entity(Vector3 position, Texture texture, float scale, short cb, short mb, short gindex) {
        super(
                position, Constants.world,
                BodyDef.BodyType.DynamicBody,
                new Vector2(texture.getWidth()*scale* Constants.PPM, texture.getHeight()*scale* Constants.PPM),
                cb, mb, gindex
        );
        setModel(texture);

    }

    public Entity(MapGraph mapGraph, Coord start, Texture texture, float scale, short cb, short mb, short gindex) {

        super(
                new Vector3(start.x, start.y, 0.0f),
                Constants.world,
                BodyDef.BodyType.DynamicBody,
                new Vector2(texture.getWidth()*scale* Constants.PPM, texture.getHeight()*scale* Constants.PPM),
                cb, mb, gindex
        );

        if(texture != null) {
            System.out.println("Texture not null");
            setModel(texture);
        }
        else
            System.out.println("BITCH WHERE THE FUCK ARE YOU");
        this.mapGraph = mapGraph;
        position = new Vector3(start.x, start.y, 0.0f);
        this.previous = start;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getSpeedMove() {
        return speedMove;
    }

    public void setSpeedMove(int speedMove) {
        this.speedMove = speedMove;
    }



    public void move(){};
    public void takeDmg(){};

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        super.render(batch);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0f, 0f, 1);
        shapeRenderer.circle(position.x, position.y, 5);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.circle(position.x, position.y, 5);
        shapeRenderer.end();
    }
    @Override
    public void update(float dt) {
        //position.add(position);
        checkCollision();
    }

    /**
     * Set the goal Coord, calculate a path, and start moving.
     */
    public void setGoal(Coord goal) {
        graphPath = mapGraph.findPath(previous, goal);
        for (int i = 1; i < graphPath.getCount(); i++) {
            pathQueue.addLast(graphPath.get(i));
        }
        setSpeedToNextCoord();
    }

    /**
     * Check whether Agent has reached the next Coord in its path.
     */
    private void checkCollision() {
        if (pathQueue.size > 0) {
            Coord target = pathQueue.first();
            if (Vector2.dst(position.x, position.y, target.x, target.y) < 5) {
                reachNextCoord();
            }
        }
    }

    /**
     * Agent has collided with the next City in its path.
     */
    private void reachNextCoord() {

        Coord next = pathQueue.first();

        // Set the position to keep the Agent in the middle of the path
        position.set(next.x,next.y,1);

        this.previous = next;
        pathQueue.removeFirst();

        if (pathQueue.size == 0) {
            reachDestination();
        } else {
            setSpeedToNextCoord();
        }
    }

    /**
     * Set xSpeed and ySpeed to move towards next Coord on path.
     */
    private void setSpeedToNextCoord() {
        Coord next = pathQueue.first();
        /*
                |                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                |               |                             |
            n.y |\              |               n.y - p.y     |   use cos(angle) * speed to get change in horizontal movement per frame
                | \             |    tan(0) = ------------    |
                |  \            |               n.x - p.x     |   use sin(angle) * speed to get change in vertical movement per frame
                |   \           |_ _ _ _ _ _ _ _ _ _ _ _ _ _ _|
            p.y |_ _0\ _ _ _ _ _ _ _ _ _
              n.x    p.x
         */
        float angle = MathUtils.atan2(next.y - previous.y, next.x - previous.x);
        position = new Vector3(MathUtils.cos(angle) * speed,MathUtils.sin(angle) * speed, 0.0f);
    }

    /**
     * Agent has reached the goal Coord.
     */
    private void reachDestination() {
        position = new Vector3(0.0f,0.0f,0.0f);
    }
}
