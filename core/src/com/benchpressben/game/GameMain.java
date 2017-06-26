package com.benchpressben.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import scenes.GameWorld;

import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

public class GameMain extends Game {

	private SpriteBatch batch;

	private OrthographicCamera cam;

	@Override
	public void create () {

		float aspectRatio = Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		cam = new OrthographicCamera(WIDTH, HEIGHT * aspectRatio);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();
		setScreen(new GameWorld(this));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined);

		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	/*************************************************
	 * get batch
	 *************************************************/
	public SpriteBatch getBatch() {
		return this.batch;
	}
}
