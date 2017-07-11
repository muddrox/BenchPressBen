package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.benchpressben.game.GameMain;

import java.util.ArrayList;

import enemy.Enemy;
import gui.GUI;
import gui.PointsQueue;
import gui.Score;
import helpers.Alarm;
import messages.Loser;
import player.Player;
import player.Weight;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

public class GameWorld implements Screen {

    private GameMain game;
    private float timePassed = 0;
    private Player player;
    private Texture background;
    private Texture buttons;
    private Weight weight;
    private Score score;
    private Boolean atGym;
    private ArrayList<Enemy> enemies;
    private GUI gui;

    private float minTime;
    private float maxTime;

    private Alarm resetGameAlarm;
    private Alarm spawnAlarm;

    private Loser loser;

    public GameWorld(GameMain game) {
        this.game = game;

        player  = new Player("spr_player.atlas", this, 360, 160);
        weight  = new Weight("spr_weight.png", this, 360, 640);
        enemies = new ArrayList<Enemy>();

        background = new Texture("bg_main.png");
        buttons = new Texture("bg_buttons.png");
        atGym   = true;

        gui = new GUI(this);
        loser = new Loser();
        score = new Score(this);

        minTime = 5f;
        maxTime = 10f;

        spawnAlarm = new Alarm(random(minTime,maxTime), true);
        resetGameAlarm = new Alarm(2f, false);

        spawnEnemy();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, .75f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if( weight.getY() <= 160 ){ // game over!
            setAtGym(false);
            Gdx.app.debug("game over", "Weight y: " + weight.getY());
        }

        if (getAtGym()) {
            // update player movement
            player.updateMotion();
            // update weight movement
            weight.updateMotion();
            // update enemy movement and enemy alarm
            spawnAlarm.updateAlarm(Gdx.graphics.getDeltaTime());

            if ( spawnAlarm.isFinished() ){
                spawnEnemy();
                spawnAlarm.resetAlarm(random(minTime,maxTime), true);
            }

            for ( Enemy enemy : enemies ) {
                enemy.updateMotion();

                if ( enemy.contact(weight) ){
                    weight.setHsp( ( weight.getX() - enemy.getX() ) * 0.2f );
                    weight.setVsp(15);
                    enemy.destroy();
                }

                if ( enemy.getY() < 160 - enemy.getHeight() ) {
                    enemy.destroy();
                }
            }

        } else {

            if ( !resetGameAlarm.isRunning() ){
                resetGameAlarm.startAlarm();
            }

            resetGameAlarm.updateAlarm(Gdx.graphics.getDeltaTime());

            if ( resetGameAlarm.isFinished() ){
                setAtGym(true);
                game.dispose();
                game.create();
            }
        }

        if ( weight.contact(player) && !weight.isHeld() && weight.getVsp() < 0 ){
            weight.setxOffset( ( weight.getX() + weight.getWidth()/2 ) - ( player.getX() + player.getWidth()/2 ) );
            gui.setFlicker(Color.PURPLE, 5);
            weight.setHeld(true);
        }

        game.getBatch().begin();

        game.getBatch().draw(background, 0, 0);

        timePassed += Gdx.graphics.getDeltaTime();

        game.getBatch().draw(player.getCurrentFrame(), player.getX(), player.getY(), player.getWidth(), player.getHeight() * player.getyScale());

        game.getBatch().draw(weight, weight.getX(), weight.getY() );

        for ( Enemy enemy : enemies ) {
            game.getBatch().draw(enemy.getCurrentFrame(), enemy.getX(), enemy.getY());
        }

        game.getBatch().end();

        //The shapesRender doesn't work when drawn within the batch.
        //We also need it drawn over the background so stop the batch.
        gui.drawGui();  //Draw the gui.
        //Now that the gui had been drawn, begin the batch again to
        //draw everything else over on top of it.

        game.getBatch().begin();

        game.getBatch().draw(buttons, 0, 0);

        if ( !getAtGym() ) {
            loser.getTextFont().draw(game.getBatch(), loser.getText(), 120, 640); //batch, string, x, y
        }

        score.getScoreFont().draw(game.getBatch(), score.getScoreString(), 100, 1240); //batch, string, x, y
        
        game.getBatch().end();

        //Remove destroyed objects
        removeDestroyed();
    }

    public float getTimePassed() {
        return timePassed;
    }
    public Player getPlayer() { return player; }
    public Weight getWeight() { return weight; }
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
        background.dispose();
        buttons.dispose();
        game.dispose();
        gui.dispose();
        score.dispose();
    }

    private void spawnEnemy(){
        enemies.add(new Enemy("spr_enemy.atlas", this, 40 + random(WIDTH - 144), HEIGHT - 80));
    }

    private void removeDestroyed(){
        for ( int i = 0; i < enemies.size(); i++ ){
            if ( enemies.get(i).isDestroyed() ){
                enemies.remove(i);
            }
        }
    }
}