package com.kroy.classes;

public class Item extends Object {
    private int dmgBonus,rangeBonus,hpBonus,speedBonus;

    public int[] getBonuses(){
        return new int[]{dmgBonus,rangeBonus,hpBonus,speedBonus};
    };

    public int getDmgBonus() {
        return dmgBonus;
    }

    public int getRangeBonus() {
        return rangeBonus;
    }

    public int getHpBonus() {
        return hpBonus;
    }

    public int getSpeedBonus() {
        return speedBonus;
    }

    public void setDmgBonus(int dmgBonus) {
        this.dmgBonus = dmgBonus;
    }

    public void setRangeBonus(int rangeBonus) {
        this.rangeBonus = rangeBonus;
    }

    public void setHpBonus(int hpBonus) {
        this.hpBonus = hpBonus;
    }

    public void setSpeedBonus(int speedBonus) {
        this.speedBonus = speedBonus;
    }
}
