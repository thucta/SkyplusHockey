package com.skyplus.hockey.state;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skyplus.hockey.Hockey;

/**
 * Created by THUC UYEN on 29-Apr-17.
 */
public class PlayPPState extends State implements Screen{


    private Texture bg;
    private Sprite button_Local,button_Bluetooth;

    private Rectangle createBoundsLoacal, createBoundsBluetooth;
    private OrthographicCamera cam;
    private SpriteBatch batch;



    public PlayPPState(GameStateManager gsm) {
        super(gsm);
        Gdx.app.log("here","MenuState");
        bg = new Texture(Hockey.PATCH+"backGame.png");
        button_Local = new Sprite(new Texture(Hockey.PATCH+"buttonLocal.png"));
        button_Bluetooth = new Sprite(new Texture(Hockey.PATCH+"buttonBluetooth.png"));

        button_Local.setPosition(Hockey.WITDH/2-button_Local.getWidth()/2,Hockey.HEIGHT/3-button_Local.getHeight()/2);
        button_Bluetooth.setPosition(Hockey.WITDH/2-button_Bluetooth.getWidth()/2,Hockey.HEIGHT*2/3-button_Bluetooth.getHeight()/2);






        batch = new SpriteBatch();
        cam = new OrthographicCamera(Hockey.WITDH, Hockey.HEIGHT);
        cam.setToOrtho(true, Hockey.WITDH, Hockey.HEIGHT);
        batch.setProjectionMatrix(cam.combined);


        createBoundsLoacal = new Rectangle((cam.viewportWidth - button_Local.getWidth())/2,
                (cam.viewportHeight/3)-(button_Local.getHeight()/2),
                button_Local.getWidth(), button_Local.getHeight());

        createBoundsBluetooth = new Rectangle(cam.viewportWidth/2-button_Bluetooth.getWidth()/2,cam.viewportHeight*2/3-button_Bluetooth.getHeight()/2,
                button_Bluetooth.getWidth(), button_Bluetooth.getHeight());



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
                if (createBoundsLoacal.contains(screenX,screenY)) {
                    gsm.set(new ModeState(gsm));
                    dispose();
                }
                else if (createBoundsBluetooth.contains(screenX,screenY)) {
                    gsm.set(new JoinGameState(gsm));
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
        button_Local.draw(sb);
        button_Bluetooth.draw(sb);

        sb.end();

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

    @Override
    public void dispose() {
        bg.dispose();
    }

}