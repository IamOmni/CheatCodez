package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.kroy.game.mainKroyGame;
import java.util.concurrent.ThreadLocalRandom;

public class Firetruck extends Entity {
    private int waterVol, waterDmg, waterRange;
    private boolean active;
    public Firetruck(int x, int y){
        position = new Vector3(x,y,0);
        waterVol = ThreadLocalRandom.current().nextInt(5, 12 + 1);
        model = new Texture("Firetruck.png");
        setMovement(new Vector3(1,0,0));
    }
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

    @Override
    public void update(float dt){
        position.add(movement);
        if(position.x < 0 || position.x > (mainKroyGame.WIDTH -  model.getWidth())){
            setMovement(new Vector3(-10,0,0));
            position.add(movement);
            System.out.println("at boundary");
        }
    }

}
