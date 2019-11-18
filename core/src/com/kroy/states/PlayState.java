package com.kroy.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.classes.Firetruck;
import com.kroy.game.mainKroyGame;

public class PlayState extends State {
    private Firetruck fireTruck;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        fireTruck = new Firetruck(50, 50);
        cam.setToOrtho(false, mainKroyGame.WIDTH/2, mainKroyGame.HEIGHT/2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        fireTruck.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(fireTruck.model, fireTruck.getX(), fireTruck.getY(), 200, 100);
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
