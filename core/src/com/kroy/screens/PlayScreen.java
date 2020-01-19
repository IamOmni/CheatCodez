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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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
    private ArrayList<Landmark> landmarks = new ArrayList<>();
    private ArrayList<Base> bases = new ArrayList<>();
    private Firetruck activeFiretruck;
    public static BitmapFont font;

    private int width, height;
    private Button toggleActive;
    private Box2DDebugRenderer mB2dr;

    public PlayScreen(kroyGame game) {
        this.game = game;

        //set empty variables
        mapGraph = new MapGraph();
        score = 0;
        map = game.manager.get("map-two-layer-new.tmx", TiledMap.class);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(width / Constants.PPM, height / Constants.PPM);
        hudCamera = new OrthographicCamera(width, height);
        viewport = new FitViewport(width / Constants.PPM, height / Constants.PPM, camera);
        hudViewport = new FitViewport(width, height, hudCamera);

        camera.zoom = 40;
        //    camera.position.set((float)cameraCoords.get(xCounterCam).get(yCounterCam).get(0) , (float)cameraCoords.get(xCounterCam).get(yCounterCam).get(1) , 0);
        camera.update();
        hudCamera.update();
        camera.position.set(game.WIDTH / 2, game.HEIGHT / 2, 0);
        hudCamera.position.set(game.WIDTH / 2, game.HEIGHT / 2, 0);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1);
        tiledMapRenderer.setView((OrthographicCamera) camera);

        this.mB2dr = new Box2DDebugRenderer();
        Constants.world = new World(new Vector2(0.0f, 0.0f), true);


        toggleActive = new Button(50, 200, height - 200, height - 50, game.manager.get("Menu_Assets/HELP.png", Texture.class));
        try {
            MapLoader.loadGraph(coords,mapGraph, "graph.txt");
            MapLoader.loadObjects(map,Constants.world);
            //load Buildings
            {
                Landmark b = new Landmark(3500, 1600, 100, game.manager.get("shambles_invaded.png", Texture.class), -50f, 0.6f, Constants.world);
                Landmark cliffordTower = new Landmark(1792, 510, 100, game.manager.get("cliffordtower_invaded.png", Texture.class), 0f, 0.5f, Constants.world);
                Landmark yorkStation = new Landmark(512, 2750, 100, game.manager.get("yorkstation_invaded.png", Texture.class), 0f, 0.7f, Constants.world);
                Landmark yorkMinster = new Landmark(3008, 2750, 100, game.manager.get("minster_invaded.png", Texture.class), 0f, 0.7f, Constants.world);
                Base watertower1 = new Base(1920, 1792, 100, game.manager.get("waterstation_normal.png", Texture.class), 0f, 0.6f, Constants.world);
                Base watertower2 = new Base(448, 510, 100, game.manager.get("waterstation_normal.png", Texture.class), 0f, 0.4f, Constants.world);
                yorkMinster.invaded = true;
                yorkStation.invaded = true;
                cliffordTower.invaded = true;

                //int x, int y, int health, Texture texture, float rotation, float scale, World world
                Building genericBuilding = new Building(0, 766, 0, game.manager.get("building4.png", Texture.class), 0f, 1.2f, Constants.world);
                objs.add(genericBuilding);

                genericBuilding = new Building(0, (int) (766+ (256 * 2.4)), 0, game.manager.get("building4.png", Texture.class), 0f, 1.2f, Constants.world);
                objs.add(genericBuilding);
                genericBuilding = new Building(0, (int) (766+ (256 * 2.4)), 0, game.manager.get("building4.png", Texture.class), 0f, 1.2f, Constants.world);
                objs.add(genericBuilding);
                genericBuilding = new Building(832, 510, 0, game.manager.get("building2.png", Texture.class), 0f, 1.35f, Constants.world);
                objs.add(genericBuilding);
                genericBuilding = new Building(832, (int) (510+(570*1.35)), 0, game.manager.get("building2.png", Texture.class), 0f, 1.35f, Constants.world);
                objs.add(genericBuilding);

                genericBuilding = new Building(3136, 150, 0, game.manager.get("building3.png", Texture.class), 0f, 1.37f, Constants.world);
                objs.add(genericBuilding);

                genericBuilding = new Building(4096, 1100, 0, game.manager.get("building5.png", Texture.class), 0f, 1.37f, Constants.world);
                objs.add(genericBuilding);
                genericBuilding = new Building(2688, (int) (1984), 0, game.manager.get("building2.png", Texture.class), 0f, 1.1f, Constants.world);
                objs.add(genericBuilding);
                landmarks.add(cliffordTower);
                landmarks.add(yorkStation);
                landmarks.add(b);
                landmarks.add(yorkMinster);
                bases.add(watertower1);
                bases.add(watertower2);


                for (Landmark lm : landmarks) {
                    objs.add(lm);
                }
                for (Base base : bases) {
                    objs.add(base);
                }
            }
            //load Firetrucks
            {
                Firetruck f = new Firetruck(mapGraph, coords.get("B"), 1, game.manager);
                firetrucks.add(f);
                objs.add(f);
                f = new Firetruck(mapGraph, coords.get("C"), 2, game.manager);
                firetrucks.add(f);
                objs.add(f);
                activeFiretruck = firetrucks.get(0);

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
        int x = (int) (0.07 * width);
        int y = (int) (0.07 * height) + 50;

        handleInput();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TitilliumWeb-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        font = generator.generateFont(parameter);

        time += Math.ceil(Gdx.graphics.getDeltaTime());
        camera.update();
        // display background layout
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Constants.world.step(delta, 6, 2);

        camera.position.set(activeFiretruck.body.getPosition(), 0);
        game.shapeRenderer.setColor(Color.WHITE);
        viewport.apply();

        Gdx.gl.glViewport(
                (int) (0),
                (int) (0),
                (int) (width),
                (int) (height)
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
        for (Object i : objs) {
            if (i instanceof Base) {
                ((Base) i).update(firetrucks);
            }

            if (i instanceof Landmark) {

                ((Landmark) i).setMisiledelay(((Landmark) i).getMisiledelay() - 1f);
                if (((Landmark) i).getMisiledelay() < 0 && ((Landmark) i).invaded) {

                    for (Firetruck firetruck : firetrucks) {

                        Vector2 position1v = firetruck.body.getPosition();
                        Vector2 position2v = i.body.getPosition();
                        float v = (float) Math.sqrt(position2v.dst(position1v));

                        float xDif = position1v.x - (Math.abs(position2v.x));
                        float yDif = position1v.y - (Math.abs(position2v.y));
                        float angle = (float) atan2(yDif, xDif);


                        if (v < 25) {
                            FortressMissile p = new FortressMissile(i.body.getPosition().x, i.body.getPosition().y, kroyGame.manager.get("alienbullet.png"), (float) Math.toRadians(Math.toDegrees(angle) - 90f));
                            tempStore.add(p);
                        }
                    }
                    ((Landmark) i).setMisiledelay(40f);
                }

            }

            i.render(game.batch);
            i.update(delta);

        }

        objs.addAll(tempStore);


        for (Object i : objs) {
            i.displayHealth(game.batch);
        }
        game.batch.end();
        mB2dr.render(Constants.world, camera.combined);

        game.batch.setProjectionMatrix(hudCamera.combined);
        game.shapeRenderer.setProjectionMatrix(hudCamera.combined);
        hudViewport.apply();

        toggleActive.render(game.batch);

        game.batch.begin();

        font.setColor(Color.WHITE);
        font.getData().scale(1);


        font.draw(game.batch, "SCORE", x, y);
        font.draw(game.batch, String.format("%d", score), (float) (x + 0.15 * width), y);
        game.batch.end();

        removeDeadObjects();

        Array<Contact> contacts = Constants.world.getContactList();

        for (Contact contact : contacts) {

            Object a = (Object) contact.getFixtureA().getBody().getUserData();
            Object b = (Object) contact.getFixtureB().getBody().getUserData();
            if (a instanceof Landmark && !(b instanceof FortressMissile)) {
                ((Object) contact.getFixtureB().getBody().getUserData()).kill();
                ((Object) contact.getFixtureA().getBody().getUserData()).takeDamage(Constants.FIRETRUCK_DAMAGE);
                score += Constants.FORTRESS_DAMAGE_SCORE;
                if(((Object) contact.getFixtureA().getBody().getUserData()).getHitpoints() <= 0)
                    score += Constants.FORTRESS_DESTROY_SCORE_BOOST;
            }

            if (b instanceof FortressMissile && a instanceof Firetruck) {

                ((Object) contact.getFixtureB().getBody().getUserData()).kill();
                ((Object) contact.getFixtureA().getBody().getUserData()).takeDamage(Constants.FORTRESS_DAMAGE);
            }
        }


        if (time % 1000 == 0) {
            score += 10;
        }
        ;

        if (time > 3000) time = 0;

    }

    public void removeDeadObjects() {
        ArrayList<Object> notDeleted = new ArrayList<>();
        ArrayList<Firetruck> remainingFTs = new ArrayList<>();
        ArrayList<Landmark> remainingLMs = new ArrayList<>();
        ArrayList<Base> remainingBs = new ArrayList<>();
        int enemyCount = 0;
        for (Firetruck ft : firetrucks) {
            if (ft.getHitpoints() > 0)
                remainingFTs.add(ft);
        }
        for (Landmark lm:landmarks){
            if(lm.getHitpoints() >0) {
                remainingLMs.add(lm);
            }
        }
        for (Base bs:bases){
            if(bs.getHitpoints() >0)
                remainingBs.add(bs);
        }

        for (Object obj : objs) {
            if (obj.getHitpoints() > 0) {
                notDeleted.add(obj);
            } else {
                //if a firetruck has low health check if it is the active firetruck
                // if the fire truck is the active firetruck then switch to a remaining firetruck if possible
                if (obj instanceof Firetruck) {
                    if (activeFiretruck == obj) {
                        activeFiretruck = null;
                    }

                }
                obj.body.setUserData(null);
                Constants.world.destroyBody(obj.body);
            }
        }
        objs = notDeleted;
        firetrucks = remainingFTs;
        bases = remainingBs;
        landmarks = remainingLMs;
        // if there are no firetrucks remaining the game ends
        if (remainingFTs.size() <= 0 || remainingLMs.size() <= 0) {
            game.setScreen(new MainMenuScreen(game));
        }
        if(activeFiretruck == null)
            switchFiretruck();
    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
        this.width = width;
        viewport.update(width, height);
        hudViewport.update(width, height);
        hudCamera.update();
        camera.update();

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
    public void handleInput() {
        //firetruck movement
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            activeFiretruck.mDriveDirection = activeFiretruck.DRIVE_DIRECTION_FORWARD;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            activeFiretruck.mDriveDirection = activeFiretruck.DRIVE_DIRECTION_BACKWARD;
        } else {
            activeFiretruck.mDriveDirection = activeFiretruck.DRIVE_DIRECTION_NONE;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            activeFiretruck.mTurnDirection = activeFiretruck.TURN_DIRECTION_LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            activeFiretruck.mTurnDirection = activeFiretruck.TURN_DIRECTION_RIGHT;
        } else {
            activeFiretruck.mTurnDirection = activeFiretruck.TURN_DIRECTION_NONE;
        }
        //firetruck projectile launch
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (activeFiretruck.getFiredelay() < 0f && (activeFiretruck.getAmmo() > 0)) {
                Projectile p = activeFiretruck.createProjectile();

                objs.add(p);
                activeFiretruck.setFiredelay(Constants.FIRETRUCK_FIRE_RATE);
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
            camera.zoom = camera.zoom - 10f;
        }
        if (keycode == Input.Keys.E) {
            camera.zoom = camera.zoom + 10f;
        }
        if (keycode == Input.Keys.DOWN) {
            {
                camera.translate(0f, -10f);
            }

            //   camera.position.set((float) cameraCoords.get(xCounterCam).get(yCounterCam).get(0), (float) cameraCoords.get(xCounterCam).get(yCounterCam).get(1), 0);


        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    //changes active firetruck to next firetruck available in list
    void switchFiretruck() {
        for (int i = 0; i < firetrucks.size(); i++) {
            if (activeFiretruck.ufid == firetrucks.get(i).ufid) {
                System.out.println("ID: " + activeFiretruck.ufid);
                activeFiretruck.setActive(false);
                if (i < firetrucks.size() - 1) {
                    activeFiretruck = firetrucks.get(i + 1);

                } else {
                    activeFiretruck = firetrucks.get(0);
                }
                activeFiretruck.setActive(true);
                return;
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (toggleActive.hasBeenClicked(screenX, screenY, true)) {
            System.out.println("BUTTON PRESSED");
            switchFiretruck();
            return false;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.zoom = camera.zoom + amount;
        return
                false;
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

}
