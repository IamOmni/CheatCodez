package com.kroy.modules;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.Constants;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import com.kroy.pathfinding.Street;
import com.kroy.screens.MainMenuScreen;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class MapLoader {

    /**
     * Load graph.txt for the Pathfinding
     * @param coords - Coords Map, connections between nodes
     * @param mapGraph - The mapgraph
     * @param path - Path of the FILE
     * @throws IOException
     */
    public static void loadGraph(Map<String,Coord> coords, MapGraph mapGraph, String path) throws IOException {

        // 4.5hrs
        // Read fle and fetch all lines

        if (!System.getProperty("user.dir").contains("assets"))
            path =  "assets/"+path;
        File file = new File(path);
        List<String> lines = Files.readAllLines(Paths.get(file.getCanonicalPath()));

        // Parse the file
        for (int i=0; i<lines.size();i++) {
            String name = lines.get(i).split(" ")[0];
            String[] connections = lines.get(i).split(":")[1].replace(" ", "").split(",");
            String s = lines.get(i).split("\\(")[1].split("\\)")[0].replace(" ", "");

            Integer x = Integer.parseInt(s.split(",")[0]);
            Integer y = Math.abs(3240-Integer.parseInt(s.split(",")[1]));

            Coord c = new Coord(x, y, name, connections );
            coords.put(name, c);
            mapGraph.addPoint(c);
        }

        // Calculate connections

        for (String key: coords.keySet()) {
            // Iterate through all connections
            for (String conKey: coords.get(key).connections) {
                // Create a conneciton between the two nodes
                mapGraph.connectPoints(coords.get(key), coords.get(conKey));

            }
        }

    }

    /**
     * extracts all objects on wall layer and adds them to world
     * @param map  map imported from Tiled
     * @param world Box2D world
     */
    public static void loadObjects(TiledMap map, World world){
        MapObjects objects = map.getLayers().get("WALLS").getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            System.out.println(rectangle.getX());
            System.out.println(rectangle.getY());
            ShapeFactory.createRectangle(
                    new Vector2(rectangle.getX()* Constants.PPM+ rectangle.getWidth()*Constants.PPM*0.5f, rectangle.getY()*Constants.PPM + rectangle.getHeight()*Constants.PPM*0.5f), // position
                    new Vector2(rectangle.getWidth()*Constants.PPM*0.5f, rectangle.getHeight()*Constants.PPM*0.5f), // size
                    BodyDef.BodyType.StaticBody, world, 1f, false);
        }

    }

    /**
     * Renders the graph on the screen REMOVED for Assessment 1 as unused
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     * @param mapGraph MapGraph
     * @param font BitmapFont
     */
    public void renderGraph(SpriteBatch sb, ShapeRenderer sr, MapGraph mapGraph, BitmapFont font) {
        for (
                Street street : mapGraph.streets) {
            street.render(sr);
        }
        // Draw all points
        for (Coord city : mapGraph.coords) {
            city.render(sr, sb, font, false);
        }
    }

}
