package com.skyplus.hockey.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.skyplus.hockey.Hockey;


/**
 *
 * Created by TruongNN on 3/24/2017.
 *
 */

public class Pandle extends GameObject {
    private Texture body;
    private Texture body_dark;
    private Texture body_light;

    private Vector3 postion;

    private Circle bounds;

    public static float speed = 700000000;
    public static float elapsed = 0.001f;

    private Background background;

    private Boolean moving = false;

    private Vector2 end = new Vector2(0, 0);
    private Circle bound = new Circle();


    public Pandle(int x, int y, Texture body1, Texture body2, Background background) {
        postion = new Vector3(x, y, 0);
        this.body_dark = body1;
        this.body_light = body2;
        this.background = background;
        body = body1;
        bounds = new Circle(postion.x, postion.y, getWitdh() / 2);


    }


    @Override
    public Boolean hits(Circle circle) {
        if (Intersector.overlaps(circle, background.getBoundRight()) || Intersector.overlaps(circle, background.getBoundLeft()) ||
                Intersector.overlaps(circle, background.getBoundTop()) || Intersector.overlaps(circle, background.getBoundBottom()))
            return true;

        return false;
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
        // khoang cach giua 2 diem trong mat phang
        float distance = Vector2.dst2(start.x, start.y, end.x, end.y);

        // xac dinh huong di
        Vector2 direction = new Vector2(end.x - start.x, end.y - start.y);
        direction.nor();

        float x = direction.x * speed * elapsed;
        float y = direction.y * speed * elapsed;


//        Gdx.app.log("Position","x "  + tempX + ": y " +tempY);
        float tempX = postion.x;
        float tempY = postion.y;

        if (moving) {
            postion.x += x;
            postion.y += y;


            if (Vector2.dst2(start.x, start.y, postion.x, postion.y) >= distance) {
                postion.set(end, 0);
                moving = false;
            }

            if (hits(getBounds())) {
                body = body_light;


            } else {
                body = body_dark;

            }


        }


    }

    @Override
    public float getX() {
        return postion.x - body_dark.getWidth() / 2;
    }

    @Override
    public float getY() {
        return postion.y - body_dark.getHeight() / 2;
    }

    @Override
    public float getWitdh() {
        return body_dark.getWidth();
    }

    @Override
    public float getHeight() {
        return body_dark.getHeight();
    }

    @Override
    public Texture getTexture() {

        return body;
    }

    @Override
    public Circle getBounds() {
        bounds.set(postion.x, postion.y, body.getWidth() / 2);
        return bounds;
    }

    @Override
    public Boolean isContain(Rectangle rectangle) {
        return !(postion.x < 0 || postion.x > Hockey.WITDH || postion.y < 0 || postion.y > Hockey.HEIGHT);
    }

    public void setBody(Texture texture) {
        body_dark = texture;
    }

    public Boolean hisEdge(Circle circle) {
        if (Intersector.overlaps(circle, background.getBoundRight()) || Intersector.overlaps(circle, background.getBoundLeft()) ||
                Intersector.overlaps(circle, background.getBoundTop()) || Intersector.overlaps(circle, background.getBoundBottom()))
            return true;
        return false;
    }
}
