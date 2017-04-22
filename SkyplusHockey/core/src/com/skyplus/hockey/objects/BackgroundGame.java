package com.skyplus.hockey.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.skyplus.hockey.Hockey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by NVTT on 4/19/2017.
 */

public class BackgroundGame {



    private Sprite bg;
    private List<Edge> listEdge ;

    private Boolean hits  = false;

    public BackgroundGame(int widthScreen, int hieghtscreen, HashMap<String,Texture> backgroud){

        bg = new Sprite(backgroud.get("backgroud"));
        bg.setRegionWidth(widthScreen);
        bg.setRegionHeight(hieghtscreen);
        bg.setPosition(0,0);
        listEdge = new ArrayList<Edge>();

        addEdge(widthScreen-backgroud.get("bg_left").getWidth(),0,backgroud.get("bg_left"),backgroud.get("bg_left_1"));
        addEdge(0,0,backgroud.get("bg_right"),backgroud.get("bg_right_1"));

        addEdge(0,0,backgroud.get("bg_top_right"),backgroud.get("bg_top_right_light"));
        addEdge(widthScreen-backgroud.get("bg_top_left").getWidth(),0,backgroud.get("bg_top_left"),backgroud.get("bg_top_left_light"));
        addEdge(0,hieghtscreen-backgroud.get("bg_bottom_right").getHeight(),backgroud.get("bg_bottom_right"),backgroud.get("bg_bottom_right_light"));
        addEdge(widthScreen-backgroud.get("bg_bottom_left").getWidth(),hieghtscreen-backgroud.get("bg_bottom_left").getHeight(),backgroud.get("bg_bottom_left"),backgroud.get("bg_bottom_left_light"));
    }
    public List<Edge> getListEdge() {
        return listEdge;
    }

    public void setListEdge(List<Edge> listEdge) {
        this.listEdge = listEdge;
    }

    public void draw(SpriteBatch sb){
        bg.draw(sb);
        for (Edge edge: listEdge) {
            edge.draw(sb);
        }
    }

    // update cac doi tuong va cham voi canh
    public void update(Pandle pandle_pink,Pandle pandle_green,Puck puck ){

        for( Edge edge:listEdge){
            hits = false;
            if(Intersector.overlaps(pandle_pink.getBounds(), edge.getBound()))                                    {
                edge.setBody(edge.getBody_light());
                pandle_pink.setBody(pandle_pink.getBody_light());
                hits = true;
            }
            if( Intersector.overlaps(pandle_green.getBounds(),edge.getBound())){
                pandle_green.setBody(pandle_green.getBody_light());
                edge.setBody(edge.getBody_light());
                hits = true;
            }
            if (Intersector.overlaps(puck.getBounds(),edge.getBound())){
                edge.setBody(edge.getBody_light());
                hits = true;
            }
            if(hits==false){
                edge.setBody(edge.getBody_dark());
            }

        }
    }

    private void addEdge(int x,int y, Texture body1,Texture body2){
        Edge edge = new Edge(x,y,body1,body2);
        listEdge.add(edge);
    }

    // cac canh cua background
    public  class Edge {

        private int postionX,getPostionY;
        private Sprite body;
        private Texture body_dark;
        private Texture body_light;
        private Rectangle bound;



        public Edge(int poistionX, int positionY, Texture body_dark, Texture body_light) {
            this.postionX = poistionX;
            this.getPostionY = positionY;
            this.body_dark = body_dark;
            this.body_light = body_light;
            this.body = new Sprite(body_dark);
            this.body.setPosition(poistionX,positionY);
            bound = new Rectangle(this.body.getX(),this.body.getY(),this.body.getWidth(),this.body.getHeight());
        }

        public Sprite getBody() {
            return body;
        }



        // ham set body sang toi, neu flagLigh  = true nghia la sang, co vat va cham nguoc lai khong
        public void setBody(Texture body) {
            this.body.setRegion(body);
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
        public Rectangle getBound() {

            return bound;
        }

        public void setBound(Rectangle bound) {
            bound.set(this.body.getX(),this.body.getY(),this.body.getWidth(),this.body.getHeight());
            this.bound = bound;
        }

        public  void draw(SpriteBatch sb){
            this.body.setPosition(postionX,getPostionY);
            this.body.draw(sb);
        }


    }

}
