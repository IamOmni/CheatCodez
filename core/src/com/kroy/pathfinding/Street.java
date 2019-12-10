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
        if ((int)from.x == (int)to.x) d = Math.abs((int)to.y - (int)from.y);
        else if ((int)from.y == (int)to.y)  d = Math.abs((int)to.x - (int)from.x);
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
        shapeRenderer.rectLine((int)from.x, (int)from.y, (int)to.x, (int)to.y, 20);
        shapeRenderer.end();
    }
}
