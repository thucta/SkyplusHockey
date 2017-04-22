package com.skyplus.hockey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.skyplus.hockey.state.GameStateManager;
import com.skyplus.hockey.state.MenuState;


/**
 * Created by TruongNN  on 3/24/2017.
 */

public class Hockey extends Game {

	public static  int WITDH =0;
	public static  int HEIGHT = 0;
	public static  String TITLE = "Skys Hockey";
	public static String PATCH="";
	SpriteBatch batch;
	private GameStateManager gms;




	@Override
	public void create () {
		batch = new SpriteBatch();
		gms = new GameStateManager();

		WITDH = Gdx.app.getGraphics().getWidth();
		HEIGHT = Gdx.app.getGraphics().getHeight();

		// dua vao kich thuoc mang hinh set duong dan den thu muc hinh anh phu hop
		PATCH=WITDH+"x"+HEIGHT+"/";

		Gdx.gl.glClearColor(1, 0, 0, 1);

		gms.push(new MenuState(gms));
	}


	/*
	*  vong lap cua game
	*
	* */
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gms.update(Gdx.graphics.getDeltaTime());
		gms.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	@Override
	public void resize(int width, int height) {
		gms.resize(width,height);
		Gdx.app.log("resize","x sssssssssssssssssssssss");
	}

	public void setPatch(){
			PATCH = WITDH+"x"+HEIGHT;
	}
}
