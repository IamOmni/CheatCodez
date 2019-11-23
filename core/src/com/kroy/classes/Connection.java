package com.kroy.classes;
import java.lang.Math.*;
public class Connection {
    public Coord from, to;
    public int cost;

    public Connection(Coord from, Coord to) {
        this.from = from;
        this.to = to;
        this.cost = calculateCost();
    }

    private int calculateCost(){

        int d = 0;
        if ((int)from.getX() == (int)to.getX()) d = Math.abs((int)to.getY() - (int)from.getY());
        else if ((int)from.getY() == (int)to.getY())  d = Math.abs((int)to.getX() - (int)from.getX());
        return d;
    }
}
