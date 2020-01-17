package com.kroy.classes;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;
import com.kroy.classes.Object;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;

public class Traversable extends Entity {
    MapGraph mapGraph;

    float speed = 5f;
    Vector2 movement;
    Coord previous;
    Queue<Coord> pathQueue = new Queue<>();
    public GraphPath<Coord> graphPath;

    /**
     *
     * @param mapGraph
     * @param start start coord/position
     */
    public Traversable(MapGraph mapGraph, Coord start) {
        super(new Vector2(start.x, start.y));

        this.mapGraph = mapGraph;
        setPosition(new Vector2(start.x, start.y));
        this.previous = start;
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0f, 0f, 1);
        shapeRenderer.circle(getPosition().x, getPosition().y, 5);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.circle(getPosition().x, getPosition().y, 5);
        shapeRenderer.end();
    }

    public void step() {
        getPosition().set(getPosition().add(movement));

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
            if (Vector2.dst(getPosition().x, getPosition().y, target.x, target.y) < 5) {
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
        setPosition(next.x,next.y);

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
        movement = new Vector2(MathUtils.cos(angle) * speed,MathUtils.sin(angle) * speed);
    }

    /**
     * Agent has reached the goal Coord.
     */
    private void reachDestination() {
        movement = new Vector2(0.0f,0.0f);
    }
}