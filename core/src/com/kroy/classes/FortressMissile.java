package com.kroy.classes;

import com.badlogic.gdx.graphics.Texture;

public class FortressMissile extends Projectile{
    public FortressMissile(float x, float y, Texture texture, float angle){
        super(x, y, texture, angle, CollisionBits.PROJECTILE, CollisionBits.FIRETRUCK);
        body.setUserData(this);

    }
}
