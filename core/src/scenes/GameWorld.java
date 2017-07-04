package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.benchpressben.game.GameMain;

import gui.GUI;
import gui.Score;
import player.Player;
import player.Weight;

import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

public class GameWorld implements Screen {

    private GameMain game;
    private float timePassed = 0;
    private Player player;
    private Texture buttons;
    private Weight weight;
    private Score score;
    private Boolean atGym;
    private GUI gui;

    public GameWorld(GameMain game) {
        this.game = game;

        player  = new Player("spr_player.atlas", this, 360, 160);
        weight  = new Weight("spr_weight.png", this, 360, 640);
        buttons = new Texture("bg_buttons.png");
        atGym   = true;

        gui = new GUI(this);

        score = new Score(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, .75f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(weight.getY() <= 60){ // game over!
            setAtGym(false);
            Gdx.app.debug("game over", "Weight y: " + weight.getY());
        }
        // this constraint causes the game to crash
        //if (getAtGym()) {
            // update player movement
            player.updateMotion();
            // update weight movement
            weight.updateMotion();
            // update enemy movement
            // enemy.updateMotion();
        //}

        if ( weight.contact(player) && !weight.isHeld() && weight.getVsp() < 0 ){
            weight.setxOffset( ( weight.getX() + weight.getWidth()/2 ) - ( player.getX() + player.getWidth()/2 ) );
            weight.setHeld(true);
        }

        gui.drawGui();

        game.getBatch().begin();

        game.getBatch().draw(buttons, 0, 0);

        timePassed += Gdx.graphics.getDeltaTime();

        game.getBatch().draw(player.getCurrentFrame(), player.getX(), player.getY(), player.getWidth(), player.getHeight() * player.getyScale());

        game.getBatch().draw(weight, weight.getX(), weight.getY() );

        score.getBitmapFont().draw(game.getBatch(), score.getScoreString(), 100, 1240);

        game.getBatch().end();
    }

    public float getTimePassed() {
        return timePassed;
    }
    public Player getPlayer() { return player; }
    public OrthographicCamera getCam() { return game.getCam(); }
    public Boolean getAtGym() { return atGym; }
    public void setAtGym(Boolean atGym) { this.atGym = atGym; }

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