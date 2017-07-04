package gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import scenes.GameWorld;


/**
 * Created by Aaron on 7/3/2017.
 */

public class Score extends SpriteBatch {
    private GameWorld gameWorld;

    private int score;
    private String scoreString;
    BitmapFont scoreFont;

    public Score(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        score = 0;
        scoreString = "Score: 0";
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(4.5f, 4.5f); //float scale x, float scale y
    }

    public int getScore() { return score; }
    public String getScoreString() { return scoreString; }
    public BitmapFont getBitmapFont() { return scoreFont; }



    //a brief psuedo-code to show the idea of how it looks like the scoreString can be updated
    /*
    if()) {
        score += 20;
        scoreString = "score: " + score;
    }
    */

}
