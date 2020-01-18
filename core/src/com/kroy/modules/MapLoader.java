package com.kroy.modules;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.game.Constants;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import com.kroy.screens.MainMenuScreen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class MapLoader {

    public static void loadGraph(Map<String,Coord> coords, MapGraph mapGraph) throws IOException {

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
}