package com.kroy.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Street implements Connection<Coord> {
    Coord from, to;
    float cost;
    public Street(Coord fromC, Coord toC) {
        this.from  = fromC;
        this.to    = toC;
    }
    @Override
    public float getCost() {
        return cost;
    }

    private float calculateCost(){
        float d = 0;
        if (from.x == to.x) d = Math.abs(to.y - from.y);
        else if (from.y == to.y)  d = Math.abs(to.x - from.x);
        return d;
    }
    @Override
    public Coord getFromNode() {
        return from;
    }

    @Override
    public Coord getToNode() {
        return to;
    }

    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rectLine(from.x, from.y, to.x, to.y, 20);
        shapeRenderer.end();
    }
}
