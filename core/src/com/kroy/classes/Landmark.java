package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;

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
    public Landmark(int x, int y, int health, Texture texture, float rotation, float scale) {
        super(x, y, health, texture, rotation, scale);
        spawnTime=500;
    }

    public Landmark(int x, int y, int health, Texture texture, float rotation) {
        super(x, y, health, texture, rotation, 1);
    }

    public Landmark(int x, int y, int health, Texture texture) {
        super(x, y, health, texture, 0, 1);
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
