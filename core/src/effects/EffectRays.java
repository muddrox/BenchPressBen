package effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import helpers.Alarm;
import scenes.GameWorld;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.GameInfo.HEIGHT;
import static helpers.GameInfo.WIDTH;

/**
 * Created by muddr on 7/11/2017.
 */

public class EffectRays extends ShapeRenderer {

    private float x;
    private float y;

    private Array<Corner> corner1;
    private Array<Corner> corner2;

    private float length;
    private float alpha;
    private float angle;
    private float delay;

    private int numOfRays;

    private Alarm lifeTimer;
    private Alarm changeTimer;

    private GameWorld gameWorld;

    private boolean destroyed;

    public EffectRays(GameWorld gameWorld, float x, float y, float length, int numOfRays, float life){
        this.x = x;
        this.y = y;

        this.length = length;
        this.numOfRays = numOfRays;

        delay = 0.025f;

        lifeTimer = new Alarm(life, true);
        changeTimer = new Alarm(delay,true);

        corner1 = new Array<Corner>();
        corner2 = new Array<Corner>();

        angle = 0f;
        alpha = random(.5f, 1f);

        this.gameWorld = gameWorld;

        for (int i = 0; i < numOfRays; i++ ){
            float len = random(length);
            angle = random(360f);

            corner1.add(new Corner(x + Math.cos(angle * Math.PI / 180f) * len, y + Math.sin(angle * Math.PI / 180f) * len));
            corner2.add(new Corner(x + Math.cos((angle - 10f) * Math.PI / 180f) * (len - 10f), y + Math.sin((angle - 10f) * Math.PI / 180f) * (len - 10f)));
        }
    }

    public void drawRays(){

        manageTimers();

        gameWorld.getCam().update();
        setProjectionMatrix(gameWorld.getCam().combined);

        begin(ShapeRenderer.ShapeType.Filled);

        setColor(new Color(random(255f)/255f, random(255f)/255f, random(255f)/255f, alpha));

        for (int i = 0; i < numOfRays; i++) {
            triangle(x, y, corner1.get(i).x, corner1.get(i).y, corner2.get(i).x, corner2.get(i).y);
        }

        end();
    }

    public void manageTimers(){

        if ( changeTimer.isFinished() ) {
            for (int i = 0; i < numOfRays; i++) {
                float len = random(length);
                angle = random(360f);

                corner1.get(i).x = (float) (x + Math.cos(angle * Math.PI / 180f) * len);
                corner1.get(i).y = (float) (y + Math.sin(angle * Math.PI / 180f) * len);

                corner2.get(i).x = (float) (x + Math.cos((angle - 10f) * Math.PI / 180f) * (len - 10f));
                corner2.get(i).y = (float) (y + Math.sin((angle - 10f) * Math.PI / 180f) * (len - 10f));

                changeTimer.resetAlarm(delay,true);
            }
        }

        if ( lifeTimer.isFinished() ){
            destroyed = true;
        }

        changeTimer.updateAlarm(Gdx.graphics.getDeltaTime());
        lifeTimer.updateAlarm(Gdx.graphics.getDeltaTime());
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }
}
