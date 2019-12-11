package com.kroy.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

public class Agent {
    MapGraph mapGraph;

    float x;
    float y;

    float speed = 5f;
    float deltaX = 0;
    float deltaY = 0;

    Coord previous;
    Queue<Coord> pathQueue = new Queue<>();
    public GraphPath<Coord> graphPath;

    /**
     *
     * @param mapGraph
     * @param start start coord/position
     */
    public Agent(MapGraph mapGraph, Coord start) {
        this.mapGraph = mapGraph;
        this.x = start.x;
        this.y = start.y;
        this.previous = start;
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0f, 0f, 1);
        shapeRenderer.circle(x, y, 5);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.circle(x, y, 5);
        shapeRenderer.end();
    }

    public void step() {
        x += deltaX;
        y += deltaY;
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
            if (Vector2.dst(x, y, target.x, target.y) < 5) {
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
        this.x = next.x;
        this.y = next.y;

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
        deltaX = MathUtils.cos(angle) * speed;
        deltaY = MathUtils.sin(angle) * speed;
    }

    /**
     * Agent has reached the goal Coord.
     */
    private void reachDestination() {
        deltaX = 0;
        deltaY = 0;
    }
}
