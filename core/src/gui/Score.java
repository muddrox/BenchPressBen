package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


/**
 * The Score class represents the score of the player as an object which contains an integer, string,
 * and BitmapFont.
 *
 * @author Aaron
 * @version 1.0
 * @since 7/3/2017
 */
public class Score {

    private int score;
    private String scoreString;
    private BitmapFont scoreFont;

    /**
     * The Constructor. It initializes the score (int), scoreString (String), creates an instance
     * of scoreFont (BitmapFont), and sets the scale (size) of the font.
     *
     */
    public Score() {

        score = 0;
        scoreString = "Score: 0";

        scoreFont = new BitmapFont(Gdx.files.internal("destinie_48.fnt"));
        scoreFont.getData().setScale(1.5f, 1.5f); //float scale x, float scale y
    }

    public void updateScore (int points) {
        score += points;
        scoreString = "Score: " + score;
    }

    /*************************************************
     * Standard getters
     *************************************************/
    public int getScore() { return score; }
    public String getScoreString() { return scoreString; }
    public BitmapFont getScoreFont() { return scoreFont; }
}
