package com.skyplus.hockey.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.skyplus.hockey.Hockey;


/**
 * Created by TruongNN on 3/24/2017.
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
    private long timer = 0;


    public Pandle(int x, int y, Texture body1, Texture body2) {
        postion = new Vector2(x, y);
        this.body_dark = body1;
        this.body_light = body2;
        body = new Sprite(body1);
        bounds = new Circle(postion.x, postion.y, getWitdh() / 2);
        velocity = new Vector2d(0, 0);
    }

    @Override
    public void draw(SpriteBatch batch) {
        body.setPosition(postion.x - body.getWidth() / 2, postion.y - body.getHeight() / 2);
        body.draw(batch);
    }

    @Override
    public void move(int x, int y) {
        end.set(x, y);
        end.x = Math.min(Math.max(end.x, getWitdh()/2+14),Hockey.WITDH-getWitdh()/2-14 );
        end.y = Math.min(Math.max(end.y, getHeight()/2+14),Hockey.HEIGHT-getWitdh()/2-14 );
        moving = true;
    }

    @Override
    public void update(float delta) {

        if (!moving) return;

        Vector2 start = new Vector2(postion.x, postion.y);

        // khoang cach giua 2 diem trong mat phang, chua lay can ban 2
        double distance = Vector2.dst2(start.x, start.y, end.x, end.y);
        double distance2 = Math.sqrt(distance);

        // xac dinh huong di
        Vector2 direction = new Vector2(end.x - start.x, end.y - start.y);
        direction.nor();

        double x = direction.x * speed * elapsed;
        double y = direction.y * speed * elapsed;

        setVelocity();  // set van toc cho pandle
        postion.x += x;
        postion.y += y;
        if (Vector2.dst2(start.x, start.y, postion.x, postion.y) >= distance) {
            postion.set(end);
//            moving = false;
        }
//
//        if (moving) {
//            setVelocity();  // set van toc cho pandle
//            Gdx.app.log("sau",velocity+" " );
//            postion.x += x;
//            postion.y += y;
//
//            if (Vector2.dst2(start.x, start.y, postion.x, postion.y) >= distance) {
//                postion.set(end);
//                moving = false;
//            }
//        }


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
        for (BackgroundGame.Edge edge : background.getListEdge()) {
            if (Intersector.overlaps(getBounds(), edge.getBound())) {
                edge.setBody(edge.getBody_light());
                flag = true;
            } else {
                edge.setBody(edge.getBody_dark());
            }

        }
        return flag;
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
    public void setPostion(float x ,float y) {
        this.postion.y = x;
        this.postion.y = y;
    }
    public Vector2 getPostion() {
        return this.postion;
    }

    public void setVelocity() {
        velocity.set((end.x - postion.x)*3, (end.y - postion.y)*3);
    }
    public void setBody(Texture body) {
        this.body.setRegion(body);
    }
}
