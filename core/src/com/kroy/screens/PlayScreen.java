package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.Connection;
import com.kroy.classes.Coord;
import com.kroy.classes.Firetruck;
import com.kroy.classes.Object;
import com.kroy.game.MapGraph;
import com.kroy.game.kroyGame;
import com.kroy.scenes.Hud;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScreen implements Screen {
    private kroyGame game;
    private OrthographicCamera cam;
    private Viewport gamePort;

    private Music bgMusic;
    private Texture background;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private MapGraph mapGraph;
    private GraphPath<Coord> mapPath;
    private ArrayList<Object> objs = new ArrayList<Object>();

    public PlayScreen(kroyGame game){
        this.game   = game;
        background  = new Texture("bg-menu.png");
        cam         = new OrthographicCamera();
        gamePort    = new FitViewport(1080, 720, cam);
        hud         = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
       // map = mapLoader.load("[filename].tmx");
        //renderer = new OrthogonalTiledMapRenderer(map);
        try {
            loadGraph();
        } catch (IOException e) {
            System.out.println("Error reading file..." + e.toString());
        }

        objs.add(new Firetruck(50, 50));
    }

    @Override
    public void show() {

    }
    public void handleInput(){

    }
    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(cam.projection);
        game.batch.begin();
        //draw all objects
        for(Object i : objs) {
              game.batch.draw(i.model, i.getX(), i.getY());
              i.update(delta);
        }
        game.batch.end();
        //draw HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        cam.update();
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
            Coords.put(name, c);
            mapGraph.addPoint(c);
        }

        // Calculate connections
        ArrayList<Connection> connections = new ArrayList<>();

        for (String key: Coords.keySet()) {
            // Iterate through all connections
            for (String conKey: Coords.get(key).connections) {
                System.out.println(key + "=>" + conKey);
                // Create a conneciton between the two nodes
                Connection temp = new Connection(Coords.get(key), Coords.get(conKey));
                mapGraph.connectPoints(Coords.get(key), Coords.get(conKey));
                System.out.println(temp.cost);
                connections.add(temp);
            }
        }

    }

}
