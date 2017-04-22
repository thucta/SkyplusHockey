package com.skyplus.hockey.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;


/**
 *
 * Created by TruongNN on 3/24/2017.
 *
 */

public class Pandle extends GameObject {
    private Sprite body;
    private Texture body_dark;
    private Texture body_light;

    private Vector2 postion;
    private Circle bounds;

    public static float speed = 7000;
    public static float elapsed = 0.01f;

    private Boolean moving = false;
    private Vector2 end = new Vector2(0, 0);
    private static float LIMIT = 20;
    private Vector2d velocity;
    private static long timer =0;


    public Pandle(int x, int y, Texture body1, Texture body2) {
        postion = new Vector2(x, y);
        this.body_dark = body1;
        this.body_light = body2;
        body = new Sprite(body1);
        bounds = new Circle(postion.x, postion.y, getWitdh() / 2);
        velocity = new Vector2d(0,0);
    }

    @Override
    public void draw(SpriteBatch batch) {
        body.setPosition(postion.x-body.getWidth()/2,postion.y-body.getHeight()/2);
        body.draw(batch);
    }
    @Override
    public void move(int x, int y) {
        end.set(x, y);
        moving = true;
    }

    @Override
    public void update(float delta) {

        if (!moving) return;

        Vector2 start = new Vector2(postion.x, postion.y);

        // khoang cach giua 2 diem trong mat phang, chua lay can ban 2
        double distance = Vector2.dst2(start.x,start.y,end.x,end.y);
        double distance2 = Math.sqrt(distance);

        // xac dinh huong di
        Vector2 direction = new Vector2(end.x - start.x, end.y - start.y);
        direction.nor();

        double x = direction.x * speed * elapsed;
        double y = direction.y * speed * elapsed;

        if (moving) {
            setVelocity();  // set van toc cho pandle
            postion.x += x;
            postion.y += y;


            if (Vector2.dst2(start.x, start.y, postion.x, postion.y) >= distance) {
                postion.set(end);
                moving = false;
            }
        }


    }
    @Override
    public Boolean hits(Circle circle) {

        return false;
    }


    @Override
    public float getX() {
        return postion.x;
    }

    @Override
    public float getY() {
        return postion.y;
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
        bounds.set(postion.x, postion.y, body.getWidth() / 2);
        return bounds;
    }

    public Boolean hitEdge(BackgroundGame background) {
        Boolean flag = false;
        for (BackgroundGame.Edge edge : background.getListEdge()){
            if (Intersector.overlaps(getBounds(), edge.getBound())){
                edge.setBody(true);
                flag = true;
            }else {
                edge.setBody(false);
            }

        }
        return flag;
    }

    public Boolean hitsPuck(Puck puck,BackgroundGame bg){

        if(Intersector.overlaps(puck.getBounds(),getBounds()) || hitEdge(bg)){
            body.setTexture(body_light);
            timer = System.currentTimeMillis();

        }

        else if(System.currentTimeMillis()-timer >100) {
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
    public Vector2 getPostion() {
        return this.postion;
    }

    public void setVelocity() {
        velocity.set((end.x-postion.x)*2,(end.x-postion.x)*2);
    }
}
