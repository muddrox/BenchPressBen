package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import scenes.GameWorld;

/**
 * The Player class represents Ben, i.e, the character the user plays as. This class holds the variables
 * and methods needed for Ben to appear and move on screen.
 *
 * @version
 * @since
 */
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

    private float yScale;

    private Rectangle collisionMask;

    /**
     * Player Constructor.
     * It creates an instance of Ben's sprite (texture), an instance of his textureAtlas and an instance of his move animation.
     * It initializes Ben's X and Y coordinates and his moveSpeed, direction, yScale, and hsp variables.
     * The constructor also creates an instance of Ben's collisionMask
     *
     * @param name       the internal file name of Ben's textureAtlas
     * @param gameWorld
     * @param x          the starting X coordinate for Ben
     * @param y          the starting Y coordinate for Ben
     */
    public Player(String name, GameWorld gameWorld, float x, float y) {
        super ( new Texture(Gdx.files.internal("spr_player.png")) , 0, 0, 96, 144 );

        this.gameWorld = gameWorld;

        playerAtlas = new TextureAtlas(Gdx.files.internal(name));
        animation = new Animation(1/15f, playerAtlas.getRegions());

        this.x = x - getWidth()/2;
        this.y = y;

        setX(x);
        setY(y);

        collisionMask = new Rectangle();

        moveSpeed = 5;
        direction = 0;

        yScale = 1f;

        hsp = 0;
    }

    /**
     * updateMotion method sets the X and Y coordinates that were updated in the previous cycle.
     * It then modifies Ben's X with the horizontal speed (hsp) that was updated by the move function.
     *
     * The collisionMask is updated with Ben's new X and Y coordinates Finally, if Ben is moving (hsp != 0)
     * than Ben performs his walking animation.
     */
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

    /**
     * move method checks whether the screen is being touched (isTouched) and then determines where
     * the screen is being touched. It stores the touch location in touchX and touchY, then checks
     * that Ben is not already at either edge of the screen.
     *
     * After that check, the move method compares touchX and touchY to see what button (if any) was
     * being pressed and assigns the left and right variables accordingly. move method then calculates
     * left and right to form a direction. Left can only equal 0 or -1 and right can only equal 0 or 1,
     * therefore, the sum of these two will always equal -1, 0- or 1.
     *
     * Finally, hsp is calculated.
     */
    private void move(){
        int left, right, touchX, touchY;

        left = 0;
        right = 0;

        int isTouched = Gdx.input.isTouched()? 1 : 0;

        touchX = Gdx.input.getX();
        touchY = Gdx.input.getY();

        if (gameWorld.getWeight().isHeld()) {
            if (x > 80) {
                if (touchX < 360 && touchY > Gdx.graphics.getHeight() - 240) {
                    left = -1;
                }
            }

            if (x < 680 - getWidth() - 40) {
                if (touchX > 360 && touchX < 720 && touchY > Gdx.graphics.getHeight() - 240) {
                    right = 1;
                }
            }
        } else {
            if (x > 40) {
                if (touchX < 360 && touchY > Gdx.graphics.getHeight() - 240) {
                    left = -1;
                }
            }

            if (x < 720 - getWidth() - 40) {
                if (touchX > 360 && touchX < 720 && touchY > Gdx.graphics.getHeight() - 240) {
                    right = 1;
                }
            }
        }

        direction = left + right;

        hsp = ( moveSpeed * direction ) * isTouched;
    }


    /********************************************************************
     * Standard getters and setters
     ********************************************************************/
    public TextureRegion getCurrentFrame() { return currentFrame; }

    public Rectangle getMask() {
        return collisionMask;
    }

    public float getyScale() { return yScale; }

    public void setyScale(float yScale) {
        this.yScale = yScale;
    }
}