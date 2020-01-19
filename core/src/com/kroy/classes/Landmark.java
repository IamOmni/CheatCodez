package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.Constants;
import com.kroy.game.kroyGame;

import java.util.ArrayList;

public class Landmark extends Building{
    public boolean shoot;
    private String name;
    private int level;
    private int spawnTime;
    protected ArrayList<Projectile> misiles;
    protected float misiledelay;
    public boolean invaded;
    /**
     * Constructor class for Building
     *
     * @param x      - int x coordinate
     * @param y      - int y coordinate
     * @param health - health
     */
    public Landmark(int x, int y, int health, Texture texture, float rotation, float scale, World world) {
        super(x, y, health, texture, rotation, scale, world);
        misiles = new ArrayList<>();
        misiledelay=4f;
        spawnTime=500;
        shoot=false;
        invaded = false;
        body.setUserData(this);
    }

    public float getMisiledelay(){return misiledelay;};


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void upgrade(){};

    public int getSpawnTime() { return this.spawnTime; };

    public void setMisiledelay(float v) { this.misiledelay=v; }


}
