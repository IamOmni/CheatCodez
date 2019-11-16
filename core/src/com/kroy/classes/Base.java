package com.kroy.classes;

public class Base extends Building {
    private int dmg;
    private boolean destroyed;

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void updateModel(){};

    // Change to return int when implemented
    public void takeDmg(int dmgTaken){};

    public void destory(){
        this.destroyed=true;
    };
}
