package com.skyplus.hockey.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by TruongNN on 4/8/2017.
 * s
 */

public class Background {
    private static int x,y,w,h;

    private Texture bg;
    private Texture bg_right;
    private Texture bg_left;
    private Texture bg_top;
    private Texture bg_bottom;

    private Sprite sp_bg;
    private Sprite sp_bg_right;
    private Sprite sp_bg_left;
    private Sprite sp_bg_top;
    private Sprite sp_bg_bottom;

    private Rectangle boundRight;
    private Rectangle boundLeft;
    private Rectangle boundTop;
    private Rectangle boundBottom;

    public Background(int x,int y,int w,int h,Texture bg, Texture bg_right, Texture bg_left, Texture bg_top, Texture bg_bottom) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bg = bg;
        this.bg_right = bg_right;
        this.bg_left = bg_left;
        this.bg_top = bg_top;
        this.bg_bottom = bg_bottom;
        initation();
    }
    private void initation(){
        sp_bg = new Sprite(bg,x,y,bg.getWidth(),bg.getHeight());
        sp_bg_right = new Sprite(bg_right,x,y,bg_right.getWidth(),bg_right.getHeight());
        sp_bg_left = new Sprite(bg_left,w-bg_left.getWidth(),0,bg_left.getWidth(),bg_left.getHeight());
        sp_bg_top = new Sprite(bg_top,x,y,bg_top.getWidth(),bg_top.getHeight());
        sp_bg_bottom = new Sprite(bg_bottom,x,h-bg_bottom.getHeight(),bg_bottom.getWidth(),bg_bottom.getHeight());

        sp_bg.setPosition(0,0);
        sp_bg_right.setPosition(0,0);
        sp_bg_left.setPosition(w-bg_left.getWidth(),0);
        sp_bg_top.setPosition(0,0);
        sp_bg_bottom.setPosition(0,h-bg_bottom.getHeight());


        boundRight = new Rectangle(sp_bg_right.getX(),sp_bg_right.getY(),sp_bg_right.getWidth(),sp_bg_right.getHeight());
        boundLeft = new Rectangle(sp_bg_left.getX(),sp_bg_left.getY(),sp_bg_left.getWidth(),sp_bg_left.getHeight());
        boundTop = new Rectangle(sp_bg_top.getX(),sp_bg_top.getY(),sp_bg_top.getWidth(),sp_bg_top.getHeight());
        boundBottom = new Rectangle(sp_bg_bottom.getX(),sp_bg_bottom.getY(),sp_bg_bottom.getWidth(),sp_bg_bottom.getHeight());

    }
    public void draw(SpriteBatch sb){
        sp_bg.draw(sb);
        sp_bg_right.draw(sb);
        sp_bg_left.draw(sb);
        sp_bg_top.draw(sb);
        sp_bg_bottom.draw(sb);

    }

    




    public Texture getBg() {
        return bg;
    }

    public void setBg(Texture bg) {
        this.bg = bg;
    }


    public Texture getBg_right() {
        return bg_right;
    }

    public void setBg_right(Texture bg_right) {
        this.bg_right = bg_right;
    }

    public Texture getBg_left() {
        return bg_left;
    }

    public void setBg_left(Texture bg_left) {
        this.bg_left = bg_left;
    }

    public Texture getBg_top() {
        return bg_top;
    }

    public void setBg_top(Texture bg_top) {
        this.bg_top = bg_top;
    }

    public Texture getBg_bottom() {
        return bg_bottom;
    }

    public void setBg_bottom(Texture bg_bottom) {
        this.bg_bottom = bg_bottom;
    }


    public Rectangle getBoundRight() {
        return boundRight;
    }

    public Rectangle getBoundLeft() {
        return boundLeft;
    }

    public Rectangle getBoundTop() {
        return boundTop;
    }

    public Rectangle getBoundBottom() {
        return boundBottom;
    }
}
