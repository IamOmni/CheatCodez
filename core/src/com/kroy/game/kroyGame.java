package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
		try {
			loadGraph();
		} catch (IOException e) {
			System.out.println("Error reading file..." + e.toString());
		}
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

	public void loadGraph() throws IOException {
	    // Read fle and fetch all lines
		String workingDir = System.getProperty("user.dir");
		File file = new File("../../core/assets/graph.txt");
		List<String> lines = Files.readAllLines(Paths.get(file.getCanonicalPath()));

		// Create Map to store all the coords
        Map<String, Coord> Coords = new HashMap<>();
		for (int i=0; i<lines.size();i++) {
			String name = lines.get(i).split(" ")[0];
			String[] connections = lines.get(i).split(":")[1].replace(" ", "").split(",");
			String s = lines.get(i).split("\\(")[1].split("\\)")[0].replace(" ", "");
			Integer x = Integer.parseInt(s.split(",")[0]);
            Integer y = Integer.parseInt(s.split(",")[1]);
            Coord c = new Coord(x, y, name, connections );
			Coords.put(name, c);
		}

		// Calculate connections
		ArrayList<Connection> connections = new ArrayList<>();
		for (String key: Coords.keySet()) {
			// Iterate through all connections
			for (String conKey: Coords.get(key).connections) {
				System.out.println(key + "=>" + conKey);
				// Create a conneciton between the two nodes
				Connection temp = new Connection(Coords.get(key), Coords.get(conKey));
				System.out.println(temp.cost);
				connections.add(temp);
			}
		}

	}
}
