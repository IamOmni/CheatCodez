package com.kroy.modules;

import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphLoader {
    public static void loadGraph(Map<String, Coord> Coords, MapGraph mapGraph) throws IOException {


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
}
