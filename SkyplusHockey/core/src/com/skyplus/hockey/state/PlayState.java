package com.skyplus.hockey.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skyplus.hockey.Hockey;
import com.skyplus.hockey.config.Config;
import com.skyplus.hockey.objects.BackgroundGame;
import com.skyplus.hockey.objects.Pandle;
import com.skyplus.hockey.objects.Puck;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by TruongNN  on 3/24/2017.
 */
public class PlayState extends State implements Screen{

    private BackgroundGame background;
    private Pandle pandle_pink;
    private Pandle pandle_green;
    private Puck puck;

    //pause
    private Texture buttonPause;
    private Circle boundsPause;
    private Sprite button_Resume,button_NewGame, button_Exit;
    private boolean GAME_PAUSED = false;
    private Sprite sprite;


    public static Map<Integer, Sprite> spriteMap;
    private float scoreVerticalOffset = 20, scoreHorizontalOffset = 20;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        initator();
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
                if(GAME_PAUSED == false) {
                    if (boundsPause.contains(screenX, screenY) == true) {
                        pause();
                    } else {
                        // Ham di chuyen
                        move(screenX, screenY);

                    }
                }else {

                    if (button_Resume.getBoundingRectangle().contains(screenX, screenY)) {
                        gsm.set(new WinState(gsm));
                        dispose();
                    }
                    if(button_NewGame.getBoundingRectangle().contains(screenX, screenY)){
                        gsm.set(new PlayState(gsm));
                        dispose();
                    }
                    if(button_Exit.getBoundingRectangle().contains(screenX, screenY)){
                        gsm.set(new MenuState(gsm));
                        dispose();
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

                // ham di chuyen
                move(screenX,screenY);

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



        ckeckHit(pandle_green,puck,background);
        ckeckHit(pandle_pink,puck,background);

    }




    @Override
    public void update(float dt) {
        if(!GAME_PAUSED){
            handleInput();
            pandle_pink.update(dt);
            pandle_green.update(dt);
            puck.update(dt);
            goalScore();  // kiem tra xem co ghi duoc diem khong
        }
    }

    private void initator() {
        HashMap<String,Texture>  backgroud = new HashMap<String, Texture>();
        backgroud.put(Config.BACKGROUND,new Texture(Hockey.PATCH+"backGame.png"));

        backgroud.put(Config.EDGE_RIGHT_TOP,new Texture(Hockey.PATCH+"bg_right.png"));
        backgroud.put(Config.EDGE_RIGHT_TOP_LIGHT,new Texture(Hockey.PATCH+"bg_right_l.png"));

        backgroud.put(Config.EDGE_RIGHT_BOTTOM,new Texture(Hockey.PATCH+"bg_right.png"));
        backgroud.put(Config.EDGE_RIGHT_BOTTOM_LIGHT,new Texture(Hockey.PATCH+"bg_right_l.png"));

        backgroud.put(Config.EDGE_LEFT_TOP,new Texture(Hockey.PATCH+"bg_right.png"));
        backgroud.put(Config.EDGE_LEFT_TOP_LIGHT,new Texture(Hockey.PATCH+"bg_right_l.png"));

        backgroud.put(Config.EDGE_LEFT_BOTTOM,new Texture(Hockey.PATCH+"bg_right.png"));
        backgroud.put(Config.EDGE_LEFT_BOTTOM_LIGHT,new Texture(Hockey.PATCH+"bg_right_l.png"));



        backgroud.put(Config.EDGE_TOP_RIGHT,new Texture(Hockey.PATCH+"bg_top.png"));
        backgroud.put(Config.EDGE_TOP_RIGHT_LIGHT,new Texture(Hockey.PATCH+"bg_top_light.png"));
        backgroud.put(Config.EDGE_TOP_LEFT,new Texture(Hockey.PATCH+"bg_top.png"));
        backgroud.put(Config.EDGE_TOP_LEFT_LIGHT,new Texture(Hockey.PATCH+"bg_top_light.png"));

        backgroud.put(Config.EDGE_BOTTOM_RIGHT,new Texture(Hockey.PATCH+"bg_top.png"));
        backgroud.put(Config.EDGE_BOTTOM_RIGHT_LIGHT,new Texture(Hockey.PATCH+"bg_top_light.png"));
        backgroud.put(Config.EDGE_BOTTOM_LEFT,new Texture(Hockey.PATCH+"bg_top.png"));
        backgroud.put(Config.EDGE_BOTTOM_LEFT_LIGHT,new Texture(Hockey.PATCH+"bg_top_light.png"));

        background = new BackgroundGame(Hockey.WITDH,Hockey.HEIGHT,backgroud);

        //scrore
        spriteMap = new HashMap<Integer, Sprite>();
        for (int i = 0; i < 10; i++) {
            Sprite sprite = new Sprite(new Texture(Hockey.PATCH+i + ".png"));
            sprite.setSize(sprite.getWidth(), sprite.getHeight());
            spriteMap.put(i, sprite);
        }

        pandle_pink = new Pandle(0,0,new Texture(Hockey.PATCH+"pandle.png"),new Texture(Hockey.PATCH+"pandle_l.png"),spriteMap);
        pandle_pink.setPosition(Hockey.WITDH/2,Hockey.HEIGHT- pandle_pink.getHeight());
        pandle_green = new Pandle(0, 0,new Texture(Hockey.PATCH+"pandle_1.png"),new Texture(Hockey.PATCH+"pandle_l_1.png"),spriteMap);
        pandle_green.setPosition(Hockey.WITDH/2,pandle_green.getHeight());
        puck = new Puck((int) cam.viewportWidth / 2, (int) cam.viewportHeight / 2,background.getMapEdge());

        //pause
        buttonPause = new Texture(Hockey.PATCH + "buttonPause.png");
        boundsPause = new Circle(cam.viewportWidth-buttonPause.getWidth()/2,cam.viewportHeight/2-buttonPause.getHeight()/2,buttonPause.getHeight());
        button_Resume = new Sprite(new Texture(Hockey.PATCH+"buttonResume.png"));
        button_NewGame = new Sprite(new Texture(Hockey.PATCH+"buttonNewGame.png"));
        button_Exit = new Sprite(new Texture(Hockey.PATCH+"buttonExit.png"));


        button_Resume.setPosition(Hockey.WITDH/2-button_Resume.getWidth()/2,Hockey.HEIGHT/2-button_Resume.getHeight()*3);
        button_NewGame.setPosition(Hockey.WITDH/2-button_NewGame.getWidth()/2,Hockey.HEIGHT/2-button_NewGame.getHeight()/2);
        button_Exit.setPosition(Hockey.WITDH/2-button_Exit.getWidth()/2,Hockey.HEIGHT/2+button_Exit.getHeight()*2);




        sprite = new Sprite(backgroud.get(Config.BACKGROUND));

    }



    /*
    * cac Ham trong render
    *
    * */

    @Override
    public void render(SpriteBatch sb) {
        if(!GAME_PAUSED){
            sb.setProjectionMatrix(cam.combined);
            sb.begin();
            background.draw(sb,pandle_pink,pandle_green,puck);
            puck.draw(sb);
            pandle_pink.draw(sb);
            pandle_green.draw(sb);
            sb.draw(buttonPause,cam.viewportWidth- buttonPause.getWidth(),cam.viewportHeight/2-buttonPause.getHeight()/2);
        }else {

            sb.begin();
            sprite.setAlpha(0.5f);
            puck.draw(sb);
            pandle_pink.draw(sb);
            pandle_green.draw(sb);
            sprite.draw(sb);
            button_Resume.draw(sb);
            button_NewGame.draw(sb);
            button_Exit.draw(sb);
        }

//        drawScores(sb);
        sb.end();
    }

    /*
    * Ham kiem tra xem cuoi cung cua update thi puck co va cham voi pandle neu co thi di chuyen puck ra xa
    *
    * */

    public void ckeckHit(Pandle pandle, Puck puck,BackgroundGame background){

        pandle.hits(puck);
        puck.hits(pandle);

        double distance = Math.sqrt(Vector2.dst2(puck.getX(),puck.getY(),pandle.getX(),pandle.getY()));

        if(Intersector.overlaps(pandle.getBounds(),puck.getBounds()))
        {
            Double x = (puck.getX()-pandle.getX())*(puck.getWitdh()/2+pandle.getWitdh()/2)/distance+pandle.getX();
            Double y =  (puck.getY()-pandle.getY())*(puck.getWitdh()/2 + pandle.getWitdh()/2)/distance+pandle.getY();
            puck.setPosition(x.floatValue(),y.floatValue());

        }

    }
    /*
        Giới hạn không cho di chuyển ra khởi màng hình, va di chuyen
    **/
    public void move(int screenX,int screenY){
        if (screenY < Hockey.HEIGHT / 2) {

            screenX = (int) Math.min(Math.max(screenX, pandle_green.getWitdh()/2+background.getMapEdge().get(Config.EDGE_RIGHT_TOP).getWitdh()-1),
                    Hockey.WITDH-pandle_green.getWitdh()/2-(background.getMapEdge().get(Config.EDGE_LEFT_TOP).getWitdh()-1));

            screenY = (int) Math.min(Math.max(screenY, pandle_green.getHeight()/2+background.getMapEdge().get(Config.EDGE_TOP_RIGHT).getHeight()-1),
                    Hockey.HEIGHT/2-pandle_green.getHeight()/2);
            pandle_green.move(screenX, screenY);

        } else {
            // gioi han bounds khong cho chay ra khoi mang hinh
            screenX = (int) Math.min(Math.max(screenX, pandle_pink.getWitdh()/2+background.getMapEdge().get(Config.EDGE_RIGHT_BOTTOM).getWitdh()-1),
                    Hockey.WITDH-pandle_pink.getWitdh()/2-(background.getMapEdge().get(Config.EDGE_LEFT_BOTTOM).getWitdh()-1));

            screenY = (int) Math.min(Math.max(screenY,Hockey.HEIGHT/2+pandle_pink.getHeight()/2),
                    Hockey.HEIGHT -pandle_pink.getHeight()/2-(background.getMapEdge().get(Config.EDGE_BOTTOM_RIGHT).getHeight()-1));

            pandle_pink.move(screenX, screenY);
        }
    }
    /*
           Kiem tra xem c
     */
    public void goalScore(){

        if(puck.getY()<=0 || puck.getY()>= Hockey.HEIGHT){
            if(puck.getY()< Hockey.HEIGHT/2) {
//                Gdx.app.log("abc","1");
                pandle_pink.score++;
                pandle_pink.updateScore();
            }else {
                pandle_green.score++;
                pandle_green.updateScore();
            }
            pandle_pink.reLoadGame(Hockey.WITDH / 2, Hockey.HEIGHT - pandle_pink.getHeight());
            pandle_green.reLoadGame(Hockey.WITDH / 2, pandle_pink.getHeight());
            puck.reLoadGame(Hockey.WITDH / 2, Hockey.HEIGHT / 2);

        }
    }
    public void drawScores(SpriteBatch sb) {
        pandle_green.score1.setPosition(scoreHorizontalOffset,
                Hockey.WITDH  / 2 - scoreVerticalOffset - pandle_green.score1.getHeight());
        pandle_green.score1.draw(sb);

        pandle_green.score2.setPosition(scoreHorizontalOffset * 1.1f + pandle_green.score1.getWidth(),
                Hockey.WITDH  / 2 - scoreVerticalOffset - pandle_green.score2.getHeight());
        pandle_green.score2.draw(sb);

        pandle_pink.score1.setPosition(scoreHorizontalOffset,
                Hockey.WITDH  / 2 + scoreVerticalOffset);
        pandle_pink.score1.draw(sb);

        pandle_pink.score2.setPosition(scoreHorizontalOffset * 1.1f + pandle_pink.score1.getWidth(),
                Hockey.WITDH  / 2 + scoreVerticalOffset);
        pandle_pink.score2.draw(sb);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
//       gamePort.update(width,height);
    }

    @Override
    public void pause() {
        GAME_PAUSED = true;
    }

    @Override
    public void resume() {
        GAME_PAUSED = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
