package com.skyplus.hockey.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by TruongNN on 3/24/2017.
 */

public class Pandle extends GameObject {
    private Sprite body;
    private Texture body_dark;
    private Texture body_light;



    private Vector2 position;
    private Circle bounds;
    public Sprite score1, score2;
    public int score = 0;
    private Map<Integer, Sprite> spriteMap = new HashMap<Integer, Sprite>();
    public static float speed = 70000;


    public static float elapsed = 0.001f;

    private Boolean moving = false;
    private Vector2 end = new Vector2(0, 0);

    private static float LIMIT = 20;
    private Vector2d velocity;
    private long timer = 0;


    public Pandle(int x, int y, Texture body1, Texture body2,Map<Integer, Sprite> spriteMap ) {
        position = new Vector2(x, y);
        this.body_dark = body1;
        this.body_light = body2;
        this.spriteMap = spriteMap;
        body = new Sprite(body1);
        bounds = new Circle(position.x, position.y, getWitdh() / 2);
        velocity = new Vector2d(0, 0);
        updateScore();
    }

    @Override
    public void draw(SpriteBatch batch) {
        body.setPosition(position.x - body.getWidth() / 2, position.y - body.getHeight() / 2);
        body.draw(batch);
    }

    @Override
    public void move(float x, float y) {

        end.set(x, y);
        moving = true;
    }

    @Override
    public void update(float delta) {

        if (!moving) return;

        Vector2 start = new Vector2(position.x, position.y);

        // khoang cach giua 2 diem trong mat phang, chua lay can ban 2
        double distance = Vector2.dst2(start.x, start.y, end.x, end.y);
        double distance2 = Math.sqrt(distance);

        // xac dinh huong di
        Vector2 direction = new Vector2(end.x - start.x, end.y - start.y);
        direction.nor();

        double x = direction.x * speed * elapsed;
        double y = direction.y * speed * elapsed;

        setVelocity();  // set van toc cho pandle
        position.x += x;
        position.y += y;
        if (Vector2.dst2(start.x, start.y, position.x, position.y) >= distance) {
            position.set(end);
        }



    }

    public void reLoadGame(float x,float y){
        setPosition(x,y);
        velocity.x=0;
        velocity.y =0;
    }

    @Override
    public Boolean hits(Circle circle) {

        return false;
    }


    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public float getWitdh() {
        return body.getWidth();
    }

    @Override
    public float getHeight() {
        return body.getHeight();
    }

    @Override
    public Texture getTexture() {
        return body.getTexture();
    }

    @Override
    public Circle getBounds() {
        bounds.set(position.x, position.y, body.getWidth() / 2);
        return bounds;
    }

    public Boolean hits(Puck puck) {
        if (Intersector.overlaps(puck.getBounds(), getBounds()) ) {
            body.setTexture(body_light);
            timer = System.currentTimeMillis();

        }
        else if (System.currentTimeMillis() - timer > 100) {
            body.setTexture(body_dark);
        }
        return false;
    }

    /*
    *
    * seter
    * geter
    * */
    public Vector2d getVelocity() {
        return velocity;
    }

    public Texture getBody_dark() {
        return body_dark;
    }

    public void setBody_dark(Texture body_dark) {
        this.body_dark = body_dark;
    }

    public Texture getBody_light() {
        return body_light;
    }

    public void setBody_light(Texture body_light) {
        this.body_light = body_light;
    }
    public void setPosition(float x , float y) {
        this.position.x = x;
        this.position.y = y;
        moving = false;
    }
    public Vector2 getPosition() {
        return this.position;
    }

    public void setVelocity() {
        velocity.set((end.x - position.x)*3, (end.y - position.y)*3);
    }
    public void setBody(Texture body) {
        this.body.setRegion(body);
    }
    public void updateScore() {
        score1 = spriteMap.get(score / 10);
        score1.flip(false, true);
        score2 = spriteMap.get(score % 10);
        score2.flip(false, true);
    }
}
