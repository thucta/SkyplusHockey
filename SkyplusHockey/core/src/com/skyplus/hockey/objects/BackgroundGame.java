package com.skyplus.hockey.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public BackgroundGame(int widthScreen, int hieghtscreen, HashMap<String,Texture> backgroud){

        bg = new Sprite(backgroud.get("backgroud"));
        bg.setRegionWidth(widthScreen);
        bg.setRegionHeight(hieghtscreen);
        bg.setPosition(0,0);
        listEdge = new ArrayList<Edge>();
        addEdge(widthScreen-backgroud.get("bg_left").getWidth(),0,backgroud.get("bg_left_1"),backgroud.get("bg_left"));
        addEdge(0,0,backgroud.get("bg_right_1"),backgroud.get("bg_right"));
        addEdge(0,0,backgroud.get("bg_top"),backgroud.get("bg_top_1"));
        addEdge(0,hieghtscreen-backgroud.get("bg_bottom").getHeight(),backgroud.get("bg_bottom"),backgroud.get("bg_bottom_1"));
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


    private void addEdge(int x,int y, Texture body1,Texture body2){
        Edge edge = new Edge(x,y,body1,body2);
        listEdge.add(edge);
    }

    // cac canh cua background
    public  class Edge {

        private Sprite body;
        private Texture body_dark;
        private Texture body_light;

        private Rectangle bound;


        public Edge(int poistionX,int positionY,Texture body_dark, Texture body_light) {
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

        public void setBody(Boolean flagLight) {
            if(flagLight) {
                this.body.setTexture(body_dark);
            }else{
                this.body.setTexture(body_light);
            }
        }


        public Rectangle getBound() {

            return bound;
        }

        public void setBound(Rectangle bound) {
            bound.set(this.body.getX(),this.body.getY(),this.body.getWidth(),this.body.getHeight());
            this.bound = bound;
        }

        public  void draw(SpriteBatch sb){
             body.draw(sb);
        }


    }

}
