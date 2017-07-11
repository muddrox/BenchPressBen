package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Queue;

import scenes.GameWorld;


/**
 * The PointsQueue class is intended to function as a visual queue whereby a player's earned points
 * can be ticked up, held for a moment, and then ticked into the actual Score class
 *
 * @author Aaron
 * @version 0.5
 * @since 7/7/2017
 */
public class PointsQueue {
    private GameWorld gameWorld;

    public int points;
    public int pointsCounter;

    private Boolean pointsIncoming;
    private Boolean pointsInCounter;
    private Boolean tickedUp;

    private String queueString;
    private BitmapFont queueFont;
    private Queue scoreQueue;

    /**
     * The constructor
     *
     * @param gameWorld
     */
    public PointsQueue(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        points = 100;
        pointsCounter = 0;

        pointsIncoming = true;
        pointsInCounter = true;
        tickedUp = false;

        queueString = "+";
        queueFont = new BitmapFont(Gdx.files.internal("arial_large.fnt"));
        queueFont.getData().setScale(1.25f, 1.25f);

        scoreQueue = new Queue();
    }


    public void insertIntoQueue(int points) {
        this.points += points;
        scoreQueue.addFirst(points);
    }

    public void addToQueue(int points) {
        this.points += points;
        pointsInCounter = true;
    }

    public void tickUpPoints() {
        if (this.points > 0) {
            pointsCounter += 1;
            this.points -= 1;
            queueString = "+" + pointsCounter;
        } else {
            pointsIncoming = false;
            tickedUp = true;
        }
    }

    public void tickDownPoints(Score scoreObject) {
        if (pointsCounter > 0) {
            pointsCounter -= 1;
            scoreObject.updateScore(1);
            queueString = "+" + pointsCounter;
        } else {
            pointsInCounter = false;
        }
    }

    public void clearQueue() {

    }

    /*************************************************
     * Standard getters
     *************************************************/
    public String getQueueString() {return queueString; }
    public BitmapFont getQueueFont() { return queueFont; }
    public Boolean isPointsInCounter() { return pointsInCounter; }
    public Boolean isPointsIncoming() { return pointsIncoming; }
    public Boolean isTickedUp() { return tickedUp; }
}
