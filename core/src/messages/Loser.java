package messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import static com.badlogic.gdx.math.MathUtils.random;


/**
 * Created by wil15 on 7/7/2017.
 */

public class Loser{
    /**
     * private variables of loser class are text, font, x, and y
     */
    private String text;
    private BitmapFont textFont;
    private int x;
    private int y;


    /**
     * Loser constructor initializes private variables:
     * text font size and type. Constructor also set lose message
     * randomly from the enum
     *
     * @author Ethan Williams
     */
    public Loser() {

        textFont = new BitmapFont(Gdx.files.internal("darling_48.fnt"));
        textFont.getData().setScale(2f, 2f);
        textFont.setColor(0f, 1f, 1f, 1f);
        Messages youLost = Messages.randomMess();
        text = youLost.toString();
        setX(135);
        setY(665);
    }

    /**
     * getters for text and textFont
     * @return String, BitmapFont
     */
    public String getText() {
        return text;
    }

    public BitmapFont getTextFont() {
        return textFont;
    }

    /**
     * basic getters and setters for x and y variables
     *
     * @return int & void
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * getters for jitx and jity are for creating a jitter display
     *
     * @return int
     */
    public int getJitX(){
        int jitX = random(-12, 13);
        return (getX() + jitX);
    }

    public int getJitY(){
        int jitY = random(-12, 13);
        return (getY() + jitY);
    }
}
