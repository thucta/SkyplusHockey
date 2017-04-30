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
 * Created by THUC UYEN on 29-Apr-17.
 */
public class ModeState extends State implements Screen{


    private Texture bg,checkMode, button_Mode1, button_Mode2, button_Mode3, button_Mode4, button_OK;

    private Rectangle createBoundsMode1 , createBoundsMode2, createBoundsMode3, createBoundsMode4, createBoundsOK;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private float width,height;
    private int check;



    public ModeState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture(Hockey.PATCH+"backGame.png");
        checkMode = new Texture(Hockey.PATCH+"checkMode.png");
        button_Mode1 = new Texture(Hockey.PATCH+"mode1.png");
        button_Mode2 = new Texture(Hockey.PATCH+"mode2.png");
        button_Mode3 = new Texture(Hockey.PATCH+"mode3.png");
        button_Mode4 = new Texture(Hockey.PATCH+"mode4.png");
        button_OK = new Texture(Hockey.PATCH+"buttonOK.png");







        batch = new SpriteBatch();
        cam = new OrthographicCamera(Hockey.WITDH, Hockey.HEIGHT);
        cam.setToOrtho(true, Hockey.WITDH, Hockey.HEIGHT);
        batch.setProjectionMatrix(cam.combined);
        width = cam.viewportWidth/3-button_Mode1.getWidth()/2;
        createBoundsMode1 = new Rectangle(width,cam.viewportHeight/6-button_Mode1.getHeight()/2,
                button_Mode1.getWidth(), button_Mode1.getHeight());
        createBoundsMode2 = new Rectangle(width,
                (cam.viewportHeight/3)-(button_Mode2.getHeight()/2),
                button_Mode2.getWidth(), button_Mode2.getHeight());
        createBoundsMode3 = new Rectangle(width,cam.viewportHeight/2-button_Mode3.getHeight()/2,
                button_Mode3.getWidth(), button_Mode3.getHeight());
        createBoundsMode4 = new Rectangle(width,cam.viewportHeight*2/3-button_Mode4.getHeight()/2,
                button_Mode4.getWidth(), button_Mode4.getHeight());
        createBoundsOK = new Rectangle(cam.viewportWidth/2-button_OK.getWidth()/2,cam.viewportHeight*5/6-button_OK.getHeight()/2,
                button_OK.getWidth(), button_OK.getHeight());
        height = cam.viewportHeight;
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
                if (createBoundsMode1.contains(screenX,screenY)) {
                    height = cam.viewportHeight/6-button_Mode1.getHeight()/2;
                    check = 1;
                }
                else if (createBoundsMode2.contains(screenX,screenY)) {
                    height = cam.viewportHeight/3- button_Mode2.getHeight()/2;
                    check = 2;
                }
                else if (createBoundsMode3.contains(screenX,screenY)) {
                    height = cam.viewportHeight/2-button_Mode3.getHeight()/2;
                    check = 3;
                }
                else if (createBoundsMode4.contains(screenX,screenY)){
                    height = cam.viewportHeight*2/3-button_Mode4.getHeight()/2;
                    check = 4;
                }
                if(createBoundsOK.contains(screenX,screenY)){
                    switch (check){
                        case 1:
                            gsm.set(new PlayState(gsm));
                            dispose();
                            break;
                        case 2:
                            gsm.set(new PlayState(gsm));
                            dispose();
                            break;
                        case 3:
                            gsm.set(new PlayState(gsm));
                            dispose();
                            break;
                        case 4:
                            gsm.set(new JoinGameState(gsm));
                            dispose();
                            break;
                        default:
                            break;
                    }
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
        sb.draw(checkMode,cam.viewportWidth*4/5,height);
        sb.draw(button_Mode1,width,cam.viewportHeight/6-button_Mode1.getHeight()/2 );
        sb.draw(button_Mode2,width,cam.viewportHeight/3-button_Mode2.getHeight()/2);
        sb.draw(button_Mode3,width,cam.viewportHeight/2-button_Mode3.getHeight()/2);
        sb.draw(button_Mode4,width,cam.viewportHeight*2/3-button_Mode4.getHeight()/2);
        sb.draw(button_OK,cam.viewportWidth/2-button_OK.getWidth()/2,cam.viewportHeight*5/6-button_OK.getHeight()/2);

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
        button_Mode1.dispose();
        button_Mode2.dispose();
        button_Mode3.dispose();
        button_Mode4.dispose();
    }

}