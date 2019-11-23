package com.kroy.classes;

import com.badlogic.gdx.math.Vector3;

public class Entity extends Object{
    protected int hitpoints, speedMove;
    protected Vector3 movement_b;
    protected Vector3 movement;
    public Entity(){
    }
    @Override
    public void update(float dt){
        movement = movement_b;
        movement.scl(dt);
        position.add(movement);
    }

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

    public Vector3 getMovement() {
        return movement;
    }

    public void setMovement(Vector3 movement) {
        this.movement = movement;
    }

    public void move(){};
    public void takeDmg(){};
}
