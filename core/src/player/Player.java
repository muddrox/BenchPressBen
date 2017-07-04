package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import scenes.GameWorld;

public class Player extends Sprite  {

    private GameWorld gameWorld;

    private Animation animation;
    private TextureAtlas playerAtlas;
    private TextureRegion currentFrame;

    private float moveSpeed;
    private int direction;
    private float hsp;

    private float x;
    private float y;

    private Rectangle collisionMask;

    public Player(String name, GameWorld gameWorld, float x, float y) {
        super ( new Texture(Gdx.files.internal("spr_player.png")) , 0, 0, 96, 144 );

        this.gameWorld = gameWorld;

        playerAtlas = new TextureAtlas(Gdx.files.internal(name));
        animation = new Animation(1/15f, playerAtlas.getRegions());

        this.x = x - getWidth()/2;
        this.y = y;

        collisionMask = new Rectangle();

        moveSpeed = 5;
        direction = 0;
        hsp = 0;
    }

    public void updateMotion(){
        move();

        setX(x);
        setY(y);

        x += hsp;

        collisionMask.set(x,y,getWidth(),getHeight());

        if ( hsp != 0 ) {
            currentFrame = (TextureRegion) animation.getKeyFrame(gameWorld.getTimePassed(), true);
        } else {
            currentFrame = (TextureRegion) animation.getKeyFrame(0, true);
        }
    }

    private void move(){
        int left, right, touchX, touchY;

        left = 0;
        right = 0;

        int isTouched = Gdx.input.isTouched()? 1 : 0;

        touchX = Gdx.input.getX();
        touchY = Gdx.input.getY();

        if ( x > 40 ) {
            if (touchX < 360 && touchY > Gdx.graphics.getHeight() - 240) {
                left = -1;
            }
        }

        if ( x < 720 - getWidth() - 40 ) {
            if (touchX > 360 && touchX < 720 && touchY > Gdx.graphics.getHeight() - 240) {
                right = 1;
            }
        }

        direction = left + right;

        hsp = ( moveSpeed * direction ) * isTouched;
    }

    public TextureRegion getCurrentFrame() { return currentFrame; }

    public Rectangle getMask() {
        return collisionMask;
    }
}