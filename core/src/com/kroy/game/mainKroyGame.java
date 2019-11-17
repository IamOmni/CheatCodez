package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kroy.modules.gdxWrapper;

public class mainKroyGame extends GDXWrapper,ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
    private OrthographicCamera camera;
	//mouse / finger position on screen when
	private Vector3 touchPos = new Vector3();
	private World world;
	//this creates a new world without any force in either x or y direction bc top down view
	
	public void mainKroyGame(){
		world = new World(new Vector2(0, 0), true);
	};

	public void makeObject(){
		Rectangle box = new Rectangle();
		box.x = 820 / 2 - 64 / 2;
		box.y = 20;
		box.width = 32;
		box.height = 32;

		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, 10));

		// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(camera.viewportWidth, 10.0f);
		// Create a fixture from our polygon shape and add it to our ground body
		groundBody.createFixture(groundBox, 0.0f);
		// Clean up after ourselves
		groundBox.dispose();
	}

	@Override
	public void create() {
		Box2D.init();

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1080, 720);

		makeObject();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, box.x, box.y);

		if(Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			box.x = touchPos.x - 64 / 2;
			box.y = touchPos.y - 64 / 2;
		}

		// Create an array to be filled with the bodies
		// (better don't create a new one every time though)
		Array<Body> bodies = new Array<Body>();
		// Now fill the array with all bodies
		world.getBodies(bodies);

		for (Body b : bodies) {
			// Get the body's user data - in this example, our user
			// data is an instance of the Entity class
				// Update the entities/sprites position and angle
				batch.draw(img, b.getPosition().x, b.getPosition().y);
				// We need to convert our angle from
				//		 radians to degrees
		}
		world.step(1/60f, 6, 2);
		camera.update();
		batch.end();
	}


	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		world.dispose();
	}
}
