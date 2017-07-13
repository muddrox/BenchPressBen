package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

    private Integer score;
    private Integer highScore;
    private String scoreString;
    private String highString;
    private BitmapFont scoreFont;
    private BitmapFont highFont;

    private Preferences prefs;

    /**
     * The Constructor. It initializes the score (int), scoreString (String), creates an instance
     * of scoreFont (BitmapFont), and sets the scale (size) of the font.
     *
     */
    public Score() {

        // read in highscore
        prefs = Gdx.app.getPreferences("The high score");
        highScore = prefs.getInteger("highscore");
        System.out.println("highscore: " + highScore);

        score = 0;
        scoreString = "Score: 0";
        highString = "High Score: " + highScore;

        scoreFont = new BitmapFont(Gdx.files.internal("destinie_48.fnt"));
        highFont = scoreFont;

    }

    public void updateScore (int points) {
        score += points;
        scoreString = "Score: " + score;
    }

    /*************************************************
     * Standard getters
     *************************************************/
    public int getScore()            { return score; }
    public String getScoreString()   { return scoreString; }
    public BitmapFont getScoreFont() { return scoreFont; }
    public int getHighScore()        { return highScore; }
    public String getHighString()    { return highString; }
    public BitmapFont getHighFont()  { return highFont; }

    /**
     * highScore setter
     * @param highScore
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
