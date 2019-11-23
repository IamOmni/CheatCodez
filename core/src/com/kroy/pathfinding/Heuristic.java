package com.kroy.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.kroy.classes.Coord;

public class Heuristic implements com.badlogic.gdx.ai.pfa.Heuristic<Coord> {
    @Override
    public float estimate(Coord from, Coord to) { float d = 0;
        if ((int)from.getX() == (int)to.getX()) d = Math.abs((int)to.getY() - (int)from.getY());
        else if ((int)from.getY() == (int)to.getY())  d = Math.abs((int)to.getX() - (int)from.getX());
        return d;
    }
}
