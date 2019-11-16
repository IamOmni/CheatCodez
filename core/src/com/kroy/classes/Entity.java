package com.kroy.classes;

public class Entity extends Object{
    private int hitpoints, speedMove;
    private int[] movement;

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getSpeedMove() {
        return speedMove;
    }

    public void setSpeedMove(int speedMove) {
        this.speedMove = speedMove;
    }

    public int[] getMovement() {
        return movement;
    }

    public void setMovement(int[] movement) {
        this.movement = movement;
    }

    public void move(){};
    public void takeDmg(){};
}
