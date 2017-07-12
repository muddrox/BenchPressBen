package gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import scenes.GameWorld;

import static com.badlogic.gdx.math.MathUtils.random;
import static helpers.GameInfo.HEIGHT;

/**
 * Created by muddr on 7/12/2017.
 */

public class Marker extends ShapeRenderer {

    private GameWorld gameWorld;

    private float x;
    private float y;

    public Marker(GameWorld gameWorld, float x, float y){
        this.gameWorld = gameWorld;

        this.x = x;
        this.y = y;
    }

    public void drawMarker(){
        gameWorld.getCam().update();
        setProjectionMatrix(gameWorld.getCam().combined);

        begin(ShapeRenderer.ShapeType.Filled);

        setColor(new Color(random(255f)/255f, random(255f)/255f, random(255f)/255f, 1));

        triangle(x - 16, y, x, y + 40, x + 16, y);

        end();
    }

    public void setMarkerX(float x) {
        this.x = x;
    }

    public void setMarkerY(float y) {
        this.y = y;
    }
}
