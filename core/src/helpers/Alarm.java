package helpers;

/**
 * Created by muddr on 7/7/2017.
 */

public class Alarm {
    private float time;
    private float stopTime;
    private boolean isStarted;
    private boolean isFinished;

    public Alarm(float stopTime, boolean isStarted){
        this.stopTime = stopTime;
        this.isStarted = isStarted;
        isFinished = false;
        time = 0;
    }

    public void updateAlarm(float dt){
        if ( isStarted ) {
            if (time < stopTime) {
                time += dt;
            } else {
                isStarted = false;
                isFinished = true;
            }
        }
    }

    public void startAlarm(){
        isStarted = true;
    }

    public void resetAlarm(float stopTime, boolean isStarted){
        this.stopTime = stopTime;
        this.isStarted = isStarted;
        isFinished = false;
        time = 0;
    }

    public boolean isRunning() {
        return isStarted;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
