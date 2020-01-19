package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.Constants;

import java.util.ArrayList;

public class Base extends Building {
    private int refillAmount;
    private float refillDelay;

    /**
     * Constructor class for Building
     *
     * @param x      - int x coordinate
     * @param y      - int y coordinate
     * @param health - health
     */
    public Base(int x, int y, int health, Texture texture, float rotation, float scale, World world) {
        super(x, y, health, texture, rotation, scale, world);
        refillDelay= 10f;
        refillAmount=1;
    }

    public void update(ArrayList<Firetruck> firetrucks){
      //  super.update(bullets);
        float dt = Gdx.graphics.getDeltaTime();

        refillDelay-=dt;
        if (refillDelay<0){
            for (Firetruck firetruck: firetrucks){
                Vector2 position1 = firetruck.body.getPosition();
                Vector2 position2 = body.getPosition();
                float v = (float) Math.sqrt(position1.dst(position2));

                //System.out.println(String.format("V = %f", v));
                if (v < Constants.REFILL_RADIUS) {
                    firetruck.refillAmmo(2);
                    firetruck.setHitpoints(firetruck.getHitpoints()+1);
                    refillDelay=2f;
                }
            }
        }

    }

}
