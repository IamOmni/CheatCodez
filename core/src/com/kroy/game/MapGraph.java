package com.kroy.game;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kroy.classes.Coord;
import com.kroy.pathfinding.Heuristic;
import com.kroy.pathfinding.Street;

public class MapGraph implements IndexedGraph<Coord> {
    Heuristic heur = new Heuristic();
    public Array<Coord> coords = new Array<>();
    public Array<Street> streets = new Array<>();
    ObjectMap<Coord, Array<Connection<Coord>>> streetsMap = new ObjectMap<>();

    private int lastNodeIndex = 0;

    public void addPoint(Coord pnt){
        pnt.index = lastNodeIndex;
        lastNodeIndex++;
        coords.add(pnt);
    }

    public void connectPoints(Coord from, Coord to){
        Street street = new Street(from, to);
        if(!streetsMap.containsKey(from)){
            streetsMap.put(from, new Array<Connection<Coord>>());
        }
        streetsMap.get(from).add(street);
        streets.add(street);
    }

    public GraphPath<Coord> findPath(Coord start, Coord end){
        GraphPath<Coord> path = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(start, end, heur, path);
        return path;
    }

    @Override
    public int getIndex(Coord node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<Coord>> getConnections(Coord fromNode) {
        if(streetsMap.containsKey(fromNode)){
            return streetsMap.get(fromNode);
        }

        return new Array<>(0);
    }
}
