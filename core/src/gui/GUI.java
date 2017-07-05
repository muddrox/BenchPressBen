package gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

import scenes.GameWorld;

import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

public class GUI extends ShapeRenderer {

    private GameWorld gameWorld;

    private boolean flickerOn;

    private Color flickerCol;

    public GUI(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        flickerOn = true;
        flickerCol = Color.BLACK;
    }

    public void drawGui() {
        gameWorld.getCam().update();
        setProjectionMatrix(gameWorld.getCam().combined);

        begin(ShapeRenderer.ShapeType.Filled);

        setColor(flickerCol);

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

    public boolean isFlickerOn() {
        return flickerOn;
    }

    public void setFlickerOn(boolean flickerOn) {
        this.flickerOn = flickerOn;

        if ( flickerOn ) {
            setFlickTime(0, 5);
        } else {
            flickerCol = Color.BLACK;
            Timer.instance().clear();
        }
    }

    private void setFlickTime(int currentFlicks, int maxFlicks) {
        final int current, max;

        current = currentFlicks;
        max = maxFlicks;

        if (flickerCol == Color.PURPLE) {
            flickerCol = Color.BLACK;
        } else {
            flickerCol = Color.PURPLE;
        }

        if ( current < max ) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setFlickTime(current + 1, max);
                }
            }, .05f);
        } else {
            setFlickerOn(false);
            return;
        }
    }
}
