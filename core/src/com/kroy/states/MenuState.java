package com.kroy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.game.mainKroyGame;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Texture OptionBtn;
    public MenuState(GameStateManager gsm){
        super(gsm);
        background = new Texture("bg-menu.png");
        playBtn = new Texture("Play_Button.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){
            System.out.println("input detected");
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, mainKroyGame.WIDTH, mainKroyGame.HEIGHT);
        sb.draw(playBtn, mainKroyGame.WIDTH/2 - playBtn.getWidth()/2, mainKroyGame.HEIGHT/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }


}
