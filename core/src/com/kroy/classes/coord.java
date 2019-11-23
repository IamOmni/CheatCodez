package com.kroy.classes;

import java.util.Arrays;

public class Coord extends Pair {
    public String name;
    public String[] connections;

    public Coord(Integer x, Integer y, String name, String[] connections) {
        super(x, y);
        this.name = name;
        this.connections = connections;
    }

    @Override
    public String toString() { return this.name + " | " + super.getX() + ","+super.getY() + " | " + Arrays.toString(this.connections);  }

}
