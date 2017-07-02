package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.benchpressben.game.GameMain;

import player.Player;
import player.Weight;

import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

public class GameWorld implements Screen {

    private GameMain game;

    private float timePassed = 0;

    private Player player;

    private Texture ground;

    private Weight weight;

    private static ShapeRenderer worldBorder;

    public GameWorld(GameMain game) {
        this.game = game;

        player = new Player("spr_player.atlas", 360, 160);
        ground = new Texture("bg_ground.png");
        weight = new Weight("spr_weight.png", this,360,640);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, .75f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldBorder = new ShapeRenderer();
        worldBorder.begin(ShapeRenderer.ShapeType.Filled);
        worldBorder.setColor(0, 0, 0, 0);
        worldBorder.rect(0, 200, 100, 1600); //left border
        worldBorder.rect(0, 1700, 1000, 100); //top border
        worldBorder.rect(1000, 200, 100, 1600); //right border
        worldBorder.end();

        //update player movement
        player.updateMotion();
        weight.updateMotion();

        if ( weight.contact(player) && weight.isHeld() == false && weight.getVsp() < 0 ){
            weight.setHeld(true);
        }

        game.getBatch().begin();

        game.getBatch().draw(ground, 0, 0);

        timePassed += Gdx.graphics.getDeltaTime();

        game.getBatch().draw((TextureRegion) player.getAnimation().getKeyFrame(timePassed,true), player.getX(), player.getY());

        game.getBatch().draw(weight, weight.getX(), weight.getY() );

        game.getBatch().end();
    }

    public Player getPlayer() {
        return player;
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