package com.kroy.classes;

public class Pair<A, B> {
    private A X;
    private B Y;

    /**
     * Constructor for Pair class
     * @param X - Of Type A
     * @param Y - Of Type B
     */
    public Pair(A X, B Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Getter for X attribute
     * @return X attribute of type A
     */
    public A getX() {
        return this.X;
    }

    /**
     * Setter for X attribute
     * @param X
     */
    public void setX(A X) {
        this.X = X;
    }

    /**
     * Getter for Y attribute
     * @return Y attribute of type B
     */
    public B getY() {
        return this.Y;
    }

    /**
     * Setter for Y attribute
     * @returns Y of typr B
     */
    public void setY(B second) {
        this.Y = Y;
    }
}
