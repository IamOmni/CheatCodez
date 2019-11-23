package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kroy.classes.Connection;
import com.kroy.classes.Pair;
import com.kroy.screens.PlayScreen;
import com.kroy.states.GameStateManager;
import com.kroy.states.MenuState;
import com.kroy.classes.Coord;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class kroyGame extends Game {
	public static final int HEIGHT = 720;
	public static final int WIDTH = 1080;

    protected GameStateManager gsm;
	public SpriteBatch batch;

	// Create an array to be filled with the bodies
	// (better don't create a new one every time though)
	private Array<Body> bodies = new Array<Body>();
	//this creates a new world without any force in either x or y direction bc top down view


	public void init(){

	};

	@Override
    public void resize(int width, int height){


    }

	@Override
	public void create() {
		System.out.println("running create()");
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));

		gsm = new GameStateManager();

		init();
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
