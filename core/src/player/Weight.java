package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import scenes.GameWorld;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.GameInfo.WIDTH;
import static java.lang.Math.signum;

/**
 * The Weight class represent the weight Ben throws, the methods necessary to update its motion; check
 * if the weight is being held by Ben, has Ben re-caught the weight after throwing it; and the variables
 * needed to manage the weight's horizontal and vertical speeds.
 *
 * @version
 * @since
 */
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

    private int xOffset;

    private Rectangle collisionMask;

    private int angle;

    /**
     * Weight constructor
     * It creates an instance of the weight's texture, sets the weight's starting position as well as
     * creates an instance of the weight's collisionMask.
     *
     * The constructor then initializes the weight's variables and sets its isHeld Boolean to true.
     *
     * @param name       the internal file name for the weight's texture
     * @param gameWorld
     * @param x          the starting X coordinate for the weight
     * @param y          the starting Y coordinate for the weight
     */
    public Weight(String name, GameWorld gameWorld, float x, float y) {
        super( new Texture(Gdx.files.internal(name)) );

        this.gameWorld = gameWorld;

        setOriginCenter();

        this.x = x;
        this.y = y;

        setX(this.x);
        setY(this.y);

        xOffset = 0;

        collisionMask = new Rectangle();

        moveSpeed = 5;
        direction = 0;

        gravity = 0.4f;

        hsp = 0;
        vsp = 0;

        isHeld = true;
    }

    /**
     *
     */
    public void updateMotion(){
        int touchX, touchY;

        int isTouched = Gdx.input.isTouched()? 1 : 0;

        touchX = Gdx.input.getX();
        touchY = Gdx.input.getY();

        if ( isTouched == 1 ) {
            if ( touchX > 720 && touchX < 1080 && touchY > Gdx.graphics.getHeight() - 240 && isHeld ) {
                gameWorld.getPlayer().setyScale(120f/144f);
            }
        } else {
            if ( gameWorld.getPlayer().getyScale() != 1 ){
                gameWorld.getSoundManager().get("audio/sounds/snd_throw.wav", Sound.class).play();
                gameWorld.getPlayer().setyScale(1f);

                setVsp(24);
                isHeld = false;
            }
        }

        if ( !isHeld ) {
            move();
        } else {
            hsp = 0;

            if ( xOffset != 0 ){
                if ( Math.abs(xOffset) > 5 ){
                    xOffset += 10 * -signum(xOffset);
                } else {
                    xOffset += 1  * -signum(xOffset);
                }
            }

            if ( angle != 0 ){
                if ( Math.abs(angle) > 5 ){
                    angle += 10 * -signum(angle);
                } else {
                    angle += 1 * -signum(angle);
                }
            }

            if ( gameWorld.getPlayer().getX() - 32 + xOffset > 40
                    && gameWorld.getPlayer().getX() + getWidth() - 32 + xOffset < WIDTH - 40) {
                x = ((gameWorld.getPlayer().getX() + gameWorld.getPlayer().getWidth() / 2) - getWidth() / 2) + xOffset;
            }

            int isSquatting = gameWorld.getPlayer().getyScale() != 1? 1 : 0;

            y = gameWorld.getPlayer().getY() + 120 - ( 24 * isSquatting );
        }

        setX(x);
        setY(y);

        x += hsp;
        y += vsp;

        collisionMask.set(x, y + 24, getWidth(), 16);
    }

    /**
     * move method controls the vertical movement of the weight after it is thrown. If the weight is
     * above the ground (y = 160) and vsp (vertical speed) is greater than -18, the weight is affected
     * by gravity. Else, set vsp to 0 and don't move the weight.
     */
    private void move(){
        if ( x < 40 || x > WIDTH - 40 - getWidth() ) {
            gameWorld.getSoundManager().get("audio/sounds/snd_bounce.wav", Sound.class).play();

            gameWorld.getGUI().setBorderColor( new Color(random(255f)/255f, random(255f)/255f, random(255f)/255f, 1) );
            gameWorld.getGUI().setFlicker(Color.YELLOW, 1);
            gameWorld.getGUI().setFrameEffect(20);

            hsp = -hsp;
        }

        if ( angle != -hsp ){
            angle += signum(-hsp);
        }

        if ( y > 160 ) {
            if (vsp > -18) {
                vsp -= gravity;
            }
        } else {
            setVsp(0);
        }
    }

    public boolean contact(Player player){
        return Intersector.overlaps(collisionMask,player.getMask());
    }

    /********************************************************************
     * Standard getters and setters
     ********************************************************************/

    public boolean isHeld() {
        return isHeld;
    }

    public void setHeld(boolean held) {
        isHeld = held;
    }

    public float getHsp() {
        return hsp;
    }

    public void setHsp(float hsp) { this.hsp = hsp; }

    public float getVsp() {
        return vsp;
    }

    public void setVsp(float vsp) {
        this.vsp = vsp;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public Rectangle getMask() {
        return collisionMask;
    }

    public int getAngle() { return angle; }
}