package com.kroy.classes;

import com.badlogic.gdx.assets.AssetManager;

public class UFO extends Alien {
    private Object target;

    public UFO(Landmark parent, String direction, AssetManager manager) {
        super(parent, direction,manager);
    }
}
