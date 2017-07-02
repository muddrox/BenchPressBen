package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import scenes.GameWorld;


public class Weight extends Sprite  {

    private GameWorld gameWorld;

    private float moveSpeed;
    private float direction;
    private boolean isHeld;

    private float hsp;
    private float vsp;

    private float gravity;

    private float x;
    private float y;

    private Rectangle collisionMask;

    public Weight(String name, GameWorld gameWorld, float x, float y) {
        super( new Texture(Gdx.files.internal(name)) );

        this.gameWorld = gameWorld;

        setOriginCenter();

        this.x = x;
        this.y = y;

        collisionMask = new Rectangle();

        moveSpeed = 5;
        direction = 0;

        gravity = 0.4f;

        hsp = 0;
        vsp = 0;

        isHeld = true;
    }

    public void updateMotion(){
        int touchX, touchY;

        int isTouched = Gdx.input.isTouched()? 1 : 0;

        touchX = Gdx.input.getX();
        touchY = Gdx.input.getY();

        if ( isTouched == 1 ) {
            if (touchX > 720 && touchX < 1080 && touchY > Gdx.graphics.getHeight() - 240 && isHeld == true) {
                vsp = 24;
                isHeld = false;
            }
        }

        if ( !isHeld ) {
            move();
        } else {
            x =  ( gameWorld.getPlayer().getX() + gameWorld.getPlayer().getWidth()/2 ) - getWidth()/2;
            y = gameWorld.getPlayer().getY() + 120;
        }

        setX(x);
        setY(y);

        x += hsp;
        y += vsp;

        collisionMask.set(x, y + 24, getWidth(), 16);
    }

    private void move(){
        if ( y > 160 ) {
            if (vsp > -18) {
                vsp -= gravity;
            }
        } else {
            vsp = 0;
        }
    }

    public boolean contact(Player player){
        return Intersector.overlaps(collisionMask,player.getMask());
    }

    public float getHsp() {
        return hsp;
    }

    public boolean isHeld() {
        return isHeld;
    }

    public void setHeld(boolean held) {
        isHeld = held;
    }

    public float getVsp() {
        return vsp;
    }

    public void setVsp(float vsp) {
        this.vsp = vsp;
    }
}