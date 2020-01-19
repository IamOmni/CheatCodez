package com.kroy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kroy.screens.MainMenuScreen;
import com.kroy.screens.PlayScreen;
import com.kroy.states.GameStateManager;

import java.util.function.Function;

import com.badlogic.gdx.maps.tiled.TiledMap;


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

	/**
	 * Load assets in the assets subfolder folder
	 */
	private List<String> loadAssetsFolder(String folder){
		ArrayList<String> results = new ArrayList<>();
		File[] files = new File(Paths.get("assets",folder).toAbsolutePath().toString()).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null.
		for (File file : files) {
			System.out.println(file.getName());
			if (file.isFile()) {
				results.add(String.format("%s/%s", folder, file.getName()));
				manager.load(String.format("%s/%s", folder, file.getName()), Texture.class);
			}
		}
		return results;
	}

	/**
	 * Load assets in the assets folder to one subfolder depth (rushed solution)
	 */
	public void loadAssets(){
		manager = new AssetManager();
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(Texture.class, new TextureLoader(resolver));
		ArrayList<String> results = new ArrayList<>();

		File[] files = new File(Paths.get("assets").toAbsolutePath().toString()).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null.
		for (File file : files) {

			if (file.isFile() && file.getName().contains(".png") ) {
				results.add(file.getName());
				manager.setLoader(Texture.class, new TextureLoader(resolver));
				manager.load(file.getName(), Texture.class);
			} else if (file.isFile() && file.getName().contains(".tmx")){
				results.add(file.getName());
				manager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
				manager.load(file.getName(), TiledMap.class);
			}
			else if (file.isFile() && file.getName().contains(".ttf")) {
				manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
				manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
				FreetypeFontLoader.FreeTypeFontLoaderParameter parms = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
				parms.fontFileName = file.getName();    // path of .ttf file where that exist
				parms.fontParameters.size = 16;
				manager.load(file.getName(), BitmapFont.class, parms);
			}else if (!file.isFile()){
				List<String> s = loadAssetsFolder(file.getName());
				results.addAll(s);
			}
		}
		manager.finishLoading();
		manager.update();

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
		Music mp3Music = Gdx.audio.newMusic(Gdx.files.internal("assets/Boss Fight.ogg"));
		mp3Music.play();
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
