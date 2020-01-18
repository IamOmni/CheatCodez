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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.Object;
import com.kroy.classes.*;
import com.kroy.game.Constants;
import com.kroy.game.kroyGame;
import com.kroy.modules.MapLoader;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import com.kroy.pathfinding.Street;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.atan2;

import static java.lang.Math.atan2;
import static java.lang.Math.tan;

public class PlayScreen implements Screen, InputProcessor {
    private kroyGame game;
    private OrthographicCamera camera, hudCamera;
    private Viewport viewport, hudViewport;
    private int time;
    private Integer score;
    // Create Map to store all the coords
    Map<String, Coord> coords = new HashMap<>();

    private TiledMap map;
    private TiledMapRenderer tiledMapRenderer;

    private MapGraph mapGraph;
    private ArrayList<Object> objs = new ArrayList<Object>();
    private ArrayList<Firetruck> firetrucks = new ArrayList<>();
    private Firetruck activeFiretruck;
    public static BitmapFont font;

    private int width, height;
    private int ratioW, ratioH;
    private Button toggleActive;
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
        camera = new OrthographicCamera(width/ Constants.PPM, height/ Constants.PPM);
        hudCamera = new OrthographicCamera(width, height);
        viewport = new FitViewport(width/ Constants.PPM, height/ Constants.PPM, camera);
        hudViewport = new FitViewport(width, height, hudCamera);

        camera.zoom = 40;


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


        toggleActive  = new Button(50, 200, height-200, height-50, game.manager.get("Menu_Assets/HELP.png", Texture.class));
        try {
            MapLoader.loadGraph(coords,mapGraph);
            MapLoader.loadObjects(map,world);
            //load Buildings
            {
                Landmark b = new Landmark(3500, 1610, 100, game.manager.get("shambles_invaded.png", Texture.class), -50f, 0.6f, world);
                Landmark cliffordTower = new Landmark(1810, 510, 100, game.manager.get("cliffordtower_invaded.png", Texture.class), 0f, 0.5f, world);
                Landmark yorkStation = new Landmark(500, 2750, 100, game.manager.get("yorkstation_invaded.png", Texture.class), 0f, 0.7f, world);
                Landmark yorkMinster = new Landmark(3005, 2750, 100, game.manager.get("minster_invaded.png", Texture.class), 0f, 0.7f, world);
                Base watertower1 = new Base(1919, 1792, 100, game.manager.get("waterstation_normal.png", Texture.class), 0f, 0.6f, world);
                Base watertower2 = new Base(450, 510, 100, game.manager.get("waterstation_normal.png", Texture.class), 0f, 0.4f, world);
                //int x, int y, int health, Texture texture, float rotation, float scale, World world
                Building genericBuilding = new Building(450, 766, 0, game.manager.get("generic_building_2.png", Texture.class), 0f, 1, world);

                objs.add(genericBuilding);

                for (int i = 0; i < 5; i++) {
                    genericBuilding = new Building(450, 766 + (256 * i), 0, game.manager.get("generic_building_2.png", Texture.class), 0f, 1, world);
                    objs.add(genericBuilding);
                }

                for (int i = 0; i < 5; i++) {
                    genericBuilding = new Building(832, 766 + (256 * i), 0, game.manager.get("generic_building_2.png", Texture.class), 0f, 1, world);
                    objs.add(genericBuilding);
                }

                for (int i = 0; i < 4; i++) {
                    genericBuilding = new Building(448 + (256 * i), 128, 0, game.manager.get("generic_building.png", Texture.class), 0f, 1, world);
                    objs.add(genericBuilding);
                }
                objs.add(cliffordTower);
                objs.add(yorkStation);
                objs.add(yorkMinster);
                objs.add(watertower1);
                objs.add(watertower2);
                objs.add(b);
            }
            //load Firetrucks
            {
                Firetruck f = new Firetruck(mapGraph, coords.get("A"), 1, game.manager);
                firetrucks.add(f);
                objs.add(f);
                activeFiretruck = firetrucks.get(0);
                f = new Firetruck(mapGraph, coords.get("G"), 2, game.manager);
                firetrucks.add(f);
                objs.add(f);
            }

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
        int x   = (int)(0.07 * width);
        int y      = (int)(0.07 * height) +50;

        handleInput();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TitilliumWeb-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        font = generator.generateFont(parameter);

        time +=Math.ceil(Gdx.graphics.getDeltaTime());
        camera.update();
        // display background layout
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,0,1);
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
            if (i instanceof Base) {
                ((Base) i).update(firetrucks);
            }

