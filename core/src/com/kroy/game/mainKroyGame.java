package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kroy.states.GameStateManager;
import com.kroy.states.MenuState;

public class mainKroyGame extends ApplicationAdapter {
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1080;

    private GameStateManager gsm;
	private SpriteBatch batch;

    // Create an array to be filled with the bodies
    // (better don't create a new one every time though)
    private Array<Body> bodies = new Array<Body>();
    Rectangle box = new Rectangle();
	//this creates a new world without any force in either x or y direction bc top down view
	
	public void init(){

	};

	public void makeObject(){
		box.x = 820 / 2 - 64 / 2;
		box.y = 20;
		box.width = 32;
		box.height = 32;
	}

	@Override
	public void create() {

		System.out.println("aaaa");
		batch = new SpriteBatch();
        gsm = new GameStateManager();
        gsm.push(new MenuState(gsm));
        init();
		makeObject();
        Gdx.gl.glClearColor(0, 233, 255, 1);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(0);
        gsm.render(batch);

	}


	@Override
	public void dispose() {
		batch.dispose();

	}
}
