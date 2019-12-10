package com.kroy.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.classes.Pair;

import java.util.Arrays;

public class Coord {
    public String name;
    public String[] connections;
    public int index;
    public int x, y;
    public Coord(Integer x, Integer y, String name, String[] connections) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.connections = connections;
    }

    @Override
    public String toString() { return this.name + " | " + x + ","+y + " | " + Arrays.toString(this.connections);  }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch, BitmapFont font, boolean inPath){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(inPath) {
            // green
            shapeRenderer.setColor(Color.GREEN);
        }
        else{
            // blue
            shapeRenderer.setColor(Color.CYAN);
        }
        shapeRenderer.circle(x, y, 20);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(x, y, 32);
        shapeRenderer.end();

        shapeRenderer.setColor(Color.WHITE);
        batch.begin();
        font.setColor(0, 0, 0, 255);
        font.draw(batch, name, x-5, y+5);
        batch.end();
    }
}
