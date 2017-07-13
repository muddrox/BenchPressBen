package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.benchpressben.game.GameMain;

import gui.Score;

import static java.sql.Types.NULL;


/**
 * Created by wil15 on 7/11/2017.
 */

public class Menu implements Screen{

    private GameMain game;
    private AssetManager soundManager;
    private Texture bg;
    private int touchX;
    private int touchY;
    private Score score;


    public Menu(GameMain game, AssetManager soundManager){
        this.soundManager = soundManager;
        this.game = game;
        bg = new Texture("Main Screen Bench Press Ben.png");

        Music music = this.soundManager.get("audio/music/snd_bensound_main.mp3", Music.class);
        music.setLooping(true);
        music.play();
        
        touchY = 0;
        touchX = 0;

        score = new Score();
}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, .75f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int isTouched = Gdx.input.isTouched()? 1 : 0;

        touchX = Gdx.input.getX();
        touchY = Gdx.input.getY();

        game.getBatch().begin();
        game.getBatch().draw(bg, 0, 0);
        if (score.getHighScore() != NULL) {
            score.getHighFont().draw(game.getBatch(), score.getHighString(), 150, 840);
        }
        game.getBatch().end();

        if ( isTouched == 1 ) {
            if (touchX > 180 && touchX < 900) {
                if (touchY > Gdx.graphics.getHeight() - 240 && touchY < Gdx.graphics.getHeight() - 55) {
                    game.setScreen(new GameWorld(game, game.getSoundManager()));
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
