package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import helpers.Alarm;


/**
 * The PointsQueue class is intended to function as a visual queue whereby a player's earned points
 * can be ticked up, held for a moment, and then ticked into the actual Score class
 *
 * @author Aaron
 * @version 0.7
 * @since 7/11/2017
 */
public class PointsQueue {

    private int points;
    private int pointsCounter;

    private Alarm tickerHoldAlarm;

    private Boolean pointsIncoming;
    private Boolean pointsInCounter;
    private Boolean tickedUp;

    private String queueString;
    private BitmapFont queueFont;

    /**
     * The constructor
     *
     */
    public PointsQueue() {

        points = 0;
        pointsCounter = 0;

        pointsIncoming = false;
        pointsInCounter = false;
        tickedUp = false;

        tickerHoldAlarm = new Alarm(1.5f, false);

        queueString = "+";
        queueFont = new BitmapFont(Gdx.files.internal("arial_large.fnt"));
        queueFont.getData().setScale(1.25f, 1.25f);
    }

    public void addToQueue(int points) {
        this.points += points;
        pointsIncoming = true;
        tickedUp = false;
    }

    public void tickUpPoints() {
        if (this.points > 0) {
            pointsCounter += 1;
            pointsInCounter = true;

            this.points -= 1;
            queueString = "+" + pointsCounter;
        } else {
            pointsIncoming = false;
            tickedUp = true;
            tickerHoldAlarm.startAlarm();
        }
    }

    public void tickDownPoints(Score scoreObject) {
        if (pointsCounter > 0) {
            pointsCounter -= 1;
            scoreObject.updateScore(1);
            queueString = "+" + pointsCounter;
        } else {
            pointsInCounter = false;
            tickerHoldAlarm.resetAlarm(1.5f, false);
        }
    }

    public void updateTickerHold() {
        tickerHoldAlarm.updateAlarm(Gdx.graphics.getDeltaTime());
    }

    /*************************************************
     * Standard getters
     *************************************************/
    public String getQueueString() {return queueString; }
    public BitmapFont getQueueFont() { return queueFont; }
    public Boolean tickerHoldIsDone() { return tickerHoldAlarm.isFinished(); }
    public Boolean isPointsInCounter() { return pointsInCounter; }
    public Boolean isPointsIncoming() { return pointsIncoming; }
    public Boolean isTickedUp() { return tickedUp; }
}
