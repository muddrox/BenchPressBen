package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.benchpressben.game.GameMain;

import player.Player;

import static helpers.GameInfo.WIDTH;

public class GameWorld implements Screen {

    private GameMain game;

    private float timePassed = 0;

    private Player player;

    private Texture ground;

    public GameWorld(GameMain game) {
        this.game = game;

        player = new Player("spr_player.atlas", WIDTH/2, 160);
        ground = new Texture("bg_ground.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, .75f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update player movement
        player.setPosition( player.getX() + player.getHsp(), player.getY() );

        game.getBatch().begin();

        game.getBatch().draw(ground, 0, 0);

        timePassed += Gdx.graphics.getDeltaTime();

        game.getBatch().draw((TextureRegion) player.getAnimation().getKeyFrame(timePassed,true), player.getX(), player.getY());

        game.getBatch().end();
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
        //player.dispose();
    }
}
