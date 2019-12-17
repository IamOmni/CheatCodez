package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.kroy.classes.Pair;


public class Object{
    public Texture model;
    protected Vector3 position;
    public float texRotation;

    public Object() {
    }
    public Texture getModel() {
        return model;
    }

    public void update(float dt){

    }
    public void setModel(Texture model) {
            this.model = (model);
    }

    public float getX(){
        return position.x;  /*this.coords.getX();*/
    }

    public float getY(){
        return  position.y; //this.coords.getY();
    }

    public void render(SpriteBatch sb){
        sb.draw(model,
                position.x,position.y,
                0,0,model.getWidth(),model.getHeight(),
                1,1,
                00f,
                0,0,model.getWidth(),model.getHeight(),
                false,false);
    }

}
