package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kroy.game.kroyGame;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;


import java.util.Random;
public class Firetruck extends Entity {
    private int waterVol, waterDmg, waterRange;
    private boolean active;

    public Firetruck(MapGraph mapGraph, Coord start){
        super(mapGraph, start);
        waterVol =  new Random().nextInt(20);
        model = ( new Texture("Firetruck_sprite_static.png"));
        setMovement(new Vector3(10,0,0)); // Change to new Vector3(position)?

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
    public void render(SpriteBatch sb){
        float rot = (movement.y != 0? 90.0f :0.0f);
        rot += (movement.y < 0? 180.0f : 0.0f);
        System.out.println(movement.x + ", " + movement.y);
        System.out.println(rot);
        sb.draw(model,
                position.x - model.getWidth(),position.y - model.getHeight(),
                0,0,model.getWidth(),model.getHeight(),
                4,4,
                rot,
                0,0,model.getWidth(),model.getHeight(),
                false,false);
    }

    @Override
    public void update(float dt){


        if (this.active) {
            // This method would be moved to a render function
            // These may need changing slightly
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                this.position.y += 1;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                this.position.y -= 1;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                this.position.x += 1;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                this.position.x -= 1;
            }


        }
        else{
            super.update(dt);
        }
    }




}
