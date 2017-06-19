package com.benchpressben.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import scenes.GameWorld;

public class GameMain extends Game {

	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameWorld(this));
	}

	@Override
	public void render () {
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
