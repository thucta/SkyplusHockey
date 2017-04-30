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
import com.badlogic.gdx.math.Vector3;
import com.skyplus.hockey.Hockey;

/**
 * Created by TruongNN on 3/24/2017.
 */
public class MenuState extends State implements Screen{


    private Texture bg;
    private Sprite button_PC,button_PP,button_ST;

    private Rectangle createBoundsPC , createBoundsPP, createBoundsST;
    private OrthographicCamera cam;
    private SpriteBatch batch;



    public MenuState(GameStateManager gsm) {
        super(gsm);
        Gdx.app.log("here","MenuState");
        bg = new Texture(Hockey.PATCH+"backGame.png");
        button_PC = new Sprite(new Texture(Hockey.PATCH+"buttonPC.png"));
        button_PP = new Sprite(new Texture(Hockey.PATCH+"buttonPP.png"));
        button_ST = new Sprite(new Texture(Hockey.PATCH+"buttonST.png"));

        button_PP.setPosition(Hockey.WITDH/2-button_PP.getWidth()/2,Hockey.HEIGHT/2-button_PP.getHeight()*3 );
        button_PC.setPosition(Hockey.WITDH/2-button_PC.getWidth()/2,Hockey.HEIGHT/2-button_PC.getHeight()/2);
        button_ST.setPosition(Hockey.WITDH/2-button_ST.getWidth()/2,Hockey.HEIGHT/2+button_ST.getHeight()*2);






        batch = new SpriteBatch();
        cam = new OrthographicCamera(Hockey.WITDH, Hockey.HEIGHT);
        cam.setToOrtho(true, Hockey.WITDH, Hockey.HEIGHT);
        batch.setProjectionMatrix(cam.combined);

        createBoundsPP = new Rectangle(cam.viewportWidth/2-button_PP.getWidth()/2,cam.viewportHeight/2-button_PP.getHeight()*3,
                button_PP.getWidth(), button_PP.getHeight());
        createBoundsPC = new Rectangle((cam.viewportWidth - button_PC.getWidth())/2,
                (cam.viewportHeight/2)-(button_PC.getHeight()/2),
                button_PC.getWidth(), button_PC.getHeight());

        createBoundsST = new Rectangle(cam.viewportWidth/2-button_ST.getWidth()/2,cam.viewportHeight/2+button_ST.getHeight()*2,
                button_ST.getWidth(), button_ST.getHeight());



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
                if (createBoundsPP.contains(screenX,screenY)) {
                    gsm.set(new PlayPPState(gsm));
                    dispose();
                }
                else if (createBoundsPC.contains(screenX,screenY)) {
                    gsm.set(new ModeState(gsm));
                    dispose();
                }
                else if (createBoundsST.contains(screenX,screenY)) {
                    gsm.set(new SettingState(gsm));
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
        button_PP.draw(sb);
        button_ST.draw(sb);
        button_PC.draw(sb);

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