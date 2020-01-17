package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.kroy.screens.PlayScreen;

import java.util.ArrayList;

public class Alien extends Entity {
    private int speed, damage;

    float x, y, degree,  rotationSpeed, dx, dy, radians, v, dt, radius;
    double xSpeed, ySpeed;
    private Math MathsUtils;
    private float firedelay;
    private ArrayList<Projectile> projectiles;

    // LibGDX Attributes
    private Texture texture;
    private Sprite sprite;
    private boolean isAttack;
    private ShapeRenderer shapeRenderer;
    private Color color;

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }




    public Alien(Landmark parent, String direction, AssetManager manager) {
        super(new Vector3(parent.getX(), parent.getY()-64,0),  manager.get("alien.png", Texture.class), 1f);
        //super.setModel(manager.get("alien.png", Texture.class));
        shapeRenderer = new ShapeRenderer();
        setModel(manager.get("alien.png", Texture.class));
        xSpeed=0.7;
        ySpeed=0.7;
        radius=150;
        firedelay=0f;
        dt=0.1f;
        projectiles = new ArrayList<Projectile>();

        double test = Math.random();
        if (test>0.5) isAttack=true;
        else isAttack=false;

        isAttack=true;
        System.out.println(test);
        System.out.println(isAttack);

        if (test>0.5) {
            isAttack=true;
            color = new Color(1f, 0, 0, 0.5f);
        }
        else {
            isAttack=false;
            this.radius=250;
            color = new Color(0, 1f, 0, 0.5f);
        }
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

    private void defend(ArrayList<Firetruck> firetrucks){
        this.xSpeed=0.7;
        this.ySpeed=0.7;
        calcMove(firetrucks);

    }

    private void attack(ArrayList<Firetruck> firetrucks) {
        this.xSpeed=0.5;
        this.ySpeed=0.5;
        calcMove(firetrucks);

    }

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

            if (v<radius){ shoot(); }
        }
    }

    public ArrayList<Projectile> getProjectiles(){ return projectiles; }

    public void setFiredelay(float firedelay) {
        this.firedelay = firedelay;
    }

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
            Projectile p = new Projectile(x, x, y, degree);
            projectiles.add(p);
            firedelay=2f;
        }
    }

    public void update(ArrayList<Firetruck> firetrucks){
        firedelay-=dt;
        if (isAttack==false) { defend(firetrucks); }
        else { attack(firetrucks); }
    }


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
