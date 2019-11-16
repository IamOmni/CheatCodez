package com.kroy.classes;

public class Firetruck extends Entity {
    private int waterVol, waterDmg, waterRange;
    private boolean active;

    public int getWaterVol() {
        return waterVol;
    }

    public void setWaterVol(int waterVol) {
        this.waterVol = waterVol;
    }

    public int getWaterDmg() {
        return waterDmg;
    }

    public void setWaterDmg(int waterDmg) {
        this.waterDmg = waterDmg;
    }

    public int getWaterRange() {
        return waterRange;
    }

    public void setWaterRange(int waterRange) {
        this.waterRange = waterRange;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void Control(){};
    public void CPUAttack(){};
    public void CPUDefent(){};
    public void fireWater(){};
    public void getWaterLevel(){};
    public void returnToBase(){};

}
