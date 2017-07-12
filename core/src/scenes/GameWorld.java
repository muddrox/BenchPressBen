package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.benchpressben.game.GameMain;

import java.util.ArrayList;

import effects.EffectExplode;
import effects.EffectRays;
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
    private AssetManager soundManager;
    private Music music;
    private float timePassed = 0;
    private Player player;
    private Texture background;
    private Texture buttons;
    private Weight weight;
    private Score score;
    private PointsQueue pQueue;
    private Boolean atGym;
    private ArrayList<Enemy> enemies;
    private ArrayList<EffectRays> rays;
    private ArrayList<EffectExplode> explosions;
    private GUI gui;

    private float minTime;
    private float maxTime;

    private Alarm resetGameAlarm;
    private Alarm spawnAlarm;

    private Loser loser;

    public GameWorld(GameMain game, AssetManager soundManager) {
        this.game = game;
        this.soundManager = soundManager;

        music = soundManager.get("audio/music/snd_bensound_main.mp3", Music.class);
        music.setLooping(true);
        music.play();

        player  = new Player("spr_player.atlas", this, 360, 160);
        weight  = new Weight("spr_weight.png", this, 360, 640);
        enemies = new ArrayList<Enemy>();
        rays = new ArrayList<EffectRays>();
        explosions  = new ArrayList<EffectExplode>(); //new EffectExplode("spr_explode.atlas", this, 360, 640);

        background = new Texture("bg_main.png");
        buttons = new Texture("bg_buttons.png");
        atGym   = true;

        gui = new GUI(this);
        loser = new Loser();
        score = new Score();
        pQueue = new PointsQueue();

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

                if ( enemy.contact(weight) && !weight.isHeld() ){
                    long s_explode = soundManager.get("audio/sounds/snd_hit.wav", Sound.class).play();
                    soundManager.get("audio/sounds/snd_hit.wav", Sound.class).setPitch(s_explode, random(1f, 2f));
                    rays.add(new EffectRays(this, enemy.getX() + enemy.getWidth()/2, enemy.getY() + enemy.getHeight()/2, 100, 4, 0.18f));
                    explosions.add(new EffectExplode("spr_explode.atlas", this, enemy.getX() + enemy.getWidth()/2, enemy.getY() + enemy.getHeight()/2));

                    weight.setHsp( ( (weight.getX() + weight.getWidth()/2) - (enemy.getX() + enemy.getWidth()/2) ) * 0.2f );
                    weight.setVsp(15);

                    pQueue.addToQueue(5);
                    enemy.destroy();
                }

                if ( enemy.getY() < 160 - enemy.getHeight() ) {
                    enemy.destroy();
                }
            }

            //update the frames for explosion effects
            for ( EffectExplode explode : explosions ) {
                explode.updateFrames();
            }

        } else {

            if ( !resetGameAlarm.isRunning() ){
                soundManager.get("audio/sounds/snd_fail.wav", Sound.class).play();
                resetGameAlarm.startAlarm();

                rays.add(new EffectRays(this, weight.getX() + weight.getWidth()/2, weight.getY() + weight.getHeight()/2, 640, 6, 10));
            }

            resetGameAlarm.updateAlarm(Gdx.graphics.getDeltaTime());

            if ( resetGameAlarm.isFinished() ){
                game.dispose();
                game.create();
            }
        }

        if ( weight.contact(player) && !weight.isHeld() && weight.getVsp() < 0 ){
            weight.setxOffset((int) (( weight.getX() + weight.getWidth()/2 ) - ( player.getX() + player.getWidth()/2 )));
            soundManager.get("audio/sounds/snd_catch.wav", Sound.class).play();
            gui.setFlicker(Color.PURPLE, 5);
            weight.setHeld(true);
        }

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().end();

        timePassed += Gdx.graphics.getDeltaTime();

        //The shapesRender doesn't work when drawn within the batch.
        //We also need it drawn over the background so stop the batch.
        for ( EffectRays ray : rays ) {
            ray.drawRays();  //draw the rays
        }
        //Now that the rays had been drawn, begin the batch again to
        //draw everything else over on top of it.

        game.getBatch().begin();

        game.getBatch().draw(player.getCurrentFrame(), player.getX(), player.getY(), player.getWidth(), player.getHeight() * player.getyScale());

        game.getBatch().draw(weight, weight.getX(), weight.getY(), weight.getOriginX(), weight.getOriginY(), weight.getWidth(), weight.getHeight(), 1, 1, weight.getAngle() );

        for ( Enemy enemy : enemies ) {
            game.getBatch().draw(enemy.getCurrentFrame(), enemy.getX(), enemy.getY());
        }

        for ( EffectExplode explode : explosions ){
            game.getBatch().draw(explode.getCurrentFrame(), explode.getX(), explode.getY(), explode.getOriginX(), explode.getOriginY(), explode.getWidth(), explode.getHeight(), 1, 1, explode.getAngle() );
        }

        game.getBatch().end();

        //The shapesRender doesn't work when drawn within the batch.
        //We also need it drawn over the background, player, enemies
        //and weight so stop the batch.
        gui.drawGui();  //Draw the gui.
        //Now that the gui had been drawn, begin the batch again to
        //draw everything else over on top of it like
        //the buttons, fonts, etc.

        game.getBatch().begin();

        game.getBatch().draw(buttons, 0, 0);

        score.getScoreFont().draw(game.getBatch(), score.getScoreString(), 100, 1240); //batch, string, x, y

        if ( !getAtGym() ) {
            String haters = loser.getText();

            // change color then jitter
            loser.getTextFont().setColor(random(255f)/255f, random(255f)/255f, random(255f)/255f, 1f);
            loser.getTextFont().draw(game.getBatch(), haters, loser.getJitX(), loser.getJitY()); //batch, string, x, y

            loser.getTextFont().setColor(0f, 1f, 1f, 1f);
            loser.getTextFont().draw(game.getBatch(), haters, loser.getX(), loser.getY()); //batch, string, x, y

            loser.shrinkText();
        }

        if(pQueue.isPointsIncoming()) {
            pQueue.getQueueFont().draw(game.getBatch(), pQueue.getQueueString(), 500, 1240); //batch, string, x, y
            pQueue.tickUpPoints();
        } else if (pQueue.isTickedUp()) {
            if(pQueue.isPointsInCounter()) {
                pQueue.getQueueFont().draw(game.getBatch(), pQueue.getQueueString(), 500, 1240); //batch, string, x, y
            }

            if(pQueue.tickerHoldIsDone()) {
                pQueue.tickDownPoints(score);
            } else {
                pQueue.updateTickerHold();
            }
        }
        
        game.getBatch().end();

        //Remove destroyed objects
        removeDestroyed();
    }

    /**
     * GameWorld getters include:
     * timePassed, player, weight, gui, cam, and atGym
     * @return
     */
    public float getTimePassed() { return timePassed; }
    public AssetManager getSoundManager() { return soundManager; }
    public Player getPlayer() { return player; }
    public Weight getWeight() { return weight; }
    public GUI getGUI() { return gui; }
    public OrthographicCamera getCam() { return game.getCam(); }
    public Boolean getAtGym() { return atGym; }

    /**
     * GameWorld setters:
     * public setAtGym()
     * @param atGym
     */
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
        soundManager.dispose();
        background.dispose();
        buttons.dispose();
        game.dispose();
        gui.dispose();
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

        for ( int i = 0; i < rays.size(); i++ ){
            if ( rays.get(i).isDestroyed() ){
                rays.remove(i);
            }
        }

        for ( int i = 0; i < explosions.size(); i++ ){
            if ( explosions.get(i).isDestroyed() ){
                explosions.remove(i);
            }
        }
    }
}