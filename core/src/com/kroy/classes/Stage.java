package com.kroy.classes;
import java.util.List;

public class Stage {
    private List<Alien> aliens;
    private List<Firetruck> firetrucks;
    private List<Item> items;
    private List<Building> buildings;

    public List<Alien> getAliens() {
        return aliens;
    }

    public void setAliens(List<Alien> aliens) {
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
