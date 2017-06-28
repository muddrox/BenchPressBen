package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class Player extends Sprite  {

    private Animation animation;
    private TextureAtlas playerAtlas;

    private float moveSpeed;
    private float direction;
    private float hsp;

    private float x;
    private float y;

    public Player(String name, float x, float y) {

        playerAtlas = new TextureAtlas(Gdx.files.internal(name));
        animation = new Animation(1/15f, playerAtlas.getRegions());

        this.x = x;
        this.y = y;

        moveSpeed = 5;
        direction = 0;
        hsp = 0;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public float getHsp() {
        return hsp;
    }

    public void updateMotion(){
        move();

        setX(x);
        setY(y);

        x += hsp;
    }

    public void move(){
        int left, right, dir, touchX, touchY;

        left = 0;
        right = 0;

        int isTouched = Gdx.input.isTouched()? 1 : 0;

        touchX = Gdx.input.getX();
        touchY = Gdx.input.getY();

        if ( x > 0 ) {
            if (touchX < 360) {
                left = -1;
            }
        }

        if ( x < 624 ) {
            if (touchX > 360 && touchX < 720) {
                right = 1;
            }
        }

        dir = left + right;

        hsp = ( moveSpeed * dir ) * isTouched;
    }
}