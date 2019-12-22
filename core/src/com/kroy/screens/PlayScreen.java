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
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
    private TiledMapRenderer tiledMapRenderer;

    private MapGraph mapGraph;
    private ArrayList<Object> objs = new ArrayList<Object>();
    private ArrayList<Firetruck> firetrucks = new ArrayList<>();

    private BitmapFont font;
    private Texture stats;

    private int width, height;
    private int ratioW, ratioH;
    private Agent agent;

    public PlayScreen(kroyGame game){
        this.game   = game;
        background  = new Sprite(new Texture("background.png"));
        width  = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();

        ratioW = Gdx.graphics.getWidth()/game.WIDTH;
        ratioH = Gdx.graphics.getHeight()/game.HEIGHT;

        camera = new OrthographicCamera(1210 * ratioW, 827 * ratioH);
        HudCam = new OrthographicCamera(width, height);

        gamePort    = new FitViewport(1210, 827, camera);
        hudPort     = new FitViewport(width, height, HudCam);
        camera.position.set(game.WIDTH/2 , game.HEIGHT/2, 0);

        HudCam.position.set(game.WIDTH /2, game.HEIGHT/2, 0);

        stats = new Texture("sidebar.png");

        hud         = new Hud(game.batch);
        font = new BitmapFont();
        mapGraph = new MapGraph();
        mapLoader = new TmxMapLoader();

        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = false;
        map = mapLoader.load("map-two-layer-new.tmx",parameters);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1);
        tiledMapRenderer.setView((OrthographicCamera) camera);


        try {
            loadGraph();

            firetrucks.add(new Firetruck(mapGraph, Coords.get("A")));
            firetrucks.get(firetrucks.size()-1).setGoal(Coords.get("M"));
            objs.add(firetrucks.get(firetrucks.size()-1));
            agent = new Agent(mapGraph, Coords.get("A"));
            agent.setGoal(Coords.get("M"));

            System.out.println("Count for returned path: " + agent.graphPath.getCount());
        } catch (IOException e) {
            System.out.println("Error reading file..." + e.toString());
        }
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        camera.update();
        agent.step();

        // display background layout
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1,0,1,1);
        game.shapeRenderer.setColor(Color.WHITE);
        Gdx.gl.glViewport(0,0,width, height);
        //draw HUD
        hudPort.apply();
        game.batch.setProjectionMatrix(HudCam.combined);
        game.batch.begin();

        game.batch.draw(background, 0, 0, width, height);

        game.batch.end();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        game.shapeRenderer.end();

        // Thing that fits in the area where the map is displayed
        gamePort.apply();

        Gdx.gl.glViewport(585 * ratioW, (1080 - (175 + 827)) * ratioH, 1210 * ratioW, 827 * ratioH);
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.batch.end();


        // Render level
        tiledMapRenderer.setView(camera);

        map.getLayers().get(0).setVisible(true);
        map.getLayers().get(1).setVisible(true);
        tiledMapRenderer.render();
        System.out.println(map.getTileSets().toString());



        //
        for (Street street : mapGraph.streets) {
            street.render(game.shapeRenderer);
        }


        // Draw all points
        for (Coord city : mapGraph.coords) {
            city.render(game.shapeRenderer, game.batch, font, false);
        }
        // Draw all points in the agent's path coloured
        for (Coord city : agent.graphPath) {
            city.render(game.shapeRenderer, game.batch, font, true);
        }
        game.batch.begin();
        //draw all objects
        for(Object i : objs) {
            i.render(game.batch);
            i.update(delta);
        }
        game.batch.end();

        agent.render(game.shapeRenderer,game.batch);


     //   hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
        this.width  = width;
        ratioW = width/game.WIDTH;
        ratioH = height/game.HEIGHT;


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
     //   bgMusic.dispose();
        stats.dispose();
    //    map.dispose();
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
            Integer y = Math.abs(3240-Integer.parseInt(s.split(",")[1]));

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
