package com.kroy.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.classes.Firetruck;
import com.kroy.classes.Object;
import com.kroy.game.mainKroyGame;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {
    private ArrayList<Object> objs = new ArrayList<Object>();
    private Texture background;
    private Firetruck firetruck = new Firetruck(50, 50);
    public PlayState(GameStateManager gsm) {
        super(gsm);
        objs.add(new Firetruck(50, 50));
        background = new Texture("bg-menu.png");
        cam.setToOrtho(false, mainKroyGame.WIDTH, mainKroyGame.HEIGHT);

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();

        for(Object i : objs)
            i.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(background, 0, 0, mainKroyGame.WIDTH, mainKroyGame.HEIGHT);
        //draw all objects
        for(Object i : objs) {
            sb.draw(i.model, i.getX(), i.getY(), 200, 100);
        }
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
