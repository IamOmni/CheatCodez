package com.kroy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kroy.screens.MainMenuScreen;
import com.kroy.screens.PlayScreen;
import com.kroy.states.GameStateManager;

import java.util.function.Function;

public class kroyGame extends Game {
	public static final int HEIGHT = 1080;
	public static final int WIDTH = 1920;
	public ShapeRenderer shapeRenderer;
	public SpriteBatch batch;

	@Override
    public void resize(int width, int height){
		screen.resize(width,height);

    }

	@Override
	public void create() {
		System.out.println("running create()");
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
//		var s = " Michale ";
//		String stripTrailingResult = s.stripTrailing();
//		System.out.println(stripTrailingResult);

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();

	}


	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}

}
