package com.skyplus.hockey.state;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.skyplus.hockey.Hockey;
import com.skyplus.hockey.network.Client;
import com.skyplus.hockey.network.GameClientInterface;
import com.skyplus.hockey.network.GameListener;
import com.skyplus.hockey.network.Server;
import com.skyplus.hockey.objects.DeviceAPI;

/**
 * Created by THUC UYEN on 29-Apr-17.
 */
public class JoinGameState extends State implements Screen,GameListener {


    private Texture bg;
    private Sprite button_CreateGame, button_JoinGame, button_Exit;
    private GameClientInterface gameClient;
    private Sprite spriteClickAfter;
    private Animation<TextureRegion>  loading;
    private Boolean flag = false;
    private float elapsed;

    public JoinGameState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture(Hockey.PATCH + "backGame.png");
        button_CreateGame = new Sprite(new Texture(Hockey.PATCH + "buttonCreate.png"));
        button_JoinGame = new Sprite(new Texture(Hockey.PATCH + "buttonJoin.png"));
        button_Exit = new Sprite(new Texture(Hockey.PATCH + "buttonExit.png"));

<<<<<<< .mine        button_CreateGame.setPosition(Hockey.WITDH / 2 - button_CreateGame.getWidth() / 2, Hockey.HEIGHT / 2 - button_CreateGame.getHeight() * 3);
        button_JoinGame.setPosition(Hockey.WITDH / 2 - button_JoinGame.getWidth() / 2, Hockey.HEIGHT / 2 - button_JoinGame.getHeight() / 2);
        button_Exit.setPosition(Hockey.WITDH / 2 - button_Exit.getWidth() / 2, Hockey.HEIGHT / 2 + button_Exit.getHeight() * 2);
=======        button_CreateGame.setPosition(Hockey.WITDH/2-button_CreateGame.getWidth()/2,Hockey.HEIGHT/2-button_CreateGame.getHeight()*3 );
        button_JoinGame.setPosition(Hockey.WITDH/2-button_JoinGame.getWidth()/2,Hockey.HEIGHT/2-button_JoinGame.getHeight()/2);
        button_Exit.setPosition(Hockey.WITDH/2-button_Exit.getWidth()/2,Hockey.HEIGHT/2+button_Exit.getHeight()*2);
>>>>>>> .theirs        spriteClickAfter = new Sprite(new Texture(Hockey.PATCH+"backGame1.png"));
        loading =  GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(Hockey.PATCH+"loading.gif").read());
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
                // click button tao game
                if (button_CreateGame.getBoundingRectangle().contains(screenX, screenY)) {

                    if(Hockey.deviceAPI.isConnectedToLocalNetwork() && !flag){
                        createServer();
                        flag = true;
                   }else{
                        Hockey.deviceAPI.showNotification("Not connected to a network.");
                   }

                    // click button join game
                } else if (button_JoinGame.getBoundingRectangle().contains(screenX, screenY) && !flag) {

                    if(Hockey.deviceAPI.isConnectedToLocalNetwork()){
                        runClient();
                        flag = true;
                    }else{
                        Hockey.deviceAPI.showNotification("Not connected to a network.");
                    }


                    // click button quay lai
                } else if (button_Exit.getBoundingRectangle().contains(screenX, screenY) && !flag) {
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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0, Hockey.WITDH, Hockey.HEIGHT);
        button_CreateGame.draw(sb);
        button_JoinGame.draw(sb);
        button_Exit.draw(sb);
        if (flag){
            spriteClickAfter.setAlpha(0.77f);
            spriteClickAfter.draw(sb);
            elapsed += Gdx.graphics.getDeltaTime();
            sb.draw(loading.getKeyFrame(elapsed), Hockey.WITDH/2-loading.getKeyFrame(elapsed).getRegionWidth()/2, Hockey.HEIGHT/2-loading.getKeyFrame(elapsed).getRegionHeight()/2);
        }


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
    private void createServer() {
        gameClient = new Server(this,Hockey.deviceAPI.getIpAddress());
        Thread t = new Thread(gameClient);
        t.start();
    }
    private void runClient() {
        gameClient = new Client(this,Hockey.deviceAPI.getIpAddress());
        Thread t = new Thread(gameClient);
        t.start();
    }

    @Override
    public void onConnected() {
        Hockey.deviceAPI.showNotification("Player found");
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.log(DeviceAPI.TAG, "Setting screen");
                gsm.set(new PlayStateNetWork(gsm,gameClient));
                Gdx.app.log(DeviceAPI.TAG, "Screen set");
            }
        }, 0.2f);
    }

    @Override
    public void onDisconnected() {
        Hockey.deviceAPI.showNotification("Lost connection with other player");
    }

    @Override
    public void onConnectionFailed() {
        Hockey.deviceAPI.showNotification("Failed to find a game");
    }

    @Override
    public void onMessageReceived(String message) {

    }


}