package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

import helpers.Alarm;
import scenes.GameWorld;

import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

public class GUI extends ShapeRenderer {

    private GameWorld gameWorld;

    private Color flickerCol;

    private Alarm flickTimer;

    private int flickCurrent;

    private Color frameCol;

    private int flickCap;

    public GUI(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        flickTimer = new Alarm(0, false);

        frameCol = Color.BLACK;

        flickCurrent = 0;
        flickCap = 0;
    }

    public void drawGui() {
        gameWorld.getCam().update();
        setProjectionMatrix(gameWorld.getCam().combined);

        drawFlicker();

        begin(ShapeRenderer.ShapeType.Filled);

        setColor(frameCol);

        //Black Borders
        rect(        40,          160,   -40, HEIGHT - 280); //left   border
        rect(         0, HEIGHT - 120, WIDTH,          120); //top    border
        rect(WIDTH - 40,          160,    40, HEIGHT - 280); //right  border
        rect(         0,          160, WIDTH,        - 160); //bottom border

        //Thin Borders
        float fSize = 12; //frame size
        setColor(0, 128f/255f, 128/255f, 1);

        rect(        40,  160 - fSize,                   -fSize, HEIGHT - 280 + (fSize * 2) ); //left   border
        rect(40 - fSize, HEIGHT - 120, WIDTH - 80 + (fSize * 2),                      fSize ); //top    border
        rect(WIDTH - 40,  160 - fSize,                    fSize, HEIGHT - 280 + (fSize * 2) ); //right  border
        rect(40 - fSize,          160, WIDTH - 80 + (fSize * 2),                    - fSize ); //bottom border

        end();
    }

    public void setFlicker(Color flickerCol, int flickCap) {
        this.flickerCol = flickerCol;
        this.flickCap = flickCap;
        flickCurrent = 0;
        flickTimer.startAlarm();
    }

    private void drawFlicker() {
        if ( flickCurrent >= flickCap ){
            frameCol = Color.BLACK;
            flickTimer.resetAlarm(0,false);
            return;
        }

        flickTimer.updateAlarm(Gdx.graphics.getDeltaTime());

        if ( flickTimer.isFinished() ) {

            if (frameCol == flickerCol) {
                frameCol = Color.BLACK;
            } else {
                frameCol = flickerCol;
            }

            flickTimer.resetAlarm(.025f, true);
            flickCurrent++;
        }
    }
}
