package com.kroy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class kroyGameTest extends Game {
    public static final int HEIGHT = 1080;
    public static final int WIDTH = 1920;
    public ShapeRenderer shapeRenderer;
    public SpriteBatch batch;
    //public FiretruckTest f;


    /**
     *
     */
    @Override
    public void create() {
        System.out.println("running create() for Test");
    }

    /**
     *
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }

}
