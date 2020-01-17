package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.screens.PlayScreen;


/**
 *  float position * screen_height
 *  float position * screen_width
 */
public class Button {
    public double x1, x2, y1, y2;
    public Sprite sprite;
    float wRatio, hRatio;
    public Button(double x1, double x2, double y1, double y2, String path){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        sprite = new Sprite(new Texture(path));
    }

    public Button(double x1, double x2, double y1, double y2, Texture tex){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        sprite = new Sprite(tex);
    }

    public void updateRatio(float wRatio, float hRatio){
        this.wRatio = wRatio;
        this.hRatio = hRatio;
    }
    public boolean hasBeenClicked(int screenX, int screenY, boolean noScale){
        screenY = Gdx.graphics.getHeight() - screenY;
        if(noScale) {
            System.out.println("Screen X: " + screenX);
            System.out.println("Screen Y: " + screenY);
            System.out.println("Button X: (" + x1 + ", " + x2 + ")");
            System.out.println("Button Y: (" + y1 + ", " + y2 + ")");
            if (
                    screenX >= (this.x1)
                            && screenX <= (this.x2)
                            && screenY >= (this.y1)
                            && screenY <= (this.y2)) {
                return true;
            }
        }
        else{
            return hasBeenClicked(screenX, screenY);
        }
        return false;
    }
    public boolean hasBeenClicked(int screenX, int screenY){
        System.out.println("Screen X: " + screenX);
        System.out.println("Screen Y: " + screenY);
        screenY = Gdx.graphics.getHeight() - screenY;
        if(
                screenX    >= (this.x1 * Gdx.graphics.getWidth())
                        && screenX <= (this.x2 * Gdx.graphics.getWidth())
                        && screenY >= (this.y1 * Gdx.graphics.getHeight())
                        && screenY <= (this.y2 * Gdx.graphics.getHeight()))
        {
            return true;
        }
        return false;
    }

    public void render(SpriteBatch sb){
        this.render(sb, true);
    }

    public void render(SpriteBatch sb, boolean autoScale){
        float x1 = (float) this.x1;
        float x2 = (float) this.x2;
        float y1 = (float) this.y1;
        float y2 = (float) this.y2;
        sb.begin();
        if(autoScale)
            sb.draw(sprite.getTexture(), x1 * Gdx.graphics.getWidth(), y1 * Gdx.graphics.getHeight(), (x2 - x1) * Gdx.graphics.getWidth(), (y2 - y1) * Gdx.graphics.getHeight());
        else
            sb.draw(sprite.getTexture(), x1, y1, x2-x1, y2-y1);
       // sb.draw(sprite.getTexture(), x1 * (wRatio), y1 * hRatio, (x2 - x1) * wRatio, (y2 - y1) * hRatio);
        sb.end();
    }

    public Sprite getSprite(){
        return sprite;
    }

}
