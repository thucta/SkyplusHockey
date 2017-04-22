package com.skyplus.hockey.state;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skyplus.hockey.Hockey;

/**
 * Created by TruongNN on 3/24/2017.
 */
public class MenuState extends State{

    private Texture bg;
    private Texture button;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture(Hockey.PATCH+"backGame.png");
        button = new Texture(Hockey.PATCH+"playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()) {
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
        cam.update();

        sb.setProjectionMatrix(cam.combined);
        sb.begin();;
        sb.draw(bg,0,0);
        sb.draw(button,cam.viewportWidth/2-button.getWidth()/2,cam.viewportHeight/2-button.getHeight()/2);
        sb.end();

    }

    @Override
    public void resize(int width, int height) {
//        gamePort.update(width,height);

    }

    @Override
    public void dispose() {
        bg.dispose();
        button.dispose();
    }
}