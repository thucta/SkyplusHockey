package com.skyplus.hockey.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.skyplus.hockey.Hockey;
import com.skyplus.hockey.objects.Background;
import com.skyplus.hockey.objects.BackgroundGame;
import com.skyplus.hockey.objects.Pandle;
import com.skyplus.hockey.objects.Puck;

import java.util.HashMap;


/**
 * Created by TruongNN  on 3/24/2017.
 */
public class PlayState extends State {

    private BackgroundGame background;
    private Pandle pandle_pink;
    private Pandle pandle_green;
    private Puck puck;
    private HashMap<String,Texture> backgroud ;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        initator();
    }

    private void initator() {
        backgroud = new HashMap<String, Texture>();
        backgroud.put("backgroud",new Texture(Hockey.PATCH+"backGame.png"));
        backgroud.put("bg_right",new Texture(Hockey.PATCH+"bg_right.png"));
        backgroud.put("bg_left",new Texture(Hockey.PATCH+"bg_left.png"));
        backgroud.put("bg_top",new Texture(Hockey.PATCH+"bg_top.png"));
        backgroud.put("bg_bottom",new Texture(Hockey.PATCH+"bg_bottom.png"));
        backgroud.put("bg_right_1",new Texture(Hockey.PATCH+"bg_left.png"));
        backgroud.put("bg_left_1",new Texture(Hockey.PATCH+"bg_right.png"));
        backgroud.put("bg_top_1",new Texture(Hockey.PATCH+"bg_top.png"));
        backgroud.put("bg_bottom_1",new Texture(Hockey.PATCH+"bg_bottom.png"));
        background = new BackgroundGame(Hockey.WITDH,Hockey.HEIGHT,backgroud);

        pandle_pink = new Pandle((int) (cam.viewportWidth/ 2), (int) (cam.viewportHeight - 100),new Texture(Hockey.PATCH+"pandle.png"),new Texture(Hockey.PATCH+"pandle_l.png"));
        pandle_green = new Pandle((int) (cam.viewportWidth / 2), (int) (cam.viewportHeight/2-200),new Texture(Hockey.PATCH+"pandle_1.png"),new Texture(Hockey.PATCH+"pandle_l_1.png"));
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
                pandle_pink.move(screenX, screenY);

//                if (screenY < Hockey.HEIGHT / 2) {
//                    pandle_green.move(screenX, screenY);
//                } else {
//                    pandle_pink.move(screenX, screenY);
//                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                pandle_pink.move(screenX, screenY);

//                if (screenY < Hockey.HEIGHT / 2) {
//                    pandle_green.move(screenX, screenY);
//                } else {
//                    pandle_pink.move(screenX, screenY);
//                }
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



        ckeckHit(pandle_pink,puck,background);
        ckeckHit(pandle_green,puck,background);

    }

    @Override
    public void update(float dt) {
        handleInput();
        pandle_pink.update(dt);
        pandle_green.update(dt);
        puck.update(dt);

    }


    /*
    * cac Ham trong render
    *
    * */

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        background.draw(sb);
        puck.draw(sb);
        pandle_pink.draw(sb);
        pandle_green.draw(sb);
        sb.end();
    }

    /*
    * Ham kiem tra xem cuoi cung cua update thi puck co va cham voi pandle neu co thi di chuyen puck ra xa
    *
    * */

    public void ckeckHit(Pandle pandle, Puck puck,BackgroundGame background){

        pandle.hitsPuck(puck,background);
        puck.hits(pandle);

        double distance = Math.sqrt(Vector2.dst2(puck.getX(),puck.getY(),pandle.getX(),pandle.getY()));

        if(Intersector.overlaps(pandle.getBounds(),puck.getBounds()))
        {
            Double x = (puck.getX()-pandle.getX())*(puck.getWitdh()/2+pandle.getWitdh()/2)/distance+pandle.getX();
            Double y =  (puck.getY()-pandle.getY())*(puck.getWitdh()/2 + pandle.getWitdh()/2)/distance+pandle.getY();
            puck.setPosition(x.floatValue(),y.floatValue());

        }

    }
    @Override
    public void resize(int width, int height) {
//       gamePort.update(width,height);
    }




    @Override
    public void dispose() {

    }
}
