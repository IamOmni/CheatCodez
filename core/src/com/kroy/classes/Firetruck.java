package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.kroy.game.kroyGame;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import com.kroy.screens.PlayScreen;


import java.util.ArrayList;
import java.util.Random;
public class Firetruck extends Traversable {



    Firetruck(MapGraph mapGraph, Coord startCoord) {
        super(mapGraph, startCoord);
    }
}
