package com.kroy.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.classes.Sector;
import com.kroy.game.kroyGame;

import java.awt.*;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    com.badlogic.gdx.scenes.scene2d.ui.Label countdownLabel;
    com.badlogic.gdx.scenes.scene2d.ui.Label scoreLabel;
    com.badlogic.gdx.scenes.scene2d.ui.Label timeLabel;
    com.badlogic.gdx.scenes.scene2d.ui.Label levelLabel;
    com.badlogic.gdx.scenes.scene2d.ui.Label worldLabel;
    com.badlogic.gdx.scenes.scene2d.ui.Label ftLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(kroyGame.WIDTH, kroyGame.HEIGHT,  new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label(String.format("%03d", worldTimer), new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        scoreLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label(String.format("%03d", score), new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        timeLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("TIME", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        levelLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("1-1", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        worldLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("WORLD", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        ftLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("FireTruck", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));

        table.add(ftLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expand();

        stage.addActor(table);
    }
}