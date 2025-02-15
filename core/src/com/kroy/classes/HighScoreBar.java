package com.kroy.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;

public class HighScoreBar {

    public String name;
    private String score;


    /**
     * Constructor for the HighScoreBar
     * @param record - Array list of the username and score
     */
    public HighScoreBar(ArrayList<String> record){
         name = record.get(0);
         score = record.get(1);
        if(name.length()>16)
            name = name.substring(0,10);
    }


    /**
     * Render the highscore bar
     * @param shapeRenderer - shaperenderer object
     * @param sb - SpriteBatch
     * @param width - Width of the text area
     * @param height - Height of the text area
     * @param num - amount
     */
    public void render(ShapeRenderer shapeRenderer, SpriteBatch sb, int width, int height, int num){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TitilliumWeb-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        BitmapFont font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        font.getData().scale(1);
        sb.begin();
        font.draw(sb,name,  (1 * width / 2) - (150), (num) * height / 8 + height / 2);
        font.draw(sb,score, (1 * width / 2), (num) * height / 8 + height / 2);
        sb.end();
    }


    /**
     * Get Score, return score of the highscore bar
     * @return String score
     */
    public String getScore() {
        return score;
    }

}
