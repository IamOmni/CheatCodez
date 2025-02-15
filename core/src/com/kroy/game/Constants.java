package com.kroy.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Constants {
    public static float PPM = 64;
    public static float FORTRESS_DAMAGE = 1f;
    public static float FIRETRUCK_FIRE_RATE = 50f;
    public static float FIRETRUCK_DAMAGE = 10f;
    public static int FORTRESS_DAMAGE_SCORE = 10;
    public static int FORTRESS_DESTROY_SCORE_BOOST = 100;
    public static int REFILL_RADIUS = 25;
    public static int FORTRESS_FIRE_RADIUS = 25;
    public static World world = new World(new Vector2(0,0),true);
}
