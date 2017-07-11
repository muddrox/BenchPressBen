package enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import helpers.Alarm;
import scenes.GameWorld;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.randomSign;
import static helpers.GameInfo.WIDTH;
import static helpers.GameInfo.HEIGHT;

public class Enemy extends Sprite  {

    private GameWorld gameWorld;

    private Animation animation;
    private TextureAtlas enemyAtlas;
    private TextureRegion currentFrame;

    private float moveSpeed;
    private int direction;
    private float hsp;
    private float vsp;

    private float x;
    private float y;

    private float minTime;
    private float maxTime;

    private Alarm moveTimer;

    private Rectangle collisionMask;

    private boolean destroyed;

    public Enemy(String name, GameWorld gameWorld, float x, float y) {
        super ( new Texture(Gdx.files.internal("spr_enemy.png")) , 0, 0, 64, 64 );

        this.gameWorld = gameWorld;

        enemyAtlas = new TextureAtlas(Gdx.files.internal(name));
        animation = new Animation(1/4f, enemyAtlas.getRegions());

        this.x = x - getWidth()/2;
        this.y = y;

        setX(x);
        setY(y);

        setOriginCenter();
        collisionMask = new Rectangle();

        direction = randomSign();
        moveSpeed = 5f;

        minTime = 1.0f;
        maxTime = 2.5f;

        hsp = 0;
        vsp = -1;

        moveTimer = new Alarm(random(minTime,maxTime), true);

        destroyed = false;
    }

    public void updateMotion(){
        move();

        setX(x);
        setY(y);

        x += hsp;
        y += vsp;

        collisionMask.set(x,y,getWidth(),getHeight());
        currentFrame = (TextureRegion) animation.getKeyFrame(gameWorld.getTimePassed(), true);

        if ( currentFrame.isFlipX() && direction == 1) {
            currentFrame.flip(true, false);
        }

        if ( !currentFrame.isFlipX() && direction == -1) {
            currentFrame.flip(true, false);
        }
    }

    private void move(){
        moveTimer.updateAlarm(Gdx.graphics.getDeltaTime());

        if ( direction == 1 ) {
            if ( hsp < moveSpeed * direction ) {
                hsp += .05;
            }
        } else {
            if ( hsp > moveSpeed * direction ) {
                hsp -= .05;
            }
        }

        if ( x < 40 || x > WIDTH - 40 - getWidth() ){
            direction = -direction;
            hsp = -hsp;

            moveTimer.resetAlarm(random(minTime,maxTime), true);
        }

        if ( moveTimer.isFinished() ){
            direction = -direction;
            moveTimer.resetAlarm(random(minTime,maxTime), true);
        }
    }

    public TextureRegion getCurrentFrame() { return currentFrame; }

    public Rectangle getMask() {
        return collisionMask;
    }

    public int getDirection() { return direction; }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }
}