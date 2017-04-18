package com.skyplus.hockey.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.skyplus.hockey.Hockey;


/**
 * Created by TruongNN on 3/24/2017.
 */

public class Puck extends  GameObject {

    private Texture body ;
    private Vector3 postion;
    private Vector3 velocity;
    private Circle bounds;

    public Puck(int x,int y){
        postion = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        body = new Texture(Hockey.PATCH+"puck.png");
        bounds = new Circle(postion.x,postion.y,body.getWidth()/2);
    }



    @Override
    public Boolean hits(Circle circle) {
        return null;
    }

    @Override
    public void move(int x,int y) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public float getX() {
        return postion.x - body.getWidth()/2;
    }

    @Override
    public float getY() {
        return postion.y-body.getHeight()/2;
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
        return body;
    }

    @Override
    public Circle getBounds() {
        bounds.set(postion.x,postion.y,body.getWidth()/2);
        return bounds;
    }

    @Override
    public Boolean isContain(Rectangle rectangle) {
        return null;
    }
}
