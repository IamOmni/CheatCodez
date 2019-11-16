package com.kroy.classes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kroy.classes.Pair;


public class Object {
    private Sprite model;
    private Pair<Integer, Integer> coords;

    public Sprite getModel() {
        return model;
    }

    public void setModel(Sprite model) {
        this.model = model;
    }

    public Pair<Integer, Integer> getCoords() {
        return coords;
    }

    public void setCoords(Pair<Integer, Integer> coords) {
        this.coords = coords;
    }

    public Integer getX(){
        return this.coords.getX();
    }

    public Integer getY(){
        return this.coords.getY();
    }
}
