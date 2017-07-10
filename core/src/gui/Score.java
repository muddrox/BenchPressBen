package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;

import scenes.GameWorld;


/**
 * The Score class represents the score of the player as an object which contains an integer, string,
 * and BitmapFont.
 *
 * @author Aaron
 * @version 1.0
 * @since 7/3/2017
 */
public class Score extends SpriteBatch {
    private GameWorld gameWorld;

    private int score;
    private String scoreString;
    private BitmapFont scoreFont;

    /**
     * The Constructor. It initializes the score (int), scoreString (String), creates an instance
     * of scoreFont (BitmapFont), and sets the scale (size) of the font.
     *
     * @param gameWorld the instance of the GameWorld that is being played in currently
     */
    public Score(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        score = 0;
        scoreString = "Score: 0";

        scoreFont = new BitmapFont(Gdx.files.internal("arial_large.fnt"));
        scoreFont.getData().setScale(2f, 2f); //float scale x, float scale y
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
