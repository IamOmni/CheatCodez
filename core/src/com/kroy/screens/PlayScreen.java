package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.*;

import com.kroy.classes.Object;
import com.kroy.modules.ShapeFactory;
import com.kroy.pathfinding.Coord;
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
    private OrthographicCamera camera, hudCamera;
    private Viewport viewport, hudViewport;
    private int time;
    private Integer score;
    // Create Map to store all the coords
    Map<String, Coord> Coords = new HashMap<>();

    private TiledMap map;
    private TiledMapRenderer tiledMapRenderer;

    private MapGraph mapGraph;
    private ArrayList<Object> objs = new ArrayList<Object>();
    private ArrayList<Firetruck> firetrucks = new ArrayList<>();
    private Firetruck activeFiretruck;
    public static BitmapFont font;

    private int width, height;
    private int ratioW, ratioH;

    public static World world;
    private Box2DDebugRenderer mB2dr;

    public PlayScreen(kroyGame game){
        this.game   = game;


        mapGraph = new MapGraph();
        score = 0;
        map = game.manager.get("map-two-layer-new.tmx", TiledMap.class);

        width  = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        ratioW = Gdx.graphics.getWidth()/game.WIDTH;
        ratioH = Gdx.graphics.getHeight()/game.HEIGHT;
        camera = new OrthographicCamera(width, height);
        hudCamera = new OrthographicCamera(width, height);
        viewport = new FitViewport(width, height, camera);
        hudViewport = new FitViewport(width, height, hudCamera);




        System.out.println(width/3);
    //    camera.position.set((float)cameraCoords.get(xCounterCam).get(yCounterCam).get(0) , (float)cameraCoords.get(xCounterCam).get(yCounterCam).get(1) , 0);
        camera.update();
        hudCamera.update();
        camera.position.set(game.WIDTH /2, game.HEIGHT/2, 0);
        hudCamera.position.set(game.WIDTH/2, game.HEIGHT/2, 0);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1);
        tiledMapRenderer.setView((OrthographicCamera) camera);

        this.mB2dr = new Box2DDebugRenderer();
        world = new World(new Vector2(0.0f, 0.0f),true);


        try {
            loadGraph();
            loadTiledMap();

            Landmark b = new Landmark(3500, 1610, 50, game.manager.get("shambles_invaded.png", Texture.class), -50f, 0.6f, world);
            Landmark cliffordTower = new Landmark(1810, 510, 50, game.manager.get("cliffordtower_invaded.png", Texture.class), 0f, 0.5f, world);
            Landmark yorkStation = new Landmark(500, 2750, 50, game.manager.get("yorkstation_invaded.png", Texture.class), 0f, 0.7f, world);
            Landmark yorkMinster = new Landmark(3005, 2750, 50, game.manager.get("minster_invaded.png", Texture.class), 0f, 0.7f, world);
            Base watertower1 = new Base(1919, 1792, 50, game.manager.get("waterstation_normal.png", Texture.class), 0f, 0.6f, world);
            Base watertower2 = new Base(450, 510, 50, game.manager.get("waterstation_normal.png", Texture.class), 0f, 0.4f, world);
         //   Alien aTest = new Alien(yorkMinster, "below", game.manager);
          //  objs.add(aTest);
            Firetruck f = new Firetruck(mapGraph, Coords.get("P"), 1, game.manager);
            firetrucks.add(f);
            activeFiretruck = firetrucks.get(0);
            objs.add(f);
            objs.add(cliffordTower);
            objs.add(yorkStation);
            objs.add(yorkMinster);
            objs.add(watertower1);
            objs.add(watertower2);
            objs.add(b);

        } catch (IOException e) {
            System.out.println("Error reading file..." + e.toString());
        }
        Gdx.input.setInputProcessor(this);
    }


    public void loadTiledMap(){

        loadObjects();
    }
    public void loadObjects(){
        MapObjects objects = map.getLayers().get("WALLS").getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            System.out.println(rectangle.getX());
            System.out.println(rectangle.getY());
            ShapeFactory.createRectangle(
                    new Vector2(rectangle.getX() * 4 + rectangle.getWidth() * 2, rectangle.getY() * 4 + rectangle.getHeight() * 2), // position
                    new Vector2(rectangle.getWidth() *2, rectangle.getHeight() *2), // size
                    BodyDef.BodyType.StaticBody, world, 1f, false);
        }

    }

    @Override
    public void show() {

    }

    public void newRender(float dt){

    }

    @Override
    public void render(float delta) {
        handleInput();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TitilliumWeb-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        font = generator.generateFont(parameter);

        time +=Math.ceil(Gdx.graphics.getDeltaTime());
        camera.update();
        // display background layout
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,0,1);
        camera.position.set(activeFiretruck.body.getPosition(), 0);
        world.step(delta, 6, 2);
        camera.position.set(activeFiretruck.body.getPosition(),0);
        game.shapeRenderer.setColor(Color.WHITE);
        viewport.apply();

        Gdx.gl.glViewport(
                (int)(0),
                (int)(0),
                (int)(width),
                (int)(height)
        );






        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        game.shapeRenderer.end();

        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.batch.end();


        // Render level
        tiledMapRenderer.setView(camera);

        map.getLayers().get(0).setVisible(true);
        map.getLayers().get(1).setVisible(true);
        tiledMapRenderer.render();
        //
        for (Street street : mapGraph.streets) {
            street.render(game.shapeRenderer);
        }


        // Draw all points
        for (Coord city : mapGraph.coords) {
            city.render(game.shapeRenderer, game.batch, font, false);
        }

        game.batch.begin();

        //draw all objects
        ArrayList<Object> tempStore = new ArrayList<>();
        for(Object i : objs) {
            if (i instanceof Landmark){
//                if (time% ((Landmark) i).getSpawnTime()==0) {
//                    System.out.println("SPAWN");
//                    Alien a = new Alien((Landmark) i, "above", game.manager);
//                    tempStore.add(a);
//                }
                i.render(game.batch);
            } else if (i instanceof Firetruck) {
                for (Projectile p: ((Firetruck) i).getBullets()) {
                    p.render(game.batch);
                }
                i.render(game.batch);
            } else {
                i.render(game.batch);
                i.update(delta);
            }
        }
        objs.addAll(tempStore);
      //  game.batch.draw(kroyGame.manager.get("alien.png", Texture.class), 10, 10, 200, 200);


        for(Object i : objs) {
            i.displayHealth(game.batch);
        }
        game.batch.end();
        //agent.render(game.shapeRenderer,game.batch);
        mB2dr.render(world,camera.combined);

        game.batch.setProjectionMatrix(hudCamera.combined);
        game.shapeRenderer.setProjectionMatrix(hudCamera.combined);
        hudViewport.apply();
        game.batch.begin();
       // game.batch.draw(kroyGame.manager.get("alien.png", Texture.class), 10, 10, 200, 200);
        font.setColor(Color.WHITE);
        font.getData().scale(1);
        int x   = (int)(0.07 * width);
        int y      = (int)(0.07 * height) +50;
        font.draw( game.batch,"SCORE",x , y);
        font.draw( game.batch,String.format("%d", score), (float) (x+0.15*width), y);
        Button r  = new Button();

        game.shapeRenderer.setColor(Color.WHITE);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(50, height - 100, 100, height - 50);
        game.shapeRenderer.end();
        game.batch.end();

        if (time%1000==0) {score+=10;};

        if (time>3000) time=0;

    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
        this.width  = width;
        ratioW = width/game.WIDTH;
        ratioH = height/game.HEIGHT;
        viewport.update(width, height);
        hudViewport.update(width,height);
        hudCamera.update();
        camera  .update();

   //     camera = new OrthographicCamera(0.63f * width, 0.77f * height);
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
    }


    public void loadGraph() throws IOException {

        // 4.5hrs
        // Read fle and fetch all lines
        File file = new File("graph.txt");
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

    public void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            activeFiretruck.mDriveDirection = activeFiretruck.DRIVE_DIRECTION_FORWARD;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            activeFiretruck.mDriveDirection = activeFiretruck.DRIVE_DIRECTION_BACKWARD;
        }
        else {
            activeFiretruck.mDriveDirection = activeFiretruck.DRIVE_DIRECTION_NONE;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            activeFiretruck.mTurnDirection = activeFiretruck.TURN_DIRECTION_LEFT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            activeFiretruck.mTurnDirection = activeFiretruck.TURN_DIRECTION_RIGHT;
        }
        else {
            activeFiretruck.mTurnDirection = activeFiretruck.TURN_DIRECTION_NONE;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if (activeFiretruck.getFiredelay()<0){
                Projectile p = new Projectile(activeFiretruck.body.getPosition().x, activeFiretruck.body.getPosition().y, game.manager.get("bullet.png"), activeFiretruck.body.getAngle());
                objs.add(p);
                activeFiretruck.setFiredelay(50);
            }

        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            dispose();
            game.setScreen(new MainMenuScreen(game));

        }
        if (keycode == Input.Keys.LEFT) {
            camera.translate(-10f, 0f);
        }
        if (keycode == Input.Keys.RIGHT) {
            camera.translate(10f, 0f);
        }
        if (keycode == Input.Keys.UP) {
            camera.translate(0, 10f);
        }
        if (keycode == Input.Keys.DOWN) {
            {
                camera.translate(0f, -10f);
            }

         //   camera.position.set((float) cameraCoords.get(xCounterCam).get(yCounterCam).get(0), (float) cameraCoords.get(xCounterCam).get(yCounterCam).get(1), 0);

            if (activeFiretruck != null) {
                if (keycode == (Input.Keys.W)) {

                }
                if (keycode == (Input.Keys.D)) {
                    System.out.println("D key is pressed");
                    activeFiretruck.setRight(true);
                }
                if (keycode == (Input.Keys.A)) {
                    System.out.println("A key is pressed");

                    activeFiretruck.setLeft(true);
                }
                if (keycode == (Input.Keys.S)) {
                    System.out.println("S key is pressed");
                    activeFiretruck.setDown(true);
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(activeFiretruck != null){
            if (keycode == (Input.Keys.W)) {
                System.out.println("W key is pressed");
                activeFiretruck.setUp(false);
            }
             if (keycode == (Input.Keys.D)) {
                System.out.println("D key is pressed");
                activeFiretruck.setRight(false);
            }
             if (keycode == (Input.Keys.A)) {
                System.out.println("A key is pressed");
                activeFiretruck.setLeft(false);
            }
             if(keycode == (Input.Keys.S)) {
                System.out.println("S key is pressed");
                activeFiretruck.setDown(false);
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (Firetruck ft: firetrucks){
            System.out.println("Checking firetruck");
            if(ft.status.getAttack().hasBeenClicked(screenX,screenY, true)){
                System.out.println("FIRETRUCK SET TO " + ft.isActive());
                ft.setActive(!ft.isActive());
                activeFiretruck.setActive(false);
                activeFiretruck = ft;
            }
        }
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
