package com.kroy.classes;

public class Laserblast extends projectile {
    private int length;
    private Entity target;

    /**
     * @param x      - int - X position of the projectile
     * @param y      - int - Y positiong of the projectile
     * @param degree
     * @param v
     */
    public Laserblast(float x, float y, float degree, float v) {
        super(x, y, degree, v);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }


}