            if (i instanceof Landmark){

                ((Landmark)i).setMisiledelay(((Landmark) i).getMisiledelay()-1f);
                if (((Landmark) i).getMisiledelay()<0) {

                    for (Firetruck firetruck: firetrucks){

                        Vector2 position1v = firetruck.body.getPosition();
                        Vector2 position2v = i.body.getPosition();
                        float v = position2v.dst(position1v)/ Constants.PPM;

                        float xDif = position1v.x-(Math.abs(position2v.x));
                        float yDif = position1v.y-(Math.abs(position2v.y));
                        float angle = (float) atan2(yDif, xDif);

                        if (i.getModel().getTextureData().toString().contains("station")){
                            System.out.println(angle);
                        }

                        if (v < 15) {
                            FortressMissile p = new FortressMissile(i.body.getPosition().x, i.body.getPosition().y, kroyGame.manager.get("alienbullet.png"), (float) Math.toRadians(Math.toDegrees(angle)-90f));
                            tempStore.add(p);
                        }
                    }
                    ((Landmark)i).setMisiledelay(40f);
                }

            }

            i.render(game.batch);
            i.update(delta);

        }

        objs.addAll(tempStore);


        for(Object i : objs) {
            i.displayHealth(game.batch);
        }
        game.batch.end();
        mB2dr.render(world,camera.combined);

        game.batch.setProjectionMatrix(hudCamera.combined);
        game.shapeRenderer.setProjectionMatrix(hudCamera.combined);
        hudViewport.apply();

        toggleActive.render(game.batch);

        game.batch.begin();

        font.setColor(Color.WHITE);
        font.getData().scale(1);


        font.draw( game.batch,"SCORE",x , y);
        font.draw( game.batch,String.format("%d", score), (float) (x+0.15*width), y);
        game.batch.end();

        removeDeadObjects();

        Array<Contact> contacts = world.getContactList();

        for(Contact contact : contacts) {

            Object a = (Object) contact.getFixtureA().getBody().getUserData();
            Object b = (Object) contact.getFixtureB().getBody().getUserData();
            if (a instanceof Landmark && !(b instanceof FortressMissile)) {
                ((Object) contact.getFixtureB().getBody().getUserData()).hitpoints=0;
                ((Object) contact.getFixtureA().getBody().getUserData()).hitpoints-=10;
            }

            if (b instanceof FortressMissile && a instanceof Firetruck) {

                ((Object) contact.getFixtureB().getBody().getUserData()).hitpoints=0;
                ((Object) contact.getFixtureA().getBody().getUserData()).hitpoints-=Constants.FORTRESS_DAMAGE;
            }
        }



        if (time%1000==0) {score+=10;};

        if (time>3000) time=0;

    }

    public void removeDeadObjects(){
        ArrayList<Object> notDeleted = new ArrayList<>();
        ArrayList<Firetruck> remainingFTs = new ArrayList<>();
        for (Firetruck ft: firetrucks){
            if(ft.hitpoints > 0)
                remainingFTs.add(ft);
        }
        for (Object obj: objs){
            if(obj.hitpoints >= 0){
                notDeleted.add(obj);
            }
            else{
                if(obj instanceof Firetruck){
                    System.out.println("DELETE ME");
                    if(activeFiretruck == obj){

                        switchFiretruck();
                    }

                }
                obj.body.setUserData(null);
                world.destroyBody(obj.body);
            }
        }
        objs = notDeleted;
        firetrucks = remainingFTs;
        if(remainingFTs.size() <= 0){
            game.setScreen(new MainMenuScreen(game));
        }
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




    // handles inputs not covered by the InputProcessor
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
            if (activeFiretruck.getFiredelay()<0f && (activeFiretruck.getAmmo() > 0)){
                Projectile p = activeFiretruck.createProjectile();

                objs.add(p);
                activeFiretruck.setFiredelay(50f);
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
        if (keycode == Input.Keys.Q) {
            camera.zoom = camera.zoom -10f;
        }
        if (keycode == Input.Keys.E) {
            camera.zoom = camera.zoom + 10f;
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


    void switchFiretruck(){
        for (int i = 0; i < firetrucks.size(); i++){
            if(activeFiretruck.ufid == firetrucks.get(i).ufid){
                System.out.println("ID: " + activeFiretruck.ufid);
                activeFiretruck.setActive(false);
                if(i < firetrucks.size() - 1){
                    activeFiretruck = firetrucks.get(i + 1);

                }
                else{
                    activeFiretruck = firetrucks.get(0);
                }
                activeFiretruck.setActive(true);
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(toggleActive.hasBeenClicked(screenX, screenY, true)){
            System.out.println("BUTTON PRESSED");
            switchFiretruck();
            return false;
        }
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
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
        camera.zoom = camera.zoom + amount;
        return false;
    }
}