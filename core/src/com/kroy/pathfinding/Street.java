package com.kroy.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.classes.Coord;

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
        if ((int)from.getX() == (int)to.getX()) d = Math.abs((int)to.getY() - (int)from.getY());
        else if ((int)from.getY() == (int)to.getY())  d = Math.abs((int)to.getX() - (int)from.getX());
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
        System.out.println("Draw line from " + to.name + " to " + from.name);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rectLine((int)from.getX(), (int)from.getY(), (int)to.getX(), (int)to.getY(), 40);
        shapeRenderer.end();
        System.out.println("Drawn line from " + to.name + " to " + from.name);
    }
}
