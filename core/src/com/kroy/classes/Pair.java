package com.kroy.classes;

public class Pair<A, B> {
    private A X;
    private B Y;

    public Pair(A X, B Y) {
        super();
        this.X = X;
        this.Y = Y;
    }

    public String toString()
    {
        return "(" + X + ", " + Y + ")";
    }

    public A getX() {
        return this.X;
    }

    public void setX(A X) {
        this.X = X;
    }

    public B getY() {
        return this.Y;
    }

    public void setY(B second) {
        this.Y = Y;
    }
}
