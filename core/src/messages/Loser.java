package messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


/**
 * Created by wil15 on 7/7/2017.
 */

public class Loser{

    private String text;
    private BitmapFont textFont;

    /**
     * Loser constructor initializes private variables:
     * text font size and type
     *
     * @author Ethan Williams
     */
    public Loser() {
        textFont = new BitmapFont(Gdx.files.internal("arial_large.fnt"));
        textFont.getData().setScale(2f, 2f);
        textFont.setColor(0f, 9f, 4f, 0f);
        text = Messages.ONE.toString();
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
}
