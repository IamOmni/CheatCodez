import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.kroy.classes.Firetruck;
import com.kroy.classes.Projectile;
import com.kroy.modules.MapLoader;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import junit.textui.TestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(GameTestRunner.class)
public class FiretruckTestNew {

    private Box2DDebugRenderer mB2dr;

    private void success(String description) {
        System.out.println("Success - " + description);
    }

    private void fail(String description) {

        System.out.println("Failed - " + description);
        Assert.fail(description);
    }

    @Test
    public void test_firetruck_move_forwards() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        float oldY = engine.body.getPosition().y;
        Vector2 baseVector = new Vector2();
        baseVector.set(0, 120f);
        for (int i = 0; i < 100; i++) {
            engine.body.setLinearVelocity(engine.body.getWorldVector(baseVector.scl(80000)));
            GameTestRunner.world.step(60, 60, 2);
        }
        TimeUnit.SECONDS.sleep(3);
        float newY = engine.body.getPosition().y;
        System.out.println(String.format("%f, %f", oldY, newY));

        try {
            assertTrue(newY < oldY);
            success("firetruck has moved forwards");
        }
        catch (AssertionError a) {
            fail("firetruck has not moved forwards");
        }
    }

    @Test
    public void test_firetruck_move_backwards() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("..", "android", "assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        float oldY = engine.body.getPosition().y;
        Vector2 baseVector = new Vector2();
        baseVector.set(0, -120f);
        for (int i = 0; i < 100; i++) {
            engine.body.applyForceToCenter(engine.body.getWorldVector(baseVector.scl(800000)), true);
            GameTestRunner.world.step(60, 60, 2);
        }
        TimeUnit.SECONDS.sleep(3);
        float newY = engine.body.getPosition().y;
        System.out.println(String.format("%f, %f", oldY, newY));

        try {
            assertTrue(newY < oldY);
            success("firetruck has moved backwards");
        }
        catch (AssertionError a) {
            fail("firetruck has not moved backwards");
        }
    }

    @Test
    public void test_firetruck_turn_right() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("..", "android", "assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        engine.body.setAngularVelocity(-2.0f);
        for (int i = 0; i < 100; i++) {
            //engine.body.applyForceToCenter(engine.body.getWorldVector(baseVector.scl(800000)), true);
            GameTestRunner.world.step(60, 60, 2);
        }

        // CHECK TURNED RIGHT

        try {
            assertTrue(1 > 2);
            success("firetruck has turned right");
        }
        catch (AssertionError a) {
            fail("firetruck has not turned right");
        }
    }

    @Test
    public void test_firetruck_turn_left() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("..", "android", "assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        engine.body.setAngularVelocity(2.0f);
        for (int i = 0; i < 100; i++) {
            //engine.body.applyForceToCenter(engine.body.getWorldVector(baseVector.scl(800000)), true);
            GameTestRunner.world.step(60, 60, 2);
        }

        // CHECK TURNED LEFT

        try {
            assertTrue(1 > 2);
            success("firetruck has turned left");
        }
        catch (AssertionError a) {
            fail("firetruck has not turned left");
        }
    }

    @Test
    public void test_firetruck_can_shoot() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("..", "android", "assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        Projectile shot = engine.createProjectile();
        try {
            assertTrue(shot != null);
            success("firetruck has shot");
        }
        catch (AssertionError a) {
            fail("firetruck has not shot");
        }
    }

    @Test
    public void test_firetruck_ammo_decreases() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("..", "android", "assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        int startAmmo = engine.getAmmo();
        engine.createProjectile();
        int newAmmo = engine.getAmmo();

        try {
            assertTrue(newAmmo < startAmmo);
            success("Success - ammo decreases when shooting");
        }
        catch (AssertionError a) {
            fail("Failure - ammo does not decrease when shooting");
        }
    }

    @Test
    public void test_firetruck_ammo_refill_low() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("..", "android", "assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        engine.setAmmo(30);
        engine.refillAmmo(10);

        try {
            assertTrue(engine.getAmmo() == 40);
            success("Success - ammo refills when low");
        }
        catch (AssertionError a) {
            fail("Failure - ammo does not refill when low");
        }
    }

    @Test
    public void test_firetruck_ammo_refill_full() throws IOException, InterruptedException {
        MapGraph mapGraph = new MapGraph();
        Map<String, Coord> coords = new HashMap<>();
        TiledMap map = new TmxMapLoader().load("map-three-layer-new-walls.tmx");
        MapLoader.loadGraph(coords,mapGraph, Paths.get("..", "android", "assets","graph.txt").toAbsolutePath().toString());
        MapLoader.loadObjects(map, GameTestRunner.world);
        Firetruck engine = new Firetruck(mapGraph, coords.get("A"), 1, new Texture("Firetruck.png"));

        engine.refillAmmo(10);

        try {
            assertTrue(engine.getAmmo() == 50);
            success("Success - ammo does not refill when full");
        }
        catch (AssertionError a) {
            fail("Failure - ammo refills when full");
        }
    }
}
