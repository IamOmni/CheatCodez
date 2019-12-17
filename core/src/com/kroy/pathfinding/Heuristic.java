package com.kroy.pathfinding;

public class Heuristic implements com.badlogic.gdx.ai.pfa.Heuristic<Coord> {
    @Override
    public float estimate(Coord from, Coord to) { float d = 0;
        if ((int)from.x == (int)to.x) d = Math.abs((int)to.y - (int)from.y);
        else if ((int)from.y == (int)to.y)  d = Math.abs((int)to.x - (int)from.x);
        return d;
    }
}
