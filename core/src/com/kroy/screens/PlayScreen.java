package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.Coord;
import com.kroy.classes.Firetruck;
import com.kroy.classes.Object;
import com.kroy.pathfinding.MapGraph;
import com.kroy.game.kroyGame;
import com.kroy.pathfinding.Street;
import com.kroy.scenes.Hud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PlayScreen implements Screen, InputProcessor {
    private kroyGame game;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Animation anim;
    private Music bgMusic;
    private Sprite background;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private MapGraph mapGraph;
    private GraphPath<Coord> mapPath;
    private ArrayList<Object> objs = new ArrayList<Object>();

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;


    public PlayScreen(kroyGame game){
        this.game   = game;
        background  = new Sprite(new Texture("Menu_Assets/BACKGROUND.png"));
        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();

        camera = new OrthographicCamera();

        gamePort    = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        gamePort.apply();
        camera.position.set(game.WIDTH /2, game.HEIGHT/2, 0);


        hud         = new Hud(game.batch);
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        mapGraph = new MapGraph();
        mapLoader = new TmxMapLoader();
       // map = mapLoader.load("[filename].tmx");
        //renderer = new OrthogonalTiledMapRenderer(map);
        try {
            loadGraph();
        } catch (IOException e) {
            System.out.println("Error reading file..." + e.toString());
        }
        Gdx.input.setInputProcessor(this);
        objs.add(new Firetruck(50, 50));
    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        camera.update();


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1,0,1,1);

        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        //draw all objects
        for(Object i : objs) {
              game.batch.draw(i.model, i.getX(), i.getY());
              i.update(delta);
        }
        game.batch.end();
        //
        for (Street street : mapGraph.streets) {
            street.render(shapeRenderer);
        }
        // Draw all cities blue
        for (Coord city : mapGraph.coords) {
            city.render(shapeRenderer, game.batch, font, false);
        }


        //draw HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
     //   hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        camera.update();
        System.out.println("----");
        System.out.println(width);
        System.out.println(height);
        System.out.println("----");
    }

    @Override
    public void pause() {
        System.out.println("paused");

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

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
           // Coords.put(name, c);
            System.out.println("created node " + c.name);
            mapGraph.addPoint(c);
        }

        // Calculate connections

        for (String key: Coords.keySet()) {
            // Iterate through all connections
            for (String conKey: Coords.get(key).connections) {
                System.out.println(key + "=>" + conKey);
                // Create a conneciton between the two nodes
                mapGraph.connectPoints(Coords.get(key), Coords.get(conKey));
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT){
            camera.translate(-100f, 0f);
            System.out.println("LEFT");
        }
        if(keycode == Input.Keys.RIGHT){
            camera.translate(100f, 0f);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.translate(0, -100 * amount);
        return false;
    }
}
