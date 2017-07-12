package com.benchpressben.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import scenes.GameWorld;
import scenes.Menu;

import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

public class GameMain extends Game {

	private SpriteBatch batch;

	private OrthographicCamera cam;

	private AssetManager soundManager;

    @Override
	public void create () {

		float aspectRatio = Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		cam = new OrthographicCamera(WIDTH, HEIGHT * aspectRatio);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();
		soundManager = new AssetManager();

		soundManager.load("audio/music/snd_bensound_main.mp3", Music.class);
		soundManager.load("audio/sounds/snd_bounce.wav", Sound.class);
		soundManager.load("audio/sounds/snd_throw.wav", Sound.class);
		soundManager.load("audio/sounds/snd_catch.wav", Sound.class);
		soundManager.load("audio/sounds/snd_fail.wav", Sound.class);
		soundManager.load("audio/sounds/snd_hit.wav", Sound.class);
		soundManager.finishLoading();

        setScreen(new Menu(this, soundManager));
		//setScreen(new GameWorld(this, soundManager));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined);

		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		soundManager.dispose();
	}

	/*************************************************
	 * get batch
	 *************************************************/
	public SpriteBatch getBatch() {
		return this.batch;
	}

	public OrthographicCamera getCam() { return cam; }

    public AssetManager getSoundManager() {
        return soundManager;
    }
}
