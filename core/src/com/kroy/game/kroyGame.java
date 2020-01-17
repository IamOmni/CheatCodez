package com.kroy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.kroy.screens.MainMenuScreen;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class kroyGame extends Game {
	public static final int HEIGHT = 1080;
	public static final int WIDTH = 1920;
	public ShapeRenderer shapeRenderer;
	public static AssetManager manager;
	public SpriteBatch batch;

	private List<String> loadAssetsFolder(String folder){
		ArrayList<String> results = new ArrayList<>();
		File[] files = new File(Paths.get(folder).toAbsolutePath().toString()).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null.
		for (File file : files) {
			System.out.println(file.getName());
			if (file.isFile()) {
				System.out.println(String.format("%s/%s", folder, file.getName()));
				results.add(String.format("%s/%s", folder, file.getName()));
				manager.load(String.format("%s/%s", folder, file.getName()), Texture.class);
			}
		}
		return results;
	}

	public void loadAssets(){
		manager = new AssetManager();
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(Texture.class, new TextureLoader(resolver));
		System.out.println(Paths.get("").toAbsolutePath().toString());
		ArrayList<String> results = new ArrayList<>();

		File[] files = new File(Paths.get("").toAbsolutePath().toString()).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null.
		for (File file : files) {
			System.out.println(file.getName());
			//&& file.getName().contains(".png")
			if (file.isFile() && file.getName().contains(".png") ) {
				results.add(file.getName());
				manager.setLoader(Texture.class, new TextureLoader(resolver));
				manager.load(file.getName(), Texture.class);
			} else if (file.isFile() && file.getName().contains(".tmx")){
				results.add(file.getName());
				manager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
				manager.load(file.getName(), TiledMap.class);
			} else if (!file.isFile()){
				List<String> s = loadAssetsFolder(file.getName());
				results.addAll(s);
			}
		}
		manager.finishLoading();
		manager.update();
		System.out.println("loaded assets: " + manager.getLoadedAssets());

	}

	/**
	 *
	 * @param width
	 * @param height
	 */
	@Override
    public void resize(int width, int height){
		screen.resize(width,height);

    }

	/**
	 *
	 */
	@Override
	public void create() {
		System.out.println("running create()");
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		loadAssets();
		setScreen(new MainMenuScreen(this));
	}

	/**
	 *
	 */
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render();

	}

	/**
	 *
	 */
	@Override
	public void dispose() {
		batch.dispose();
		manager.dispose();
		shapeRenderer.dispose();
	}

}
