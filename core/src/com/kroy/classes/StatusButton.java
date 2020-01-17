package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class StatusButton {
    private Vector2 position = new Vector2();

    private Button attack;
    private Button defend;
    private Texture buttonTexture;
    private float waterRat = 1f, healthRat = 1f;
    private Rectangle waterIndicator;
    private String name;
    private BitmapFont font= new BitmapFont();
    private Rectangle healthIndicator;
    // 282 x 110
    final double WIDTH = 0.21 * Gdx.graphics.getWidth();
    final double HEIGHT = 0.1925 * Gdx.graphics.getHeight();
    final int scrWidth = Gdx.graphics.getWidth();
    final int scrHeight = Gdx.graphics.getHeight();

    public Button getAttack(){
        return attack;
    }
    public Button getDefend(){
        return defend;
    }

    /*
    <----( x )----> |                       |
                    |                       |
    (0.7)   b       |-----------------------|
                    | ===================== |
                    | ===================== |
                    |          n-1          |
                    |   _______   _______   |
                    |  |___A___| |___D___|  |
  (n + 1) * HEIGHT  |-----------------------|
                    | ===================== |
                    | ===================== |
                    |           n           |  <---- nth status button set
                    |   _______   _______   |
                    |  |___A___| |___D___|  |
      (n) * HEIGHT  |-----------------------|
                    |                       |

     */
    //n start from 1
    public StatusButton(int n, AssetManager manager){
        n = 5 - n;
        int x   = (int)(0.07 * Gdx.graphics.getWidth());
        int bottom_y   = (  n-1  )  * (int)HEIGHT + (int)(0.07 * Gdx.graphics.getHeight());
        int top_y      = (n)  * (int)HEIGHT + (int)(0.07 * Gdx.graphics.getHeight());
        name = "Firetruck " + (n - 3);
        buttonTexture = manager.get("button.png", Texture.class);
        //button texture: 448 x 68
        attack = new Button(
                x + 0.0067          * scrWidth,
                x + 0.0867          * scrWidth,
                bottom_y + 0.01     * scrHeight,
                bottom_y + 0.0525    * scrHeight,
                buttonTexture);
        defend = new Button(
                x + ( 0.21/2 + 0.0067)    * scrWidth,
                x + ( 0.21/2 + 0.0867 )   * scrWidth,
                bottom_y + 0.01      * scrHeight,
                bottom_y + 0.0525    * scrHeight,
                buttonTexture);

        waterIndicator = new Rectangle(
                x       + (int)(0.005 * scrWidth),
                top_y   - (0.05f * scrHeight) ,
                (float) WIDTH,
                10);
        healthIndicator = new Rectangle(
                x       + (int)(0.005 * scrWidth),
                top_y   - (0.05f * scrHeight) - 20 ,
                (float) WIDTH,
                10);

        position.x = (float)(healthIndicator.x + attack.x1)/2;
        position.y = (float)(healthIndicator.y + attack.y2)/2;


        font.setColor(Color.WHITE);
    }
    public void update(float hR, float wR){
        healthRat = hR;
        waterRat = wR;
        System.out.println("hR: " + hR);
        System.out.println("wR: " + wR);
    }
    public void render(SpriteBatch sb, ShapeRenderer sr){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);
        sr.rect(healthIndicator.x, healthIndicator.y, healthRat * healthIndicator.width, healthIndicator.height);
        sr.setColor(Color.BLUE);
        sr.rect(waterIndicator.x, waterIndicator.y, waterRat * waterIndicator.width, waterIndicator.height);
        sb.setColor(Color.WHITE);
        sr.end();
        attack.render(sb);
        defend.render(sb);

//        System.out.println("Heart Indicator:");
//        System.out.println(healthIndicator.x + ", " + healthIndicator.y + ", " + healthIndicator.width + ", " + healthIndicator.height);
//        System.out.println("Water Indicator:");
//        System.out.println(waterIndicator.x + ", " + waterIndicator.y + ", " + waterIndicator.width + ", " +  waterIndicator.height);
//
//
//
//        System.out.println("Attack Button Indicator:");
//        System.out.println(attack.x1 + ", " + attack.y1 + ", " + attack.x2 + ", " +  attack.y2);
//        System.out.println("Defend Button Indicator:");
//        System.out.println(defend.x1 + ", " + defend.y1 + ", " + defend.x2 + ", " +  defend.y2);


        sb.begin();
        // sb.draw(new Texture("button.png"), (int)(attack.x1), (int)(attack.y1), (int)((attack.x2 - attack.x1))  , (int)(Math.abs(attack.y2 - attack.y1)));

        font.getData().setScale(2);
        font.draw(sb, name, (int)position.x, position.y);
        font.getData().setScale(1);
        font.draw(sb, "Active", (int)((attack.x1 + attack.x2)/2 - (attack.x2 - attack.x1)/8),(int)(attack.y1 + attack.y2)/2);
        font.draw(sb, "Defend", (int)((defend.x1 + defend.x2)/2 - (defend.x2 - defend.x1)/8),(int)(defend.y1 + defend.y2)/2);

        sb.end();
    }
}
