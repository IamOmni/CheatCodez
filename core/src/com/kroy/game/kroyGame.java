package com.kroy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kroy.screens.MainMenuScreen;
import com.kroy.screens.PlayScreen;
import com.kroy.states.GameStateManager;

public class kroyGame extends Game {
	public static final int HEIGHT = 500;
	public static final int WIDTH = 1000;

    protected GameStateManager gsm;
	public SpriteBatch batch;

	// Create an array to be filled with the bodies
	// (better don't create a new one every time though)
	private Array<Body> bodies = new Array<Body>();
	//this creates a new world without any force in either x or y direction bc top down view


	@Override
    public void resize(int width, int height){


    }

	@Override
	public void create() {
		System.out.println("running create()");
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
        System.out.println("exiting create()");
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		//gsm.update(0);
		//gsm.render(batch);

	}


	@Override
	public void dispose() {
		batch.dispose();

	}

}
