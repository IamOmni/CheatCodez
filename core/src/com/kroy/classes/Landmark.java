package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Landmark extends Building{
    private String name;
    private int level;
    private int spawnTime;

    /**
     * Constructor class for Building
     *
     * @param x      - int x coordinate
     * @param y      - int y coordinate
     * @param health - health
     */
    public Landmark(int x, int y, int health, Texture texture, float rotation, float scale, World world) {
        super(x, y, health, texture, rotation, scale, world);
        spawnTime=500;
    }

    public Landmark(int x, int y, int health, Texture texture, float rotation, World world) {
        super(x, y, health, texture, rotation, 1,world);
    }

    public Landmark(int x, int y, int health, Texture texture, World world) {
        super(x, y, health, texture, 0, 1,world);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void upgrade(){};

    public int getSpawnTime() { return this.spawnTime; };
}
