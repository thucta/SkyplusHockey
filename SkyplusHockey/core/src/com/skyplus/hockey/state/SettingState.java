package com.skyplus.hockey.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.skyplus.hockey.Hockey;

/**
 * Created by THUC UYEN on 20-Apr-17.
 */

public class SettingState extends State implements Screen {
    private Texture bg;
    private Texture check;
    private Texture uncheck;
    private Texture button_ST;
    private Texture textSound;


    private Vector3 touchpoint;


    private Rectangle createBoundsCheck,createBoundsST;
    private OrthographicCamera cam;
    private SpriteBatch batch;


    public SettingState(GameStateManager gsm) {
        super(gsm);
        Gdx.app.log("here","SettingState");
        bg = new Texture(Hockey.PATCH + "backGame.png");
        check = new Texture(Hockey.PATCH+"check.png");
        uncheck = new Texture(Hockey.PATCH+"uncheck.png");
        button_ST = new Texture(Hockey.PATCH+"buttonST.png");
        textSound = new Texture(Hockey.PATCH+"soundText.png");


        batch = new SpriteBatch();
        cam = new OrthographicCamera(Hockey.WITDH, Hockey.HEIGHT);
        cam.setToOrtho(true, Hockey.WITDH, Hockey.HEIGHT);
        batch.setProjectionMatrix(cam.combined);
        createBoundsCheck = new Rectangle(cam.viewportWidth*4/5,
                (cam.viewportHeight/2)-(check.getHeight()/2),
                check.getWidth(), check.getHeight());
        createBoundsST = new Rectangle(cam.viewportWidth/2-button_ST.getWidth()/2,cam.viewportHeight/2+button_ST.getHeight()*2,
                button_ST.getWidth(), button_ST.getHeight());

        touchpoint = new Vector3();
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
                if (createBoundsCheck.contains(screenX,screenY)) {
                    if(Hockey.flagCheck) {
                        Hockey.flagCheck = false;
                        Hockey.sound.pause();
                    }else {
                        Hockey.flagCheck = true;
                        Hockey.sound.play();
                    }
                }
                if(createBoundsST.contains(screenX, screenY)){
                    gsm.set(new MenuState(gsm));
                    dispose();
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
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
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg,0,0, Hockey.WITDH, Hockey.HEIGHT);
        sb.draw(button_ST,cam.viewportWidth/2-button_ST.getWidth()/2,cam.viewportHeight/2+button_ST.getHeight()*2);
        sb.draw(textSound,cam.viewportWidth/5,cam.viewportHeight/2-check.getHeight()/2);
        if(Hockey.flagCheck){
            sb.draw(check,cam.viewportWidth*4/5,cam.viewportHeight/2-check.getHeight()/2);
        }else {
            sb.draw(uncheck,cam.viewportWidth*4/5,cam.viewportHeight/2-uncheck.getHeight()/2);
        }
        sb.end();


    }

    @Override
    public void dispose() {
        button_ST.dispose();
        bg.dispose();
        uncheck.dispose();
        check.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
