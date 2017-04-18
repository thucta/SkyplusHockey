package com.skyplus.hockey.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skyplus.hockey.Hockey;
import com.skyplus.hockey.objects.Background;
import com.skyplus.hockey.objects.Pandle;
import com.skyplus.hockey.objects.Puck;


/**
 * Created by TruongNN  on 3/24/2017.
 */
public class PlayState extends State {

    private Background background;
    private Pandle pandle_pink;
    private Pandle pandle_green;
    private Puck puck;
    private float mouseX, mouseY;  // toa do khi nhan vao mang hinh

    public PlayState(GameStateManager gsm) {
        super(gsm);
        background = new Background(0,0,Hockey.WITDH,Hockey.HEIGHT,new Texture(Hockey.PATCH+"backGame.png"),
                                    new Texture(Hockey.PATCH+"bg_right.png"),new Texture(Hockey.PATCH+"bg_left.png"),
                                    new Texture(Hockey.PATCH+"bg_top.png"),new Texture(Hockey.PATCH+"bg_bottom.png"));


        pandle_pink = new Pandle(Hockey.WITDH / 2, (int) (cam.viewportHeight - 100),new Texture(Hockey.PATCH+"pandle.png"),new Texture(Hockey.PATCH+"pandle_l.png"),background);
        pandle_green = new Pandle(Hockey.WITDH / 2, 100,new Texture(Hockey.PATCH+"pandle_1.png"),new Texture(Hockey.PATCH+"pandle_l_1.png"),background);
        puck = new Puck((int) cam.viewportWidth / 2, (int) cam.viewportHeight / 2);

    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(new InputProcessor() {

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenY < Hockey.HEIGHT / 2) {
                    pandle_green.move(screenX, screenY);
                } else {
                    pandle_pink.move(screenX, screenY);
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                //  Gdx.app.log("mouse",screenX + " " +screenY);
                if (screenY < Hockey.HEIGHT / 2) {
                    pandle_green.move(screenX, screenY);
                } else {
                    pandle_pink.move(screenX, screenY);
                }
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });

    }

    @Override
    public void update(float dt) {
        handleInput();
        pandle_pink.update(dt);
        pandle_green.update(dt);


    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        background.draw(sb);
        sb.draw(pandle_pink.getTexture(), pandle_pink.getX(), pandle_pink.getY());
        sb.draw(pandle_green.getTexture(), pandle_green.getX(), pandle_green.getY());
        sb.draw(puck.getTexture(), puck.getX(), puck.getY());
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
//       gamePort.update(width,height);
    }

    @Override
    public void dispose() {

    }
}
