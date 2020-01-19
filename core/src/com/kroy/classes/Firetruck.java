package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.kroy.game.Constants;
import com.kroy.game.kroyGame;
import com.kroy.pathfinding.Coord;
import com.kroy.pathfinding.MapGraph;
import com.kroy.screens.PlayScreen;


import java.util.ArrayList;
import java.util.Random;
public class Firetruck extends Entity {
    private boolean active;

    // Our attributes
    float x,y,firedelay;
    double xSpeed, ySpeed;

    private int ammo;
    private int ammoCap;
    private ArrayList<Projectile> bullets;

    public int ufid;
    public static final int DRIVE_DIRECTION_NONE = 0;
    public static final int DRIVE_DIRECTION_FORWARD = 1;
    public static final int DRIVE_DIRECTION_BACKWARD = 2;

    public static final int TURN_DIRECTION_NONE = 0;
    public static final int TURN_DIRECTION_LEFT = 1;
    public static final int TURN_DIRECTION_RIGHT = 2;
    public int mDriveDirection = DRIVE_DIRECTION_NONE;
    public int mTurnDirection = TURN_DIRECTION_NONE;
    /**
     * Constructor for Firetruck object
     * @param mapGraph - MapGraph object for traversal
     * @param start - Starting coord for the traversal
     */
    public Firetruck(MapGraph mapGraph, Coord start, int ufid, AssetManager manager){
        super(mapGraph, start, manager.get("Firetruck.png", Texture.class), 0.15f, CollisionBits.FIRETRUCK, (short) (CollisionBits.WALL ), (short) 1);
        this.ufid = ufid;
        hitpointCap =  new Random().nextInt(20) + 5;
        hitpointCap *= 5;
        setHitpoints(hitpointCap);
        this.scale = 0.15f;
        setModel(manager.get("Firetruck.png", Texture.class));

        body.setUserData(this);

        bullets = new ArrayList<Projectile>();
        firedelay = 0f;
        ammoCap=50;
        ammo=ammoCap;
    }
    public Firetruck(MapGraph mapGraph, Coord start, int ufid, Texture texture){
        super(mapGraph, start, texture, 0.15f, CollisionBits.FIRETRUCK, (short) (CollisionBits.WALL ), (short) 1);
        this.ufid = ufid;
        hitpointCap =  new Random().nextInt(20) + 5;
        hitpointCap *= 5;
        setHitpoints(hitpointCap);
        this.scale = 0.15f;
        setModel(texture);

        body.setUserData(this);
        xSpeed = 0;
        ySpeed = 0;


        bullets = new ArrayList<Projectile>();
        firedelay = 0f;
        ammoCap=50;
        ammo=ammoCap;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    /**
     * Fetches all bullets from specific firetruck
     * @return bullets - ArrayList<Projectile>
     */
    public ArrayList<Projectile> getBullets() {
        return bullets;
    }

    public int getAmmo(){
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void refillAmmo(int amt){
        if(ammo < ammoCap)
            ammo += amt;
    }

    /**
     * Updater method for class player
     * @param dt - Delta time
     */
    public void update(float dt){
        Vector2 baseVector = new Vector2();
        if(mTurnDirection == TURN_DIRECTION_RIGHT){
            body.setAngularVelocity(-2.0f);
        }
        else if(mTurnDirection == TURN_DIRECTION_LEFT){
            body.setAngularVelocity(2.0f);
        }
        else{
            body.setAngularVelocity(0.0f);
        }

        if(mDriveDirection == DRIVE_DIRECTION_FORWARD) {
            baseVector.set(0, 120f);
        } else if (mDriveDirection== DRIVE_DIRECTION_BACKWARD){
            baseVector.set(0,-120f);
        }

        if (!baseVector.isZero()){
            System.out.println(baseVector.toString());
            //body.applyForceToCenter(body.getWorldVector(baseVector.scl(80000)), true);
            body.setLinearVelocity(body.getWorldVector(baseVector.scl(8000000)));
            System.out.println(body.getPosition().toString());
        }

        firedelay-=dt;

    }
    public int getX() { return (int) this.x; }
    public int getY() { return (int) this.y; }
    public float getFiredelay() {
        return firedelay;
    }

    public void setFiredelay(float firedelay) {
        this.firedelay = firedelay;
    }

    public void render(SpriteBatch batch){

        update(1f);
        super.render(batch);
    }

    public Projectile createProjectile(){
        ammo -= 1;
        return new Projectile(body.getPosition().x, body.getPosition().y, kroyGame.manager.get("bullet.png"), body.getAngle());
    }

    @Override
    public void displayHealth(SpriteBatch sb){

            PlayScreen.font.setColor(Color.RED);
            PlayScreen.font.getData().scale(0.25f);

            String text = String.format("%d HP / %d WP", getHitpoints(), getAmmo());
            final GlyphLayout layout = new GlyphLayout(PlayScreen.font, text);
            PlayScreen.font.draw(sb, text, body.getPosition().x - layout.width/2, body.getPosition().y + 1 * getOffsets().y + PlayScreen.font.getLineHeight());


    }

}
