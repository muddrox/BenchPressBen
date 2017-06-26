package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class Player extends Sprite implements InputProcessor {

    private Animation animation;
    private TextureAtlas playerAtlas;

    private float moveSpeed;
    private float direction;
    private float hsp;

    public Player(String name, float x, float y) {

        playerAtlas = new TextureAtlas(Gdx.files.internal(name));
        animation = new Animation(1/15f, playerAtlas.getRegions());

        setPosition( x , y );
        //setPosition( x - getWidth() / 2f, y - getHeight() / 2f );

        moveSpeed = 5;
        direction = 0;
        hsp = 0;

        Gdx.input.setInputProcessor(this);
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public float getHsp() {
        return hsp;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int left, right, dir;

        if ( screenX > Gdx.graphics.getWidth() / 2 ){
            right = 1;
        } else {
            right = 0;
        }

        if ( screenX < Gdx.graphics.getWidth() / 2 ){
            left = -1;
        } else {
            left = 0;
        }

        dir = left + right;

        hsp = moveSpeed * dir;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        hsp = 0;
        return true;
    }

    /******************************************************
     * Don't worry about the methods below.  Don't delete them either.
     * ****************************************************/

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}