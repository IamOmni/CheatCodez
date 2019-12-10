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

    Coord previousCity;
    Queue<Coord> pathQueue = new Queue<>();
    public GraphPath<Coord> graphPath;

    public Agent(MapGraph mapGraph, Coord start) {
        this.mapGraph = mapGraph;
        this.x = start.x;
        this.y = start.y;
        this.previousCity = start;
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
     * Set the goal City, calculate a path, and start moving.
     */
    public void setGoal(Coord goal) {
        graphPath = mapGraph.findPath(previousCity, goal);
        for (int i = 1; i < graphPath.getCount(); i++) {
            pathQueue.addLast(graphPath.get(i));
        }
        setSpeedToNextCity();
    }

    /**
     * Check whether Agent has reached the next City in its path.
     */
    private void checkCollision() {
        if (pathQueue.size > 0) {
            Coord targetCity = pathQueue.first();
            if (Vector2.dst(x, y, targetCity.x, targetCity.y) < 5) {
                reachNextCity();
            }
        }
    }

    /**
     * Agent has collided with the next City in its path.
     */
    private void reachNextCity() {

        Coord nextCity = pathQueue.first();

        // Set the position to keep the Agent in the middle of the path
        this.x = nextCity.x;
        this.y = nextCity.y;

        this.previousCity = nextCity;
        pathQueue.removeFirst();

        if (pathQueue.size == 0) {
            reachDestination();
        } else {
            setSpeedToNextCity();
        }
    }

    /**
     * Set xSpeed and ySpeed to move towards next City on path.
     */
    private void setSpeedToNextCity() {
        Coord nextCity = pathQueue.first();
        float angle = MathUtils.atan2(nextCity.y - previousCity.y, nextCity.x - previousCity.x);
        deltaX = MathUtils.cos(angle) * speed;
        deltaY = MathUtils.sin(angle) * speed;
    }

    /**
     * Agent has reached the goal City.
     */
    private void reachDestination() {
        deltaX = 0;
        deltaY = 0;

        // Find a new goal City
        Coord newGoal;
        do {
            newGoal = mapGraph.coords.random();
        } while (newGoal == previousCity);

        setGoal(newGoal);
    }
}
