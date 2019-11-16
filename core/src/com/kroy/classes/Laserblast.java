package com.kroy.classes;

public class Laserblast extends Projectile {
    private int length;
    private Entity target;

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
