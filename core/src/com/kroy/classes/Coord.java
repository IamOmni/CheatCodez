package com.kroy.classes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Arrays;

public class Coord extends Pair {
    public String name;
    public String[] connections;
    public int index;

    public Coord(Integer x, Integer y, String name, String[] connections) {
        super(x, y);
        this.name = name;
        this.connections = connections;
    }

    @Override
    public String toString() { return this.name + " | " + super.getX() + ","+super.getY() + " | " + Arrays.toString(this.connections);  }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch, BitmapFont font, boolean inPath){
        int x = (int) getX();
        int y = (int) getY();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(inPath) {
            // green
            shapeRenderer.setColor(.57f, .76f, .48f, 1);
        }
        else{
            // blue
            shapeRenderer.setColor(.8f, .88f, .95f, 1);
        }
        shapeRenderer.circle(x, y, 20);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.circle(x, y, 20);
        shapeRenderer.end();

        batch.begin();
        font.setColor(0, 0, 0, 255);
        font.draw(batch, name, x-5, y+5);
        batch.end();
    }
}
