package com.kroy.classes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

public class Sector {
    private List<alien> aliens;
    private List<Firetruck> firetrucks;
    private List<Item> items;
    private List<Building> buildings;

    public Sector(Viewport viewport, SpriteBatch sb) {
    }

    public List<alien> getAliens() {
        return aliens;
    }

    public void setAliens(List<alien> aliens) {
        this.aliens = aliens;
    }

    public List<Firetruck> getFiretrucks() {
        return firetrucks;
    }

    public void setFiretrucks(List<Firetruck> firetrucks) {
        this.firetrucks = firetrucks;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public void loadStage(){

    }
}
