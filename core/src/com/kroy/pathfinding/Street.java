package com.kroy.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
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
}
