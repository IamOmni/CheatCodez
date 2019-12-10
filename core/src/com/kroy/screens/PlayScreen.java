package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
import com.kroy.pathfinding.Agent;
import com.kroy.pathfinding.Coord;
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
    private OrthographicCamera camera, HudCam;
    private Viewport gamePort, hudPort;
    private Animation anim;
    private Music bgMusic;
    private Sprite background;
    private Hud hud;

    // Create Map to store all the coords
    Map<String, Coord> Coords = new HashMap<>();

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private MapGraph mapGraph;
    private GraphPath<Coord> mapPath;
    private ArrayList<Object> objs = new ArrayList<Object>();

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Texture stats;

    private int width, height;

    private Agent agent;

    public PlayScreen(kroyGame game){
        this.game   = game;
        background  = new Sprite(new Texture("Menu_Assets/BACKGROUND.png"));
        width  = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        HudCam = new OrthographicCamera(width, height);

        gamePort    = new FitViewport(width, height, camera);
        hudPort     = new FitViewport(width, height, HudCam);
        camera.position.set(game.WIDTH /2, game.HEIGHT/2, 0);
        HudCam.position.set(game.WIDTH /2, game.HEIGHT/2, 0);

        stats = new Texture("sidebar.png");

        hud         = new Hud(game.batch);
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        mapGraph = new MapGraph();
        mapLoader = new TmxMapLoader();


        try {
            loadGraph();

            agent = new Agent(mapGraph, Coords.get("A"));
            agent.setGoal(Coords.get("E"));
            mapPath = mapGraph.findPath(Coords.get("A"), Coords.get("E"));

            System.out.println("Count for returned path: " + mapPath.getCount());
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

        agent.step();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1,0,1,1);

        gamePort.apply();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        game.batch.draw(background, 0, 0, gamePort.getScreenWidth(), gamePort.getScreenHeight());
        //draw all objects
        for(Object i : objs) {
              game.batch.draw(i.model, i.getX(), i.getY());
              i.update(delta);
        }
        game.batch.end();
        //
        for (Street street : mapGraph.streets) {
            street.render(shapeRenderer);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.end();
        }


        // Draw all cities blue
        for (Coord city : mapGraph.coords) {
            city.render(shapeRenderer, game.batch, font, false);
        }

        for (Coord city : agent.graphPath) {
            city.render(shapeRenderer, game.batch, font, true);
        }

        agent.render(shapeRenderer,game.batch);
        shapeRenderer.setColor(Color.WHITE);

        //draw HUD
        game.batch.setProjectionMatrix(HudCam.combined);
        game.batch.begin();

        game.batch.draw(stats,
                Gdx.graphics.getWidth() - stats.getWidth(),
                0,
                stats.getWidth(),
                height
        );
        game.batch.end();
     //   hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
        this.width  = width;
        gamePort.update(width, height);
        hudPort .update(width, height);
        camera  .update();
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
        font.dispose();
        shapeRenderer.dispose();
     //   bgMusic.dispose();
        stats.dispose();
        map.dispose();
    }


    public void loadGraph() throws IOException {


        // Read fle and fetch all lines
        File file = new File("../../core/assets/graph.txt");
        List<String> lines = Files.readAllLines(Paths.get(file.getCanonicalPath()));


        for (int i=0; i<lines.size();i++) {
            String name = lines.get(i).split(" ")[0];
            String[] connections = lines.get(i).split(":")[1].replace(" ", "").split(",");
            String s = lines.get(i).split("\\(")[1].split("\\)")[0].replace(" ", "");
            Integer x = Integer.parseInt(s.split(",")[0]);
            Integer y = Integer.parseInt(s.split(",")[1]);
            Coord c = new Coord(x, y, name, connections );
            Coords.put(name, c);
            mapGraph.addPoint(c);
        }

        // Calculate connections

        for (String key: Coords.keySet()) {
            // Iterate through all connections
            for (String conKey: Coords.get(key).connections) {
                // Create a conneciton between the two nodes
                mapGraph.connectPoints(Coords.get(key), Coords.get(conKey));

            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE){
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
        if(keycode == Input.Keys.LEFT){
            camera.translate(-100f, 0f);
            System.out.println("LEFT");
        }
        if(keycode == Input.Keys.RIGHT){
            camera.translate(100f, 0f);
        }
        if(keycode == Input.Keys.UP){
            camera.translate(0f, 100f);
        }
        if(keycode == Input.Keys.DOWN){
            camera.translate(0f, -100f);
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
