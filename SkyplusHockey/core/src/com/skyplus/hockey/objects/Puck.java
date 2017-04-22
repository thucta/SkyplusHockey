package com.skyplus.hockey.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.skyplus.hockey.Hockey;

import java.util.Date;


/**
 * Created by TruongNN on 3/24/2017.
 */

public class Puck extends  GameObject {

    private Sprite body ;
    private Vector2 postion;
    private Vector2d velocity;
    private Circle bounds;

    private int radius;
    private static float speed = 0.98f;
    private static float LIMIT = 25;
    private static float LIMIT_STOP = 0.3f;




    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public Puck(int x, int y){
        postion = new Vector2(x,y);
       initator();

    }

    private void initator() {
        velocity = new Vector2d(0,0);
        body = new Sprite(new Texture(Hockey.PATCH+"puck.png"));

        // tru ban kinh cua puck de ve ngay tam duong tron
        body.setPosition(postion.x-body.getWidth()/2,postion.y-body.getWidth()/2);

        bounds = new Circle(postion.x,postion.y,body.getWidth()/2);
        radius = (int) (body.getWidth()/2);

    }

    public void setPostion(Vector2 postion) {
        this.postion = postion;
    }

    public Vector2d getVelocity() {

        return velocity;
    }

    public  Vector2d vectorTemp = new Vector2d(0,0);
    public Boolean hits(Pandle pandle) {
        if(Intersector.overlaps(getBounds(),pandle.getBounds())){


//            velocity.x += pandle.getVelocity().x;
//            velocity.y += pandle.getVelocity().y;
            vectorTemp.x = pandle.getVelocity().x;
            vectorTemp.y = pandle.getVelocity().y;
//            pandle.setVelocity();
//            Gdx.app.log("sau", pandle.getVelocity()+" " );
            //xac ding huong va cham
            Vector2d direction = new Vector2d(postion.x- pandle.getX(), postion.y- pandle.getY());

//            if(!velocity.hasSameDirection(pandle.getPostion())){
//                Gdx.app.log("sau", pandle.getVelocity()+" " );
//                velocity.x += pandle.getVelocity().x*2/3 ;
//                velocity.y += pandle.getVelocity().y*2/3;
//            }

            velocity =  vectorTemp.proj(direction).plus(velocity.proj(direction).times(-1.5f)
                    .plus(velocity.proj(new Vector2d(direction.y, -direction.x)))).times(1.2f);


            /*
                 F = -F => a1m1 = - a2m2 (m1 = 2/3 m2)    (m1: khoi luong cua puck , m2 : khoi luong cua pandle)
                 => do giam van toc cua puck khi va cham vao cac dia
            */
            velocity.x += velocity.x*-2/3 ;
            velocity.y += velocity.y/-2/3;

            return true;
        }
        return false;
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
        velocityLimit();
        velocity.x *= speed;
        velocity.y *= speed;
        postion.x += velocity.x;
        postion.y += velocity.y;

//        Gdx.app.log("Position","x " + velocity );

        if(postion.x <= radius){
            velocity.x = Math.abs(velocity.x); //bounce
            postion.x = radius;
        }

        // bounce off right wall
        if(postion.x >= Hockey.WITDH - radius){
            velocity.x = -Math.abs(velocity.x);
            postion.x = Hockey.WITDH - radius;
        }

        // bounce off bottom
        if(postion.y <= radius){
                velocity.y = Math.abs(velocity.y);
                postion.y = radius;


        }

        // bounce off top for Player 1 or bottom for Player 2
        if(postion.y >= Hockey.HEIGHT - radius){
            velocity.y = -Math.abs(velocity.y);
            postion.y = Hockey.HEIGHT - radius;
        }


    }

    public void setPosition(float x,float y){
        postion.x = x;
        postion.y = y;
    }

    @Override
    public float getX() {

        return this.postion.x;
    }

    public Vector2 getPostion() {
        return this.postion;
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
        bounds.set(postion.x,postion.y,body.getWidth()/2);
        return bounds;
    }



    @Override
    public void draw(SpriteBatch batch) {
        body.setPosition(postion.x-body.getWidth()/2,postion.y-body.getHeight()/2);
        body.draw(batch);
    }


    // gioi han van toc cua puck
    public void velocityLimit(){
        velocity.x = Math.min(Math.max(velocity.x,-LIMIT), LIMIT);
        velocity.y =Math.min(Math.max(velocity.y,-LIMIT), LIMIT);

        if(velocity.x >-LIMIT_STOP && velocity.x < LIMIT_STOP){
            velocity.x = 0;
        }
        if(velocity.y >-LIMIT_STOP && velocity.y <LIMIT_STOP){
            velocity.y = 0;
        }
    }
}
