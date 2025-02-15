package com.kroy.classes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Alien extends Entity {
    private int speed, damage;

    float degree;
    float v;
    float dt;
    float radius;
    double xSpeed, ySpeed;
    private float firedelay;
    private ArrayList<Projectile> projectiles;

    // LibGDX Attributes
    private boolean isAttack;
    private ShapeRenderer shapeRenderer;
    private Color color;
    private Math MathsUtils;

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }


    /**
     * Constructor for Alien
     * @param parent - parent landmark, used for spawning
     * @param manager - AssetManager for getting the graphic
     */
    public Alien(Landmark parent, AssetManager manager) {
        super(new Vector3(parent.getX(), parent.getY()-64,0),  manager.get("alien.png", Texture.class), 1f, CollisionBits.FIRETRUCK, CollisionBits.FIRETRUCK, (short) 1);

        setModel(manager.get("alien.png", Texture.class));
        xSpeed=0.7;
        ySpeed=0.7;
        radius=150;
        firedelay=0f;
        dt=0.1f;
        projectiles = new ArrayList<Projectile>();

        // Check whether alien is offensive or defensive
        double test = Math.random();
        if (test>0.5) isAttack=true;
        else isAttack=false;

        super.position.x = parent.getX()+(float)test*400;
        super.position.y = parent.getY()+(float)test*400;


    }

    public int getX(){return (int) super.position.x;};

    public int getY(){return (int) super.position.y;};

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    /**
     *If alien is defensive
     */
    private void defend(ArrayList<Firetruck> firetrucks){
        this.xSpeed=0.7;
        this.ySpeed=0.7;
        calcMove(firetrucks);

    }

    /**
     *If alien is offensive
     */
    private void attack(ArrayList<Firetruck> firetrucks) {
        this.xSpeed=0.5;
        this.ySpeed=0.5;
        calcMove(firetrucks);

    }

    /**
     * Calculate aliens move to the firetruck, wont work on multiple firetrucks.
     * @param firetrucks - Arraylist of firetrucks
     */
    private void calcMove(ArrayList<Firetruck> firetrucks) {
        for (Firetruck truck: firetrucks){
            float truckX = truck.getX();
            float truckY = truck.getY();

            float difX=truckX-super.position.x;
            float difY=truckY-super.position.y;
            float absDifX = MathsUtils.abs(truckX-super.position.x);
            float absDifY = MathsUtils.abs(truckY-super.position.y);

            degree = (float) Math.toDegrees(Math.atan2(difY, difX));
            degree= (degree < 0) ? (float) (360d + degree) : degree;

            v = (float) Math.sqrt(absDifX*absDifX + absDifY*absDifY);
            if (isAttack==false){
                if (v<this.radius) {
                    absDifX = truckX-super.position.x;
                    absDifY = truckY-super.position.y;
                    super.position.x+=(absDifX/v)*xSpeed;
                    super.position.y+=(absDifY/v)*ySpeed;
                }
            } else {
                absDifX = truckX-super.position.x;
                absDifY = truckY-super.position.y;
                super.position.x+=(absDifX/v)*xSpeed;
                super.position.y+=(absDifY/v)*ySpeed;
            }

            // Shoot if difference is lower than the radius of the alien (dependant on the type)
            if (v<radius){ shoot(); }
        }
    }

    /**
     * OLD BOX2D NOT IMPLEMENTED - MAY NOT PROPERLY WORK.
     * Shoot projectile at the firetruck
     */
    public void shoot(){

        // Clean up the projectiles
        if (projectiles.size()>4){
            if (projectiles.get(0).getDx()<5){
                ArrayList<Projectile> temp = new ArrayList<Projectile>();
                projectiles.get(0).dispose();
                for (int i=0; i<projectiles.size()-1;i++){
                    temp.add(projectiles.get(i+1));
                }
                projectiles=temp;
            }
        }
        if (firedelay<0) {
            System.out.format("%f |\n", firedelay);

            firedelay=2f;
        }
    }

    /**
     * Update Alien
     * @param firetrucks - array of firetrucks ready for attacking
     */
    public void update(ArrayList<Firetruck> firetrucks){
        firedelay-=dt;
        if (isAttack==false) { defend(firetrucks); }
        else { attack(firetrucks); }
    }

    /**
     * Render function for Alien
     * @param sb - SpriteBatch to render the sprite
     * @param firetrucks - Array of firetrucks for attacking
     */
    public void render(SpriteBatch sb, ArrayList<Firetruck> firetrucks){
        update(firetrucks);
        super.render(sb);
    }

    public int getSpeed() { return speed; }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
