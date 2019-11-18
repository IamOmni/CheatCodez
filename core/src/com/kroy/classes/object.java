package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.kroy.classes.Pair;


public class Object{
    public Texture model;
    protected Vector3 position;

    private Pair<Integer, Integer> coords;

    public Object() {
    }
    public Texture getModel() {
        return model;
    }

    public void setModel(Texture model) {
        this.model = model;
    }

    public Pair<Integer, Integer> getCoords() {
        return coords;
    }

    public void setCoords(Pair<Integer, Integer> coords) {
        this.coords = coords;
    }

    public float getX(){
        return position.x;  /*this.coords.getX();*/
    }

    public float getY(){
        return  position.x; //this.coords.getY();
    }
}
