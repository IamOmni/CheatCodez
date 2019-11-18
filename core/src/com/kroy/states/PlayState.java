package com.kroy.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {
    private Texture fireTruck;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        fireTruck = new Texture("Firetruck.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(fireTruck, 50, 50, 200, 100);
        sb.end();
    }

    @Override
    public void dispose() {
        fireTruck.dispose();
    }
}
